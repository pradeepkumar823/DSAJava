1class Solution {
2    public String removeStars(String s) {
3        StringBuilder sb=new StringBuilder();
4
5        for(char ch: s.toCharArray()){
6            if(ch !='*'){
7                sb.append(ch);
8            }
9            else if(sb.length()>0){
10                    sb.deleteCharAt(sb.length()-1);
11                }
12            }
13        
14        return sb.toString();
15    }
16}
17
18