<h2><a href="https://leetcode.com/problems/minimum-common-value">2540. Minimum Common Value</a></h2>

<p>Given two integer arrays <code>nums1</code> and <code>nums2</code>, sorted in non-decreasing order, return <em>the <strong>minimum integer common</strong> to both arrays</em>. If there is no common integer amongst <code>nums1</code> and <code>nums2</code>, return <code>-1</code>.</p>

<p>Note that an integer is said to be <strong>common</strong> to <code>nums1</code> and <code>nums2</code> if both arrays have <strong>at least one</strong> occurrence of that integer.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums1 = [1,2,3], nums2 = [2,4]
<strong>Output:</strong> 2
<strong>Explanation:</strong> The smallest element common to both arrays is 2, so we return 2.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums1 = [1,2,3,6], nums2 = [2,3,4,5]
<strong>Output:</strong> 2
<strong>Explanation:</strong> There are two common elements in the array 2 and 3 out of which 2 is the smallest, so 2 is returned.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums1.length, nums2.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums1[i], nums2[j] &lt;= 10<sup>9</sup></code></li>
	<li>Both <code>nums1</code> and <code>nums2</code> are sorted in <strong>non-decreasing</strong> order.</li>
</ul>


---

# 🛍️ Minimum-Common-Value | Explained

## Approach 1: Two-Pointer Technique (Sorted Arrays)

### Intuition
Imagine two people standing in front of two separate lines of numbered boxes arranged in strictly ascending order. Each person points at the first box in their respective line. 

To find the smallest number that appears in both lines:
- If both people are pointing to the exact same number, that number is guaranteed to be the smallest common value because both lines are sorted and we started from the smallest elements.
- If Person A is pointing to a smaller number than Person B, Person A's number can never match Person B's current number or any number further down Person B's line (since all remaining numbers in Person B's line are even larger). Therefore, Person A must move their finger to the next box.
- Conversely, if Person B's number is smaller, Person B must advance their finger.

This linear scan eliminates unnecessary comparisons without needing extra storage.

### Algorithm Visualized

```mermaid
flowchart TD
    Start([Start: pointer1 = 0, pointer2 = 0]) --> CheckBounds{pointer1 < len(nums1) <br/>AND<br/> pointer2 < len(nums2)?}
    
    CheckBounds -- No --> ReturnNotFound([Return -1])
    CheckBounds -- Yes --> Compare{Compare nums1[pointer1] <br/>and nums2[pointer2]}
    
    Compare -- Equal == --> Found([Return nums1[pointer1]])
    Compare -- nums1[pointer1] < nums2[pointer2] --> IncP1[pointer1 += 1]
    Compare -- nums1[pointer1] > nums2[pointer2] --> IncP2[pointer2 += 1]
    
    IncP1 --> CheckBounds
    IncP2 --> CheckBounds
```

### Approach
1. **Initialize Indices:** Set two pointers, `pointer1` and `pointer2`, both starting at index `0` for `nums1` and `nums2` respectively.
2. **Traverse Both Arrays:** Run a `while` loop that continues as long as both pointers are within the valid index range of their respective arrays (`pointer1 < len(nums1)` and `pointer2 < len(nums2)`).
3. **Compare Values:**
   - If `nums1[pointer1] == nums2[pointer2]`: The smallest common value is found. Return it immediately.
   - If `nums1[pointer1] < nums2[pointer2]`: Increment `pointer1` by `1` to search for a larger value in `nums1`.
   - If `nums1[pointer1] > nums2[pointer2]`: Increment `pointer2` by `1` to search for a larger value in `nums2`.
4. **Fallback:** If either pointer exceeds its array length, no common element exists. Return `-1`.

### Detailed Code Analysis

```python
class Solution(object):
    def getCommon(self, nums1, nums2):
        # Step 1: Initialize pointers to track the current index in each array
        pointer1 = 0
        pointer2 = 0
        
        # Step 2: Loop until one of the pointers runs out of bounds
        while pointer1 < len(nums1) and pointer2 < len(nums2):
            # Step 3a: Check for equality
            if nums1[pointer1] == nums2[pointer2]:
                # Since arrays are sorted, the first match encountered is the minimum
                return nums1[pointer1]
            
            # Step 3b: Advance the pointer holding the smaller value
            elif nums1[pointer1] < nums2[pointer2]:
                pointer1 += 1
            else:
                pointer2 += 1
        
        # Step 4: If loop terminates without a return, no common value exists
        return -1
```

- `pointer1 = 0` and `pointer2 = 0`: Pointers track position without modifying the original input arrays or allocating new data structures.
- `while pointer1 < len(nums1) and pointer2 < len(nums2)`: Guards against `IndexError`. If either pointer reaches the end of its array, no further common elements are possible.
- `if nums1[pointer1] == nums2[pointer2]`: Provides an $O(1)$ early exit the moment the lowest common value is identified.
- `elif nums1[pointer1] < nums2[pointer2]: pointer1 += 1`: Discards the smaller value by stepping forward in `nums1`.
- `else: pointer2 += 1`: Discards the smaller value by stepping forward in `nums2`.
- `return -1`: The safety return triggered when the arrays are disjoint.

### Code
```python
class Solution(object):
    def getCommon(self, nums1, nums2):
        pointer1 = 0
        pointer2 = 0
        while pointer1 < len(nums1) and pointer2 < len(nums2):
            if nums1[pointer1] == nums2[pointer2]:
                return nums1[pointer1]
            elif nums1[pointer1] < nums2[pointer2]:
                pointer1 += 1
            else:
                pointer2 += 1
        return -1
```

### Complexity
- **Time:** $O(N + M)$, where $N$ is the length of `nums1` and $M$ is the length of `nums2`. In the worst-case scenario (e.g., when arrays have no common elements or the common element is at the very end), each iteration increments at least one pointer, leading to at most $N + M$ comparisons.
- **Space:** $O(1)$ auxiliary space. Only two integer variables (`pointer1` and `pointer2`) are allocated in memory, satisfying constant space requirements.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **What if the length of one array is significantly smaller than the other (e.g., $N = 5$ and $M = 10^7$)?**
   - **Answer:** The two-pointer approach takes $O(N + M)$ time, which could iterate through millions of elements in the larger array. Instead, iterate through the smaller array and perform **Binary Search** on the larger array for each element. This achieves a time complexity of $O(N \log M)$, which is significantly faster when $N \ll M$.

2. **What if the input arrays are not sorted?**
   - **Answer:** If the arrays are unsorted, convert the smaller array into a **Hash Set** in $O(\min(N, M))$ space and time, then iterate through the other array to find the minimum match. This yields $O(N + M)$ average time and $O(\min(N, M))$ space. Alternatively, sorting both arrays first would take $O(N \log N + M \log M)$ time and $O(1)$ extra space.