1class Solution {
2    public String[] uncommonFromSentences(String s1, String s2) {
3        
4        //combine both string in s
5     String s = s1+ +s2;
6     //create map for count the string
7        HashMap<String,Integer>hm =new HashMap<>();
8
9        String[] words=s.split( );
10
11        //loop for check and count the string and add in the (hm)
12        for(String word:words){
13            hm.put(word,hm.getOrDefault(word,0)+1);
14        }
15
16        //create list for the result :- here add string which count or value ==1
17        List<String> result=new ArrayList<>();
18
19
20        for(Map.Entry<String,Integer> entry:hm.entrySet()){
21            if(entry.getValue()==1){
22                result.add(entry.getKey());
23            }
24        }
25        return result.toArray(new String[0]);
26    }
27}