<h2><a href="https://leetcode.com/problems/palindrome-number">Palindrome Number</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given an integer <code>x</code>, return <code>true</code> if <code>x</code> is a <span data-keyword="palindrome-integer"><strong>palindrome</strong></span>, and <code>false</code> otherwise.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> x = 121
<strong>Output:</strong> true
<strong>Explanation:</strong> 121 reads as 121 from left to right and from right to left.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> x = -121
<strong>Output:</strong> false
<strong>Explanation:</strong> From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> x = 10
<strong>Output:</strong> false
<strong>Explanation:</strong> Reads 01 from right to left. Therefore it is not a palindrome.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>-2<sup>31</sup>&nbsp;&lt;= x &lt;= 2<sup>31</sup>&nbsp;- 1</code></li>
</ul>

<p>&nbsp;</p>
<strong>Follow up:</strong> Could you solve it without converting the integer to a string?

---

## 🧠 AI Explanation

### 💡 Intuition
To check if an integer is a palindrome without converting it to a string, your solution reverses the **second half** of the number and compares it directly with the **first half**. 

Reversing only half of the number achieves two major benefits:
1. Negative numbers and numbers ending in `0` (except `0` itself) are filtered out immediately because they can never form valid palindromes.
2. Reversing only half the digits guarantees that the reversed number will never overflow the 32-bit integer range.

### 🔍 Approach
1. **Early Exit Guard**:
   - `x < 0`: Negative numbers have a leading minus sign (e.g., `-121` becomes `121-`), so they are immediately returned as `false`.
   - `x % 10 == 0 && x != 0`: Any positive number ending in `0` (e.g., `10`, `100`) cannot be a palindrome because no integer starts with `0`. The single digit `0` is an exception and passes this check.

2. **Half-Reversal Loop**:
   - Initialize `r = 0` to accumulate the reversed digits of the right half.
   - Run a loop `while (x > r)`:
     - Append the last digit of `x` to `r` using `r = r * 10 + x % 10`.
     - Remove the last digit from `x` using `x /= 10`.
   - The loop terminates as soon as `x <= r`, which signifies that half (or slightly more than half) of the digits have been moved from `x` to `r`.

3. **Palindrome Verification**:
   - **Even length** (e.g., `1221`): Loop ends when `x = 12` and `r = 12`. Here, `x == r` evaluates to `true`.
   - **Odd length** (e.g., `12321`): Loop ends when `x = 12` and `r = 123`. The middle digit (`3`) is at the end of `r`. Dividing `r` by 10 (`r / 10 = 12`) strips the middle digit, making `x == r / 10` evaluate to `true`.

### 🧩 Algorithm
- **State variables**:
  - `x`: Holds the remaining left half of the original integer.
  - `r`: Reconstructed reversed right half of the original integer.
- **Loop Invariants**:
  - At every step, `r` accumulates the tail digits of `x` in reverse order.
  - The condition `x > r` guarantees the loop stops exactly when `r` has at least as many digits as `x`.
- **Transitions**:
  - $r_{next} = r_{current} \times 10 + (x_{current} \pmod{10})$
  - $x_{next} = \lfloor x_{current} / 10 \rfloor$

### ✅ Why This Works
- **Correctness for Even Lengths**: When the number of digits is even, processing half the digits splits the number into two equal halves. If the original number is a palindrome, the left half `x` and reversed right half `r` will be identical.
- **Correctness for Odd Lengths**: When the number of digits is odd, the middle digit gets appended to `r`. Dividing `r / 10` discards this middle digit, leaving the reversed remaining right half to be compared with `x`.
- **Overflow Prevention**: Since the loop terminates as soon as `r >= x`, `r` can never exceed the magnitude of `x`, making overflow impossible within 32-bit signed integers.

### ⏱️ Complexity
- **Time Complexity:** $\mathcal{O}(\log_{10}(N))$, where $N$ is the input integer `x`. In each iteration, `x` is divided by 10, so the loop runs for half the total number of digits in `x` (i.e., $\frac{1}{2} \log_{10}(N)$ steps).
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. Only a single primitive variable `r` is created, performing all operations in-place.

### 🧠 DSA Pattern
- **Math / Digit Manipulation** (Half-integer reversal)

### ⚠️ Common Mistakes
- **Forgetting the `x % 10 == 0 && x != 0` check**: Without this check, numbers like `10` would cause the loop `x > r` (`10 > 0`) to execute once, resulting in `x = 1` and `r = 0`. Then `x == r / 10` (`1 == 0`) would evaluate correctly, but for numbers like `1000`, missing the early return can cause wrong logic or redundant iterations.
- **Reversing the whole integer**: Reversing all digits of $2^{31}-1$ causes integer overflow in 32-bit signed integers. Reversing only half avoids this completely.

### 🚀 Optimization Notes
- This solution is already fully optimal in terms of both time ($\mathcal{O}(\log_{10}(N))$) and space ($\mathcal{O}(1)$).
- It fulfills the follow-up requirement of solving the problem without converting the integer to a string.
