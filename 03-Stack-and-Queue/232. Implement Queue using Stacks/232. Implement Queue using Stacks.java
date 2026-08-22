1class MyQueue {
2
3    private Stack<Integer> first;
4    private Stack<Integer> second;
5
6    public MyQueue() {
7        first=new Stack<>();
8        second=new Stack<>();
9    }
10    
11    public void push(int x) {
12        first.push(x);
13        
14    }
15    
16    public int pop() {
17        if(second.isEmpty()){
18            while(!first.isEmpty()){
19                second.push(first.pop());
20            }
21        }
22        return second.pop();
23        
24    }
25    
26    public int peek() {
27        if(second.isEmpty()){
28            while(!first.isEmpty()){
29                second.push(first.pop());
30            }
31        }
32        return second.peek();
33        
34    }
35    
36    public boolean empty() {
37        return first.isEmpty() && second.isEmpty();
38        
39    }
40}
41
42/**
43 * Your MyQueue object will be instantiated and called as such:
44 * MyQueue obj = new MyQueue();
45 * obj.push(x);
46 * int param_2 = obj.pop();
47 * int param_3 = obj.peek();
48 * boolean param_4 = obj.empty();
49 */