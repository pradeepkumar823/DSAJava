1class Solution {
2    public List<String> generateParenthesis(int n) {
3        List<String> result = new ArrayList<>();
4        backtrack(result, , 0, 0, n);
5        return result;
6    }
7    
8    private void backtrack(List<String> result, String current, int openCount, int closeCount, int n) {
9        if (openCount == n && closeCount == n) {
10            result.add(current);
11            return;
12        }
13        
14        if (openCount < n) {
15            backtrack(result, current + (, openCount + 1, closeCount, n);
16        }
17        if (closeCount < openCount) {
18            backtrack(result, current + ), openCount, closeCount + 1, n);
19        }
20    }
21}