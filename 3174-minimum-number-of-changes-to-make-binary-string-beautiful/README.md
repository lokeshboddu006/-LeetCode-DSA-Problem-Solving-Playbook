<h2><a href="https://leetcode.com/problems/minimum-number-of-changes-to-make-binary-string-beautiful">Minimum Number of Changes to Make Binary String Beautiful</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a <strong>0-indexed</strong> binary string <code>s</code> having an even length.</p>

<p>A string is <strong>beautiful</strong> if it&#39;s possible to partition it into one or more substrings such that:</p>

<ul>
	<li>Each substring has an <strong>even length</strong>.</li>
	<li>Each substring contains <strong>only</strong> <code>1</code>&#39;s or <strong>only</strong> <code>0</code>&#39;s.</li>
</ul>

<p>You can change any character in <code>s</code> to <code>0</code> or <code>1</code>.</p>

<p>Return <em>the <strong>minimum</strong> number of changes required to make the string </em><code>s</code> <em>beautiful</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;1001&quot;
<strong>Output:</strong> 2
<strong>Explanation:</strong> We change s[1] to 1 and s[3] to 0 to get string &quot;1100&quot;.
It can be seen that the string &quot;1100&quot; is beautiful because we can partition it into &quot;11|00&quot;.
It can be proven that 2 is the minimum number of changes needed to make the string beautiful.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;10&quot;
<strong>Output:</strong> 1
<strong>Explanation:</strong> We change s[1] to 1 to get string &quot;11&quot;.
It can be seen that the string &quot;11&quot; is beautiful because we can partition it into &quot;11&quot;.
It can be proven that 1 is the minimum number of changes needed to make the string beautiful.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;0000&quot;
<strong>Output:</strong> 0
<strong>Explanation:</strong> We don&#39;t need to make any changes as the string &quot;0000&quot; is beautiful already.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s</code> has an even length.</li>
	<li><code>s[i]</code> is either <code>&#39;0&#39;</code> or <code>&#39;1&#39;</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem requires partitioning an even-length binary string into substrings of even length where each substring contains only `0`s or only `1`s. 

Notice that any even-length uniform substring can be broken down into consecutive disjoint pairs of length 2 (e.g., `"0000"` is two pairs of `"00"`). Therefore, for the whole string to be valid, every non-overlapping pair of adjacent characters `(s[0], s[1])`, `(s[2], s[3])`, ..., `(s[i], s[i+1])` must consist of identical characters.

If a pair already has equal characters (`"00"` or `"11"`), it requires `0` changes. If a pair has different characters (`"01"` or `"10"`), changing either character to match the other requires exactly `1` change. Because pairs are independent, summing the required changes for each pair gives the minimum total changes.

### 🔍 Approach

1. **Initialize Counter**: Declare `int ch = 0` to store the total number of character changes required.
2. **Iterate in Steps of 2**: Loop through the string starting at index `i = 0` up to `s.length()`, incrementing `i` by `2` in each iteration (`i += 2`).
3. **Compare Pair**: In each iteration, compare `s.charAt(i)` with `s.charAt(i + 1)`.
   - If `s.charAt(i) != s.charAt(i + 1)`, increment `ch` by 1.
4. **Return Result**: After checking all non-overlapping pairs, return `ch`.

### 🧩 Algorithm

The algorithm uses a **Greedy / Pairwise Inspection** approach:

1. **Loop Invariant**: Before processing index `i`, `ch` holds the minimum number of changes needed to make the prefix `s[0 ... i-1]` beautiful.
2. **Pair Inspection**:
   $$\text{cost}(i) = \begin{cases} 0 & \text{if } s[i] = s[i+1] \\ 1 & \text{if } s[i] \neq s[i+1] \end{cases}$$
3. **Total Cost Accumulation**:
   $$\text{minChanges} = \sum_{k=0}^{\frac{N}{2} - 1} \text{cost}(2k)$$

### ✅ Why This Works

- **Necessary Condition**: Every valid partition into even-length uniform substrings implies that every 2-character block `(s[2k], s[2k+1])` within those substrings must have equal characters.
- **Sufficient Condition**: If every adjacent pair of length 2 has identical characters, each pair itself is a valid uniform substring of even length (length 2), satisfying all conditions.
- **Minimality**: Each mismatched pair `(s[2k] != s[2k+1])` independently requires at least 1 change to become matched. Since pair boundaries are fixed at even indices, changes in one pair do not affect any other pair. Thus, modifying 1 character per mismatched pair is strictly minimal.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of the string `s`. The loop runs $\frac{N}{2}$ times, performing constant-time $\mathcal{O}(1)$ character lookups and comparisons in each iteration.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space, as only a few primitive integer variables (`ch` and `i`) are used.

### 🧠 DSA Pattern

- **Greedy**: Making the locally optimal choice for each non-overlapping pair of length 2 guarantees the globally optimal minimum number of changes.

### ⚠️ Common Mistakes

1. **Incorrect Loop Increment**: Incrementing by `i++` instead of `i += 2`. Doing so would evaluate overlapping pairs (e.g., comparing `s[1]` with `s[2]`), leading to overcounting.
2. **Out of Bounds Error**: Accessing `s.charAt(i + 1)` without a step of 2 or on an odd-length string. The problem guarantees an even length, so `i += 2` safely stays within bounds.

### 🚀 Optimization Notes

- The solution is already **optimal** in both time complexity ($\mathcal{O}(N)$) and space complexity ($\mathcal{O}(1)$).
- **Micro-optimization**: Calling `s.charAt(i)` twice per mismatch could be avoided by converting the string to a character array using `s.toCharArray()` beforehand, but for $N \le 10^5$, `charAt` is already fast enough in Java and avoids allocating extra array memory.
