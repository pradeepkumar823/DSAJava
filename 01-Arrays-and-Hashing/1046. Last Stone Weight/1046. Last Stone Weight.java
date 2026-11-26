1class Solution {
2    public int lastStoneWeight(int[] stones) {
3        PriorityQueue<Integer> a = new PriorityQueue<>(Collections.reverseOrder());
4        for (int i = 0; i < stones.length; i++) {
5            a.add(stones[i]);
6        }
7        while (a.size() > 1) {
8            int x = a.peek();
9            a.poll();
10            int y = a.peek();
11            a.poll();
12            if (x != y) a.add(x - y);
13        }
14        if (a.size() == 0) return 0;
15        return a.peek();
16    }
17}