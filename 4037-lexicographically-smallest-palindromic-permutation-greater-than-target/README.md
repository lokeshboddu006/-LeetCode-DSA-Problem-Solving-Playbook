<h2><a href="https://leetcode.com/problems/lexicographically-smallest-palindromic-permutation-greater-than-target">Lexicographically Smallest Palindromic Permutation Greater Than Target</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given two strings <code>s</code> and <code>target</code>, each of length <code>n</code>, consisting of lowercase English letters.</p>

<p>Return the <strong><span data-keyword="lexicographically-smaller-string">lexicographically smallest</span> string</strong> that is <strong>both</strong> a <strong><span data-keyword="palindrome-string">palindromic</span> <span data-keyword="permutation">permutation</span></strong> of <code>s</code> and <strong>strictly</strong> greater than <code>target</code>. If no such permutation exists, return an empty string.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;baba&quot;, target = &quot;abba&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;baab&quot;</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The palindromic permutations of <code>s</code> (in lexicographical order) are <code>&quot;abba&quot;</code> and <code>&quot;baab&quot;</code>.</li>
	<li>The lexicographically smallest permutation that is strictly greater than <code>target</code> is <code>&quot;baab&quot;</code>.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;baba&quot;, target = &quot;bbaa&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;&quot;</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The palindromic permutations of <code>s</code> (in lexicographical order) are <code>&quot;abba&quot;</code> and <code>&quot;baab&quot;</code>.</li>
	<li>None of them is lexicographically strictly greater than <code>target</code>. Therefore, the answer is <code>&quot;&quot;</code>.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;abc&quot;, target = &quot;abb&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;&quot;</span></p>

<p><strong>Explanation:</strong></p>

<p><code>s</code> has no palindromic permutations. Therefore, the answer is <code>&quot;&quot;</code>.</p>
</div>

<p><strong class="example">Example 4:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;aac&quot;, target = &quot;abb&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;aca&quot;</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The only palindromic permutation of <code>s</code> is <code>&quot;aca&quot;</code>.</li>
	<li><code>&quot;aca&quot;</code> is strictly greater than <code>target</code>. Therefore, the answer is <code>&quot;aca&quot;</code>.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == s.length == target.length &lt;= 300</code></li>
	<li><code>s</code> and <code>target</code> consist of only lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

A palindrome is completely determined by its first half (plus a middle character if its length $n$ is odd). Therefore, to form a palindromic permutation of $s$, we only need to construct the first $\lceil n / 2 \rceil$ characters using half of the available occurrences of each character.

To find the **lexicographically smallest** palindromic permutation that is **strictly greater** than `target`, we can build this prefix character-by-character from left to right using backtracking:
1. Try characters in ascending order (`'a'` to `'z'`).
2. Keep track of a boolean flag `g` (already strictly greater than `target`). As long as `g` is `false`, the current character cannot be smaller than the corresponding character in `target`.
3. Because we iterate through candidate characters in ascending order, the very first valid complete palindrome we construct that satisfies `s > target` is guaranteed to be the lexicographically smallest answer.

---

### 🔍 Approach

1. **Palindrome Validity & Half-Frequency Setup**:
   - Count frequencies of all characters in $s$ into array `c`.
   - Count how many characters have odd frequencies (`o`). If $o > 1$, a palindrome is impossible, so return `""`.
   - If an odd-frequency character exists, record its index in `m`.
   - Divide each character count in `c` by 2 (via `c[i] /= 2`).

2. **Backtracking Function `f(i, p, g)`**:
   - `i`: Current index in the prefix array `p` (of size `(n + 1) / 2`).
   - `p`: Array storing the first half (and middle character if $n$ is odd).
   - `g`: Boolean indicating whether the prefix built so far is strictly greater than `target[0...i-1]`.

3. **Branching Logic**:
   - **Base Case (`i == p.length`)**: Construct the full palindrome string `s` by combining `p[0...n/2-1]`, middle character (if $n$ is odd), and the reverse of `p[0...n/2-1]`. Check if `s.compareTo(target) > 0`. If so, assign `r = s` and return `true` to stop search immediately.
   - **Odd Length Middle Character (`n % 2 != 0 && i == n / 2`)**: The middle character is fixed to `'a' + m`. If `!g` and this middle character is smaller than `target[i]`, prune this path (return `false`). Otherwise, move to $i+1$.
   - **General Prefix Characters**: Loop through character indices $j$ from $0$ to $25$:
     - Check if count `c[j] > 0`.
     - Pruning: If `!g` and character `ch < target[i]`, skip it (`continue`).
     - Decrement `c[j]`, assign `p[i] = ch`, and recursively call `f(i + 1, p, g || (ch > target[i]))`.
     - If the recursive call returns `true`, return `true` immediately.
     - Otherwise, restore `c[j]++` (backtrack).

---

### 🧩 Algorithm

