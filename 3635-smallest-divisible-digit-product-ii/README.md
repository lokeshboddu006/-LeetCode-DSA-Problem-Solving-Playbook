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

The problem requires finding the smallest zero-free integer greater than or equal to `n` whose digit product is divisible by `t`. 

Since the digits of any zero-free number are between `1` and `9`, their prime factors can only be `2`, `3`, `5`, and `7`. Therefore:
1. If `t` contains any prime factor strictly greater than `7` (such as `11`, `13`, etc.), it is impossible for any digit product to be divisible by `t`, so we immediately return `"-1"`.
2. Otherwise, `t` can be factored into $2^a \cdot 3^b \cdot 5^c \cdot 7^d$.
3. To make a number's digit product divisible by `t`, the multiset of its digits must collectively contribute at least $a$ factors of $2$, $b$ factors of $3$, $c$ factors of $5$, and $d$ factors of $7$.

To find the smallest valid number $\ge n$:
- If $n$ contains a `'0'`, it is invalid, so we fix the first `'0'` and subsequent digits to `'1'` to bring it to a valid zero-free baseline.
- We check if $n$ itself already satisfies all factor requirements.
- If not, we try to keep the longest possible prefix of $n$ unchanged, increment a digit at index $i$, and check if the remaining $m$ digits can satisfy all required missing prime factors.
- If no solution exists with length equal to $n$'s length, the answer must have a greater length ($L + 1$).

---

### 🔍 Approach

1. **Prime Factorization of $t$**:
   - Divide out factors of `2`, `3`, `5`, and `7` from `t`, storing their counts in `a`, `b`, `c`, and `d`.
   - If `x > 1` after removing factors of 2, 3, 5, 7, return `"-1"`.

2. **Handling Zeros in `n`**:
   - Convert `n` to an integer array `v`.
   - Find the first occurrence of `0` at index `z`. If found, set `v[z] = 1` and all subsequent `v[i] = 1` (since digits cannot be `0`).

3. **Prefix Prime Factor Counts**:
   - Construct prefix sum arrays `p2`, `p3`, `p5`, `p7` of length `l + 1` to store cumulative counts of prime factors $2, 3, 5, 7$ up to index $i$.
   - Digits contribute factors as follows:
     - `2`: one 2; `4`: two 2s; `8`: three 2s
     - `3`: one 3; `9`: two 3s
     - `6`: one 2 and one 3
     - `5`: one 5; `7`: one 7

4. **Check Original Number**:
   - If $n$ had no zeros (`z == -1`) and its total factors satisfy `p2[l] >= a`, `p3[l] >= b`, `p5[l] >= c`, and `p7[l] >= d`, `n` is directly returned.

5. **Right-to-Left Longest Prefix Matching**:
   - Iterate backwards from index `i = l - 1` down to `0`.
   - Determine the starting digit `start` for position `i`:
     - If `z == -1` and `i == l - 1`, we must strictly increase `v[i]` (`start = v[i] + 1`).
     - If `z != -1` and `i >= z`, position `i` was already modified due to zero-elimination, so `start = 1`.
     - Otherwise, `start = v[i] + 1`.
   - Try candidate digits $k$ from `start` to `9`:
     - Calculate remaining required factor counts `r2, r3, r5, r7` after considering prefix `0...i-1` and candidate digit $k$.
     - Calculate the minimum number of digits `req` needed to supply these remaining factors.
     - If `req <= m` (where `m = l - 1 - i` is the remaining space), place $k$ at index $i$, build the optimal tail string using helper function `g()`, append it to prefix `v[0...i]`, and return the result.

6. **Increasing Length**:
   - If no valid number of length `l` can be formed, increment
