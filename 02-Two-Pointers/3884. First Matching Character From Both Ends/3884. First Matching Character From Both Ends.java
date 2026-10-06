1class Solution {
2    public int firstMatchingIndex(String s) {
3              int left=0;
4              int right=s.length()-1;
5              while(left<=right){
6                if(s.charAt(left)==s.charAt(right)){
7                    return left;
8                }
9                left++;
10                right--;
11              }
12     return -1;   
13    }
14}