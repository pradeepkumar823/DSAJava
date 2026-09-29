1class Solution {
2    public int countPrefixes(String[] words, String s) {
3        int count=0;
4        for(int i =0;i<words.length;i++){
5            if(s.startsWith(words[i])){
6                count++;
7            }
8        }
9        return count;
10    }
11}