1class Solution {
2    public int maxRepeating(String sequence, String word) {
3        int count = 0;
4        String temp = word;
5      while(sequence.contains(temp)){
6        count++;
7        temp += word;
8      }
9      return count;
10    }
11}