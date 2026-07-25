<h2><a href="https://leetcode.com/problems/integer-to-roman">12. Integer to Roman</a></h2>

<p>Seven different symbols represent Roman numerals with the following values:</p>

<table><thead><tr><th>Symbol</th><th>Value</th></tr></thead><tbody><tr><td>I</td><td>1</td></tr><tr><td>V</td><td>5</td></tr><tr><td>X</td><td>10</td></tr><tr><td>L</td><td>50</td></tr><tr><td>C</td><td>100</td></tr><tr><td>D</td><td>500</td></tr><tr><td>M</td><td>1000</td></tr></tbody></table>

<p>Roman numerals are formed by appending&nbsp;the conversions of&nbsp;decimal place values&nbsp;from highest to lowest. Converting a decimal place value into a Roman numeral has the following rules:</p>

<ul>
	<li>If the value does not start with 4 or&nbsp;9, select the symbol of the maximal value that can be subtracted from the input, append that symbol to the result, subtract its value, and convert the remainder to a Roman numeral.</li>
	<li>If the value starts with 4 or 9 use the&nbsp;<strong>subtractive form</strong>&nbsp;representing&nbsp;one symbol subtracted from the following symbol, for example,&nbsp;4 is 1 (<code>I</code>) less than 5 (<code>V</code>): <code>IV</code>&nbsp;and 9 is 1 (<code>I</code>) less than 10 (<code>X</code>): <code>IX</code>.&nbsp;Only the following subtractive forms are used: 4 (<code>IV</code>), 9 (<code>IX</code>),&nbsp;40 (<code>XL</code>), 90 (<code>XC</code>), 400 (<code>CD</code>) and 900 (<code>CM</code>).</li>
	<li>Only powers of 10 (<code>I</code>, <code>X</code>, <code>C</code>, <code>M</code>) can be appended consecutively at most 3 times to represent multiples of 10. You cannot append 5&nbsp;(<code>V</code>), 50 (<code>L</code>), or 500 (<code>D</code>) multiple times. If you need to append a symbol&nbsp;4 times&nbsp;use the <strong>subtractive form</strong>.</li>
</ul>

<p>Given an integer, convert it to a Roman numeral.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">num = 3749</span></p>

<p><strong>Output:</strong> <span class="example-io">"MMMDCCXLIX"</span></p>

<p><strong>Explanation:</strong></p>

<pre>3000 = MMM as 1000 (M) + 1000 (M) + 1000 (M)
 700 = DCC as 500 (D) + 100 (C) + 100 (C)
  40 = XL as 10 (X) less of 50 (L)
   9 = IX as 1 (I) less of 10 (X)
Note: 49 is not 1 (I) less of 50 (L) because the conversion is based on decimal places
</pre>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">num = 58</span></p>

<p><strong>Output:</strong> <span class="example-io">"LVIII"</span></p>

<p><strong>Explanation:</strong></p>

<pre>50 = L
 8 = VIII
</pre>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">num = 1994</span></p>

<p><strong>Output:</strong> <span class="example-io">"MCMXCIV"</span></p>

<p><strong>Explanation:</strong></p>

<pre>1000 = M
 900 = CM
  90 = XC
   4 = IV
</pre>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= num &lt;= 3999</code></li>
</ul>


---

# 🛍️ Integer-to-Roman | Explained

## Approach 1: Unrolled Greedy Reduction with Fixed Primitive Buffer

### Intuition
Converting an integer to a Roman numeral is conceptually identical to making monetary change with the fewest possible coins/bills using standard denominations. You always want to use the largest denomination possible before moving to smaller ones. 

Because Roman numerals feature additive symbols (`M`=1000, `D`=500, `C`=100, `L`=50, `X`=10, `V`=5, `I`=1) and subtractive pair symbols (`CM`=900, `CD`=400, `XC`=90, `XL`=40, `IX`=9, `IV`=4), we can treat all 13 unique symbol representations as discrete "denominations" sorted in descending order. By manually unrolling the evaluation of these 13 cases from highest to lowest value, we systematically subtract values from `num` while building the result character by character.

### Algorithm Visualized

```mermaid
flowchart TD
    A[Start: Input num] --> B{num >= 1000?}
    B -- Yes --> C[Append 'M', num -= 1000] --> B
    B -- No --> D{num >= 900?}
    D -- Yes --> E[Append 'CM', num -= 900] --> F
    D -- No --> F{num >= 500?}
    F -- Yes --> G[Append 'D', num -= 500] --> H
    F -- No --> H{num >= 400?}
    H -- Yes --> I[Append 'CD', num -= 400] --> J
    H -- No --> J[Process Hundreds: 100, 90, 50, 40]
    J --> K[Process Tens & Ones: 10, 9, 5, 4, 1]
    K --> L[Convert char buffer c[0..i] to String]
    L --> M([Return Result])
```

