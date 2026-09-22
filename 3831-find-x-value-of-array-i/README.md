<h2><a href="https://leetcode.com/problems/find-x-value-of-array-i">Find X Value of Array I</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an array of <strong>positive</strong> integers <code>nums</code>, and a <strong>positive</strong> integer <code>k</code>.</p>

<p>You are allowed to perform an operation <strong>once</strong> on <code>nums</code>, where in each operation you can remove any <strong>non-overlapping</strong> prefix and suffix from <code>nums</code> such that <code>nums</code> remains <strong>non-empty</strong>.</p>

<p>You need to find the <strong>x-value</strong> of <code>nums</code>, which is the number of ways to perform this operation so that the <strong>product</strong> of the remaining elements leaves a <em>remainder</em> of <code>x</code> when divided by <code>k</code>.</p>

<p>Return an array <code>result</code> of size <code>k</code> where <code>result[x]</code> is the <strong>x-value</strong> of <code>nums</code> for <code>0 &lt;= x &lt;= k - 1</code>.</p>

<p>A <strong>prefix</strong> of an array is a <span data-keyword="subarray">subarray</span> that starts from the beginning of the array and extends to any point within it.</p>

<p>A <strong>suffix</strong> of an array is a <span data-keyword="subarray">subarray</span> that starts at any point within the array and extends to the end of the array.</p>

<p><strong>Note</strong> that the prefix and suffix to be chosen for the operation can be <strong>empty</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,3,4,5], k = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">[9,2,4]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>For <code>x = 0</code>, the possible operations include all possible ways to remove non-overlapping prefix/suffix that do not remove <code>nums[2] == 3</code>.</li>
	<li>For <code>x = 1</code>, the possible operations are:
	<ul>
		<li>Remove the empty prefix and the suffix <code>[2, 3, 4, 5]</code>. <code>nums</code> becomes <code>[1]</code>.</li>
		<li>Remove the prefix <code>[1, 2, 3]</code> and the suffix <code>[5]</code>. <code>nums</code> becomes <code>[4]</code>.</li>
	</ul>
	</li>
	<li>For <code>x = 2</code>, the possible operations are:
	<ul>
		<li>Remove the empty prefix and the suffix <code>[3, 4, 5]</code>. <code>nums</code> becomes <code>[1, 2]</code>.</li>
		<li>Remove the prefix <code>[1]</code> and the suffix <code>[3, 4, 5]</code>. <code>nums</code> becomes <code>[2]</code>.</li>
		<li>Remove the prefix <code>[1, 2, 3]</code> and the empty suffix. <code>nums</code> becomes <code>[4, 5]</code>.</li>
		<li>Remove the prefix <code>[1, 2, 3, 4]</code> and the empty suffix. <code>nums</code> becomes <code>[5]</code>.</li>
	</ul>
	</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,4,8,16,32], k = 4</span></p>

<p><strong>Output:</strong> <span class="example-io">[18,1,2,0]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>For <code>x = 0</code>, the only operations that <strong>do not</strong> result in <code>x = 0</code> are:

	<ul>
		<li>Remove the empty prefix and the suffix <code>[4, 8, 16, 32]</code>. <code>nums</code> becomes <code>[1, 2]</code>.</li>
		<li>Remove the empty prefix and the suffix <code>[2, 4, 8, 16, 32]</code>. <code>nums</code> becomes <code>[1]</code>.</li>
		<li>Remove the prefix <code>[1]</code> and the suffix <code>[4, 8, 16, 32]</code>. <code>nums</code> becomes <code>[2]</code>.</li>
	</ul>
	</li>
	<li>For <code>x = 1</code>, the only possible operation is:
	<ul>
		<li>Remove the empty prefix and the suffix <code>[2, 4, 8, 16, 32]</code>. <code>nums</code> becomes <code>[1]</code>.</li>
	</ul>
	</li>
	<li>For <code>x = 2</code>, the possible operations are:
	<ul>
		<li>Remove the empty prefix and the suffix <code>[4, 8, 16, 32]</code>. <code>nums</code> becomes <code>[1, 2]</code>.</li>
		<li>Remove the prefix <code>[1]</code> and the suffix <code>[4, 8, 16, 32]</code>. <code>nums</code> becomes <code>[2]</code>.</li>
	</ul>
	</li>
	<li>For <code>x = 3</code>, there is no possible way to perform the operation.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,1,2,1,1], k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">[9,6]</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= k &lt;= 5</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Removing a non-overlapping prefix and suffix from `nums` such that `nums` remains non-empty is equivalent to choosing a **non-empty contiguous subarray**. The product of this remaining subarray leaves a remainder `x` when divided by `k`.

