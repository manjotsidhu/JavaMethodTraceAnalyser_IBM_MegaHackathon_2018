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
     * necessary data for analyser to compare logs.
     *
     * @param logFile input log belonging to File Object 
     * @return ArrayList of parsed log
     * @throws IOException if log file given doesn't exists.
     */
    public static ArrayList parse(File logFile) throws IOException {
        ArrayList parsedLog = new ArrayList();

        ArrayList timeline = new ArrayList();
        ArrayList execEnv = new ArrayList();
        ArrayList traceApp = new ArrayList();
        ArrayList traceId = new ArrayList();
        ArrayList traceText = new ArrayList();

        parsedLog.add(timeline);
        parsedLog.add(execEnv);
        parsedLog.add(traceApp);
        parsedLog.add(traceId);
        parsedLog.add(traceText);

        String log = FileUtils.readFileToString(logFile);

        Pattern pattern1 = Pattern.compile("(\\d+:\\d+:\\d+.\\d+)([*|\\s])(\\w+)(\\s{6})(\\w+)(\\.)(\\d+)((\\s{9})(-|<|>)|(\\s{6}\\*\\s{2}\\*))(\\s)(.+)");
        Matcher matcher1 = pattern1.matcher(log);

        while (matcher1.find()) {
            timeline.add(matcher1.group(1));
            execEnv.add(matcher1.group(3));
            traceApp.add(matcher1.group(5));
            traceId.add(matcher1.group(7));
            traceText.add(matcher1.group(13));
        }

        return parsedLog;
    }
}
