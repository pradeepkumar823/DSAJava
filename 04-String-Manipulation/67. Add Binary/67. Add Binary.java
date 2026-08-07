1class Solution {
2    public String addBinary(String a, String b) {
3        StringBuilder sb=new StringBuilder();
4        int carry=0;
5        int i=a.length()-1;
6        int j=b.length()-1;
7        
8        while(i>=0 || j>=0 || carry==1){
9            int sum=carry;
10            if(i>=0){
11                sum+=a.charAt(i--)-'0';
12            }
13            if(j>=0){
14                sum+=b.charAt(j--)-'0';
15            }
16            sb.append(sum%2);
17            carry=sum/2;
18        }
19            return sb.reverse().toString();
20    }
21}