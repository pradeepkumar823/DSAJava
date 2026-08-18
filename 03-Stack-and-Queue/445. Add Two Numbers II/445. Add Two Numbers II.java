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
12    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
13
14     l1 = reverse(l1);
15     l2 = reverse(l2);
16
17        ListNode dummy = new ListNode(0);
18        ListNode curr = dummy;
19        int carry = 0;
20
21        while (l1 != null || l2 != null || carry != 0) {
22
23            int digit1 = (l1 != null) ? l1.val : 0;
24            int digit2 = (l2 != null) ? l2.val : 0;
25            int sum = digit1 + digit2 + carry;
26            int digit = sum % 10;
27            carry = sum / 10;
28
29                        curr.next = new ListNode(digit);
30            curr = curr.next;
31
32            if (l1 != null){  
33                l1=l1.next ;
34            }
35           if (l2 != null){  
36                l2=l2.next ;
37            }
38
39        }
40        return reverse(dummy.next);
41    }
42
43    private ListNode reverse(ListNode head) {
44        ListNode curr = head;
45        ListNode prev = null;
46
47        while (curr != null) {
48            ListNode next = curr.next;
49            curr.next = prev;
50            prev = curr;
51            curr = next;
52        }
53        return prev;
54
55    }
56
57}