1class Solution {
2    public boolean backspaceCompare(String s, String t) {
3        StringBuilder sb1=new StringBuilder();
4        StringBuilder sb2=new StringBuilder();
5         
6         for(char ch:s.toCharArray()){
7            if(Character.isLetter(ch)){
8                sb1.append(ch);              
9            }
10            else{
11                if(sb1.length()>0){
12                    sb1.deleteCharAt(sb1.length()-1);
13                }
14            }
15         }
16         for(char ch:t.toCharArray()){
17            if(Character.isLetter(ch)){
18                sb2.append(ch);              
19            }
20            else{
21                if(sb2.length()>0){
22                    sb2.deleteCharAt(sb2.length()-1);
23                }
24            }
25         }
26
27    return sb1.toString().equals(sb2.toString());
28    }
29}