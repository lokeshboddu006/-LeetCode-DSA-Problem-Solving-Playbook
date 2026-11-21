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

The problem requires us to modify a tree by changing each node $x$'s parent to its **closest ancestor** that shares the same character `s[x]`. If no such ancestor exists, node $x$ keeps its original parent. Finally, we need to calculate the subtree size of every node in the **newly formed tree**.

The core observations in your solution are:
1. **Ancestor Lookup via DFS Path State**: As we traverse down the tree from the root, we maintain the path from the root to the current node. By keeping track of the last node seen for each of the 26 lowercase characters (`last[26]`), we can instantly find the closest ancestor of node $x$ with character `s[x]` in $\mathcal{O}(1)$ time.
2. **Bottom-Up Subtree Aggregation**: The new parent of node $x$ is determined by the original tree structure. Once every node knows its new parent, we can calculate modified subtree sizes using a **post-order traversal** (bottom-up). By the time we finish processing node $x$, its modified subtree size `size[x]` is fully computed, and we can immediately pass `size[x]` up to its *new parent*.

---

### 🔍 Approach

Your solution executes the following steps:

1. **Graph Construction**:
   - You build an adjacency list `children` where `children.get(u)` stores all direct children of node `u` in the original tree.

2. **State Setup**:
   - `size`: An array initialized to `1` for all nodes (since every node initially counts itself).
   - `above`: An array where `above[x]` stores the closest ancestor of node $x$ with character `s[x]`, or `-1` if no such ancestor exists.
   - `last`: A fixed-size array of length 26 initialized to `-1`, tracking the most recent ancestor node on the current DFS path for each character `'a'` to `'z'`.

3. **Iterative DFS with Bitwise Complement Encoding (`~x`)**:
   - You push root node `0` onto an explicit `stack` (`ArrayDeque`).
   - While the stack is not empty, you pop an element `x`:
     - **Pre-Order Visit (`x >= 0`)**:
       - Identify the character index `c = s.charAt(x) - 'a'`.
       - Record `above[x] = last[c]` (the closest ancestor with matching character).
       - Update `last[c] = x` (current node is now the latest ancestor for character `c`).
       - Push `~x` (bitwise NOT of `x`, which yields a negative number) back onto the stack to act as a marker for the post-order return phase.
       - Push all children of `x` onto the stack for traversal.
     - **Post-Order Visit (`x < 0`)**:
       - Decode the node index back to positive: `x = ~x`.
       - Backtrack the ancestor state: `last[s.charAt(x) - 'a'] = above[x]`.
       - If `x > 0` (it is not the root node), determine its new parent: `targetParent = above[x] >= 0 ? above[x] : parent[x]`.
       - Accumulate the subtree size: `size[targetParent] += size[x]`.

4. **Return Result**:
   - Return the `size` array containing final subtree sizes for all nodes.

---

### 🧩 Algorithm

1. **Tree Construction**:
   $$\text{children}[\text{parent}[i]].\text{add}(i) \quad \forall \, i \in [1, n-1]$$

2. **DFS Pre-order Entry** (for node $x$):
   $$c = s[x] - \text{'a'}$$
   $$\text{above}[x] = \text{last}[c]$$
   $$\text{last}[c] = x$$

3. **DFS Post-order Exit** (for node $x$):
   $$\text{last}[s[x] - \text{'a'}] = \text{above}[x]$$
   $$\text{if } x > 0:$$
   $$\text{new\_parent} = 
   \begin{cases} 
   \text{above}[x] & \text{if } \text{above}[x] \ge 0 \\ 
   \text{parent}[x] & \text{otherwise} 
   \end{cases}$$
   $$\text{size}[\text{new\_parent}] += \text{size}[x]$$

---

### ✅ Why This Works

- **Simultaneous Tree Modification**: The problem states changes occur simultaneously. This means the target parent of node $x$ depends strictly on ancestors in the *original* tree. By recording `above[x]` during the pre-order DFS on the original tree, you correctly capture the new parent for every node before any tree structure is altered.
- **Correct Post-Order Aggregation**: Post-order processing ensures that when node $x$ is processed on exit, all nodes in $x$'s subtree in the new tree have already added their sizes to `size[x]`. Adding `size[x]` to its `new_parent` correctly propagates the total subtree size upward.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Building the adjacency list takes $\mathcal{O}(n)$ time.
  - Every node is pushed to and popped from the stack exactly twice (once for pre-order entry and once for post-order exit).
  - Array lookups and updates (`last`, `above`, `size`) are all $\mathcal{O}(1)$ operations per node.
  - Total time complexity is strictly linear with respect to the number of nodes $n$.

- **Space Complexity**: $\mathcal{O}(n)$
  - `children` adjacency list takes $\mathcal{O}(n)$ space.
  - `size` and `above` arrays take $\mathcal{O}(n)$ space.
  - `stack` holds at most $\mathcal{O}(n)$ elements.
  - `last` array takes $\mathcal{O}(26) = \mathcal{O}(1)$ space.

---

### 🧠 DSA Pattern

- **Tree Traversal (Iterative DFS)**: Simulated recursion using a stack with bitwise complement `~x` to perform post-order processing.
- **Path State / Backtracking**: Using the `last[26]` array to track active ancestors along the current root-to-leaf path.
- **Tree Dynamic Programming (Bottom-Up Aggregation)**: Accumulating subtree sizes from leaf nodes to parents in post-order traversal.

---

### ⚠️ Common Mistakes

1. **Misunderstanding Bitwise Complement (`~x`)**:
   - Bitwise NOT maps non-negative integer `x` to `-x - 1`. For instance, `~0` is `-1`, `~1` is `-2`.
   - Applying `~` a second time restores the original value: `~(~x) == x`.
   - Re-entering the popped node without checking `x >= 0` would cause infinite stack loops.

2. **Forgetting State Backtracking**:
   - When exiting a node $x$ in post-order, `last[c]` must be restored to `above[x]`. Omitting this line would leave node $x$ registered in `last[c]` even when exploring sibling branches where $x$ is not an ancestor.

3. **Accumulating Sizes Pre-order Instead of Post-order**:
   - If `size[targetParent] += size[x]` were executed during entry (pre-order), `size[x]` would only equal `1` because child subtrees would not have been computed yet.

---

### 🚀 Optimization Notes

- **Avoiding StackOverflowError**: By using an explicit `ArrayDeque<Integer>` stack instead of system recursion, your solution avoids JVM stack overflow errors for deep trees up to $n = 10^5$.
- **Bitwise Trick Efficiency**: Encoding entry vs. exit states with bitwise complement `~x` avoids needing a custom class or boolean pairs inside the stack, keeping memory footprint low and execution fast.
- **Direct Character Lookup**: Array `last` of size 26 provides constant-time $\mathcal{O}(1)$ lookup and state restoration for character matching along the tree path.
