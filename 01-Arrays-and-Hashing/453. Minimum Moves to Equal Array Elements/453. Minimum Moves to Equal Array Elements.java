1class Solution {
2    public int minMoves(int[] nums) {
3        int minNum= nums[0];
4        for(int num : nums){
5            minNum = Math.min(minNum , num);
6        }
7
8        int minimumMoves = 0;
9        for(int num : nums){
10            minimumMoves += (num - minNum);
11        }
12        return minimumMoves;
13    }
14}