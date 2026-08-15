<h2><a href="https://leetcode.com/problems/longest-subsequence-with-non-zero-bitwise-xor">Longest Subsequence With Non-Zero Bitwise XOR</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer array <code>nums</code>.</p>

<p>Return the length of the <strong>longest <span data-keyword="subsequence-array-nonempty">subsequence</span></strong> in <code>nums</code> whose bitwise <strong>XOR</strong> is <strong>non-zero</strong>. If no such <strong>subsequence</strong> exists, return 0.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>One longest subsequence is <code>[2, 3]</code>. The bitwise XOR is computed as <code>2 XOR 3 = 1</code>, which is non-zero.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [2,3,4]</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>The longest subsequence is <code>[2, 3, 4]</code>. The bitwise XOR is computed as <code>2 XOR 3 XOR 4 = 5</code>, which is non-zero.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution is based on a fundamental property of the bitwise XOR operation: XORing a value $v$ with itself yields $0$ ($v \oplus v = 0$), and XORing $0$ with $v$ leaves $v$ unchanged ($0 \oplus v = v$).

1. If every single element in the array is `0`, then any non-empty subsequence will have a bitwise XOR sum of `0`. Thus, no valid subsequence exists, and the answer is `0`.
2. If the bitwise XOR sum of all $n$ elements in the array is already non-zero, the entire array is the longest valid subsequence, giving a length of $n$.
3. If the total XOR sum of all $n$ elements is `0`, but there is at least one non-zero element $v$, removing that element $v$ from the full set leaves $n - 1$ elements whose XOR sum becomes $0 \oplus v = v$. Since $v \neq 0$, this $n - 1$ length subsequence has a non-zero XOR sum.

### 🔍 Approach

The code executes in a single pass over the input array `a`:

1. **Variables Initialization**:
   - `x`: Keeps track of the running bitwise XOR sum of all elements (initialized to `0`).
   - `z`: Counts the number of zeros in the array (initialized to `0`).
   - `n`: Stores the total number of elements in array `a`.

2. **Single Pass Loop**:
   - Iterates through each value `v` in array `a`.
   - Accumulates `v` into the running XOR sum: `x ^= v`.
   - Increments the zero counter `z` if `v == 0`.

3. **Final Answer Determination**:
   - Uses ternary operators to check:
     - `z == n`: If all elements are `0`, returns `0`.
     - `x != 0`: If the total XOR sum is non-zero, returns `n`.
     - Otherwise: Returns `n - 1`.

### 🧩 Algorithm

The algorithm relies on the following logic:

- **Total XOR Calculation**:
  $$\text{Total XOR } X = a[0] \oplus a[1] \oplus \dots \oplus a[n-1]$$

- **Decision Rule**:
  $$\text{Result} = \begin{cases} 
  0 & \text{if } \text{count}(a[i] == 0) = n \\
  n & \text{if } X \neq 0 \\
  n - 1 & \text{if } X = 0 \text{ and } \exists a[i] \neq 0
  \end{cases}$$

### ✅ Why This Works

- **Case 1 ($z == n$)**: All elements are zero. Every subsequence sums to $0$, so the maximum length is $0$.
- **Case 2 ($X \neq 0$)**: The entire array of length $n$ satisfies the condition directly, which is the maximum possible length for any subsequence.
- **Case 3 ($X = 0$ and $z < n$)**: The entire array XORs to $0$. Since $z < n$, there exists at least one non-zero element $v$. Removing $v$ leaves $n - 1$ elements with XOR sum $X \oplus v = 0 \oplus v = v \neq 0$. Since length $n$ gives XOR sum $0$, $n - 1$ is guaranteed to be the maximum possible length.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$, where $n$ is the length of array `a`. The code performs a single linear iteration over the array.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. Only three primitive integer variables (`x`, `z`, `n`) are used.

### 🧠 DSA Pattern

- **Bit Manipulation** (XOR properties)
- **Greedy / Mathematical Observation**

### ⚠️ Common Mistakes

1. **Overcomplicating with Dynamic Programming or Subset-Sum**: Thinking that finding a subsequence with a specific XOR requires tracking dynamic programming states, whereas bitwise properties allow an $O(n)$ greedy check.
2. **Missing All-Zeros Case**: Returning `n - 1` when all array elements are `0` instead of checking `z == n` to return `0`.

### 🚀 Optimization Notes

This solution is already optimal in both time ($\mathcal{O}(n)$) and auxiliary space ($\mathcal{O}(1)$). No further performance or implementation optimizations are needed.
