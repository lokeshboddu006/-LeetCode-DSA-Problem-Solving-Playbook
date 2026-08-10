<h2><a href="https://leetcode.com/problems/stone-game-iv">Stone Game IV</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Alice and Bob take turns playing a game, with Alice starting first.</p>

<p>Initially, there are <code>n</code> stones in a pile. On each player&#39;s turn, that player makes a <em>move</em> consisting of removing <strong>any</strong> non-zero <strong>square number</strong> of stones in the pile.</p>

<p>Also, if a player cannot make a move, he/she loses the game.</p>

<p>Given a positive integer <code>n</code>, return <code>true</code> if and only if Alice wins the game otherwise return <code>false</code>, assuming both players play optimally.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> n = 1
<strong>Output:</strong> true
<strong>Explanation: </strong>Alice can remove 1 stone winning the game because Bob doesn&#39;t have any moves.</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> n = 2
<strong>Output:</strong> false
<strong>Explanation: </strong>Alice can only remove 1 stone, after that Bob removes the last one winning the game (2 -&gt; 1 -&gt; 0).
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> n = 4
<strong>Output:</strong> true
<strong>Explanation:</strong> n is already a perfect square, Alice can win with one move, removing 4 stones (4 -&gt; 0).
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

In zero-sum combinatorial games with perfect information, a state is a **winning state** (`true`) if the current player can make at least one legal move that leaves the opponent in a **losing state** (`false`). Conversely, a state is a losing state if every possible move leaves the opponent in a winning state.

Your solution builds on this game-theoretic principle:
- If there are `0` stones remaining, the current player has no valid moves and loses (`f[0] = false`).
- For `i` stones, if there exists any valid square move $k^2$ such that the remaining `i - k * k` stones put the opponent into a losing position (`!f[i - k * k]`), then starting with `i` stones is a winning position (`f[i] = true`).

---

### 🔍 Approach

1. **State Array Initialization**:
   - Create a boolean array `f` of size `n + 1`. By default in Java, all elements are initialized to `false`.
   - `f[i]` represents whether the first player to move with `i` stones remaining will win.

2. **Iterative Dynamic Programming**:
   - Loop `i` from `1` up to `n` to compute the result for each subproblem iteratively.
   - For a given `i`, try all valid square subtractions by incrementing `k` starting at `1` as long as `k * k <= i`.

3. **Transition & Early Termination**:
   - For each choice of $k^2$, inspect the state `f[i - k * k]`.
   - If `!f[i - k * k]` is `true` (meaning `f[i - k * k]` is `false`), the player can make this move to force the opponent into a losing state.
   - Set `f[i] = true` immediately and `break` out of the inner loop, as finding just one winning move is sufficient.

4. **Final Result**:
   - Return `f[n]`, which indicates whether Alice (the first player) wins with `n` initial stones.

---

### 🧩 Algorithm

- **DP State**: 
  - `f[i]`: `true` if starting a turn with `i` stones guarantees a win under optimal play; `false` otherwise.

- **Base Case**: 
  - `f[0] = false` (a player facing 0 stones cannot make any move and loses).

- **DP Transition**:
  $$f[i] = \bigvee_{1 \le k \le \lfloor\sqrt{i}\rfloor} \left(\neg f[i - k^2]\right)$$

- **Greedy / Early Exit Rule**:
  - Stop checking further values of `k` as soon as a single $k$ satisfies `!f[i - k * k]`.

---

### ✅ Why This Works

The game is finite, deterministic, and played with optimal strategy. Since players take turns removing square numbers of stones:
1. `f[0] = false` correctly models the losing condition when no moves are available.
2. For any $i > 0$, checking `!f[i - k * k]` accurately determines if there is any transition to a state where the second player (now taking their turn from $i - k^2$ stones) faces a losing position.
3. If such a transition exists, the current player chooses it, guaranteeing a win.
4. Because subproblems $i - k^2$ are strictly smaller than $i$, they are already computed when we evaluate $i$, satisfying the DP order invariant.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n \sqrt{n})$  
  The outer loop runs $n$ times. The inner loop runs $\lfloor\sqrt{i}\rfloor$ times for each $i$. Summing up the operations:
  $$\sum_{i=1}^{n} \sqrt{i} \approx \int_{1}^{n} \sqrt{x} \, dx = \frac{2}{3} n^{3/2} = \mathcal{O}(n \sqrt{n})$$
  With $n = 10^5$, $n \sqrt{n} \approx 3.16 \times 10^7$ operations, which easily runs well within typical 1-second time limits.

- **Space Complexity:** $\mathcal{O}(n)$  
  Allocated for the boolean array `f` of size `n + 1`.

---

### 🧠 DSA Pattern

- **Dynamic Programming (1D / Bottom-Up)**
- **Game Theory (Winning and Losing States / Minimax Decision)**

---

### ⚠️ Common Mistakes

1. **Missing Early Exit**: Forgetting the `break` statement inside the inner loop would force the code to evaluate all square choices for every $i$, causing unnecessary computation and slowing execution.
2. **Incorrect Loop Condition**: Writing `k <= i` instead of `k * k <= i` in the inner loop condition, leading to invalid moves or `IndexOutOfBoundsException`.
3. **Integer Overflow**: For extremely large $n$, `k * k` could potentially overflow standard 32-bit signed integers if $k$ gets large enough. Given $n \le 10^5$, $k \le 316$, so $k * k$ safely fits in a standard `int`.

---

### 🚀 Optimization Notes

- **Optimal Time Complexity**: Your $O(n \sqrt{n})$ solution with the `break` optimization is already optimal for this problem.
- **Micro-optimization**: The `break` clause significantly improves practical runtime because many numbers have multiple valid square moves, and finding a losing state early avoids evaluating the remaining squares.
