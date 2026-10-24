1class Solution {
2    public String reversePrefix(String s, int k) {
3        StringBuilder sb=new StringBuilder();
4
5        for(int i =0;i<k;i++){
6            sb.append(s.charAt(i));
7        }
8        sb.reverse();
9        sb.append(s.substring(k,s.length()));
10        return sb.toString();
11        
12    }
13}