1. **Initialization**:
   - Calculate frequency array `c` for input string `s`.
   - If odd count $o > 1$, return `""`.
   - Prepare half counts in `c`.

2. **Recursive Backtracking State**:
   - $\text{State}: (i, p, g)$
   - $\text{Base Case}: i = \text{p.length}$
     $$\text{Full String } S = p[0 \dots \lfloor n/2 \rfloor - 1] + (\text{if } n \% 2 \neq 0 \text{ then } p[\lfloor n/2 \rfloor] \text{ else } "") + \text{reverse}(p[0 \dots \lfloor n/2 \rfloor - 1])$$
     $$\text{If } S > \text{target}, \text{ return } true$$

3. **Transitions**:
   - If $n \% 2 \neq 0$ and $i = \lfloor n / 2 \rfloor$:
     $$p[i] = \text{'a'} + m$$
     $$\text{If } \neg g \text{ and } p[i] < target[i], \text{ fail}$$
     $$\text{Recurse } f(i + 1, p, g \lor (p[i] > target[i]))$$
   - Else for $j \in [0 \dots 25]$ with $c[j] > 0$:
     $$\text{If } \neg g \text{ and } ('a' + j) < target[i], \text{ skip}$$
     $$c[j] \leftarrow c[j] - 1, \quad p[i] \leftarrow 'a' + j$$
     $$\text{If } f(i + 1, p, g \lor (p[i] > target[i])) = true, \text{ return } true$$
     $$c[j] \leftarrow c[j] + 1 \quad \text{(Backtrack)}$$

---

### ✅ Why This Works

1. **Palindromic Structure**: By forcing symmetry around the center using half frequencies, every constructed string is guaranteed to be a valid palindromic permutation of $s$.
2. **First-Fit Guarantee for Lexicographical Minimality**: Trying characters from `'a'` to `'z'` at each position creates a depth-first search that visits lexicographically smaller prefixes first.
3. **Target Comparison via `g` Flag**:
   - If $g = \text{false}$, we are matching the prefix of `target` exactly. Picking a smaller character would make the prefix smaller than `target`'s prefix, which can never result in a string strictly greater than `target`.
   - Once $g = \text{true}$, any available remaining characters can be placed in lexicographical order (from `'a'` upward).

---

### ⏱️ Complexity

- **Time Complexity**: 
  - Validating parity takes $O(n)$ time.
  - Backtracking explores permutations of size up to $\lceil n / 2 \rceil$. Because branches that fall below `target` are pruned early, the depth-first search explores a limited number of states.
  - At most, the full search takes $O(26 \times \frac{n}{2} \times \text{number of valid prefix permutations})$. In the worst case without heavy pruning, building the candidate takes $O(n)$ per leaf.
  - Overall Time Complexity: $O(n \cdot K)$ where $K$ is the number of valid half-permutations evaluated (bounded by $O((\lceil n/2 \rceil)!)$ worst-case theoretical backtracking tree, but heavily pruned by prefix ordering).
- **Space Complexity**:
  - Recursion stack depth is $O(n)$.
  - Array `p` uses $O(n)$ space.
  - Frequency array `c` uses $O(26) = O(1)$ space.
  - Overall Auxiliary Space Complexity: $O(n)$.

---

### 🧠 DSA Pattern

- **Backtracking / Depth-First Search (DFS)**: Systematic search over permutations with state restoration.
- **Greedy / Lexicographical Search Pruning**: Branch-and-bound optimization using flag `g` to prune lexicographically smaller paths early and returning early on the first match.

---

### ⚠️ Common Mistakes

1. **Incorrect Middle Handling for Odd Lengths**: Forgetting that the middle character in an odd-length palindrome is strictly fixed by the single character with an odd frequency.
2. **Comparing Half-String vs. Full-String**: Relying solely on `g` flag during the half-string construction might occasionally fail if the prefix matches `target` exactly up to $\lceil n/2 \rceil$, but the mirrored second half ends up strictly smaller than `target`'s second half. The submitted code handles this safely by doing an explicit `s.compareTo(t) > 0` check on the fully constructed palindrome at the base case.
3. **Forgetting to Backtrack State**: Failing to restore `c[j]++` after returning from recursive calls.

---

### 🚀 Optimization Notes

1. **Early Return / Short-circuiting**: The recursive calls return `boolean`, which allows immediate termination as soon as the first valid palindrome is found (`return true`), avoiding unnecessary search operations.
2. **Base Case Full Comparison**: The explicit check `s.compareTo(t) > 0` in the base case guarantees correctness even when the prefix matches `target` up to the midpoint (where prefix comparison alone isn't enough to guarantee the full reversed tail is strictly larger).
3. **Readability / Minor Redundancy**: In the base case, `StringBuilder` recreates the string from `p`. This is clean and correct, though string creation happens at leaf nodes. Since $n \le 300$, this string construction remains very efficient.
