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

The problem asks for the minimum number of moves to collect all litter items in a grid with an energy constraint and reset cells. Since every movement step costs 1 unit of energy and 1 move count, finding the shortest path in an unweighted graph naturally points to a **Breadth-First Search (BFS)**.

However, standard 2D grid BFS isn't enough because a cell's state depends not only on its row and column coordinates $(r, c)$, but also on:
1. Which litter items have already been collected.
2. How much energy remains.

Since there are at most 10 litter items (`'L'`), you used a **bitmask** where the $i$-th bit indicates whether the $i$-th litter item has been collected. Thus, the complete state during BFS is defined by `(row, col, energy, litter_mask)`. By exploring states level by level via BFS, the first time all litter bits are set (`mask == targetMask`), the current step count is guaranteed to be minimal.

---

### 🔍 Approach

1. **Preprocessing Grid & Mapping Litter:**
   - Traverse the `classroom` grid to find the starting cell `'S'` and record `startX` and `startY`.
   - Each time a litter cell `'L'` is encountered, map its `(i, j)` coordinate to a zero-indexed integer `litterCount` inside `litterMap[i][j]` and increment `litterCount`.
   - If `litterCount == 0` initially, return `0` immediately since no moves are required.

2. **Target Mask & BFS Setup:**
   - Compute `targetMask = (1 << litterCount) - 1`, which has all bits set to `1` for every litter item.
   - Set up a 4D boolean array `visited[m][n][energy + 1][1 << litterCount]` to keep track of already visited state combinations `(r, c, energy, mask)`.
   - Initialize a `Queue<int[]>` for BFS storing arrays of `{row, col, remaining_energy, litter_mask, steps}`.
   - Push the starting state `{startX, startY, energy, 0, 0}` to the queue and mark `visited[startX][startY][energy][0] = true`.

3. **BFS State Transitions:**
   - Dequeue the current state `(r, c, e, mask, steps)`.
   - For each of the 4 directional neighbors `(nr, nc)` using `dirs`:
     - Skip if `(nr, nc)` is out of bounds or is an obstacle `'X'`.
     - Calculate remaining energy: `nxt_e = e - 1`.
     - Calculate updated bitmask: `nxt_mask = mask`. If `nextCell == 'L'`, update `nxt_mask |= (1 << litterMap[nr][nc])`.
     - **Early Exit Check**: If `nxt_mask == targetMask`, return `steps + 1` immediately.
     - **Energy Reset Check**: If `nextCell == 'R'`, reset energy to maximum (`nxt_e = energy`).
     - **Pruning Dead States**: If `nxt_e == 0` and the destination is not `'R'`, the student cannot make any further moves from this state, so `continue`.
     - **Visited Check**: If `visited[nr][nc][nxt_e][nxt_mask]` is false, mark it true and enqueue `{nr, nc, nxt_e, nxt_mask, steps + 1}`.

4. **Termination:**
   - If the queue becomes empty without reaching `targetMask`, return `-1`.

---

### 🧩 Algorithm

1. **State Space Representation**:
   - $\text{State} = (r, c, e, \text{mask})$ where $r \in [0, m-1]$, $c \in [0, n-1]$, $e \in [0, \text{energy}]$, and $\text{mask} \in [0, 2^{L} - 1]$ ($L =$ litter count).

2. **Transitions**:
   - Move to valid neighbor $(nr, nc)$.
   - $e' = \begin{cases} \text{energy} & \text{if } \text{classroom}[nr][nc] == \text{'R'} \\ e - 1 & \text{otherwise} \end{cases}$
   - $\text{mask}' = \begin{cases} \text{mask} \mid (1 \ll \text{litterMap}[nr][nc]) & \text{if } \text{classroom}[nr][nc] == \text{'L'} \\ \text{mask} & \text{otherwise} \end{cases}$

3. **Invariants**:
   - The BFS queue processes states in non-decreasing order of `steps`.
   - The first time a state satisfies `nxt_mask == targetMask`, `steps + 1` is the minimum path cost.

---

### ✅ Why This Works

- **Breadth-First Search Optimality**: In an unweighted graph, BFS guarantees that nodes are visited in increasing order of distance from the root.
- **Complete State Tracking**: Including both `energy` and `mask` in the state array avoids prematurely pruning valid paths that revisit the same cell with either a higher energy level or more litter collected.
- **Bitwise Collection Tracking**: Bitwise OR (`nxt_mask |= (1 << litterMap[nr][nc])`) efficiently maintains the exact set of litter items collected without needing set data structures.
- **Strict Energy Enforcement**: The check `if (nxt_e == 0 && nextCell != 'R') continue;` correctly enforces the rule that energy reaching zero halts movement unless restored on a reset cell `'R'`.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(m \times n \times \text{energy} \times 2^{L})$
  - There are at most $m \times n \times (\text{energy} + 1) \times 2^{L}$ states.
  - Each state is added to the queue at most once and tries 4 transitions.
  - With $m, n \le 20$, $\text{energy} \le 50$, $L \le 10$, total states $\approx 20 \times 20 \times 51 \times 1024 \approx 2 \times 10^7$, which comfortably executes within time limits.

- **Space Complexity**: $\mathcal{O}(m \times n \times \text{energy} \times 2^{L})$
  - Dominated by the 4D boolean `visited` array of size $m \times n \times (\text{energy} + 1) \times 2^{L}$ and the BFS queue storing states.

---

### 🧠 DSA Pattern

- **BFS (Breadth-First Search)**
- **Bitmask State Compression** (used for tracking subset of visited litter items)

---

### ⚠️ Common Mistakes

1. **Incorrect Energy Reset Order**:
   - If energy reset logic were applied *before* decrementing energy or checking `nxt_e == 0`, a cell with `0` energy could mistakenly reset or move further when it shouldn't. Your code correctly decrements energy first (`nxt_e = e - 1`) and then resets if `nextCell == 'R'`.

2. **Omitting Energy or Mask in Visited Set**:
   - Storing visited as just `boolean[m][n]` or `boolean[m][n][mask]` would cause incorrect pruning because you might revisit a cell with more energy, which could unlock previously unreachable litter.

3. **Bitwise Bit Shift Limits**:
   - This approach relies on $L \le 31$ because standard integer shift `(1 << L)` is used. Since constraints state at most 10 `'L'` cells, $2^{10} = 1024$, which fits easily in a standard bitmask.

---

### 🚀 Optimization Notes

1. **Memory Footprint**:
   - The 4D array `visited` requires $\sim 20\text{MB}$ of memory, which easily fits inside standard platform memory limits.
   
2. **Queue Memory Optimization**:
   - Instead of storing `steps` inside the array enqueued (`new int[]{startX, startY, energy, 0, 0}`), you could use level-by-level BFS iteration (`size = queue.size()`) and track `steps` as a loop variable, reducing allocation overhead.

3. **Early Exit Optimization**:
   - Your code checks `if (nxt_mask == targetMask) return steps + 1;` right inside the neighbor exploration loop, which avoids pushing the final state into the queue and terminates immediately on discovery.
