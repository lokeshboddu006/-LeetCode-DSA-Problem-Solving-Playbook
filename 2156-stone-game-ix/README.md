<h2><a href="https://leetcode.com/problems/stone-game-ix">Stone Game IX</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Alice and Bob continue their games with stones. There is a row of n stones, and each stone has an associated value. You are given an integer array <code>stones</code>, where <code>stones[i]</code> is the <strong>value</strong> of the <code>i<sup>th</sup></code> stone.</p>

<p>Alice and Bob take turns, with <strong>Alice</strong> starting first. On each turn, the player may remove any stone from <code>stones</code>. The player who removes a stone <strong>loses</strong> if the <strong>sum</strong> of the values of <strong>all removed stones</strong> is divisible by <code>3</code>. Bob will win automatically if there are no remaining stones (even if it is Alice&#39;s turn).</p>

<p>Assuming both players play <strong>optimally</strong>, return <code>true</code> <em>if Alice wins and</em> <code>false</code> <em>if Bob wins</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> stones = [2,1]
<strong>Output:</strong> true
<strong>Explanation:</strong>&nbsp;The game will be played as follows:
- Turn 1: Alice can remove either stone.
- Turn 2: Bob removes the remaining stone. 
The sum of the removed stones is 1 + 2 = 3 and is divisible by 3. Therefore, Bob loses and Alice wins the game.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> stones = [2]
<strong>Output:</strong> false
<strong>Explanation:</strong>&nbsp;Alice will remove the only stone, and the sum of the values on the removed stones is 2. 
Since all the stones are removed and the sum of values is not divisible by 3, Bob wins the game.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> stones = [5,1,2,4,3]
<strong>Output:</strong> false
<strong>Explanation:</strong> Bob will always win. One possible way for Bob to win is shown below:
- Turn 1: Alice can remove the second stone with value 1. Sum of removed stones = 1.
- Turn 2: Bob removes the fifth stone with value 3. Sum of removed stones = 1 + 3 = 4.
- Turn 3: Alices removes the fourth stone with value 4. Sum of removed stones = 1 + 3 + 4 = 8.
- Turn 4: Bob removes the third stone with value 2. Sum of removed stones = 1 + 3 + 4 + 2 = 10.
- Turn 5: Alice removes the first stone with value 5. Sum of removed stones = 1 + 3 + 4 + 2 + 5 = 15.
Alice loses the game because the sum of the removed stones (15) is divisible by 3. Bob wins the game.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= stones.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= stones[i] &lt;= 10<sup>4</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core insight in your code is that individual stone values do not matter—only their values modulo 3 matter (`x % 3`). 

Because a player loses if the total sum of removed stones becomes divisible by 3:
1. Stones with remainder `0` (`x % 3 == 0`) do **not** change the running sum modulo 3. They act purely as "pass" or "stall" moves, shifting who has to make the next non-zero choice.
2. Stones with remainder `1` and remainder `2` actively change the running sum modulo 3.

Because of this, the entire game state reduces to counting the occurrences of each remainder (`0`, `1`, and `2`). The parity of `c[0]` (whether the number of `0`-remainder stones is even or odd) determines whether `0`-stones cancel each other out in terms of turn advantage or if they flip the advantage between Alice and Bob.

---

### 🔍 Approach

1. **Frequency Counting**:
   - You allocate a frequency array `c` of size 3:
     - `c[0]` counts stones where `x % 3 == 0`
     - `c[1]` counts stones where `x % 3 == 1`
     - `c[2]` counts stones where `x % 3 == 2`
   - You iterate through `s` with a `for-each` loop and increment `c[x % 3]`.

2. **Case 1: Even number of `0`-remainder stones (`c[0] % 2 == 0`)**:
   - When there is an even number of `0`-stones, their turn-flipping effect effectively neutralizes. 
   - Alice can choose to start with either remainder `1` or remainder `2`.
   - If she starts with `1`, the sequence of non-zero moves to avoid a sum divisible by 3 must be `1, 1, 2, 1, 2, 1, 2...`
   - If she starts with `2`, the sequence must be `2, 2, 1, 2, 1, 2, 1...`
   - As long as both `c[1] > 0` and `c[2] > 0`, Alice can pick the optimal starting strategy to guarantee a win. Thus, you return `Math.min(c[1], c[2]) > 0`.

3. **Case 2: Odd number of `0`-remainder stones (`c[0] % 2 != 0`)**:
   - An odd count of `0`-stones acts as a single turn-swapper that Bob can exploit to force Alice into a losing move unless Alice has a significant numerical dominance in one of the remainder types.
   - Specifically, Alice can only win if the difference between the counts of `1`-stones and `2`-stones is strictly greater than 2. Thus, you return `Math.abs(c[1] - c[2]) > 2`.

---

### 🧩 Algorithm

1. **Remainder Frequency Calculation**:
   $$\text{c}[x \pmod 3] = \text{c}[x \pmod 3] + 1 \quad \forall x \in s$$

2. **Decision Logic**:
   $$\text{Result} = \begin{cases} 
   \min(c[1], c[2]) > 0 & \text{if } c[0] \pmod 2 == 0 \\
   |c[1] - c[2]| > 2 & \text{if } c[0] \pmod 2 \neq 0 
   \end{cases}$$

---

### ✅ Why This Works

- **Correctness of `c[0] % 2 == 0`**: 
  When `c[0]` is even, `0`-stones alternate turns harmlessly. Alice dictates whether the sequence starts with `1` or `2`. If both `1` and `2` exist (`Math.min(c[1], c[2]) > 0`), Alice can force Bob into a situation where Bob is either forced to pick a stone that makes the sum divisible by 3, or Bob runs out of valid moves on his turn.

- **Correctness of `c[0] % 2 != 0`**: 
  When `c[0]` is odd, the extra `0`-stone gives Bob the ability to steal turn order once. To overcome this extra stall move, Alice needs enough stone count difference between remainder `1` and `2` (`|c[1] - c[2]| > 2`) to sustain her win sequence despite Bob's turn swap.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of array `s`. You traverse the input array exactly once to count remainder frequencies. The subsequent mathematical evaluations are $\mathcal{O}(1)$.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. The array `c` is fixed to size 3 regardless of the input size $N$.

---

### 🧠 DSA Pattern

- **Game Theory / Math / Modulo Arithmetic**: Analyzing game states by reducing inputs using modulo operators, followed by constant-time decision rules derived from game invariants.

---

### ⚠️ Common Mistakes

1. **Index Out of Bounds or Modulo Errors**: If input values could be negative, `x % 3` in Java could produce negative indices. However, given problem constraints ($1 \le stones[i]$), `x % 3` is guaranteed to be in range `[0, 2]`.
2. **Misinterpreting the `> 2` Bound**: When `c[0]` is odd, it's easy to mistakenly check `|c[1] - c[2]| > 1` or `> 0`. The threshold strictly requires a difference of at least 3 (`> 2`) because of how the turn swap affects the non-zero sequence length.

---

### 🚀 Optimization Notes

- This solution is **already optimal** in both time ($\mathcal{O}(N)$) and auxiliary space ($\mathcal{O}(1)$).
- Any solution must read the input array at least once to count remainder frequencies, making $\mathcal{O}(N)$ time the absolute lower bound.
