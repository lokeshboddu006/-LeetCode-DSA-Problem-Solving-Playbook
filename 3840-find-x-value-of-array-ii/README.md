<h2><a href="https://leetcode.com/problems/find-x-value-of-array-ii">Find X Value of Array II</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given an array of <strong>positive</strong> integers <code>nums</code> and a <strong>positive</strong> integer <code>k</code>. You are also given a 2D array <code>queries</code>, where <code>queries[i] = [index<sub>i</sub>, value<sub>i</sub>, start<sub>i</sub>, x<sub>i</sub>]</code>.</p>

<p>You are allowed to perform an operation <strong>once</strong> on <code>nums</code>, where you can remove any <strong>suffix</strong> from <code>nums</code> such that <code>nums</code> remains <strong>non-empty</strong>.</p>

<p>The <strong>x-value</strong> of <code>nums</code> <strong>for a given</strong> <code>x</code> is defined as the number of ways to perform this operation so that the <strong>product</strong> of the remaining elements leaves a <em>remainder</em> of <code>x</code> <strong>modulo</strong> <code>k</code>.</p>

<p>For each query in <code>queries</code> you need to determine the <strong>x-value</strong> of <code>nums</code> for <code>x<sub>i</sub></code> after performing the following actions:</p>

<ul>
	<li>Update <code>nums[index<sub>i</sub>]</code> to <code>value<sub>i</sub></code>. Only this step persists for the rest of the queries.</li>
	<li><strong>Remove</strong> the prefix <code>nums[0..(start<sub>i</sub> - 1)]</code> (where <code>nums[0..(-1)]</code> will be used to represent the <strong>empty</strong> prefix).</li>
</ul>

<p>Return an array <code>result</code> of size <code>queries.length</code> where <code>result[i]</code> is the answer for the <code>i<sup>th</sup></code> query.</p>

<p>A <strong>prefix</strong> of an array is a <span data-keyword="subarray">subarray</span> that starts from the beginning of the array and extends to any point within it.</p>

<p>A <strong>suffix</strong> of an array is a <span data-keyword="subarray">subarray</span> that starts at any point within the array and extends to the end of the array.</p>

<p><strong>Note</strong> that the prefix and suffix to be chosen for the operation can be <strong>empty</strong>.</p>

