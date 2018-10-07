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
import java.util.ArrayList;
import java.util.regex.*;
import org.apache.commons.io.FileUtils;

/**
 * Parses IBM formatted trace log and returns required data to be used for
 * Analyser.
 *
 * @author Manjot Sidhu
 */
public class Parser {

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

        ArrayList<Integer> eventType = new ArrayList<>();
        ArrayList<String> methodTime = new ArrayList<>();
        ArrayList<String> methodText = new ArrayList<>();
        ArrayList<Integer> methodSequence = new ArrayList<>();
        ArrayList<String> methodJStackTrace = new ArrayList<>();

        parsedLog.add(methodTime);
        parsedLog.add(methodText);
        parsedLog.add(methodSequence);
        parsedLog.add(eventType);
        parsedLog.add(methodJStackTrace);

        //System.out.println("Parsing file: " + logFile.getAbsolutePath());
        String tempLog = FileUtils.readFileToString(logFile);
        String log = (tempLog != null) ? tempLog.replaceAll("\\r", "") : tempLog;
        String logRegex = "(\\d+:\\d+:\\d+.\\d+)(\\s+)?([*|\\s])(\\w+)(\\s+)(\\w+)(\\.)(\\d+)(\\s+(\\w+\\s+)?(([^\\n\\>\\<\\]\\[]+)?)([<>])\\s?)([^\\n\\]]+)(\\n\\s+(.+))?((\\n.+)(jstacktrace:)\\n(((?!((\\d+:\\d+:\\d+.\\d+)(\\s+)?([*|\\s])(\\w+)(\\s+)(\\w+)(\\.)(\\d+)(\\s+(\\w+\\s+)?(([^\\n\\>\\<\\]\\[]+)?)([<>])\\s?)))(.+)[\\n])+))?";
        Pattern logPattern = Pattern.compile(logRegex);
        Matcher logMatcher = logPattern.matcher(log);

        int methodId = 0;
        ArrayList<Integer> BufferId = new ArrayList<>();

        while (logMatcher.find()) {
            if (findEventType(logMatcher.group(13)) == 0 || findEventType(logMatcher.group(13)) == 1) {
                methodTime.add(logMatcher.group(1));
                String additionalText = (logMatcher.group(16) != null) ? " " + logMatcher.group(16) : "";
                methodText.add(logMatcher.group(14) + additionalText);
                eventType.add(findEventType(logMatcher.group(13)));
            }

            switch (eventType.get(eventType.size() - 1)) {
                case 0:
                    methodId++;
                    BufferId.add(methodId);
                    methodSequence.add(methodId);
                    String jstacktrace = (logMatcher.group(20) != null) ? logMatcher.group(20).replaceAll("(.+)(\\[\\d+\\](.+))", "$2") : logMatcher.group(20);
                    methodJStackTrace.add(jstacktrace);
                    //System.out.println("Found method, " + methodText.get(methodText.size() - 1)
                    //        + " assigning method id: " + methodId);
                    break;

                case 1:
                    methodSequence.add(BufferId.get(BufferId.size() - 1));
                    BufferId.remove(BufferId.size() - 1);
                    break;
            }

        }
        //System.out.println("Methods found " + (int) (methodId));
        //System.out.println(methodJStackTrace.toString());
        return parsedLog;
    }

    /**
     * Finds event type based on the greater than or less than sign in log
     *
     * @param input signs of String datatype
     * @return return 0 if the method is entering and 1 if the method is exiting
     */
    public static int findEventType(String input) {
        if (input.equals(">")) {
            return 0;
        } else if (input.equals("<")) {
            return 1;
        }

        return -1;
    }
}
