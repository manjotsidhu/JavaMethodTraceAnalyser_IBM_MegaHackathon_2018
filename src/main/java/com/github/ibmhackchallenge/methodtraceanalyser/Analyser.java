/*
 * Copyright 2018 Manjot Sidhu <manjot.techie@gmail.com>.
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

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The heart of method trace analyser
 *
 * @author manjotsidhu
 */
public class Analyser {

    private final ArrayList logSequence = new ArrayList();
    private final ArrayList logText = new ArrayList();

    private final ArrayList anomalies = new ArrayList();
    private final ArrayList anomaliesType = new ArrayList();
    private final ArrayList anomaliesMethod = new ArrayList();
    private final ArrayList anomaliesTime = new ArrayList();

    private final ArrayList analysedTime = new ArrayList();
    private final ArrayList<Integer> analysedBufferMethods = new ArrayList();
    private final ArrayList analysedTimeMethods = new ArrayList();

    private final ArrayList graphAnalysedTime = new ArrayList();
    private final ArrayList graphAnalysedTimeMethods = new ArrayList();

    private final ArrayList analysedNMethods = new ArrayList();
    private final ArrayList<String> logFiles = new ArrayList();

    Analyser(File[] files) throws IOException {
        anomalies.add(anomaliesType);
        anomalies.add(anomaliesMethod);
        anomalies.add(anomaliesTime);
        analysedTime.add(analysedTimeMethods);
        graphAnalysedTime.add(graphAnalysedTimeMethods);
        logFiles.add("Methods");
        boolean firstItr = true;

        for (File file : files) {
            ArrayList parsedLog = Parser.parse(file);

            ArrayList<String> parsedTime = (ArrayList<String>) parsedLog.get(0);
            ArrayList parsedText = (ArrayList) parsedLog.get(1);
            this.logText.add(parsedText);
            ArrayList<Integer> parsedSequence = (ArrayList) parsedLog.get(2);
            this.logSequence.add(parsedSequence);
            Integer nMethods = parsedSequence.size() / 2;

            analyseTime(nMethods, parsedTime, parsedText, parsedSequence);
            analyseNMethods(parsedText, parsedSequence, analysedTimeMethods, firstItr);
            analyseCodeFlow(parsedText, parsedSequence);
            logFiles.add(file.getName());
            if (!firstItr) {
                findAnomalies(parsedSequence, parsedTime);
            }
            firstItr = false;
        }
    }

    /**
     * Analysis for time taken by every method of every log.
     *
     * @param nMethods number of methods exist in the log
     * @param parsedTime time of each methods at entry and exit parsed by the
     * parser
     * @param parsedText message of each event and methods in the log
     * @param parsedSequence sequence of methods donated by unique numbers
     * generated by parser
     * {@link com.github.ibmhackchallenge.methodtraceanalyser.parser}
     */
    private void analyseTime(Integer nMethods, ArrayList<String> parsedTime, ArrayList parsedText, ArrayList parsedSequence) {

        ArrayList results = new ArrayList();

        for (int iteration = 1; iteration <= nMethods; iteration++) {
            int firstIndex = Tools.find(parsedSequence, 0, iteration);
            int secondIndex = Tools.find(parsedSequence, firstIndex + 1, iteration);

            long timeTaken = ChronoUnit.MILLIS.between(LocalTime.parse(parsedTime.get(firstIndex)), LocalTime.parse(parsedTime.get(secondIndex)));

            if (analysedBufferMethods.contains((Integer) parsedSequence.get(firstIndex)) && analysedTimeMethods.contains((String) parsedText.get(firstIndex))) {
                int methodOccur = Tools.find(analysedBufferMethods, analysedTimeMethods, 0, (Integer) parsedSequence.get(firstIndex), (String) parsedText.get(firstIndex));
                results.add(methodOccur, timeTaken);
            } else {
                if (graphAnalysedTimeMethods.contains((String) parsedText.get(firstIndex))) {
                    analysedTimeMethods.add(parsedText.get(firstIndex));
                    graphAnalysedTimeMethods.add(parsedText.get(firstIndex) + " #" + iteration);
                } else {
                    analysedTimeMethods.add(parsedText.get(firstIndex));
                    graphAnalysedTimeMethods.add(parsedText.get(firstIndex));
                }
                analysedBufferMethods.add((Integer) parsedSequence.get(firstIndex));

                results.add(timeTaken);
            }
        }
        analysedTime.add(results);
        graphAnalysedTime.add(results);
    }

