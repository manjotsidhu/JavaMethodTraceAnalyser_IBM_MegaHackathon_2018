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

import java.util.ArrayList;

public class tools {
    
    public static int find(ArrayList<Integer> arr, int startLength, int number) {
        for(int iteration = startLength; iteration < arr.size(); iteration++) {
            if(arr.get(iteration) == number) {
                return iteration;
            }
        }
        return 0;
    }
    
    public static int find(ArrayList arr, int startLength, String number) {
        for(int iteration = startLength; iteration < arr.size(); iteration++) {
            if(arr.get(iteration).equals(number)) {
                return iteration;
            }
        }
        return 0;
    }
    
    public static int find(Object[] arr, int startLength, String number) {
        for(int iteration = startLength; iteration < arr.length; iteration++) {
            if(arr[iteration].equals(number)) {
                return iteration;
            }
        }
        return 0;
    }
}
