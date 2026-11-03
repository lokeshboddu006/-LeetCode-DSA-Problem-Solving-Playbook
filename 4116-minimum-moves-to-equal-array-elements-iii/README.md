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

Since the operation allows you to **increase** any element by 1, you can never decrease a number to match a smaller value. To make all elements equal with the minimum number of total moves, every element must be brought up to match the maximum element currently present in the array.

The moves required for any element `nums[i]` is `mx - nums[i]`, where `mx` is the maximum value in `nums`. 

Instead of subtracting every element from `mx` individually, mathematically summing this over all elements gives:
$$\sum (mx - nums[i]) = (N \times mx) - \sum nums[i]$$

This allows the total moves to be computed using just the maximum value (`mx`) and the sum of all elements (`sm`).

---

### 🔍 Approach

1. **Initialization:**
   - `mx`: initialized to `Integer.MIN_VALUE` to track the maximum element in `nums`.
   - `sm`: initialized to `0` to accumulate the total sum of elements in `nums`.

2. **Single Pass Iteration:**
   - Loop through each element `num` in `nums`:
     - Add `num` to `sm`.
     - Update `mx` using `Math.max(num, mx)` if `num` is larger than the current maximum.

3. **Final Calculation:**
   - Calculate `nums.length * mx - sm` and return the result.

---

### 🧩 Algorithm

1. Set $sm \leftarrow 0$ and $mx \leftarrow -\infty$.
2. For each element $num \in nums$:
   - $sm \leftarrow sm + num$
   - $mx \leftarrow \max(mx, num)$
3. Calculate final answer: $\text{Result} = (\text{nums.length} \times mx) - sm$.

---

### ✅ Why This Works

- **Greedy Target Choice:** The optimal target value for all numbers to reach is the maximum element in the original array (`mx`). Choosing any value smaller than `mx` is impossible without decrement operations (which are not allowed). Choosing any value larger than `mx` would require extra, unnecessary moves for every element.
- **Algebraic Correctness:** 
  $$\text{Total Moves} = \sum_{i=0}^{N-1} (mx - nums[i]) = \sum_{i=0}^{N-1} mx - \sum_{i=0}^{N-1} nums[i] = N \times mx - sm$$
  This simplification guarantees the exact sum of moves needed without needing an extra array or second pass.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is `nums.length`. The code performs a single linear scan over the array to compute `sm` and `mx`.
- **Space Complexity:** $\mathcal{O}(1)$. Only two scalar variables (`mx` and `sm`) are used, consuming constant extra memory.

---

### 🧠 DSA Pattern

- **Math / Greedy**

---

### ⚠️ Common Mistakes

1. **Integer Overflow Risk:** In problems with larger constraints on `nums.length` or `nums[i]`, `sm` or `nums.length * mx` could overflow a standard 32-bit signed `int`. For the given problem constraints ($N \le 100$, $nums[i] \le 100$), `int` is safe, but using `long` is recommended for larger datasets.
2. **Confusing with standard "Minimum Moves to Equal Array Elements" (LeetCode 453):** In that problem, you increment $N-1$ elements by 1, which conceptually equals decrementing 1 element, leading to targeting the *minimum* element. Here, you increment a *single* element, which directly targets the *maximum* element.

---

### 🚀 Optimization Notes

- **Already Optimal:** This code runs in $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ space in a single pass, which is the theoretical lower bound for this problem since every element must be inspected at least once.
