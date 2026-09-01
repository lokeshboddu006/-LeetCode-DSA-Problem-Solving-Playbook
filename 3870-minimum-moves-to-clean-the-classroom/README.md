<h2><a href="https://leetcode.com/problems/minimum-moves-to-clean-the-classroom">Minimum Moves to Clean the Classroom</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p data-end="324" data-start="147">You are given an <code>m x n</code> grid <code>classroom</code> where a student volunteer is tasked with cleaning up litter scattered around the room. Each cell in the grid is one of the following:</p>

<ul>
	<li><code>&#39;S&#39;</code>: Starting position of the student</li>
	<li><code>&#39;L&#39;</code>: Litter that must be collected (once collected, the cell becomes empty)</li>
	<li><code>&#39;R&#39;</code>: Reset area that restores the student&#39;s energy to full capacity, regardless of their current energy level (can be used multiple times)</li>
	<li><code>&#39;X&#39;</code>: Obstacle the student cannot pass through</li>
	<li><code>&#39;.&#39;</code>: Empty space</li>
</ul>

<p>You are also given an integer <code>energy</code>, representing the student&#39;s maximum energy capacity. The student starts with this energy from the starting position <code>&#39;S&#39;</code>.</p>

<p>Each move to an adjacent cell (up, down, left, or right) costs 1 unit of energy. If the energy reaches 0, the student can only continue if they are on a reset area <code>&#39;R&#39;</code>, which resets the energy to its <strong>maximum</strong> capacity <code>energy</code>.</p>

<p>Return the <strong>minimum</strong> number of moves required to collect all litter items, or <code>-1</code> if it&#39;s impossible.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">classroom = [&quot;S.&quot;, &quot;XL&quot;], energy = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The student starts at cell <code data-end="262" data-start="254">(0, 0)</code> with 2 units of energy.</li>
	<li>Since cell <code>(1, 0)</code> contains an obstacle &#39;X&#39;, the student cannot move directly downward.</li>
	<li>A valid sequence of moves to collect all litter is as follows:
	<ul>
		<li>Move 1: From <code>(0, 0)</code> &rarr; <code>(0, 1)</code> with 1 unit of energy and 1 unit remaining.</li>
		<li>Move 2: From <code>(0, 1)</code> &rarr; <code>(1, 1)</code> to collect the litter <code>&#39;L&#39;</code>.</li>
	</ul>
	</li>
	<li>The student collects all the litter using 2 moves. Thus, the output is 2.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">classroom = [&quot;LS&quot;, &quot;RL&quot;], energy = 4</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>The student starts at cell <code data-end="262" data-start="254">(0, 1)</code> with 4 units of energy.</li>
	<li>A valid sequence of moves to collect all litter is as follows:
	<ul>
		<li>Move 1: From <code>(0, 1)</code> &rarr; <code>(0, 0)</code> to collect the first litter <code>&#39;L&#39;</code> with 1 unit of energy used and 3 units remaining.</li>
		<li>Move 2: From <code>(0, 0)</code> &rarr; <code>(1, 0)</code> to <code>&#39;R&#39;</code> to reset and restore energy back to 4.</li>
		<li>Move 3: From <code>(1, 0)</code> &rarr; <code>(1, 1)</code> to collect the second litter <code data-end="1068" data-start="1063">&#39;L&#39;</code>.</li>
	</ul>
	</li>
	<li>The student collects all the litter using 3 moves. Thus, the output is 3.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">classroom = [&quot;L.S&quot;, &quot;RXL&quot;], energy = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">-1</span></p>

<p><strong>Explanation:</strong></p>

<p>No valid path collects all <code>&#39;L&#39;</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= m == classroom.length &lt;= 20</code></li>
	<li><code>1 &lt;= n == classroom[i].length &lt;= 20</code></li>
	<li><code>classroom[i][j]</code> is one of <code>&#39;S&#39;</code>, <code>&#39;L&#39;</code>, <code>&#39;R&#39;</code>, <code>&#39;X&#39;</code>, or <code>&#39;.&#39;</code></li>
	<li><code>1 &lt;= energy &lt;= 50</code></li>
	<li>There is exactly <strong>one</strong> <code>&#39;S&#39;</code> in the grid.</li>
	<li>There are <strong>at most</strong> 10 <code>&#39;L&#39;</code> cells in the grid.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition
The problem asks for the minimum number of moves to collect all litter items (`'L'`) while managing energy constraints and reset areas (`'R'`).

Since the grid edges have equal weight (1 move per step) and we want the shortest path (minimum moves), **Breadth-First Search (BFS)** is the natural choice. However, a simple 2D `(x, y)` BFS state is insufficient because:
1. We need to track which litters have already been collected.
2. We need to track the student's remaining energy.

Since the maximum number of litter items is small ($\le 10$), we can compress the set of collected litters into an integer bitmask (`mask`). To avoid processing redundant paths, we keep track of the maximum energy achieved at every state `(x, y, mask)`. If a path reaches cell `(x, y)` with a set of collected litters `mask` with higher remaining energy than previously seen, it is worth exploring further.

---

### 🔍 Approach

