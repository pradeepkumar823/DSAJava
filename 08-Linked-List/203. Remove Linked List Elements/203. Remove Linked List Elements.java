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
12    public ListNode removeElements(ListNode head, int val) {
13        ListNode a=new ListNode(-1);
14        a.next=head;
15        ListNode curr=head,prev=a;
16        while(curr!=null){
17            if(curr.val==val){
18                prev.next=curr.next;
19            }else{
20                prev=prev.next;
21            }
22            curr=curr.next;
23        }
24        return a.next;
25    }
26}