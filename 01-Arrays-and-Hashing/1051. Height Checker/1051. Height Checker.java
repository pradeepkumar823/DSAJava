1class Solution {
2    public int heightChecker(int[] heights) {
3        int[] count = new int[101];
4
5        for (int height : heights) {
6            count[height]++;
7        }
8
9        int idx = 0;
10        int ans = 0;
11
12        for (int i = 0; i < 101; i++) {
13            while (count[i]-- != 0) {
14                if (heights[idx] != i) {
15                    ans++;
16                }
17                idx++;
18            }
19        }
20
21        return ans;
22    }
23}