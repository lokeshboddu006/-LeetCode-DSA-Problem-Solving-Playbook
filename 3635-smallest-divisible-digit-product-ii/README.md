<h2><a href="https://leetcode.com/problems/smallest-divisible-digit-product-ii">Smallest Divisible Digit Product II</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given a string <code>num</code> which represents a <strong>positive</strong> integer, and an integer <code>t</code>.</p>

<p>A number is called <strong>zero-free</strong> if <em>none</em> of its digits are 0.</p>

<p>Return a string representing the <strong>smallest</strong> <strong>zero-free</strong> number greater than or equal to <code>num</code> such that the <strong>product of its digits</strong> is divisible by <code>t</code>. If no such number exists, return <code>&quot;-1&quot;</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">num = &quot;1234&quot;, t = 256</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;1488&quot;</span></p>

<p><strong>Explanation:</strong></p>

<p>The smallest zero-free number that is greater than 1234 and has the product of its digits divisible by 256 is 1488, with the product of its digits equal to 256.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">num = &quot;12355&quot;, t = 50</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;12355&quot;</span></p>

<p><strong>Explanation:</strong></p>

<p>12355 is already zero-free and has the product of its digits divisible by 50, with the product of its digits equal to 150.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">num = &quot;11111&quot;, t = 26</span></p>

<p><strong>Output:</strong> <span class="example-io">&quot;-1&quot;</span></p>

<p><strong>Explanation:</strong></p>

