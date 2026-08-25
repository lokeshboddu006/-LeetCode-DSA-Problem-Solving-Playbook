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

In Stone Game VIII, whenever a player takes $x$ stones ($x > 1$), they score the sum of those $x$ stones and place a new stone with that exact sum at the left. 

A key mathematical property of this process is that **prefix sums remain invariant**. Replacing the first $x$ stones with a single stone equal to their sum means that for any subsequent choice $j > x$, the sum of the first $j$ original stones is still equal to the sum of stones up to position $j$ in the modified game. Thus, taking stones up to index $i$ (0-indexed, where $i \ge 1$) always awards the player `prefix[i]` points.

Since both players play optimally:
- If a player chooses to stop at index $i$, they earn `prefix[i]` and give the second player the optimal score difference achievable from the remaining indices $(i+1 \dots n-1)$. The net score difference for the current player would be `prefix[i] - best_from_i_plus_1`.
- Alternatively, the current player can skip index $i$ and pass the choice to a larger index, yielding `best_from_i_plus_1`.

This leads to a simple decision at each index $i$: choose the maximum between taking index $i$ (`prefix[i] - best`) and skipping index $i$ (`best`).

### 🔍 Approach

1. **Prefix Sum Computation**:
   - The code clones the `stones` array into `prefix`.
   - It computes prefix sums in-place inside `prefix` using a forward loop from `i = 1` to `n - 1`.

2. **Backward DP Sweep (Space Optimized)**:
   - `best` is initialized to `prefix[n - 1]`. This represents the base case where a player takes all stones (index $n - 1$). Since no stones remain for the opponent, the relative score difference is simply `prefix[n - 1]`.
   - The code loops backward from `i = n - 2` down to `1`:
     - At each step, `best` is updated to `Math.max(best, prefix[i] - best)`.
     - Here, the right-hand `best` is the optimal score difference from indices $> i$.
     - `prefix[i] - best` represents taking stones up to index $i$.
     - `Math.max` chooses the optimal move between taking up to index $i$ vs. taking at a larger index.

3. **Result**:
   - Alice must pick $x \ge 2$ stones, which corresponds to index $i \ge 1$. The final value of `best` after the loop finishes at $i = 1$ is the maximum score difference Alice can guarantee.

### 🧩 Algorithm

- **DSA Technique**: Dynamic Programming (1D Game Theory with Space Compression) / Suffix Optimization
- **State Definition**: Let $dp[i]$ be the maximum relative score difference a player can obtain considering available moves from index $i$ to $n-1$.
- **Base Case**: $dp[n - 1] = \text{prefix}[n - 1]$
- **State Transition**:
  $$dp[i] = \max(dp[i + 1], \text{prefix}[i] - dp[i + 1])$$
- **Optimization**: The code uses a single integer variable `best` instead of an explicit $dp$ array, updating it iteratively from right to left.

### ✅ Why This Works

- **Prefix Invariance**: Merging stones up to index $i$ into a single stone of value `prefix[i]` does not alter the cumulative sum for any index $j > i$. Therefore, precomputing prefix sums on the original array remains valid throughout the game.
- **Minimax / Subproblem Optimal Substructure**: Because both players play optimally, the score difference earned by player 1 when making a move at index $i$ is `prefix[i]` minus the opponent's best possible difference starting from index $i + 1$.
- **Complete Search Space Coverage**: Iterating backward from $n - 2$ down to $1$ evaluates all legal moves $x \in [2, n]$ without recomputing subproblems.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Cloning and computing prefix sums takes $\mathcal{O}(n)$ time.
  - The single backward loop from $n - 2$ down to $1$ executes $n - 2$ iterations, performing $\mathcal{O}(1)$ operations per iteration.
  - Total time complexity is strictly linear, $\mathcal{O}(n)$.

- **Space Complexity**: $\mathcal{O}(n)$
  - `stones.clone()` allocates an auxiliary array `prefix` of size $n$.

### 🧠 DSA Pattern

- **Dynamic Programming** (Suffix State Compression)
- **Prefix Sum**
- **Game Theory / Minimax**

### ⚠️ Common Mistakes

- **Incorrect Loop Termination ($i = 0$)**: The problem requires choosing $x > 1$ stones, which means $i \ge 1$ (0-indexed). Stopping the loop at `i = 1` correctly enforces this condition.
- **Direction of Iteration**: Trying to iterate left-to-right instead of right-to-left will fail because the value at state $i$ depends on the future optimal state $i + 1$.
- **Forgetting Subproblem Negation**: Subtracting `best` (`prefix[i] - best`) is crucial because the roles of Alice and Bob alternate; the score accumulated by the opponent reduces the current player's relative lead.

### 🚀 Optimization Notes

- **Space Optimization**: The solution clones `stones` into `prefix`, taking $\mathcal{O}(n)$ extra memory. This can be optimized to $\mathcal{O}(1)$ auxiliary space by modifying `stones` in-place instead of using `stones.clone()`.
- Given $n \le 10^5$, an $\mathcal{O}(n)$ time and space approach is well within memory limits and runs comfortably within time limits.
