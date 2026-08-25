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
The core idea behind this solution is a pruned substring search combined with a two-pointer palindrome check. 

Instead of checking every single substring of length $1$ to $N$, the algorithm maintains the length of the longest palindrome found so far (`maxLen`). As it scans through all possible starting indices `i`, it skips candidate ending indices `j` that would yield substrings shorter than or equal to `maxLen`. Whenever a candidate substring is strictly longer than `maxLen`, it checks if that substring is a palindrome. If it is, `maxLen` and `maxStr` are updated, which automatically increases the threshold for all future checks.

### 🔍 Approach
1. **Base Case Check**: 
   - If `s.length() <= 1`, the string itself is already the longest palindrome, so it returns `s` immediately.

2. **Tracking Variables**:
   - `maxLen`: Initialized to `1` (since any single character is a palindrome of length 1).
   - `maxStr`: Initialized to `s.substring(0, 1)`.

3. **Generating Candidate Substrings**:
   - **Outer loop (`i`)**: Iterates through every possible starting position of a substring from `0` to `s.length() - 1`.
   - **Inner loop (`j`)**: Iterates through ending positions (exclusive index) starting from `i + maxLen` up to `s.length()`.
   - By starting `j` at `i + maxLen`, the algorithm attempts to look only at substrings that could potentially match or exceed the current maximum length.

4. **Filtering and Validation**:
   - The condition `j - i > maxLen` ensures that only substrings strictly longer than `maxLen` are tested.
   - It creates a candidate substring via `s.substring(i, j)` and passes it to the `isPalindrome` helper function.
   - If `isPalindrome` returns `true`, `maxLen` is updated to `j - i`, and `maxStr` is updated to `s.substring(i, j)`.

5. **Helper Function (`isPalindrome`)**:
   - Uses two pointers (`left = 0` and `right = str.length() - 1`).
   - Moves `left` inward to the right and `right` inward to the left, verifying that `str.charAt(left) == str.charAt(right)` at every step.
   - Returns `false` on the first character mismatch; returns `true` if pointers cross without mismatch.

### 🧩 Algorithm

#### Main Method (`longestPalindrome`):
1. If $|s| \le 1$, return $s$.
2. Set $\text{maxLen} \leftarrow 1$, $\text{maxStr} \leftarrow s[0..1]$.
3. For $i$ from $0$ to $|s| - 1$:
   - For $j$ from $i + \text{maxLen}$ to $|s|$:
     - If $j - i > \text{maxLen}$:
       - Extract $sub \leftarrow s[i..j]$.
       - If $\text{isPalindrome}(sub)$ is true:
         - $\text{maxLen} \leftarrow j - i$
         - $\text{maxStr} \leftarrow sub$
4. Return $\text{maxStr}$.

#### Helper Method (`isPalindrome`):
1. Set $l \leftarrow 0$, $r \leftarrow |str| - 1$.
2. While $l < r$:
   - If $str[l] \neq str[r]$, return `false`.
   - Increment $l$, decrement $r$.
3. Return `true`.

### ✅ Why This Works
- **Completeness**: The outer loop visits every valid starting index $i$. The inner loop checks candidate end indices $j$. Since `maxLen` only increases, skipping substrings of length $\le \text{maxLen}$ cannot cause the algorithm to miss a longer palindrome.
- **Correctness of Validation**: The `isPalindrome` function checks character symmetry from the outer edges to the center. If all symmetric pairs match, the string is guaranteed to be a palindrome.

### ⏱️ Complexity
- **Time Complexity**: $\mathcal{O}(N^3)$ worst-case.
  - The outer loop runs $N$ times.
  - The inner loop runs up to $N$ times.
  - Inside the inner loop, `s.substring(i, j)` takes $\mathcal{O}(L)$ time (where $L = j - i$), and `isPalindrome` takes $\mathcal{O}(L)$ time.
  - In the worst case (for example, a string with all distinct characters where `maxLen` remains $1$), the nested loops evaluate $\mathcal{O}(N^2)$ candidate substrings, taking $\mathcal{O}(N)$ time per check, resulting in $\mathcal{O}(N^3)$ overall operations.
- **Space Complexity**: $\mathcal{O}(N)$ auxiliary space.
  - On each loop iteration that passes the length condition, `s.substring(i, j)` allocates a new `String` object of length up to $N$.

### 🧠 DSA Pattern
- **Brute Force with Pruning**: Generating substring candidate pairs $(i, j)$ while skipping lengths $\le \text{maxLen}$.
- **Two Pointers**: Used inside `isPalindrome` to check character symmetry from both ends toward the middle.

### ⚠️ Common Mistakes
- **Redundant First Iteration in Inner Loop**: The inner loop starts at `j = i + maxLen`. At this initial value, `j - i` is equal to `maxLen`. Thus, the condition `j - i > maxLen` evaluates to `false` on the very first step of the inner loop every time.
- **Heap Allocation Overhead**: Calling `s.substring(i, j)` before checking if the substring is a palindrome creates many short-lived `String` instances in memory, increasing garbage collection overhead during execution.

### 🚀 Optimization Notes
- **Avoid Substring Allocation**: Instead of slicing the string with `s.substring(i, j)` and passing the substring to `isPalindrome(String str)`, you can pass `s`, `i`, and `j - 1` directly into `isPalindrome(String s, int left, int right)`. This avoids allocating new String objects until a strictly longer palindrome is actually confirmed.
- **Adjust Loop Start Index**: Starting `j` at `i + maxLen + 1` instead of `i + maxLen` avoids the redundant first iteration where `j - i > maxLen` fails.
