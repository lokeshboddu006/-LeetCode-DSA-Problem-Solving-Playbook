<h2><a href="https://leetcode.com/problems/longest-palindromic-substring">Longest Palindromic Substring</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a string <code>s</code>, return <em>the longest</em> <span data-keyword="palindromic-string"><em>palindromic</em></span> <span data-keyword="substring-nonempty"><em>substring</em></span> in <code>s</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;babad&quot;
<strong>Output:</strong> &quot;bab&quot;
<strong>Explanation:</strong> &quot;aba&quot; is also a valid answer.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;cbbd&quot;
<strong>Output:</strong> &quot;bb&quot;
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 1000</code></li>
	<li><code>s</code> consist of only digits and English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

A palindrome is symmetric around its center. That means if you start at the center of a palindrome and move outward in both directions, the characters on the left and right will match.

Since a palindrome can have either an **odd length** (e.g., `"aba"`, centered at `'b'`) or an **even length** (e.g., `"abba"`, centered between `'b'` and `'b'`), this solution tests every possible center index in the string and expands outward as far as possible to find the longest symmetric substring.

### 🔍 Approach

1. **Track State**:
   - `start`: Stores the starting index of the longest palindrome found so far.
   - `maxLen`: Stores the length of the longest palindrome found so far.

2. **Iterate Over Centers**:
   - Loop through each index `i` from `0` to `s.length() - 1`.
   - Treat `i` as the center of potential palindromes.

3. **Expand in Both Directions**:
   - Call helper method `expand(s, i, i)` to find the length of the longest **odd-length** palindrome centered at character `i`.
   - Call helper method `expand(s, i, i + 1)` to find the length of the longest **even-length** palindrome centered between characters `i` and `i + 1`.
   - Take the maximum of both lengths: `len = Math.max(...)`.

4. **Update Best Match**:
   - If `len > maxLen`, update `maxLen = len`.
   - Calculate the starting index of this new longest palindrome using:
     `start = i - (len - 1) / 2;`

5. **Helper Function `expand(s, l, r)`**:
   - Uses two pointers `l` and `r`.
   - Decrements `l` and increments `r` as long as indices are within bounds (`l >= 0 && r < s.length()`) and characters match (`s.charAt(l) == s.charAt(r)`).
   - Once the loop stops, pointers `l` and `r` are both one step past the boundary of the valid palindrome.
   - The length of the valid palindrome is returned as `r - l - 1`.

6. **Return Result**:
   - Extract and return `s.substring(start, start + maxLen)`.

### 🧩 Algorithm

- **Expand Around Center Strategy**:
  - For each center candidate $i \in [0, N-1]$:
    - **Odd-length Expansion**: set $l = i, r = i$.
    - **Even-length Expansion**: set $l = i, r = i + 1$.
    - While $l \ge 0 \land r < N \land s[l] == s[r]$:
      - $l \leftarrow l - 1$
      - $r \leftarrow r + 1$
    - Palindrome length is $(r - 1) - (l + 1) + 1 = r - l - 1$.

- **Start Index Mapping**:
  - $start = i - \lfloor \frac{len - 1}{2} \rfloor$
  - Works for odd length ($len = 2k + 1 \implies \frac{2k}{2} = k$)
  - Works for even length ($len = 2k \implies \frac{2k - 1}{2} = k - 1$)

### ✅ Why This Works

- Every palindromic substring has a center (either a single character or the gap between two adjacent characters).
- By checking all $2N - 1$ possible centers ($N$ single-character centers and $N - 1$ two-character centers) and expanding outwards until character mismatch or boundary hit, the algorithm guarantees finding every maximal palindrome centered at each position.
- Tracking the maximum length (`maxLen`) and updating `start` ensures the global maximum palindromic substring is captured and sliced correctly at the end.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^2)$ where $N$ is the length of string `s`.
  - There are $2N - 1$ possible centers.
  - Expanding around a center takes $\mathcal{O}(N)$ time in the worst case (e.g., string `"aaaaa"`).
  - Overall time is $\mathcal{O}(N \times N) = \mathcal{O}(N^2)$.

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space.
  - The pointers `l`, `r`, `i`, `start`, `maxLen`, and `len` use constant extra space.
  - String slicing `s.substring(...)` takes $\mathcal{O}(N)$ memory for the returned string output.

### 🧠 DSA Pattern

- **Two Pointers** (Expand Around Center)

### ⚠️ Common Mistakes

1. **Incorrect Length Formula**:
   - Returning `r - l` or `r - l + 1` in `expand` instead of `r - l - 1`. Since `l` and `r` step out of bounds *after* the last match, the valid range is $[l + 1, r - 1]$. The length is $(r - 1) - (l + 1) + 1 = r - l - 1$.

2. **Incorrect Start Index Calculation**:
   - Writing `i - len / 2` instead of `i - (len - 1) / 2`. Integer division flooring for `(len - 1) / 2` is necessary to unify the start index calculation for both odd and even lengths.

3. **Missing Even-Length Palindromes**:
   - Forgetting to call `expand(s, i, i + 1)`, which causes the algorithm to fail on palindromes with even lengths like `"baab"`.

### 🚀 Optimization Notes

- This solution is already optimal in terms of auxiliary space complexity ($\mathcal{O}(1)$ space).
- For the given problem constraint ($N \le 1000$), this $\mathcal{O}(N^2)$ solution easily runs well within time limits.
