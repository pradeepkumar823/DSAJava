1class Solution {
2    public int[] shortestToChar(String s, char c) {
3        int[] ans = new int[s.length()];
4        List<Integer> ls =new ArrayList<>();
5        for(int i=0;i<s.length();i++){
6            if(s.charAt(i)==c){
7                ls.add(i);
8            }
9        }
10       
11        for(int i=0;i<s.length();i++){
12            int minn=Integer.MAX_VALUE;
13            for(int j=0;j<ls.size();j++){
14                minn=Math.min(minn,Math.abs(i-ls.get(j)));
15            }
16            ans[i]=minn;
17        }
18        return ans;
19        
20    }
21}