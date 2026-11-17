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

The problem requires partitioning a string $s$ into $k$ contiguous substrings such that the total number of character changes to make every substring a **semi-palindrome** is minimized.

Your solution solves this using a two-level **Top-Down Dynamic Programming with Memoization** approach:
1. **Partitioning Level (`dfs`)**: Recursively decides where to place substring boundaries to split the string into $k$ parts, memoizing states $(i, k)$ representing the starting index $i$ and remaining partitions $k$.
2. **Cost Calculation Level (`calculate` & `check`)**: For any substring $s[i \dots j]$, calculates the minimum changes needed to make it a semi-palindrome by checking all proper divisors $d$ of the substring's length and memoizing the results in a 2D `cache`.

---

### 🔍 Approach

Your solution is structured into three main component functions:

1. **`minimumChanges(String s, int k)`**:
   - Initializes `dp[n][k + 1]` table with `-1` to memoize subproblems of splitting $s[i \dots n-1]$ into $k$ parts.
   - Initializes `cache[n][n]` table with `-1` to memoize the cost of turning substring $s[i \dots j]$ into a semi-palindrome.
   - Invokes `dfs(s, 0, k, dp, cache)` to get the answer.

2. **`dfs(String s, int i, int k, int[][] dp, int[][] cache)`**:
   - **Base Cases & Pruning**:
     - If $i \ge n$: returns `0` if $k == 0$, else `Integer.MAX_VALUE` (invalid state).
     - Returns memoized `dp[i][k]` if already computed.
     - Early pruning: `if (n - i < k + k)`, returns `Integer.MAX_VALUE` because every semi-palindrome substring must have a length of at least `2`. If remaining characters are less than $2k$, it's impossible.
     - If $k == 1$: the rest of the string $s[i \dots n-1]$ must form 1 semi-palindrome, so it calls `calculate(s, i, n - 1, cache)`.
   - **Transitions**:
     - Iterates over all possible ending indices $j$ from $i$ to $n - 1$.
     - Solves subproblem recursively for the remaining suffix: `temp = dfs(s, j + 1, k - 1, dp, cache)`.
     - If valid (`temp < Integer.MAX_VALUE`), computes cost for current segment `calculate(s, i, j, cache)` and updates `dp[i][k] = min(dp[i][k], temp + changes)`.

3. **`calculate(String s, int i, int j, int[][] cache)`**:
   - Calculates the minimum changes to make substring $s[i \dots j]$ a semi-palindrome.
   - If `len == 1`, returns `Integer.MAX_VALUE` since a length of 1 has no valid proper divisor strictly less than its length.
   - Finds all proper divisors $d$ of `len` ($1 \le d < len$). It iterates $d$ from $1$ up to $\lfloor\sqrt{len}\rfloor$.
   - For each valid divisor pair $(d, num)$ where $d \times num = len$:
     - Calls `check(s, i, d, num)` for stride $d$.
     - If $num$ is also a valid proper divisor ($d \neq num$ and $d \neq 1$), calls `check(s, i, num, d)` for stride $num$.
   - Keeps the minimum cost across all valid divisors and stores it in `cache[i][j]`.

4. **`check(String s, int start, int d, int num)`**:
   - Calculates changes required for substring starting at `start` with a fixed divisor $d$.
   - For each offset $0 \le \text{offset} < d$ (each of the $d$ modulo groups):
     - Uses two pointers `i` and `j` moving inwards within the group by steps of $d$:
       - `i = start + offset`
       - `j = i + d * (num - 1)`
     - Increments `changes` whenever characters at $i$ and $j$ mismatch (`s.charAt(i) != s.charAt(j)`).

---

### 🧩 Algorithm

#### DP State
- $dp[i][k]$: The minimum letter changes needed to split suffix $s[i \dots n-1]$ into $k$ semi-palindrome substrings.

#### Recurrence Relation
$$dp[i][k] = \min_{i \le j < n} \left( dp[j + 1][k - 1] + \text{calculate}(s, i, j) \right)$$

#### Base Cases
- $dp[n][0] = 0$
- $dp[i][1] = \text{calculate}(s, i, n - 1)$
- $dp[i][k] = \infty$ if $(n - i) < 2k$

#### Substring Semi-Palindrome Cost Calculation
$$\text{cache}[i][j] = \min_{\substack{d \mid len \\ 1 \le d < len}} \text{check}(s, i, d, len / d)$$

---

### ✅ Why This Works

1. **Complete Search with Memoization**: The recursive DFS explores all valid substring partition options, guaranteed to find the global minimum by avoiding recalculation of identical subproblems via `dp[i][k]`.
2. **Correct Semi-Palindrome Definition**: A semi-palindrome of length $L$ requires a proper divisor $d < L$ such that each character sequence $s[offset], s[offset + d], s[offset + 2d], \dots$ forms a palindrome. The `check` method directly checks the palindromic symmetry of each offset group using inward two pointers (`i += d`, `j -= d`).
3. **Divisor Optimization**: `calculate` tests all proper divisors of $len$. By checking $d$ up to $\sqrt{len}$, both factors $d$ and $len / d$ are evaluated correctly while strictly ensuring no divisor equals $len$.

---

### ⏱️ Complexity

- **Time Complexity:** 
  - **Substring evaluation (`calculate` & `check`)**:
    - There are $O(n^2)$ total substrings $s[i \dots j]$.
    - For a length $L$, the number of proper divisors is small. For each divisor $d$, `check` visits each character of the substring once ($O(L)$ operations).
    - Thus, filling the `cache` table takes $O(n^3)$ operations in the worst case (or $O(n^2 \cdot \text{divisors}(n) \cdot n)$).
  - **
