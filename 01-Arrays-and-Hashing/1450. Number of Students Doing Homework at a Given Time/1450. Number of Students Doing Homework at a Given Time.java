1class Solution {
2        public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
3          int count = 0;
4          for (int i = 0; i < startTime.length; i++) {
5             if (startTime[i] <= queryTime && queryTime <= endTime[i]) {
6                   count++;
7             }
8             }
9             return count;    
10        }                                               
11}