1. **Preprocessing & Litter Indexing**:
   - Traverse the grid to locate the starting point `'S'` at `(startX, startY)`.
   - Map each litter cell `'L'` to a unique 0-based index (`0` to `litterCount - 1`) using a 2D array `litterIndex`.
   - If there is no litter (`litterCount == 0`), return `0` immediately.
   - Calculate `targetMask = (1 << litterCount) - 1`, which represents all litter items collected.

2. **State Memoization Matrix**:
   - Create a 3D array `bestEnergy[m][n][1 << litterCount]` initialized to `-1`.
   - `bestEnergy[x][y][mask]` stores the highest energy recorded when arriving at cell `(x, y)` with the collected litter set `mask`.

3. **BFS Execution**:
   - Initialize a queue with the starting state: `[startX, startY, mask = 0, energy, steps = 0]`.
   - Mark `bestEnergy[startX][startY][0] = energy`.
   - Dequeue states level-by-level:
     - Check if `currE == 0` (cannot make any moves out).
     - Try moving in 4 cardinal directions to neighbor `(nx, ny)`.
     - Skip if out of bounds or if cell is an obstacle `'X'`.
     - Calculate remaining energy `nextE`:
       - If cell is `'R'`, energy resets to full capacity `energy`.
       - Otherwise, energy decreases by 1 (`currE - 1`).
     - Calculate `nextMask`: if cell `(nx, ny)` is `'L'`, set the corresponding bit `nextMask |= (1 << litterIndex[nx][ny])`.
     - **Early Exit**: If `nextMask == targetMask`, return `steps + 1` immediately, as BFS guarantees the shortest path.
     - **Pruning / Re-visiting**: If `nextE > bestEnergy[nx][ny][nextMask]`, update `bestEnergy[nx][ny][nextMask] = nextE` and push `[nx, ny, nextMask, nextE, steps + 1]` into the queue.

4. **Return `-1`**:
   - If the queue becomes empty without collecting all litter, return `-1`.

---

### 🧩 Algorithm

- **State Representation**: `(x, y, mask)` where:
  - `(x, y)` is the current grid coordinate.
  - `mask` is a bitmask where the $i$-th bit is `1` if litter $i$ is collected, else `0`.
- **Transitions**:
  $$\text{nextE} = \begin{cases} \text{energy} & \text{if } \text{classroom}[nx][ny] = \text{'R'} \\ \text{currE} - 1 & \text{otherwise} \end{cases}$$
  $$\text{nextMask} = \begin{cases} \text{mask} \mid (1 \ll \text{litterIndex}[nx][ny]) & \text{if } \text{classroom}[nx][ny] = \text{'L'} \\ \text{mask} & \text{otherwise} \end{cases}$$
- **Pruning Invariant**: A transition to state `(nx, ny, nextMask)` is valid if and only if:
  $$\text{nextE} > \text{bestEnergy}[nx][ny][nextMask]$$

---

### ✅ Why This Works

1. **Shortest Path Guarantee**: BFS processes grid states in increasing order of step count `steps`. The first time any state reaches `nextMask == targetMask`, the corresponding step count `steps + 1` is guaranteed to be minimal.
2. **State Pruning**: Storing the maximum energy in `bestEnergy[x][y][mask]` prevents processing paths that arrive at the same cell with the same collected litters but equal or less energy. Strict inequality (`>`) ensures cyclic transitions (e.g., oscillating at reset cell `'R'`) do not create infinite loops.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(m \times n \times 2^{K})$  
  Where $m \times n$ is the grid size and $K$ is the total number of litter cells (`litterCount` $\le 10$). For each state `(x, y, mask)` (total $m \cdot n \cdot 2^K$ states), we check at most $4$ direction vectors. Thus, the total operations are bounded by $O(4 \cdot m \cdot n \cdot 2^K)$.

- **Space Complexity**: $\mathcal{O}(m \times n \times 2^{K})$  
  Required for storing `bestEnergy[m][n][1 << K]` and the BFS queue.

---

### 🧠 DSA Pattern

- **BFS with Bitmask Dynamic Programming / State Pruning**

---

### ⚠️ Common Mistakes

1. **Omitting Mask from Visited Array**: Checking visited locations using only `[x][y]` instead of `[x][y][mask]` would prevent re-visiting cells after collecting new litter items or recharging energy.
2. **Using Non-Strict Comparison (`>=`) in Pruning**: Using `nextE >= bestEnergy[nx][ny][nextMask]` would lead to infinite loops on reset cells `'R'`, because moving back and forth to an `'R'` cell would continuously yield equal full energy without advancing.
3. **Queue Invalidation**: Forgetting to update `bestEnergy` before pushing to the queue could cause duplicate identical states to flood the queue.

---

### 🚀 Optimization Notes

- **Redundant Energy Check**: Because `if (currE == 0) continue;` is executed at the top of the loop, `nextE = currE - 1` is always $\ge 0$. The check `if (nextE < 0) continue;` inside the direction loop is redundant and will never trigger.
- **Early Exit**: Checking `nextMask == targetMask` before pushing to the queue saves processing time compared to checking when popping states from the queue.
