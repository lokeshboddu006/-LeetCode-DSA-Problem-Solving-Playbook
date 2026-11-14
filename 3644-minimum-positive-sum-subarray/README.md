<h2><a href="https://leetcode.com/problems/minimum-positive-sum-subarray">Minimum Positive Sum Subarray </a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an integer array <code>nums</code> and <strong>two</strong> integers <code>l</code> and <code>r</code>. Your task is to find the <strong>minimum</strong> sum of a <strong>subarray</strong> whose size is between <code>l</code> and <code>r</code> (inclusive) and whose sum is greater than 0.</p>

<p>Return the <strong>minimum</strong> sum of such a subarray. If no such subarray exists, return -1.</p>

<p>A <strong>subarray</strong> is a contiguous <b>non-empty</b> sequence of elements within an array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3, -2, 1, 4], l = 2, r = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong></p>

<p>The subarrays of length between <code>l = 2</code> and <code>r = 3</code> where the sum is greater than 0 are:</p>

<ul>
	<li><code>[3, -2]</code> with a sum of 1</li>
	<li><code>[1, 4]</code> with a sum of 5</li>
	<li><code>[3, -2, 1]</code> with a sum of 2</li>
	<li><code>[-2, 1, 4]</code> with a sum of 3</li>
</ul>

<p>Out of these, the subarray <code>[3, -2]</code> has a sum of 1, which is the smallest positive sum. Hence, the answer is 1.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [-2, 2, -3, 1], l = 2, r = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">-1</span></p>

<p><strong>Explanation:</strong></p>

<p>There is no subarray of length between <code>l</code> and <code>r</code> that has a sum greater than 0. So, the answer is -1.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1, 2, 3, 4], l = 2, r = 4</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>The subarray <code>[1, 2]</code> has a length of 2 and the minimum sum greater than 0. So, the answer is 3.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= l &lt;= r &lt;= nums.length</code></li>
	<li><code>-1000 &lt;= nums[i] &lt;= 1000</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of your solution is to iterate through every valid subarray size between `l` and `r`, and for each specific size, compute the sum of all contiguous subarrays using a sliding window. 

Instead of recomputing the subarray sum from scratch every time, you compute the sum of the first window of size `size`, and then slide the window across the array by adding the incoming element and subtracting the outgoing element. Whenever a window's sum is strictly positive (`sum > 0`), you track the smallest sum seen so far.

---

### 🔍 Approach

1. **Initialization**:
   - `n`: Stores the size of the input list `nums`.
   - `ans`: Initialized to `Integer.MAX_VALUE` to track the minimum positive sum encountered across all valid window sizes.

2. **Outer Loop (Window Sizes)**:
   - `for (int size = l; size <= r; size++)`: Iterates through every allowed length `size` from `l` to `r` inclusive.

3. **Building the First Window**:
   - For a given `size`, you calculate the sum of the first subarray of length `size` (indices `0` to `size - 1`) using a loop:
     ```java
     int sum = 0;
     for (int i = 0; i < size; i++) {
         sum += nums.get(i);
     }
     ```
   - If `sum > 0`, update `ans` with `Math.min(ans, sum)`.

4. **Sliding the Window**:
   - `for (int i = size; i < n; i++)`: Move the window one element to the right at each step.
   - Add the new element entering the window (`nums.get(i)`) and remove the element leaving the window (`nums.get(i - size)`):
     ```java
     sum += nums.get(i);
     sum -= nums.get(i - size);
     ```
   - If the updated `sum > 0`, check if it's the new minimum positive sum (`ans = Math.min(ans, sum)`).

5. **Return Result**:
   - If `ans` remains `Integer.MAX_VALUE`, no valid positive sum subarray of length between `l` and `r` was found, so return `-1`. Otherwise, return `ans`.

---

### 🧩 Algorithm

- **Loop Invariant**: For a fixed `size` and current right boundary `i`, `sum` accurately holds the sum of elements from index `i - size + 1` to `i`.
- **Sliding Window State Transition**:
  $$\text{sum}_{new} = \text{sum}_{old} + \text{nums}[i] - \text{nums}[i - \text{size}]$$
- **Filtering Condition**:
  $$\text{if } \text{sum} > 0 \implies \text{ans} = \min(\text{ans}, \text{sum})$$

---

### ✅ Why This Works

- **Exhaustiveness**: By iterating through all sizes from `l` to `r` and sliding a window of length `size` from index `0` to `n - 1`, your algorithm evaluates **every possible subarray** whose length is between `l` and `r`.
- **Accuracy**: The sliding window technique correctly updates the sum of the current subarray in $O(1)$ time per shift, guaranteeing that no window sums are calculated incorrectly.
- **Correct Return Value**: Initializing `ans` to `Integer.MAX_VALUE` makes it easy to detect if any positive sum was found.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}((r - l + 1) \cdot n)$
  - For each window `size` (which runs $r - l + 1$ times), you perform an initial $O(\text{size})$ loop followed by an $O(n - \text{size})$ sliding loop. Together, processing a single size takes $O(n)$ operations.
  - Across all sizes, the total time is $O((r - l + 1) \cdot n)$, which is bounded by $O(n^2)$ in the worst case (when $l = 1$ and $r = n$). Given the constraints ($n \le 100$), this runs almost instantaneously.

- **Space Complexity**: $\mathcal{O}(1)$
  - You only use a few scalar variables (`n`, `ans`, `size`, `sum`, `i`), requiring constant extra space.

---

### 🧠 DSA Pattern

- **Sliding Window (Fixed Size)**: Used repeatedly for each target length `size` from `l` to `r` to compute contiguous subarray sums in linear time per length.

---

### ⚠️ Common Mistakes

1. **Incorrect Condition Check**: Checking `sum >= 0` instead of `sum > 0`. The problem specifically asks for a sum *greater than 0* (positive). Your code correctly uses `sum > 0`.
2. **Missing Initial Window Check**: Forgetting to check `if (sum > 0)` after computing the initial window of size `size` before entering the sliding loop. Your implementation correctly handles the initial window.
3. **Off-by-One in Sliding Index**: Using `i - size + 1` instead of `i - size` when subtracting the outgoing element. Your code correctly subtracts `nums.get(i - size)`.

---

### 🚀 Optimization Notes

- **Current Implementation Efficiency**: Given $n \le 100$, your $O(n \cdot (r - l + 1))$ solution is concise, readable, and executes well within the time limits.
- **Micro-Optimization**: Using primitive arrays (`int[]`) instead of `List<Integer>` avoids boxing/unboxing overhead and `.get(i)` method calls, though for $n \le 100$ the difference is negligible.
