<h2><a href="https://leetcode.com/problems/number-of-segments-in-a-string">434. Number of Segments in a String</a></h2>

<p>Given a string <code>s</code>, return <em>the number of segments in the string</em>.</p>

<p>A <strong>segment</strong> is defined to be a contiguous sequence of <strong>non-space characters</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre><strong>Input:</strong> s = "Hello, my name is John"
<strong>Output:</strong> 5
<strong>Explanation:</strong> The five segments are ["Hello,", "my", "name", "is", "John"]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre><strong>Input:</strong> s = "Hello"
<strong>Output:</strong> 1
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>0 &lt;= s.length &lt;= 300</code></li>
	<li><code>s</code> consists of lowercase and uppercase English letters, digits, or one of the following characters <code>"!@#$%^&amp;*()_+-=',.:"</code>.</li>
	<li>The only space character in <code>s</code> is <code>' '</code>.</li>
</ul>


---

# 🛍️ Number-of-Segments-in-a-String | Explained

## Approach 1: Single-Pass Boundary Detection

### Intuition
Imagine walking along a train track where train cars (words) are separated by stretches of empty track (spaces). To count the total number of train cars, you don't need to walk through every single car completely and keep track of its length. Instead, you simply count every time your foot steps onto the **front edge** of a train car—either because you started your walk directly on one, or because you just stepped off an empty stretch of track onto a car. 

By detecting only the transitions from whitespace to a non-whitespace character (the "start" of a segment), we can accurately count segments in a single pass without creating temporary objects or modifying the string.

### Algorithm Visualized

```mermaid
graph TD
    Start([Start Iteration at index i]) --> CheckChar{s.charAt\(i\) != ' '}
    CheckChar -- No (Is Space) --> Next[Move to next character i++]
    CheckChar -- Yes (Non-Space) --> CheckBoundary{i == 0 OR<br>s.charAt\(i-1\) == ' '}
    CheckBoundary -- Yes (Start of Segment) --> Increment[count++]
    CheckBoundary -- No (Inside Segment) --> Next
    Increment --> Next
    Next --> EndCheck{i < s.length\(\)}
    EndCheck -- Yes --> Start
    EndCheck -- No --> Return([Return count])
```

### Approach
1. Initialize an integer variable `count = 0` to track the total number of segments.
2. Iterate through the string character by character from index `i = 0` to `s.length() - 1`.
3. For each position `i`, check if it marks the **beginning** of a new segment. A position is the start of a segment if:
   - The current character is **not** a space (`s.charAt(i) != ' '`).
   - **AND** either:
     - It is the very first character of the string (`i == 0`).
     - The preceding character was a space (`s.charAt(i - 1) == ' '`).
4. If both conditions are satisfied, increment `count`.
5. After the loop completes, return `count`.

### Detailed Code Analysis

```java
class Solution {
    public int countSegments(String s) {

        int count = 0; // Accumulator variable to keep track of segment starts

        // Iterate through each index of the string s
        for(int i = 0; i < s.length(); i++) {

            // Condition 1: Check if current character is non-whitespace
            // Condition 2 (Short-circuited): Check if current char is at string start (i == 0)
            //               OR if the immediately preceding char was whitespace
            if(s.charAt(i) != ' ' &&
               (i == 0 || s.charAt(i - 1) == ' ')) {

                count++; // Valid start of a segment detected
            }
        }

        return count; // Total segment count
    }
}
```

- **`int count = 0;`**: Initializes the counter to keep track of valid segments without requiring dynamic memory allocations.
- **`for(int i = 0; i < s.length(); i++)`**: Executes a standard linear scan over the string.
- **`s.charAt(i) != ' '`**: Filters out space characters immediately. If a character is a space, it cannot be the start of a segment.
- **`i == 0 || s.charAt(i - 1) == ' '`**: Uses Java's short-circuit `||` operator:
  - If `i == 0`, the left operand is true, avoiding an out-of-bounds evaluation for `s.charAt(i - 1)`.
  - If `i > 0`, it safely checks if the previous character was a space (`' '`), confirming a transition from whitespace to non-whitespace.
- **`count++;`**: Increments the total count only at the boundary start of each segment.

### Code

```java
class Solution {
    public int countSegments(String s) {

        int count = 0;

        for(int i = 0; i < s.length(); i++) {

            if(s.charAt(i) != ' ' &&
               (i == 0 || s.charAt(i - 1) == ' ')) {

                count++;
            }
        }

        return count;
    }
}
```

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of the string `s`. The algorithm inspects each character in the string at most twice (current character and previous character via `charAt`), leading to a linear time scan.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. The algorithm only uses a single integer counter (`count`) and loop pointer (`i`), requiring constant extra space regardless of string size.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

### 1. Why is this approach preferred over built-in string splitting methods like `s.trim().split("\\s+")`?
Using `split()` or regular expressions incurs significant overhead:
- **Memory Allocation:** `split()` creates an array of substrings and allocates new `String` objects on the heap, leading to $\mathcal{O}(N)$ auxiliary memory usage and garbage collection pressure.
- **Performance:** Regular expression parsing adds CPU overhead. 
- **Edge Cases:** Calling `.split()` on an empty or space-only string can produce unexpected arrays containing empty strings (e.g., `[""]`), requiring extra conditional guards. The manual pointer approach runs in $\mathcal{O}(1)$ space and handles edge cases natively.

### 2. How would you extend this logic if segments can be separated by any whitespace character (spaces, tabs `\t`, newlines `\n`)?
Instead of hardcoding character checks for `' '`, you can replace the space checks with Java's built-in `Character.isWhitespace(char ch)` method:

```java
if (!Character.isWhitespace(s.charAt(i)) &&
   (i == 0 || Character.isWhitespace(s.charAt(i - 1)))) {
    count++;
}
```