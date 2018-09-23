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
import java.util.regex.*;
import org.apache.commons.io.FileUtils;

public class parser {

    /**
     * Parses the given log file and returns a ArrayList containing all
     * necessary data for analyser to compare logs.
     *
     * @param logFile input log belonging to File Object
     * @return ArrayList of parsed log
     * @throws IOException if log file given doesn't exists.
     */
    public static ArrayList parse(File logFile) throws IOException {
        ArrayList parsedLog = new ArrayList();

        ArrayList methodTimeline = new ArrayList();
        ArrayList methodTime = new ArrayList();
        //ArrayList execEnv = new ArrayList();
        //ArrayList traceApp = new ArrayList();
        ArrayList methodText = new ArrayList();
        ArrayList methodSequence = new ArrayList();

        parsedLog.add(methodTimeline);
        parsedLog.add(methodTime);
        //parsedLog.add(execEnv);
        //parsedLog.add(traceApp);
        parsedLog.add(methodText);
        parsedLog.add(methodSequence);

        System.out.println("Parsing file: " + logFile.getAbsolutePath());
        String log = FileUtils.readFileToString(logFile);

        Pattern pattern1 = Pattern.compile("(\\d+:\\d+:\\d+.\\d+)([*|\\s])(\\w+)(\\s{6})(\\w+)(\\.)(\\d+)(\\s{6}[*\\s]\\s+[<>*-]\\s)(.+)");
        Matcher matcher1 = pattern1.matcher(log);

        int methodId = 0;
        ArrayList<Integer> IdBuffer = new ArrayList<>();

        while (matcher1.find()) {
            methodTimeline.add(matcher1.group(1));
            
            //methodTime.add(methodTimeline.get(tools.find(methodTimeline, methodId, methodId)));
            
            //execEnv.add(matcher1.group(3));
            //traceApp.add(matcher1.group(5));
            methodText.add(matcher1.group(9));

            switch (matcher1.group(7)) {
                case "0":
                    methodId++;
                    IdBuffer.add(methodId);
                    methodSequence.add(methodId);
                    System.out.println("Found method, " + matcher1.group(9) +
                            " assigning method id: " + methodId);
                    break;
                case "1":
                    methodSequence.add(IdBuffer.get(IdBuffer.size() - 1));
                    IdBuffer.remove(IdBuffer.size() - 1);
                    break;
                default:
                    methodSequence.add("null");
                    break;
            }

        }
        System.out.println("Methods found " + (int) (methodId));
        return parsedLog;
    }

    public static void main(String[] args) throws IOException {
        File in = new File("sample_logs/input.log");
        System.out.println(Arrays.deepToString(parse(in).toArray()));
        
        LocalTime one = LocalTime.parse("03:35:59.196");
        LocalTime two = LocalTime.parse("03:35:59.197");
        System.out.println("03:35:59.196");
        System.out.println(one.getHour()+" " + one.getMinute() + " " + one.getSecond() + " " + one.getNano());
        
        Long sub = ChronoUnit..between(one,two);
        System.out.println(sub);
    }
}
