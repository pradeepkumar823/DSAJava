1class Solution {
2    public double minimumAverage(int[] nums) {
3        Arrays.sort(nums);
4        double minAvg = Double.MAX_VALUE;
5        int right=nums.length-1;
6        for(int left=0;left<nums.length/2;left++){
7        double avg=(nums[left]+nums[right])/2.0;
8        minAvg=Math.min(avg,minAvg);
9        right--;
10        }
11
12        return minAvg;
13        
14    }
15}