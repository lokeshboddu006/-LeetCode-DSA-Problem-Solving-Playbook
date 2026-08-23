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

The problem can be modeled as a turn-based game where Alice wants to make the sums of the two halves unequal, while Bob wants to force them to be equal.

Instead of simulating all possible moves, the code relies on two main mathematical observations:

1. **Parity of total question marks (`q1 + q2`)**:
   - Alice moves first. If the total count of `?` characters is odd, Alice gets the final move. Having the last move allows Alice to arbitrarily break any equality Bob tried to construct. Therefore, if total question marks are odd, Alice is guaranteed to win.
   
2. **Matching pairs of moves**:
   - When the total number of `?` is even, Bob has the last move. 
   - Whenever Alice plays a digit $d$ into a `?`, Bob can choose another `?` and play $9 - d$. This pairs up the choices such that every pair of question marks can contribute a sum of $9$.
   - If there are `?` on both sides, Bob can pair a `?` on the left with a `?` on the right, effectively canceling them out without changing the net sum difference between the two halves.
   - Ultimately, the excess question marks on one side (e.g., $q2 - q1$ on the right) will contribute an average of $4.5$ (or $9 / 2$) per remaining question mark to that side.
   - For Bob to win, the initial sum difference $(s1 - s2)$ must exactly balance out the excess question marks $(q2 - q1)$ scaled by $9 / 2$.

### 🔍 Approach

1. **Count and Sum Left Half**:
   - Iterate through indices `0` to `n / 2 - 1`.
   - Increment `q1` if `num.charAt(i) == '?'`.
   - Otherwise, accumulate the numeric digit value `num.charAt(i) - '0'` into `s1`.

2. **Count and Sum Right Half**:
   - Iterate through indices `n / 2` to `n - 1`.
   - Increment `q2` if `num.charAt(i) == '?'`.
   - Otherwise, accumulate the numeric digit value `num.charAt(i) - '0'` into `s2`.

3. **Check Parity**:
   - Check if `(q1 + q2) % 2 == 1`.
   - If true, return `true` immediately because Alice gets the last move and can always win.

4. **Mathematical Balance Equation**:
   - Check if `2 * (s1 - s2) != 9 * (q2 - q1)`.
   - If the equality holds, Bob can force both halves to have identical final sums, so the function returns `false` (Alice loses).
   - If it does not hold, Alice wins, so the function returns `true`.

### 🧩 Algorithm

The exact logic in code is as follows:

1. **State variables**:
   - `s1`: sum of initial known digits in the first half (`[0, n/2)`).
   - `q1`: count of `?` in the first half.
   - `s2`: sum of initial known digits in the second half (`[n/2, n)`).
   - `q2`: count of `?` in the second half.

2. **Odd question marks check**:
   $$\text{If } (q1 + q2) \bmod 2 \neq 0 \implies \text{Return } \text{true}$$

3. **Game balance condition**:
   - The theoretical target difference for Bob to win is:
     $$s1 - s2 = \frac{9}{2} \times (q2 - q1)$$
   - To avoid floating-point calculations, rearrange by multiplying both sides by 2:
     $$2 \times (s1 - s2) = 9 \times (q2 - q1)$$
   - If $2 \times (s1 - s2) \neq 9 \times (q2 - q1)$, return `true` (Alice wins). Otherwise, return `false` (Bob wins).

### ✅ Why This Works

- **Odd total `?`**: Alice plays first, so an odd total count means Alice makes the last move. No matter what sum exists before the final `?`, Alice can pick a digit that makes the two sums unequal. Thus, odd total `?` $\implies$ Alice wins.
- **Even total `?`**: Bob can respond to every Alice move.
  - If Alice plays on the side with fewer `?`, Bob can mirror her on the side with more `?`.
  - Bob can always complement Alice's digit $d$ with $9 - d$, securing a fixed sum of $9$ for every pair of `?` placed on the same side, or balancing identical number of `?` across opposite sides.
  - Therefore, $k$ excess question marks on one side can be forced by Bob to add exactly $9 \times (k / 2)$ to that side's sum.
  - Multiplying by 2 avoids division/floating point issues, leading directly to the integer comparison `2 * (s1 - s2) != 9 * (q2 - q1)`.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of the string `num`. We iterate through the string twice in single loops to compute sums and counts.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space, as only a few primitive integer counters (`s1`, `s2`, `q1`, `q2`, `n`) are used.

### 🧠 DSA Pattern

- **Game Theory / Minimax Reduction**
- **Math / Greedy Strategy**

### ⚠️ Common Mistakes

1. **Floating-point division issues**:
   - Writing `(s1 - s2) == 4.5 * (q2 - q1)` or `(s1 - s2) == 9 / 2 * (q2 - q1)` using integer division `9 / 2` (which truncates to `4`). The code correctly avoids floating-point precision issues by multiplying by `2`: `2 * (s1 - s2) != 9 * (q2 - q1)`.
2. **Ignoring parity**:
   - Forgetting that when total `?` is odd, Alice always wins due to having the final move.
3. **Misinterpreting return value**:
   - The problem asks if *Alice* wins. `true` means Alice wins, `false` means Bob wins.

### 🚀 Optimization Notes

- **Optimal Solution**: This implementation is already optimal in both time ($\mathcal{O}(N)$) and space ($\mathcal{O}(1)$).
- **Minor micro-optimization**: The two sequential `for` loops could be merged into a single loop running from `0` to `n / 2 - 1` that inspects both `num.charAt(i)` and `num.charAt(i + n / 2)` simultaneously, but this does not change the asymptotic complexity.
