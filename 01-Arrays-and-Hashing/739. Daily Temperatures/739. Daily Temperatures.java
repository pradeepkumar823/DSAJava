1class Solution {
2    public int[] dailyTemperatures(int[] temps) {
3        int[] results = new int[temps.length];
4        Stack<Integer> stack = new Stack<>();
5        /// UPVOTE !
6        for (int i = 0; i < temps.length; i++) {
7            while (!stack.isEmpty() && temps[stack.peek()] < temps[i]) {
8                results[stack.peek()] = i - stack.pop();
9            }
10            stack.push(i);
11        }
12
13        return results;
14    }
15}