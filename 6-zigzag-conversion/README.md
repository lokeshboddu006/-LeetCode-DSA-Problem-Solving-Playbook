<h2><a href="https://leetcode.com/problems/zigzag-conversion">Zigzag Conversion</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>The string <code>&quot;PAYPALISHIRING&quot;</code> is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)</p>

<pre>
P   A   H   N
A P L S I I G
Y   I   R
</pre>

<p>And then read line by line: <code>&quot;PAHNAPLSIIGYIR&quot;</code></p>

<p>Write the code that will take a string and make this conversion given a number of rows:</p>

<pre>
string convert(string s, int numRows);
</pre>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;PAYPALISHIRING&quot;, numRows = 3
<strong>Output:</strong> &quot;PAHNAPLSIIGYIR&quot;
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;PAYPALISHIRING&quot;, numRows = 4
<strong>Output:</strong> &quot;PINALSIGYAHRPI&quot;
<strong>Explanation:</strong>
P     I    N
A   L S  I G
Y A   H R
P     I
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;A&quot;, numRows = 1
<strong>Output:</strong> &quot;A&quot;
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 1000</code></li>
	<li><code>s</code> consists of English letters (lower-case and upper-case), <code>&#39;,&#39;</code> and <code>&#39;.&#39;</code>.</li>
	<li><code>1 &lt;= numRows &lt;= 1000</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Instead of calculating mathematical formulas or 2D matrix coordinates, your solution directly simulates the zigzag process line-by-line using a moving row pointer (`k`) and a directional step (`d`).

Imagine placing characters onto a set of rows:
- You walk down from row `0` to row `r - 1`.
- Once you reach the bottom row (`r - 1`), you bounce back up toward row `0`.
- Once you reach the top row (`0`), you bounce back down toward row `r - 1`.

By maintaining a `StringBuilder` for each row, you append each character to its corresponding row buffer. At the end, concatenating all row buffers sequentially produces the correct zigzag converted string.

---

### 🔍 Approach

1. **Edge Case Handling**:
   ```java
   if (r == 1 || s.length() <= r) return s;
   ```
   If `r == 1` or the string length is less than or equal to `r`, the zigzag pattern cannot change the character ordering. Returning `s` directly handles these edge cases efficiently and prevents boundary issues later.

2. **Row Buffers Initialization**:
   ```java
   StringBuilder[] a = new StringBuilder[r];
   for (int i = 0; i < r; i++) a[i] = new StringBuilder();
   ```
   You allocate an array `a` of `r` `StringBuilder` objects and initialize each index with a new `StringBuilder` instance to accumulate characters row by row.

3. **Zigzag Simulation with Direction Bouncing**:
   ```java
   int k = 0, d = 1;
   for (char c : s.toCharArray()) {
       a[k].append(c);
       if (k == 0) d = 1;
       else if (k == r - 1) d = -1;
       k += d;
   }
   ```
   - `k`: Tracks the active row index (starts at `0`).
   - `d`: Tracks the movement direction (`1` for moving downwards, `-1` for moving upwards).
   - For each character `c` in `s`:
     - Append `c` to the active row `a[k]`.
     - Check boundary conditions:
       - If `k == 0`, switch direction downwards (`d = 1`).
       - If `k == r - 1`, switch direction upwards (`d = -1`).
     - Advance `k` by `d` to move to the next target row.

4. **Concatenation and Output**:
   ```java
   StringBuilder res = new StringBuilder();
   for (StringBuilder b : a) res.append(b);
   return res.toString();
   ```
   Finally, iterate through the array of `StringBuilder`s from row `0` to row `r - 1`, combine them into a single `res` buffer, and return the final string.

---

### 🧩 Algorithm

The exact mechanism implemented is a **1D Directional Bouncing Simulation**:

- **State Variables**:
  - Active row index: $k \in [0, r - 1]$
  - Direction state: $d \in \{1, -1\}$

- **Transition Rule**:
  $$\text{Next Row } k' = k + d$$
  $$\text{where } d = \begin{cases} 1 & \text{if } k = 0 \\ -1 & \text{if } k = r - 1 \\ d & \text{otherwise} \end{cases}$$

- **Invariant**:
  At any step during character placement, $0 \le k < r$.

---

### ✅ Why This Works

- **Preservation of Order**: Characters assigned to the same row retain their natural left-to-right relative ordering because they are appended sequentially to that row's `StringBuilder`.
- **Correct Direction Switching**: Checking `k == 0` and `k == r - 1` right after appending guarantees that `k` turns around at the exact boundaries before `k += d` is executed.
- **Completeness**: Every character in `s` is processed exactly once, ensuring no characters are missed or duplicated. Concatenating row `0` through row `r - 1` at the end naturally produces the line-by-line reading requested.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$  
  Where $N$ is the length of string `s`.
  - Converting `s` to a char array takes $\mathcal{O}(N)$ time.
  - Iterating through all characters takes $N$ steps.
  - Appending each character to a `StringBuilder` is $\mathcal{O}(1)$ amortized time.
  - Concatenating all $r$ `StringBuilder`s takes $\mathcal{O}(N)$ total time.

- **Space Complexity**: $\mathcal{O}(N)$  
  - The array of `StringBuilder` objects collectively stores all $N$ characters of string `s`.
  - The output `StringBuilder` requires $\mathcal{O}(N)$ space to build the result string.

---

### 🧠 DSA Pattern

- **Simulation**: Re-creating the row traversal mechanics step-by-step.
- **String Manipulation**: Using dynamic string builders (`StringBuilder[]`) for efficient character appending.

---

### ⚠️ Common Mistakes

1. **Omitting the Base Case `r == 1`**:
   If `r == 1`, `k == 0` sets `d = 1` and `k == r - 1` sets `d = -1`. The `else if` logic would cause `d` to keep oscillating without moving forward properly or cause array out-of-bounds indexing. Your early return `if (r == 1 || s.length() <= r)` correctly avoids this issue.
2. **Order of Direction Switch vs. Index Update**:
   Changing `k += d` *before* checking boundary conditions `if (k == 0)` or `if (k == r - 1)` can cause `k` to exceed `[0, r - 1]` bounds and throw an `ArrayIndexOutOfBoundsException`. Checking boundaries right before updating `k` preserves correctness.

---

### 🚀 Optimization Notes

- **Optimal Time & Space**: This solution already runs in optimal $\mathcal{O}(N)$ time and $\mathcal{O}(N)$ auxiliary space.
- **Allocation Efficiency**: Using `s.toCharArray()` creates a temporary array of size $N$. Alternatively, using `s.charAt(i)` inside a standard loop avoids array creation, though `s.toCharArray()` is fast and readable in Java.
