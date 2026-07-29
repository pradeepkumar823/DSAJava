<h2><a href="https://leetcode.com/problems/generate-parentheses">22. Generate Parentheses</a></h2>

<p>Given <code>n</code> pairs of parentheses, write a function to <em>generate all combinations of well-formed parentheses</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> n = 3
<strong>Output:</strong> ["((()))","(()())","(())()","()(())","()()()"]
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> n = 1
<strong>Output:</strong> ["()"]
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 8</code></li>
</ul>


---

# 🛍️ Generate-Parentheses | Explained

## Approach 1: Backtracking with Pruning (Constrained State-Space Exploration)

### Intuition
Imagine you are packing fragile items into matching pairs of boxes. Each item requires an "opening box" `(` first, and can only be sealed with a "closing box" `)`. 

To ensure every open box gets closed properly and no box is closed before it is opened, you must follow two fundamental rules at any point during assembly:
1. You cannot open more than $n$ total boxes.
2. You cannot place a closing box `)` unless there is at least one unclosed opening box `(` waiting for it (i.e., `closeCount < openCount`).

Instead of generating every possible sequence of $2n$ characters and checking if it's valid afterward (which wastes immense time on invalid combinations like `)))(((`), we build the string character by character. We **prune** (abandon) invalid paths the instant they violate our two rules.

---

### Algorithm Visualized

Below is the decision tree (State Space Tree) for $n = 2$, illustrating how backtracking explores valid paths while ignoring invalid branches:

```mermaid
graph TD
    A["'' (open:0, close:0)"] -->|Add '('| B["'(' (open:1, close:0)"]
    
    B -->|Add '('| C["'((' (open:2, close:0)"]
    B -->|Add ')'| D["'()' (open:1, close:1)"]
    
    C -->|Add ')'| E["'(()' (open:2, close:1)"]
    E -->|Add ')'| F["'(())' (open:2, close:2) ✅"]
    
    D -->|Add '('| G["'()(' (open:2, close:1)"]
    G -->|Add ')'| H["'()()' (open:2, close:2) ✅"]

    classDef valid fill:#2e7d32,stroke:#333,stroke-width:2px,color:#fff;
    class F,H valid;
```

---

### Approach

1. **Initialize State Tracker:** Start recursion with an empty string `current = ""`, `openCount = 0`, and `closeCount = 0`.
2. **Base Case:** When `openCount == n` and `closeCount == n`, a complete, valid string of length $2n$ has been formed. Add `current` to the `result` list and return.
3. **Add Opening Parenthesis `(`:** 
   - We are allowed to add `(` as long as `openCount < n`.
   - Increment `openCount` by `1` and recurse.
4. **Add Closing Parenthesis `)`:** 
   - We are allowed to add `)` only when `closeCount < openCount`. This guarantees balance.
   - Increment `closeCount` by `1` and recurse.
5. **Implicit Backtracking:** Because Java `String` concatenation (`current + "("`) creates a new `String` object for each recursive call frame, the call stack automatically handles reverting to the previous state when returning from a recursive branch.

---

### Detailed Code Analysis

- **`generateParenthesis(int n)` (Lines 2–6):**
  - Acts as the entry point. Initializes the result array list `result` and kicks off the recursive process with `backtrack(result, "", 0, 0, n)`.

- **Base Case Check (Lines 9–12):**
  - Checks if both `openCount` and `closeCount` equal `n`. If true, `current` contains $n$ pairs of well-formed parentheses. It appends `current` to `result` and terminates that recursive call frame.

- **Branch 1: Adding Opening Parenthesis (Lines 14–16):**
  - `if (openCount < n)` ensures we never exceed $n$ total open parentheses.
  - `backtrack(result, current + "(", openCount + 1, closeCount, n)` passes a *new string* with `(` appended and increments `openCount`.

- **Branch 2: Adding Closing Parenthesis (Lines 17–19):**
  - `if (closeCount < openCount)` prevents syntax violations like `)(` or `())`. A closing parenthesis is only placed if an unmatched open parenthesis exists.
  - `backtrack(result, current + ")", openCount, closeCount + 1, n)` passes a *new string* with `)` appended and increments `closeCount`.

---

### Code

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, String current, int openCount, int closeCount, int n) {
        // Base case: formed a full valid string of 2 * n length
        if (openCount == n && closeCount == n) {
            result.add(current);
            return;
        }
        
        // Choice 1: Add an open parenthesis if limit 'n' is not reached
        if (openCount < n) {
            backtrack(result, current + "(", openCount + 1, closeCount, n);
        }
        
        // Choice 2: Add a close parenthesis if it won't exceed open parentheses
        if (closeCount < openCount) {
            backtrack(result, current + ")", openCount, closeCount + 1, n);
        }
    }
}
```

---

### Complexity

- **Time Complexity:** $\mathcal{O}\left(\frac{4^n}{\sqrt{n}}\right)$
  - The number of valid combinations generated for a given $n$ is given by the $n$-th **Catalan Number**:
    $$C_n = \frac{1}{n+1} \binom{2n}{n} \approx \frac{4^n}{n \sqrt{\pi n}}$$
  - Since every generated sequence is guaranteed to be valid due to early pruning, the total time spent building valid strings is bounded by the $n$-th Catalan number multiplied by the time taken to copy each string of length $2n$, yielding $\mathcal{O}\left(\frac{4^n}{\sqrt{n}}\right)$.

- **Space Complexity:** $\mathcal{O}(n)$ (excluding output storage)
  - **Call Stack:** The recursion depth reaches at most $2n$ frames (one frame per character in the string), using $\mathcal{O}(n)$ auxiliary space on the execution stack.
  - **Output Space:** Storing the results requires $\mathcal{O}(C_n \cdot n)$ memory space for $C_n$ strings of length $2n$.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you optimize the memory allocation of this solution?
**Answer:** In the current implementation, `current + "("` creates a new `String` object in heap memory at every recursive step. To reduce Garbage Collection overhead and memory allocations:
- Replace `String current` with a single mutable `StringBuilder`.
- Perform explicit backtracking: `sb.append('(')`, recurse, and then pop/truncate via `sb.deleteCharAt(sb.length() - 1)`.

### 2. What is the difference between this approach and Brute-Force generation?
**Answer:** 
- **Brute-Force:** Generates all $2^{2n}$ possible strings of `(` and `)` of length $2n$, then runs a validator function $\mathcal{O}(n)$ on each. Time complexity is $\mathcal{O}(2^{2n} \cdot n)$.
- **Backtracking with Pruning:** Never generates invalid prefixes. It aborts dead-end paths early, cutting down search operations directly to the $n$-th Catalan number $C_n$.