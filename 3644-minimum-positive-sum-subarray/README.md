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

The core idea of this solution is to exhaustively check every possible contiguous subarray of `nums`. By iterating over every starting position `i` and expanding to every ending position `j`, the code calculates the subarray sum incrementally. Whenever the subarray length is within the range $[l, r]$ and the sum is strictly positive ($> 0$), it keeps track of the smallest sum encountered.

### 🔍 Approach

1. **Initialization**:
   - `n`: Stores the size of the input list `nums`.
   - `ans`: Initialized to `Integer.MAX_VALUE` to serve as a sentinel value representing that no valid subarray has been found yet.

2. **Outer Loop (`for (int i = 0; i < n; i++)`)**:
   - Sets the starting index `i` for a new batch of subarrays.
   - Resets `sum = 0` to accumulate the total as the subarray expands.

3. **Inner Loop (`for (int j = i; j < n; j++)`)**:
   - Expands the right endpoint `j` of the subarray starting at `i`.
   - `sum += nums.get(j)`: Continuously adds the current element to `sum`, allowing the code to compute the subarray sum of `nums[i...j]` in $O(1)$ time per step.
   - `int len = j - i + 1`: Computes the length of the current subarray `nums[i...j]`.

4. **Condition Check**:
   - `if (len >= l && len <= r && sum > 0)`: Checks if the current subarray satisfies all three conditions:
     1. Length is at least `l` (`len >= l`)
     2. Length is at most `r` (`len <= r`)
     3. Sum is strictly positive (`sum > 0`)
   - If satisfied, `ans = Math.min(ans, sum)` updates `ans` with the smaller positive sum.

5. **Return Value**:
   - If `ans` was updated, returns `ans`.
   - If `ans` remains `Integer.MAX_VALUE` (meaning no valid subarray was found), returns `-1`.

### 🧩 Algorithm

- **Brute-Force Subarray Iteration**:
  - Loop bounds: Outer index $i \in [0, n-1]$, Inner index $j \in [i, n-1]$.
  - State invariant: `sum` represents $\sum_{k=i}^{j} \text{nums}[k]$.
  - Length check: $\text{len} = j - i + 1$.
  - Update rule: $\text{ans} = \min(\text{ans}, \text{sum})$ if $l \le \text{len} \le r$ and $\text{sum} > 0$.

### ✅ Why This Works

- **Exhaustive Evaluation**: By testing every pair of start index `i` and end index `j`, no contiguous subarray is skipped.
- **Accurate Running Sum**: Adding `nums.get(j)` step-by-step ensures the sum of `nums[i...j]` is always computed accurately without redundant recalculation.
- **Correct Range and Positivity Filter**: The explicit `if (len >= l && len <= r && sum > 0)` check guarantees that only valid subarrays update the candidate answer.
- **Sentinel Handing**: Starting `ans` at `Integer.MAX_VALUE` ensures any valid positive sum will successfully override it, while returning `-1` if no valid subarray exists.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^2)$, where $N$ is the length of `nums`.
  - The outer loop runs $N$ times.
  - The inner loop runs up to $N$ times for each outer iteration, performing $O(1)$ work per step. Total subarray checks are $\frac{N(N+1)}{2}$.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space.
  - Only a fixed set of primitive variables (`n`, `ans`, `i`, `sum`, `j`, `len`) is used.

### 🧠 DSA Pattern

- **Brute Force / Exhaustive Search** (Nested loops for subarray generation)
- **Running Sum / Incremental Aggregation**

### ⚠️ Common Mistakes

1. **Iterating past maximum allowed length `r`**:
   - While the condition `len <= r` guards against updating `ans`, the inner loop continues incrementing `j` even after `len > r`, leading to redundant computations.
2. **Confusing positive with non-negative**:
   - Checking `sum >= 0` instead of `sum > 0` would incorrectly include zero-sum subarrays. The code correctly uses `sum > 0`.
3. **Incorrect Sentinel Value**:
   - Initializing `ans` to `0` or `-1` instead of `Integer.MAX_VALUE` would break the `Math.min` comparison.

### 🚀 Optimization Notes

- **Early Termination in Inner Loop**: Since `len = j - i + 1` increases by $1$ in every iteration of the inner loop, once `len > r`, any further increments of `j` will also result in `len > r`. You can optimize the inner loop by breaking early:
  ```java
  if (len > r) break;
  ```
- **Constraint Context**: Given $N \le 100$, the $O(N^2)$ complexity performs at most $\approx 5000$ operations, which easily runs well within the execution time limit.
