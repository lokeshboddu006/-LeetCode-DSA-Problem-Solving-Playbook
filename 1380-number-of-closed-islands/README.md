<h2><a href="https://leetcode.com/problems/number-of-closed-islands">Number of Closed Islands</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a 2D&nbsp;<code>grid</code> consists of <code>0s</code> (land)&nbsp;and <code>1s</code> (water).&nbsp; An <em>island</em> is a maximal 4-directionally connected group of <code><font face="monospace">0</font>s</code> and a <em>closed island</em>&nbsp;is an island <strong>totally</strong>&nbsp;(all left, top, right, bottom) surrounded by <code>1s.</code></p>

<p>Return the number of <em>closed islands</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2019/10/31/sample_3_1610.png" style="width: 240px; height: 120px;" /></p>

<pre>
<strong>Input:</strong> grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
<strong>Output:</strong> 2
<strong>Explanation:</strong> 
Islands in gray are closed because they are completely surrounded by water (group of 1s).</pre>

<p><strong class="example">Example 2:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2019/10/31/sample_4_1610.png" style="width: 160px; height: 80px;" /></p>

<pre>
<strong>Input:</strong> grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
<strong>Output:</strong> 1
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> grid = [[1,1,1,1,1,1,1],
&nbsp;              [1,0,0,0,0,0,1],
&nbsp;              [1,0,1,1,1,0,1],
&nbsp;              [1,0,1,0,1,0,1],
&nbsp;              [1,0,1,1,1,0,1],
&nbsp;              [1,0,0,0,0,0,1],
               [1,1,1,1,1,1,1]]
<strong>Output:</strong> 2
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= grid.length, grid[0].length &lt;= 100</code></li>
	<li><code>0 &lt;= grid[i][j] &lt;=1</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

A closed island is an island (a connected group of `0`s) that is completely surrounded by water (`1`s) on all four sides and does **not** touch the boundary of the grid. 

Instead of searching for closed islands directly and checking if they hit a boundary during the search, this solution uses a two-phase strategy:

1. **Eliminate invalid islands**: Any land (`0`) connected to the grid boundary can never form a closed island. By starting Depth-First Search (DFS) from every land cell on the four borders, we can "sink" (change to `1`) all land reachable from the boundary.
2. **Count remaining islands**: After the boundary pass, any remaining `0` in the interior of the grid is guaranteed to be completely enclosed by water. We iterate through the inner grid, count each unvisited land cell as a valid closed island, and flood-fill it to sink the entire island.

---

### 🔍 Approach

1. **First Phase: Sinking Boundary-Connected Land**
   - Iterate through every row index `i` from `0` to `m - 1`. If the cell on the left boundary (`grid[i][0]`) or right boundary (`grid[i][n - 1]`) is `0`, run `dfs` starting from that cell.
   - Iterate through every column index `j` from `0` to `n - 1`. If the cell on the top boundary (`grid[0][j]`) or bottom boundary (`grid[m - 1][j]`) is `0`, run `dfs` starting from that cell.
   - The `dfs` call mutates `0` to `1` for all connected land cells, effectively removing all boundary-touching islands from consideration.

2. **Second Phase: Counting Closed Islands**
   - Initialize `ans = 0`.
   - Iterate through the interior grid coordinates: `i` from `1` to `m - 2` and `j` from `1` to `n - 2`.
   - Whenever a cell `grid[i][j] == 0` is encountered, it represents a new, unvisited closed island:
     - Increment `ans`.
     - Invoke `dfs(grid, i, j)` to turn all connected `0`s of this island into `1`s so they are not counted again.

3. **DFS Helper (`dfs`)**
   - **Base condition**: If `(i, j)` is out of bounds, or if `grid[i][j] == 1` (water/already visited), return immediately.
   - **Mark visited**: Set `grid[i][j] = 1`.
   - **Recursive calls**: Recursively invoke `dfs` in all four orthogonal directions (down, up, right, left).

---

### 🧩 Algorithm

1. **Boundary Traversal & Flood Fill**:
   $$\forall i \in [0, m-1]: \text{if } grid[i][0] == 0 \implies \text{dfs}(i, 0); \quad \text{if } grid[i][n-1] == 0 \implies \text{dfs}(i, n-1)$$
   $$\forall j \in [0, n-1]: \text{if } grid[0][j] == 0 \implies \text{dfs}(0, j); \quad \text{if } grid[m-1][j] == 0 \implies \text{dfs}(m-1, j)$$

2. **Interior Traversal**:
   $$\forall i \in [1, m-2], \forall j \in [1, n-2]: \text{if } grid[i][j] == 0 \implies ans \gets ans + 1, \text{dfs}(i, j)$$

3. **DFS Function Base & Step**:
   $$\text{dfs}(i, j): \text{If out of bounds or } grid[i][j] == 1 \implies \text{return}$$
   $$grid[i][j] \gets 1$$
   $$\text{dfs}(i+1, j), \text{dfs}(i-1, j), \text{dfs}(i, j+1), \text{dfs}(i, j-1)$$

---

### ✅ Why This Works

- **Exclusion of Non-Closed Islands**: Any island that reaches the boundary is disqualified from being a closed island. By starting DFS from all boundary land cells, every node in those components is changed to `1`.
- **Isolation of Closed Islands**: After phase 1, all remaining `0`s are strictly enclosed within `1`s and cannot reach the grid boundaries.
- **In-place Visited Marking**: Mutating `grid[i][j] = 1` acts as a visited flag. This prevents infinite loops during recursion and ensures each closed island is counted exactly once.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(m \times n)$, where $m$ is the number of rows and $n$ is the number of columns.
  - Every cell in the grid is visited a constant number of times across the loops and DFS calls. Each land cell `0` is mutated to `1` at most once.
- **Space Complexity**: $\mathcal{O}(m \times n)$ worst-case auxiliary space due to the call stack of the recursive DFS (e.g., if an island forms a long snake-like path covering the entire grid).
  - No additional data structures are used, as the grid itself is updated in-place.

---

### 🧠 DSA Pattern

- **Graph Traversal (Depth-First Search / Flood Fill)**
- **In-place Matrix Mutation**

---

### ⚠️ Common Mistakes

- **Modifying the Input Grid**: The code modifies the input array `grid` directly. If the caller requires the original `grid` to remain unchanged, this approach mutates state permanently.
- **Off-by-one in Interior Loops**: Running the second nested loops from `1` to `m-2` and `1` to `n-2` works only because boundary cells were cleared first. Skipping phase 1 or using incorrect loop bounds would miscount islands.
- **Stack Overflow on Large Grids**: For very large grids, deep recursion could cause a `StackOverflowError` in Java, though the constraints ($m, n \le 100$) keep call stack depth safely within limits.

---

### 🚀 Optimization Notes

- **Already Optimal Time Complexity**: The $\mathcal{O}(m \times n)$ time complexity is asymptotically optimal since every cell must be inspected at least once.
- **Memory Efficiency**: In-place modification avoids allocating an explicit $O(m \times n)$ `boolean[][] visited` array.
- **Boundary Optimization**: Skipping outer row/column indices (`i = 1` to `m - 2`, `j = 1` to `n - 2`) during the second phase avoids redundant checks on boundary cells that are already guaranteed to be `1`.
