<h2><a href="https://leetcode.com/problems/path-sum">Path Sum</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given the <code>root</code> of a binary tree and an integer <code>targetSum</code>, return <code>true</code> if the tree has a <strong>root-to-leaf</strong> path such that adding up all the values along the path equals <code>targetSum</code>.</p>

<p>A <strong>leaf</strong> is a node with no children.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/01/18/pathsum1.jpg" style="width: 500px; height: 356px;" />
<pre>
<strong>Input:</strong> root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
<strong>Output:</strong> true
<strong>Explanation:</strong> The root-to-leaf path with the target sum is shown.
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/01/18/pathsum2.jpg" />
<pre>
<strong>Input:</strong> root = [1,2,3], targetSum = 5
<strong>Output:</strong> false
<strong>Explanation:</strong> There are two root-to-leaf paths in the tree:
(1 --&gt; 2): The sum is 3.
(1 --&gt; 3): The sum is 4.
There is no root-to-leaf path with sum = 5.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> root = [], targetSum = 0
<strong>Output:</strong> false
<strong>Explanation:</strong> Since the tree is empty, there are no root-to-leaf paths.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[0, 5000]</code>.</li>
	<li><code>-1000 &lt;= Node.val &lt;= 1000</code></li>
	<li><code>-1000 &lt;= targetSum &lt;= 1000</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of your implementation is **top-down subtraction during recursion**. 

Instead of maintaining an accumulating sum as you traverse down from the root, you subtract the current node's value (`r.val`) from the target sum (`s`). As you move down the tree, `s` represents the *remaining path sum required* from the current node to a leaf. When you reach a leaf node (a node with no left or right children), you simply check if the remaining required sum equals the leaf node's value.

### 🔍 Approach

1. **Base Case 1 (Null Node / Empty Tree):**
   ```java
   if (r == null) return false;
   ```
   If the tree is empty or you step beyond a node that has only one child, `r` is `null`. A `null` node cannot form a path, so it returns `false`.

2. **Base Case 2 (Leaf Node Check):**
   ```java
   if (r.left == null && r.right == null) return s == r.val;
   ```
   If the current node `r` has no left child and no right child, it is a **leaf node**. You check whether the remaining target sum `s` is equal to `r.val`. If it matches, a valid root-to-leaf path is found.

3. **Recursive Step:**
   ```java
   return hasPathSum(r.left, s - r.val) || hasPathSum(r.right, s - r.val);
   ```
   If `r` is an internal (non-leaf) node, you subtract `r.val` from `s` and pass `s - r.val` to both the left sub-tree (`r.left`) and the right sub-tree (`r.right`). 
   - The logical OR (`||`) ensures that if *either* the left path or the right path returns `true`, the overall result is `true`.
   - Thanks to short-circuit evaluation in Java, if `hasPathSum(r.left, s - r.val)` returns `true`, the right subtree will not even be traversed.

### 🧩 Algorithm

This implementation follows a recursive **Depth-First Search (DFS)** algorithm:

- **State Representation:** `hasPathSum(r, s)` represents whether there exists a leaf node under subtree `r` such that the path sum from `r` to that leaf equals `s`.
- **Recurrence Relation:**
  $$\text{hasPathSum}(r, s) = 
  \begin{cases} 
  \text{false} & \text{if } r \text{ is null} \\
  (s == r.\text{val}) & \text{if } r \text{ is a leaf node} \\
  \text{hasPathSum}(r.\text{left}, s - r.\text{val}) \lor \text{hasPathSum}(r.\text{right}, s - r.\text{val}) & \text{otherwise}
  \end{cases}$$

### ✅ Why This Works

- **Correct Leaf Identification:** A path must extend strictly from the root to a leaf node. By explicitly checking `r.left == null && r.right == null`, your code guarantees that success (`s == r.val`) is only evaluated at true leaves, avoiding false positives on internal nodes with only one child.
- **Mathematical Correctness:** Subtracting values at each step preserves the invariant: at any node `r`, the variable `s` represents `targetSum - (sum of node values from root down to parent of r)`.
- **Short-circuiting:** The `||` operator correctly propagates `true` up the call stack as soon as any valid leaf path is discovered.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the total number of nodes in the binary tree. In the worst case (e.g., when no valid path exists or the path is in the rightmost leaf), every node in the tree is visited once.
- **Space Complexity:** $\mathcal{O}(H)$, where $H$ is the height of the tree. This space is consumed by the implicit call stack during recursion.
  - In a balanced tree, $H = \mathcal{O}(\log N)$.
  - In a skewed tree (worst case), $H = \mathcal{O}(N)$.

### 🧠 DSA Pattern

- **Tree DFS (Depth-First Search)**
- **Recursion**

### ⚠️ Common Mistakes

1. **Treating single-child nodes as leaves:** A common bug in similar problems is checking `r == null` and returning `s == 0`. That mistake counts a node with only one child as a valid ending point for a path. Your code correctly avoids this by explicitly checking `r.left == null && r.right == null`.
2. **Forgetting negative numbers:** Node values and `targetSum` can be negative. Because your code does exact equality checks rather than premature pruning (e.g., stopping when `s < 0`), it handles negative node values and target sums correctly.

### 🚀 Optimization Notes

- Your solution is already **optimal** in terms of both time ($\mathcal{O}(N)$) and space ($\mathcal{O}(H)$) complexities.
- The use of short-circuit evaluation (`||`) gives a small practical speedup by skipping the right subtree whenever a valid path is found in the left subtree.
