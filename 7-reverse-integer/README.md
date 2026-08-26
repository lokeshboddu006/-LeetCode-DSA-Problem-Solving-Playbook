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

The core idea of this solution is to extract digits from `x` one by one from right to left using the modulo operator (`% 10`), and append them to a running total `r`. Multiplying `r` by `10` before adding the new digit shifts the previous digits to the left by one decimal place.

To handle potential 32-bit integer overflow during the reversal process, the solution accumulates the result inside a standard 64-bit integer (`long`). This allows the code to perform arithmetic safely without wrapping around mid-calculation, deferring the overflow check until after all digits have been processed.

### 🔍 Approach

1. **Initialization**: 
   - A variable `long r = 0` is initialized to hold the accumulated reversed number. Using `long` avoids silent arithmetic overflow during multiplication and addition.

2. **Digit Extraction Loop (`while (x != 0)`)**:
   - The loop continues as long as `x` has remaining digits.
   - `x % 10` extracts the last digit of `x`.
   - `r = r * 10 + x % 10` shifts `r` one decimal position to the left and appends the extracted digit.
   - `x /= 10` truncates the last digit from `x`.
   - Note: Because of how Java's `%` and `/` operators handle negative numbers (e.g., `-123 % 10 = -3` and `-123 / 10 = -12`), the negative sign is naturally preserved without requiring separate logic for negative inputs.

3. **Range Check and Return**:
   - After extracting all digits, `r` is checked against 32-bit signed integer limits (`Integer.MIN_VALUE` and `Integer.MAX_VALUE`).
   - If `r` falls outside `[-2³¹, 2³¹ - 1]`, the function returns `0`.
   - Otherwise, `r` is cast to an `int` and returned.

### 🧩 Algorithm

- **Loop Condition**: `x != 0`
- **State Transition**:
  $$\text{digit} = x \bmod 10$$
  $$r_{\text{new}} = r_{\text{old}} \times 10 + \text{digit}$$
  $$x_{\text{new}} = \lfloor x_{\text{old}} / 10 \rfloor$$
- **Loop Invariant**: At the start of each iteration, `r` contains the exact digit-reversal of the processed suffix of `x`, while `x` contains the remaining prefix to be processed.
- **Overflow Validation**: 
  $$\text{result} = \begin{cases} 0 & \text{if } r < -2^{31} \text{ or } r > 2^{31} - 1 \\ \text{(int)} r & \text{otherwise} \end{cases}$$

### ✅ Why This Works

- **Sign Handling**: Java uses truncated division for integer arithmetic. Remainder retains the sign of the dividend. Thus, for negative numbers, `x % 10` yields a negative digit, which correctly subtracts when added to `r * 10` (since `r` is also negative).
- **Overflow Safety**: A 32-bit integer reversed can yield a maximum magnitude of $9,646,324,351$, which easily fits in a standard 64-bit signed `long` ($\approx \pm 9 \times 10^{18}$). Hence, `r` will never overflow during the `while` loop, allowing a simple bounds check at the end.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(\log_{10} |x|)$ — The number of iterations corresponds to the number of decimal digits in $x$. Since $x$ is a 32-bit integer, it has at most 10 digits, meaning the loop runs at most 10 times ($\mathcal{O}(1)$ in practice).
- **Space Complexity**: $\mathcal{O}(1)$ — Only a few primitive variables (`x` and `r`) are used, consuming constant memory.

### 🧠 DSA Pattern

- **Math**: Digit manipulation via arithmetic operations (`%` and `/`).

### ⚠️ Common Mistakes

1. **Using `x > 0` instead of `x != 0`**: Using `x > 0` breaks the loop immediately if `x` is negative, causing negative inputs to return `0`.
2. **Expecting `% 10` to always be positive**: In languages like Python, modulo returns positive values, but in Java/C++, `%` retains the sign of the left operand. Trying to force positivity manually can distort negative reversals.
3. **Casting before range check**: Doing `(int) r < Integer.MIN_VALUE` would overflow before the check, making the boundary check useless. Checking `r` while it is still a `long` avoids this.

### 🚀 Optimization Notes

- **Simplicity**: Storing the running total in a `long` keeps the code clean and avoids complex inline overflow boundary checks inside the loop.
- **Environment Assumption**: The problem description mentions assuming the environment cannot store 64-bit integers. While this Java code relies on `long` (a 64-bit integer) and successfully executes, a strict 32-bit-only environment would require checking for overflow *before* multiplying `r * 10` by comparing `r` directly against `Integer.MAX_VALUE / 10` and `Integer.MIN_VALUE / 10`.
