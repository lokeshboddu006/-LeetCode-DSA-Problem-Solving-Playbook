<h2><a href="https://leetcode.com/problems/minimum-score-by-changing-two-elements">Minimum Score by Changing Two Elements</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer array <code>nums</code>.</p>

<ul>
	<li>The <strong>low</strong> score of <code>nums</code> is the <strong>minimum</strong> absolute difference between any two integers.</li>
	<li>The <strong>high</strong> score of <code>nums</code> is the <strong>maximum</strong> absolute difference between any two integers.</li>
	<li>The <strong>score</strong> of <code>nums</code> is the sum of the <strong>high</strong> and <strong>low</strong> scores.</li>
</ul>

<p>Return the <strong>minimum score</strong> after <strong>changing two elements</strong> of <code>nums</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,4,7,8,5]</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Change <code>nums[0]</code> and <code>nums[1]</code> to be 6 so that <code>nums</code> becomes [6,6,7,8,5].</li>
	<li>The low score is the minimum absolute difference: |6 - 6| = 0.</li>
	<li>The high score is the maximum absolute difference: |8 - 5| = 3.</li>
	<li>The sum of high and low score is 3.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,4,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Change <code>nums[1]</code> and <code>nums[2]</code> to 1 so that <code>nums</code> becomes [1,1,1].</li>
	<li>The sum of maximum absolute difference and minimum absolute difference is 0.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>3 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem requires us to minimize the **score** of the array after changing at most two elements. The score is defined as `low_score + high_score`:
- `low_score`: minimum absolute difference between any two elements.
- `high_score`: maximum absolute difference between any two elements (i.e., `max(nums) - min(nums)`).

Key observation: Since we can change **two** elements, we can always set at least two elements in the array to be identical (or set a modified element equal to an unmodified one). This guarantees that the `low_score` becomes `0`. 

Thus, the total score simplifies to just the **`high_score`** of the remaining unmodified elements. To minimize `high_score = max - min`, we must adjust the extreme values (the smallest or largest numbers) in the array. Changing interior numbers would not reduce the range `[min, max]`.

### 🔍 Approach

1. **Sort the array**: The code sorts `nums` in non-decreasing order using `Arrays.sort(nums)`. Sorting groups the smallest elements at the beginning (`nums[0], nums[1], nums[2]`) and the largest elements at the end (`nums[n-3], nums[n-2], nums[n-1]`).

2. **Evaluate the 3 greedy choices**: We have 2 modifications available, so we can remove 2 boundary elements from consideration. There are exactly 3 ways to remove 2 boundary elements:
   - **Remove the 2 smallest elements** (`nums[0]` and `nums[1]`): The remaining range is bounded by `nums[2]` and `nums[n - 1]`. The high score is `nums[n - 1] - nums[2]`.
   - **Remove the 2 largest elements** (`nums[n - 1]` and `nums[n - 2]`): The remaining range is bounded by `nums[0]` and `nums[n - 3]`. The high score is `nums[n - 3] - nums[0]`.
   - **Remove 1 smallest and 1 largest element** (`nums[0]` and `nums[n - 1]`): The remaining range is bounded by `nums[1]` and `nums[n - 2]`. The high score is `nums[n - 2] - nums[1]`.

3. **Take the minimum**: The code returns the minimum high score among these 3 valid configurations using nested `Math.min`:
   ```java
   Math.min(nums[n - 1] - nums[2], Math.min(nums[n - 3] - nums[0], nums[n - 2] - nums[1]))
   ```

### 🧩 Algorithm

1. Sort `nums` in ascending order.
2. Store `n = nums.length`.
3. Calculate the range for three scenarios:
   - Scenario A: `diff1 = nums[n - 1] - nums[2]`
   - Scenario B: `diff2 = nums[n - 3] - nums[0]`
   - Scenario C: `diff3 = nums[n - 2] - nums[1]`
4. Return `min(diff1, min(diff2, diff3))`.

### ✅ Why This Works

- **Low score is always 0**: Because $n \ge 3$ and we can change 2 elements, we can always choose to set one modified element equal to any existing element in the array, making `low_score = 0`.
- **Optimal range reduction**: To minimize `max - min`, any change made to an element outside the remaining range will move it inside that range. Therefore, the new `min` and `max` will simply be boundary elements of the original array that were not modified. Removing 2 elements from the array's extremes leaves only 3 possible remaining boundary pairs: `(2, n-1)`, `(0, n-3)`, and `(1, n-2)`. Checking the minimum span among these three guarantees the optimal solution.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N \log N)$ due to sorting the array of size $N$ using `Arrays.sort(nums)`. Computing the 3 differences and taking their minimum takes $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space if ignoring the stack space used by Java's primitive sorting algorithm (`Dual-Pivot Quicksort`), which uses $\mathcal{O}(\log N)$ space.

### 🧠 DSA Pattern

- **Sorting**
- **Greedy**

### ⚠️ Common Mistakes

1. **Trying to account for low score separately**: Overcomplicating the solution by attempting to calculate non-zero low scores, missing the fact that 2 modifications can trivially force `low_score = 0`.
2. **Missing the mixed case**: Considering only removing 2 from the left or 2 from the right, but forgetting the case where 1 element is removed from the left and 1 from the right (`nums[n - 2] - nums[1]`).

### 🚀 Optimization Notes

- **Sorting Overhead**: `Arrays.sort` takes $\mathcal{O}(N \log N)$. Since only the 3 smallest and 3 largest elements of the array are required, one could find these 6 elements in $\mathcal{O}(N)$ time using a single pass (or partial sorting/min-max tracking).
- **In-place modification**: `Arrays.sort(nums)` mutates the input array. If preserving the input array is required, a copy would need to be made first.
