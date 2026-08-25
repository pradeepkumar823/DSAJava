1class Solution {
2    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
3        Map<Integer, Integer> ng = new HashMap<>();
4        Deque<Integer> st = new ArrayDeque<>();
5
6        for (int num : nums2) {
7            while (!st.isEmpty() && st.peek() < num) {
8                ng.put(st.pop(), num);
9            }
10            st.push(num);
11        }
12
13        int[] res = new int[nums1.length];
14        for (int i = 0; i < nums1.length; i++) {
15            res[i] = ng.getOrDefault(nums1[i], -1);
16        }
17        return res;        
18    }
19}