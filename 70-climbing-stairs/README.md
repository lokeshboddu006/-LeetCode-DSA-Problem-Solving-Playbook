<h2><a href="https://leetcode.com/problems/climbing-stairs">Climbing Stairs</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are climbing a staircase. It takes <code>n</code> steps to reach the top.</p>

<p>Each time you can either climb <code>1</code> or <code>2</code> steps. In how many distinct ways can you climb to the top?</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> n = 2
<strong>Output:</strong> 2
<strong>Explanation:</strong> There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> n = 3
<strong>Output:</strong> 3
<strong>Explanation:</strong> There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 45</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To reach step `i`, you can either take a 1-step jump from step `i - 1` or a 2-step jump from step `i - 2`. Therefore, the total number of distinct ways to reach step `i` is simply the sum of the number of ways to reach step `i - 1` and step `i - 2`.

Your code takes advantage of this observation. Instead of maintaining an array to store the results for all steps up to `n`, your code only tracks the results of the previous two steps using two variables (`fs` and `sec`) and updates them iteratively.

### 🔍 Approach

1. **Base Case Check**:
   - `if (n <= 1) return 1;`: If `n` is 0 or 1, there is only 1 way to be at or reach the top (taking 0 steps or 1 step respectively).

2. **Initialization**:
   - `int fs = 1;`: Represents the number of ways to reach step `0` (initially acting as `step i - 2`).
   - `int sec = 1;`: Represents the number of ways to reach step `1` (initially acting as `step i - 1`).

3. **Iterative State Shift**:
   - A `for` loop runs from `i = 2` up to `n`.
   - In each iteration:
     - `int third = fs + sec;`: Calculates the number of ways to reach step `i` by adding the ways to reach step `i - 2` (`fs`) and step `i - 1` (`sec`).
     - `fs = sec;`: Shifts `fs` forward to store the ways for step `i - 1`.
     - `sec = third;`: Shifts `sec` forward to store the ways for step `i`.

4. **Result**:
   - After the loop finishes, `sec` holds the total number of ways to reach step `n`, which is then returned.

### 🧩 Algorithm

This is a **Bottom-Up Dynamic Programming** approach (equivalent to generating Fibonacci numbers) with **$O(1)$ Space Optimization**.

* **Base Cases**:
  $$dp[0] = 1$$
  $$dp[1] = 1$$
* **Transition State**:
  $$dp[i] = dp[i - 1] + dp[i - 2] \quad \text{for } i \ge 2$$
* **Space Optimization**:
  - `fs` stores $dp[i-2]$
  - `sec` stores $dp[i-1]$
  - `third` stores $dp[i]$

### ✅ Why This Works

The sequence of choices to reach step $i$ depends exclusively on the immediate prior two steps ($i-1$ and $i-2$). By ensuring that `fs` and `sec` strictly maintain the calculated values of step $i-2$ and step $i-1$ prior to computing step $i$, the recurrence relation $dp[i] = dp[i-1] + dp[i-2]$ is correctly preserved across every step up to $n$.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - The `for` loop executes $n - 1$ times (from `i = 2` to `n`). Inside the loop, only basic arithmetic and variable assignments occur in constant $\mathcal{O}(1)$ time.

- **Space Complexity**: $\mathcal{O}(1)$
  - The solution uses a fixed number of integer variables (`fs`, `sec`, `third`) regardless of how large $n$ is. No extra dynamic memory or data structures are allocated.

### 🧠 DSA Pattern

- **Dynamic Programming** (Bottom-Up with Space Optimization)
- **Fibonacci Sequence Variant**

### ⚠️ Common Mistakes

1. **Incorrect Swapping/Updating Order**: Updating `fs = sec` before calculating `third = fs + sec` would overwrite `fs` prematurely and produce wrong results.
2. **Off-by-One Loop Boundary**: Using `i < n` instead of `i <= n` in the loop condition would calculate the ways to reach step $n - 1$ instead of $n$.
3. **Missing Base Case**: Omitting the `if (n <= 1)` guard would cause incorrect initial values or out-of-bounds calculations for small inputs.

### 🚀 Optimization Notes

- The submitted solution is already **optimal** in terms of both time complexity ($\mathcal{O}(n)$) and auxiliary space ($\mathcal{O}(1)$).
- Variable names like `fs` (first) and `sec` (second) could be renamed to `prev2` and `prev1` for slightly better readability, but functionally the code is clean, efficient, and direct.
