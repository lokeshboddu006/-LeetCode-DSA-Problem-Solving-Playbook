<h2><a href="https://leetcode.com/problems/maximum-number-of-non-overlapping-palindrome-substrings">Maximum Number of Non-overlapping Palindrome Substrings</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given a string <code>s</code> and a <strong>positive</strong> integer <code>k</code>.</p>

<p>Select a set of <strong>non-overlapping</strong> substrings from the string <code>s</code> that satisfy the following conditions:</p>

<ul>
	<li>The <strong>length</strong> of each substring is <strong>at least</strong> <code>k</code>.</li>
	<li>Each substring is a <strong>palindrome</strong>.</li>
</ul>

<p>Return <em>the <strong>maximum</strong> number of substrings in an optimal selection</em>.</p>

<p>A <strong>substring</strong> is a contiguous sequence of characters within a string.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;abaccdbbd&quot;, k = 3
<strong>Output:</strong> 2
<strong>Explanation:</strong> We can select the substrings underlined in s = &quot;<u><strong>aba</strong></u>cc<u><strong>dbbd</strong></u>&quot;. Both &quot;aba&quot; and &quot;dbbd&quot; are palindromes and have a length of at least k = 3.
It can be shown that we cannot find a selection with more than two valid substrings.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;adbcda&quot;, k = 2
<strong>Output:</strong> 0
<strong>Explanation:</strong> There is no palindrome substring of length at least 2 in the string.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= k &lt;= s.length &lt;= 2000</code></li>
	<li><code>s</code> consists of lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code solves the problem by combining the **Expand Around Center** technique for finding palindromes with a **Greedy** strategy to select the maximum number of non-overlapping substrings.

The key mathematical observation is:
1. If there is a palindrome substring of length $\ge k$, it contains a smaller palindrome of length $k$ (if $k$ is even/odd parity matches) or $k+1$ centered around the same axis.
2. To maximize the total count of non-overlapping substrings, we should always greedily select palindromes that end as early as possible. Selecting shorter valid palindromes (length $k$ or $k+1$) leaves maximum room for remaining substrings to the right.

By iterating through all possible centers left-to-right and expanding outward just until a valid length ($\ge k$) is reached, the code greedily picks valid non-overlapping palindromes as early as possible.

---

### 🔍 Approach

1. **Initialization**:
   - `n`: Length of string `s`.
   - `c`: Counter for the number of valid non-overlapping palindromes found so far (initialized to `0`).
   - `e`: Tracks the ending index of the most recently chosen palindrome (initialized to `-1`).

2. **Iterating Center Positions**:
   - The loop runs `i` from `0` to `2 * n - 1` to cover all $2n - 1$ potential centers:
     - When `i` is even: `l = i / 2`, `r = l` (odd-length palindrome centered at a character).
     - When `i` is odd: `l = i / 2`, `r = l + 1` (even-length palindrome centered between two characters).

3. **Expanding Around Center**:
   - A `while` loop expands outward (`l--`, `r++`) as long as `l >= 0 && r < n` and `s.charAt(l) == s.charAt(r)`.

4. **Greedy Selection & Validation**:
   - Inside the expansion loop, as soon as the current palindrome length `r - l + 1` reaches or exceeds `k`:
     - Check if the start index `l` is strictly greater than `e` (`l > e`). This ensures the new palindrome does not overlap with the previously selected one.
     - If non-overlapping (`l > e`), increment the count `c++` and update `e = r` (marking `r` as the new end boundary).
     - **Break out of the expansion**: `break` terminates the `while` loop immediately because further expansion from the same center would only produce a longer palindrome ending further right, which is suboptimal for greedy selection.

5. **Return**:
   - After checking all centers, return `c`.

---

### 🧩 Algorithm

1. **Center Enumeration**:
   - For `i` from `0` to `2N - 1`:
     - Set $l = \lfloor i / 2 \rfloor$
     - Set $r = l + (i \bmod 2)$

2. **Expansion and Condition**:
   - While $l \ge 0$ and $r < N$ and $s[l] == s[r]$:
     - If $(r - l + 1) \ge k$:
       - If $l > e$:
         - $c \leftarrow c + 1$
         - $e \leftarrow r$
       - **Break** inner loop.
     - $l \leftarrow l - 1$, $r \leftarrow r + 1$

---

### ✅ Why This Works

- **Non-overlapping Guarantee**: The condition `l > e` strictly enforces that the new palindrome starts at index `l`, which must be strictly after the ending index `e` of the previously picked palindrome.
- **Optimal Greedy Choice**: Picking the smallest valid palindrome ($\text{length } k \text{ or } k + 1$) at the earliest possible center index minimizes `e` (the end boundary). Minimizing the end boundary leaves the largest possible remaining suffix of the string to find more non-overlapping palindromes.
- **Completeness**: Since every palindrome of length $> k+1$ contains a smaller palindrome of length $k$ or $k+1$ centered around the same center, stopping expansion as soon as length $\ge k$ is reached will never miss an optimal choice.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N \cdot K)$ worst-case.
  - The outer loop runs $2N$ times (for $2N - 1$ centers).
  - The inner `while` loop expands at most $\lceil k / 2 \rceil + 1$ times per center before reaching length $\ge k$ and hitting the `break` statement.
  - Thus, total operations are bounded by $\mathcal{O}(N \cdot K)$, which easily runs within time limits for $N \le 2000$.

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space.
  - Only primitive integer pointers/variables (`n`, `c`, `e`, `i`, `l`, `r`) are used. No additional data structures or dynamic programming tables are allocated.

---

### 🧠 DSA Pattern

- **Two Pointers / Expand Around Center**: Used to expand symmetrically around all odd and even centers to detect palindromes on the fly.
- **Greedy**: Chooses the earliest terminating valid palindrome to maximize room for subsequent selections.

---

### ⚠️ Common Mistakes

1. **Omitting the `break` statement**:
   If you don't break after finding length $\ge k$, the code would continue expanding to larger lengths. This could update `e` with a larger value unnecessarily, potentially missing future non-overlapping palindromes.

2. **Off-by-one in overlap check (`l >= e` vs `l > e`)**:
   Since substring indices are inclusive, two substrings overlap if the second starts at `l == e`. The strict inequality `l > e` correctly prevents sharing the character at index `e`.

3. **Incorrect center initialization**:
   Miscalculating `l` and `r` from `i` can lead to skipping either even-length or odd-length centers. The formula `l = i / 2` and `r = l + i % 2` cleanly handles both cases.

---

### 🚀 Optimization Notes

- **Early Termination (`break`)**: The `break` inside the `if (r - l + 1 >= k)` check is the critical optimization in this code. It bounds the inner expansion loop to at most $\sim k/2$ steps per center instead of $\sim N/2$ steps.
- **Memory Efficiency**: Unlike solutions that precompute all palindromes using an $N \times N$ DP table ($\mathcal{O}(N^2)$ space), this solution operates directly on the string in $\mathcal{O}(1)$ space.
