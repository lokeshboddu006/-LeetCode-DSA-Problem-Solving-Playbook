<h2><a href="https://leetcode.com/problems/distinct-subsequences-ii">Distinct Subsequences II</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given a string s, return <em>the number of <strong>distinct non-empty subsequences</strong> of</em> <code>s</code>. Since the answer may be very large, return it <strong>modulo</strong> <code>10<sup>9</sup> + 7</code>.</p>
A <strong>subsequence</strong> of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., <code>&quot;ace&quot;</code> is a subsequence of <code>&quot;<u>a</u>b<u>c</u>d<u>e</u>&quot;</code> while <code>&quot;aec&quot;</code> is not.
<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;abc&quot;
<strong>Output:</strong> 7
<strong>Explanation:</strong> The 7 distinct subsequences are &quot;a&quot;, &quot;b&quot;, &quot;c&quot;, &quot;ab&quot;, &quot;ac&quot;, &quot;bc&quot;, and &quot;abc&quot;.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;aba&quot;
<strong>Output:</strong> 6
<strong>Explanation:</strong> The 6 distinct subsequences are &quot;a&quot;, &quot;b&quot;, &quot;ab&quot;, &quot;aa&quot;, &quot;ba&quot;, and &quot;aba&quot;.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;aaa&quot;
<strong>Output:</strong> 3
<strong>Explanation:</strong> The 3 distinct subsequences are &quot;a&quot;, &quot;aa&quot; and &quot;aaa&quot;.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 2000</code></li>
	<li><code>s</code> consists of lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the number of **distinct non-empty subsequences** of a string $s$. 

The core idea behind this solution is to categorize all formed distinct subsequences based on their **last character**:
1. When we encounter a character `c`, we can create new distinct subsequences by appending `c` to **every** distinct subsequence formed so far.
2. We can also create a single-character subsequence consisting of just `c`.
3. If there are currently `sum` total distinct subsequences, appending `c` to all of them plus creating `"c"` gives `1 + sum` total subsequences that end in `c`.
4. However, some subsequences ending in `c` may have already been counted during a previous occurrence of `c`. To avoid double-counting, we replace the previous count of subsequences ending in `c` (`count[c - 'a']`) with the new total (`1 + sum`).

---

### 🔍 Approach

1. **State Tracking**:
   - `count`: An array of size 26 where `count[i]` stores the number of distinct subsequences ending with character `'a' + i`.
   - `sum`: A variable tracking the total number of distinct non-empty subsequences formed so far.

2. **Iteration over `s`**:
   - Convert `s` to a character array using `s.toCharArray()` and iterate over each character `c`.
   - Calculate `total = (1 + sum) % MOD`. This represents the new total count of distinct subsequences ending in `c` (1 for the single character `"c"` plus `sum` for appending `c` to all previously existing distinct subsequences).
   - Compute the net change in distinct subsequences: `total - count[c - 'a']`.
   - Update `sum` by adding this net change (`sum = sum + (total - count[c - 'a'])`).
   - Update `count[c - 'a'] = total` so `count` now reflects the updated number of subsequences ending in `c`.

3. **Result**:
   - Return `(int)(sum % MOD)` at the end.

---

### 🧩 Algorithm

This is a **Dynamic Programming** state tracking approach based on ending characters:

- **State**:
  - $count[ch]$ = Number of distinct subsequences ending with character $ch$.
  - $sum = \sum_{i=0}^{25} count[i]$ (Total distinct subsequences across all ending characters).

- **Transitions for character `c`**:
  $$total = (1 + sum) \pmod{10^9 + 7}$$
  $$sum_{new} = sum_{old} + (total - count[c])$$
  $$count[c] = total$$

---

### ✅ Why This Works

- **Disjoint Partitioning**: Any distinct subsequence must end with some character from `'a'` to `'z'`. Partitioning by the last character guarantees that subsequences ending in different characters (e.g., `"ab"` vs `"aa"`) are counted independently without overlap.
- **Duplicate Prevention**: When character `c` appears again, any new subsequence formed by appending `c` replaces all older subsequences that ended with `c`. Subtracting `count[c - 'a']` removes the stale counts of subsequences ending in `c`, keeping the global `sum` exact and free of duplicates.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of string `s`. We iterate through `s` once and perform $\mathcal{O}(1)$ operations per character.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space for the fixed-size array `long[26]` and variables. (Note: `s.toCharArray()` allocates $\mathcal{O}(N)$ memory temporarily for the char array).

---

### 🧠 DSA Pattern

- **Dynamic Programming** (State Reduction / Ending-character DP)

---

### ⚠️ Common Mistakes

1. **Negative Remainder with Modulo**:
   In `total - count[c - 'a']`, since `total` is modulo'd by `MOD` while `count[c - 'a']` comes from a previous step, `total - count[c - 'a']` can evaluate to a negative value. In Java, `% MOD` on a negative number yields a negative result. Adding `MOD` before taking `% MOD` (i.e., `(total - count[c - 'a'] + MOD) % MOD`) avoids potential negative values.

2. **Forgetting to Subtract Previous Counts**:
   Simply adding `1 + sum` without subtracting `count[c - 'a']` leads to double counting duplicate subsequences generated by repeating characters.

---

### 🚀 Optimization Notes

- **Modulo Maintenance**: Currently, `sum` accumulates directly without being modulo'd during every iteration step. To guarantee `sum` stays safely within standard bounds and avoids negative results when converting to `int`, maintain `sum` with modulo inside the loop:
  `sum = (sum + total - count[c - 'a'] + MOD) % MOD;`
- **Memory Optimization**: Instead of `s.toCharArray()`, using a standard index loop `s.charAt(i)` avoids allocating an $\mathcal{O}(N)$ character array in Java.
