<h2><a href="https://leetcode.com/problems/flatten-binary-tree-to-linked-list">114. Flatten Binary Tree to Linked List</a></h2>

<p>Given the <code>root</code> of a binary tree, flatten the tree into a "linked list":</p>

<ul>
	<li>The "linked list" should use the same <code>TreeNode</code> class where the <code>right</code> child pointer points to the next node in the list and the <code>left</code> child pointer is always <code>null</code>.</li>
	<li>The "linked list" should be in the same order as a <a href="https://en.wikipedia.org/wiki/Tree_traversal#Pre-order,_NLR" target="_blank"><strong>pre-order</strong><strong> traversal</strong></a> of the binary tree.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/01/14/flaten.jpg" style="width: 500px; height: 226px;">
<pre><strong>Input:</strong> root = [1,2,5,3,4,null,6]
<strong>Output:</strong> [1,null,2,null,3,null,4,null,5,null,6]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> root = []
<strong>Output:</strong> []
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre><strong>Input:</strong> root = [0]
<strong>Output:</strong> [0]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[0, 2000]</code>.</li>
	<li><code>-100 &lt;= Node.val &lt;= 100</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow up:</strong> Can you flatten the tree in-place (with <code>O(1)</code> extra space)?

---

# 🛍️ Flatten-Binary-Tree-to-Linked-List | Explained

## Approach 1: Pre-Order Traversal with Auxiliary Array List

### Intuition
Think of a binary tree as an organization chart with managers and sub-teams. Your goal is to line up every person single-file in the exact order they would be called during a standard roll call (Pre-Order: Boss $\rightarrow$ Left Subordinate $\rightarrow$ Right Subordinate). 

In this approach, you conduct the roll call first and write down everyone's reference on a clipboard (an `ArrayList`). Once you have the complete list in order, you walk down the list and adjust everyone's pointers: clearing out their left hand pointer (`left = null`) and making their right hand point directly to the next person in line (`right = next_node`).

---

### Algorithm Visualized

```mermaid
flowchart TD
    subgraph Step 1: Pre-Order Traversal Collection
        A(("1")) --> B(("2"))
        A --> C(("5"))
        B --> D(("3"))
        B --> E(("4"))
        C --> F(("6"))
    end

    subgraph Step 2: Collected List
        L["List: [1, 2, 3, 4, 5, 6]"]
    end

    subgraph Step 3: Rewire Pointers
        1(("1")) -->|right| 2(("2"))
        2 -->|right| 3(("3"))
        3 -->|right| 4(("4"))
        4 -->|right| 5(("5"))
        5 -->|right| 6(("6"))
    end

    Step 1 --> Step 2 --> Step 3
```

---

### Approach
1. **Edge Case Handling**: Check if the tree root is `null`. If it is, return early as there is nothing to flatten.
2. **Node Collection**: Initialize a dynamic list (`List<TreeNode>`) to hold the references to all tree nodes.
3. **Pre-Order Traversal**: Perform a recursive DFS traversal following the **Root $\rightarrow$ Left $\rightarrow$ Right** ordering pattern. Append each visited node to the list.
4. **Pointer Rewiring**: Iterate through the collected list from index `0` up to `size - 2`:
   - Set the current node's `left` pointer to `null`.
   - Set the current node's `right` pointer to reference the next node in the list (`nodes.get(i + 1)`).
5. **Final Node**: The last node in the pre-order sequence is naturally a leaf node, so its `left` and `right` pointers remain `null`.

---

### Detailed Code Analysis

```java
17    public void flatten(TreeNode root) {
18        if (root == null)
19            return;
```
* **Lines 18–19**: Base case protection. Avoids `NullPointerException` if an empty tree is passed as input.

```java
20        List<TreeNode> nodes = new ArrayList<>();
21        preOrder(root, nodes);
```
* **Line 20**: Instantiates an `ArrayList` to temporarily hold memory references of the tree nodes in pre-order sequence.
* **Line 21**: Initiates the recursive traversal starting from `root`.

```java
28    private void preOrder(TreeNode node, List<TreeNode> nodes) {
29        if (node == null) return;
30        nodes.add(node);
31        preOrder(node.left, nodes);
32        preOrder(node.right, nodes);
33    }
```
* **Lines 28–33**: Standard recursive pre-order traversal:
  - **Line 29**: Returns when hitting a `null` child branch.
  - **Line 30**: Adds the current node **before** exploring children (Root processing).
  - **Lines 31–32**: Recursively visits left subtree, then right subtree.

```java
22        for (int i = 0; i < nodes.size() - 1; i++) {
23            nodes.get(i).left = null;
24            nodes.get(i).right = nodes.get(i + 1);
25        }
26    }
```
* **Lines 22–25**: Transforms the tree structure into a linked-list structure:
  - Iterates up to `nodes.size() - 1` because node `i` links to `i + 1`.
  - **Line 23**: Nullifies the `left` pointer to fulfill the problem's requirement that all left pointers must be `null`.
  - **Line 24**: Updates the `right` pointer to point to the subsequent node in the pre-order list.

---

### Code

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public void flatten(TreeNode root) {
        if (root == null)
            return;
        List<TreeNode> nodes = new ArrayList<>();
        preOrder(root, nodes);
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).left = null;
            nodes.get(i).right = nodes.get(i + 1);
        }
    }

    private void preOrder(TreeNode node, List<TreeNode> nodes) {
        if (node == null) return;
        nodes.add(node);
        preOrder(node.left, nodes);
        preOrder(node.right, nodes);
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$
  - Traversal visits each of the $N$ nodes exactly once ($\mathcal{O}(N)$).
  - The `for` loop runs $N - 1$ times, performing constant time $\mathcal{O}(1)$ pointer updates per iteration ($\mathcal{O}(N)$).
  - **Total Time:** $\mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)$.

- **Space Complexity:** $\mathcal{O}(N)$
  - The `ArrayList` holds references to all $N$ nodes, taking $\mathcal{O}(N)$ extra space.
  - Recursion call stack requires $\mathcal{O}(H)$ space where $H$ is the height of the tree ($\mathcal{O}(N)$ in the worst case for a skewed tree, $\mathcal{O}(\log N)$ for a balanced tree).
  - **Total Space:** $\mathcal{O}(N)$.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can you solve this in $O(1)$ extra auxiliary space (in-place)?
**Answer:**
You can use **Morris Traversal pattern (Threaded Binary Tree concepts)** or an **Iterative Right-to-Left Post-Order Traversal**:

1. **Morris-style approach**: Iterate through nodes with a `curr` pointer. For any node with a `left` child, find the rightmost node of that `left` subtree (the pre-order predecessor). Connect that rightmost node's `right` pointer to `curr.right`. Then move `curr.right = curr.left` and set `curr.left = null`. Finally, advance `curr = curr.right`.
2. **Reverse Post-Order (Right $\rightarrow$ Left $\rightarrow$ Root)**: Maintain a global `prev` pointer initialized to `null`. Recursively process right child, then left child, then update `curr.right = prev`, `curr.left = null`, and `prev = curr`.