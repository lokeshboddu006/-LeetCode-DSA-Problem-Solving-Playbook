<h2><a href="https://leetcode.com/problems/shortest-and-lexicographically-smallest-beautiful-string">Shortest and Lexicographically Smallest Beautiful String</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a binary string <code>s</code> and a positive integer <code>k</code>.</p>

<p>A substring of <code>s</code> is <strong>beautiful</strong> if the number of <code>1</code>&#39;s in it is exactly <code>k</code>.</p>

<p>Let <code>len</code> be the length of the <strong>shortest</strong> beautiful substring.</p>

<p>Return <em>the lexicographically <strong>smallest</strong> beautiful substring of string </em><code>s</code><em> with length equal to </em><code>len</code>. If <code>s</code> doesn&#39;t contain a beautiful substring, return <em>an <strong>empty</strong> string</em>.</p>

<p>A string <code>a</code> is lexicographically <strong>larger</strong> than a string <code>b</code> (of the same length) if in the first position where <code>a</code> and <code>b</code> differ, <code>a</code> has a character strictly larger than the corresponding character in <code>b</code>.</p>

<ul>
	<li>For example, <code>&quot;abcd&quot;</code> is lexicographically larger than <code>&quot;abcc&quot;</code> because the first position they differ is at the fourth character, and <code>d</code> is greater than <code>c</code>.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;100011001&quot;, k = 3
<strong>Output:</strong> &quot;11001&quot;
<strong>Explanation:</strong> There are 7 beautiful substrings in this example:
1. The substring &quot;<u>100011</u>001&quot;.
2. The substring &quot;<u>1000110</u>01&quot;.
3. The substring &quot;<u>10001100</u>1&quot;.
4. The substring &quot;1<u>00011001</u>&quot;.
5. The substring &quot;10<u>0011001</u>&quot;.
6. The substring &quot;100<u>011001</u>&quot;.
7. The substring &quot;1000<u>11001</u>&quot;.
The length of the shortest beautiful substring is 5.
The lexicographically smallest beautiful substring with length 5 is the substring &quot;11001&quot;.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;1011&quot;, k = 2
<strong>Output:</strong> &quot;11&quot;
<strong>Explanation:</strong> There are 3 beautiful substrings in this example:
1. The substring &quot;<u>101</u>1&quot;.
2. The substring &quot;1<u>011</u>&quot;.
3. The substring &quot;10<u>11</u>&quot;.
The length of the shortest beautiful substring is 2.
The lexicographically smallest beautiful substring with length 2 is the substring &quot;11&quot;.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;000&quot;, k = 1
<strong>Output:</strong> &quot;&quot;
<strong>Explanation:</strong> There are no beautiful substrings in this example.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 100</code></li>
	<li><code>1 &lt;= k &lt;= s.length</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution uses a brute-force substring generation strategy. Since string length $N$ is very small ($N \le 100$), checking every possible substring is computationally feasible.

To find the shortest and lexicographically smallest string with exactly $k$ ones:
1. Generate all possible substrings of length at least $k$ (since a string with $k$ ones must have a length of at least $k$).
2. Count the number of `'1'`s in each substring by stripping away all `'0'`s and checking if the resulting length equals $k$.
3. Keep track of the best string seen so far by prioritizing shorter length first, and lexicographically smaller string second.

### 🔍 Approach

1. **Initialization**:
   - `r` is initialized to `""` (empty string) to store the best valid substring found.
   - `n` stores the length of `s`.

2. **Substring Generation**:
   - The outer loop `i` runs from `0` to `n - 1`, representing the starting index of the substring.
   - The inner loop `j` runs from `i + k` to `n`, representing the ending index (exclusive). Starting `j` at `i + k` skips substrings shorter than length $k$.

3. **Counting 1s**:
   - `String t = s.substring(i, j)` extracts the candidate substring.
   - `t.replace("0", "").length() == k` removes all `'0'` characters from `t`. If the length of the remaining string equals `k`, then `t` contains exactly `k` ones.

4. **Updating Best Result (`r`)**:
   - The solution updates `r = t` if any of these conditions hold:
     - `r.isEmpty()`: This is the first valid beautiful substring found.
     - `t.length() < r.length()`: `t` is strictly shorter than the current best substring.
     - `t.length() == r.length() && t.compareTo(r) < 0`: `t` has the same length as `r`, but is lexicographically smaller.

5. **Return**:
   - Returns `r`. If no beautiful substring was found, `r` remains `""`.

### 🧩 Algorithm

The exact algorithm implemented in the code:

1. `r = ""`
2. For $i = 0$ to $n - 1$:
   3. For $j = i + k$ to $n$:
      4. $t = s[i \dots j-1]$
      5. If $\text{length}(t.\text{replaceAll}('0', '')) == k$:
         6. If $r = \text{""}$ OR $\text{length}(t) < \text{length}(r)$ OR ($\text{length}(t) == \text{length}(r)$ AND $t <_{\text{lex}} r$):
            7. $r = t$
8. Return $r$

### ✅ Why This Works

- **Completeness**: The nested loops test all contiguous substrings of length $\ge k$. Since any beautiful substring must contain $k$ ones, its length must be at least $k$.
- **Correct Counting**: Removing `'0'` from a binary string leaves only `'1'`s. Thus, `t.replace("0", "").length()` gives the exact count of `'1'`s in `t`.
- **Tie-Breaking Order**: By checking `t.length() < r.length()` before `t.compareTo(r) < 0`, the algorithm correctly enforces the priority:
  1. Minimum length
  2. Lexicographically smallest among strings of that minimum length

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^3)$
  - Generating all substring pairs $(i, j)$ takes $\mathcal{O}(N^2)$ iterations.
  - Inside the loop, `s.substring(i, j)` takes $\mathcal{O}(N)$ time.
  - `t.replace("0", "")` scans string `t` and builds a new string in $\mathcal{O}(N)$ time.
  - `t.compareTo(r)` takes $\mathcal{O}(N)$ time in the worst case.
  - Total time: $N^2 \times \mathcal{O}(N) = \mathcal{O}(N^3)$. Given $N \le 100$, $N^3 \approx 10^6$ operations, which easily runs within the time limit.

- **Space Complexity**: $\mathcal{O}(N)$ auxiliary space per iteration
  - Creating temporary strings `t` and `t.replace("0", "")` allocates memory proportional to the length of substring $t$, which is at most $N$.

### 🧠 DSA Pattern

- **Brute Force / Exhaustive Search**: Generating all subsegments of an array/string.
- **String Manipulation**: Substring extraction, character replacement, and lexicographical string comparison.

### ⚠️ Common Mistakes

1. **Incorrect inner loop start**: Starting $j$ at $i$ or $i+1$ instead of $i+k$ can cause unnecessary substring extractions for strings that cannot possibly contain $k$ ones.
2. **Confusing string comparison**: Using `==` or `.equals()` instead of `compareTo()` when comparing lexicographical order.
3. **Loop bounds**: Using `j < n` instead of `j <= n` in the inner loop, which would miss substrings that extend to the end of the string.

### 🚀 Optimization Notes

- **Current Efficiency**: For the problem constraint $N \le 100$, this solution runs well within the 2-second time limit.
- **String Allocations**: `t.replace("0", "")` creates multiple intermediate String objects in memory during each loop iteration. A simple character-counting loop or keeping track of ones dynamically would avoid allocating new string objects on every check.
