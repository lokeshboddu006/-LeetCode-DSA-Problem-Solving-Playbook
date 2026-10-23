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

For two elements $x$ and $y$ to be swappable under the rule $x \text{ AND } y == k$, both $x$ and $y$ must contain all the bits set in $k$. 

In order to sort the array, every misplaced element (an element where `nums[i] != i`) must eventually participate in swaps to move to its correct position. Consequently, any bit set in $k$ must be set in **every single misplaced element**. 

To find the maximum possible $k$, we can greedily take the bitwise AND of all elements that are not in their correct positions. Starting with a mask of all 1s (represented as `-1` in 32-bit signed integers), we iteratively perform `lo &= nums[i]` for every misplaced element.

### 🔍 Approach

1. **Initialization**:
   - `int lo = -1;`
   - In 32-bit signed 2's complement representation, `-1` has all bits set to `1` (`0xFFFFFFFF`). This acts as the identity element for the bitwise AND operation.

2. **Iterate and Accumulate Bitwise AND**:
   - Loop through the array using index `i` from `0` to `nums.length - 1`.
   - Check if the element is out of place using `nums[i] != i`.
   - If misplaced, update `lo` by performing `lo &= nums[i]`. This keeps only the bits that are set in both `lo` and the current misplaced element `nums[i]`.

3. **Early Exit**:
   - If `lo` reaches `0` during iteration, it means there are no common set bits among the misplaced elements processed so far. Thus, $k$ cannot be greater than `0`, so the method immediately returns `0`.

4. **Return Result**:
   - If the array was already sorted, `nums[i] != i` is never true, and
 `lo` remains `-1`.
   - `Math.max(lo, 0)` handles this edge case: if `lo == -1`, it returns `0` (as required when the array is already sorted). Otherwise, it returns the accumulated value of `lo`.

### 🧩 Algorithm

- **Bitwise Intersection Algorithm**:
  - Initial state: $lo \gets -1$
  - Iteration rule: For each $i \in [0, n - 1]$:
    - If $nums[i] \neq i$:
      - $lo \gets lo \text{ AND } nums[i]$
      - If $lo = 0$: **return** $0$
  - Final Output: $\max(lo, 0)$

### ✅ Why This Works

- **Preserving Swap Condition**: Any valid $k$ must satisfy $x \text{ AND } k = k$ for every element $x$ that needs to move. This means every bit in $k$ must be a bit present in all elements that need to be swapped.
- **Maximum Bitwise AND**: Taking the bitwise AND over all misplaced elements produces the largest integer containing only the bits shared by every misplaced element.
- **Sorted Array Case**: If no elements are misplaced, `lo` remains `-1`. `Math.max(-1, 0)` evaluates to `0`, satisfying the requirement to return `0` for an already sorted permutation.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$, where $n$ is the length of `nums`. The code makes a single pass over the array.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. The algorithm uses only a single integer variable `lo`.

### 🧠 DSA Pattern

- **Bit Manipulation**: Accumulating bits using bitwise AND (`&=`) to find common active bits across numbers.
- **Greedy**: Taking the bitwise intersection of all necessary elements to maximize $k$.

### ⚠️ Common Mistakes

1. **Misinterpreting `-1` in Bitwise Operations**:
   - Forgetting that in Java, `-1` as an integer has all 32 bits set to `1`, making it the standard starting neutral value for accumulating bitwise AND operations.

2. **Neglecting the Sorted Permutation Edge Case**:
   - Returning `lo` directly without `Math.max(lo, 0)` would return `-1` when the input array is already sorted.

3. **Including Correctly Placed Elements**:
   - Incorporating elements where `nums[i] == i` into the `lo &= nums[i]` calculation would incorrectly restrict $k$ using elements that do not need to move.

### 🚀 Optimization Notes

- **Optimal Time and Space**: $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space is the theoretical lower bound since every element must be inspected at least once to verify if it is placed correctly.
- **Early Loop Termination**: The check `if (lo == 0) return 0;` inside the loop avoids unnecessary iterations once `lo` deteriorates to `0`.
