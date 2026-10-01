<h2><a href="https://leetcode.com/problems/valid-parentheses">Valid Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given a string <code>s</code> containing just the characters <code>&#39;(&#39;</code>, <code>&#39;)&#39;</code>, <code>&#39;{&#39;</code>, <code>&#39;}&#39;</code>, <code>&#39;[&#39;</code> and <code>&#39;]&#39;</code>, determine if the input string is valid.</p>

<p>An input string is valid if:</p>

<ol>
	<li>Open brackets must be closed by the same type of brackets.</li>
	<li>Open brackets must be closed in the correct order.</li>
	<li>Every close bracket has a corresponding open bracket of the same type.</li>
</ol>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;()&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;()[]{}&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;(]&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">false</span></p>
</div>

<p><strong class="example">Example 4:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;([])&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 5:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;([)]&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">false</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>4</sup></code></li>
	<li><code>s</code> consists of parentheses only <code>&#39;()[]{}&#39;</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Your implementation solves the problem by simulating a **Stack** using an array in-place, paired with clever **ASCII bit manipulation and arithmetic tricks** instead of object-heavy data structures (like `java.util.Stack`) or explicit character conditionals.

By analyzing the binary representations of ASCII characters for `()`, `[]`, and `{}`:
1. Every **opening bracket** (`(`, `[`, `{`) has lower two bits `c & 3` equal to `0` or `3` (never `1`).
2. Every **closing bracket** (`)`, `]`, `}`) has lower two bits `c & 3` equal to `1`.
3. The ASCII distance between a matching closing bracket and opening bracket is always **1** (for `()`) or **2** (for `[]` and `{}`).

Using these properties, your code uses the input array `S` itself as a stack, incrementing and decrementing pointer `j` to push and pop opening brackets, and checking bracket pairs using bitwise logic.

---

### 🔍 Approach

1. **Length Parity Check**:
   - `if (str.length() % 2 == 1) return false;`
   - A valid parenthesized string must have an even length. If odd, it returns `false` immediately.

2. **In-Place Stack Initialization**:
   - `char[] S = str.toCharArray();` converts the string into a mutable character array.
   - `int j = 0;` acts as the stack pointer (representing both current size and top index for the next element).

3. **Single Pass Iteration**:
   - For each character `c` in `S`:
     - **Check if `c` is an opening bracket**: `(c & 3) != 1`
       - ASCII values: `'('` (40), `'['` (91), `'{'` (123).
       - `40 & 3 = 0`, `91 & 3 = 3`, `123 & 3 = 3`. None equal `1`.
       - If `true`, push `c` to the stack by writing `S[j++] = c`.
     - **Otherwise (`c` is a closing bracket)**:
       - ASCII values: `')'` (41), `']'` (93), `'}'` (125). All yield `& 3 == 1`.
       - Check if stack is empty (`j == 0`). If so, return `false` (closing bracket without an open pair).
       - Pop the top open bracket using `--j` and check if it matches `c` using `((c - S[--j] + 1) >> 1) != 1`.
       - If it doesn't match, return `false`.

4. **Final Check**:
   - `return j == 0;` ensures all opening brackets were popped and matched.

---

### 🧩 Algorithm

#### ASCII Bitwise & Arithmetic Rules Used
- **Opening Bracket Identification**:
  $$(c \text{ \& } 3) \neq 1$$
- **Matching Pair Verification**:
  Given closing character $c$ and top stack character $open = S[j-1]$:
  $$\text{diff} = c - open$$
  - For `()`: $41 - 40 = 1 \implies ((1 + 1) \gg 1) = (2 \gg 1) = 1$
  - For `[]`: $93 - 91 = 2 \implies ((2 + 1) \gg 1) = (3 \gg 1) = 1$
  - For `{}`: $125 - 123 = 2 \implies ((2 + 1) \gg 1) = (3 \gg 1) = 1$

If $\text{diff} \notin \{1, 2\}$, then $((\text{diff} + 1) \gg 1) \neq 1$, correctly identifying a mismatch.

#### Invariants
- `S[0 ... j-1]` stores the current sequence of unclosed opening brackets in LIFO order.
- Pointer `j` is strictly $\ge 0$.

---

### ✅ Why This Works

- **Classification Correctness**: The bitwise mask `c & 3` partitions the 6 bracket characters into two distinct sets: opening bracket characters (mask $\neq 1$) and closing bracket characters (mask $== 1$).
- **Matching Correctness**: The mathematical expression `((c - open + 1) >> 1) == 1` evaluates to `true` **if and only if** `c - open` equals $1$ or $2$. Because the ASCII offsets of valid pairs `()`, `[]`, `{}` are uniquely $1$, $2$, and $2$ respectively, and any mismatched pair produces a different distance (or negative value), mismatched pairs are guaranteed to fail this check.
- **Order Correctness**: Short-circuiting `j == 0` prevents underflow when closing brackets appear unexpectedly, and evaluating `S[--j]` ensures Last-In, First-Out (LIFO) matching order.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of `str`. The algorithm iterates through the array once. Each character operation (bitwise AND, subtraction, shift, stack write/read) executes in $\mathcal{O}(1)$ time.
- **Space Complexity**: $\mathcal{O}(N)$ to store the `char[] S` buffer created by `str.toCharArray()`. The stack operations reuse this array without extra heap allocations.

---

### 🧠 DSA Pattern

- **Monotonic Stack / LIFO Stack** (implemented via primitive array and stack pointer)
- **Bit Manipulation** (character filtering via bitwise masking)

---

### ⚠️ Common Mistakes

1. **Operator Precedence Errors**:
   - Writing `c & 3 != 1` without parentheses would fail because `!=` has higher precedence than `&` in Java (`c & (3 != 1)`). The code correctly uses `(c & 3) != 1`.
2. **Stack Underflow**:
   - Accessing `S[--j]` when `j == 0` causes an `ArrayIndexOutOfBoundsException`. The code prevents this via short-circuit evaluation: `j == 0 || ...`.
3. **Assuming ASCII Compatibility Everywhere**:
   - This technique relies explicitly on standard ASCII encoding values for `()`, `[]`, and `{}`. It will not work on non-ASCII encodings (e.g. EBCDIC).

---

### 🚀 Optimization Notes

- **Low-level Overhead**: By avoiding Java collections (e.g. `Stack<Character>`) and boxing/unboxing `Character` objects, this solution avoids garbage collection pressure and allocation overhead.
- **In-place Re-use**: Mutating `S` directly as the stack space avoids allocating a separate array for stack tracking.
- **Optimal Time Complexity**: The solution runs in optimal linear time $\mathcal{O}(N)$ and cannot be asymptotically improved.
