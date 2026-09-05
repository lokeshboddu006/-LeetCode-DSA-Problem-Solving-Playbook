<h2><a href="https://leetcode.com/problems/smallest-stable-index-i">Smallest Stable Index I</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an integer array <code>nums</code> of length <code>n</code> and an integer <code>k</code>.</p>

<p>For each index <code>i</code>, define its <strong>instability score</strong> as <code>max(nums[0..i]) - min(nums[i..n - 1])</code>.</p>

<p>In other words:</p>

<ul>
	<li><code>max(nums[0..i])</code> is the <strong>largest</strong> value among the elements from index 0 to index <code>i</code>.</li>
	<li><code>min(nums[i..n - 1])</code> is the <strong>smallest</strong> value among the elements from index <code>i</code> to index <code>n - 1</code>.</li>
</ul>

<p>An index <code>i</code> is called <strong>stable</strong> if its instability score is <strong>less than or equal to</strong> <code>k</code>.</p>

<p>Return the <strong>smallest</strong> stable index. If no such index exists, return -1.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [5,0,1,4], k = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>At index 0: The maximum in <code>[5]</code> is 5, and the minimum in <code>[5, 0, 1, 4]</code> is 0, so the instability score is <code>5 - 0 = 5</code>.</li>
	<li>At index 1: The maximum in <code>[5, 0]</code> is 5, and the minimum in <code>[0, 1, 4]</code> is 0, so the instability score is <code>5 - 0 = 5</code>.</li>
	<li>At index 2: The maximum in <code>[5, 0, 1]</code> is 5, and the minimum in <code>[1, 4]</code> is 1, so the instability score is <code>5 - 1 = 4</code>.</li>
	<li>At index 3: The maximum in <code>[5, 0, 1, 4]</code> is 5, and the minimum in <code>[4]</code> is 4, so the instability score is <code>5 - 4 = 1</code>.</li>
	<li>This is the first index with an instability score less than or equal to <code>k = 3</code>. Thus, the answer is 3.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3,2,1], k = 1</span></p>

<p><strong>Output:</strong> <span class="example-io">-1</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>At index 0, the instability score is <code>3 - 1 = 2</code>.</li>
	<li>At index 1, the instability score is <code>3 - 1 = 2</code>.</li>
	<li>At index 2, the instability score is <code>3 - 1 = 2</code>.</li>
	<li>None of these values is less than or equal to <code>k = 1</code>, so the answer is -1.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [0], k = 0</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<p>At index 0, the instability score is <code>0 - 0 = 0</code>, which is less than or equal to <code>k = 0</code>. Therefore, the answer is 0.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>0 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>0 &lt;= k &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the smallest index $i$ where `max(nums[0..i]) - min(nums[i..n - 1]) <= k`.

To evaluate this instability score efficiently for any index $i$, we need:
1. The maximum element from index `0` to `i` (Prefix Maximum).
2. The minimum element from index `i` to `n - 1` (Suffix Minimum).

Instead of recalculating the minimum of `nums[i..n-1]` repeatedly in $\mathcal{O}(n)$ time per index, your solution precomputes all suffix minimums in a single right-to-left pass. Then, during a left-to-right pass, it maintains a running prefix maximum on the fly and checks the condition at each index.

### 🔍 Approach

1. **Suffix Minimum Precomputation (Right-to-Left Pass)**:
   - You create an integer array `suffix` of size `n`.
   - You initialize a running minimum `mn = Integer.MAX_VALUE`.
   - Iterate backward from `i = n - 1` down to `0`:
     - Update `mn` to `Math.min(mn, nums[i])`.
     - Store `mn` in `suffix[i]`. Thus, `suffix[i]` holds `min(nums[i..n-1])`.

2. **Prefix Maximum & Instability Score Check (Left-to-Right Pass)**:
   - Initialize `mx = 0` to track the prefix maximum.
   - Iterate forward from `i = 0` to `n - 1`:
     - Update `mx` to `Math.max(mx, nums[i])`, representing `max(nums[0..i])`.
     - Calculate `score = mx - suffix[i]`.
     - Check if `score <= k`. Since you traverse from $i = 0$ upwards, the first index satisfying this condition is guaranteed to be the smallest stable index, so you return `i` immediately.

3. **Fallback**:
   - If no index satisfies `score <= k` after checking all elements, return `-1`.

### 🧩 Algorithm

- **Precomputation (Suffix Minimum)**:
  $$\text{suffix}[i] = \min(\text{nums}[i], \text{suffix}[i+1]) \quad \text{for } i = n-2 \text{ down to } 0$$
- **Prefix Maximum (On-the-fly)**:
  $$\text{mx}_i = \max(\text{mx}_{i-1}, \text{nums}[i]) \quad \text{with } \text{mx}_{-1} = 0$$
- **Evaluation**:
  $$\text{score}_i = \text{mx}_i - \text{suffix}[i]$$
  Return $i$ at the first instance where $\text{score}_i \le k$.

### ✅ Why This Works

- `suffix[i]` accurately stores $\min(\text{nums}[i..n-1])$ because it accumulates the minimum element from index $n-1$ down to $i$.
- `mx` accurately keeps track of $\max(\text{nums}[0..i])$ as the loop moves from left to right.
- The formula `mx - suffix[i]` directly computes the required instability score for index $i$.
- Returning `i` upon the very first match guarantees that the output is the **smallest** stable index.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$
  - Computing the `suffix` array takes one pass over `nums` ($\mathcal{O}(n)$ operations).
  - Checking the stability condition takes at most one pass over `nums` ($\mathcal{O}(n)$ operations).
  - Total time complexity is linear, $\mathcal{O}(n)$.

- **Space Complexity:** $\mathcal{O}(n)$
  - An auxiliary array `suffix` of length $n$ is allocated to store the suffix minimums.

### 🧠 DSA Pattern

- **Prefix / Suffix Technique**: Combining a precomputed suffix array (`suffix`) with an on-the-fly prefix state (`mx`).

### ⚠️ Common Mistakes

1. **Initial value assumption for `mx`**:
   - `mx` is initialized to `0`. This works correctly here because array constraints state $0 \le \text{nums}[i] \le 10^9$. If elements could be negative, initializing `mx = 0` would produce incorrect prefix maximums. Initializing with `nums[0]` or `Integer.MIN_VALUE` is a safer standard practice.

2. **Index out of bounds / direction in suffix computation**:
   - Reversing the loop or incorrectly setting the loop bounds when building `suffix` could result in using invalid values or missing elements in the suffix range.

### 🚀 Optimization Notes

- **Time Optimality**: The algorithm runs in $\mathcal{O}(n)$ time, which is optimal because every element must be inspected at least once.
- **Space Usage**: The $\mathcal{O}(n)$ extra space for `suffix` is well within limits for $n \le 100$. Since prefix maximums are evaluated left-to-right, storing suffix minimums in an array is the natural way to enable $\mathcal{O}(1)$ lookup during the forward pass.
