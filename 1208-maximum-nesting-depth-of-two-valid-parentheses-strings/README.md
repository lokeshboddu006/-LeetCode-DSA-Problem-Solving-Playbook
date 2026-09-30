<h2><a href="https://leetcode.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings">Maximum Nesting Depth of Two Valid Parentheses Strings</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>A string is a <em>valid parentheses string</em>&nbsp;(denoted VPS) if and only if it consists of <code>&quot;(&quot;</code> and <code>&quot;)&quot;</code> characters only, and:</p>

<ul>
	<li>It is the empty string, or</li>
	<li>It can be written as&nbsp;<code>AB</code>&nbsp;(<code>A</code>&nbsp;concatenated with&nbsp;<code>B</code>), where&nbsp;<code>A</code>&nbsp;and&nbsp;<code>B</code>&nbsp;are VPS&#39;s, or</li>
	<li>It can be written as&nbsp;<code>(A)</code>, where&nbsp;<code>A</code>&nbsp;is a VPS.</li>
</ul>

<p>We can&nbsp;similarly define the <em>nesting depth</em> <code>depth(S)</code> of any VPS <code>S</code> as follows:</p>

<ul>
	<li><code>depth(&quot;&quot;) = 0</code></li>
	<li><code>depth(A + B) = max(depth(A), depth(B))</code>, where <code>A</code> and <code>B</code> are VPS&#39;s</li>
	<li><code>depth(&quot;(&quot; + A + &quot;)&quot;) = 1 + depth(A)</code>, where <code>A</code> is a VPS.</li>
</ul>

<p>For example, <code>&quot;&quot;</code>,&nbsp;<code>&quot;()()&quot;</code>, and&nbsp;<code>&quot;()(()())&quot;</code>&nbsp;are VPS&#39;s (with nesting depths 0, 1, and 2), and <code>&quot;)(&quot;</code> and <code>&quot;(()&quot;</code> are not VPS&#39;s.</p>

<p>Given a VPS <font face="monospace">seq</font>, split it into two disjoint subsequences <code>A</code> and <code>B</code>, such that&nbsp;<code>A</code> and <code>B</code> are VPS&#39;s (and&nbsp;<code>A.length + B.length = seq.length</code>). The subsequences may not necessarily be contiguous.</p>

<p>For example, for the sequence <code>123456789</code>, one possible split is:</p>

<ul data-end="822" data-start="776">
	<li data-end="800" data-start="776">
	<p data-end="800" data-start="778"><code data-end="799" data-start="778">A = {1, 3, 5, 7, 9}</code>,</p>
	</li>
	<li data-end="822" data-start="801">
	<p data-end="822" data-start="803"><code data-end="821" data-start="803">B = {2, 4, 6, 8}</code>.</p>
	</li>
</ul>

<p data-end="855" data-start="824">This corresponds to the output <code>[0, 1, 0, 1, 0, 1, 0, 1, 0]</code> &nbsp;where 0 indicates membership in&nbsp;<code data-end="929" data-start="926">A</code>&nbsp;and 1 indicates membership in&nbsp;<code data-end="965" data-start="962">B</code>.</p>

<p>Now choose <strong>any</strong> such <code>A</code> and <code>B</code> such that&nbsp;<code>max(depth(A), depth(B))</code> is the minimum possible value.</p>

<p>Return an <code>answer</code> array (of length <code>seq.length</code>) that encodes such a&nbsp;choice of <code>A</code> and <code>B</code>:&nbsp; <code>answer[i] = 0</code> if <code>seq[i]</code> is part of <code>A</code>, else <code>answer[i] = 1</code>.&nbsp; Note that even though multiple answers may exist, you may return any of them.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> seq = &quot;(()())&quot;
<strong>Output:</strong> [0,1,1,1,1,0]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> seq = &quot;()(())()&quot;
<strong>Output:</strong> [0,0,0,1,1,0,1,1]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= seq.size &lt;= 10000</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The goal is to split a valid parentheses string into two valid subsequences (group `0` and group `1`) such that the maximum nesting depth between the two groups is minimized.

