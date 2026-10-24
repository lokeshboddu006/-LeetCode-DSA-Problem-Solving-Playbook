<h2><a href="https://leetcode.com/problems/maximum-k-to-sort-a-permutation">Maximum K to Sort a Permutation</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer array <code>nums</code> of length <code>n</code>, where <code>nums</code> is a <strong><span data-keyword="permutation-array">permutation</span></strong> of the numbers in the range <code>[0..n - 1]</code>.</p>

<p>You may swap elements at indices <code>i</code> and <code>j</code> <strong>only if</strong> <code>nums[i] AND nums[j] == k</code>, where <code>AND</code> denotes the bitwise AND operation and <code>k</code> is a <strong>non-negative</strong> integer.</p>

<p>Return the <strong>maximum</strong> value of <code>k</code> such that the array can be sorted in <strong>non-decreasing</strong> order using any number of such swaps. If <code>nums</code> is already sorted, return 0.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [0,3,2,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong></p>

<p>Choose <code>k = 1</code>. Swapping <code>nums[1] = 3</code> and <code>nums[3] = 1</code> is allowed since <code>nums[1] AND nums[3] == 1</code>, resulting in a sorted permutation: <code>[0, 1, 2, 3]</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [0,1,3,2]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>Choose <code>k = 2</code>. Swapping <code>nums[2] = 3</code> and <code>nums[3] = 2</code> is allowed since <code>nums[2] AND nums[3] == 2</code>, resulting in a sorted permutation: <code>[0, 1, 2, 3]</code>.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3,2,1,0]</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<p>Only <code>k = 0</code> allows sorting since no greater <code>k</code> allows the required swaps where <code>nums[i] AND nums[j] == k</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= nums[i] &lt;= n - 1</code></li>
	<li><code>nums</code> is a permutation of integers from <code>0</code> to <code>n - 1</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

In a permutation of numbers from `0` to `n - 1`, an element `nums[i]` is in its correct sorted position if `nums[i] == i`. Any element where `nums[i] != i` must participate in swaps to reach its correct index.

For any swap operation involving a value `x` under the rule `x AND y == k`, every set bit in `k` must also be set in `x` (i.e., `k` must be a bitwise submask of `x`). Consequently, every misplaced element must have set bits wherever `k` has set bits. To find the maximum possible `k`, we greedily take the bitwise AND of all elements that are currently out of place (`nums[i] != i`).

### 🔍 Approach

1. **Bitmask Accumulator Initialization**:
   - Initialize `k = -1`. In 32-bit signed integers (two's complement), `-1` has all bits set to `1` (i.e., `0xFFFFFFFF`), serving as the identity element for the bitwise AND operation.

2. **Single Pass Scan**:
   - Iterate through the array from `i = 0` to `nums.length - 1`.
   - Check if the current element is misplaced (`nums[i] != i`).

3. **Accumulate Bitwise AND**:
   - For every misplaced element, update `k &= nums[i]`.
   - If `k` reaches `0`, return `0` immediately (since bitwise AND can never restore bits once zeroed out).

4. **Return Result**:
   - After inspecting all elements, return `Math.max(k, 0)`.
   - If the array was already sorted, no element triggered the `if (nums[i] != i)` block, leaving `k` as `-1`. `Math.max(-1, 0)` correctly returns `0`.

### 🧩 Algorithm

- **Loop Invariant**: At index `i`, `k` holds the bitwise AND of all elements `nums[j]` encountered so far (where $0 \le j < i$) that satisfy `nums[j] != j`.
- **State Transition**:
  $$\text{if } \text{nums}[i] \neq i \implies k = k \mathbin{\&} \text{nums}[i]$$
- **Early Exit Condition**:
  $$\text{if } k == 0 \implies \text{return } 0$$
- **Final Output Calculation**:
  $$\text{return } \max(k, 0)$$

### ✅ Why This Works

- **Mandatory Bit Preservation**: If an element `nums[i]` is not at index `i`, it must be moved. For `nums[i] AND y == k` to hold in any swap involving `nums[i]`, `k` cannot contain any bit that is `0` in `nums[i]`.
- **Intersection of Valid Bits**: Combining all misplaced numbers via bitwise AND computes the largest value `k` whose set bits are present in *every* misplaced number.
- **Already Sorted Array**: If `nums[i] == i` for all indices, `k` remains `-1`. `Math.max(-1, 0)` handles this edge case seamlessly by returning `0`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$ — The solution scans the array once. Each iteration performs $\mathcal{O}(1)$ comparisons and bitwise operations. The `if (k == 0)` check allows early exit.
- **Space Complexity**: $\mathcal{O}(1)$ — Uses a single integer variable `k` without allocating extra memory.

### 🧠 DSA Pattern

- **Bit Manipulation**
- **Greedy**

### ⚠️ Common Mistakes

- **Initializing `k` to 0**: Setting `k = 0` initially would cause `k &= nums[i]` to always evaluate to `0`. Initializing to `-1` (all bits set to `1`) is essential for cumulative bitwise AND.
- **Including Correctly Placed Elements**: Including elements where `nums[i] == i` in the bitwise AND would unnecessarily clear bits and reduce `k` further than required.
- **Missing Already-Sorted Edge Case**: Returning `k` directly without `Math.max(k, 0)` would return `-1` instead of `0` when the array is already sorted.

### 🚀 Optimization Notes

- The solution is already optimal in terms of both time ($\mathcal{O}(n)$) and space ($\mathcal{O}(1)$).
- The early return `if (k == 0) return 0;` optimizes best/average-case runtime by stopping execution as soon as `k` degrades to zero.
