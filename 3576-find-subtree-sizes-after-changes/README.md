<h2><a href="https://leetcode.com/problems/find-subtree-sizes-after-changes">Find Subtree Sizes After Changes</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a tree rooted at node 0 that consists of <code>n</code> nodes numbered from <code>0</code> to <code>n - 1</code>. The tree is represented by an array <code>parent</code> of size <code>n</code>, where <code>parent[i]</code> is the parent of node <code>i</code>. Since node 0 is the root, <code>parent[0] == -1</code>.</p>

<p>You are also given a string <code>s</code> of length <code>n</code>, where <code>s[i]</code> is the character assigned to node <code>i</code>.</p>

<p>We make the following changes on the tree <strong>one</strong> time <strong>simultaneously</strong> for all nodes <code>x</code> from <code>1</code> to <code>n - 1</code>:</p>

<ul>
	<li>Find the <strong>closest</strong> node <code>y</code> to node <code>x</code> such that <code>y</code> is an ancestor of <code>x</code>, and <code>s[x] == s[y]</code>.</li>
	<li>If node <code>y</code> does not exist, do nothing.</li>
	<li>Otherwise, <strong>remove</strong> the edge between <code>x</code> and its current parent and make node <code>y</code> the new parent of <code>x</code> by adding an edge between them.</li>
</ul>

<p>Return an array <code>answer</code> of size <code>n</code> where <code>answer[i]</code> is the <strong>size</strong> of the <span data-keyword="subtree">subtree</span> rooted at node <code>i</code> in the <strong>final</strong> tree.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">parent = [-1,0,0,1,1,1], s = &quot;abaabc&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">[6,3,1,1,1,1]</span></p>

<p><strong>Explanation:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2024/08/15/graphex1drawio.png" style="width: 230px; height: 277px;" />
<p>The parent of node 3 will change from node 1 to node 0.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">parent = [-1,0,4,0,1], s = &quot;abbba&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">[5,2,1,1,1]</span></p>

<p><strong>Explanation:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2024/08/20/exgraph2drawio.png" style="width: 160px; height: 308px;" />
<p>The following changes will happen at the same time:</p>

<ul>
	<li>The parent of node 4 will change from node 1 to node 0.</li>
	<li>The parent of node 2 will change from node 4 to node 1.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == parent.length == s.length</code></li>
	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= parent[i] &lt;= n - 1</code> for all <code>i &gt;= 1</code>.</li>
	<li><code>parent[0] == -1</code></li>
	<li><code>parent</code> represents a valid tree.</li>
	<li><code>s</code> consists only of lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution operates in two main phases:

