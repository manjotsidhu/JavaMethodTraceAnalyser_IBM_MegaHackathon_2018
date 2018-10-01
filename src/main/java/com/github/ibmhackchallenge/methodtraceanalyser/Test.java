/*
 * Copyright 2018 Sidhu.
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
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        File[] in = new File[2];
        in[0] = new File("sample_logs/logfile1a");
        //in[1] = new File("sample_logs/logfile1b");
        in[1] = new File("sample_logs/logfile1c");
        
        //Parser parse = new Parser();
        //System.out.println(Arrays.deepToString(parse.parse(in[2]).toArray()));
        
        ArrayList a = new ArrayList();
        ArrayList a1 = new ArrayList();
        ArrayList a2 = new ArrayList();
        a.add(a1);
        a.add(a2);
        a1.add(1);
        a1.add(2);
        a2.add(3);
        a2.add(4);
        
        //System.out.println(Arrays.deepToString(a.toArray()));
        

        Analyser an = new Analyser(in);
        System.out.println(Arrays.deepToString(an.getAnomalies().toArray()));
        //Object[][] r = Tools.toArray(an.getAnalysedTime(), ((ArrayList) an.getAnalysedTime().get(0)).size());
        
        //System.out.println(Arrays.deepToString(r));
        //System.out.println(Arrays.deepToString(an.getAnalysedTime().toArray()));
        //System.out.println(Arrays.deepToString(an.getLogFiles().toArray()));
        //System.out.println(Arrays.deepToString(an.getAnalysedTime());
    }
}
