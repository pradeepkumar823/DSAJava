1class Solution {
2   
3    public int largestRectangleArea(int[] heights) {
4        int n = heights.length;
5        long maxArea = 0;               
6        Deque<Integer> st = new ArrayDeque<>(); 
7        for (int i = 0; i <= n; i++) {
8            int currHeight = (i == n) ? 0 : heights[i];
9            while (!st.isEmpty() && currHeight < heights[st.peek()]) {
10                int topIdx = st.pop();          
11                int height = heights[topIdx];
12                int right = i - 1;
13                int left = st.isEmpty() ? 0 : st.peek() + 1;
14
15                int width = right - left + 1;
16                long area = (long) height * width;
17                if (area > maxArea) maxArea = area;
18            }
19            st.push(i);
20        }
21        return (int) maxArea;
22    }
23}
24