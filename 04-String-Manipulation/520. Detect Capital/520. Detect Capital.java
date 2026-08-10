1class Solution {
2    public boolean detectCapitalUse(String word) {
3        int n =word.length();
4        int uppercase=0;
5        for(int i =0;i<n;i++){
6            if(Character.isUpperCase(word.charAt(i))){
7                uppercase++;
8            }
9        }
10        return uppercase==n || uppercase==0 || (uppercase==1 && Character.isUpperCase(word.charAt(0)));    
11    }
12}