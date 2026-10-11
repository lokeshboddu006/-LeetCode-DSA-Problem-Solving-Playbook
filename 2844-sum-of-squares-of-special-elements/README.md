<h2><a href="https://leetcode.com/problems/sum-of-squares-of-special-elements">Sum of Squares of Special Elements </a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given a <strong>1-indexed</strong> integer array <code>nums</code> of length <code>n</code>.</p>

<p>An element <code>nums[i]</code> of <code>nums</code> is called <strong>special</strong> if <code>i</code> divides <code>n</code>, i.e. <code>n % i == 0</code>.</p>

<p>Return <em>the <strong>sum of the squares</strong> of all <strong>special</strong> elements of </em><code>nums</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3,4]
<strong>Output:</strong> 21
<strong>Explanation:</strong> There are exactly 3 special elements in nums: nums[1] since 1 divides 4, nums[2] since 2 divides 4, and nums[4] since 4 divides 4. 
Hence, the sum of the squares of all special elements of nums is nums[1] * nums[1] + nums[2] * nums[2] + nums[4] * nums[4] = 1 * 1 + 2 * 2 + 4 * 4 = 21.  
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,7,1,19,18,3]
<strong>Output:</strong> 63
<strong>Explanation:</strong> There are exactly 4 special elements in nums: nums[1] since 1 divides 6, nums[2] since 2 divides 6, nums[3] since 3 divides 6, and nums[6] since 6 divides 6. 
Hence, the sum of the squares of all special elements of nums is nums[1] * nums[1] + nums[2] * nums[2] + nums[3] * nums[3] + nums[6] * nums[6] = 2 * 2 + 7 * 7 + 1 * 1 + 3 * 3 = 63. 
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length == n &lt;= 50</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 50</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition
The solution checks each element in the array to see if its 1-based position divides the total array length `n`. 

Since Java arrays use 0-based indexing (`0` to `n - 1`), the 1-based index corresponding to element `nums[i]` is `i + 1`. An element is "special" if `n % (i + 1) == 0`. When a special element is found, its square (`nums[i] * nums[i]`) is added to an accumulator variable `res`.

### 🔍 Approach
1. **Initialization**:
   - `n` stores the length of the array `nums`.
   - `res` is initialized to `0` to accumulate the sum of squares of special elements.

2. **Iteration & Selection**:
   - The code uses a `for` loop starting at `i = 0` and running while `i <= n`.
   - In each iteration, it checks if `i + 1` divides `n` using `n % (i + 1) == 0`.
   - If `i + 1` is a divisor of `n`, it squares the element (`nums[i] * nums[i]`) and adds it to `res`.

3. **Return**:
   - After completing the loop, `res` is returned as the final answer.

### 🧩 Algorithm

- **Loop range**: `i` goes from `0` to `n`.
- **Special element condition**: `n % (i + 1) == 0`.
- **Accumulation step**:
  $$\text{res} \leftarrow \text{res} + \text{nums}[i]^2 \quad \text{if } n \pmod{i + 1} = 0$$

### ✅ Why This Works
- The problem defines indices as 1-based ($1, 2, \dots, n$). Mapping 0-based loop variable `i` to `i + 1` correctly represents the 1-based position.
- Evaluating `n % (i + 1) == 0` filters exactly those elements whose 1-based index divides $n$.
- When `i = n` (the extra iteration due to `i <= n`), `i + 1` becomes `n + 1`. Since $n \pmod{n + 1} = n \neq 0$ for any positive integer $n$, the condition `n % (i + 1) == 0` evaluates to `false`. Because of this, `nums[n]` is never accessed, safely avoiding an `ArrayIndexOutOfBoundsException`.

### ⏱️ Complexity
- **Time Complexity:** $\mathcal{O}(n)$
  The loop runs $n + 1$ times. In each iteration, constant time $\mathcal{O}(1)$ modulo, arithmetic, and conditional logic operations are performed.
- **Space Complexity:** $\mathcal{O}(1)$
  Only a few primitive variables (`n`, `res`, `i`) are used, requiring constant extra space.

### 🧠 DSA Pattern
- **Array Traversal / Direct Simulation**
- **Math (Divisibility & Modular Arithmetic)**

### ⚠️ Common Mistakes
1. **Loop Bound Off-By-One (`i <= n`)**:
   The loop uses `i <= n` instead of the standard `i < n`. Although it avoids a runtime crash because `n % (n + 1) == 0` is false, running `i = n` on an array of size `n` is an implementation risk. If the `if` condition were structured differently or placed after an array access, it would throw an `ArrayIndexOutOfBoundsException`.
2. **Indexing Mismatch**:
   Forgetting to add `1` to `i` when computing `n % (i + 1)` would test 0-based indices instead of 1-based indices, leading to incorrect results or division by zero when `i = 0`.

### 🚀 Optimization Notes
- **Correct the Loop Boundary**:
  Changing `for(int i = 0; i <= n; i++)` to `for(int i = 0; i < n; i++)` eliminates the redundant iteration at `i = n` and prevents potential out-of-bounds risks.
- **Efficiency**:
  Since array length $n \le 50$, the linear scan $\mathcal{O}(n)$ is already optimal for practical limits.
