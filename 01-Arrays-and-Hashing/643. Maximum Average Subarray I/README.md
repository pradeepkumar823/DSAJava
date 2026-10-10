<h2><a href="https://leetcode.com/problems/maximum-average-subarray-i">643. Maximum Average Subarray I</a></h2>

<p>You are given an integer array <code>nums</code> consisting of <code>n</code> elements, and an integer <code>k</code>.</p>

<p>Find a contiguous subarray whose <strong>length is equal to</strong> <code>k</code> that has the maximum average value and return <em>this value</em>. Any answer with a calculation error less than <code>10<sup>-5</sup></code> will be accepted.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> nums = [1,12,-5,-6,50,3], k = 4
<strong>Output:</strong> 12.75000
<strong>Explanation:</strong> Maximum average is (12 - 5 - 6 + 50) / 4 = 51 / 4 = 12.75
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> nums = [5], k = 1
<strong>Output:</strong> 5.00000
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == nums.length</code></li>
	<li><code>1 &lt;= k &lt;= n &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>4</sup> &lt;= nums[i] &lt;= 10<sup>4</sup></code></li>
</ul>


---

# 🛍️ Maximum-Average-Subarray-I | Explained

## Approach 1: Fixed-Size Sliding Window
### Intuition
Imagine you are looking at a train through a fixed window frame that only shows $k$ passengers at any given time. As the train moves forward by one seat, you don't need to count all $k$ passengers again from scratch. You simply subtract the passenger who just moved out of sight on the left and add the new passenger who just appeared on the right. 

Furthermore, because the window size $k$ is fixed, the window with the **maximum average** is strictly the window with the **maximum sum** ($\text{Average} = \frac{\text{Sum}}{k}$). Dividing inside the loop would introduce expensive floating-point arithmetic and precision issues; keeping it as an integer sum until the very end is both faster and mathematically cleaner.

### Algorithm Visualized
```mermaid
flowchart LR
    subgraph Initial Window [Step 1: Compute First Window of Size k]
        A["[ nums[0] + nums[1] + ... + nums[k-1] ]"] --> S1["sum = Initial Sum"]
        S1 --> M1["maxValue = sum"]
    end

    subgraph Slide Window [Step 2: Slide from index k to n - 1]
        direction TB
        W["Current Window: sum"] --> Sub["Subtract Outgoing: sum - nums[i - k]"]
        Sub --> Add["Add Incoming: + nums[i]"]
        Add --> Up["maxValue = max(maxValue, sum)"]
    end

    subgraph Final Result [Step 3: Convert to Average]
        Fin["return (double) maxValue / k"]
    end

    Initial Window --> Slide Window --> Final Result
```

### Approach
1. **Initialize First Window:** Calculate the sum of the first $k$ elements (indices `0` to `k - 1`). Store this value in `sum` and initialize `maxValue` to `sum`.
2. **Slide the Window:** Iterate through the rest of the array starting from index `i = k` up to `nums.length - 1`:
   - Subtract the element leaving the window from the left: `nums[i - k]`.
   - Add the element entering the window from the right: `nums[i]`.
   - Update `maxValue` to keep track of the largest window sum encountered so far using `Math.max()`.
3. **Compute Final Average:** Cast `maxValue` to `double` and divide by `k` to obtain the maximum average.

### Detailed Code Analysis
- **Lines 3–4:** 
  ```java
  int maxValue = Integer.MIN_VALUE;
  int sum = 0;
  ```
  Initializes `sum` to accumulate the window's elements. Initializing `maxValue` to `Integer.MIN_VALUE` ensures that any valid window sum (even negative values) will overwrite it, though it is immediately reassigned on line 8.
- **Lines 5–8:** 
  ```java
  for(int i = 0; i < k; i++){
      sum += nums[i];
  }
  maxValue = sum;
  ```
  Populates the first window of length $k$. Setting `maxValue = sum` sets the baseline comparison for all subsequent windows.
- **Lines 10–13:** 
  ```java
  for(int i = k; i < nums.length; i++){
      sum = sum - nums[i - k] + nums[i];
      maxValue = Math.max(maxValue, sum);
  }
  ```
  Iterates from index `k` to the end of the array. At every iteration `i`:
  - `nums[i - k]` represents the leftmost element of the previous window that is now out of scope.
  - `nums[i]` represents the new element sliding into the window.
  - `sum` is updated in $O(1)$ time, and `maxValue` is updated if the new window's sum exceeds the previous best.
- **Line 15:** 
  ```java
  return (double) maxValue / k;
  ```
  The explicit cast `(double) maxValue` is critical. In Java, dividing two integers (`maxValue / k`) performs integer division (truncating decimals). Casting to `double` ensures standard floating-point division.

### Code
```java
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int maxValue = Integer.MIN_VALUE;
        int sum = 0;
        
        // Step 1: Calculate the sum of the first window of size k
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        maxValue = sum;

        // Step 2: Slide the window across the remaining array
        for (int i = k; i < nums.length; i++) {
            sum = sum - nums[i - k] + nums[i];
            maxValue = Math.max(maxValue, sum);
        }
       
        // Step 3: Compute and return the maximum average
        return (double) maxValue / k;
    }
}
```

### Complexity
- **Time:** $O(n)$ — The algorithm iterates through the first $k$ elements, and then iterates through the remaining $n - k$ elements. Every element is visited at most twice (once added, once subtracted), resulting in a linear runtime.
- **Space:** $O(1)$ — Only two scalar variables (`maxValue`, `sum`) are maintained throughout the execution. No auxiliary heap memory is allocated.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **What happens if $k$ and values in `nums` are very large (potential integer overflow)?**
   - In LeetCode 643, $n \le 10^5$ and $-10^4 \le nums[i] \le 10^4$. The maximum sum could theoretically reach $10^5 \times 10^4 = 10^9$, which fits within a 32-bit signed integer (`Integer.MAX_VALUE` $\approx 2.14 \times 10^9$). However, if the constraints were larger (e.g., values up to $10^5$), an integer overflow would occur. In that scenario, `sum` and `maxValue` must be declared as `long`.

2. **What if the problem asked for the maximum average subarray of length *at least* $k$ (variable length)?**
   - This shifts the problem from fixed sliding window to LeetCode 644 (Hard). It cannot be solved with a standard $O(n)$ fixed-size sliding window. Instead, it is solved using **Binary Search on the Answer** combined with prefix sums in $O(n \log(\text{range}))$, or convex hull trick optimizations.