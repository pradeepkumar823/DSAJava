<h2><a href="https://leetcode.com/problems/move-zeroes">283. Move Zeroes</a></h2>

<p>Given an integer array <code>nums</code>, move all <code>0</code>'s to the end of it while maintaining the relative order of the non-zero elements.</p>

<p><strong>Note</strong> that you must do this in-place without making a copy of the array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> nums = [0,1,0,3,12]
<strong>Output:</strong> [1,3,12,0,0]
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> nums = [0]
<strong>Output:</strong> [0]
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>4</sup></code></li>
	<li><code>-2<sup>31</sup> &lt;= nums[i] &lt;= 2<sup>31</sup> - 1</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow up:</strong> Could you minimize the total number of operations done?

---

# 🛍️ Move-Zeroes | Explained

## Approach 1: Two-Pointer Overwrite with Zero Filling (Two-Pass)

### Intuition
Imagine a bookshelf filled with books interspersed with empty dummy display blocks (zeros). Instead of swapping books back and forth repeatedly, a librarian walks down the shelf from left to right. Every time they find a real book, they move it to the leftmost available slot. Once all real books are compressed to the front in their original relative order, the librarian fills the rest of the shelf on the right with empty dummy blocks.

This approach uses two pointers: a read pointer (`i`) that scans the entire array and a write pointer (`j`) that tracks the next available position for a non-zero number.

### Algorithm Visualized

```mermaid
graph TD
    Start([Start: Input Array nums]) --> Init[Initialize j = 0]
    Init --> Pass1Loop{Loop: i = 0 to nums.length - 1}
    
    Pass1Loop -- i < nums.length --> CheckVal{nums[i] != 0 ?}
    CheckVal -- Yes --> Overwrite[nums[j] = nums[i]<br/>Increment j++]
    CheckVal -- No --> NextI[Increment i++]
    Overwrite --> NextI
    NextI --> Pass1Loop
    
    Pass1Loop -- i >= nums.length --> Pass2Loop{Loop: j < nums.length}
    
    Pass2Loop -- Yes --> FillZero[nums[j] = 0<br/>Increment j++]
    FillZero --> Pass2Loop
    
    Pass2Loop -- No --> End([End: Array Modified In-Place])
```

### Approach
1. Initialize a write pointer `j = 0` to track the placement index for non-zero elements.
2. **First Pass (Read & Move Non-Zeros):** Iterate through the array with pointer `i` from `0` to `nums.length - 1`.
   - If `nums[i]` is non-zero, write `nums[i]` to `nums[j]` and increment `j`.
   - If `nums[i]` is zero, do nothing and proceed to the next element.
3. **Second Pass (Fill Remaining with Zeros):** After the first pass, all non-zero elements occupy indices `0` through `j - 1`.
   - Run a loop starting from index `j` up to `nums.length - 1`, setting each `nums[j]` to `0`.

### Detailed Code Analysis

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int j = 0; // Initialize write-pointer 'j' at the start of the array
        
        // Pass 1: Compact non-zero elements at the beginning of the array
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i]; // Copy non-zero element to the write index
                j++;                // Advance the write pointer
            }
        }
        
        // Pass 2: Fill the rest of the array (from index j to the end) with zeroes
        while (j < nums.length) {
            nums[j] = 0; // Assign zero to remaining slots
            j++;         // Advance pointer until the end of the array
        }
    }
}
```

- **Line 3 (`int j=0;`)**: Serves as the write boundary pointer. At any point during Pass 1, `j` holds the count of non-zero elements encountered so far and points to the position where the next non-zero element should go.
- **Line 4-9 (`for loop`)**: Iterates through every element using index `i`.
  - **Line 5 (`if(nums[i]!=0)`)**: Filters out zero elements.
  - **Line 6-7 (`nums[j]=nums[i]; j++;`)**: Overwrites `nums[j]` with the valid non-zero value and increments `j`. This guarantees that the relative order of non-zero elements is preserved.
- **Line 10-13 (`while loop`)**: Begins execution once `i` finishes scanning.
  - If `j` is less than `nums.length`, it means there were zeros in the array. All indices from `j` to `nums.length - 1` are set to `0`.

### Code
```java
class Solution {
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                j++;
            }
        }
        while (j < nums.length) {
            nums[j] = 0;
            j++;
        }
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of elements in `nums`.
  - The first `for` loop executes $N$ times.
  - The second `while` loop executes $N - j$ times (at most $N$ times).
  - Overall time complexity simplifies to $\mathcal{O}(N + N) = \mathcal{O}(N)$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.
  - No additional data structures are created; all modifications occur in-place using two scalar integer variables (`i` and `j`).

---

## 🕵️‍♂️ Follow-up Questions

### 1. How can you optimize this to minimize the total number of write operations (Single-Pass Optimal Solution)?
**Answer:**
In the two-pass approach, if an array contains mostly non-zero elements (e.g., `[1, 2, 3, 4, 0]`), every non-zero element gets rewritten to its own current position, resulting in redundant writes.

We can optimize to a **single-pass swap approach**:
Instead of overwriting and zero-filling later, swap `nums[i]` and `nums[j]` whenever `nums[i] != 0`.

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != j) { // Avoid unnecessary swaps with itself
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
                j++;
            }
        }
    }
}
```
*Why this matters:* If an array has very few zeros (e.g., millions of non-zero numbers and 1 zero at the end), this reduces array write operations significantly.

### 2. What if array writes are extremely expensive (e.g., Flash Memory / NVMe SSD writes)?
**Answer:**
The standard swap operation performs two writes per non-zero element (`nums[i]` and `nums[j]`). The two-pass solution provided in your initial code performs fewer total writes when the array contains mostly zeros, because setting contiguous zeros in the second pass can be vectorized/optimized by standard runtime engines (like `Arrays.fill`). Understanding the balance between read/write costs guides whether you choose two-pass overwrite or single-pass swap.