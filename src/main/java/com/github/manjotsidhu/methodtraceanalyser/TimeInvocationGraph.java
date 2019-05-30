package com.github.manjotsidhu.methodtraceanalyser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JInternalFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Renders Graph for time taken by each method to execute.
 * 
 * @author Manjot Sidhu
 */
public class TimeInvocationGraph extends JInternalFrame {

    public TimeInvocationGraph(String applicationTitle, String chartTitle, Object[] logFiles, ArrayList analysedTable) throws IOException {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Method Names",
                "Time Taken (in ms)",
                createDataset(logFiles, analysedTable),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset(Object[] logFiles, ArrayList analysedTable) throws IOException {
        //System.out.println(Arrays.deepToString(logFiles.toArray()));
        Object[] labels = logFiles;
        Object[][] time = Tools.toArray(analysedTable, ((ArrayList) analysedTable.get(0)).size());
        
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
}
