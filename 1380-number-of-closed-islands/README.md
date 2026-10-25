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

An island is "closed" if it is completely surrounded by water (`1`s) and does not touch any of the grid boundaries.

Instead of keeping track of whether an island touches a boundary during traversal, your solution uses a **two-pass flood-fill strategy**:
1. **Eliminate boundary-connected islands first**: Any island that reaches the outer edge of the grid cannot be closed. By running DFS on all land cells (`0`s) on the boundary, you "sink" them (change them to `1`s).
2. **Count remaining islands**: After all boundary-connected land is turned into water, any `0` remaining in the grid must belong to a completely enclosed island. You can then simply count connected components of `0`s in a standard second pass.

### 🔍 Approach

1. **Grid Dimensions**:
   - Store grid dimensions in class fields `m = g.length` and `n = g[0].length`.

2. **First Pass — Sinking Boundary Islands**:
   - Loop through all cells `(i, j)` in the grid.
   - Check if `(i, j)` lies on the boundary (`i == 0 || j == 0 || i == m - 1 || j == n - 1`).
   - If the boundary cell is land (`g[i][j] == 0`), call the recursive helper `d(g, i, j)` to sink this cell and all land cells connected to it.

3. **Second Pass — Counting Closed Islands**:
   - Iterate through the entire grid once more.
   - When a land cell `g[i][j] == 0` is found, it is guaranteed to be part of a closed island (since all boundary-connected land was removed in Pass 1).
   - Call `d(g, i, j)` to sink all cells belonging to this closed island so they aren't counted again.
   - Increment `ans`.

4. **DFS Helper `d(g, r, c)`**:
   - **Base Case**: Return immediately if `(r, c)` is out of bounds (`r < 0 || c < 0 || r >= m || c >= n`) or if `g[r][c] == 1` (already water or visited).
   - **Mark as Visited**: Set `g[r][c] = 1` (sinking the land cell in-place).
   - **Recursion**: Make 4 recursive DFS calls in orthogonal directions: down `(r+1, c)`, up `(r-1, c)`, right `(r, c+1)`, and left `(r, c-1)`.

### 🧩 Algorithm

- **Step 1 (Boundary Cleansing)**:
  For each $(i, j)$ where $i \in \{0, m-1\}$ or $j \in \{0, n-1\}$:
  $$\text{if } g[i][j] == 0 \implies \text{DFS}(g, i, j)$$

- **Step 2 (Enclosed Island Counting)**:
  For each $i \in [0, m-1], j \in [0, n-1]$:
  $$\text{if } g[i][j] == 0 \implies \text{DFS}(g, i, j); \quad ans \leftarrow ans + 1$$

- **DFS Recurrence**:
  $$\text{DFS}(r, c) = \begin{cases} \text{return} & \text{if out of bounds or } g[r][c] == 1 \\ g[r][c] \leftarrow 1, \text{ recurse on } (r\pm1, c) \text{ and } (r, c\pm1) & \text{otherwise} \end{cases}$$

### ✅ Why This Works

- **Boundary Invariant**: Any cell connected 4-directionally to a boundary land cell cannot be part of a closed island. Sinking all boundary land in the first pass cleanly removes all non-closed islands from consideration.
- **Exhaustive Counting**: After the first pass, any remaining `0` cell in the grid *must* belong to an island that is completely enclosed by water.
- **No Double Counting**: Calling `d(g, i, j)` on a remaining `0` turns the entire connected island into `1`s. This guarantees each closed island component is visited and counted exactly once.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(m \times n)$
  - The nested loops in both passes iterate through each cell a constant number of times.
  - Every cell is processed by the recursive helper `d` at most once before being converted from `0` to `1`. Subsequent visits to `1` cells hit the base case in $\mathcal{O}(1)$ time.

- **Space Complexity**: $\mathcal{O}(m \times n)$
  - In the worst-case (e.g., a grid filled with land arranged in a long snake pattern), the call stack of the recursive DFS can reach a depth proportional to the total number of cells, i.e., $\mathcal{O}(m \times n)$.
  - No auxiliary data structures are used because the grid is modified in-place.

### 🧠 DSA Pattern

- **Depth-First Search (DFS)**
- **Flood Fill**
- **In-Place Matrix Traversal**

### ⚠️ Common Mistakes

- **Input Mutation**: The solution modifies the input matrix `g` directly by overwriting `0`s with `1`s. If the problem context required keeping the input matrix intact, this would be a side effect.
- **Redundant Cell Checks in First Pass**: The nested loop in the first pass iterates over every cell $(i, j)$ in the matrix and checks whether `i == 0 || j == 0 ...`. While correct, it checks internal cells unnecessarily during the loop condition.
- **Stack Overflow Risk**: For large grid dimensions, deep recursive DFS calls can hit call stack limits, although for the given constraint ($100 \times 100$), recursion depth is well within standard limits.

### 🚀 Optimization Notes

- The solution is already optimal in terms of time complexity ($\mathcal{O}(m \times n)$).
- **Minor Loop Optimization**: In the first pass, instead of looping over all $m \times n$ cells and using an `if` condition to check if a cell is on the boundary, you could iterate exclusively over the border rows (`i = 0` and `i = m - 1`) and border columns (`j = 0` and `j = n - 1`).
