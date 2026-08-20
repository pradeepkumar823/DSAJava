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
12    public ListNode deleteDuplicates(ListNode head) {
13        // Dummy node to handle edge cases (e.g., first node being a duplicate)
14        ListNode dummy = new ListNode(0);
15        dummy.next = head;
16        
17        ListNode prev = dummy;  // Node before the current sequence
18        ListNode current = head;
19        
20        while (current != null) {
21            // Check if current node has duplicates
22            while (current.next != null && current.val == current.next.val) {
23                current = current.next;
24            }
25            
26            // If prev.next == current, there were no duplicates
27            if (prev.next == current) {
28                prev = prev.next;
29            } else {
30                // Skip all duplicates
31                prev.next = current.next;
32            }
33            
34            current = current.next;
35        }
36        
37        return dummy.next;
38    }
39}
40