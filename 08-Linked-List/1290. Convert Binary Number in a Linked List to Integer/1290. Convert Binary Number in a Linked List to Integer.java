1/**
2 * Definition for singly-linked list.
3 * public class ListNode {
4 *     int val;
5 *     ListNode next;
6 *     ListNode() {}
7 *     ListNode(int val) { this.val = val; }
8 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
9 * }
10 */
11class Solution {
12    // Intuition: multiply running result by 2, add next bit (Horner's method base 2)
13    public int getDecimalValue(ListNode head) {
14        int result = head.val;
15        while (head.next != null) {
16            head = head.next;
17            result = result * 2 + head.val;
18        }
19        return result;
20    }
21}