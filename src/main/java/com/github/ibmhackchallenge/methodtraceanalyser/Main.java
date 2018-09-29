/*
 * Copyright 2018 Sidhu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ibmhackchallenge.methodtraceanalyser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Main extends JFrame implements ActionListener {

    JFrame frame;
    File[] logFiles;
    JButton browseBtn;
    JButton analyseBtn;
    DefaultListModel<File> filesList;
    Analyser analyse;

    Main() {
        frame = new JFrame();

        // Browse Button (Browse Tab)
        browseBtn = new JButton("Browse");
        browseBtn.setBounds(50, 100, 95, 30);
        browseBtn.addActionListener(this);
        
        // Analyse Button (Browse Tab)
        analyseBtn = new JButton("Analyse");
        analyseBtn.setBounds(50, 100, 95, 30);
        analyseBtn.addActionListener(this);

        // ListModel (Browse Tab)
        filesList = new DefaultListModel<>();

        // List Box (Browse Tab)
        JList<File> list = new JList<>(filesList);
        list.setBounds(5, 20, 50, 100);

        // Browse Panel (Browse Tab)
        JPanel browsePanel = new JPanel();
        browsePanel.add(list);
        browsePanel.add(browseBtn);
        browsePanel.add(analyseBtn);

        // Tab Pane
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setBounds(0, 0, 1000, 600);
        tabPane.add("main", browsePanel);

        // frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tabPane);
        frame.setSize(1000, 600);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseBtn) {
            // File Chooser (Browse Tab)
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(frame);
            logFiles = chooser.getSelectedFiles();
            for (File file : logFiles) {
                filesList.addElement(file);
            }
        } else if(e.getSource() == analyseBtn) {
            try {
                // Analyser
                analyse = new Analyser(logFiles);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            // TABLE
            ArrayList analysedTime = analyse.getAnalysedTime();
            for(int itr1 = 0; itr1 < analysedTime.size(); itr1++) {
                for(int itr2 = 0; itr2 < ((ArrayList) analysedTime.get(itr1)).size(); itr2++) {
                    System.out.print("|" + ((ArrayList) analysedTime.get(itr1)).get(itr2) + "|");
                }
                System.out.println();
            }
        }
    }
}