### Approach
1. **Buffer Allocation**: Allocate a `char` array of size `15` and a write index pointer `i = 0`. The maximum possible length of a Roman numeral under LeetCode constraints ($1 \le \text{num} \le 3999$) is **15 characters** (specifically for 3888: `MMMDCCCLXXXVIII`).
2. **Greedy Thousands Reduction**: Loop while `num >= 1000`, writing `'M'` into array `c` and decrementing `num` by 1000.
3. **Subtractive & Major Value Evaluation**: Use conditional checks (`if`) for single-instance subtractive forms (`900`, `400`, `90`, `40`, `9`, `4`) and single-instance major forms (`500`, `50`, `5`), writing their corresponding character pairs or single characters into `c`.
4. **Iterative Minor Value Evaluation**: Use `while` loops for repeating base values (`100`, `10`, `1`), appending characters until `num` drops below that denomination threshold.
5. **String Construction**: Construct a `String` from `c` strictly spanning from index `0` to `i`, bypassing any overhead associated with dynamic resizing data structures like `StringBuilder`.

### Detailed Code Analysis

```java
class Solution {
    public String intToRoman(int num) {
        // Allocate a fixed-size char buffer. 15 is the upper bound length 
        // for any Roman numeral representation of num <= 3999 (e.g., 3888 -> "MMMDCCCLXXXVIII").
        char[] c = new char[15];
        int i = 0; // Pointer tracking the next write position in the array.
        
        // --- THOUSANDS PLACE (1000s and 900) ---
        while (num >= 1000) { c[i++] = 'M'; num -= 1000; }
        if (num >= 900) { c[i++] = 'C'; c[i++] = 'M'; num -= 900; }
        
        // --- FIVE-HUNDREDS AND FOUR-HUNDREDS (500s and 400) ---
        if (num >= 500) { c[i++] = 'D'; num -= 500; }
        if (num >= 400) { c[i++] = 'C'; c[i++] = 'D'; num -= 400; }
        
        // --- HUNDREDS PLACE (100s and 90) ---
        while (num >= 100) { c[i++] = 'C'; num -= 100; }
        if (num >= 90) { c[i++] = 'X'; c[i++] = 'C'; num -= 90; }
        
        // --- FIFTIES AND FORTIES (50s and 40) ---
        if (num >= 50) { c[i++] = 'L'; num -= 50; }
        if (num >= 40) { c[i++] = 'X'; c[i++] = 'L'; num -= 40; }
        
        // --- TENS PLACE (10s and 9) ---
        while (num >= 10) { c[i++] = 'X'; num -= 10; }
        if (num >= 9) { c[i++] = 'I'; c[i++] = 'X'; num -= 9; }
        
        // --- FIVES AND FOURS (5s and 4) ---
        if (num >= 5) { c[i++] = 'V'; num -= 5; }
        if (num >= 4) { c[i++] = 'I'; c[i++] = 'V'; num -= 4; }
        
        // --- ONES PLACE (1s) ---
        while (num >= 1) { c[i++] = 'I'; num -= 1; }
        
        // Create a new String referencing only the written slice of the buffer [0, i).
        return new String(c, 0, i);
    }
}
```

### Code
```java
class Solution {
    public String intToRoman(int num) {
        char[] c = new char[15];
        int i = 0;
        
        while (num >= 1000) { c[i++] = 'M'; num -= 1000; }
        if (num >= 900) { c[i++] = 'C'; c[i++] = 'M'; num -= 900; }
        if (num >= 500) { c[i++] = 'D'; num -= 500; }
        if (num >= 400) { c[i++] = 'C'; c[i++] = 'D'; num -= 400; }
        
        while (num >= 100) { c[i++] = 'C'; num -= 100; }
        if (num >= 90) { c[i++] = 'X'; c[i++] = 'C'; num -= 90; }
        if (num >= 50) { c[i++] = 'L'; num -= 50; }
        if (num >= 40) { c[i++] = 'X'; c[i++] = 'L'; num -= 40; }
        
        while (num >= 10) { c[i++] = 'X'; num -= 10; }
        if (num >= 9) { c[i++] = 'I'; c[i++] = 'X'; num -= 9; }
        if (num >= 5) { c[i++] = 'V'; num -= 5; }
        if (num >= 4) { c[i++] = 'I'; c[i++] = 'V'; num -= 4; }
        
        while (num >= 1) { c[i++] = 'I'; num -= 1; }
        
        return new String(c, 0, i);
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(1)$  
  Because the input range is bounded ($1 \le \text{num} \le 3999$), the total number of operations (loop checks, array assignments, and subtractions) is strictly capped by a small constant. The maximum possible number of loop iterations across all blocks is 15.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space  
  The implementation allocates a fixed primitive `char[15]` array on the stack/heap memory, which does not scale with input. The output `String` of maximum length 15 also requires $\mathcal{O}(1)$ space.

---

## 🕵️‍♂️ Follow-up Questions

### 1. Why use a fixed primitive array `char[15]` over `StringBuilder`?
**Answer:** `StringBuilder` defaults to an initial capacity of 16 characters, but incurs class abstraction overhead, method invocation costs (`append()`), and internal boundary/capacity checks. By allocating a stack-allocated primitive `char[15]` array, we eliminate object initialization overheads and direct index assignments (`c[i++] = ...`) execute as minimal CPU instructions.

### 2. How can this solution be generalized if symbol mappings change dynamically?
**Answer:** The current solution hardcodes the logic flow for performance (loop unrolling). To make it maintainable for configurable maps, we can store values and symbols in parallel arrays or lookup tables:
```java
int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
```
We then iterate through the `values` array in a clean double loop, reducing `num` systematically while appending to a string builder or character buffer.