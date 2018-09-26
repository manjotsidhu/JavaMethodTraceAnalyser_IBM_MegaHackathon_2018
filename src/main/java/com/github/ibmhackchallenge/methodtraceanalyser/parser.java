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
import java.util.ArrayList;
import java.util.regex.*;
import org.apache.commons.io.FileUtils;

public class parser {

    /**
     * Parses the given log file and returns a ArrayList containing all
     * necessary data for analser to compare logs.
     *
     * @param logFile input log belonging to File Object
     * @return ArrayList of parsed log
     * @throws IOException if log file given doesn't exists.
     */
    public static ArrayList parse(File logFile) throws IOException {
        ArrayList parsedLog = new ArrayList();

        ArrayList<String> methodTime = new ArrayList<>();
        ArrayList<String> methodText = new ArrayList<>();
        ArrayList<Integer> methodSequence = new ArrayList<>();

        parsedLog.add(methodTime);
        parsedLog.add(methodText);
        parsedLog.add(methodSequence);

        System.out.println("Parsing file: " + logFile.getAbsolutePath());
        String log = FileUtils.readFileToString(logFile);

        Pattern pattern1 = Pattern.compile("(\\d+:\\d+:\\d+.\\d+)([*|\\s])(\\w+)(\\s{6})(\\w+)(\\.)(\\d+)(\\s{6}[*\\s]\\s+[<>*-]\\s)(.+)");
        Matcher matcher1 = pattern1.matcher(log);

        int methodId = 0;
        ArrayList<Integer> BufferId = new ArrayList<>();

        while (matcher1.find()) {
            methodTime.add(matcher1.group(1));
            methodText.add(matcher1.group(9));

            switch (matcher1.group(7)) {
                case "0":
                    methodId++;
                    BufferId.add(methodId);
                    methodSequence.add(methodId);
                    System.out.println("Found method, " + matcher1.group(9)
                            + " assigning method id: " + methodId);
                    break;
                case "1":
                    methodSequence.add(BufferId.get(BufferId.size() - 1));
                    BufferId.remove(BufferId.size() - 1);
                    break;
                //default:
                //    methodSequence.add(0);
                //    break;
            }

        }
        System.out.println("Methods found " + (int) (methodId));
        return parsedLog;
    }
}
