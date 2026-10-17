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
12    public ListNode removeNthFromEnd(ListNode head, int n) {
13        ListNode res=new ListNode(0,head);
14        ListNode dummy=res;
15        for (int i=0;i<n;i++){
16            head=head.next;
17        }
18        while(head!=null){
19            head=head.next;
20            dummy=dummy.next;
21        }
22        dummy.next=dummy.next.next;
23        return res.next;
24    }   
25}