To count the total number of such subarrays efficiently, the solution iterates through `A` while maintaining the counts of all subarray products modulo `k` that **end at the current index**. By maintaining these frequency counts at each step, you can extend previous subarrays by multiplying them with the current element modulo `k`, as well as start a new subarray containing only the current element.

---

### 🔍 Approach

1. **Initialization**:
   - `res`: An array of size `k` (type `long`) to store the total counts of non-empty subarrays whose product modulo `k` equals `x`.
   - `freq`: An array of size `k` (type `int`) that stores the counts of product remainders for all subarrays ending at the *previous* index.

2. **Iterating through each element**:
   - For each element `n` in `A`:
     - Reduce `n` modulo `k`: `n %= k`.
     - Create a temporary array `cur` of size `k` to store the modulo counts of subarrays ending at the *current* index.
     - **Start a new subarray**: Increment `cur[n]++` to represent the single-element subarray `[n]`.
     - **Extend existing subarrays**: Loop `x` from `0` to `k - 1`. For every remainder `x` that had `freq[x]` subarrays ending at the previous index, multiplying by `n` changes their remainder to `(x * n) % k`. Add `freq[x]` to `cur[(x * n) % k]`.

3. **State Update & Result Accumulation**:
   - Set `freq = cur` to prepare for the next iteration.
   - Add all counts in `freq` to `res` because every subarray ending at the current index is a valid non-empty subarray.

4. **Return**:
   - Return the accumulated counts in `res`.

---

### 🧩 Algorithm

This is a **1D Dynamic Programming / Rolling Array** approach over remainders modulo `k`.

* **DP State**: 
  `freq[x]` = number of contiguous subarrays ending at index `i - 1` whose product modulo `k` equals `x`.

* **Base Case (at current index `i` with `n = A[i] % k`)**:
  Start a new subarray of length 1:
  $$\text{cur}[n] = 1$$

* **Transition**:
  For each $x \in [0, k-1]$:
  $$\text{cur}[(x \times n) \bmod k] = \text{cur}[(x \times n) \bmod k] + \text{freq}[x]$$

* **Accumulation**:
  $$\text{res}[x] = \text{res}[x] + \text{freq}[x] \quad \forall x \in [0, k-1]$$

---

### ✅ Why This Works

Every non-empty contiguous subarray ends at some index $i$ in `A`. By processing elements one by one from left to right:
- `freq` correctly captures all subarray products modulo $k$ that end at $i - 1$.
- Multiplying all previous products modulo $k$ by `A[i] % k` correctly transitions their remainders to the new end index $i$.
- Adding `cur[n]++` accounts for the subarray starting and ending at index $i$.
- Summing `freq` into `res` at each step ensures every valid contiguous subarray is counted exactly once without missing any or double-counting.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N \cdot k)$
  - We loop through $N$ elements (`A.length`).
  - Inside the loop, we perform $k$ transitions to build `cur` and $k$ operations to update `res`.
  - Since $k \le 5$, $N \cdot k$ takes at most $5 \times 10^5$ operations, which easily runs in $\mathcal{O}(N)$ time.

- **Space Complexity:** $\mathcal{O}(k)$
  - `res`, `freq`, and `cur` all have a fixed size of $k$.
  - Auxiliary space is $O(k)$ (or $O(1)$ relative to $N$).

---

### 🧠 DSA Pattern

- **Dynamic Programming (Modulo Arithmetic / State Compression)**
- **Prefix / Subarray Counting via Rolling States**

---

### ⚠️ Common Mistakes

1. **Ignoring Single-Element Subarrays**: Forgetting to initialize `cur[n]++` would miss all subarrays of length 1 and any subarrays starting at the current index.
2. **Updating `freq` In-Place**: Trying to update `freq` directly without a temporary `cur` array would cause intermediate updates to pollute subsequent modulo calculations in the same step.
3. **Integer Overflow during Modulo**: If $n$ was not reduced using `n %= k` or if $k$ were large, $x \cdot n$ could overflow integer limits. However, since $k \le 5$ and `n` is reduced modulo $k$, $x \cdot n \le 16$, which safely avoids overflow.

---

### 🚀 Optimization Notes

- **Optimal Time Complexity**: The time complexity $\mathcal{O}(N \cdot k)$ is already optimal for this DP state representation.
- **Memory Allocation**: Inside the loop, `int[] cur = new int[k]` creates a new array object of size $k$ in every iteration ($N$ allocations). While $N \le 10^5$ is well within Java's garbage collection limits, allocating two reusable buffers outside the loop and swapping them would eliminate heap allocations entirely.
