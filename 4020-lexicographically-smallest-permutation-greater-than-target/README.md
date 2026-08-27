<h2><a href="https://leetcode.com/problems/lexicographically-smallest-permutation-greater-than-target">Lexicographically Smallest Permutation Greater Than Target</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given two strings <code>s</code> and <code>target</code>, both having length <code>n</code>, consisting of lowercase English letters.</p>

<p>Return the <strong>lexicographically smallest <span data-keyword="permutation-string">permutation</span></strong> of <code>s</code> that is <strong>strictly</strong> greater than <code>target</code>. If no permutation of <code>s</code> is lexicographically strictly greater than <code>target</code>, return an empty string.</p>

<p>A string <code>a</code> is <strong>lexicographically strictly greater </strong>than a string <code>b</code> (of the same length) if in the first position where <code>a</code> and <code>b</code> differ, string <code>a</code> has a letter that appears later in the alphabet than the corresponding letter in <code>b</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;abc&quot;, target = &quot;bba&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;bca&quot;</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The permutations of <code>s</code> (in lexicographical order) are <code>&quot;abc&quot;</code>, <code>&quot;acb&quot;</code>, <code>&quot;bac&quot;</code>, <code>&quot;bca&quot;</code>, <code>&quot;cab&quot;</code>, and <code>&quot;cba&quot;</code>.</li>
	<li>The lexicographically smallest permutation that is strictly greater than <code>target</code> is <code>&quot;bca&quot;</code>.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;leet&quot;, target = &quot;code&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;eelt&quot;</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The permutations of <code>s</code> (in lexicographical order) are <code>&quot;eelt&quot;</code>, <code>&quot;eetl&quot;</code>, <code>&quot;elet&quot;</code>, <code>&quot;elte&quot;</code>, <code>&quot;etel&quot;</code>, <code>&quot;etle&quot;</code>, <code>&quot;leet&quot;</code>, <code>&quot;lete&quot;</code>, <code>&quot;ltee&quot;</code>, <code>&quot;teel&quot;</code>, <code>&quot;tele&quot;</code>, and <code>&quot;tlee&quot;</code>.</li>
	<li>The lexicographically smallest permutation that is strictly greater than <code>target</code> is <code>&quot;eelt&quot;</code>.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;baba&quot;, target = &quot;bbaa&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;&quot;</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The permutations of <code>s</code> (in lexicographical order) are <code>&quot;aabb&quot;</code>, <code>&quot;abab&quot;</code>, <code>&quot;abba&quot;</code>, <code>&quot;baab&quot;</code>, <code>&quot;baba&quot;</code>, and <code>&quot;bbaa&quot;</code>.</li>
	<li>None of them is lexicographically strictly greater than <code>target</code>. Therefore, the answer is <code>&quot;&quot;</code>.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length == target.length &lt;= 300</code></li>
	<li><code>s</code> and <code>target</code> consist of only lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To find the lexicographically smallest permutation of `s` that is strictly greater than `target`, we want to make the permutation match `target` for as long a prefix as possible. 

The ideal strategy is to iterate backwards from the end of `target` to find the rightmost index `i` where:
1. The prefix `target[0...i-1]` can be formed using characters from `s`.
2. There is at least one character in `s` (not yet used in `target[0...i-1]`) that is **strictly greater** than `target[i]`.

Once we find the largest such index `i`, we:
- Place the **smallest available character strictly greater than `target[i]`** at index `i`.
- Place all remaining available characters in **ascending order** (`'a'` to `'z'`) for the rest of the string to keep the suffix as lexicographically small as possible.

### 🔍 Approach

1. **Character Difference Frequency Array (`cnt`)**:
   - We initialize a frequency array `cnt` of size 26.
   - First, increment counts for all characters in `s`.
   - Then, decrement counts for all characters in `target`.
   - Initially, `cnt[c]` represents: `(count of character c in s) - (count of character c in target)`.

2. **Right-to-Left Traversal (Finding the divergence point `i`)**:
   - We iterate `i` from `target.length() - 1` down to `0`.
   - At each step, we "add back" `target[i]` into `cnt` by doing `cnt[cur]++`. This effective operation dynamically updates `cnt` to represent the remaining available characters from `s` after using the prefix `target[0...i-1]`.

