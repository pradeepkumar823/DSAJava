<h2><a href="https://leetcode.com/problems/merge-two-sorted-lists">21. Merge Two Sorted Lists</a></h2>

<p>You are given the heads of two sorted linked lists <code>list1</code> and <code>list2</code>.</p>

<p>Merge the two lists into one <strong>sorted</strong> list. The list should be made by splicing together the nodes of the first two lists.</p>

<p>Return <em>the head of the merged linked list</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/10/03/merge_ex1.jpg" style="width: 662px; height: 302px;">
<pre><strong>Input:</strong> list1 = [1,2,4], list2 = [1,3,4]
<strong>Output:</strong> [1,1,2,3,4,4]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> list1 = [], list2 = []
<strong>Output:</strong> []
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> list1 = [], list2 = [0]
<strong>Output:</strong> [0]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in both lists is in the range <code>[0, 50]</code>.</li>
	<li><code>-100 &lt;= Node.val &lt;= 100</code></li>
	<li>Both <code>list1</code> and <code>list2</code> are sorted in <strong>non-decreasing</strong> order.</li>
</ul>


---

# 🛍️ Merge-Two-Sorted-Lists | Explained

## Approach 1: Iterative Pointer Splicing with a Dummy Node

### Intuition
Imagine you have two pre-sorted decks of playing cards placed face up on a table (`list1` and `list2`). Your goal is to combine them into one single sorted pile. 

At any moment, you look at the top card of both decks. You pick whichever card has the smaller value, place it at the end of your new pile, and expose the next card in that deck. You repeat this until one deck runs completely out of cards. Once one deck is empty, you don't need to compare cards anymore—you simply take the entire remaining stack of the other deck and place it at the end of your merged pile.

In linked lists, using a **dummy node** acts as a temporary anchor for the head of the new list. This avoids writing special-case logic to determine which node becomes the initial head.

---

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start: list1, list2]) --> Init["Create Dummy Node 'a'<br/>Set result = a"]
    Init --> CheckBoth{"list1 != null AND list2 != null?"}
    
    CheckBoth -- Yes --> Compare{"list1.val > list2.val?"}
    Compare -- Yes --> PickL2["result.next = list2<br/>list2 = list2.next"]
    Compare -- No --> PickL1["result.next = list1<br/>list1 = list1.next"]
    
    PickL2 --> AdvanceResult["result = result.next"]
    PickL1 --> AdvanceResult
    AdvanceResult --> CheckBoth
    
    CheckBoth -- No --> AttachRemaining["Attach remaining non-null list:<br/>result.next = (list1 != null) ? list1 : list2"]
    AttachRemaining --> ReturnNode(["Return a.next (Merged Head)"])
```

---

### Approach

1. **Initialize a Dummy Head**: Create a sentinel/dummy node `a` to serve as the start of the merged list. Keep a working pointer `result` positioned at `a`.
2. **Iterate While Both Lists Are Active**: Run a loop while both `list1` and `list2` are not `null`:
   - Compare `list1.val` and `list2.val`.
   - If `list1.val > list2.val`, link `result.next` to `list2` and advance the `list2` pointer.
   - Otherwise, link `result.next` to `list1` and advance the `list1` pointer.
   - Advance the `result` pointer forward by one node (`result = result.next`).
3. **Connect Remaining Nodes**: Once one list is exhausted, the loop terminates. The remaining list is already sorted, so attach the non-null list directly to `result.next` using a ternary operator.
4. **Return Head**: Return `a.next`, which skips the dummy node and points directly to the first real node of the merged list.

---

### Detailed Code Analysis

#### 1. Sentinel Initialization
```java
ListNode a = new ListNode();
ListNode result = a;
```
- `a` is allocated as a dummy node. It holds no meaningful value, but its `.next` pointer will anchor the head of the sorted list.
- `result` acts as our moving tail pointer. Every newly chosen node is attached to `result.next`.

#### 2. The Merging Loop
```java
while (list1 != null && list2 != null) {
    if (list1.val > list2.val) {
        result.next = list2;
        list2 = list2.next;
    } else {
        result.next = list1;
        list1 = list1.next;
    }
    result = result.next;
}
```
- **Loop Condition (`list1 != null && list2 != null`)**: We can only compare values if both nodes exist. 
- **Comparison & Re-linking**:
  - If `list1.val > list2.val`, `list2` has the smaller value. We wire `result.next` to point to `list2`, and advance `list2` to its next node.
  - If `list1.val <= list2.val`, we wire `result.next` to point to `list1`, and advance `list1` to its next node.
- **Advancing the Builder Pointer (`result = result.next`)**: We shift `result` forward so it always stays at the current end of the merged list.

#### 3. Splicing the Remainder & Returning
```java
result.next = (list1 != null) ? list1 : list2;
return a.next;
```
- Because linked lists are reference-based, we do not need to iterate through the rest of the surviving list. We attach the entire remaining sublist in $O(1)$ time by pointing `result.next` to whichever pointer is not `null`.
- `return a.next;` bypasses the dummy node `a` and returns the actual head of the combined list.

---

### Code

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode a = new ListNode();
        ListNode result = a;

        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                result.next = list2;
                list2 = list2.next;
            } else {
                result.next = list1;
                list1 = list1.next;
            }
            result = result.next;
        }

        result.next = (list1 != null) ? list1 : list2;
        return a.next;
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(M + N)$
  - Where $M$ is the number of nodes in `list1` and $N$ is the number of nodes in `list2`.
  - In the worst case (elements alternate in magnitude), the `while` loop runs $M + N - 1$ times. Splicing the remainder takes $\mathcal{O}(1)$ time.

- **Space Complexity:** $\mathcal{O}(1)$
  - No new list nodes are created except for a single dummy node (`ListNode a = new ListNode()`).
  - The algorithm re-wires existing pointers in place, requiring constant auxiliary memory.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **How would you solve this recursively, and what are the trade-offs?**
   - **Answer:** You can define `mergeTwoLists(l1, l2)` recursively: compare heads, assign the smaller node's `.next` to the result of `mergeTwoLists(smaller.next, larger)`, and return the smaller node. 
   - **Trade-off:** The recursive approach is more concise, but it uses $\mathcal{O}(M + N)$ auxiliary stack space. If the lists contain tens of thousands of nodes, it can lead to a `StackOverflowError`. The iterative approach is production-safe.

2. **How does this scale to merging $K$ sorted lists (LeetCode 23)?**
   - **Answer:** 
     - **Option A (Priority Queue):** Push the head of all $K$ lists into a Min-Heap of size $K$. Repeatedly extract the min and insert its `.next`. Complexity: $\mathcal{O}(N \log K)$ where $N$ is total nodes.
     - **Option B (Divide and Conquer):** Pair up the $K$ lists and merge each pair using this exact 2-list algorithm, repeating until one list remains. Complexity: $\mathcal{O}(N \log K)$ time and $\mathcal{O}(1)$ auxiliary space.