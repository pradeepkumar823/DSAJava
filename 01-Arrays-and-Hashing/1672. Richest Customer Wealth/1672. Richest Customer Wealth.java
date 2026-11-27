1class Solution {
2    public int maximumWealth(int[][] accounts) {
3        int maxSum=Integer.MIN_VALUE;
4        for(int i=0;i<accounts.length;i++){
5            int sum=0;
6            for(int j=0;j<accounts[i].length;j++){
7                sum = sum + accounts[i][j];
8            }
9            maxSum=Math.max(sum,maxSum);
10        }
11        return maxSum;
12    }
13}