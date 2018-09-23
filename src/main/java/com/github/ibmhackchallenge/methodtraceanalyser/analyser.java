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

public class analyser {

    ArrayList analysedTime = new ArrayList();
    ArrayList analysedTimeMethods = new ArrayList();
    ArrayList analysedTimeDiff = new ArrayList();

    analyser(ArrayList<String> files) throws IOException {
        analysedTime.add(analysedTimeMethods);
        analysedTime.add(analysedTimeDiff);

        for (String file : files) {
            ArrayList parsedLog = parser.parse(new File(file));
            analyseTime(parsedLog);
        }
    }

    public void analyseTime(ArrayList parsedLog) {
        ArrayList parsedTime = (ArrayList) parsedLog.get(0);
        ArrayList parsedText = (ArrayList) parsedLog.get(0);
        ArrayList parsedSequence = (ArrayList) parsedLog.get(0);
        Integer nMethods = parsedSequence.size() / 2;

        for (int iteration = 1; iteration <= nMethods; iteration++) {
            int firstIndex = tools.find(parsedSequence, 0, iteration);
            int secondIndex = tools.find(parsedSequence, firstIndex, iteration);

            long timeTaken = ChronoUnit.MILLIS.between(LocalTime.parse((String) (parsedTime.get(secondIndex))), LocalTime.parse((String) (parsedTime.get(firstIndex))));
            Integer methodOccur = tools.find(analysedTimeMethods, 0, (String) parsedText.get(firstIndex));
            
            if(methodOccur == 0) {
                analysedTimeMethods.add(parsedText.get(firstIndex));
            } else {
                
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File in = new File("sample_logs/input.log");
        ArrayList my = parser.parse(in);
        System.out.println(Arrays.deepToString(my.toArray()));

        //System.out.println(((ArrayList) my.get(0)).get(0).toString());
        LocalTime t1 = LocalTime.parse("03:35:59.195");
        LocalTime t2 = LocalTime.parse("03:36:59.196");

        long minutesBetween = ChronoUnit.MINUTES.between(t1, t2);
        long hoursBetween = ChronoUnit.HOURS.between(t1, t2);
        long secsBetween = ChronoUnit.SECONDS.between(t1, t2);
        long msecsBetween = ChronoUnit.MILLIS.between(t1, t2);
        long nsecsBetween = ChronoUnit.NANOS.between(t1, t2);
        System.out.println(minutesBetween + "\n" + hoursBetween + "\n" + secsBetween + "\n" + msecsBetween + "\n" + nsecsBetween + "\n");
    }
}
