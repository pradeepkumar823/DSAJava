1class Solution {
2    public boolean canJump(int[] nums) {
3        int n=nums.length;
4        int goal=0;
5        for(int i=0;i<n;i++){
6            if(i > goal){
7                return false;
8            }
9            goal=Math.max(goal,nums[i]+i);
10        }
11        return true;
12    }
13}