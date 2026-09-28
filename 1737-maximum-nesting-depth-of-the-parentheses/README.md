<h2><a href="https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses">Maximum Nesting Depth of the Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given a <strong>valid parentheses string</strong> <code>s</code>, return the <strong>nesting depth</strong> of<em> </em><code>s</code>. The nesting depth is the <strong>maximum</strong> number of nested parentheses.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;(1+(2*3)+((8)/4))+1&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>Digit 8 is inside of 3 nested parentheses in the string.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;(1)+((2))+(((3)))&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>Digit 3 is inside of 3 nested parentheses in the string.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;()(())((()()))&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 100</code></li>
	<li><code>s</code> consists of digits <code>0-9</code> and characters <code>&#39;+&#39;</code>, <code>&#39;-&#39;</code>, <code>&#39;*&#39;</code>, <code>&#39;/&#39;</code>, <code>&#39;(&#39;</code>, and <code>&#39;)&#39;</code>.</li>
	<li>It is guaranteed that parentheses expression <code>s</code> is a VPS.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The maximum nesting depth of parentheses is determined by tracking the current level of open parentheses as you iterate through the string character by character. 

Every time an opening parenthesis `'('` is encountered, the current nesting depth increases by 1, and we check if a new maximum depth has been reached. Every time a closing parenthesis `')'` is encountered, the current depth decreases by 1. Any other characters (digits, mathematical operators) do not affect parentheses depth and are skipped.

### 🔍 Approach

1. **State Initialization**:
   - `depth`: tracks the current nesting depth level (initialized to `0`).
   - `r`: stores the maximum depth encountered so far (the final result, initialized to `0`).

2. **Iterate through the string**:
   - Convert the string `s` into a character array via `s.toCharArray()` and loop over each character `c`:
     - **If `c == ')'`**: Decrement `depth` by 1 (`depth--`) and immediately `continue` to the next character.
     - **If `c` is not `'('`**: Skip it using `continue` (this filters out digits and operators like `+`, `-`, `*`, `/`).
     - **If `c == '('`**: Increment `depth` by 1 (`depth++`). Then, if `depth` exceeds `r`, update `r` to the new `depth`.

3. **Return Result**:
   - After processing all characters, `r` holds the maximum nesting depth recorded during the traversal. Return `r`.

### 🧩 Algorithm

- **Variables**:
  - `depth`: current depth count.
  - `r`: running maximum of `depth`.

- **Invariant**:
  - At any point during traversal, `depth` equals the number of unclosed opening parentheses `(` seen so far.
  - `r` always holds $\max(\text{all } depth \text{ values seen so far})$.

- **State Transitions**:
  - When `c == ')'`: `depth` $\leftarrow$ `depth - 1`
  - When `c == '('`: `depth` $\leftarrow$ `depth + 1`, `r` $\leftarrow \max(r, depth)$
  - Otherwise: no state change

### ✅ Why This Works

Because the input string is guaranteed to be a Valid Parentheses String (VPS), `depth` will never drop below `0` and every opening parenthesis will eventually be closed. 

Nesting depth at any point in the string is precisely equal to the count of open, unclosed parentheses preceding that point. By updating `r` whenever `depth` increases (on encountering `'('`), `r` is guaranteed to capture the highest peak depth reached throughout the entire string traversal.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of the string `s`. The code processes each character in the string once in a single loop. Converting the string to a character array (`s.toCharArray()`) also takes $\mathcal{O}(n)$ time.
- **Space Complexity:** $\mathcal{O}(n)$ auxiliary space created by `s.toCharArray()`, which allocates a new array of size $n$. The counter variables `depth` and `r` use $\mathcal{O}(1)$ space.

### 🧠 DSA Pattern

- **Stack / Counter Simulation**: Since the problem only asks for the depth of balanced parentheses and not the actual content inside them, a full `Stack` data structure is not needed. A simple integer counter (`depth`) acts as a lightweight stack size tracker.

### ⚠️ Common Mistakes

- **Updating `r` at the wrong time**: Updating `r = depth` after encountering `')'` would be incorrect because `depth` is decreasing at that point. The solution correctly updates `r` only when `depth` increases upon seeing `'('`.
- **Neglecting non-parenthesis characters**: Assuming all input characters are parentheses. The implementation safely ignores non-parenthesis characters using `if (c != '(') continue;`.

### 🚀 Optimization Notes

- **Memory Optimization**: Calling `s.toCharArray()` creates a temporary array of size $n$, taking $\mathcal{O}(n)$ memory. Replacing the `for-each` loop with an indexed loop using `s.charAt(i)` would eliminate this extra array allocation, reducing auxiliary space complexity to strictly $\mathcal{O}(1)$.
- **Time Efficiency**: The overall approach is already optimal in terms of time complexity ($\mathcal{O}(n)$), visiting every character exactly once. Using `if (depth > r) r = depth;` avoids function call overhead compared to `Math.max(r, depth)`.
