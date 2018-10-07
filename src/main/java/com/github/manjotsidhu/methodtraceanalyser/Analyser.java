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
package com.github.manjotsidhu.methodtraceanalyser;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The heart of IBM Method Trace Analyser which analyses method time invocation,
 * number of methods executed, graph for one or more log files.
 *
 * @author Manjot Sidhu
 */
public class Analyser {

    private final ArrayList logSequence = new ArrayList();
    private final ArrayList logText = new ArrayList();

    private final ArrayList analysedTime = new ArrayList();
    private final ArrayList analysedTimeMethods = new ArrayList();

    private final ArrayList analysedNMethods = new ArrayList();
    private final ArrayList<String> logFiles = new ArrayList();

    private final ArrayList analysedJST = new ArrayList();

    Analyser(File[] files) throws IOException {
        analysedTime.add(analysedTimeMethods);
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
            ArrayList<String> parsedJST = (ArrayList<String>) parsedLog.get(4);

            analyseTime(nMethods, parsedTime, parsedText, parsedSequence);
            analyseNMethods(parsedText, parsedSequence, firstItr, nMethods);
            analyseJStackTrace(parsedJST, parsedSequence, parsedText, nMethods, firstItr);
            logFiles.add(file.getName());
            firstItr = false;
        }
        System.out.println(Arrays.deepToString(getAnalysedJST().toArray()));
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
            // if secondIndex is not found then surely method is not out.
            long timeTaken = 0;
            try {
                timeTaken = ChronoUnit.MILLIS.between(LocalTime.parse(parsedTime.get(firstIndex)), LocalTime.parse(parsedTime.get(secondIndex)));
            } catch (Exception e) {
                System.out.println("failed");
            }
            if (analysedTimeMethods.contains((String) parsedText.get(firstIndex))) {
                int methodOccur = Tools.find(analysedTimeMethods, 0, (String) parsedText.get(firstIndex));

                Tools.extendArrayIndex(results, methodOccur);
                results.set(methodOccur, timeTaken);
            } else {
                analysedTimeMethods.add((String) parsedText.get(firstIndex));
                results.add(timeTaken);
            }
        }
        analysedTime.add(results);
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
    private void analyseNMethods(ArrayList parsedText, ArrayList parsedSequence, boolean firstItr, int nMethods) {
        ArrayList results = new ArrayList();
        ArrayList<String> methodNames = analysedTimeMethods;

        if (firstItr) {
            analysedNMethods.add(methodNames);
        }

        for (int method = 1; method <= nMethods; method++) {
            int firstIndex = Tools.find(parsedSequence, 0, method);
            int count = Tools.count(parsedSequence, method) / 2;
            String methodName =  (String) parsedText.get(firstIndex);
            
            if(methodNames.contains(methodName)) {
                int methodIndex = Tools.find(methodNames, 0, (String) parsedText.get(firstIndex));
                results.add(methodIndex, count);
            } else {
                methodNames.add(methodName);
                results.add(count);
            }
            
        }

        analysedNMethods.add(results);
    }

    /**
     * 
     */
    public void analyseJStackTrace(ArrayList<String> parsedJST, ArrayList parsedSequence, ArrayList parsedText, int nMethods, boolean firstItr) {
        ArrayList results = new ArrayList();
        ArrayList<String> methodNames = analysedTimeMethods;
        
        if(firstItr) {
            analysedJST.add(methodNames);
        }
        
        for(int method = 1; method <= nMethods; method++) {
            int index = Tools.find(parsedSequence, 0, method);
            String stackTrace = (String) parsedJST.get(index);
            
            if(methodNames.contains((String) parsedText.get(index))) {
                int methodIndex = Tools.find(methodNames, 0, (String) parsedText.get(index));
                Tools.extendArrayIndex(results, methodIndex);
                results.set(methodIndex, stackTrace);
            } else {
                methodNames.add((String) parsedText.get(index));
                results.add(stackTrace);
            }
        }
        
        analysedJST.add(results);
    }

    /**
     * Returns the results of time taken by methods which is stored in a private
     * variable.
     *
     * @return ArrayList analysedTime
     */
    public ArrayList getAnalysedTime() {
        return analysedTime;
    }

    /**
     * getanalysedNMethods returns the results of analyseNMethods
     *
     * @return ArrayList analysedNMethods
     */
    public ArrayList getanalysedNMethods() {
        return analysedNMethods;
    }

    /**
     * Returns list of names of inputted log files.
     *
     * @return 1D ArrayList of only the file name of log files.
     */
    public ArrayList<String> getLogFiles() {
        return logFiles;
    }

    /**
     * Fetches parsedSequence from parser.
     *
     * @return 1D ArrayList of sequence of methods denoted by unique numbers.
     */
    public ArrayList<Integer> getLogSequence() {
        return logSequence;
    }

    /**
     * Fetches parsedLogText from parser (parsedLog).
     *
     * @return 1D ArrayList of all texts given in log file
     */
    public ArrayList getLogText() {
        return logText;
    }
    
    /**
     * 
     */
    public ArrayList getAnalysedJST() {
        return analysedJST;
    }
}
