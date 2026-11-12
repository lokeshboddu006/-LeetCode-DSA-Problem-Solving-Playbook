<h2><a href="https://leetcode.com/problems/longest-strictly-increasing-or-strictly-decreasing-subarray">Longest Strictly Increasing or Strictly Decreasing Subarray</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an array of integers <code>nums</code>. Return <em>the length of the <strong>longest</strong> <span data-keyword="subarray-nonempty">subarray</span> of </em><code>nums</code><em> which is either <strong><span data-keyword="strictly-increasing-array">strictly increasing</span></strong> or <strong><span data-keyword="strictly-decreasing-array">strictly decreasing</span></strong></em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,4,3,3,2]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>The strictly increasing subarrays of <code>nums</code> are <code>[1]</code>, <code>[2]</code>, <code>[3]</code>, <code>[3]</code>, <code>[4]</code>, and <code>[1,4]</code>.</p>

<p>The strictly decreasing subarrays of <code>nums</code> are <code>[1]</code>, <code>[2]</code>, <code>[3]</code>, <code>[3]</code>, <code>[4]</code>, <code>[3,2]</code>, and <code>[4,3]</code>.</p>

<p>Hence, we return <code>2</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3,3,3,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong></p>

<p>The strictly increasing subarrays of <code>nums</code> are <code>[3]</code>, <code>[3]</code>, <code>[3]</code>, and <code>[3]</code>.</p>

<p>The strictly decreasing subarrays of <code>nums</code> are <code>[3]</code>, <code>[3]</code>, <code>[3]</code>, and <code>[3]</code>.</p>

<p>Hence, we return <code>1</code>.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3,2,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>The strictly increasing subarrays of <code>nums</code> are <code>[3]</code>, <code>[2]</code>, and <code>[1]</code>.</p>

<p>The strictly decreasing subarrays of <code>nums</code> are <code>[3]</code>, <code>[2]</code>, <code>[1]</code>, <code>[3,2]</code>, <code>[2,1]</code>, and <code>[3,2,1]</code>.</p>

<p>Hence, we return <code>3</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 50</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 50</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem requires finding the length of the longest subarray that is either strictly increasing or strictly decreasing. 

The key intuition behind this solution is that as we traverse the array from left to right, we only need to compare each element with its immediate predecessor (`nums[i - 1]`):
- If `nums[i] > nums[i - 1]`, we can extend our current strictly increasing sequence, while any strictly decreasing sequence is broken (reset to length 1).
- If `nums[i] < nums[i - 1]`, we can extend our current strictly decreasing sequence, while any strictly increasing sequence is broken (reset to length 1).
- If `nums[i] == nums[i - 1]`, neither sequence can continue, so both sequences reset to length 1.

By maintaining state counters for both sequence types in a single linear scan, we can continuously track the maximum length encountered.

### 🔍 Approach

1. **Initialization**:
   - `n`: Stores the length of `nums`.
   - `maxLen`: Initialized to `1` because any single element array itself is a valid strictly increasing/decreasing subarray of length 1.
   - `ic` (*increasing count*): Keeps track of the length of the current strictly increasing subarray ending at index `i` (initialized to `1`).
   - `dc` (*decreasing count*): Keeps track of the length of the current strictly decreasing subarray ending at index `i` (initialized to `1`).

2. **Iterative Scan**:
   - Loop `i` from `1` to `n - 1`:
     - Compare `nums[i]` with `nums[i - 1]`:
       - If `nums[i] > nums[i - 1]`: increment `ic` by `1` and reset `dc` to `1`.
       - Else if `nums[i] < nums[i - 1]`: increment `dc` by `1` and reset `ic` to `1`.
       - Else (`nums[i] == nums[i - 1]`): reset both `ic` and `dc` to `1`.
     - Update `maxLen` with `Math.max(maxLen, Math.max(ic, dc))` after evaluating the current index.

3. **Result**:
   - Return `maxLen`.

### 🧩 Algorithm

- **State Invariants**:
  - `ic` represents the length of the longest strictly increasing subarray ending at index `i`.
  - `dc` represents the length of the longest strictly decreasing subarray ending at index `i`.
  - `maxLen` represents the maximum of all `ic` and `dc` values seen up to index `i`.

- **State Transitions** at step `i`:
  - If `nums[i] > nums[i-1]`: 
    $$\text{ic}_i = \text{ic}_{i-1} + 1, \quad \text{dc}_i = 1$$
  - If `nums[i] < nums[i-1]`: 
    $$\text{ic}_i = 1, \quad \text{dc}_i = \text{dc}_{i-1} + 1$$
  - If `nums[i] == nums[i-1]`: 
    $$\text{ic}_i = 1, \quad \text{dc}_i = 1$$
  - Update overall maximum:
    $$\text{maxLen}_i = \max(\text{maxLen}_{i-1}, \text{ic}_i, \text{dc}_i)$$

### ✅ Why This Works

- **Correct Base Case**: Starting `maxLen`, `ic`, and `dc` at `1` correctly handles arrays of length 1 as well as any fresh sequence start.
- **Local to Global Transition**: Any contiguous strictly increasing or decreasing subarray ending at index `i` must either extend the valid subarray ending at index `i - 1` or start a new subarray of length `1` at index `i`. Since we update `maxLen` at every index `i`, no valid sequence length is missed.
- **Handling Equal Elements**: Equal adjacent elements (`nums[i] == nums[i - 1]`) break strict monotonicity in both directions, so resetting both `ic` and `dc` to `1` ensures strictness is strictly maintained.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the number of elements in `nums`. We perform a single loop from index `1` to $N - 1$, doing constant time $\mathcal{O}(1)$ operations at each iteration.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. Only a few integer variables (`n`, `maxLen`, `ic`, `dc`) are used.

### 🧠 DSA Pattern

- **Greedy / One-Pass State Tracking**: Maintaining running tracking counters (`ic`, `dc`) while iterating through the array once to compute the optimal global answer (`maxLen`).
- **Dynamic Programming (Space-Optimized)**: Can be viewed as 1D DP where the state only depends on the immediately preceding index, allowing space optimization to $\mathcal{O}(1)$.

### ⚠️ Common Mistakes

1. **Resetting to `0` instead of `1`**: When monotonicity breaks, resetting counters to `0` instead of `1` is a common bug. The element at `nums[i]` itself forms a valid subarray of length 1.
2. **Missing equal elements**: Forgetting that equal elements (e.g. `[3, 3]`) are neither strictly increasing nor strictly decreasing, requiring both counters to reset.
3. **Updating `maxLen` outside the loop**: Updating `maxLen` only at the end of the loop can work if structured properly, but updating it inside the loop at every iteration (as done in this solution) is safer and avoids edge cases when sequences end at the end of the array.

### 🚀 Optimization Notes

- This solution is already optimal in terms of both time complexity ($\mathcal{O}(N)$) and space complexity ($\mathcal{O}(1)$).
- Memory allocations are minimal, and operations inside the loop are simple scalar arithmetic and conditionals. No micro-optimizations are necessary.
