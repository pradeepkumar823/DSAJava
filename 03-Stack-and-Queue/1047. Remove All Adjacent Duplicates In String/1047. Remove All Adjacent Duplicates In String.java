1class Solution {
2    public String removeDuplicates(String s) {
3        //create empty string
4        // loop
5        // if data is match with previous data then add,otherwise remove previous data
6        //return
7        //otherwise push the data in stack
8        //then after processing all char ,the stack contain final char
9        //now convet the stack char into string using StringBuilder()
10        //and return it
11        Stack<Character> stack =new Stack<>();
12        for(int i=0;i<s.length();i++){
13            if(!stack.isEmpty() && stack.peek()==s.charAt(i)){
14                stack.pop();
15            }else{
16                stack.push(s.charAt(i));
17            }
18        }
19        StringBuilder ans= new StringBuilder();
20        for(char ch: stack){
21            ans.append(ch);
22        }
23        return ans.toString();
24    }
25}