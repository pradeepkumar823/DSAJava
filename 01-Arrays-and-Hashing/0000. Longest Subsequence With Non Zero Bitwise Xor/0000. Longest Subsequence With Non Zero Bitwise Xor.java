1class Solution {
2    public int longestSubsequence(int[] nums) {
3        int totalXor = 0;
4        boolean hasNonZero = false;
5        
6        for (int x : nums) {
7            totalXor ^= x;
8            if (x != 0) hasNonZero = true;
9        }
10         if (!hasNonZero) return 0;
11        if (totalXor != 0) return nums.length;
12        
13        return nums.length - 1;
14    }
15}