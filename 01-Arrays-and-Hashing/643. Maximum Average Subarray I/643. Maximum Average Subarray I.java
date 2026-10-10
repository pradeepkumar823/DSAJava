1class Solution {
2    public double findMaxAverage(int[] nums, int k) {
3         int maxValue=Integer.MIN_VALUE;
4         int sum=0;
5       for(int i =0;i<k;i++){
6        sum+=nums[i];
7       }
8        maxValue=sum;
9
10       for(int i=k;i<nums.length;i++){
11            sum=sum-nums[i-k]+nums[i];
12            maxValue=Math.max(maxValue,sum);
13        }
14       
15       return (double) maxValue / k;
16    }
17}