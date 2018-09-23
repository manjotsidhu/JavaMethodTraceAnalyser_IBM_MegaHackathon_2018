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

import java.time.LocalTime;

public class test {
    public static void main(String[] args) {
        String time = "03:35:59.195";
        
        LocalTime t = LocalTime.parse(time);
        
        System.out.println(t.getHour() + ":" + t.getMinute() + ":" + t.getSecond() + "." + (int)(t.getNano()/1000000));
    }
}
