<h2><a href="https://leetcode.com/problems/detect-capital">520. Detect Capital</a></h2>

<p>We define the usage of capitals in a word to be right when one of the following cases holds:</p>

<ul>
	<li>All letters in this word are capitals, like <code>"USA"</code>.</li>
	<li>All letters in this word are not capitals, like <code>"leetcode"</code>.</li>
	<li>Only the first letter in this word is capital, like <code>"Google"</code>.</li>
</ul>

<p>Given a string <code>word</code>, return <code>true</code> if the usage of capitals in it is right.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> word = "USA"
<strong>Output:</strong> true
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> word = "FlaG"
<strong>Output:</strong> false
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= word.length &lt;= 100</code></li>
	<li><code>word</code> consists of lowercase and uppercase English letters.</li>
</ul>


---

# 🛍️ Detect-Capital | Explained

## Approach 1: Uppercase Counter Method

### Intuition
Imagine a grammar checker standardizing capitalization rules in a text editor. According to standard typography, a valid word must strictly adhere to one of three formatting rules:
1. **ALL CAPS** (e.g., `"USA"`)
2. **all lowercase** (e.g., `"leetcode"`)
3. **Title Case** (e.g., `"Google"`)

Instead of writing complex string matching logic or regular expressions, we can simply count the total number of uppercase letters in the word. Once we know the total count, checking whether the word satisfies one of the three valid formats becomes a simple set of logical conditions.

### Algorithm Visualized

```mermaid
graph TD
    A[Start: Input word] --> B[Initialize uppercase = 0]
    B --> C[Loop i from 0 to n-1]
    C --> D{Is word[i] Uppercase?}
    D -- Yes --> E[uppercase++]
    D -- No --> F[Next Iteration]
    E --> F
    F --> G{Reached End of Word?}
    G -- No --> C
    G -- Yes --> H{Evaluate Rules}
    H -- uppercase == n --> I[Valid: All Uppercase]
    H -- uppercase == 0 --> J[Valid: All Lowercase]
    H -- uppercase == 1 AND word[0] is Upper --> K[Valid: Title Case]
    H -- Otherwise --> L[Invalid Capitalization]
    I --> M[Return true]
    J --> M
    K --> M
    L --> N[Return false]
```

### Approach
1. **Count Uppercase Characters**: Iterate through every character in the string `word` using a simple loop. Increment an `uppercase` counter whenever `Character.isUpperCase()` evaluates to `true`.
2. **Apply Validation Rules**: Evaluate the final count against the three valid rules:
   - **Rule 1 (All Uppercase)**: The number of uppercase letters equals the string length (`uppercase == n`).
   - **Rule 2 (All Lowercase)**: The number of uppercase letters is zero (`uppercase == 0`).
   - **Rule 3 (Title Case)**: The number of uppercase letters is exactly one (`uppercase == 1`), **AND** that single uppercase letter is at index 0 (`Character.isUpperCase(word.charAt(0))`).
3. If any of these three conditions evaluate to `true`, the word uses capitals correctly. Otherwise, return `false`.

### Detailed Code Analysis

```java
1class Solution {
2    public boolean detectCapitalUse(String word) {
3        int n = word.length();
4        int uppercase = 0;
```
- **Lines 3–4**: We extract the string length `n` to avoid calling `.length()` repeatedly inside loop conditions. We also initialize `uppercase` to track the count of uppercase characters found.

```java
5        for(int i = 0; i < n; i++) {
6            if(Character.isUpperCase(word.charAt(i))) {
7                uppercase++;
8            }
9        }
```
- **Lines 5–9**: Standard single-pass loop traversing from index `0` to `n - 1`. `word.charAt(i)` retrieves the character at index `i`. `Character.isUpperCase(...)` checks if the character is an ASCII uppercase letter (`'A'` through `'Z'`). If true, `uppercase` is incremented.

```java
10       return uppercase == n || uppercase == 0 || (uppercase == 1 && Character.isUpperCase(word.charAt(0)));    
11   }
12}
```
- **Line 10**: The core boolean decision logic combining all three valid capital usage cases via logical OR (`||`) operators:
  - `uppercase == n`: Handles `"USA"`, `"LEETCODE"`.
  - `uppercase == 0`: Handles `"leetcode"`, `"coding"`.
  - `(uppercase == 1 && Character.isUpperCase(word.charAt(0)))`: Handles `"Google"`, `"FlaG" -> false` (because for `"FlaG"`, `uppercase == 2`, failing all conditions).

### Code

```java
class Solution {
    public boolean detectCapitalUse(String word) {
        int n = word.length();
        int uppercase = 0;
        
        for (int i = 0; i < n; i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                uppercase++;
            }
        }
        
        return uppercase == n || uppercase == 0 || (uppercase == 1 && Character.isUpperCase(word.charAt(0)));    
    }
}
```

### Complexity

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the length of `word`. We perform a single full pass over the string of length $N$, inspecting each character exactly once.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. We only use two scalar integer variables (`n` and `uppercase`), consuming constant memory regardless of the input size.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

### 1. How can we optimize this solution to early-exit without processing the full string every time?
**Answer:** Instead of counting all uppercase characters first, we can check early termination conditions starting from index 1:
- If the first letter is lowercase (`word.charAt(0)` is lowercase), then **all** subsequent letters MUST be lowercase. If we encounter any uppercase letter starting at index 1, immediately return `false`.
- If the first letter is uppercase, the second letter determines the pattern:
  - If the 2nd letter is uppercase, all remaining characters must be uppercase.
  - If the 2nd letter is lowercase, all remaining characters must be lowercase.

This allows us to abort early on inputs like `"FlaG"` or `"aBC"` after checking just the second or third character.

### 2. How would you solve this using Regular Expressions in Java?
**Answer:** A single regex pattern matching the logic is `word.matches("[A-Z]+|[a-z]+|[A-Z][a-z]*")`.
- `[A-Z]+`: All uppercase.
- `[a-z]+`: All lowercase.
- `[A-Z][a-z]*`: One uppercase followed by zero or more lowercase letters.

*Note: While concise, regex compiling and matching introduces extra runtime overhead and non-constant memory overhead compared to direct character inspection.*