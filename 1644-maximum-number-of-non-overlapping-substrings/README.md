<h2><a href="https://leetcode.com/problems/maximum-number-of-non-overlapping-substrings">Maximum Number of Non-Overlapping Substrings</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given a string <code>s</code> of lowercase letters, you need to find the maximum number of <strong>non-empty</strong> substrings of <code>s</code> that meet the following conditions:</p>

<ol>
	<li>The substrings do not overlap, that is for any two substrings <code>s[i..j]</code> and <code>s[x..y]</code>, either <code>j &lt; x</code> or <code>i &gt; y</code> is true.</li>
	<li>A substring that contains a certain character <code>c</code> must also contain all occurrences of <code>c</code>.</li>
</ol>

<p>Find <em>the maximum number of substrings that meet the above conditions</em>. If there are multiple solutions with the same number of substrings, <em>return the one with minimum total length. </em>It can be shown that there exists a unique solution of minimum total length.</p>

<p>Notice that you can return the substrings in <strong>any</strong> order.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;adefaddaccc&quot;
<strong>Output:</strong> [&quot;e&quot;,&quot;f&quot;,&quot;ccc&quot;]
<b>Explanation:</b>&nbsp;The following are all the possible substrings that meet the conditions:
[
&nbsp; &quot;adefaddaccc&quot;
&nbsp; &quot;adefadda&quot;,
&nbsp; &quot;ef&quot;,
&nbsp; &quot;e&quot;,
  &quot;f&quot;,
&nbsp; &quot;ccc&quot;,
]
If we choose the first string, we cannot choose anything else and we&#39;d get only 1. If we choose &quot;adefadda&quot;, we are left with &quot;ccc&quot; which is the only one that doesn&#39;t overlap, thus obtaining 2 substrings. Notice also, that it&#39;s not optimal to choose &quot;ef&quot; since it can be split into two. Therefore, the optimal way is to choose [&quot;e&quot;,&quot;f&quot;,&quot;ccc&quot;] which gives us 3 substrings. No other solution of the same number of substrings exist.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;abbaccd&quot;
<strong>Output:</strong> [&quot;d&quot;,&quot;bb&quot;,&quot;cc&quot;]
<b>Explanation: </b>Notice that while the set of substrings [&quot;d&quot;,&quot;abba&quot;,&quot;cc&quot;] also has length 3, it&#39;s considered incorrect since it has larger total length.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s</code> contains only lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem requires finding the maximum number of non-overlapping valid substrings (and minimizing total length as a tie-breaker). A valid substring containing a character $c$ must contain **all** occurrences of $c$.

The key observation in your solution is:
1. Every valid substring must have a start index $L$ and an end index $R$ such that for every character in $s[L..R]$, all of its occurrences lie entirely within $[L, R]$.
2. To find valid candidate intervals, you only need to consider starting an interval at the **first occurrence** of each distinct character present in $s$.
3. Starting from `L = first[c]`, you expand the right boundary `R` whenever you encounter a character whose last occurrence is further right. If during expansion you encounter a character whose first occurrence is to the *left* of `L` (`first[c] < L`), then `L` cannot be the true start of a valid interval without extending left—so this candidate is discarded (returns `-1`).
4. Once all valid candidate intervals are formed, the problem transforms into the classic **Interval Scheduling Problem**: pick the maximum number of non-overlapping intervals by greedily picking the interval that ends earliest.

---

### 🔍 Approach

1. **Precomputation (`first` and `last` arrays)**:
   - You initialize two 26-element arrays, `first` (filled with $N$) and `last` (filled with $-1$).
   - You traverse `s` once from $0$ to $N-1$ to record the index of the first occurrence `first[c]` and the last occurrence `last[c]` for each character $c \in ['a' .. 'z']$.

2. **Finding Valid Intervals (`getRight` helper)**:
   - For each character present in $s$, set $L = \text{first}[c]$ as a candidate start position.
   - Pass $L$ to `getRight(...)`:
     - Set initial right boundary $R = \text{last}[s[L]]$.
     - Loop index $i$ from $L$ up to $R$:
       - If $s[i]$ has a first occurrence strictly before $L$ (`first[c] < L`), return `-1` immediately because an valid interval starting at $L$ cannot exist without expanding to the left.
       - Otherwise, extend $R = \max(R, \text{last}[c])$.
     - Return $R$ if the loop completes successfully.
   - If `getRight` returns a valid $R \neq -1$, add $[L, R]$ to `intervals`.

