<h2><a href="https://leetcode.com/problems/count-nodes-equal-to-average-of-subtree">Count Nodes Equal to Average of Subtree</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given the <code>root</code> of a binary tree, return <em>the number of nodes where the value of the node is equal to the <strong>average</strong> of the values in its <strong>subtree</strong></em>.</p>

<p><strong>Note:</strong></p>

<ul>
	<li>The <strong>average</strong> of <code>n</code> elements is the <strong>sum</strong> of the <code>n</code> elements divided by <code>n</code> and <strong>rounded down</strong> to the nearest integer.</li>
	<li>A <strong>subtree</strong> of <code>root</code> is a tree consisting of <code>root</code> and all of its descendants.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img src="https://assets.leetcode.com/uploads/2022/03/15/image-20220315203925-1.png" style="width: 300px; height: 212px;" />
<pre>
<strong>Input:</strong> root = [4,8,5,0,1,null,6]
<strong>Output:</strong> 5
<strong>Explanation:</strong> 
For the node with value 4: The average of its subtree is (4 + 8 + 5 + 0 + 1 + 6) / 6 = 24 / 6 = 4.
For the node with value 5: The average of its subtree is (5 + 6) / 2 = 11 / 2 = 5.
For the node with value 0: The average of its subtree is 0 / 1 = 0.
For the node with value 1: The average of its subtree is 1 / 1 = 1.
For the node with value 6: The average of its subtree is 6 / 1 = 6.
</pre>

<p><strong class="example">Example 2:</strong></p>
<img src="https://assets.leetcode.com/uploads/2022/03/26/image-20220326133920-1.png" style="width: 80px; height: 76px;" />
<pre>
<strong>Input:</strong> root = [1]
<strong>Output:</strong> 1
<strong>Explanation:</strong> For the node with value 1: The average of its subtree is 1 / 1 = 1.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[1, 1000]</code>.</li>
	<li><code>0 &lt;= Node.val &lt;= 1000</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To determine if a node's value equals the average of its subtree, we need two pieces of information from both its left and right subtrees:
1. The **sum** of all node values in the subtree.
2. The total **count** of nodes in the subtree.

Because a subtree's average depends on the results of its child subtrees, a **bottom-up post-order traversal** is ideal. By obtaining the sum and node count from the left and right subtrees first, the current node can easily calculate its own total sum, total count, and subtree average in $O(1)$ time.

### 🔍 Approach

1. **State Tracking**:
   - `ans`: An instance variable initialized to `0` that keeps track of the total number of nodes whose value equals their subtree average.

2. **DFS Traversal (`dfs(TreeNode node)`)**:
   - The recursive function returns a 2-element integer array `new int[]{sum, count}` representing the aggregate sum and total count of nodes for the subtree rooted at `node`.
   - **Base Case**: If `node == null`, return `new int[]{0, 0}` (0 sum, 0 nodes).
   - **Post-Order Recursive Steps**:
     - Call `dfs(node.left)` to get the pair `left` containing `{left_sum, left_count}`.
     - Call `dfs(node.right)` to get the pair `right` containing `{right_sum, right_count}`.
   - **Combine Subtree Results**:
     - `sum = left[0] + right[0] + node.val`
     - `cnt = left[1] + right[1] + 1`
   - **Check Subtree Condition**:
     - Calculate the integer average: `sum / cnt`. In Java, integer division automatically truncates (rounds down), matching the problem requirement.
     - If `sum / cnt == node.val`, increment `ans`.
   - **Return Pair**: Return `new int[]{sum, cnt}` so the parent node can aggregate its own subtree stats.

3. **Execution Flow**:
   - `averageOfSubtree` calls `dfs(root)` and returns `ans`.

### 🧩 Algorithm

- **Traversal Pattern**: Post-order Tree Depth-First Search (DFS)
- **State passing**:
  - Subproblem state returned: `[sum, count]`
  - Aggregation logic:
    $$\text{sum}_{\text{current}} = \text{sum}_{\text{left}} + \text{sum}_{\text{right}} + \text{val}_{\text{current}}$$
    $$\text{count}_{\text{current}} = \text{count}_{\text{left}} + \text{count}_{\text{right}} + 1$$
  - Condition checked:
    $$\lfloor \frac{\text{sum}_{\text{current}}}{\text{count}_{\text{current}}} \rfloor == \text{val}_{\text{current}}$$

### ✅ Why This Works

- **Post-Order Guarantee**: Processing children before the root guarantees that both the subtree sum and count are accurate when evaluating the node itself.
- **Integer Division**: The problem specifies that the average should be rounded down to the nearest integer. Java's integer division (`/`) truncates the fractional part towards zero, which accurately implements floor division for non-negative subtree sums.
- **Base Case Correctness**: Returning `{0, 0}` for `null` nodes acts as an identity element for both addition operations without distorting sums or node counts.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the total number of nodes in the binary tree. Every node is visited exactly once during the DFS traversal.
- **Space Complexity**: $\mathcal{O}(H)$, where $H$ is the height of the binary tree.
  - In the worst case (a skewed tree), $H = N$, leading to $\mathcal{O}(N)$ recursion stack depth.
  - In the best case (a balanced tree), $H = \log N$, leading to $\mathcal{O}(\log N)$ recursion stack depth.

### 🧠 DSA Pattern

- **DFS / Post-Order Tree Traversal**: Gathering information from child nodes bottom-up to compute a condition at the current node.

### ⚠️ Common Mistakes

1. **Index Confusion**: Mixing up array indices (`[0]` for sum and `[1]` for count) inside the recursive function.
2. **Division by Zero**: Forgetting to add `+ 1` to `cnt` for the current node, which could cause a division by zero if `cnt` were 0.
3. **Floating Point Precision**: Using `double` division unnecessarily, which could introduce floating-point inaccuracies or require manual rounding (`Math.floor`) when integer division already handles rounding down correctly.

### 🚀 Optimization Notes

- **Time Efficiency**: The solution is already optimal in terms of time complexity ($\mathcal{O}(N)$).
- **Object Allocation**: Creating a `new int[]{sum, cnt}` at every non-null node allocates $\mathcal{O}(N)$ small short-lived arrays. Given $N \le 1000$, this garbage collection overhead is negligible.