<p>No number greater than 11111 has the product of its digits divisible by 26.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= num.length &lt;= 2 * 10<sup>5</sup></code></li>
	<li><code>num</code> consists only of digits in the range <code>[&#39;0&#39;, &#39;9&#39;]</code>.</li>
	<li><code>num</code> does not contain leading zeros.</li>
	<li><code>1 &lt;= t &lt;= 10<sup>14</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the smallest zero-free integer greater than or equal to `n` whose digit product is divisible by `t`.

Because the digits of any zero-free number are strictly between `1` and `9`, their prime factors can only be `2`, `3`, `5`, and `7`. 
1. If `t` has any prime factor greater than `7`, it's impossible to form such a digit product, so we return `"-1"`.
2. Otherwise, `t` can be fully decomposed into powers of prime factors: $2^a \cdot 3^b \cdot 5^c \cdot 7^d$.
3. Any candidate number must provide at least $a$ factors of 2, $b$ factors of 3, $c$ factors of 5, and $d$ factors of 7 through its digits.

To find the smallest number $\ge n$:
- We first eliminate any `'0'` digits in `n` by turning the first zero and all subsequent digits into `'1'`s.
- We check if `n` itself already satisfies all factor requirements.
- If not, we try to keep the longest possible matching prefix with `n`, increment the digit at index `i`, and check if the remaining `m` digits on the right can supply all missing prime factors.
- If no valid number of length `l` can be formed, we construct the smallest number of length `l + 1` (or larger if required).

---

### 🔍 Approach

1. **Prime Factorization of `t`**:
   - Divide out factors of `2`, `3`, `5`, and `7` from `t`, storing their counts in `a`, `b`, `c`, and `d`.
   - If `x > 1` remains after removing these factors, return `"-1"`.

2. **Zero Handling in `n`**:
   - Convert `n` into an integer array `v`.
   - Locate the first zero at index `z`.
   - If a zero exists (`z != -1`), set `v[z] = 1` and set all subsequent digits `v[i] = 1`. This makes `v` a zero-free number $\ge n$.

3. **Prefix Factor Counting**:
   - Compute prefix sum arrays `p2`, `p3`, `p5`, and `p7` of length `l + 1` to track cumulative prime factor counts provided by `v[0...i-1]`.
   - For a digit `k`:
     - Factor 2: `2` and `6` give $1$, `4` gives $2$, `8` gives $3$.
     - Factor 3: `3` and `6` give $1$, `9` gives $2$.
     - Factor 5: `5` gives $1$.
     - Factor 7: `7` gives $1$.

4. **Checking the Initial Number**:
   - If `n` contained no zeroes (`z == -1`) and `p2[l] >= a`, `p3[l] >= b`, `p5[l] >= c`, `p7[l] >= d`, return `n` directly.

5. **Suffix Generation Search (Right-to-Left)**:
   - Loop `i` backwards from `l - 1` down to `0`.
   - Determine the minimum digit `start` to try at position `i`:
     - If `z != -1` and `i >= z`, position `i` was modified during zero elimination, so `start = 1`.
     - Otherwise, `start = v[i] + 1` to ensure the generated number is strictly greater than `n` at position `i`.
   - Iterate candidate digit `k` from `start` to `9`:
     - Subtract the factor contributions of prefix `0...i-1` and digit `k` from `a, b, c, d` to get remaining needed factors `r2, r3, r5, r7`.
     - Calculate the minimum number of digits `req` needed to supply these remaining factors.
     - If `req <= m` (where `m = l - 1 - i` is the remaining positions available), set `v[i] = k`, call helper function `g()` to construct the optimal suffix of length `m`, concatenate prefix `v[0...i]` with suffix `sxf`, and return.

6. **Increasing String Length**:
   - If no valid arrangement of length `l` exists, search for the smallest length `m >= l + 1` that can accommodate the required factor count `req` calculated from `a, b, c, d`, and build it using `g()`.

7. **Helper Method `g(a, b, c, d, m)`**:
   - Minimizes total digits required by greedily combining factors into largest possible digits:
     - Combine 2s into `8`s (`a / 3`), 3s into `9`s (`b / 2`).
     - If one factor of 2 and one factor of 3 remain, combine them into `6`.
     - Combine remaining 2s into `4`s (`a / 2`).
     - Single remaining factors become `2`, `3`, `5`, and `7`.
   - Fills remaining positions `m - tot` with `'1'`s.
   - Appends digits in sorted order (`1` through `9`) to build the lexicographically smallest string.

---

### 🧩 Algorithm

#### Factor Counting & Minimum Digits Needed (`req` calculation)
Given remaining required factor counts `r2, r3, r5, r7`:
1. `req = r7 + r5 + (r3 + 1) / 2` (Each 7 and 5 takes 1 digit; two 3s combine into a `9`).
2. If `r3` is odd, one `3` combines with a `2` to form a `6`, consuming one factor of 2:
   $$\text{rem2} = \max(0, r2 - (r3 \pmod 2))$$
3. Remaining 2s combine into `8`s (3 factors per digit):
   $$\text{req} = \text{req} + \frac{\text{rem2} + 2}{3}$$

#### Greedily Constructing Minimal Suffix (`g` function)
- $e_8 = \lfloor a / 3 \rfloor, \quad a \leftarrow a \pmod 3$
- $e_9 = \lfloor b / 2 \rfloor, \quad b \leftarrow b \pmod 2$
- If $a > 0$ and $b > 0$: $e_6 = 1, \quad a \leftarrow a - 1, \quad b \leftarrow b - 1$
- $e_4 = \lfloor a / 2 \rfloor, \quad a \leftarrow a \pmod 2$
- $e_2 = a, \quad e_3 = b, \quad e_5 = c, \quad e_7 = d$
- $e_1 = m - (e_2 + e_3 + e_4 + e_5 + e_6 + e_7 + e_8 + e_9)$
- Construct string: $e_1 \times '1' + e_2 \times '2' + \dots + e_9 \times '9'$.

---

### ✅ Why This Works

- **Correctness of Prefix Matching**: Iterating `i` from right to left ensures that we find the longest common prefix shared with `n`. The first valid candidate digit `k` at the highest possible index `i` guarantees the overall smallest number greater than `n`.
- **Lexicographical Minimality of Suffix**: The helper function `g()` arranges digits in non-decreasing order (`1, 2, 3, 4, 5, 6, 7, 8, 9`), which inherently produces the smallest possible value for a given multiset of digits.
- **Validity of Factor Reduction**: Combining prime factors into digits ($2 \times 2 \times 2 \to 8$, $3 \times 3 \to 9$, $2 \times 3 \to 6$, $2 \times 2 \to 4$) minimizes digit count while preserving the product divisibility by $t$.

---

### ⏱️ Complexity

- **Time Complexity**: 
  - Factoring `t`: $O(\log t)$
  - Zero removal and prefix sums: $O(L)$ where $L$ is the length of string `n`.
  - Backtracking loop: Runs at most $L$ times. For each index $i$, it tests up to 9 digits, doing $O(1)$ arithmetic checks.
  - Suffix construction `g()`: Takes $O(L)$ time to construct the result string.
  - Overall Time Complexity: $\mathcal{O}(L + \log t)$, which is optimal and comfortably handles $L \le 2 \cdot 10^5$.

- **Space Complexity**:
  - Array `v` of size $L$.
  - Four prefix sum arrays (`p2, p3, p5, p7`) of size $L + 1$.
  - String construction buffers of size $O(L)$.
  - Overall Space Complexity: $\mathcal{O}(L)$.

---

### 🧠 DSA Pattern

- **Greedy / Digits Construction**: Maximizing factor density per digit and ordering digits in ascending order.
- **Prefix Sums**: Tracking factor counts contributed by the prefix of the number.
- **Number Theory / Prime Factorization**: Decomposing $t$ into factors $2, 3, 5, 7$.

---

### ⚠️ Common Mistakes

1. **Ignoring Factors $> 7$**: Forgetting that digits $1-9$ can only provide prime factors $2, 3, 5, 7$. If $t$ has factors like $11$ or $13$, no solution exists.
2. **Handling Zeroes Incorrectly**: Replacing a zero at index `z` with `'1'` requires setting all subsequent digits to `'1'` as well, because replacing a zero inherently increases the number beyond `n` at index `z`.
3. **Suboptimal Factor Combination Order**: Not combining a remaining factor of 2 and 3 into a `6` before turning remaining 2s into `4`s, which could waste digit positions.

---

### 🚀 Optimization Notes

1. **Dead / Redundant Assignment in Loop**:
   Lines 44 and 45–48 calculate `start` twice:
   ```java
   int start = (z != -1 && i >= z) ? 1 : ...; // Line 44 (overwritten immediately)
   if (z == -1 && i == l - 1) start = v[i] + 1;
   else if (z != -1 && i > z) start = 1;
   else if (z != -1 && i == z) start = 1;
   else start = v[i] + 1;
   ```
   Lines 46 and 47 are identical. This logic can be simplified to:
   ```java
   int start = (z != -1 && i >= z) ? 1 : v[i] + 1;
   ```

2. **Unnecessary `while(true)` Loop for Length Expansion**:
   In the fallback loop for length $> L$:
   ```java
   int m = l + 1;
   while (true) {
       int req = d + c + (b + 1) / 2;
       ...
       if (req <= m) return g(a, b, c, d, m);
       m++;
   }
   ```
   `req` is constant and independent of `m`. Instead of looping `m++`, we could directly compute `m = Math.max(l + 1, req)` and call `g(a, b, c, d, m)` once.

3. **Prefix Sum Space**:
   Instead of allocating 4 separate arrays of size $L + 1$, prefix counts can be calculated on the fly or accumulated in a single pass while traversing backwards.