3. **Sorting Intervals**:
   - You sort `intervals` using a custom comparator:
     - Primary key: Right endpoint $R$ in ascending order (earliest end time first).
     - Secondary key: Left endpoint $L$ in descending order (shorter interval length first).

4. **Greedy Interval Selection**:
   - Maintain `lastEnd = -1`.
   - Iterate through sorted `intervals`: if $L > \text{lastEnd}$, take the substring `s.substring(L, R + 1)` and update `lastEnd = R`.

---

### 🧩 Algorithm

#### 1. Range Expansion (`getRight`)
Given a fixed candidate start index $L$:
$$\text{Let } R = \text{last}[s[L]]$$
$$\text{For } i = L \text{ to } R:$$
$$\text{If } \text{first}[s[i]] < L \implies \text{return } -1$$
$$R = \max(R, \text{last}[s[i]])$$
$$\text{Return } R$$

#### 2. Interval Scheduling Sorting Strategy
Sort pairs $(L, R)$ by:
$$a[1] \neq b[1] \implies a[1] < b[1]$$
$$a[1] == b[1] \implies a[0] > b[0]$$

#### 3. Greedy Selection Invariant
By choosing intervals sorted by $R$ ascending, any chosen interval ends as early as possible, leaving the maximum room for remaining non-overlapping intervals.

---

### ✅ Why This Works

- **Validity of Intervals**: A range $[L, R]$ is valid if and only if no character inside $[L, R]$ extends outside $[L, R]$. `getRight` guarantees this by extending $R$ whenever a character inside reaches further right, and rejecting the starting point $L$ if any character inside reaches further left.
- **Sufficiency of Start Points**: Checking only $L = \text{first}[c]$ for all 26 characters is sufficient because any valid substring must start at the first occurrence of its leftmost character.
- **Optimality of Greedy Choice**: Sorting by end position $R$ ascending ensures that at each step, picking the non-overlapping interval with the smallest $R$ maximizes the available space for future intervals. Because smaller intervals ending earlier are preferred, this automatically maximizes the number of substrings while naturally minimizing their length.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$
  - Initial pass to find `first` and `last`: $\mathcal{O}(N)$.
  - Outer loop runs at most 26 times. In `getRight`, the index $i$ goes from $L$ to $R \le N$. Across 26 calls, `getRight` does at most $26 \times N$ steps: $\mathcal{O}(N)$.
  - `intervals` list contains at most 26 elements. Sorting 26 elements takes $\mathcal{O}(26 \log 26) = \mathcal{O}(1)$.
  - Collecting final substrings takes time proportional to total length of chosen strings, bounded by $\mathcal{O}(N)$.
  - Overall time complexity: **$\mathcal{O}(N)$**.

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space (or $\mathcal{O}(N)$ to store the output strings).
  - `first` and `last` arrays take fixed size 26.
  - `intervals` list holds at most 26 interval pairs.

---

### 🧠 DSA Pattern

- **Greedy (Interval Scheduling)**: Sorting intervals by finish time to select the maximum set of mutually non-overlapping intervals.
- **Two Pointers / Variable Range Expansion**: Dynamically extending the right pointer $R$ during verification.
- **Precomputation / Character Index Tracking**: Storing first and last occurrences of characters using fixed-size arrays.

---

### ⚠️ Common Mistakes

1. **Forgetting to check left bounds in range expansion**: If `first[c] < L` were omitted inside `getRight`, intervals that illegally cut off earlier occurrences of a character would be included.
2. **Dynamic $R$ loop bound in `getRight`**: Notice that `i <= R` is evaluated dynamically in Java. If $R$ grows during the loop, `i` continues through the newly expanded range. Re-evaluating $R$ statically at the start of the loop would miss characters added by the expansion.
3. **Sorting order incorrectness**: Sorting by $L$ instead of $R$ during interval selection would fail the greedy property for interval scheduling.

---

### 🚀 Optimization Notes

- **Already Optimal Time Complexity**: The solution runs in $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ auxiliary space, which is optimal because every character in string $s$ must be inspected at least once.
- **At most 26 Candidate Intervals**: Because `intervals.size() <= 26`, operations like sorting and interval overlap checks execute in constant time $\mathcal{O}(1)$.
- **String Substring Overhead**: `s.substring(L, R + 1)` creates a new String object for each answer segment. Since at most 26 substrings are created, this is negligible in terms of memory and execution speed.
