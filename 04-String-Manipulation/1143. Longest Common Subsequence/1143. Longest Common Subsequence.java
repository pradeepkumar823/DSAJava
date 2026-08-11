1class Solution {
2    public int longestCommonSubsequence(String text1, String text2) {
3        int n = text1.length();
4        int m = text2.length();
5
6        int[][] dp = new int[n + 1][m + 1];
7
8        for (int i = 1; i <= n; i++) {
9            for (int j = 1; j <= m; j++) {
10                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
11                    dp[i][j] = dp[i - 1][j - 1] + 1;
12                } else {
13                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
14                }
15            }
16        }
17
18        return dp[n][m];
19    }
20}