<h2><a href="https://leetcode.com/problems/minimum-moves-to-equal-array-elements-iii">Minimum Moves to Equal Array Elements III</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an integer array <code>nums</code>.</p>

<p>In one move, you may <strong>increase</strong> the value of any single element <code>nums[i]</code> by 1.</p>

<p>Return the <strong>minimum total</strong> number of <strong>moves</strong> required so that all elements in <code>nums</code> become <strong>equal</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [2,1,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>To make all elements equal:</p>

<ul>
	<li>Increase <code>nums[0] = 2</code> by 1 to make it 3.</li>
	<li>Increase <code>nums[1] = 1</code> by 1 to make it 2.</li>
	<li>Increase <code>nums[1] = 2</code> by 1 to make it 3.</li>
</ul>

<p>Now, all elements of <code>nums</code> are equal to 3. The minimum total moves is <code>3</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [4,4,5]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>To make all elements equal:</p>

<ul>
	<li>Increase <code>nums[0] = 4</code> by 1 to make it 5.</li>
	<li>Increase <code>nums[1] = 4</code> by 1 to make it 5.</li>
</ul>

<p>Now, all elements of <code>nums</code> are equal to 5. The minimum total moves is <code>2</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Since the only allowed operation is **increasing** an element by 1, to make all elements equal with the minimum number of total moves, every element must be brought up to match the maximum element already present in the array. 

Increasing a smaller element beyond the maximum value present in the original array would require extra unnecessary moves. Therefore, target value for all elements must be the maximum value in `nums`.

---

### 🔍 Approach

1. **Find the Maximum Value**:
   - The code initializes `max` to `0` (or the minimum possible value within context).
   - It iterates through the array `nums` from index `0` to `n - 1` using a `for` loop to compute the maximum value using `Math.max(max, nums[i])`.

2. **Calculate Total Moves**:
   - The code initializes an accumulator variable `ans` to `0`.
   - It iterates through `nums` a second time.
   - For each element `nums[i]`, it calculates how many increment operations are needed to reach `max` (`max - nums[i]`) and adds this difference directly to `ans`.

3. **Return the Result**:
   - Returns `ans`, which stores the total sum of differences between `max` and each element.

---

### 🧩 Algorithm

1. Let $n = \text{nums.length}$.
2. Compute $max = \max_{0 \le i < n} (\text{nums}[i])$.
3. Compute total operations:
   $$\text{ans} = \sum_{i=0}^{n-1} (max - \text{nums}[i])$$
4. Return $\text{ans}$.

---

### ✅ Why This Works

- **Greedy Choice**: Any final value $V$ strictly greater than $max$ would add $V - max$ unnecessary moves to every single element. To minimize moves, the optimal final value is precisely $V = max$.
- **Correct Target**: Each element $\text{nums}[i]$ needs exactly $max - \text{nums}[i]$ moves to become equal to $max$. Summing these differences across all elements gives the exact minimum total moves.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of the array `nums`.
  - The array is traversed twice: once to find the maximum element and once to sum up the differences.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.
  - Only a few integer variables (`n`, `max`, `ans`, `i`) are used.

---

### 🧠 DSA Pattern

- **Greedy**: Selecting the local maximum element as the target state guarantees the globally optimal (minimum) total number of operations.
- **Array Traversal**: Simple linear scans over the input array.

---

### ⚠️ Common Mistakes

1. **Initial Value of `max`**:
   - In this implementation, `max` is initialized to `0`. Since constraints state $1 \le \text{nums}[i] \le 100$, initializing `max = 0` works correctly. However, if negative numbers were allowed in `nums`, initializing `max = 0` could cause bugged behavior (e.g., initializing to `Integer.MIN_VALUE` or `nums[0]` would be safer for negative inputs).
2. **Single Pass vs Two Passes**:
   - Forgetting to find `max` before attempting to calculate differences. `max` must be fully determined prior to calculating `max - nums[i]`.

---

### 🚀 Optimization Notes

- **Single Pass Streamlining**: While the current solution uses two passes (one for finding `max` and one for summing), it can be combined into a single pass by maintaining a running sum `sum` and `max` simultaneously, then returning `(max * n) - sum`.
- **Optimal Complexity**: The current time complexity $\mathcal{O}(n)$ and space complexity $\mathcal{O}(1)$ are already optimal for this problem.
