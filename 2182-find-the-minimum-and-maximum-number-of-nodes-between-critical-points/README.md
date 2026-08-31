<h2><a href="https://leetcode.com/problems/find-the-minimum-and-maximum-number-of-nodes-between-critical-points">Find the Minimum and Maximum Number of Nodes Between Critical Points</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>A <strong>critical point</strong> in a linked list is defined as <strong>either</strong> a <strong>local maxima</strong> or a <strong>local minima</strong>.</p>

<p>A node is a <strong>local maxima</strong> if the current node has a value <strong>strictly greater</strong> than the previous node and the next node.</p>

<p>A node is a <strong>local minima</strong> if the current node has a value <strong>strictly smaller</strong> than the previous node and the next node.</p>

<p>Note that a node can only be a local maxima/minima if there exists <strong>both</strong> a previous node and a next node.</p>

<p>Given a linked list <code>head</code>, return <em>an array of length 2 containing </em><code>[minDistance, maxDistance]</code><em> where </em><code>minDistance</code><em> is the <strong>minimum distance</strong> between <strong>any&nbsp;two distinct</strong> critical points and </em><code>maxDistance</code><em> is the <strong>maximum distance</strong> between <strong>any&nbsp;two distinct</strong> critical points. If there are <strong>fewer</strong> than two critical points, return </em><code>[-1, -1]</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/10/13/a1.png" style="width: 148px; height: 55px;" />
<pre>
<strong>Input:</strong> head = [3,1]
<strong>Output:</strong> [-1,-1]
<strong>Explanation:</strong> There are no critical points in [3,1].
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/10/13/a2.png" style="width: 624px; height: 46px;" />
<pre>
<strong>Input:</strong> head = [5,3,1,2,5,1,2]
<strong>Output:</strong> [1,3]
<strong>Explanation:</strong> There are three critical points:
- [5,3,<strong><u>1</u></strong>,2,5,1,2]: The third node is a local minima because 1 is less than 3 and 2.
- [5,3,1,2,<u><strong>5</strong></u>,1,2]: The fifth node is a local maxima because 5 is greater than 2 and 1.
- [5,3,1,2,5,<u><strong>1</strong></u>,2]: The sixth node is a local minima because 1 is less than 5 and 2.
The minimum distance is between the fifth and the sixth node. minDistance = 6 - 5 = 1.
The maximum distance is between the third and the sixth node. maxDistance = 6 - 3 = 3.
</pre>

<p><strong class="example">Example 3:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/10/14/a5.png" style="width: 624px; height: 39px;" />
<pre>
<strong>Input:</strong> head = [1,3,2,2,3,2,2,2,7]
<strong>Output:</strong> [3,3]
<strong>Explanation:</strong> There are two critical points:
- [1,<u><strong>3</strong></u>,2,2,3,2,2,2,7]: The second node is a local maxima because 3 is greater than 1 and 2.
- [1,3,2,2,<u><strong>3</strong></u>,2,2,2,7]: The fifth node is a local maxima because 3 is greater than 2 and 2.
Both the minimum and maximum distances are between the second and the fifth node.
Thus, minDistance and maxDistance is 5 - 2 = 3.
Note that the last node is not considered a local maxima because it does not have a next node.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the list is in the range <code>[2, 10<sup>5</sup>]</code>.</li>
	<li><code>1 &lt;= Node.val &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code solves the problem by iterating through the linked list once while maintaining a sliding window of three consecutive nodes (`prev`, `curr`, and `curr.next`). 

A node is identified as a **critical point** if its value is strictly greater than both its adjacent neighbors (local maxima) or strictly smaller than both (local minima). 

To calculate distances:
- **Maximum distance** between any two critical points is simply the distance between the **first** critical point ever seen and the **last** critical point seen (`last - first`).
- **Minimum distance** occurs between two **consecutive** critical points. By keeping track of the index of the previously found critical point (`last`), the code updates the overall minimum distance whenever a new critical point is found.

---

### 🔍 Approach

