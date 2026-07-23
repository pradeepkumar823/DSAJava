<h2><a href="https://leetcode.com/problems/largest-rectangle-in-histogram">84. Largest Rectangle in Histogram</a></h2>

<p>Given an array of integers <code>heights</code> representing the histogram's bar height where the width of each bar is <code>1</code>, return <em>the area of the largest rectangle in the histogram</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/01/04/histogram.jpg" style="width: 522px; height: 242px;">
<pre><strong>Input:</strong> heights = [2,1,5,6,2,3]
<strong>Output:</strong> 10
<strong>Explanation:</strong> The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/01/04/histogram-1.jpg" style="width: 202px; height: 362px;">
<pre><strong>Input:</strong> heights = [2,4]
<strong>Output:</strong> 4
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= heights.length &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= heights[i] &lt;= 10<sup>4</sup></code></li>
</ul>


---

# 🛍️ Largest-Rectangle-in-Histogram | Explained

## Approach 1: Monotonic Stack with Virtual Boundary (Sentinel Value)

### Intuition
Imagine building the largest possible billboard across adjacent buildings of varying heights. To maximize the area for a rectangle whose height is dictated by a specific building $B$, you must extend the rectangle as far left and right as possible until you encounter a building strictly **shorter** than $B$.

Instead of checking every possible pair of boundaries ($O(N^2)$ or $O(N^3)$ brute-force), we maintain a **Monotonic Increasing Stack** of indices. 

1. As long as building heights are increasing, we keep pushing their indices onto the stack.
2. The moment we see a building shorter than the one at the top of our stack, we know that the taller building at the stack's top **cannot extend any further to the right**.
3. We pop that taller building, determine its left and right boundaries using the current index and the new top of the stack, and calculate its maximal area.
4. By appending a virtual building of height `0` at index `n` (the sentinel value), we force the stack to pop and evaluate all remaining elements at the end of the array.

---

### Algorithm Visualized

```mermaid
graph TD
    A[Start Loop i = 0 to n] --> B{i == n?}
    B -- Yes --> C[currHeight = 0]
    B -- No --> D[currHeight = heights[i]]
    C --> E{Stack not empty AND currHeight < heights[st.peek()]}
    D --> E
    
    E -- True --> F[Pop topIdx from Stack]
    F --> G["height = heights[topIdx]<br/>right = i - 1"]
    G --> H{Stack Empty?}
    H -- Yes --> I[left = 0]
    H -- No --> J[left = st.peek() + 1]
    I --> K["width = right - left + 1<br/>area = height * width"]
    J --> K
    K --> L[maxArea = max(maxArea, area)]
    L --> E

    E -- False --> M[Push index i to Stack]
    M --> N[Increment i]
    N --> A
```

---

### Approach

1. **Initialize Data Structures**:
   - `maxArea`: Tracks the maximum area found so far.
   - `st`: A stack (`Deque<Integer>`) storing **indices** of histogram bars in non-decreasing order of their heights.

2. **Iterate with Sentinel Boundary ($i = 0$ to $n$)**:
   - Loop up to $i = n$. When $i = n$, set `currHeight = 0`. This dummy height acts as a trigger to clear all remaining elements from the stack.

3. **Maintain Monotonic Property**:
   - While the stack is not empty and the current height is less than the height at `st.peek()`:
     - Pop `topIdx` from the stack. This gives the height of the rectangle: `height = heights[topIdx]`.
     - The **right boundary index** for this rectangle is $i - 1$.
     - The **left boundary index** depends on what remains in the stack:
       - If the stack is empty, it means `heights[topIdx]` was the shortest bar seen so far, extending all the way back to index `0`.
       - If the stack is not empty, the left boundary starts at `st.peek() + 1`.
     - Calculate `width = right - left + 1` and update `maxArea`.

4. **Push & Repeat**:
   - Push index $i$ onto the stack and continue.

5. **Return Result**:
   - Cast `maxArea` back to `int` and return.

---

### Detailed Code Analysis

Let's examine the key blocks of the implementation:

```java
Deque<Integer> st = new ArrayDeque<>(); 
```
> **Why `ArrayDeque`?** In Java, `ArrayDeque` is significantly faster than `java.util.Stack` because it is unsynchronized and avoids overhead from Vector operations.

```java
for (int i = 0; i <= n; i++) {
    int currHeight = (i == n) ? 0 : heights[i];
```
> **The Sentinel Trick**: Standard monotonic stack approaches often require a second loop after the main iteration to flush remaining stack elements. By extending the loop boundary to `i <= n` and setting `currHeight = 0` when `i == n`, we cleanly force all remaining elements out of the stack within a single loop structure.

```java
while (!st.isEmpty() && currHeight < heights[st.peek()]) {
    int topIdx = st.pop();          
    int height = heights[topIdx];
    int right = i - 1;
    int left = st.isEmpty() ? 0 : st.peek() + 1;

    int width = right - left + 1;
    long area = (long) height * width;
    if (area > maxArea) maxArea = area;
}
```
> **Calculating Boundaries**:
> - `topIdx` is the candidate bar whose maximum area we are computing.
> - `right = i - 1`: Since `currHeight` is strictly smaller than `heights[topIdx]`, the bar at `topIdx` cannot extend to `i`. Thus, the right limit is `i - 1`.
> - `left = st.isEmpty() ? 0 : st.peek() + 1`: The element directly below `topIdx` in the stack is guaranteed to be strictly shorter than `heights[topIdx]`. Therefore, the bar at `topIdx` can extend to the left up to `st.peek() + 1`.

```java
st.push(i);
```
> Stores the **index** (not the height) to preserve spatial information required for width calculations.

---

### Code

```java
class Solution {
   
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        long maxArea = 0;               
        Deque<Integer> st = new ArrayDeque<>(); 
        
        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];
            
            while (!st.isEmpty() && currHeight < heights[st.peek()]) {
                int topIdx = st.pop();          
                int height = heights[topIdx];
                int right = i - 1;
                int left = st.isEmpty() ? 0 : st.peek() + 1;

                int width = right - left + 1;
                long area = (long) height * width;
                if (area > maxArea) maxArea = area;
            }
            st.push(i);
        }
        return (int) maxArea;
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$
  - Although there is a nested `while` loop inside the `for` loop, each index $i$ is pushed onto the stack **exactly once** and popped **at most once**. Thus, total stack operations are bounded by $2N$, leading to linear time complexity.

- **Space Complexity:** $\mathcal{O}(N)$
  - In the worst-case scenario (e.g., an array with strictly increasing bar heights like `[1, 2, 3, 4, 5]`), the stack will store up to $N$ indices simultaneously before hitting the sentinel $0$.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How does this solution extend to 2D matrices (e.g., LeetCode 85 - "Maximal Rectangle")?
**Answer:**
LeetCode 85 asks for the largest rectangle of 1s in a 2D binary matrix. We can reduce the 2D problem into $M$ sub-problems of "Largest Rectangle in Histogram":
1. Treat each row as the base of a histogram.
2. Accumulate consecutive `1`s column-wise to build heights for each row.
3. Call `largestRectangleArea()` on each row's height array.
4. Total Time Complexity: $\mathcal{O}(R \times C)$, where $R$ is rows and $C$ is columns.

### 2. Why store indices instead of heights directly in the stack?
**Answer:**
If we only store heights, we lose the horizontal position (x-coordinates) of the boundaries. Storing indices gives us instant access to both:
- **Height**: `heights[st.peek()]`
- **Width**: `right - left + 1` via index arithmetic.