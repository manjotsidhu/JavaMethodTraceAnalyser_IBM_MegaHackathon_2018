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
import java.util.Arrays;

public class Tools {

    /**
     * Finds the specified <code>number</code> in an <code>arr</code> starting
     * from <code>startIndex</code> and returns the index of the array.
     *
     * @param arr ArrayList< Integer > input array
     * @param startIndex Integer starting index from where to start the search.
     * @param number Integer element to find in <code>arr</code>.
     * @return index of the array, if nothing found then returns 0.
     */
    public static int find(ArrayList<Integer> arr, int startIndex, int number) {
        for (int iteration = startIndex; iteration < arr.size(); iteration++) {
            if (arr.get(iteration) == number) {
                return iteration;
            }
        }
        return 0;
    }

    /**
     * Finds the specified <code>number</code> in an <code>arr</code> starting
     * from <code>startIndex</code> and returns the index of the array.
     *
     * @param arr ArrayList input array
     * @param startIndex Integer starting index from where to start the search.
     * @param number String element to find in <code>arr</code>.
     * @return index of the array, if nothing found then returns 0.
     */
    public static int find(ArrayList arr, int startIndex, String number) {
        for (int iteration = startIndex; iteration < arr.size(); iteration++) {
            if (arr.get(iteration).equals(number)) {
                return iteration;
            }
        }
        return 0;
    }

    /**
     * Finds the specified <code>number</code> in an <code>arr</code> starting
     * from <code>startIndex</code> and returns the index of the array.
     *
     * @param arr Object[] input array
     * @param startIndex Integer starting index from where to start the search.
     * @param number String element to find in <code>arr</code>.
     * @return index of the array, if nothing found then returns 0.
     */
    public static int find(Object[] arr, int startIndex, String number) {
        for (int iteration = startIndex; iteration < arr.length; iteration++) {
            if (arr[iteration].equals(number)) {
                return iteration;
            }
        }
        return 0;
    }

    public static int find(ArrayList<Integer> arr1, ArrayList<String> arr2, int startIndex, Integer number, String str) {
        for (int iteration = startIndex; iteration < arr1.size(); iteration++) {
            if ((arr1.get(iteration) == number) && (arr2.get(iteration) == str)) {
                return iteration;
            }
        }
        return 0;
    }

    /**
     * Counts the number of <code>element</code> in the given <code>arr</code>.
     *
     * @param arr ArrayList< String > input array in which to count.
     * @param element String element to count.
     * @return number of elements exist in the array.
     */
    public static int count(ArrayList<String> arr, String element) {
        int count = 0;
        for (int iteration = 0; iteration < arr.size(); iteration++) {
            if (arr.get(iteration).equals(element)) {
                count++;
            }
        }
        return count;
    }

    /**
     * removeDuplicates simply removes duplicate elements from the given array
     * <code>arr</code> and returns a new array containing all unique elemnts.
     *
     * @param arr ArrayList< String > input array.
     * @return new array having unique elements.
     */
    public static ArrayList removeDuplicates(ArrayList<String> arr) {
        String[] tempArr = new String[arr.size()];
        ArrayList<String> newArr = new ArrayList<>();

        arr.toArray(tempArr);
        tempArr = Arrays.stream(tempArr).distinct().toArray(String[]::new);

        newArr.addAll(Arrays.asList(tempArr));
        return newArr;
    }
        
    /**
     *
     */
    public static Object[][] toArray(ArrayList arr, int y) {
        Object[][] newArr = new Object[y][arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < ((ArrayList) arr.get(i)).size(); j++) {
                newArr[j][i] = ((ArrayList) arr.get(i)).get(j);
            }
        }
        return newArr;
    }

    /**
     *
     */
    public static int anomaly(Object[] arr, int startIndex) {

        int flag = 0;

        for (int i = startIndex; i < arr.length; i++) {
            for (int j = startIndex; j < arr.length; j++) {
                if (i != j) {
                    if (arr[i] != arr[j]) {
                        flag = 1;
                    } else {
                        flag = 0;
                        break;
                    }
                }
            }
            if (flag == 1) {
                return i;
            }
        }

        return 0;
    }
    
    /**
     * 
     */
    public static void extendArrayLength(ArrayList arr, int length) {
        while(arr.size() < length ) {
            arr.add(null);
        }
    }
    
    public static void extendArrayIndex(ArrayList arr, int index) {
        while(arr.size() < index+1) {
            arr.add(null);
        }
    }
}
