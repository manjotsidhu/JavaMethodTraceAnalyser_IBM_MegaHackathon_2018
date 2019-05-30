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

//package com.github.ibmhackchallenge.methodtraceanalyser;

import com.ibm.jvm.Trace;

/**
 * ProjLog1_a for generating IBM trace log for testing Analyser.
 * 
 * Commands to compile and get log trace :-
 * $ javac ProjLog1_b.java
 * $ java -Xtrace:none -Xtrace:maximal=ProjLog1,output=ProjLog1_d.trc ProjLog1_d
 * $ java com.ibm.jvm.TraceFormat ProjLog1_d.trc ProjLog1_d.fmt
 * 
 * @author Manjot Sidhu
 */
 public class ProjLog1_d {

    static int handle;
    static String[] templates;

    public static void main(String[] args) {
        templates = new String[5];
        templates[0] = Trace.ENTRY + "%s"; // method in
        templates[1] = Trace.EXIT + "%s"; // method out
        templates[2] = Trace.EVENT + "Event id %d, text = %s";
        templates[3] = Trace.EXCEPTION + "Exception: %s";
        templates[4] = Trace.EXCEPTION_EXIT + "Exception exit from %s method";

        // Register a trace application called ProjLog1
        handle = Trace.registerApplication("ProjLog1", templates);

        // Set any tracepoints that are requested on the command line
        for (int i = 0; i < args.length; i++) {
            System.err.println("Trace setting: " + args[i]);
            Trace.set(args[i]);
        }

        // Trace something....
        Trace.trace(handle, 2, 1, "Trace initialized");

        // Call a few methods...
        method1();
        method2();
        method3();
        method4();
        method5();
    }

    private static void method1() {
        Trace.trace(handle, 0, "method1");
        System.out.println("Message from method1");
        
        try {
            Thread.sleep(90);
        } catch (InterruptedException ex) {
            // nothing
        }
        Trace.trace(handle, 1, "method1");
    }

    private static void method2() {
        Trace.trace(handle, 0, "method2");
        System.out.println("Message from method2");
        try {
            Thread.sleep(45);
        } catch (InterruptedException ex) {
            // nothing
        }
        Trace.trace(handle, 1, "method2");
    }

    private static void method3() {
        Trace.trace(handle, 0, "method3");
        System.out.println("Message from method3");

        // Example if method3 takes more time.
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            // nothing
        }
        Trace.trace(handle, 1, "method3");
    }

    private static void method4() {
        Trace.trace(handle, 0, "method4");
        System.out.println("Message from method4");
        try {
            Thread.sleep(72);
        } catch (InterruptedException ex) {
            // nothing
        }
        Trace.trace(handle, 1, "method4");
    }

    private static void method5() {
        Trace.trace(handle, 0, "method5");
        System.out.println("Message from method5");
        method2();
        method4();

        // Example if method3 takes more time.
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
            // nothing
        }

        Trace.trace(handle, 1, "method5");
    }
}
