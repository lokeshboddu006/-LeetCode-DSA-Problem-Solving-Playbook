<h2><a href="https://leetcode.com/problems/reverse-integer">Reverse Integer</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a signed 32-bit integer <code>x</code>, return <code>x</code><em> with its digits reversed</em>. If reversing <code>x</code> causes the value to go outside the signed 32-bit integer range <code>[-2<sup>31</sup>, 2<sup>31</sup> - 1]</code>, then return <code>0</code>.</p>

<p><strong>Assume the environment does not allow you to store 64-bit integers (signed or unsigned).</strong></p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> x = 123
<strong>Output:</strong> 321
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> x = -123
<strong>Output:</strong> -321
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> x = 120
<strong>Output:</strong> 21
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>-2<sup>31</sup> &lt;= x &lt;= 2<sup>31</sup> - 1</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution reverses the integer digit by digit by extracting the last digit of `x` using modulo arithmetic (`x % 10`) and appending it to a running result variable `r`. 

To strictly comply with the constraint that 64-bit integers cannot be used, the solution checks for potential 32-bit integer overflow/underflow *before* performing the operation `r * 10 + m`. If performing `r * 10 + m` would exceed the bounds of a signed 32-bit integer (`Integer.MAX_VALUE` or `Integer.MIN_VALUE`), it returns `0` immediately.

### 🔍 Approach

1. **Initialization**: Initialize `r = 0` to build the reversed number.
2. **Loop Condition**: Process digits using `while (x != 0)`. The condition `x != 0` naturally handles both positive and negative numbers.
3. **Digit Extraction & Truncation**:
   - Compute `m = x % 10` to extract the least significant digit (which can be negative if `x` is negative).
   - Divide `x` by `10` (`x /= 10`) to remove the extracted digit.
4. **Overflow and Underflow Safeguards**:
   - **Positive Overflow**: Check if `r > Integer.MAX_VALUE / 10` or if `r == Integer.MAX_VALUE / 10` and `m > 7` (since `2147483647 % 10 == 7`). If so, multiplying `r` by 10 and adding `m` would exceed `Integer.MAX_VALUE`, so return `0`.
   - **Negative Underflow**: Check if `r < Integer.MIN_VALUE / 10` or if `r == Integer.MIN_VALUE / 10` and `m < -8` (since ` -2147483648 % 10 == -8`). If so, multiplying `r` by 10 and adding `m` would drop below `Integer.MIN_VALUE`, so return `0`.
5. **Update Result**: Append the digit via `r = r * 10 + m`.
6. **Return**: Once all digits are processed (`x == 0`), return `r`.

### 🧩 Algorithm

- **Loop Invariant**: At the start of each iteration, `r` contains the reverse of the digits extracted from `x` so far, and `r` is guaranteed to fit within a 32-bit signed integer.
- **Math / Bound Logic**:
  - `Integer.MAX_VALUE` = $2^{31} - 1 = 2,147,483,647$
  - `Integer.MIN_VALUE` = $-2^{31} = -2,147,483,648$
  - Multiplier transition: $r_{\text{new}} = r_{\text{old}} \times 10 + m$
  - Bounds guard:
    - $r_{\text{old}} > \lfloor \text{MAX} / 10 \rfloor \implies \text{Overflow}$
    - $r_{\text{old}} == \lfloor \text{MAX} / 10 \rfloor \text{ and } m > 7 \implies \text{Overflow}$
    - $r_{\text{old}} < \lceil \text{MIN} / 10 \rceil \implies \text{Underflow}$
    - $r_{\text{old}} == \lceil \text{MIN} / 10 \rceil \text{ and } m < -8 \implies \text{Underflow}$

### ✅ Why This Works

- In Java, `%` on negative numbers produces negative remainders (e.g., `-123 % 10 == -3`). The logic naturally works for both positive and negative inputs without requiring separate absolute value handling.
- Checking overflow boundaries prior to multiplication guarantees that `r * 10 + m` is never evaluated when it would cause arithmetic overflow/underflow, satisfying the 32-bit storage restriction.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(\log_{10}|x|)$. The number of iterations corresponds to the number of decimal digits in $x$. Since $x$ is a 32-bit integer, there are at most $10$ digits, making execution bounded by at most 10 iterations ($\mathcal{O}(1)$ time in practice).
- **Space Complexity:** $\mathcal{O}(1)$. Uses a fixed number of primitive `int` variables (`r`, `m`) requiring constant extra space.

### 🧠 DSA Pattern

- **Math / Bit Manipulation Bounds Check**
- **Digit Extraction via Modulo and Division**

### ⚠️ Common Mistakes

1. **Relying on 64-bit integers (`long`)**: Using `long` to store the intermediate result and checking `r > Integer.MAX_VALUE` at the end violates the explicit constraint of not using 64-bit storage.
2. **Incorrect Modulo Handling for Negative Numbers**: Assuming `x % 10` always returns a positive number. In Java, `-123 % 10` is `-3`, so checking `m > 7` without accounting for negative `m < -8` would fail underflow detection.
3. **Checking Overflow After Math Operations**: Writing `r = r * 10 + m` before checking for overflow causes integer wrap-around before the check takes place.

### 🚀 Optimization Notes

- This solution is optimal for both time and space complexity.
- It strictly abides by the 32-bit environment constraint by performing pre-computation checks rather than relying on standard overflow wrapping behavior or wider data types.
