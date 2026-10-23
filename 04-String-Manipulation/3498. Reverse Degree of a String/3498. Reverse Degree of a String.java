1class Solution {
2    public int reverseDegree(String s) {
3        int totalSum=0;
4        for(int i=0;i<s.length();i++){
5            totalSum+=(26-(s.charAt(i)-'a'))*(i+1);
6           
7
8        }
9        return totalSum;
10    }
11}