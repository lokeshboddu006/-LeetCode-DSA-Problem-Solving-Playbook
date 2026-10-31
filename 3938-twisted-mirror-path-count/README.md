<h2><a href="https://leetcode.com/problems/twisted-mirror-path-count">Twisted Mirror Path Count</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given an <code>m x n</code> binary grid <code>grid</code> where:</p>

<ul>
	<li><code>grid[i][j] == 0</code> represents an empty cell, and</li>
	<li><code>grid[i][j] == 1</code> represents a mirror.</li>
</ul>

<p>A robot starts at the top-left corner of the grid <code>(0, 0)</code> and wants to reach the bottom-right corner <code>(m - 1, n - 1)</code>. It can move only <strong>right</strong> or <strong>down</strong>. If the robot attempts to move into a mirror cell, it is <strong>reflected</strong> before entering that cell:</p>

<ul>
	<li>If it tries to move <strong>right</strong> into a mirror, it is turned <strong>down</strong> and moved into the cell directly below the mirror.</li>
	<li>If it tries to move <strong>down</strong> into a mirror, it is turned <strong>right</strong> and moved into the cell directly to the right of the mirror.</li>
</ul>

<p>If this reflection would cause the robot to move outside the <code>grid</code> boundaries, the path is considered invalid and should not be counted.</p>

<p>Return the number of unique valid paths from <code>(0, 0)</code> to <code>(m - 1, n - 1)</code>.</p>

<p>Since the answer may be very large, return it <strong>modulo</strong> <code>10<sup>9</sup> + 7</code>.</p>

<p><strong>Note</strong>: If a reflection moves the robot into a mirror cell, the robot is immediately reflected again based on the direction it used to enter that mirror: if it entered while moving right, it will be turned down; if it entered while moving down, it will be turned right. This process will continue until either the last cell is reached, the robot moves out of bounds or the robot moves to a non-mirror cell.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">grid = [[0,1,0],[0,0,1],[1,0,0]]</span></p>

<p><strong>Output:</strong> <span class="example-io">5</span></p>

<p><strong>Explanation:</strong></p>

<table style="border: 1px solid black;">
	<thead>
		<tr>
			<th align="center" style="border: 1px solid black;">Number</th>
			<th align="left" style="border: 1px solid black;">Full Path</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td align="center" style="border: 1px solid black;">1</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (0, 1) [M] &rarr; (1, 1) &rarr; (1, 2) [M] &rarr; (2, 2)</td>
		</tr>
		<tr>
			<td align="center" style="border: 1px solid black;">2</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (0, 1) [M] &rarr; (1, 1) &rarr; (2, 1) &rarr; (2, 2)</td>
		</tr>
		<tr>
			<td align="center" style="border: 1px solid black;">3</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (1, 0) &rarr; (1, 1) &rarr; (1, 2) [M] &rarr; (2, 2)</td>
		</tr>
		<tr>
			<td align="center" style="border: 1px solid black;">4</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (1, 0) &rarr; (1, 1) &rarr; (2, 1) &rarr; (2, 2)</td>
		</tr>
		<tr>
			<td align="center" style="border: 1px solid black;">5</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (1, 0) &rarr; (2, 0) [M] &rarr; (2, 1) &rarr; (2, 2)</td>
		</tr>
	</tbody>
</table>

<ul data-end="606" data-start="521">
	<li data-end="606" data-start="521">
	<p data-end="606" data-start="523"><code>[M]</code> indicates the robot attempted to enter a mirror cell and instead reflected.</p>
	</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">grid = [[0,0],[0,0]]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<table style="border: 1px solid black;">
	<thead>
		<tr>
			<th align="center" style="border: 1px solid black;">Number</th>
			<th align="left" style="border: 1px solid black;">Full Path</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td align="center" style="border: 1px solid black;">1</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (0, 1) &rarr; (1, 1)</td>
		</tr>
		<tr>
			<td align="center" style="border: 1px solid black;">2</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (1, 0) &rarr; (1, 1)</td>
		</tr>
	</tbody>
</table>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">grid = </span>[[0,1,1],[1,1,0]]</p>

<p><strong>Output:</strong> 1</p>

<p><strong>Explanation:</strong></p>

<table style="border: 1px solid black;">
	<thead>
		<tr>
			<th align="center" style="border: 1px solid black;">Number</th>
			<th align="left" style="border: 1px solid black;">Full Path</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td align="center" style="border: 1px solid black;">1</td>
			<td align="left" style="border: 1px solid black;">(0, 0) &rarr; (0, 1) [M] &rarr; (1, 1) [M] &rarr; (1, 2)</td>
		</tr>
	</tbody>
</table>
<code>(0, 0) &rarr; (1, 0) [M] &rarr; (1, 1) [M] &rarr; (2, 1)</code> goes out of bounds, so it is invalid.</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li data-end="41" data-start="21"><code data-end="39" data-start="21">m == grid.length</code></li>
	<li data-end="67" data-start="44"><code data-end="65" data-start="44">n == grid[i].length</code></li>
	<li data-end="91" data-start="70"><code data-end="89" data-start="70">2 &lt;= m, n &lt;= 500</code></li>
	<li data-end="129" data-start="94"><code data-end="106" data-start="94">grid[i][j]</code> is either <code data-end="120" data-is-only-node="" data-start="117">0</code> or <code data-end="127" data-start="124">1</code>.</li>
	<li data-end="169" data-start="132"><code data-end="167" data-start="132">grid[0][0] == grid[m - 1][n - 1] == 0</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution models the path counts as directional flows passing through each grid cell `(i, j)`:
