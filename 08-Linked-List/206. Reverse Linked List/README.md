<h2><a href="https://leetcode.com/problems/reverse-linked-list">206. Reverse Linked List</a></h2>

<p>Given the <code>head</code> of a singly linked list, reverse the list, and return <em>the reversed list</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg" style="width: 542px; height: 222px;" data-first-enter-image="true">
<pre><strong>Input:</strong> head = [1,2,3,4,5]
<strong>Output:</strong> [5,4,3,2,1]
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/19/rev1ex2.jpg" style="width: 182px; height: 222px;">
<pre><strong>Input:</strong> head = [1,2]
<strong>Output:</strong> [2,1]
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> head = []
<strong>Output:</strong> []
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the list is the range <code>[0, 5000]</code>.</li>
	<li><code>-5000 &lt;= Node.val &lt;= 5000</code></li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong> A linked list can be reversed either iteratively or recursively. Could you implement both?</p>


---

# 🛍️ Reverse-Linked-List | Explained

## Approach 1: Iterative In-Place Reversal (Three-Pointer Technique)

### Intuition
Imagine a one-way conga line or a chain of train cars linked together. Each car only has a coupler pointing to the car in front of it. 

If you want to reverse the direction of the entire train without moving the physical cars, each car must disconnect its coupler and reconnect it to the car behind it. However, the moment you disconnect a car's coupler from the car ahead, you lose track of the rest of the train. To prevent the rest of the train from rolling away into the void, you must have a spotter hold onto the next car (`next`) before detaching the current connection (`curr.next = prev`). Once the link is reversed, you step forward and repeat the process.

### Algorithm Visualized

```mermaid
flowchart TD
    subgraph Step 1: Cache Next Node
        A1["prev"] -.-x B1["curr (1)"]
        B1 --> C1["next (2)"]
        C1 --> D1["3 -> null"]
    end

    subgraph Step 2: Reverse Link
        A2["prev"] <-- B2["curr (1)"]
        C2["next (2)"] --> D2["3 -> null"]
    end

    subgraph Step 3: Shift prev & curr Forward
        B3["prev (1)"] <-- A3["null"]
        C3["curr (2)"] --> D3["3 -> null"]
    end
```

### Approach
1. **Pointers Setup:**
   - `curr`: Starts at the `head` of the list. This is the node currently being redirected.
   - `prev`: Starts as `null`. This represents the tail of the newly reversed portion (since the original head will become the new tail pointing to `null`).
   - `next`: A temporary reference to preserve the remaining unreversed chain.
2. **Traversal & Redirection Loop (`while curr != null`):**
   - **Save:** Store `curr.next` in `next` to prevent losing the remainder of the list.
   - **Reverse:** Set `curr.next = prev` to redirect the current node backwards.
   - **Advance `prev`:** Move `prev` forward to `curr` (it is now the head of the reversed portion).
   - **Advance `curr`:** Move `curr` forward to `next` (ready to process the next incoming node).
3. **Termination:**
   - When `curr` reaches `null`, all nodes have been processed.
   - `prev` now points to the last node processed, which is the new `head` of the reversed linked list. Return `prev`.

### Detailed Code Analysis

```java
ListNode curr = head;
ListNode next = null;
ListNode prev = null;
```
- **Line 13–15:** Memory addresses of nodes are tracked using three reference variables. Initializing `prev = null` is crucial because the original head must point to `null` to mark the end of the new list. `next` is declared outside the loop to avoid recreating the reference variable in every iteration.

```java
while (curr != null) {
```
- **Line 17:** The loop continues as long as there is an unvisited node. Once `curr` steps past the original last node into `null`, the list has been entirely reversed.

```java
    next = curr.next;
```
- **Line 18 (Save Reference):** Before severing `curr`'s forward reference, we capture `curr.next`. Without this step, setting `curr.next = prev` would cause an irreversible loss of access to the rest of the list, resulting in an orphaned list/memory leak.

```java
    curr.next = prev;
```
- **Line 19 (Reverse Pointer):** The core mutation. The forward arrow is flipped to point backward to whatever `prev` currently points to.

```java
    prev = curr;
    curr = next;
```
- **Lines 20–21 (Slide Pointers):** Both tracking pointers slide one step to the right. `prev` claims the node we just redirected, and `curr` moves to the saved `next` node.

```java
return prev;
```
- **Line 23:** When `curr` is `null`, `prev` resides at the former tail node (now the new head). Returning `prev` provides the entry point to the fully reversed list.

### Code
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode next = null;
        ListNode prev = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
```

### Complexity
- **Time:** $\mathcal{O}(N)$, where $N$ is the number of nodes in the linked list. Each node is visited exactly once, performing constant $\mathcal{O}(1)$ pointer operations inside the loop.
- **Space:** $\mathcal{O}(1)$. The algorithm runs strictly in-place. Only three reference pointers (`curr`, `prev`, `next`) are allocated regardless of list length.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you solve this recursively, and what are the trade-offs?
You can recursively reach the end of the list and reverse pointers on the unwinding phase of the call stack:
```java
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}
```
**Trade-off:** While the recursive code is concise, it consumes $\mathcal{O}(N)$ auxiliary stack space. If the list contains tens of thousands of elements, it will trigger a `StackOverflowError`, making the iterative approach strictly superior for production environments.

### 2. How does this logic change if asked to reverse only a subsegment from position `m` to `n` (LeetCode 92)?
Instead of reversing the whole list, you traverse to index `m - 1`, keep pointers to the node before the sublist and the start of the sublist, run the identical three-pointer reversal for $(n - m + 1)$ iterations, and finally reconnect the boundaries to bridge the sublist back into the original chain.