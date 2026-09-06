<h2><a href="https://leetcode.com/problems/distinct-subsequences">Distinct Subsequences</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given two strings s and t, return <i>the number of distinct</i> <b><i>subsequences</i></b><i> of </i>s<i> which equals </i>t.</p>

<p>The test cases are generated so that the answer fits on a 32-bit signed integer.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;rabbbit&quot;, t = &quot;rabbit&quot;
<strong>Output:</strong> 3
<strong>Explanation:</strong>
As shown below, there are 3 ways you can generate &quot;rabbit&quot; from s.
<code><strong><u>rabb</u></strong>b<strong><u>it</u></strong></code>
<code><strong><u>ra</u></strong>b<strong><u>bbit</u></strong></code>
<code><strong><u>rab</u></strong>b<strong><u>bit</u></strong></code>
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;babgbag&quot;, t = &quot;bag&quot;
<strong>Output:</strong> 5
<strong>Explanation:</strong>
As shown below, there are 5 ways you can generate &quot;bag&quot; from s.
<code><strong><u>ba</u></strong>b<u><strong>g</strong></u>bag</code>
<code><strong><u>ba</u></strong>bgba<strong><u>g</u></strong></code>
<code><u><strong>b</strong></u>abgb<strong><u>ag</u></strong></code>
<code>ba<u><strong>b</strong></u>gb<u><strong>ag</strong></u></code>
<code>babg<strong><u>bag</u></strong></code></pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length, t.length &lt;= 1000</code></li>
	<li><code>s</code> and <code>t</code> consist of English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the number of distinct subsequences of string `s` that form string `t`.

Your solution uses **suffix-based Dynamic Programming**. The core observation is:
1. When comparing a character `s[i]` with `t[j]`:
   - If `s[i]` matches `t[j]`, you have two choices:
     - **Match them:** Count all valid subsequences formed by matching `s[i]` with `t[j]` (which moves both character pointers to `i + 1` and `j + 1`).
     - **Skip `s[i]`:** Count all valid subsequences formed by ignoring `s[i]` and matching `t[j]` using the rest of `s` (moving only `s` pointer to `i + 1`).
   - If `s[i]` does not match `t[j]`:
     - You **must skip** `s[i]` and try matching `t[j]` in the remaining suffix of `s`.

By building the subproblem answers from the back of the strings (suffixes) towards the front, `dp[0][0]` ultimately stores the total number of ways to form the entire string `t` from `s`.

---

### 🔍 Approach

1. **Early Exit Guard Clause:**
   - `if (m < n) return 0;`: If string `s` is shorter than `t`, it is impossible to form `t` as a subsequence, so the code immediately returns `0`.

2. **DP Table Initialization:**
   - Creates a 2D array `dp` of size `(m + 1) x (n + 1)`, initialized to all `0`s by default in Java.
   - `dp[i][j]` represents the number of distinct subsequences of suffix `s[i..m-1]` that match suffix `t[j..n-1]`.

3. **Base Cases:**
   - Loop `for (int i = 0; i <= m; i++) dp[i][n] = 1;`:
   - Matching an empty target string suffix (`t[n..n-1]`) requires choosing an empty subsequence from any suffix of `s`. There is exactly **1** way to form an empty string (by choosing 0 characters).

4. **Nested Loops (Bottom-Up Computation):**
   - Outer loop `i` iterates backwards from `m - 1` down to `0`.
   - Reads character `sChar = s.charAt(i)` once per outer loop iteration.
   - Inner loop `j` iterates backwards from `n - 1` down to `0`.
   - Compares `sChar` with `t.charAt(j)`:
     - **If equal:** `dp[i][j] = dp[i + 1][j + 1] + dp[i + 1][j]` (sum of matching `s[i]` with `t[j]` and skipping `s[i]`).
     - **If not equal:** `dp[i][j] = dp[i + 1][j]` (must skip `s[i]`).

5. **Result Extraction:**
   - Returns `dp[0][0]`, which represents matching suffix `s[0..m-1]` against suffix `t[0..n-1]`.

---

### 🧩 Algorithm

**Pattern:** Bottom-Up 2D Dynamic Programming (Suffix Slicing)

* **DP State:** `dp[i][j]` = number of distinct subsequences of `s[i..m-1]` matching `t[j..n-1]`.
* **Base Case:** 
  - `dp[i][n] = 1` for all `0 <= i <= m`
  - `dp[m][j] = 0` for all `0 <= j < n` (handled automatically by Java array default initialization)
* **Transitions:**
  $$\text{dp}[i][j] = \begin{cases} \text{dp}[i+1][j+1] + \text{dp}[i+1][j], & \text{if } s[i] == t[j] \\ \text{dp}[i+1][j], & \text{if } s[i] \neq t[j] \end{cases}$$

---

### ✅ Why This Works

- **Exhaustive Subsequence Counting:** Every character in `s` presents a decision: include it (when matching) or skip it. Summing both options whenever `s[i] == t[j]` accounts for all distinct ways to build `t` without overcounting or missing valid combinations.
- **Overlapping Subproblems:** The count of matching `t[j..]` in `s[i+1..]` is reused multiple times across different branches of computation. Storing these subproblem solutions in the `dp` table avoids exponential recomputation.
- **Correct Base Case:** The empty string `t[n..]` is a valid subsequence of any suffix `s[i..]` in exactly 1 way (the empty set choice), ensuring that when a full match of `t` is completed, `1` is contributed back to the sum.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(m \times n)$  
  - Outer loop runs $m$ times, inner loop runs $n$ times. Inside the loops, character access and additions take $\mathcal{O}(1)$ time. Total operations are proportional to $m \times n$.
- **Space Complexity:** $\mathcal{O}(m \times n)$  
  - Allocated for the 2D table `dp` of dimensions `(m + 1) x (n + 1)`.

---

### 🧠 DSA Pattern

- **Dynamic Programming** (2D String / Suffix Matching)

---

### ⚠️ Common Mistakes

1. **Incorrect Base Case Setup:** Setting `dp[i][n] = 0` instead of `1`. If empty suffix matches were initialized to 0, matching pathways would propagate 0s all the way back to `dp[0][0]`.
2. **Loop Directions:** Iterating from $0$ up to $m-1$ without reversing the DP transition direction would reference uncomputed future states (`dp[i+1][j+1]`).
3. **Array Dimensions:** Using size `[m][n]` instead of `[m + 1][n + 1]`, which causes `ArrayIndexOutOfBoundsException` when accessing base cases at `i + 1 = m` or `j + 1 = n`.

---

### 🚀 Optimization Notes

- **Micro-Optimization present in code:** Hoisting `char sChar = s.charAt(i)` out of the inner loop avoids invoking `s.charAt(i)` $n$ times per row, reducing method call overhead.
- **Space Optimization Potential:** Notice that computing row `i` only requires values from row `i + 1`. The $2\text{D}$ matrix of size $\mathcal{O}(m \times n)$ can be compressed into a $1\text{D}$ array of size $\mathcal{O}(n)$, reducing space usage from $\mathcal{O}(m \times n)$ down to $\mathcal{O}(n)$.
