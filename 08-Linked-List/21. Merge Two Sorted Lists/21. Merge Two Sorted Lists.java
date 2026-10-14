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
12    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
13        
14    ListNode a=new ListNode();
15    ListNode result=a;
16
17    while(list1!=null && list2!=null){
18        
19        if(list1.val > list2.val){
20            result.next=list2;
21            list2=list2.next;
22        }else{
23            result.next=list1;
24            list1=list1.next;;
25            
26        }
27        result=result.next;
28    }
29result.next=(list1!=null)?list1:list2;
30return a.next;
31      
32    }
33}