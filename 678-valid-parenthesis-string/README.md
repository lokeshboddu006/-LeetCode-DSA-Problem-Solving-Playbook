<h2><a href="https://leetcode.com/problems/valid-parenthesis-string">Valid Parenthesis String</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a string <code>s</code> containing only three types of characters: <code>&#39;(&#39;</code>, <code>&#39;)&#39;</code> and <code>&#39;*&#39;</code>, return <code>true</code> <em>if</em> <code>s</code> <em>is <strong>valid</strong></em>.</p>

<p>The following rules define a <strong>valid</strong> string:</p>

<ul>
	<li>Any left parenthesis <code>&#39;(&#39;</code> must have a corresponding right parenthesis <code>&#39;)&#39;</code>.</li>
	<li>Any right parenthesis <code>&#39;)&#39;</code> must have a corresponding left parenthesis <code>&#39;(&#39;</code>.</li>
	<li>Left parenthesis <code>&#39;(&#39;</code> must go before the corresponding right parenthesis <code>&#39;)&#39;</code>.</li>
	<li><code>&#39;*&#39;</code> could be treated as a single right parenthesis <code>&#39;)&#39;</code> or a single left parenthesis <code>&#39;(&#39;</code> or an empty string <code>&quot;&quot;</code>.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;()&quot;
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(*)&quot;
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(*))&quot;
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 4:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(&quot;
<strong>Output:</strong> false
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 100</code></li>
	<li><code>s[i]</code> is <code>&#39;(&#39;</code>, <code>&#39;)&#39;</code> or <code>&#39;*&#39;</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem allows `'*'` to act as `'('`, `')'`, or an empty string `""`. Instead of using backtracking or dynamic programming to track every possible valid string outcome, this solution tracks the **range of possible open parenthesis counts** at any point in the string.

By maintaining two integer counters:
- `l` (low): the **minimum** possible number of open `(` brackets currently unmatched.
- `h` (high): the **maximum** possible number of open `(` brackets currently unmatched.

As long as the range `[l, h]` contains `0` at the end of the string (and `h` never drops below `0` mid-way), the string can be validly balanced.

### 🔍 Approach

1. **Initialization**:
   - `l = 0`: Minimum open bracket count possible.
   - `h = 0`: Maximum open bracket count possible.

2. **Single Pass Iteration**:
   - Loop through string `s` index by index from `i = 0` to `s.length() - 1`:
     - **
Updating `l`**: If `s.charAt(i) == '('`, increment `l` by 1. Otherwise (if `')'` or `'*'`), decrement `l` by 1. This treats `'*'` conservatively as a closing bracket `')'` to lower the minimum count.
     - **Updating `h`**: If `s.charAt(i) == ')'`, decrement `h` by 1. Otherwise (if `'('` or `'*'`), increment `h` by 1. This treats `'*'` generously as an opening bracket `'('` to maximize the count.

3. **Invariants & Safety Checks**:
   - `if (h < 0) return false;`: If the maximum possible open brackets `h` falls below `0`, it means even if every `'*'` was treated as `'('`, there are too many closing brackets `')'`. The string is immediately invalid.
   - `l = Math.max(l, 0);`: The lower bound `l` cannot be negative because we cannot have a negative number of unmatched open brackets. If `l` goes negative, it just means we treated a `'*'` as `')'` when there was no open bracket to close; in reality, that `'*'` would instead act as an empty string `""` or `'('`. Resetting `l` to `0` enforces this lower bound.

4. **Final Return**:
   - `return l == 0;`: At the end of the loop, check if `l == 0`. Since `h >= l`, if `l == 0`, then $0 \in [l, h]$, meaning it is possible to balance all brackets to exactly 0 unmatched `(`.

### 🧩 Algorithm

1. Set `l = 0`, `h = 0`.
2. For each character `c` in string `s`:
   - `l = (c == '(') ? l + 1 : l - 1`
   - `h = (c == ')') ? h - 1 : h + 1`
   - If `h < 0`, return `false`.
   - `l = max(l, 0)`
3. Return `l == 0`.

### ✅ Why This Works

- **Continuous Range Property**: The set of possible open bracket counts at any index is a contiguous set of integers from `l` to `h`.
- **Upper Bound Check (`h < 0`)**: Ensures that at no point do closing parentheses exceed all available opening brackets and wildcards combined.
- **Lower Bound Floor (`l = max(l, 0)`)**: Prevents counting invalid negative bracket balances when wildcards could simply be ignored as empty strings `""`.
- **Final Target (`l == 0`)**: Since `0` is the target for a balanced string, if the lower bound `l` reaches `0` by the end (while `h >= 0`), then `0` is achievable within `[l, h]`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of string `s`. The algorithm makes a single linear scan through the string.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. Only two integer variables (`l` and `h`) are used.

### 🧠 DSA Pattern

- **Greedy / Range Tracking**: Simultaneously maintaining the minimum and maximum possible bounds of a state variable across uncertain choices (`'*'`).

### ⚠️ Common Mistakes

1. **Incorrect Early Return Condition**: Returning `false` when `l < 0` instead of `h < 0`. A negative `l` is normal when `'*'` is treated as `')'`, and should just be reset to `0`.
2. **Forgetting `l = Math.max(l, 0)`**: If `l` is allowed to stay negative (e.g., `-2`) and then incremented later (e.g., back to `0`), it could falsely signal a valid match when brackets were actually out of order earlier.

### 🚀 Optimization Notes

- **Optimal Solution**: The solution is already optimal with $\mathcal{O}(N)$ time complexity and $\mathcal{O}(1)$ extra space.
- **Micro-Optimization**: Converting `s.charAt(i)` inside the loop to a character array (`s.toCharArray()`) can reduce method call overhead in Java, though for constraints up to $N = 100$, the performance difference is negligible.
