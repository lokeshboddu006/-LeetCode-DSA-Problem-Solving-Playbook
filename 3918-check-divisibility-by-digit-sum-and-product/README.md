<h2><a href="https://leetcode.com/problems/check-divisibility-by-digit-sum-and-product">Check Divisibility by Digit Sum and Product</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given a positive integer <code>n</code>. Determine whether <code>n</code> is divisible by the <strong>sum </strong>of the following two values:</p>

<ul>
	<li>
	<p>The <strong>digit sum</strong> of <code>n</code> (the sum of its digits).</p>
	</li>
	<li>
	<p>The <strong>digit</strong> <strong>product</strong> of <code>n</code> (the product of its digits).</p>
	</li>
</ul>

<p>Return <code>true</code> if <code>n</code> is divisible by this sum; otherwise, return <code>false</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 99</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>

<p><strong>Explanation:</strong></p>

<p>Since 99 is divisible by the sum (9 + 9 = 18) plus product (9 * 9 = 81) of its digits (total 99), the output is true.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 23</span></p>

<p><strong>Output:</strong> <span class="example-io">false</span></p>

<p><strong>Explanation:</strong></p>

<p>Since 23 is not divisible by the sum (2 + 3 = 5) plus product (2 * 3 = 6) of its digits (total 11), the output is false.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 10<sup>6</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To determine whether $n$ is divisible by the sum of its digit sum and digit product, we need to process all the digits of $n$.

By maintaining two running variables—one for the sum of the digits (starting at `0`) and one for the product of the digits (starting at `1`)—we can extract each digit from right to left using standard modulo and integer division operations. Once all digits are processed, we simply check if $n$ is evenly divisible by `sumDigit + productDigit`.

### 🔍 Approach

1. **Initialization**:
   - `sumDigit = 0`: Accumulator for the sum of digits.
   - `productDigit = 1`: Accumulator for the product of digits.
   - `num = n`: A copy of $n$ used for digit extraction so that the original value of $n$ is preserved for the final divisibility check.

2. **Digit Extraction Loop (`while (num > 0)`)**:
   - Extract the least significant digit using `num % 10`.
   - Add the extracted digit to `sumDigit`.
   - Multiply `productDigit` by the extracted digit.
   - Remove the least significant digit by performing integer division: `num /= 10`.

3. **Final Evaluation**:
   - Calculate the divisor as `sumDigit + productDigit`.
   - Return `true` if `n % (sumDigit + productDigit) == 0`, otherwise `false`.

### 🧩 Algorithm

The solution follows a straightforward digit extraction algorithm:

1. Let $S = 0$, $P = 1$, and $temp = n$.
2. While $temp > 0$:
   - $d = temp \bmod 10$
   - $S \leftarrow S + d$
   - $P \leftarrow P \times d$
   - $temp \leftarrow \lfloor temp / 10 \rfloor$
3. Return $(n \bmod (S + P) == 0)$.

### ✅ Why This Works

- **Preservation of Original $n$**: Storing $n$ in `num` before modifying it ensures $n$ remains intact for evaluating `n % (sumDigit + productDigit)`.
- **Digit Extraction Correctness**: Repeated application of `% 10` and `/ 10` correctly visits every base-10 digit of $n$ from right to left.
- **Identity Elements**: `sumDigit` starts at `0` (additive identity) and `productDigit` starts at `1` (multiplicative identity), ensuring both operations aggregate correctly regardless of the number of digits.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(\log_{10} n)$ (or $\mathcal{O}(d)$, where $d$ is the number of digits in $n$). Since $n \le 10^6$, the loop executes at most $7$ times, which executes in $O(1)$ constant time.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. Only a few integer primitive variables (`sumDigit`, `productDigit`, `num`) are allocated.

### 🧠 DSA Pattern

- **Math / Digit Manipulation**: Extracting digits using modulo (`% 10`) and integer division (`/ 10`) without converting the number to a string.

### ⚠️ Common Mistakes

1. **Modifying $n$ Directly**: Modifying $n$ directly inside the loop would destroy the original number, making `n % (sumDigit + productDigit)` evaluate against `0` instead of the original input.
2. **Incorrect Initializer for Product**: Initializing `productDigit` to `0` instead of `1` would cause `productDigit` to remain `0` permanently.
3. **Division by Zero Concern**: If $n \ge 1$, at least one digit is non-zero, meaning `sumDigit` will be at least $1$. Thus, `sumDigit + productDigit` can never be zero, avoiding any arithmetic exceptions.

### 🚀 Optimization Notes

- **Optimal Implementation**: The approach is already optimal in terms of both time and space complexity.
- **No Extra Allocations**: Extracting digits numerically using integer arithmetic avoids string conversion and heap allocations, providing maximum performance.
