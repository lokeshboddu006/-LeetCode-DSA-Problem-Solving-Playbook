<h2><a href="https://leetcode.com/problems/count-servers-that-communicate">Count Servers that Communicate</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a map of a server center, represented as a <code>m * n</code> integer matrix&nbsp;<code>grid</code>, where 1 means that on that cell there is a server and 0 means that it is no server. Two servers are said to communicate if they are on the same row or on the same column.<br />
<br />
Return the number of servers&nbsp;that communicate with any other server.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2019/11/14/untitled-diagram-6.jpg" style="width: 202px; height: 203px;" /></p>

<pre>
<strong>Input:</strong> grid = [[1,0],[0,1]]
<strong>Output:</strong> 0
<b>Explanation:</b>&nbsp;No servers can communicate with others.</pre>

<p><strong class="example">Example 2:</strong></p>

<p><strong><img alt="" src="https://assets.leetcode.com/uploads/2019/11/13/untitled-diagram-4.jpg" style="width: 203px; height: 203px;" /></strong></p>

<pre>
<strong>Input:</strong> grid = [[1,0],[1,1]]
<strong>Output:</strong> 3
<b>Explanation:</b>&nbsp;All three servers can communicate with at least one other server.
</pre>

<p><strong class="example">Example 3:</strong></p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2019/11/14/untitled-diagram-1-3.jpg" style="width: 443px; height: 443px;" /></p>

<pre>
<strong>Input:</strong> grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
<strong>Output:</strong> 4
<b>Explanation:</b>&nbsp;The two servers in the first row can communicate with each other. The two servers in the third column can communicate with each other. The server at right bottom corner can&#39;t communicate with any other server.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>m == grid.length</code></li>
	<li><code>n == grid[i].length</code></li>
	<li><code>1 &lt;= m &lt;= 250</code></li>
	<li><code>1 &lt;= n &lt;= 250</code></li>
	<li><code>grid[i][j] == 0 or 1</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Two servers can communicate if they share the same row or the same column. This means a server at position `(i, j)` can communicate with at least one other server if:
1. There is more than 1 server in row `i` (`row[i] > 1`), **OR**
2. There is more than 1 server in column `j` (`col[j] > 1`).

Instead of searching along the row and column every time a server is found, the solution pre-calculates the server counts for every row and column in a first pass. In a second pass, it checks whether each server satisfies the condition to communicate.

### 🔍 Approach

1. **Dimensions & Tracking Arrays**:
   - `m` stores the number of rows (`grid.length`), and `n` stores the number of columns (`grid[0].length`).
   - `row` array of size `m` tracks the total number of servers in each row.
   - `col` array of size `n` tracks the total number of servers in each column.

2. **First Pass (Counting Phase)**:
   - Nested loops traverse every cell `(i, j)` in `grid`.
   - Whenever `grid[i][j] == 1`, both `row[i]` and `col[j]` are incremented by `1`.

3. **Second Pass (Verification Phase)**:
   - Initialize `ans = 0` to store the total count of communicating servers.
   - Traverse the grid again cell by cell.
   - For every cell where `grid[i][j] == 1`, check if `row[i] > 1` or `col[j] > 1`.
   - If either condition holds true, the server at `(i, j)` can communicate, so `ans` is incremented.

4. **Return Result**:
   - Return `ans`.

### 🧩 Algorithm

1. Pre-processing / Counting Invariant:
   $$\text{row}[i] = \sum_{j=0}^{n-1} \text{grid}[i][j]$$
   $$\text{col}[j] = \sum_{i=0}^{m-1} \text{grid}[i][j]$$

2. Decision Rule for cell $(i, j)$:
   $$\text{CanCommunicate}(i, j) = (\text{grid}[i][j] == 1) \land (\text{row}[i] > 1 \lor \text{col}[j] > 1)$$

3. Total Count:
   $$\text{ans} = \sum_{i=0}^{m-1} \sum_{j=0}^{n-1} \mathbb{I}(\text{CanCommunicate}(i, j))$$

### ✅ Why This Works

- **Pass 1 Correctness**: Summing all `1`s along rows and columns correctly records the total degree of connectivity per row and column.
- **Pass 2 Correctness**: A server at `(i, j)` is isolated if and only if it is the only server in its row (`row[i] == 1`) AND the only server in its column (`col[j] == 1`). Taking the negation of this condition (`row[i] > 1 || col[j] > 1`) guarantees that any server counted in `ans` has at least one other server sharing its row or column.
- Every server is evaluated independently, ensuring no double counting or missed servers.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(m \times n)$
  - Pass 1 visits all $m \times n$ cells once.
  - Pass 2 visits all $m \times n$ cells once.
  - Total operations: $2 \times m \times n \implies \mathcal{O}(m \times n)$.

- **Space Complexity:** $\mathcal{O}(m + n)$
  - Array `row` uses $\mathcal{O}(m)$ auxiliary space.
  - Array `col` uses $\mathcal{O}(n)$ auxiliary space.

### 🧠 DSA Pattern

- **Counting / Pre-computation (Matrix Frequency Arrays)**: Storing frequencies/counts along dimensions (rows/columns) to reduce lookup time from $\mathcal{O}(m + n)$ per cell to $\mathcal{O}(1)$.

### ⚠️ Common Mistakes

1. **Double Counting**: Trying to add `row[i]` directly to the total instead of checking server-by-server. This solution avoids double counting by checking each individual cell `(i, j)` once in the second pass.
2. **Checking non-server cells**: Forgetting the `grid[i][j] == 1` check in the second pass would count empty cells (`0`) as communicating servers if their row or column had servers. The code correctly includes `grid[i][j] == 1`.
3. **Using `&&` instead of `||`**: Requiring both `row[i] > 1 && col[j] > 1` would wrongly ignore servers that share only a row or only a column with another server. The code correctly uses `row[i] > 1 || col[j] > 1`.

### 🚀 Optimization Notes

- **Optimal Time Complexity**: $\mathcal{O}(m \times n)$ is optimal because every cell in the grid must be inspected at least once.
- **Micro-Optimization (Early Counting)**: In the second pass, if `row[i] > 1` is true for a row, all servers in that row can communicate. However, the current two-pass approach is already very clean, readable, and asymptotically optimal.