3. **Prefix Validity Check**:
   - We iterate through `cnt`. If any `cnt[x] < 0`, it means `target[0...i-1]` requires more of character `x` than `s` actually has. Thus, `target[0...i-1]` is invalid, so we `continue` to the next `i`.

4. **Greedy Character Selection**:
   - We look for the smallest character `next` such that `next > cur` and `cnt[next] > 0`.
   - If no such character exists (`next == -1`), we cannot diverge at index `i`, so we `continue`.

5. **Constructing the Result**:
   - If a valid `next` character is found, we decrement `cnt[next]--`.
   - We build the answer string:
     - Prefix: `target.substring(0, i)`
     - Index `i`: `(char) ('a' + next)`
     - Suffix: Iterate `c` from `0` to `25` and append all remaining counts `cnt[c]` in increasing order.
   - Return this string immediately (since we traversed from right to left, the first valid answer found has the maximum prefix match, ensuring it is the lexicographically smallest overall).

6. **Fallback**:
   - If the loop finishes without returning, no valid permutation exists, so return `""`.

### 🧩 Algorithm

1. `cnt = count(s) - count(target)`
2. `FOR i FROM target.length() - 1 DOWN TO 0 DO:`
   - `cur = target[i] - 'a'`
   - `cnt[cur]++`
   - `IF ANY x IN cnt HAS x < 0 THEN CONTINUE`
   - `Find smallest c > cur WHERE cnt[c] > 0`
   - `IF NO SUCH c EXISTS THEN CONTINUE`
   - `cnt[c]--`
   - `ans = target[0...i-1] + char(c) + remaining_chars_in_cnt_sorted()`
   - `RETURN ans`
3. `RETURN ""`

### ✅ Why This Works

- **Maximizing the Common Prefix**: By scanning from right to left (index `n-1` down to `0`), the first index `i` at which we can successfully construct a greater character guarantees that the common prefix `target[0...i-1]` is as long as possible. A longer common prefix strictly produces a smaller string than a shorter common prefix.
- **Smallest Divergence Character**: Choosing the smallest character `next > target[i]` ensures the string is as small as possible while still being strictly greater than `target` at index `i`.
- **Sorting the Suffix**: Placing all leftover characters in ascending alphabetical order (`'a'` to `'z'`) minimizes the value of the remaining suffix.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N \times \Sigma)$ where $N$ is the length of `target` ($N \le 300$) and $\Sigma = 26$ is the alphabet size.
  - Scanning `s` and `target` takes $\mathcal{O}(N)$ time.
  - The main loop runs up to $N$ times. In each iteration, checking array `cnt` and finding `next` takes $\mathcal{O}(26)$ time.
  - Building the final string takes $\mathcal{O}(N)$ time once.
  - Total Time: $\mathcal{O}(N \cdot \Sigma)$, which easily runs within the time limit.

- **Space Complexity**: $\mathcal{O}(\Sigma)$ auxiliary space (or $\mathcal{O}(N)$ if counting space for the `StringBuilder` output).
  - The array `cnt` takes a fixed size of 26 integers.

### 🧠 DSA Pattern

- **Greedy**: Always pick the longest matching prefix, the smallest valid next character at the point of divergence, and sort the remaining characters in ascending order.
- **Counting / Frequency Array**: Tracks available character counts dynamically as prefix requirements shrink.
- **Right-to-Left Traversal**: Used to find the optimal divergence point.

### ⚠️ Common Mistakes

1. **Not Checking Prefix Feasibility**: Forgetting to check if `cnt[x] < 0` for any character `x`. If `target[0...i-1]` cannot be formed by `s`, trying to diverge at index `i` would produce an invalid permutation.
2. **Scanning Left-to-Right Instead of Right-to-Left**: Scanning left-to-right might find a valid divergence point too early, yielding a larger string than necessary.
3. **Not Restoring Counts Correctly**: If one were to clone the frequency array repeatedly instead of incrementally doing `cnt[cur]++`, it could lead to higher memory allocations or unnecessary copy operations.

### 🚀 Optimization Notes

- The solution is already very efficient and optimal for $N \le 300$.
- **In-place count modification**: Reclaiming counts with `cnt[cur]++` avoids creating duplicate frequency arrays or re-counting characters from scratch in each step.
- **Early exit**: Returning `ans.toString()` on the very first valid right-to-left index avoids evaluating further prefixes.