1. **Rightward flow (`ri`)**: The number of valid paths entering cell `(i, j)` from the left (moving right).
2. **Downward flow (`arr[j]`)**: The number of valid paths entering cell `(i, j)` from above (moving down).

When reaching a cell `(i, j)`:
- **Empty Cell (`0`)**: Paths from both directions merge. The total ways to reach this cell is `(ri + arr[j]) % mod`. From an empty cell, the robot can continue moving either right (into `(i, j+1)`) or down (into `(i+1, j)`). Thus, both outgoing flows (rightward and downward) become equal to this total sum.
- **Mirror Cell (`1`)**: Paths do not merge. Instead, the directional momentum is redirected:
  - Rightward flow (`ri`) hits the mirror and bounces **downward** into column `j` for row `i+1`.
  - Downward flow (`arr[j]`) hits the mirror and bounces **rightward** into row `i` for column `j+1`.
  - Conceptually, a mirror cell simply **swaps** the rightward flow `ri` and the downward flow `arr[j]`.

---

### 🔍 Approach

1. **DP State Representation**:
   - `arr`: A 1D array of size equal to the number of columns (`grid[0].length`). `arr[j]` stores the incoming downward path count for column `j` at the current row.
   - `ri`: A variable tracking the incoming rightward path count from the left for the current cell `(i, j)`.

2. **Initialization**:
   - `arr[0] = 1`: Represents the 1 starting path at `(0, 0)`.
   - `mod = 1_000_000_007`: Prevents integer overflow as path counts grow exponentially.

3. **Row-by-Row Iteration**:
   - For each `row` in `grid`:
     - Reset `ri = 0` at the start of the row (no path enters from outside the left boundary).
     - Traverse each column `j` from `0` to `n - 1`:
       - **If `row[j] == 1` (Mirror)**:
         - Swap `ri` and `arr[j]` using a temporary variable `t`:
           - `t = ri`
           - `ri = arr[j]` (downward flow becomes new rightward flow)
           - `arr[j] = t` (rightward flow becomes new downward flow)
       - **If `row[j] == 0` (Empty)**:
         - Update both `ri` and `arr[j]` to `(ri + arr[j]) % mod`.

4. **Return Result**:
   - After traversing the entire grid, `arr[arr.length - 1]` contains the total valid paths reaching the bottom-right cell `(m - 1, n - 1)`.

---

### 🧩 Algorithm

For each cell `(i, j)` with inputs `ri` (from left) and `arr[j]` (from top):

$$
\text{Transition for } grid[i][j]:
\begin{cases} 
\text{swap}(ri, arr[j]), & \text{if } grid[i][j] = 1 \\
ri = arr[j] = (ri + arr[j]) \pmod{10^9 + 7}, & \text{if } grid[i][j] = 0
\end{cases}
$$

**Invariants maintained**:
- `ri` always holds the count of paths moving right into `(i, j+1)`.
- `arr[j]` always holds the count of paths moving down into `(i+1, j)`.

---

### ✅ Why This Works

- **Correctness of Mirror Swapping**: Moving right into a mirror redirects the robot down into column `j`, which matches setting `arr[j] = ri`. Moving down into a mirror redirects the robot right into row `i`, which matches setting `ri = arr[j]`. Swapping the two variables handles both reflections simultaneously in $O(1)$ time without simulating multi-step bounces explicitly.
- **Correctness of Empty Cells**: Paths arriving from top and left accumulate at an empty cell. Since the robot can choose either direction from an empty cell, both outgoing branches (right and down) carry the total accumulated count.
- **Boundary Handling**: Out-of-bounds reflections naturally drop off because `ri` resets to `0` at the start of each row, and any downward flow past row $m-1$ is never processed further.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(m \times n)$
  - The algorithm uses two nested loops traversing every cell in the $m \times n$ grid exactly once. Each cell operation takes $\mathcal{O}(1)$ time.

- **Space Complexity**: $\mathcal{O}(n)$
  - Only a single 1D array `arr` of length $n$ (number of columns) is used to store the DP state across rows, optimizing space from $\mathcal{O}(m \times n)$ down to $\mathcal{O}(n)$.

---

### 🧠 DSA Pattern

- **Dynamic Programming (Space-Optimized / 1D DP)**: Optimizing a 2D grid DP table into a single array by updating states row-by-row in place.

---

### ⚠️ Common Mistakes

1. **Forgetting to Reset `ri`**:
   - `ri` must be reset to `0` at the start of every row (`int ri = 0`). If `ri` persists across rows, paths would incorrectly cross from the end of one row to the beginning of the next.

2. **Misinterpreting Mirror Bounces**:
   - Treating mirror cells as obstacles (like walls `grid[i][j] == 1` in standard grid DP) instead of path redirectors.

3. **Integer Overflow**:
   - Adding `ri + arr[j]` without applying `% mod` at each empty cell can exceed `Integer.MAX_VALUE`.

---

### 🚀 Optimization Notes

- **Space Optimization**: The solution is already space-optimal for the given approach ($\mathcal{O}(n)$ auxiliary space).
- **Dimension Optimization**: If $m < n$, the grid could theoretically be transposed to reduce space complexity to $\mathcal{O}(\min(m, n))$, though $\mathcal{O}(n)$ is already lightweight since $n \le 500$.
