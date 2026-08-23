<h2><a href="https://leetcode.com/problems/symmetric-tree">Symmetric Tree</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given the <code>root</code> of a binary tree, <em>check whether it is a mirror of itself</em> (i.e., symmetric around its center).</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/19/symtree1.jpg" style="width: 354px; height: 291px;" />
<pre>
<strong>Input:</strong> root = [1,2,2,3,4,4,3]
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/19/symtree2.jpg" style="width: 308px; height: 258px;" />
<pre>
<strong>Input:</strong> root = [1,2,2,null,3,null,3]
<strong>Output:</strong> false
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[1, 1000]</code>.</li>
	<li><code>-100 &lt;= Node.val &lt;= 100</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow up:</strong> Could you solve it both recursively and iteratively?

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of this solution is to check for **mirror symmetry** by traversing two subtrees simultaneously. For a binary tree to be symmetric around its center:
1. The left subtree and the right subtree of the root must be mirror images of each other.
2. Two subtrees are mirror images if their root values are equal, and the **left** subtree of the first tree is a mirror image of the **right** subtree of the second tree (and vice versa).

Your implementation directly converts this concept into a recursive helper function (`isMirror`) that compares two nodes at a time.

---

### 🔍 Approach

1. **Main Function (`isSymmetric`)**:
   - Handles the base case: if `root` is `null`, the tree is symmetric, so it returns `true`.
   - Otherwise, it delegates the mirror check to the helper function by passing the left child (`root.left`) and right child (`root.right`).

2. **Helper Function (`isMirror(TreeNode t1, TreeNode t2)`)**:
   - **Null Check**: If either `t1` or `t2` is `null`, it evaluates `t1 == t2`.
     - If both are `null`, they match (returns `true`).
     - If only one is `null`, they do not match (returns `false`).
   - **Value Comparison**: If `t1.val != t2.val`, the symmetry is broken, so it immediately returns `false`.
   - **Recursive Call**: To verify mirror symmetry for the children:
     - It pairs the outer nodes: `isMirror(t1.left, t2.right)`.
     - It pairs the inner nodes: `isMirror(t1.right, t2.left)`.
     - Both pairs must be symmetric, so it combines these results using the logical AND (`&&`) operator.

---

### 🧩 Algorithm

The solution relies on the following recursive definition:

- **Base Cases**:
  - `isMirror(null, null) = true`
  - `isMirror(t1, null) = false` (where `t1 != null`)
  - `isMirror(null, t2) = false` (where `t2 != null`)
  - `isMirror(t1, t2) = false` if `t1.val != t2.val`

- **Recursive Step**:
  - `isMirror(t1, t2) = isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left)`

---

### ✅ Why This Works

- **Structural Symmetry**: Checking `t1 == t2` when at least one node is `null` ensures that both subtrees have identical structural layouts mirrored across the center axis.
- **Value Matching**: Checking `t1.val != t2.val` guarantees that mirrored nodes hold identical values.
- **Cross-Over Recursion**: By pairing `t1.left` with `t2.right` and `t1.right` with `t2.left`, the traversal enforces reflection across the vertical center line rather than direct equivalence.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the total number of nodes in the binary tree. In the worst-case scenario (e.g., a symmetric or nearly symmetric tree), every node is visited at most once.
- **Space Complexity**: $\mathcal{O}(H)$, where $H$ is the height of the tree. This space is used by the implicit call stack during recursion.
  - In a balanced binary tree, $H = \mathcal{O}(\log N)$.
  - In a completely skewed tree, $H = \mathcal{O}(N)$.

---

### 🧠 DSA Pattern

- **Recursion / Tree DFS (Depth-First Search)**: The algorithm explores subtree branches recursively to compare structure and node values bottom-up or top-down simultaneously across two subtrees.

---

### ⚠️ Common Mistakes

1. **Comparing Same-Side Subtrees**: Beginners often mistakenly call `isMirror(t1.left, t2.left)` and `isMirror(t1.right, t2.right)`. That checks whether two trees are *identical*, not whether they are *mirrored*.
2. **Null Pointer Exception**: Accessing `t1.val` or `t2.val` before ensuring both `t1` and `t2` are non-null. Your concise condition `if (t1 == null || t2 == null) return t1 == t2;` safely guards against this.

---

### 🚀 Optimization Notes

- This recursive approach is already **optimal** in both time ($\mathcal{O}(N)$) and space ($\mathcal{O}(H)$).
- Java's short-circuit evaluation in `&&` helps optimize runtime: if the first cross-over check `isMirror(t1.left, t2.right)` returns `false`, the second check `isMirror(t1.right, t2.left)` is skipped immediately.