1. **Tree Rewiring via DFS**: To find the closest ancestor of node $x$ that shares the same character $s[x]$, we perform a Depth-First Search (DFS) on the original tree. By maintaining an array of size 26 that tracks the most recent node seen for each character along the current path from the root, we can instantly update each node's parent pointer to its closest matching ancestor in $O(1)$ time per node.
2. **Subtree Size Computation via Topological Bottom-Up Processing**: After reassigning parents, the modified tree structure is stored in array `q`. To find the final subtree sizes without recursion, the code calculates the child count (in-degree of parent edges) for each node and processes nodes from leaves to root (Kahn's algorithm/topological order), propagating subtree size counts upward.

### 🔍 Approach

1. **Original Tree Construction**:
   - An adjacency list `g` is built from the input `parent` array.
2. **Iterative DFS Traversal with Dynamic Ancestor Tracking**:
   - Array `q` starts as a clone of `parent` and will hold the updated parents.
   - Array `l` (size 26) tracks the most recent node ID along the active search path for each lowercase letter `'a'` through `'z'`.
   - Array `b` (size $n$) stores backup values of `l[c]` so that when exiting a node during DFS, the previous state of `l[c]` can be restored (backtracking).
   - Array `k` acts as an explicit call stack with pointer `t`. Array `v` flags whether a node is being visited for the first time or is being popped after visiting its children.
   - When node `u` is first visited (`!v[u]`):
     - If `l[c]` is not `-1`, node `u`'s parent `q[u]` is updated to `l[c]`.
     - `b[u]` records the previous node present at `l[c]`.
     - `l[c]` is updated to `u`.
     - Children of `u` from `g[u]` are pushed onto the stack `k`.
   - When node `u` is processed a second time (`v[u]` is `true`):
     - The stack pointer `t` decreases, and `l[c]` is restored to `b[u]` to cleanly backtrack.
3. **Bottom-Up Subtree Size Calculation**:
   - Array `a` (subtree sizes) is initialized to `1` for each node.
   - Array `d` keeps track of how many children each node has in the new tree `q`.
   - Nodes with $d[i] == 0$ (leaf nodes in the new tree) are pushed into a queue, reusing array `k` with head `h` and tail `r` pointers.
   - A queue-based BFS processes nodes from leaves up to the root:
     - For each node `x`, its size `a[x]` is added to its parent `q[x]`'s subtree size `a[q[x]]`.
     - Node `q[x]`'s incoming child count `d[q[x]]` is decremented. When it hits `0`, `q[x]` is added to the queue.

### 🧩 Algorithm

- **DFS Traversal State**:
  - `l[c]`: Most recent ancestor with character $c$.
  - State Transition on Enter ($u$):
    $$q[u] = \begin{cases} l[s[u]], & \text{if } l[s[u]] \neq -1 \\ \text{parent}[u], & \text{otherwise} \end{cases}$$
    $$b[u] = l[s[u]], \quad l[s[u]] = u$$
  - State Transition on Exit ($u$):
    $$l[s[u]] = b[u]$$
- **Topological Order Processing (Kahn's Algorithm for Tree Bottom-Up DP)**:
  - Base DP state: $a[u] = 1$ for all $0 \le u < n$.
  - Dependency: Node $y = q[u]$ depends on node $u$.
  - Transition:
    $$a[y] = a[y] + a[u] \quad \text{when processing leaf/resolved node } u$$

### ✅ Why This Works

- **Ancestor Tracking**: Since DFS explores paths from root down to leaves, at any moment during the descent, `l[c]` strictly holds the deepest node on the path from root to the current node that has character `c`. Backtracking via `b[u]` guarantees that branch-specific ancestors do not leak into parallel subtrees.
- **Subtree Aggregation**: The second phase forms a Directed Acyclic Graph (DAG) directed from children to parents. By processing nodes with zero remaining children first, every child node guarantees that its full subtree size is computed and finalized before it contributes to its parent's subtree size.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Building adjacency list: $\mathcal{O}(n)$
  - DFS stack traversal: Each node is pushed/popped a constant number of times ($\mathcal{O}(n)$)
  - Bottom-up leaf-to-root traversal: Each node enters and leaves the queue once ($\mathcal{O}(n)$)
  - Total time complexity is strictly linear, $\mathcal{O}(n)$.
- **Space Complexity**: $\mathcal{O}(n)$
  - Adjacency list `g`: $\mathcal{O}(n)$ space
  - Arrays `q`, `b`, `k`, `v`, `a`, `d`: $\mathcal{O}(n)$ space each
  - Ancestor lookup array `l`: $\mathcal{O}(26) = \mathcal{O}(1)$ space
  - Total space complexity is $\mathcal{O}(n)$.

### 🧠 DSA Pattern

- **Tree DFS with Backtracking** (for path character state tracking)
- **Topological Sort / Leaf-to-Root Kahn's Algorithm** (for non-recursive bottom-up tree DP)

### ⚠️ Common Mistakes

1. **Variable Re-use Confusion**: The array `k` is used as both an explicit LIFO stack (for DFS) and a FIFO queue (for topological ordering). Confusing the pointers (`t` vs `h` and `r`) could lead to wrong iteration orders.
2. **Forgetting to Backtrack Character Map**: Failing to restore `l[c] = b[u]` upon exiting node `u` would result in nodes inheriting ancestors from sibling branches rather than direct ancestors.
3. **Infinite Loop in Dynamic Parent Redirection**: If parent edges were updated during DFS without carefully handling the top-down processing, or if subtree calculation relied on original parent-child relationships instead of `q`, sizes would be incorrect.

### 🚀 Optimization Notes

- **Optimal Time and Space**: The solution runs in $\mathcal{O}(n)$ time and $\mathcal{O}(n)$ auxiliary space, which is optimal.
- **Non-Recursive Implementation**: Reusing array `k` as both stack and queue avoids recursion limits and system stack overflow overhead in Java for large trees ($N = 10^5$).
- **Minor Variable Naming / Readability Concern**: The code uses single-letter variable names (`q`, `l`, `b`, `k`, `v`, `t`, `a`, `d`, `h`, `r`), which makes code revision slightly harder to read without referring back to their context. Using descriptive names like `newParent`, `charToNode`, `backupCharNode`, and `deg` would improve maintainability.
