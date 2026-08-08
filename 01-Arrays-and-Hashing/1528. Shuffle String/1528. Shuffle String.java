1class Solution {
2    public String restoreString(String s, int[] indices) {
3        
4        int length=s.length();
5        StringBuilder sb=new StringBuilder();
6         char c[]=new char[length];
7       
8        for(int i=0;i<length;i++){
9
10            c[indices[i]]=s.charAt(i);
11
12        }
13        sb.append(c);
14        return sb.toString();
15    }
16}