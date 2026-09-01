1class Solution {
2    public String mergeAlternately(String word1, String word2) {
3        // create a string builder for storing a string
4        // int i =0;
5        // start loop for word1 if not empty and append in builder 
6        // followed by word2 
7        // like one from word1 and second from word2 and countinue
8        // if any of the word1,2 will empty then direct append the remain string char in the stringbuilder
9
10        StringBuilder sb =new StringBuilder();
11        int i =0;
12        while(i< word1.length() || i<word2.length()){
13            if(i<word1.length()){
14                sb.append(word1.charAt(i));
15            }
16            if(i<word2.length()){
17                sb.append(word2.charAt(i));
18            }
19            i++;
20
21        }
22        return sb.toString();
23    }
24}