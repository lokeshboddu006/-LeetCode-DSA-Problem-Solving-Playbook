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

A server at position `(i, j)` can communicate if there is at least one other server in the same row `i` OR the same column `j`. 

Instead of searching along the entire row and column for every single server, the solution pre-calculates the total count of servers present in each row (`rows[i]`) and each column (`cols[j]`).

When examining a specific server at `(i, j)`, that server itself contributes `1` to `rows[i]` and `1` to `cols[j]`. Therefore, if the server is completely isolated (no other servers in row `i` and no other servers in column `j`), the sum `rows[i] + cols[j]` will equal `1 + 1 = 2`. If there is at least one other server in its row or column, `rows[i] + cols[j]` will be strictly greater than `2`.

### 🔍 Approach

1. **Pre-computation Arrays (`rows` and `cols`):**
   - Create an array `rows` of size `m` (number of rows) and `cols` of size `n` (number of columns) to keep track of total servers in each row and column.

2. **First Pass (Counting Servers):**
   - Traverse the entire grid using a nested `for` loop (`i` from `0` to `m-1`, `j` from `0` to `n-1`).
   - Add `grid[i][j]` directly to `rows[i]` and `cols[j]`. Since `grid[i][j]` is either `1` (server) or `0` (no server), this sums up the number of servers per row and column.

3. **Second Pass (Identifying Communicating Servers):**
   - Initialize `ans = 0`.
   - Traverse the matrix again using a second nested loop.
   - For every cell `(i, j)`, check two conditions:
     1. `grid[i][j] == 1` (a server exists at cell `(i, j)`).
     2. `rows[i] + cols[j] > 2` (there is at least one other server in row `i` or column `j`).
   - If both conditions are satisfied, increment `ans`.

4. **Return Result:**
   - Return `ans`, which holds the total count of servers capable of communicating.

### 🧩 Algorithm

- **Pre-computation (Frequency Counting):**
  $$\text{rows}[i] = \sum_{j=0}^{n-1} \text{grid}[i][j]$$
  $$\text{cols}[j] = \sum_{i=0}^{m-1} \text{grid}[i][j]$$

- **Communicating Server Condition:**
  For a server at `(i, j)` where $\text{grid}[i][j] = 1$:
  $$\text{Server Can Communicate} \iff (\text{rows}[i] > 1) \lor (\text{cols}[j] > 1)$$
  
  Since $\text{grid}[i][j] = 1$, we know $\text{rows}[i] \ge 1$ and $\text{cols}[j] \ge 1$. 
  The logical condition $(\text{rows}[i] > 1 \lor \text{cols}[j] > 1)$ is equivalent to:
  $$\text{rows}[i] + \text{cols}[j] > 2$$

### ✅ Why This Works

- **Double-Counting Trick:** For any cell `(i, j)` with a server (`grid[i][j] == 1`), the server itself is counted once in `rows[i]` and once in `cols[j]`.
- If row `i` has no other servers ($\text{rows}[i] = 1$) AND column `j` has no other servers ($\text{cols}[j] = 1$), then $\text{rows}[i] + \text{cols}[j] = 1 + 1 = 2$.
- If row `i` has another server ($\text{rows}[i] \ge 2$), then $\text{rows}[i] + \text{cols}[j] \ge 2 + 1 = 3 > 2$.
- If column `j` has another server ($\text{cols}[j] \ge 2$), then $\text{rows}[i] + \text{cols}[j] \ge 1 + 2 = 3 > 2$.
- Thus, checking `rows[i] + cols[j] > 2` accurately identifies servers that can communicate without needing explicit `OR` (`||`) operations.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(m \times n)$
  - **Pass 1:** Iterates through all $m \times n$ cells to populate `rows` and `cols`.
  - **Pass 2:** Iterates through all $m \times n$ cells to count communicating servers.
  - Overall time complexity is linear with respect to the matrix size: $\mathcal{O}(m \times n)$.

- **Space Complexity:** $\mathcal{O}(m + n)$
  - Requires two auxiliary 1D arrays: `rows` of size $m$ and `cols` of size $n$.

### 🧠 DSA Pattern

- **Counting / Frequency Array** (Pre-calculating row/column sums)
- **Two-Pass Matrix Traversal**

### ⚠️ Common Mistakes

1. **Omitting `grid[i][j] == 1` in the second pass:**
   If you only check `rows[i] + cols[j] > 2` without checking `grid[i][j] == 1`, empty cells (`0`) residing in rows/columns with multiple servers will mistakenly increment `ans`.

2. **Misinterpreting `> 2` threshold:**
   Confusing why `> 2` is used instead of `> 1`. Remembering that cell `(i, j)` is counted in both `rows[i]` and `cols[j]` explains why `2` is the baseline for an isolated server.

### 🚀 Optimization Notes

- **Optimal Solution:** The solution runs in optimal time $\mathcal{O}(m \times n)$ and space $\mathcal{O}(m + n)$. Any algorithm must inspect each matrix cell at least once, making $\mathcal{O}(m \times n)$ the theoretical lower bound.
- **Clean Expression:** Using `rows[i] + cols[j] > 2` simplifies the condition compared to `rows[i] > 1 || cols[j] > 1`.
