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

In this problem, paths can enter any cell `(i, j)` from two directions:
1. Moving **right** from the left neighbor `(i, j - 1)`.
2. Moving **down** from the top neighbor `(i - 1, j)`.

Depending on whether cell `(i, j)` is an empty space (`0`) or a mirror (`1`), the incoming paths are redirected:
- **Empty cell (`0`)**: Any incoming path (whether arriving from the left or top) can choose to continue **right** or turn **down**.
- **Mirror cell (`1`)**: Incoming paths are forced to reflect:
  - Paths entering from the **left** (moving right) are turned **down**.
  - Paths entering from the **top** (moving down) are turned **right**.

Your solution models these direction transitions as a linear state transfer using a 3D transition matrix `mat[c]`, where `c` is the cell type (`0` or `1`). By processing the grid row-by-row and updating a 1D DP array (`dp`) alongside a running `left` variable, you track the path counts propagating rightward and downward across the grid.

### 🔍 Approach

1. **Predefined State Transition Matrix (`mat`)**:
   - `mat[0]` = `{{1, 1}, {1, 1}}`: For an empty cell (`0`), incoming paths from left and top both branch into outgoing right and outgoing down directions.
   - `mat[1]` = `{{0, 1}, {1, 0}}`: For a mirror cell (`1`):
     - Outgoing right = `0 * left + 1 * top` (incoming down reflects right).
     - Outgoing down = `1 * left + 0 * top` (incoming right reflects down).

2. **State Storage Optimization**:
   - `dp[j]`: Represents the number of valid paths exiting cell `(i-1, j)` moving **down** into cell `(i, j)`.
   - `left`: Represents the number of valid paths exiting cell `(i, j-1)` moving **right** into cell `(i, j)`.
   - `dp[0]` is initialized to `1` to account for the single starting path into `(0, 0)`.

3. **Grid Traversal & State Transition**:
   - For each row in grid `A`:
     - Reset `left = 0` at the start of the row because no path can enter from outside the left boundary.
     - For each column `j`:
       - Cache `top = dp[j]` (incoming downward paths).
       - Read cell type `c = row[j]`.
       - Compute new outgoing downward paths for column `j`:
         `dp[j] = (left * mat[c][1][0] + top * mat[c][1][1]) % MOD`
       - Compute new outgoing rightward paths for column `j + 1`:
         `left = (left * mat[c][0][0] + top * mat[c][0][1]) % MOD`

4. **Final Return**:
   - After completing the traversal of the final cell `(m - 1, n - 1)`, `left` accumulates the total valid paths reaching the destination modulo `10^9 + 7`.

### 🧩 Algorithm

For cell `(i, j)` with value $c \in \{0, 1\}$:

Let the incoming state vector be:
$$\vec{v}_{\text{in}} = \begin{pmatrix} \text{left} \\ \text{top} \end{pmatrix}$$

Where:
- $\text{left}$ = incoming paths moving right from $(i, j-1)$
- $\text{top}$ = incoming paths moving down from $(i-1, j)$

The state transformation matrix $M_c$ is defined as:
- $M_0 = \begin{pmatrix} 1 & 1 \\ 1 & 1 \end{pmatrix}$ (Empty cell)
- $M_1 = \begin{pmatrix} 0 & 1 \\ 1 & 0 \end{pmatrix}$ (Mirror cell)

The outgoing state vector is computed via matrix multiplication:
$$\begin{pmatrix} \text{new\_left} \\ \text{new\_dp}[j] \end{pmatrix} = M_c \begin{pmatrix} \text{left} \\ \text{top} \end{pmatrix} \pmod{10^9 + 7}$$

### ✅ Why This Works

- **Reflections are correctly directed**:
  - `mat[1][0][1] = 1`: Incoming top path (`top`) becomes an outgoing right path (`left`).
  - `mat[1][1][0] = 1`: Incoming left path (`left`) becomes an outgoing down path (`dp[j]`).
  - `mat[1][0][0] = 0` and `mat[1][1][1] = 0`: Straight-through movement through mirrors is blocked.
- **Boundary Conditions**: Resetting `left = 0` at the beginning of each row ensures paths cannot illegally cross the left border. Paths that reflect out of bounds on the right or bottom accumulate into `left` or `dp[j]` beyond the grid limits and are naturally discarded without reaching `(m-1, n-1)`.
- **Modulo Arithmetic**: Every addition and multiplication operation applies `% MOD` immediately, preventing integer overflow.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(m \times n)$ where $m$ is the number of rows and $n$ is the number of columns. Each cell performs a fixed number of operations ($O(1)$ scalar multiplications and additions).
- **Space Complexity**: $\mathcal{O}(n)$ auxiliary space due to the 1D DP array `dp` of size $n$, plus $\mathcal{O}(1)$ space for the static `mat` array.

### 🧠 DSA Pattern

- **Dynamic Programming (1D Space-Optimized DP)**
- **State Machine / Transition Matrix**

### ⚠️ Common Mistakes

1. **Overwriting `dp[j]` prematurely**: In the inner loop, `top` must be saved before updating `dp[j]` (`int top = dp[j]`). Updating `dp[j]` first would contaminate the `left` calculation with the newly computed downward value instead of the original incoming downward value.
2. **Indexing order of `mat`**: Index `0` represents the rightward direction (`left`) and index `1` represents the downward direction (`dp[j]`). Swapping these dimensions would corrupt the reflection logic.

### 🚀 Optimization Notes

- **Branchless Transitions**: Using `mat[c]` lookup tables eliminates conditional `if-else` branches inside the inner loop, allowing better instruction pipelining and optimization by the JVM.
- **Optimal Space Complexity**: Memory is reduced from $\mathcal{O}(m \times n)$ to $\mathcal{O}(n)$ by overwriting `dp[j]` in-place as the grid is traversed row by row.
