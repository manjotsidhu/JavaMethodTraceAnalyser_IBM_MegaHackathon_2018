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
 * @author manjotsidhu
 */
public class analyser {

    ArrayList analysedTime = new ArrayList();
    ArrayList analysedTimeMethods = new ArrayList();

    analyser(ArrayList<String> files) throws IOException {
        analysedTime.add(analysedTimeMethods);

        for (String file : files) {
            ArrayList parsedLog = parser.parse(new File(file));
            analyseTime(parsedLog);
        }
    }

    /**
     * Analysis for time taken by every method of every log.
     * 
     * @param parsedLog Log returned by the parser class {@link com.github.ibmhackchallenge.methodtraceanalyser.parser}
     */
    public void analyseTime(ArrayList parsedLog) {
        ArrayList<String> parsedTime = (ArrayList<String>) parsedLog.get(0);
        ArrayList parsedText = (ArrayList) parsedLog.get(1);
        ArrayList parsedSequence = (ArrayList) parsedLog.get(2);
        ArrayList results = new ArrayList();
        Integer nMethods = parsedSequence.size() / 2;

        for (int iteration = 1; iteration <= nMethods; iteration++) {
            int firstIndex = tools.find(parsedSequence, 0, iteration);
            int secondIndex = tools.find(parsedSequence, firstIndex, iteration);

            System.out.println(parsedTime.get(firstIndex) + " " + parsedTime.get(secondIndex));
            long timeTaken = ChronoUnit.MILLIS.between(LocalTime.parse(parsedTime.get(firstIndex)), LocalTime.parse(parsedTime.get(secondIndex)));
            Integer methodOccur = tools.find(analysedTimeMethods, 0, (String) parsedText.get(firstIndex));
            
            if (methodOccur == 0 | (results.size() > methodOccur && results.get(methodOccur)!= null)) {
                analysedTimeMethods.add(parsedText.get(firstIndex));
                results.add(timeTaken);
            } else {
                results.add(methodOccur, timeTaken);
            }
        }
        analysedTime.add(results);
    }
    
    /**
     * Returns the results of analysed method {@link com.github.ibmhackchallenge.methodtraceanalyser.analyser#analyseTime(ArrayList parsedLog)}
     * which is stored in a private variable.
     * @return ArrayList analysedTime
     */
    public ArrayList getAnalysedTime() {
        return analysedTime;
    }

    /**
     * For testing purpose only.
     * 
     * @param args null
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ArrayList<String> in = new ArrayList<>();
        in.add("sample_logs/input.log");
        
        analyser an = new analyser(in);
        System.out.println(Arrays.deepToString(an.getAnalysedTime().toArray()));
    }
}
