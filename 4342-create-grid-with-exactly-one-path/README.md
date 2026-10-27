<h2><a href="https://leetcode.com/problems/create-grid-with-exactly-one-path">Create Grid With Exactly One Path</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given two integers <code>m</code> and <code>n</code>, representing the number of rows and columns of a grid.</p>

<p>Construct <strong>any</strong> <code>m x n</code> grid consisting only of the characters <code>&#39;.&#39;</code> and <code>&#39;#&#39;</code>, where:</p>

<ul>
	<li><code>&#39;.&#39;</code> represents a free cell.</li>
	<li><code>&#39;#&#39;</code> represents an obstacle cell.</li>
</ul>

<p>A <strong>valid path</strong> is a sequence of free cells that:</p>

<ul>
	<li>Starts at the top-left cell <code>(0, 0)</code>.</li>
	<li>Ends at the bottom-right cell <code>(m - 1, n - 1)</code>.</li>
	<li>Moves only:
	<ul>
		<li>Right, from <code>(i, j)</code> to <code>(i, j + 1)</code>, or</li>
		<li>Down, from <code>(i, j)</code> to <code>(i + 1, j)</code>.</li>
	</ul>
	</li>
</ul>

<p>Return any grid such that there is <strong>exactly one valid path</strong> from the top-left cell to the bottom-right cell.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">m = 2, n = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">[&quot;..#&quot;,&quot;#..&quot;]</span></p>

<p><strong>Explanation:</strong></p>

<p><img src="https://assets.leetcode.com/uploads/2026/05/26/screenshot-2026-05-26-at-61005pm.png" style="width: 200px; height: 95px;" /></p>

<p>The only valid path is: <code>(0,0) &rarr; (0,1) &rarr; (1,1) &rarr; (1,2)</code></p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">m = 3, n = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">[&quot;..#&quot;,&quot;#..&quot;,&quot;##.&quot;]</span></p>

<p><strong>Explanation:</strong></p>

<p><img src="https://assets.leetcode.com/uploads/2026/05/26/screenshot-2026-05-26-at-61129pm.png" style="width: 220px; height: 150px;" /></p>

<p>The only valid path is: <code>(0,0) &rarr; (0,1) &rarr; (1,1) &rarr; (1,2) &rarr; (2,2)</code></p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">m = 1, n = 4</span></p>

<p><strong>Output:</strong> <span class="example-io">[&quot;....&quot;]</span></p>

<p><strong>Explanation:</strong></p>

<p>The only valid path is: <code>(0,0) &rarr; (0,1) &rarr; (0,2) &rarr; (0,3)</code></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= m, n &lt;= 25</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code constructs a grid with an "L-shaped" open corridor: the entire top row (row `0`) and the entire rightmost column (column `n - 1`) consist of free cells (`'.'`), while all remaining cells are filled with obstacles (`'#'`).

By placing obstacles everywhere else in rows `1` through `m - 1`, any attempt to step down before reaching the top-right corner `(0, n - 1)` lands on an obstacle (`'#'`). This forces a single unique path from `(0, 0)`: move right all the way to `(0, n - 1)`, and then move down all the way to `(m - 1, n - 1)`.

### 🔍 Approach

1. **Grid Allocation**: A `String[]` array named `grid` of size `m` is initialized to hold the rows of the grid.
2. **Default Row Pattern**: `Arrays.fill(grid, "#".repeat(n - 1) + ".")` fills all `m` elements of `grid` with a string consisting of `n - 1` obstacle characters (`'#'`) followed by `1` free cell character (`'.'`).
3. **Top Row Overwrite**: `grid[0]` is overwritten with `".".repeat(n)`, making the entire first row consist of `n` free cell characters (`'.'`).
4. **Return**: The constructed `grid` is returned.

### 🧩 Algorithm

1. **Construct the Grid**:
   - `grid[0] = "...." ... "."` (length $n$)
   - `grid[i] = "###...#."` for all $1 \le i < m$ (where the first $n - 1$ characters are `'#'` and the last character is `'.'`).
2. **Path Mechanics**:
   - At `(0, 0)`, moving down to `(1, 0)` hits `'#'` (if $m > 1$). The only available move is right to `(0, 1)`.
   - At any cell `(0, j)` with $j < n - 1$, moving down to `(1, j)` hits `'#'`. The only available move is right to `(0, j + 1)`.
   - At `(0, n - 1)`, moving right is out of bounds. The only available move is down to `(1, n - 1)`.
   - From `(i, n - 1)` with $1 \le i < m - 1$, moving right is out of bounds, and moving down to `(i + 1, n - 1)` is a free cell (`'.'`).
   - Thus, exactly 1 path exists: `(0, 0) -> (0, 1) -> ... -> (0, n - 1) -> (1, n - 1) -> ... -> (m - 1, n - 1)`.

### ✅ Why This Works

The construction guarantees that every potential branch point that would allow moving down early leads into an obstacle cell (`'#'`). Because standard movement rules only allow going **right** or **down**:
- Moving down early from any column $j < n - 1$ is blocked.
- Moving right along row $0$ is completely unblocked.
- Once at column $n - 1$, moving down is completely unblocked all the way to row $m - 1$.

Since no alternative valid branching moves exist, there is strictly one valid path from start to end.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(m \times n)$  
  Creating each row string takes $\mathcal{O}(n)$ time. Filling $m$ rows takes $\mathcal{O}(m \times n)$ time, and updating the first row takes $\mathcal{O}(n)$ time. Overall runtime is linear with respect to the total number of cells in the grid.

- **Space Complexity:** $\mathcal{O}(m \times n)$  
  The solution constructs an array of $m$ strings, each of length $n$, to hold the grid output.

### 🧠 DSA Pattern

- **Constructive Algorithm** / Matrix Manipulation

### ⚠️ Common Mistakes

- **Incorrect string repetition counts:** Using `"#".repeat(n)` instead of `"#".repeat(n - 1)` would put an obstacle at `(i, n - 1)`, blocking the path from ever reaching the bottom-right corner.
- **Handling $n = 1$:** When $n = 1$, `"#".repeat(n - 1)` produces an empty string `""`, making the default row string just `"."`. Java's `String.repeat(0)` correctly returns `""` without throwing an exception.

### 🚀 Optimization Notes

- This solution is already optimal in terms of time and space complexity as creating the $m \times n$ grid inherently takes $\mathcal{O}(m \times n)$ operations.
- **Redundant Fill**: `Arrays.fill` populates `grid[0]` with `"#".repeat(n - 1) + "."` before it is immediately overwritten by `grid[0] = ".".repeat(n)`. While this is a minor unnecessary work item for `grid[0]`, it keeps the code concise and clean without affecting the asymptotic time complexity.
