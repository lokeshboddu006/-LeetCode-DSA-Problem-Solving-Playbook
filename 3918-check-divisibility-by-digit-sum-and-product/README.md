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

To check whether $n$ is divisible by the sum of its digit sum and digit product, we need to decompose $n$ into its constituent digits. By extracting digits one by one using standard arithmetic operations, we can simultaneously accumulate the digit sum and digit product in a single pass. Once all digits are processed, we test if the original number $n$ is evenly divisible by the calculated sum `(s + p)`.

### 🔍 Approach

1. **Variable Initialization**:
   - `s = 0`: Accumulator for the digit sum (additive identity).
   - `p = 1`: Accumulator for the digit product (multiplicative identity).
   - `x = n`: A copy of $n$ used for digit extraction so that the original value $n$ remains untouched for the final modulo operation.

2. **Digit Extraction Loop (`while (x > 0)`)**:
   - Extract the rightmost (least significant) digit using `int d = x % 10`.
   - Add the digit `d` to the running sum: `s += d`.
   - Multiply the running product by `d`: `p *= d`.
   - Remove the rightmost digit using integer division: `x /= 10`.

3. **Divisibility Test**:
   - Compute the combined total `s + p`.
   - Return `true` if `n % (s + p) == 0`, otherwise return `false`.

### 🧩 Algorithm

1. Set $s \leftarrow 0$, $p \leftarrow 1$, and $x \leftarrow n$.
2. While $x > 0$:
   - $d \leftarrow x \bmod 10$
   - $s \leftarrow s + d$
   - $p \leftarrow p \times d$
   - $x \leftarrow \lfloor x / 10 \rfloor$
3. Compute $divisor \leftarrow s + p$.
4. Check condition: $n \bmod divisor == 0$.

### ✅ Why This Works

- **Preservation of original $n$**: By mutating `x` instead of `n` inside the loop, the original value $n$ is preserved intact for the final divisibility check `n % (s + p) == 0`.
- **Identity element choices**: Initializing `s = 0` ensures addition starts cleanly, and `p = 1` ensures multiplication correctly accumulates digit products without wiping them out to zero.
- **Safety against division by zero**: Since $n \ge 1$, $n$ has at least one positive digit, which implies digit sum $s \ge 1$ and digit product $p \ge 0$. Therefore, $s + p \ge 1$, guaranteeing that division by zero never occurs.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(\log_{10} n)$ or $\mathcal{O}(D)$, where $D$ is the number of digits in $n$. Since $n \le 10^6$, the loop executes at most $7$ times, which runs in effectively $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space, as only a few primitive integer variables (`s`, `p`, `x`, `d`) are allocated on the stack.

### 🧠 DSA Pattern

- **Math / Digit Manipulation**: Using modulo (`% 10`) and integer division (`/ 10`) to iteratively process base-10 digits.

### ⚠️ Common Mistakes

1. **Modifying $n$ directly**: If the loop mutated `n` directly (`while (n > 0)`), $n$ would become `0` at the end of the loop, making `n % (s + p)` evaluate to `0 % (s + p)` (always returning `true`). Using `x = n` avoids this bug.
2. **Incorrect Initializer for Product**: Initializing `p = 0` instead of `p = 1` would cause all multiplications to yield `0`, making $p$ permanently zero.

### 🚀 Optimization Notes

- **Optimal Implementation**: This solution is already optimal in both time ($\mathcal{O}(\log_{10} n)$) and space ($\mathcal{O}(1)$).
- **No Overflow Risk**: Given $n \le 10^6$, the maximum possible digit sum is $9 \times 6 = 54$ and the maximum digit product is $9^6 = 531,441$. Both fit easily within standard 32-bit signed integers (`int`), preventing arithmetic overflow.
