1class Solution {
2    public int maxNumberOfBalloons(String text) {
3        int[] freq=new int[26];
4        for(char ch:text.toCharArray()){
5            freq[ch -'a']++;
6        }
7        return Math.min(
8        Math.min(
9        Math.min(freq['b'-'a'], freq['a'-'a']),
10        Math.min(freq['l'-'a']/2, freq['o'-'a']/2)
11        ),
12        freq['n'-'a']
13        );       
14        
15    }
16}