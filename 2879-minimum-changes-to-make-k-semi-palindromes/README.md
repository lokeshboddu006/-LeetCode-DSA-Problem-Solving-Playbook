<h2><a href="https://leetcode.com/problems/minimum-changes-to-make-k-semi-palindromes">Minimum Changes to Make K Semi-palindromes</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given a string <code>s</code> and an integer <code>k</code>, partition <code>s</code> into <code>k</code> <strong><span data-keyword="substring-nonempty">substrings</span></strong> such that the letter changes needed to make each substring a <strong>semi-palindrome</strong>&nbsp;are minimized.</p>

<p>Return the <em><strong>minimum</strong> number of letter changes</em> required<em>.</em></p>

<p>A <strong>semi-palindrome</strong> is a special type of string that can be divided into <strong><span data-keyword="palindrome">palindromes</span></strong> based on a repeating pattern. To check if a string is a semi-palindrome:​</p>

<ol>
	<li>Choose a positive divisor <code>d</code> of the string&#39;s length. <code>d</code> can range from <code>1</code> up to, but not including, the string&#39;s length. For a string of length <code>1</code>, it does not have a valid divisor as per this definition, since the only divisor is its length, which is not allowed.</li>
	<li>For a given divisor <code>d</code>, divide the string into groups where each group contains characters from the string that follow a repeating pattern of length <code>d</code>. Specifically, the first group consists of characters at positions <code>1</code>, <code>1 + d</code>, <code>1 + 2d</code>, and so on; the second group includes characters at positions <code>2</code>, <code>2 + d</code>, <code>2 + 2d</code>, etc.</li>
	<li>The string is considered a semi-palindrome if each of these groups forms a palindrome.</li>
</ol>

<p>Consider the string <code>&quot;abcabc&quot;</code>:</p>

<ul>
	<li>The length of <code>&quot;abcabc&quot;</code> is <code>6</code>. Valid divisors are <code>1</code>, <code>2</code>, and <code>3</code>.</li>
	<li>For <code>d = 1</code>: The entire string <code>&quot;abcabc&quot;</code> forms one group. Not a palindrome.</li>
	<li>For <code>d = 2</code>:
	<ul>
		<li>Group 1 (positions <code>1, 3, 5</code>): <code>&quot;acb&quot;</code></li>
		<li>Group 2 (positions <code>2, 4, 6</code>): <code>&quot;bac&quot;</code></li>
		<li>Neither group forms a palindrome.</li>
	</ul>
	</li>
	<li>For <code>d = 3</code>:
	<ul>
		<li>Group 1 (positions <code>1, 4</code>): <code>&quot;aa&quot;</code></li>
		<li>Group 2 (positions <code>2, 5</code>): <code>&quot;bb&quot;</code></li>
		<li>Group 3 (positions <code>3, 6</code>): <code>&quot;cc&quot;</code></li>
		<li>All groups form palindromes. Therefore, <code>&quot;abcabc&quot;</code> is a semi-palindrome.</li>
	</ul>
	</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1: </strong></p>

<div class="example-block" style="border-color: var(--border-tertiary); border-left-width: 2px; color: var(--text-secondary); font-size: .875rem; margin-bottom: 1rem; margin-top: 1rem; overflow: visible; padding-left: 1rem;">
<p><strong>Input: </strong> <span class="example-io" style="font-family: Menlo,sans-serif; font-size: 0.85rem;"> s = &quot;abcac&quot;, k = 2 </span></p>

<p><strong>Output: </strong> <span class="example-io" style="font-family: Menlo,sans-serif; font-size: 0.85rem;"> 1 </span></p>

<p><strong>Explanation: </strong> Divide <code>s</code> into <code>&quot;ab&quot;</code> and <code>&quot;cac&quot;</code>. <code>&quot;cac&quot;</code> is already semi-palindrome. Change <code>&quot;ab&quot;</code> to <code>&quot;aa&quot;</code>, it becomes semi-palindrome with <code>d = 1</code>.</p>
</div>

<p><strong class="example">Example 2: </strong></p>

