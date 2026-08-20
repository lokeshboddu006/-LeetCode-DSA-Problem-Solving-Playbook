<h2><a href="https://leetcode.com/problems/two-sum-iv-input-is-a-bst">Two Sum IV - Input is a BST</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given the <code>root</code> of a binary search tree and an integer <code>k</code>, return <code>true</code> <em>if there exist two elements in the BST such that their sum is equal to</em> <code>k</code>, <em>or</em> <code>false</code> <em>otherwise</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/21/sum_tree_1.jpg" style="width: 400px; height: 229px;" />
<pre>
<strong>Input:</strong> root = [5,3,6,2,4,null,7], k = 9
<strong>Output:</strong> true
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/21/sum_tree_2.jpg" style="width: 400px; height: 229px;" />
<pre>
<strong>Input:</strong> root = [5,3,6,2,4,null,7], k = 28
<strong>Output:</strong> false
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[1, 10<sup>4</sup>]</code>.</li>
	<li><code>-10<sup>4</sup> &lt;= Node.val &lt;= 10<sup>4</sup></code></li>
	<li><code>root</code> is guaranteed to be a <strong>valid</strong> binary search tree.</li>
	<li><code>-10<sup>5</sup> &lt;= k &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code solves the problem by adapting the standard **Two Pointers** technique (typically used on sorted arrays) directly to a Binary Search Tree (BST). 

Since an **in-order traversal** of a BST yields values in ascending (sorted) order and a **reverse in-order traversal** yields values in descending order, the code builds two custom BST iterators:
1. `Inorder`: yields elements starting from the smallest value upwards.
2. `RevInorder`: yields elements starting from the largest value downwards.

By placing `p1` at the smallest element and `p2` at the largest element, you can move pointers towards each other based on their sum relative to `k` without flattening the tree into an array first.

---

### 🔍 Approach

1. **Custom Iterators (`Inorder` and `RevInorder`)**:
   - **`Inorder` (Ascending Iterator)**: Uses an explicit `Stack<TreeNode>` to simulate standard controlled in-order traversal. `pushAll(node)` pushes the current node and all its left descendents onto the stack. `getNext()` pops the top node, assigns its value to `val`, pushes all left descendents of `node.right`, and returns `this`.
   - **`RevInorder` (Descending Iterator)**: Operates symmetrically to `Inorder`. `pushAll(node)` pushes the node and all its right descendents onto the stack. `getnext()` pops the top node, assigns its value to `val`, pushes all right descendents of `node.left`, and returns `this`.

2. **Initialization**:
   - `p1 = new Inorder(root)` sets `p1.val` to the minimum value in the BST.
   - `p2 = new RevInorder(root)` sets `p2.val` to the maximum value in the BST.

3. **Two-Pointer Loop**:
   - The loop runs while `p1.val < p2.val` (ensuring two distinct nodes are being compared).
   - If `p1.val + p2.val == k`, a valid pair is found, so it returns `true`.
   - If `p1.val + p2.val > k`, the sum is too large, so `p2` advances to the next smaller element via `p2 = p2.getnext()`.
   - If `p1.val + p2.val < k`, the sum is too small, so `p1` advances to the next larger element via `p1 = p1.getNext()`.

4. **Termination**:
   - If the pointers cross or meet (`p1.val >= p2.val`), no pair sums to `k`, so it returns `false`.

---

### 🧩 Algorithm

1. **BST Iterator Traversal State**:
   - Left-to-right iterator invariant: Stack top always holds the smallest unvisited node.
   - Right-to-left iterator invariant: Stack top always holds the largest unvisited node.

2. **Two-Pointer Strategy**:
   $$\text{sum} = \text{p1.val} + \text{p2.val}$$
   - Case 1: $\text{sum} == k \implies \text{return true}$
   - Case 2: $\text{sum} > k \implies \text{p2 = p2.getnext()}$
   - Case 3: $\text{sum} < k \implies \text{p1 = p1.getNext()}$

---

### ✅ Why This Works

- **BST Traversal Invariant**: The binary search tree property guarantees that the iterative `Inorder` traversal generates elements in strictly increasing order, and `RevInorder` generates elements in strictly decreasing order.
- **Two Pointers Correctness**: 
  - If `p1.val + p2.val > k`, `p2.val` cannot be paired with `p1.val` or any larger value to equal `k`. Hence, `p2` can safely step down.
  - If `p1.val + p2.val < k`, `p1.val` cannot be paired with `p2.val` or any smaller value to equal `k`. Hence, `p1` can safely step up.
- Because search space is reduced monotonically without missing valid pairs, the result is guaranteed to be correct.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$  
  Every node in the BST is pushed to and popped from each iterator's stack at most once during the traversal. Thus, the total time across all `getNext()` / `getnext()` calls is bounded by $\mathcal{O}(N)$.

- **Space Complexity:** $\mathcal{O}(H)$  
  Where $H$ is the height of the BST. The stacks in `Inorder` and `RevInorder` hold at most $H$ elements at any given time (representing a path from root to leaf). In a balanced tree, space is $\mathcal{O}(\log N)$, and in a skewed tree, it is $\mathcal{O}(N)$.

---

### 🧠 DSA Pattern

- **Two Pointers**
- **BST Iterator / Controlled Tree Traversal**
- **Monotonic Stack (explicit call stack simulation)**

---

### ⚠️ Common Mistakes

1. **Naming Inconsistency**: `p1.getNext()` uses camelCase, whereas `p2.getnext()` uses lowercase `getnext()`. Missing this distinction during maintenance can lead to compilation errors.
2. **Redundant Assignment**: Methods `getNext()` and `getnext()` return `this` and mutate internal fields in-place. Writing `p2 = p2.getnext()` is redundant; `p2.getnext()` alone performs the state update.
3. **Exhaustion Safety**: Calling `getNext()` or `getnext()` on an exhausted iterator stack does not throw an error because of the `if (!stack.isEmpty())` guard, but `val` will retain its previous value. The loop condition `p1.val < p2.val` prevents running past iterator exhaustion in a standard BST search.

---

### 🚀 Optimization Notes

- **Optimal Memory Usage**: This solution is already optimal in terms of auxiliary space complexity ($\mathcal{O}(H)$ stack space rather than $\mathcal{O}(N)$ space required to flatten the whole tree into an array or hash set).
- **In-place Mutation Cleanup**: Instead of returning `this` and re-assigning (`p1 = p1.getNext()`), changing `getNext()` to `void` and simply calling `p1.getNext()` avoids unnecessary reference reassignments.
- **Method Naming Uniformity**: Standardizing method signatures to `getNext()` for both iterator classes enhances readability.