The key idea behind your solution is to track the current nesting level using a counter (`depth`) and distribute characters between the two groups based on whether the current nesting depth is odd or even (`depth % 2`). By alternating nesting levels between group `0` and group `1`, the maximum depth is roughly cut in half, achieving the optimal split.

### 🔍 Approach

1. **Depth Tracking**:
   - You initialize `depth = 0` to keep track of the current nesting level.
   - You initialize the output array `res` with the same length as `seq`.

2. **Single Pass Processing**:
   - Iterate through each character `ch` of `seq` at index `i`.
   - **When `ch == '('`**:
     - You increment `depth` first (`depth++`), as this opening bracket takes us one level deeper.
     - You assign `res[i] = depth % 2`. This assigns odd depths to group `1` and even depths to group `0`.
   - **When `ch == ')'`**:
     - You record `res[i] = depth % 2` first, using the depth level of this closing bracket before exiting it.
     - You then decrement `depth` (`depth--`) as you exit the current nesting level.

3. **Return**:
   - Return `res` containing `0`s and `1`s.

### 🧩 Algorithm

- **State Variable**: `depth` (tracks current nesting level).
- **Transitions**:
  - For `'('`:
    1. $\text{depth} \leftarrow \text{depth} + 1$
    2. $\text{res}[i] \leftarrow \text{depth} \bmod 2$
  - For `')'`:
    1. $\text{res}[i] \leftarrow \text{depth} \bmod 2$
    2. $\text{depth} \leftarrow \text{depth} - 1$

- **Invariant**:
  Any matching pair of `(` and `)` at nesting depth $d$ will evaluate `depth % 2` at $d$, ensuring that both the opening and closing brackets of that pair are assigned to the exact same group ($d \bmod 2$).

### ✅ Why This Works

- **Valid Subsequences**: Because a matching pair `(` and `)` at depth level $d$ receives the exact same group label ($d \bmod 2$), all nested pairs remain complete within their assigned subsequence, keeping both subsequences valid parentheses strings.
- **Minimizing Max Depth**: By placing alternate levels into alternate groups (levels 1, 3, 5... into one group and levels 2, 4, 6... into the other), the nesting depth of each subgroup is at most $\lceil \text{max\_depth} / 2 \rceil$, which is the theoretical minimum possible.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of `seq`. The algorithm iterates through the string once, performing constant time $\mathcal{O}(1)$ operations per character.
- **Space Complexity:** $\mathcal{O}(n)$ required for the output array `res`. Beyond the result array, only $\mathcal{O}(1)$ auxiliary memory is used (`depth`, `i`, `ch`).

### 🧠 DSA Pattern

- **Greedy / Parity Splitting**: Using depth counting and parity (`depth % 2`) to evenly distribute nested structures across two sets.

### ⚠️ Common Mistakes

- **Incorrect Order of Operations for `')'`**: If you decrement `depth` *before* assigning `res[i] = depth % 2`, the closing parenthesis will be evaluated at depth $d - 1$ while its matching opening parenthesis was evaluated at depth $d$. This would put them in different groups and invalidate the parentheses sequences.
- **Incorrect Order of Operations for `'('`**: If you assigned `res[i]` *before* incrementing `depth`, depth 1 would be evaluated as $0 \bmod 2 = 0$, but the matching `)` at depth 1 would be evaluated as $1 \bmod 2 = 1$, creating a mismatch.

### 🚀 Optimization Notes

- This solution is already optimal in terms of both time ($\mathcal{O}(n)$) and auxiliary space ($\mathcal{O}(1)$ beyond the required output array).
- Calling `seq.charAt(i)` inside the loop is lightweight in Java. Optionally converting the string to a `char[]` via `seq.toCharArray()` can slightly improve execution time due to direct array access, but performance is already $\mathcal{O}(n)$.
