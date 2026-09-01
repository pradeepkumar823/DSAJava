1class Solution {
2    public int calPoints(String[] operations) {
3       //create a Stack
4      Stack<Integer> stack = new Stack<>();
5
6        for (String op : operations) {
7
8            if (op.equals(C)) {
9                stack.pop();
10            }
11
12            else if (op.equals(D)) {
13                stack.push(stack.peek() * 2);
14            }
15
16            else if (op.equals(+)) {
17                int last = stack.pop();
18                int secondLast = stack.peek();
19
20                stack.push(last);
21                stack.push(last + secondLast);
22            }
23
24            else {
25                stack.push(Integer.parseInt(op));
26            }
27        }
28
29        int sum = 0;
30
31        for (int score : stack) {
32            sum += score;
33        }
34
35        return sum;
36    }
37}