1. **State Initialization**:
   - `first`: Stores the 1-based index of the very first critical point found (initialized to `-1`).
   - `last`: Stores the 1-based index of the most recently found critical point (initialized to `-1`).
   - `count`: Initialized to `0` (unused in the loop).
   - `idx`: Tracks the 0-indexed position of `curr` (starts at `1` since `curr = head.next`).
   - `min`: Tracks the smallest distance between any two adjacent critical points (initialized to `Integer.MAX_VALUE`).
   - Pointers: `prev = head` and `curr = head.next`.

2. **Single-Pass Traversal**:
   - The code iterates using `while (curr.next != null)`, ensuring `curr` always has both a `prev` and a `next` node.
   - For each node `curr`, it checks the critical point condition:
     - Local Maxima: `curr.val > prev.val && curr.val > curr.next.val`
     - Local Minima: `curr.val < prev.val && curr.val < curr.next.val`
   - If a critical point is found at position `idx`:
     - **First critical point**: If `first == -1`, set both `first` and `last` to `idx`.
     - **Subsequent critical points**: Calculate the distance from the previous critical point (`idx - last`), update `min` using `Math.min(min, idx - last)`, and update `last = idx`.
   - Shift `prev` and `curr` forward and increment `idx`.

3. **Result Construction**:
   - If `first == last` (which happens if 0 or only 1 critical point is found), return `[-1, -1]`.
   - Otherwise, return `[min, last - first]`.

---

### 🧩 Algorithm

- **Traversal Strategy**: Single-pass linear scan over the linked list using two pointers (`prev` and `curr`).
- **Critical Point Detection Rule**:
  $$\text{IsCritical}(curr) = (curr.val > prev.val \land curr.val > curr.next.val) \lor (curr.val < prev.val \land curr.val < curr.next.val)$$
- **Distance Maintenance**:
  - Minimum distance transition: 
    $$min = \min(min, idx_{current} - last)$$
  - Maximum distance expression: 
    $$maxDistance = last_{final} - first$$

---

### ✅ Why This Works

- **Valid Boundaries**: By running the loop while `curr.next != null` starting from `curr = head.next`, the code guarantees that `prev` and `curr.next` are non-null. First and last nodes of the linked list are naturally skipped as they cannot be critical points.
- **Correct Min Distance**: The minimum distance between *any* two critical points must be between two *adjacent* critical points. By measuring `idx - last` every time a critical point is encountered, all adjacent distances are checked.
- **Correct Max Distance**: The maximum distance between *any* two critical points is guaranteed to be between the very first critical point and the very last critical point in the list.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$
  The linked list is traversed once from `head.next` to the second-to-last node, where $N$ is the number of nodes in the list. Inside the loop, all operations (comparisons, math, pointer moves) run in $\mathcal{O}(1)$ time.

- **Space Complexity:** $\mathcal{O}(1)$
  Only a few integer variables (`first`, `last`, `count`, `idx`, `min`) and reference pointers (`prev`, `curr`) are used. No additional data structures are allocated.

---

### 🧠 DSA Pattern

- **Two Pointers / Sliding Window**: Using `prev` and `curr` (along with `curr.next`) to examine a 3-node sliding window across a linked list.
- **Single-Pass Tracking**: Updating running global metrics (`first`, `last`, `min`) during traversal.

---

### ⚠️ Common Mistakes

1. **Unused Variable**:
   - The variable `int count = 0;` is declared and initialized at the top but is never used in the solution.
2. **First / Last Boundary Nodes**:
   - Forgetting that the head node and the tail node cannot be critical points because they lack a `prev` or `next` neighbor respectively. The `while(curr.next != null)` condition correctly handles this.
3. **Handling Fewer Than 2 Critical Points**:
   - If 0 critical points are found, `first` and `last` both remain `-1`. If 1 critical point is found, `first` and `last` are equal. Checking `if (first == last)` correctly covers both cases and returns `[-1, -1]`.

---

### 🚀 Optimization Notes

- **Unused Declaration**: The variable `int count = 0;` can be safely removed to clean up unused memory and improve readability.
- **Space & Time Efficiency**: The implementation is already optimal in terms of asymptotic time ($\mathcal{O}(N)$) and space ($\mathcal{O}(1)$). It avoids storing critical point indices in a list or array, keeping memory usage minimal.