<div class="example-block" style="border-color: var(--border-tertiary); border-left-width: 2px; color: var(--text-secondary); font-size: .875rem; margin-bottom: 1rem; margin-top: 1rem; overflow: visible; padding-left: 1rem;">
<p><strong>Input: </strong> <span class="example-io" style="font-family: Menlo,sans-serif; font-size: 0.85rem;"> s = &quot;abcdef&quot;, k = 2 </span></p>

<p><strong>Output: </strong> <span class="example-io" style="font-family: Menlo,sans-serif; font-size: 0.85rem;"> 2 </span></p>

<p><strong>Explanation: </strong> Divide <code>s</code> into substrings <code>&quot;abc&quot;</code> and <code>&quot;def&quot;</code>. Each&nbsp;needs one change to become semi-palindrome.</p>
</div>

<p><strong class="example">Example 3: </strong></p>

<div class="example-block" style="border-color: var(--border-tertiary); border-left-width: 2px; color: var(--text-secondary); font-size: .875rem; margin-bottom: 1rem; margin-top: 1rem; overflow: visible; padding-left: 1rem;">
<p><strong>Input: </strong> <span class="example-io" style="font-family: Menlo,sans-serif; font-size: 0.85rem;"> s = &quot;aabbaa&quot;, k = 3 </span></p>

<p><strong>Output: </strong> <span class="example-io" style="font-family: Menlo,sans-serif; font-size: 0.85rem;"> 0 </span></p>

<p><strong>Explanation: </strong> Divide <code>s</code> into substrings <code>&quot;aa&quot;</code>, <code>&quot;bb&quot;</code> and <code>&quot;aa&quot;</code>.&nbsp;All are already semi-palindromes.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= s.length &lt;= 200</code></li>
	<li><code>1 &lt;= k &lt;= s.length / 2</code></li>
	<li><code>s</code> contains only lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution breaks the problem into two distinct phases:

1. **Substring Cost Precomputation**: Calculate the minimum number of character modifications needed to turn any substring `s[i..j]` (of length $\ge 2$) into a valid semi-palindrome. A semi-palindrome of length $l$ relies on choosing a proper divisor $d < l$, grouping indices with the same remainder modulo $d$, and ensuring each group forms a palindrome.
2. **Partition Dynamic Programming**: Use dynamic programming to find the optimal points to partition the string $s$ into $k$ non-overlapping valid substrings such that the total cost is minimized.

---

### 🔍 Approach

#### Step 1: Precomputing Substring Costs (`c[i][j]`)
- Array `c[i][j]` stores the minimum character changes required to transform substring `s[i..j]` into a semi-palindrome.
- The outer loops iterate over all possible start indices `i` and end indices `j` with `j > i` (ensuring length $l = j - i + 1 \ge 2$).
- For each substring length $l$, the code iterates through all possible proper divisors `d` (where $1 \le d < l$ and $l \pmod d == 0$).
- For a chosen divisor $d$, characters are split into $d$ remainder groups. For offset `r` ($0 \le r < d$):
  - Group start pointer: `a = i + r`
  - Group end pointer: `b = j - d + 1 + r`
  - A two-pointer approach moves `a` forward by `d` and `b` backward by `d` until they cross (`a < b`), incrementing a change counter `x` whenever `s.charAt(a) != s.charAt(b)`.
- `c[i][j]` keeps track of the minimum changes across all valid divisors `d`.

#### Step 2: Partitioning via DP (`f[i][j]`)
- DP table `f[i][j]` represents the minimum cost to partition the prefix of length `i` (`s[0..i-1]`) into `j` valid semi-palindrome substrings.
- Matrix `f` is initialized with value `n` (representing infinity), with the base case `f[0][0] = 0`.
- Three nested loops compute the state transitions:
  - `i` from $1$ to $n$: prefix length.
  - `j` from $1$ to $k$: number of substrings.
  - `p` from $0$ to $i - 2$: the split point before the last substring `s[p..i-1]`. The upper bound `p < i - 1` guarantees that the final substring `s[p..i-1]` has a length of at least 2.
