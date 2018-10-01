package com.github.ibmhackchallenge.methodtraceanalyser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;

public class Graph extends ApplicationFrame {

    public Graph(String applicationTitle, String chartTitle) throws IOException {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Method Names",
                "Time Taken (in ms)",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset() throws IOException {

        File[] in = new File[2];
        in[0] = new File("sample_logs/logfile1a");
        in[1] = new File("sample_logs/logfile1c");
        //in[1] = new File("sample_logs/logfile1c");
        Analyser analyse = new Analyser(in);

        Object[] labels = analyse.getLogFiles().toArray();

        Object[][] time = Tools.toArray(analyse.getAnalysedTime(), ((ArrayList) analyse.getAnalysedTime().get(0)).size());
        
        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        System.out.println(Arrays.deepToString(time));
        System.out.println(Arrays.deepToString(labels));
        
        // dataset.addValue(yVal (time), xLabel (logfiles), xVal (method))
        for(Object[] element: time) {
            for(int i = 1; i < labels.length; i++) {
                dataset.addValue( (Long) element[i],(String) labels[i],((String) element[0]));
            }
        }
        return dataset;
    }

    public static void main(String[] args) throws IOException {
        Graph chart = new Graph("Method Time Invocation",
                "Method Time Invocation");
        chart.pack();
        UIUtils.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
