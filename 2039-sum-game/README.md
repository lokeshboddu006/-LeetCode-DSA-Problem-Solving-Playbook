<h2><a href="https://leetcode.com/problems/sum-game">Sum Game</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Alice and Bob take turns playing a game, with <strong>Alice</strong><strong>&nbsp;starting first</strong>.</p>

<p>You are given a string <code>num</code> of <strong>even length</strong> consisting of digits and <code>&#39;?&#39;</code> characters. On each turn, a player will do the following if there is still at least one <code>&#39;?&#39;</code> in <code>num</code>:</p>

<ol>
	<li>Choose an index <code>i</code> where <code>num[i] == &#39;?&#39;</code>.</li>
	<li>Replace <code>num[i]</code> with any digit between <code>&#39;0&#39;</code> and <code>&#39;9&#39;</code>.</li>
</ol>

<p>The game ends when there are no more <code>&#39;?&#39;</code> characters in <code>num</code>.</p>

<p>For Bob&nbsp;to win, the sum of the digits in the first half of <code>num</code> must be <strong>equal</strong> to the sum of the digits in the second half. For Alice&nbsp;to win, the sums must <strong>not be equal</strong>.</p>

<ul>
	<li>For example, if the game ended with <code>num = &quot;243801&quot;</code>, then Bob&nbsp;wins because <code>2+4+3 = 8+0+1</code>. If the game ended with <code>num = &quot;243803&quot;</code>, then Alice&nbsp;wins because <code>2+4+3 != 8+0+3</code>.</li>
</ul>

<p>Assuming Alice and Bob play <strong>optimally</strong>, return <code>true</code> <em>if Alice will win and </em><code>false</code> <em>if Bob will win</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> num = &quot;5023&quot;
<strong>Output:</strong> false
<strong>Explanation:</strong> There are no moves to be made.
The sum of the first half is equal to the sum of the second half: 5 + 0 = 2 + 3.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> num = &quot;25??&quot;
<strong>Output:</strong> true
<strong>Explanation: </strong>Alice can replace one of the &#39;?&#39;s with &#39;9&#39; and it will be impossible for Bob to make the sums equal.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> num = &quot;?3295???&quot;
<strong>Output:</strong> false
<strong>Explanation:</strong> It can be proven that Bob will always win. One possible outcome is:
- Alice replaces the first &#39;?&#39; with &#39;9&#39;. num = &quot;93295???&quot;.
- Bob replaces one of the &#39;?&#39; in the right half with &#39;9&#39;. num = &quot;932959??&quot;.
- Alice replaces one of the &#39;?&#39; in the right half with &#39;2&#39;. num = &quot;9329592?&quot;.
- Bob replaces the last &#39;?&#39; in the right half with &#39;7&#39;. num = &quot;93295927&quot;.
Bob wins because 9 + 3 + 2 + 9 = 5 + 9 + 2 + 7.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= num.length &lt;= 10<sup>5</sup></code></li>
	<li><code>num.length</code> is <strong>even</strong>.</li>
	<li><code>num</code> consists of only digits and <code>&#39;?&#39;</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

In this game, Alice wins if she can make the sums of the left and right halves unequal, while Bob wins if he can force them to be equal. Because Bob moves second, he can pair up moves:
1. If there are `?` on opposite sides, Bob can mirror Alice's digit selection on the other side to keep the net difference unchanged.
2. If there is a pair of `?` on the *same* side, Bob can ensure their sum is always `9` (if Alice picks $d$, Bob picks $9 - d$).

Because Bob can force every pair of `?` on the same side to sum to `9`, each `?` effectively contributes an average value of **$4.5$** ($9 / 2$) to its side's sum.

Your solution condenses this game theory insight into a single mathematical balance check: replace every `?` with $4.5$, add left-half values, subtract right-half values, and check if the total result is non-zero.

### 🔍 Approach

1. **Initialize State**:
   - `n = num.length()` retrieves the length of the string.
   - `res` (a `double`) tracks the accumulated balance between the left half (positive) and right half (negative).

2. **Single Pass Loop**:
   - Loop `i` from `0` to `n - 1`.
   - Determine side multiplier: `+1` if `i < n / 2` (left half), or `-1` if `i >= n / 2` (right half).
   - Determine character value:
     - If `num.charAt(i) == '?'`, treat its value as `4.5`.
     - Otherwise, convert digit character to its numeric value (`num.charAt(i) - '0'`).
   - Multiply the value by the side multiplier and accumulate into `res`.

3. **Check Outcome**:
   - Return `res != 0`. If `res` is non-zero, Alice can force a win (`true`); if `res == 0`, Bob can force equality (`false`).

### 🧩 Algorithm

- **Mathematical / Game State Reduction**:
  $$\text{res} = \sum_{i=0}^{\frac{n}{2}-1} \text{val}(i) - \sum_{i=\frac{n}{2}}^{n-1} \text{val}(i)$$
  where $\text{val}(i) = \begin{cases} d & \text{if } num[i] = d \\ 4.5 & \text{if } num[i] = '?' \end{cases}$

- **Implicit Parity Handling**:
  - If the difference in `?` counts between left and right halves ($\Delta q = L_q - R_q$) is **odd**, then $\Delta q \times 4.5$ results in a value with a `.5` fraction. Since digit sums are integers, `res` will have a `.5` fraction and thus `res != 0` evaluates to `true` (Alice wins).
  - If $\Delta q$ is **even**, $\Delta q \times 4.5$ is an integer. Bob can force equality if and only if the final net balance is `0`.

### ✅ Why This Works

- **Bob's Optimal Strategy Strategy**:
  - For `?` characters on the same side, Bob pairs them up. For any digit $d$ Alice plays in one `?`, Bob plays $9 - d$ in the paired `?`. The pair guarantees a sum contribution of $9$, or $4.5$ per `?`.
- **Handling Odd vs. Even `?` Count**:
  - If $\Delta q$ is odd, Alice gets the last move among unmatched `?`s, allowing her to force an imbalance. In your code, an odd $\Delta q$ multiplied by $4.5$ leaves a `.5` decimal remainder, automatically guaranteeing `res != 0` without needing an explicit `if` check.
  - If $\Delta q$ is even, Bob can pair up all `?`s. The game balance reduces cleanly to checking if the total weighted sum equals `0`.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of `num`. The code iterates through the string once, performing constant-time $\mathcal{O}(1)$ operations for each character.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. Only a few local variables (`n`, `res`, `i`) are used.

### 🧠 DSA Pattern

- **Math**
- **Game Theory**

### ⚠️ Common Mistakes

- **Floating-Point Precision Risks**: Generally, comparing `double` directly with `!= 0` can be risky due to floating-point rounding errors. However, $4.5$ ($9/2$) can be represented **exact** in binary floating-point (IEEE 754), so no rounding errors occur in this specific implementation.
- **Overcomplicating Game Rules**: A common pitfall is writing complex game simulation or backtracking logic, whereas the game can be completely solved mathematically by analyzing Bob's pairing ability.

### 🚀 Optimization Notes

- This solution is already optimal in terms of time ($\mathcal{O}(n)$) and space ($\mathcal{O}(1)$).
- It handles left/right counts, digit conversions, and `?` logic simultaneously in a clean, single-pass iteration using the ternary operator `(i < n / 2 ? 1 : -1)`.