<p><strong>Note</strong> that x-value has a <em>different</em> definition in this version.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,3,4,5], k = 3, queries = [[2,2,0,2],[3,3,3,0],[0,1,0,1]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[2,2,2]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>For query 0, <code>nums</code> becomes <code>[1, 2, 2, 4, 5]</code>, and the empty prefix <strong>must</strong> be removed. The possible operations are:

	<ul>
		<li>Remove the suffix <code>[2, 4, 5]</code>. <code>nums</code> becomes <code>[1, 2]</code>.</li>
		<li>Remove the empty suffix. <code>nums</code> becomes <code>[1, 2, 2, 4, 5]</code> with a product 80, which gives remainder 2 when divided by 3.</li>
	</ul>
	</li>
	<li>For query 1, <code>nums</code> becomes <code>[1, 2, 2, 3, 5]</code>, and the prefix <code>[1, 2, 2]</code> <strong>must</strong> be removed. The possible operations are:
	<ul>
		<li>Remove the empty suffix. <code>nums</code> becomes <code>[3, 5]</code>.</li>
		<li>Remove the suffix <code>[5]</code>. <code>nums</code> becomes <code>[3]</code>.</li>
	</ul>
	</li>
	<li>For query 2, <code>nums</code> becomes <code>[1, 2, 2, 3, 5]</code>, and the empty prefix <strong>must</strong> be removed. The possible operations are:
	<ul>
		<li>Remove the suffix <code>[2, 2, 3, 5]</code>. <code>nums</code> becomes <code>[1]</code>.</li>
		<li>Remove the suffix <code>[3, 5]</code>. <code>nums</code> becomes <code>[1, 2, 2]</code>.</li>
	</ul>
	</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,4,8,16,32], k = 4, queries = [[0,2,0,2],[0,2,0,1]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[1,0]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>For query 0, <code>nums</code> becomes <code>[2, 2, 4, 8, 16, 32]</code>. The only possible operation is:

	<ul>
		<li>Remove the suffix <code>[2, 4, 8, 16, 32]</code>.</li>
	</ul>
	</li>
	<li>For query 1, <code>nums</code> becomes <code>[2, 2, 4, 8, 16, 32]</code>. There is no possible way to perform the operation.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,1,2,1,1], k = 2, queries = [[2,1,0,1]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[5]</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= k &lt;= 5</code></li>
	<li><code>1 &lt;= queries.length &lt;= 2 * 10<sup>4</sup></code></li>
	<li><code>queries[i] == [index<sub>i</sub>, value<sub>i</sub>, start<sub>i</sub>, x<sub>i</sub>]</code></li>
	<li><code>0 &lt;= index<sub>i</sub> &lt;= nums.length - 1</code></li>
	<li><code>1 &lt;= value<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
	<li><code>0 &lt;= start<sub>i</sub> &lt;= nums.length - 1</code></li>
	<li><code>0 &lt;= x<sub>i</sub> &lt;= k - 1</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Removing a suffix from the subarray `nums[start..n-1]` leaves a non-empty prefix of `nums[start..n-1]`. Therefore, finding the number of valid suffix removals such that the remaining product leaves a remainder of $x_i \pmod k$ is equivalent to counting how many prefixes of the range $[start_i, n-1]$ have a total element product congruent to $x_i \pmod k$.

Because the array `nums` undergoes point updates while we query range prefix properties, a **Segment Tree** is used. Each segment tree node maintains:
1. The total product modulo $k$ of all elements in its segment (`mul`).
2. Frequency counts of all prefixes in its segment grouped by their product modulo $k$ (`pre`).

---

### 🔍 Approach

1. **`Data` Node Representation**:
   - `pre`: An array of length $k$, where `pre[r]` stores the number of prefixes within the segment whose product is $\equiv r \pmod k$.
   - `mul`: The product of all elements in the segment modulo $k$.
   - `getMergedData(d1, d2)`: Combines two adjacent segments `d1` (left) and `d2` (right):
     - The overall product modulo $k$ is `(d1.mul * d2.mul) % k`.
     - Prefixes contained entirely within `d1` keep their original products modulo $k$.
     - Prefixes that extend into `d2` consist of the entire left segment `d1` followed by a prefix of `d2`. Their product modulo $k$ becomes `(d1.mul * prefix_in_d2) % k`.

2. **`SegmentTree` Structure**:
   - `build`: Recursively creates tree nodes. Leaf nodes set `pre[arr[i] % k] = 1` and `mul = arr[i] % k`. Internal nodes merge left and right child nodes using `getMergedData`.
   - `setVal`: Updates `nums[i]` to `newVal`. It includes an early-exit optimization: if `arr[i] % k == newVal % k`, the segment tree values are unchanged, so it returns early.
   - `query`: Performs a range query over $[lq, rq]$ and merges the matching segments into a single `Data` result.

3. **Query Execution (`resultArray`)**:
   - Sets static modulo parameter `Data.k = k`.
   - Constructs the segment tree over `nums`.
   - Iterates through each query `[index, value, start, x]`:
     1. Calls `st.setVal(index, value)` to apply the update.
     2. Queries the range $[start, n - 1]$ using `st.query(start, nums.length - 1)`.
     3. Reads the count of prefixes with remainder $x$ from `.pre[x]` and stores it in the result array.

---

### 🧩 Algorithm

#### Segment Tree Node Merge (`getMergedData`)

Given left node $D_1$ and right node $D_2$:

$$\text{merged.mul} = (D_1.\text{mul} \times D_2.\text{mul}) \pmod k$$

For each $i \in [0, k-1]$:
$$\text{merged.pre}[i] = D_1.\text{pre}[i]$$

For each $i \in [0, k-1]$:
$$\text{merged.pre}[(D_1.\text{mul} \times i) \pmod k] \mathrel{+}= D_2.\text{pre}[i]$$

---

### ✅ Why This Works

- **Associativity of Modulo Multiplication**:
  Any prefix spanning into the right child $D_2$ has a product equal to $(\prod D_1) \times (\text{prefix of } D_2) \pmod k$. Since $D_1.\text{mul} = \prod D_1 \pmod k$, we can correctly map every prefix remainder $i$ from $D_2$ to $(D_1.\text{mul} \times i) \pmod k$.
- **Correct Subarray Prefix Counting**:
  Querying the Segment Tree on the range $[start_i, n-1]$ yields a `Data` node representing exactly the subarray left after removing prefix `nums[0..start_i - 1]`. The `.pre[x_i]` field of this query node directly gives the count of non-empty prefixes of that range whose product leaves remainder $x_i \pmod k$.

---

### ⏱️ Complexity

- **Time Complexity**:
  - **Tree Construction**: $O(n \cdot k)$ — $O(n)$ nodes are built, and each merge operation takes $O(k)$ time.
  - **Per Query Update**: $O(k \log n)$ — tree depth is $O(\log n)$, and merging nodes takes $O(k)$ time at each level.
  - **Per Query Range Query**: $O(k \log n)$ — standard segment tree query visits $O(\log n)$ canonical segments and merges them in $O(k)$ time.
  - **Total Time Complexity**: $O(n \cdot k + q \cdot k \log n)$, where $n = \text{nums.length}$, $q = \text{queries.length}$, and $k \le 5$.

- **Space Complexity**:
  - **Segment Tree**: $O(n \cdot k)$ memory to store $O(n)$ tree nodes, each containing a `pre` array of size $k$.
  - **Total Space Complexity**: $O(n \cdot k + q)$ including the result array.

---

### 🧠 DSA Pattern

- **Segment Tree**: Used for point updates and dynamic range query aggregation with custom node merging.

---

### ⚠️ Common Mistakes

1. **Static Variable Usage**: `Data.k` is a `static` variable. In environment setups where multiple test cases run sequentially in the same JVM process, failing to assign `Data.k = k` prior to building the tree would result in wrong array sizes or incorrect modulo arithmetic.
2. **Modulo Operations during Merge**: Forgetting to take `(d1.mul * i) % k` when mapping prefix remainders from the right child into the merged node would result in out-of-bounds array accesses or wrong bucket counts.

---

### 🚀 Optimization Notes

- **Modulus Equivalence Early Return**: In `setVal`, checking `if (arr[i] % Data.k == newVal % Data.k) return;` avoids executing an $O(k \log n)$ tree traversal when the updated value produces the same remainder modulo $k$ as the previous value.
- **Small Modulo bound $k \le 5$**: Because $k$ is very small, loops over $k$ inside `getMergedData` are $O(1)$ operations in practice.
- **Garbage Collection Overhead**: `queryHelper` creates new `Data` instances on every merge step during recursive calls. While fine within time limits, an optimization would be to pass a mutable pre-allocated output buffer to avoid creating transient `Data` objects during range queries.
