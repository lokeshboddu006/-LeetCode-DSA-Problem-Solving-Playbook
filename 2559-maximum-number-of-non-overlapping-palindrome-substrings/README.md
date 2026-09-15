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

The solution combines the **Center Expansion technique** for finding palindromes with a **Greedy Interval Scheduling strategy** to maximize the count of non-overlapping palindromes of length at least $k$.

Instead of generating all possible substrings or building an expensive Dynamic Programming table, the code iterates over every possible palindrome center (both single-character centers for odd lengths and between-character centers for even lengths). Whenever a palindrome reaches length $\ge k$, the code greedily attempts to include or optimize the ending position (`lastEnd`) of the chosen non-overlapping palindrome set. 

Choosing palindromes that end as early as possible leaves maximum room for future non-overlapping palindromes.

### 🔍 Approach

1. **Center Iteration (`0` to `2 * n - 1`)**:
   - The code iterates over `center` values from `0` to `2 * n - 1` to cover all $2n - 1$ possible center positions.
   - `left` is set to `center / 2`.
   - `right` is set to `left + center % 2`.
   - When `center % 2 == 0`, `left == right` (odd-length palindrome expansion).
   - When `center % 2 == 1`, `right == left + 1` (even-length palindrome expansion).

2. **Expand Around Center**:
   - A `while` loop expands `left` leftwards and `right` rightwards as long as indices are within bounds and `s.charAt(left) == s.charAt(right)`.

3. **Check Valid Palindrome Length ($\ge k$)**:
   - As soon as `right - left + 1 >= k`, a valid palindrome candidate is found.
   - The end boundary of this palindrome is stored in `end = right + 1` (1-based index representation).

4. **Greedy Selection Logic**:
   - **Case 1 (`left >= lastEnd`)**: The palindrome starts at or after the end of the last selected palindrome. It does not overlap with any previously counted palindrome. Thus, increment `count++` and update `lastEnd = end`.
   - **Case 2 (`left < lastEnd`)**: The palindrome overlaps with the current selection window. The code updates `lastEnd = Math.min(lastEnd, end)`. This replaces a previously considered end boundary with a smaller one if the current palindrome ends earlier, freeing up more space for subsequent palindromes.
   - **Loop Termination (`break`)**: After processing the first valid palindrome of length $\ge k$ for a center, the code immediately breaks out of the `while` loop. Expanding further around the same center would only produce longer palindromes ending later, which is strictly worse for greedy interval selection.

5. **Return Result**:
   - After testing all centers, `count` contains the maximum number of non-overlapping valid palindromic substrings.

### 🧩 Algorithm

- **Greedy Choice Property**: Always prefer palindromes that end at the smallest possible index to leave the maximum possible space for future valid substrings.
- **Center Expansion Invariant**:
  - `center` maps to `[left, right]` initial bounds.
  - Shortest valid palindrome around a center has length $k$ or $k+1$.
- **Interval Decision Logic**:
  $$
  \text{If } \text{left} \ge \text{lastEnd} \implies \text{count} = \text{count} + 1, \; \text{lastEnd} = \text{end}
  $$
  $$
  \text{If } \text{left} < \text{lastEnd} \implies \text{lastEnd} = \min(\text{lastEnd}, \text{end})
  $$

### ✅ Why This Works

1. **Minimal Length Sufficiency**: Any palindrome of length $> k + 1$ contains a smaller palindrome of length $k$ (if length difference is even) or $k+1$ (if length difference is odd) centered around the same middle region. Therefore, stopping at the *first* palindrome of length $\ge k$ around any center guarantees finding the earliest ending palindrome for that center.
2. **Interval Scheduling Correctness**: By keeping `lastEnd` as small as possible, we never eliminate potential future non-overlapping palindromes. If a new palindrome overlaps with the region ending at `lastEnd` but ends even earlier (`end < lastEnd`), taking `lastEnd = end` is strictly better because any future palindrome that does not overlap with `end` will also not overlap with `lastEnd`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n^2)$ worst-case. There are $2n$ centers, and expanding around each center takes at most $\mathcal{O}(n)$ operations. However, because the loop `break`s as soon as a palindrome of length $k$ or $k+1$ is found, the expansion around each center actually terminates in $\mathcal{O}(k)$ steps, making the average/practical runtime much faster.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. Only scalar primitive variables (`n`, `lastEnd`, `count`, `center`, `left`, `right`, `end`) are used.

### 🧠 DSA Pattern

- **Greedy** (Interval Scheduling / Early End-Time Selection)
- **Two Pointers** (Expand Around Center)

### ⚠️ Common Mistakes

1. **Forgetting to Break**: Continuing the `while` loop after finding `right - left + 1 >= k` would consider longer palindromes with larger `end` values, which could incorrectly overwrite `lastEnd` with a larger value or miss optimal early-ending boundaries.
2. **Ignoring the `Math.min` Update**: If `left < lastEnd`, simply discarding the palindrome without updating `lastEnd = Math.min(lastEnd, end)` would miss the opportunity to tighten the end boundary.
3. **Index Mapping Misunderstanding**: Confusing 0-based character indices (`right`) with end boundaries (`right + 1`) when updating `lastEnd`.

### 🚀 Optimization Notes

- **$O(1)$ Space**: The submitted implementation runs in $\mathcal{O}(1)$ extra space, avoiding the $\mathcal{O}(n^2)$ space required by DP table approaches for palindrome verification.
- **Early Loop Break**: Breaking out of the expansion immediately after `right - left + 1 >= k` is a key optimization that keeps expansions bounded to at most $k+1$ steps per center.
