<h2><a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted">Two Sum II - Input Array Is Sorted</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a <strong>1-indexed</strong> array of integers <code>numbers</code> that is already <strong><em>sorted in non-decreasing order</em></strong>, find two numbers such that they add up to a specific <code>target</code> number. Let these two numbers be <code>numbers[index<sub>1</sub>]</code> and <code>numbers[index<sub>2</sub>]</code> where <code>1 &lt;= index<sub>1</sub> &lt; index<sub>2</sub> &lt;= numbers.length</code>.</p>

<p>Return<em> the indices of the two numbers&nbsp;</em><code>index<sub>1</sub></code><em> and </em><code>index<sub>2</sub></code><em>, <strong>each incremented by one,</strong> as an integer array </em><code>[index<sub>1</sub>, index<sub>2</sub>]</code><em> of length 2.</em></p>

<p>The tests are generated such that there is <strong>exactly one solution</strong>. You <strong>may not</strong> use the same element twice.</p>

<p>Your solution must use only constant extra space.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> numbers = [<u>2</u>,<u>7</u>,11,15], target = 9
<strong>Output:</strong> [1,2]
<strong>Explanation:</strong> The sum of 2 and 7 is 9. Therefore, index<sub>1</sub> = 1, index<sub>2</sub> = 2. We return [1, 2].
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> numbers = [<u>2</u>,3,<u>4</u>], target = 6
<strong>Output:</strong> [1,3]
<strong>Explanation:</strong> The sum of 2 and 4 is 6. Therefore index<sub>1</sub> = 1, index<sub>2</sub> = 3. We return [1, 3].
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> numbers = [<u>-1</u>,<u>0</u>], target = -1
<strong>Output:</strong> [1,2]
<strong>Explanation:</strong> The sum of -1 and 0 is -1. Therefore index<sub>1</sub> = 1, index<sub>2</sub> = 2. We return [1, 2].
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= numbers.length &lt;= 3 * 10<sup>4</sup></code></li>
	<li><code>-1000 &lt;= numbers[i] &lt;= 1000</code></li>
	<li><code>numbers</code> is sorted in <strong>non-decreasing order</strong>.</li>
	<li><code>-1000 &lt;= target &lt;= 1000</code></li>
	<li>The tests are generated such that there is <strong>exactly one solution</strong>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The input array is already sorted in non-decreasing order. Because of this property, the smallest element is at the beginning (`i = 0`) and the largest element is at the end (`j = n - 1`). 

By calculating the sum of the elements at these two pointers (`nums[i] + nums[j]`):
- If the current sum is smaller than `target`, the only way to increase the sum is to move the left pointer `i` to the right to pick a larger value.
- If the current sum is larger than `target`, the only way to decrease the sum is to move the right pointer `j` to the left to pick a smaller value.
- If the sum matches `target`, the solution is found.

This eliminates searching through invalid pairs without having to check every pair explicitly.

### 🔍 Approach

1. **Initialization**:
   - `n`: Stores the length of the input array `nums`.
   - `i`: Left pointer starting at index `0`.
   - `j`: Right pointer starting at index `n - 1`.

2. **Two-Pointer Loop**:
   - The loop runs while `i < j`.
   - In each iteration, calculate `nums[i] + nums[j]`:
     - **Match (`nums[i] + nums[j] == target`)**: The pair is found. Since the problem requires 1-based indexing, return a new array containing `{i + 1, j + 1}`.
     - **Sum too small (`nums[i] + nums[j] < target`)**: Increment `i` (`i++`) to move to a larger value.
     - **Sum too large (`nums[i] + nums[j] > target`)**: Decrement `j` (`j--`) to move to a smaller value.

3. **Fallback Return**:
   - If the loop terminates without finding a pair, return `{-1, -1}` (though problem constraints guarantee exactly one valid pair exists).

### 🧩 Algorithm

- **Loop Invariant**: At any step, the target pair (if it exists) must lie within the range of indices `[i, j]`.
- **Greedy Selection Condition**:
  - `nums[i] + nums[j] == target` $\rightarrow$ Return `[i + 1, j + 1]`
  - `nums[i] + nums[j] < target` $\rightarrow$ `i = i + 1`
  - `nums[i] + nums[j] > target` $\rightarrow$ `j = j - 1`

### ✅ Why This Works

Because the array is sorted:
- Incrementing `i` strictly increases or maintains `nums[i]`, raising the total sum. Any pair involving `nums[i]` and elements to the left of `j` would produce a sum even smaller than the current sum, so index `i` cannot be part of the solution with the current upper bound `j`.
- Decrementing `j` strictly decreases or maintains `nums[j]`, lowering the total sum. Any pair involving `nums[j]` and elements to the right of `i` would produce a sum even larger than the current sum, so index `j` cannot be part of the solution with the current lower bound `i`.

This guarantees that discarding an index at each step never discards the valid pair.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - In each iteration of the `while` loop, either `i` is incremented or `j` is decremented. The two pointers traverse at most $n$ total steps before meeting or finding the answer.

- **Space Complexity**: $\mathcal{O}(1)$
  - Only a fixed number of integer variables (`n`, `i`, `j`) are used. The array returned at the end takes constant $\mathcal{O}(1)$ extra space.

### 🧠 DSA Pattern

- **Two Pointers**: Specifically, opposite-directional pointers on a sorted array shrinking the search space.

### ⚠️ Common Mistakes

1. **Forgetting 1-Based Indexing**: Returning `{i, j}` instead of `{i + 1, j + 1}`.
2. **Integer Overflow**: If array elements were large enough, `nums[i] + nums[j]` could potentially overflow a standard 32-bit signed integer. However, given the problem constraints (values between $-1000$ and $1000$), `int` arithmetic is safe here.
3. **Loop Condition (`i <= j`)**: Using `i <= j` instead of `i < j` could allow using the same element twice (e.g., `nums[i] + nums[i]`), violating the problem rules.

### 🚀 Optimization Notes

- This solution is already optimal in terms of both time complexity ($\mathcal{O}(n)$) and space complexity ($\mathcal{O}(1)$).
- A minor optimization to prevent repeated indexing in the conditions would be to store `nums[i] + nums[j]` in a local variable `sum` inside the loop, avoiding evaluating `nums[i] + nums[j]` twice per iteration in the worst case.
