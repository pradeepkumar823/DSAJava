1class Solution {
2    public int prefixCount(String[] words, String pref) {
3        int count=0;
4        
5         
6         for(int i =0;i<words.length;i++){
7            if(words[i].startsWith(pref)){
8                count++;
9            }
10         }
11         return count;
12    }
13}