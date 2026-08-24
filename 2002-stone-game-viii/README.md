<h2><a href="https://leetcode.com/problems/stone-game-viii">Stone Game VIII</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Alice and Bob take turns playing a game, with <strong>Alice starting first</strong>.</p>

<p>There are <code>n</code> stones arranged in a row. On each player&#39;s turn, while the number of stones is <strong>more than one</strong>, they will do the following:</p>

<ol>
	<li>Choose an integer <code>x &gt; 1</code>, and <strong>remove</strong> the leftmost <code>x</code> stones from the row.</li>
	<li>Add the <strong>sum</strong> of the <strong>removed</strong> stones&#39; values to the player&#39;s score.</li>
	<li>Place a <strong>new stone</strong>, whose value is equal to that sum, on the left side of the row.</li>
</ol>

<p>The game stops when <strong>only</strong> <strong>one</strong> stone is left in the row.</p>

<p>The <strong>score difference</strong> between Alice and Bob is <code>(Alice&#39;s score - Bob&#39;s score)</code>. Alice&#39;s goal is to <strong>maximize</strong> the score difference, and Bob&#39;s goal is the <strong>minimize</strong> the score difference.</p>

<p>Given an integer array <code>stones</code> of length <code>n</code> where <code>stones[i]</code> represents the value of the <code>i<sup>th</sup></code> stone <strong>from the left</strong>, return <em>the <strong>score difference</strong> between Alice and Bob if they both play <strong>optimally</strong>.</em></p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> stones = [-1,2,-3,4,-5]
<strong>Output:</strong> 5
<strong>Explanation:</strong>
- Alice removes the first 4 stones, adds (-1) + 2 + (-3) + 4 = 2 to her score, and places a stone of
  value 2 on the left. stones = [2,-5].
- Bob removes the first 2 stones, adds 2 + (-5) = -3 to his score, and places a stone of value -3 on
  the left. stones = [-3].
The difference between their scores is 2 - (-3) = 5.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> stones = [7,-6,5,10,5,-2,-6]
<strong>Output:</strong> 13
<strong>Explanation:</strong>
- Alice removes all stones, adds 7 + (-6) + 5 + 10 + 5 + (-2) + (-6) = 13 to her score, and places a
  stone of value 13 on the left. stones = [13].
The difference between their scores is 13 - 0 = 13.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> stones = [-10,-12]
<strong>Output:</strong> -22
<strong>Explanation:</strong>
- Alice can only make one move, which is to remove both stones. She adds (-10) + (-12) = -22 to her
  score and places a stone of value -22 on the left. stones = [-22].
The difference between their scores is (-22) - 0 = -22.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == stones.length</code></li>
	<li><code>2 &lt;= n &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>4</sup> &lt;= stones[i] &lt;= 10<sup>4</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

When a player removes the leftmost $x$ stones ($x \ge 2$), two important things happen:
1. The score gained is the sum of the first $x$ stones, which is equal to the **prefix sum** of the array up to index $i = x - 1$.
2. The replacement stone placed at the front has a value equal to this exact prefix sum.

Because the new stone's value equals the prefix sum of the removed elements, any subsequent move taking stones up to a higher original index $j$ ($j > i$) will yield the prefix sum up to $j$ as well. Thus, taking $x$ stones is equivalent to choosing a prefix sum index $i \ge 1$.

In a minimax game, if the current player chooses prefix index $i$, they gain $s[i]$ points, and the opponent gets the optimal score difference from the remaining available indices $j > i$. The current player tries to maximize their net gain: either by taking index $i$ (yielding $s[i] - \text{next\_player\_score\_diff}$) or skipping index $i$ to take a later choice (yielding $\text{next\_player\_score\_diff}$).

Working backward from the last available index allows us to maintain the optimal maximum score difference in a single variable (`ans`).

---

### 🔍 Approach

1. **Prefix Sum Computation (In-Place)**:
   - Loop through `s` from index `1` to `n - 1`, setting `s[i] += s[i - 1]`.
   - Now `s[i]` holds the total sum of stones from index `0` through `i`.

2. **Base Case Initialization**:
   - Initialize `ans = s[n - 1]`.
   - If the player is forced to take all stones (index $n - 1$), there are no remaining choices for the opponent, so the best score difference is simply the prefix sum `s[n - 1]`.

3. **Backward Dynamic Programming Pass**:
   - Iterate backward from `i = n - 2` down to `i = 1`:
     - Update `ans = Math.max(ans, s[i] - ans)`.
     - Here, `ans` on the right-hand side represents the best score difference achievable if we pick some index greater than `i`.
     - `s[i] - ans` represents the net score difference if the player chooses index `i` right now (gaining `s[i]` and letting the opponent gain `ans` from that point on).
     - `Math.max` chooses the optimal strategy for the current player at step `i`.

4. **Return Result**:
   - Return `ans`, which holds the maximum score difference Alice can achieve starting at the first move (which requires taking at least $x \ge 2$ stones, corresponding to index $i \ge 1$).

---

### 🧩 Algorithm

- **State Representation**:
  Let $dp[i]$ be the maximum score difference the current player can obtain considering available prefix choices from index $i$ to $n - 1$.

- **Base Case**:
  $$dp[n - 1] = s[n - 1]$$

- **DP Recurrence**:
  $$dp[i] = \max(dp[i + 1], s[i] - dp[i + 1]) \quad \text{for } i = n - 2 \text{ down to } 1$$

- **Variable Optimization**:
  Instead of maintaining a full DP array, the code uses a single variable `ans` to represent $dp[i + 1]$ at each step and updates it sequentially to represent $dp[i]$.

---

### ✅ Why This Works

- **Prefix Invariant**: Picking $x$ stones leaves a single stone of value $\sum_{k=0}^{x-1} \text{stones}[k]$ at the start. Adding more stones to this in subsequent moves yields the exact original prefix sum up to the new end position.
- **Optimal Substructure**: Game theory dictates that both players play optimally to maximize their own relative score. By working backwards from the last choice ($i = n - 1$), every state $i$ determines the optimal decision given all future optimal decisions already computed.
- **Valid Move Boundary**: The game rules require choosing $x \ge 2$ stones, which means the minimum prefix index a player can pick is $i = 1$. The loop correctly terminates after processing $i = 1$.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Generating prefix sums in-place takes a single pass of length $n - 1$.
  - The backward loop runs from $n - 2$ down to $1$, taking $n - 2$ steps.
  - Overall time complexity is linear, $\mathcal{O}(n)$.

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space
  - The algorithm modifies the input array `s` in-place to store prefix sums.
  - Only a few primitive variables (`n`, `i`, `ans`) are used.

---

### 🧠 DSA Pattern

- **Dynamic Programming** (Suffix DP / Backward DP)
- **Prefix Sum**
- **Game Theory / Minimax**

---

### ⚠️ Common Mistakes

1. **Loop Boundary Off-by-One (`i > 0` vs `i >= 0`)**:
   - The rule states $x > 1$ stones must be removed. Taking $x = 1$ stone (index `i = 0`) is illegal.
   - Stopping the loop at `i = 1` (`i > 0`) correctly respects this constraint.

2. **Misinterpreting Score Difference**:
   - Forgetting that the score difference subtracts the opponent's optimal outcome (`s[i] - ans`) instead of adding it.

3. **Inappropriate Initial State**:
   - Starting `ans` with `0` or `Integer.MIN_VALUE` instead of the base state `s[n - 1]`.

---

### 🚀 Optimization Notes

- **Space Efficiency**: The code operates in $\mathcal{O}(1)$ auxiliary space by overwriting the input array `s` with prefix sums.
- **Time Optimal**: A single linear forward pass followed by a single linear backward pass is time-optimal for this problem.
- **Side Effect Note**: Mutating `s` in-place alters the caller's array. If preserving the original array were required by a system design constraint, a separate variable or array would be needed, but for competitive programming / LeetCode, in-place modification provides maximum memory efficiency.
