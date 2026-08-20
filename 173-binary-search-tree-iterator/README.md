<h2><a href="https://leetcode.com/problems/binary-search-tree-iterator">Binary Search Tree Iterator</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Implement the <code>BSTIterator</code> class that represents an iterator over the <strong><a href="https://en.wikipedia.org/wiki/Tree_traversal#In-order_(LNR)" target="_blank">in-order traversal</a></strong> of a binary search tree (BST):</p>

<ul>
	<li><code>BSTIterator(TreeNode root)</code> Initializes an object of the <code>BSTIterator</code> class. The <code>root</code> of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.</li>
	<li><code>boolean hasNext()</code> Returns <code>true</code> if there exists a number in the traversal to the right of the pointer, otherwise returns <code>false</code>.</li>
	<li><code>int next()</code> Moves the pointer to the right, then returns the number at the pointer.</li>
</ul>

<p>Notice that by initializing the pointer to a non-existent smallest number, the first call to <code>next()</code> will return the smallest element in the BST.</p>

<p>You may assume that <code>next()</code> calls will always be valid. That is, there will be at least a next number in the in-order traversal when <code>next()</code> is called.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2018/12/25/bst-tree.png" style="width: 189px; height: 178px;" />
<pre>
<strong>Input</strong>
[&quot;BSTIterator&quot;, &quot;next&quot;, &quot;next&quot;, &quot;hasNext&quot;, &quot;next&quot;, &quot;hasNext&quot;, &quot;next&quot;, &quot;hasNext&quot;, &quot;next&quot;, &quot;hasNext&quot;]
[[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
<strong>Output</strong>
[null, 3, 7, true, 9, true, 15, true, 20, false]

<strong>Explanation</strong>
BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
bSTIterator.next();    // return 3
bSTIterator.next();    // return 7
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 9
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 15
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 20
bSTIterator.hasNext(); // return False
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[1, 10<sup>5</sup>]</code>.</li>
	<li><code>0 &lt;= Node.val &lt;= 10<sup>6</sup></code></li>
	<li>At most <code>10<sup>5</sup></code> calls will be made to <code>hasNext</code>, and <code>next</code>.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong></p>

<ul>
	<li>Could you implement <code>next()</code> and <code>hasNext()</code> to run in average <code>O(1)</code> time and use&nbsp;<code>O(h)</code> memory, where <code>h</code> is the height of the tree?</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

An in-order traversal of a Binary Search Tree (BST) visits nodes in ascending order (Left Subtree $\rightarrow$ Current Node $\rightarrow$ Right Subtree). Instead of flattening the entire tree into a list ahead of time, this solution performs a **lazy in-order traversal** using a stack.

By pushing a node and recursively following its left child pointers all the way down to a leaf, the stack guarantees that the smallest unvisited element in the tree is always sitting at the top of the stack.

---

### 🔍 Approach

1. **Stack Initialization (`st`)**:
   - An explicit stack `Stack<TreeNode> st` is used to simulate the call stack of an iterative in-order traversal.

2. **Helper Method (`leftroot`)**:
   - Takes a `TreeNode root` and pushes it onto `st`.
   - Continues moving to `root.left`, pushing every left descendant onto `st` until reaching `null`.
   - This ensures the top of `st` holds the left-most node reachable from `root`.

3. **Constructor (`BSTIterator`)**:
   - Initializes the stack `st`.
   - Calls `leftroot(root)` if `root` is not `null`, preparing the stack so the smallest element in the entire BST is at the top.

4. **`next()`**:
   - Pops the top node from `st` (which is the next smallest element in sequence).
   - Local variable `TreeNode root` holds this popped node.
   - If `root` has a right child (`root.right != null`), calls `leftroot(root.right)` to push that right child and its left-most branch onto `st`.
   - Returns `root.val`.

5. **`hasNext()`**:
   - Checks if `!st.isEmpty()`. If nodes remain in `st`, there are still elements left in the traversal.

---

### 🧩 Algorithm

- **Data Structure Invariant**: The top element of `st` is always the next node in the in-order sequence.
- **Controlled In-Order Traversal Steps**:
  1. Initialize stack by processing the left spine from the root node.
  2. To get `next()`, pop node $N$ from stack.
  3. If $N$ has a right subtree, process the left spine of $N.\text{right}$ by pushing all its left descendants into the stack.
  4. Return $N.\text{val}$.

---

### ✅ Why This Works

- **Correct Traversal Order**: For any node $N$, all nodes in its left subtree are processed before $N$ because they were pushed on top of $N$ during `leftroot`.
- **Transition to Right Subtree**: Once $N$ is popped, its left subtree is completely processed. Before moving to any higher ancestor on the stack, `leftroot(N.right)` ensures that all nodes in $N$'s right subtree are processed in order.
- **Lazy Evaluation**: Tree nodes are only pushed onto the stack as needed, satisfying the requirement to yield elements one by one without traversing the entire tree upfront.

---

### ⏱️ Complexity

- **Time Complexity**:
  - `hasNext()`: $\mathcal{O}(1)$ — purely checks if the stack is empty.
  - `next()`: Amortized $\mathcal{O}(1)$ — although `leftroot` contains a `while` loop, each node in the BST is pushed onto the stack exactly once and popped exactly once throughout the entire iteration over all $N$ nodes. Thus, $N$ calls to `next()` take $\mathcal{O}(N)$ total time.
  - Constructor: $\mathcal{O}(h)$ — traverses down the left spine of the tree, where $h$ is the height of the tree.

- **Space Complexity**: $\mathcal{O}(h)$ — where $h$ is the height of the tree. The stack stores at most the nodes along a single path from root to leaf at any given time.

---

### 🧠 DSA Pattern

- **Stack**
- **Tree Traversal (Lazy / Iterative In-Order Traversal)**
- **Binary Search Tree**

---

### ⚠️ Common Mistakes

1. **Confusing local variable shadowing**: Inside `next()`, declaring `TreeNode root = st.pop()` reuses the variable name `root`. While it works correctly here because it is local to `next()`, it can cause confusion when reading the code since `root` was also the parameter name in the constructor and `leftroot`.
2. **Not checking right child**: Forgetting to process `root.right` with `leftroot` inside `next()` would skip the entire right subtrees of popped nodes.
3. **Empty stack access**: Calling `next()` when `hasNext()` is `false` will lead to an `EmptyStackException` on `st.pop()`.

---

### 🚀 Optimization Notes

- **Optimal Time & Space**: This implementation already achieves the optimal amortized $\mathcal{O}(1)$ time complexity per call and $\mathcal{O}(h)$ auxiliary space complexity.
- **Data Structure Choice**: In Java, `java.util.Stack` inherits from `Vector` and carries synchronization overhead. Replacing `Stack<TreeNode>` with `Deque<TreeNode> st = new ArrayDeque<>()` (using `push()` and `pop()`) is a standard minor optimization for better performance in Java, though the underlying algorithm remains identical.