    /**
     * Returns the results of time taken by methods
     * {@link com.github.ibmhackchallenge.methodtraceanalyser.analyser#analyseTime(ArrayList parsedLog)}
     * which is stored in a private variable.
     *
     * @return ArrayList analysedTime
     */
    public ArrayList getAnalysedTime() {
        return analysedTime;
    }

    /**
     *
     * @return
     */
    public ArrayList getGraphAnalysedTime() {
        return graphAnalysedTime;
    }

    /**
     * Analysis number of times methods are executed.
     *
     * @param parsedText message of each event and methods in the log parsed by
     * parser
     * @param parsedSequence sequence of methods donated by unique numbers
     * generated by parser
     * {@link com.github.ibmhackchallenge.methodtraceanalyser.parser}
     */
    private void analyseNMethods(ArrayList parsedText, ArrayList parsedSequence, ArrayList analysedTimeMethods, boolean firstItr) {
        ArrayList results = new ArrayList();
        ArrayList<String> methods = (firstItr) ? Tools.removeDuplicates(analysedTimeMethods) : (ArrayList<String>) analysedNMethods.get(0);

        if (firstItr) {
            analysedNMethods.add(methods);
        }

        for (int iteration = 1; iteration <= methods.size(); iteration++) {
            int timeIndex = Tools.find(parsedSequence, 0, iteration);
            results.add(Tools.find(methods, 0, (String) parsedText.get(timeIndex)), Tools.count(parsedText, (String) parsedText.get(timeIndex)));
        }

        analysedNMethods.add(results);
    }

    /**
     * getanalysedNMethods returns the results of analyseNMethods
     * {@link com.github.ibmhackchallenge.methodtraceanalyser.analyser#analyseNMethods(ArrayList parsedText, ArrayList parsedSequence)}
     *
     * @return ArrayList analysedNMethods
     */
    public ArrayList getanalysedNMethods() {
        return analysedNMethods;
    }

    /**
     * returns list of log file names.
     *
     * @return ArrayList of Strings
     */
    public ArrayList<String> getLogFiles() {
        return logFiles;
    }

    public ArrayList<Integer> getLogSequence() {
        return logSequence;
    }

    public ArrayList getLogText() {
        return logText;
    }

    /**
     * Testing purpose only
     */
    private void analyseCodeFlow(ArrayList parsedText, ArrayList<Integer> parsedSequence) throws IOException {
        //CodeFlow frame = new CodeFlow(parsedText, parsedSequence);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(600, 520);
        //frame.setVisible(true);

    }

    /**
     * Anomalies
     */
    private void findAnomalies(ArrayList parsedSequence, ArrayList parsedTime) {
        String[] templates = new String[2];
        templates[0] = "Method Time Variation";
        templates[1] = "Method Missing Variation";

        // Methods Variation
        int methodMissingAnomaly = 0;
        Object missingMethod = "";
        Object missingAt = "";

        Object[][] method = Tools.toArray(logText, ((ArrayList) logText.get(0)).size());
        System.out.println(Arrays.deepToString(method));
        for (Object[] sub : method) {
            //System.out.println(Tools.anomaly(sub, 0));
            if (Tools.anomaly(sub, 0) != 0) {
                methodMissingAnomaly = Tools.anomaly(sub, 0);
                missingMethod = sub[methodMissingAnomaly];
                missingAt = parsedTime.get(methodMissingAnomaly);
            }
        }

        if (methodMissingAnomaly != 0) {
            anomaliesType.add(templates[1]);
            anomaliesMethod.add(missingMethod);
            anomaliesTime.add(missingAt);
        }

        // method time variation
        Object[][] time = Tools.toArray(getAnalysedTime(), ((ArrayList) getAnalysedTime().get(0)).size());
        int TimeAnomaly = 0;
        Object timeMethod = "";
        Object timeTook = "";

        for (Object[] sub : time) {
            if (Tools.anomaly(sub, 1) != 0) {
                TimeAnomaly = Tools.anomaly(sub, 1);
                timeMethod = sub[0];
                timeTook = sub[TimeAnomaly];
            }
        }

        if (TimeAnomaly != 0 && methodMissingAnomaly == 0) {
            anomaliesType.add(templates[0]);
            anomaliesMethod.add(timeMethod);
            anomaliesTime.add(timeTook);
        }

    }

    public ArrayList getAnomalies() {
        return anomalies;
    }
}
