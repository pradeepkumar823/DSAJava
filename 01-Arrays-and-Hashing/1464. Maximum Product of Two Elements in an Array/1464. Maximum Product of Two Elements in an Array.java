1class Solution {
2    public int maxProduct(int[] nums) {
3int m =0;
4        for(int i=0;i<nums.length;i++){
5            for(int j=i+1;j<nums.length;j++){
6                m=Math.max(m,(nums[i]-1)*(nums[j]-1));
7            }
8        }
9        return m;
10    }
11}