- The result for partitioning the full string into $k$ parts is stored in `f[n][k]`.

---

### 🧩 Algorithm

#### 1. Precomputation Recurrence / Logic
For each substring $s[i..j]$ of length $l = j - i + 1$:
$$c[i][j] = \min_{\substack{1 \le d < l \\ l \pmod d = 0}} \sum_{r=0}^{d-1} \text{mismatch\_count}(s, i, j, d, r)$$

Where $\text{mismatch\_count}$ compares $s[i + r + m \cdot d]$ with $s[j - d + 1 + r - m \cdot d]$ for $m \ge 0$ while the left index is less than the right index.

#### 2. Partition DP State & Transition
- **State**: `f[i][j]` = minimum modifications to divide prefix `s[0...i-1]` into `j` semi-palindromic substrings.
- **Base Case**:
  - $f[0][0] = 0$
  - $f[i][j] = \infty$ for all other $(i, j)$
- **Transition**:
$$f[i][j] = \min_{0 \le p \le i-2} \left( f[p][j-1] + c[p][i-1] \right)$$
- **Target**: `f[n][k]`

---

### ✅ Why This Works

1. **Subproblem Independence**: The cost of making any substring `s[p..i-1]` a semi-palindrome depends only on the characters within that substring. Precomputing `c[i][j]` for all pairs allows constant-time lookup during DP transitions.
2. **Valid Semi-palindrome Constraint**: A semi-palindrome must have a length of at least 2 because a length-1 string has no proper divisor $d < 1$. The code enforces this in two places:
   - Precomputation starts `j` at `i + 1`.
   - DP transition limits `p < i - 1`, ensuring the last block `s[p..i-1]` has length $(i - 1) - p + 1 = i - p \ge 2$.
3. **Exhaustive Optimization**: The DP explores every valid combination of partition points, ensuring the global minimum operational cost for $k$ substrings is found.

---

### ⏱️ Complexity

- **Time Complexity**:
  - **Cost Precomputation**: For each substring of length $l$, checking divisor $d$ takes $O(l)$ operations across all $r$. Thus, total precomputation takes $O(n^2 \cdot \sum \text{divisors}(l)) \approx O(n^3 \cdot \tau(n))$, where $\tau(n)$ is the average number of divisors for numbers up to $n$.
  - **DP Transitions**: $O(n^2 \cdot k)$ states and transitions.
  - **Total Time Complexity**: $O(n^3)$ operations (for $n \le 200$, this executes well within the 2-second time limit).

- **Space Complexity**:
  - `c`: $O(n^2)$ space to store substring costs.
  - `f`: $O(n \cdot k)$ space for DP table.
  - **Total Space Complexity**: $O(n^2 + n \cdot k)$ auxiliary space.

---

### 🧠 DSA Pattern

- **Dynamic Programming (Partition DP)**: Solving multi-stage decision problems by splitting strings into $k$ optimal segments.
- **Precomputation**: Pre-calculating interval costs to avoid redundant calculations inside DP loops.
- **Two Pointers**: Used inside the divisor loops to compare symmetric characters within each modulo group.

---

### ⚠️ Common Mistakes

1. **Handling Substrings of Length 1**: Forgetting that length 1 substrings cannot be semi-palindromes. In this implementation, limiting `j` to start at `i + 1` and DP split point `p` up to `i - 2` prevents length-1 substrings.
2. **Incorrect Two-Pointer Boundaries**: Calculating the right boundary index `b = j - d + 1 + r` incorrectly can lead to `StringIndexOutOfBoundsException` or wrong comparison pairs.
3. **Using Improper Divisor $d = l$**: The problem statement specifies $d$ must be strictly less than $l$. The inner loop condition `d < l` correctly skips $d = l$.

---

### 🚀 Optimization Notes

- **Decoupled Precomputation**: Separating the precomputation of `c[i][j]` from the DP transitions avoids recomputing character mismatch costs multiple times.
- **Optimal DP State Size**: The DP matrix size $(n + 1) \times (k + 1)$ is minimal and sufficient for standard 1-based indexing of prefix lengths and substring counts.
