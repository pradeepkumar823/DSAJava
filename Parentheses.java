import java.util.ArrayDeque;
import java.util.Deque;

public class Parentheses {
    public int longestValidParentheses(String s) {
        int maxLen = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else { // ch == ')'
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i); // new base for future calculations
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        Parentheses sol = new Parentheses();
        System.out.println(sol.longestValidParentheses("(()"));     // 2
        System.out.println(sol.longestValidParentheses(")()())"));  // 4
        System.out.println(sol.longestValidParentheses("")); //0
        System.out.println(sol.longestValidParentheses("(((((()(()()()((((())))))((()()()()))))()(())()()()"));    //48
    }
}