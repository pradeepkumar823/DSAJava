1class Solution {
2    public int findMaxK(int[] nums) {
3        HashSet<Integer> hs = new HashSet<>();
4        int ans = -1;
5        for(int num : nums){
6            hs.add(num);
7            int k = num*(-1);
8            if(hs.contains(k)){
9                ans = Math.max(ans,Math.abs(num));
10            }
11        }
12        return ans;
13    }
14}