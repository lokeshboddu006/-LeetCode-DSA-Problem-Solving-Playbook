<h2><a href="https://leetcode.com/problems/add-binary">Add Binary</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given two binary strings <code>a</code> and <code>b</code>, return <em>their sum as a binary string</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> a = "11", b = "1"
<strong>Output:</strong> "100"
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> a = "1010", b = "1011"
<strong>Output:</strong> "10101"
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= a.length, b.length &lt;= 10<sup>4</sup></code></li>
	<li><code>a</code> and <code>b</code> consist&nbsp;only of <code>&#39;0&#39;</code> or <code>&#39;1&#39;</code> characters.</li>
	<li>Each string does not contain leading zeros except for the zero itself.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation behind your code is that binary addition works column-by-column from right to left (least significant bit to most significant bit), exactly like standard base-10 addition. 

Instead of converting the binary strings to integer types (which would overflow for strings up to length $10^4$), your code simulates column-wise addition directly. By maintaining a running `carry` variable and processing bits from right to left, you append the resulting bit for each position to a dynamic string builder and reverse the final result at the end.

### 🔍 Approach

1. **Initialization**:
   - `StringBuilder sb`: Used to accumulate the sum's digits in reverse order (from least significant bit to most significant bit).
   - `carry = 0`: Tracks the carry value across binary column additions.
   - `i = a.length() - 1` and `j = b.length() - 1`: Pointers starting at the rightmost index (least significant bit) of strings `a` and `b`.

2. **Loop Condition (`while (i >= 0 || j >= 0 || carry == 1)`)**:
   - The loop continues as long as there are remaining bits in `a`, remaining bits in `b`, or a non-zero `carry` leftover from the previous addition step.

3. **Digit Addition & Carry Accumulation**:
   - If `i >= 0`, the numerical value of bit `a[i]` (computed via `a.charAt(i--) - '0'`) is added to `carry`.
   - If `j >= 0`, the numerical value of bit `b[j]` (computed via `b.charAt(j--) - '0'`) is added to `carry`.

4. **Bit Calculation & Update**:
   - `sb.append(carry % 2)`: The bit at the current place value is `carry % 2` (0 if `carry` is 0 or 2; 1 if `carry` is 1 or 3).
   - `carry /= 2`: Computes the new carry for the next position (1 if sum was $\ge 2$, otherwise 0).

5. **Result Finalization**:
   - Because bits were appended from least significant to most significant, `sb.reverse().toString()` flips the built string to produce the correct binary representation from left to right.

### 🧩 Algorithm

- **Loop Invariant**: At the start of each iteration, `carry` represents the carry-over bit from the addition of the preceding (less significant) binary digits.
- **Bit Extraction**: `a.charAt(i) - '0'` converts character `'0'` or `'1'` to integer `0` or `1`.
- **Transitions**:
  - Total sum at current column = $\text{carry}_{\text{old}} + a[i] + b[j]$.
  - Bit appended = $(\text{carry}_{\text{old}} + a[i] + b[j]) \pmod 2$.
  - $\text{carry}_{\text{new}} = \lfloor (\text{carry}_{\text{old}} + a[i] + b[j]) / 2 \rfloor$.

### ✅ Why This Works

- **Base-2 Arithmetic Correctness**: At any position, adding bits $b_1, b_2 \in \{0, 1\}$ and a carry $c \in \{0, 1\}$ yields a total in the range $[0, 3]$. 
  - $0_2 \rightarrow \text{bit } 0, \text{carry } 0$
  - $1_2 \rightarrow \text{bit } 1, \text{carry } 0$
  - $2_10 = 10_2 \rightarrow \text{bit } 0, \text{carry } 1$
  - $3_10 = 11_2 \rightarrow \text{bit } 1, \text{carry } 1$
  The expressions `carry % 2` and `carry / 2` perfectly match this binary arithmetic table.
- **Handles Variable Lengths**: Checking `i >= 0` and `j >= 0` independently allows one string to run out of digits while continuing to process the longer string.
- **Handles Final Carry**: Including `carry == 1` in the loop condition ensures an extra standard bit (e.g., $1_2 + 1_2 = 10_2$) is automatically appended after processing all characters from both strings.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(\max(N, M))$, where $N$ is `a.length()` and $M$ is `b.length()`.
  - The `while` loop runs at most $\max(N, M) + 1$ times.
  - Character lookups, append operations, and arithmetic inside the loop take $\mathcal{O}(1)$ time per iteration.
  - `sb.reverse().toString()` takes $\mathcal{O}(\max(N, M))$ time.

- **Space Complexity**: $\mathcal{O}(\max(N, M))$ auxiliary space.
  - The `StringBuilder` stores at most $\max(N, M) + 1$ characters to build the resulting binary string.

### 🧠 DSA Pattern

- **Two Pointers**: Moving pointers `i` and `j` backwards from the tail of two arrays/strings simultaneously.
- **Simulation / Elementary Arithmetic**: Simulating digit-by-digit addition with carry handling.

### ⚠️ Common Mistakes

1. **Forgetting the Final Carry**: If the loop condition was `while (i >= 0 || j >= 0)`, inputs like `"1"` + `"1"` would result in `"0"` instead of `"10"` because the final leftover carry of `1` would be lost.
2. **Post-decrement Misunderstanding**: Mixing up `i--` and `--i` can cause index out-of-bounds or miss the LSB. Your usage `a.charAt(i--)` correctly accesses index `i` first, then decrements it.
3. **ASCII Arithmetic**: Forgetting to subtract `'0'` when reading `a.charAt(i)` would add ASCII value `48` or `49` to `carry` instead of `0` or `1`.

### 🚀 Optimization Notes

- The solution is already **time and space optimal** with $\mathcal{O}(\max(N, M))$ complexity.
- `StringBuilder` is the ideal data structure in Java for string construction as it avoids repeated intermediate String allocations.
