<h2><a href="https://leetcode.com/problems/number-of-sets-of-k-non-overlapping-line-segments">Number of Sets of K Non-Overlapping Line Segments</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given <code>n</code> points on a 1-D plane, where the <code>i<sup>th</sup></code> point (from <code>0</code> to <code>n-1</code>) is at <code>x = i</code>, find the number of ways we can draw <strong>exactly</strong> <code>k</code> <strong>non-overlapping</strong> line segments such that each segment covers two or more points. The endpoints of each segment must have <strong>integral coordinates</strong>. The <code>k</code> line segments <strong>do not</strong> have to cover all <code>n</code> points, and they are <strong>allowed</strong> to share endpoints.</p>

<p>Return <em>the number of ways we can draw </em><code>k</code><em> non-overlapping line segments</em><em>.</em> Since this number can be huge, return it <strong>modulo</strong> <code>10<sup>9</sup> + 7</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/07/ex1.png" style="width: 179px; height: 222px;" />
<pre>
<strong>Input:</strong> n = 4, k = 2
<strong>Output:</strong> 5
<strong>Explanation:</strong> The two line segments are shown in red and blue.
The image above shows the 5 different ways {(0,2),(2,3)}, {(0,1),(1,3)}, {(0,1),(2,3)}, {(1,2),(2,3)}, {(0,1),(1,2)}.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> n = 3, k = 1
<strong>Output:</strong> 3
<strong>Explanation:</strong> The 3 ways are {(0,1)}, {(0,2)}, {(1,2)}.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> n = 30, k = 7
<strong>Output:</strong> 796297179
<strong>Explanation:</strong> The total number of possible ways to draw 7 line segments is 3796297200. Taking this number modulo 10<sup>9</sup> + 7 gives us 796297179.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= n &lt;= 1000</code></li>
	<li><code>1 &lt;= k &lt;= n-1</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Instead of using dynamic programming to build solutions step-by-step, this implementation leverages a **combinatorial reduction**. 

To select $k$ non-overlapping line segments from $n$ points (where adjacent segments are allowed to share endpoints):
1. Each segment needs $2$ endpoints (a start and an end), so $k$ segments require $2k$ endpoints in total.
2. If segments were strictly forbidden from sharing endpoints, we would simply pick $2k$ distinct points out of $n$, which is $\binom{n}{2k}$.
3. However, segments **can** share endpoints. Using a transformation equivalent to the *Stars and Bars* principle, allowing $k-1$ shared boundaries adds $k-1$ virtual positions to our choice pool.

Thus, the total number of valid segment configurations is exactly equal to choosing $2k$ points out of $(n + k - 1)$ positions:
$$\binom{n + k - 1}{2k}$$

Your code computes this combination $\binom{N}{R}$ modulo $10^9 + 7$ directly in $O(R)$ time using modular arithmetic and Fermat's Little Theorem.

---

### 🔍 Approach

1. **Parameter Transformation**:
   - Calculate total choose parameter: `N = n + k - 1L`.
   - Calculate items to choose: `R = 2L * k`.
2. **Symmetry Optimization**:
   - Apply the symmetry property of combinations, $\binom{N}{R} = \binom{N}{N - R}$, by taking `R = Math.min(R, N - R)`. This minimizes the number of loop iterations.
3. **Product Accumulation**:
   - Initialize `numerator = 1` and `denominator = 1`.
   - Loop `i` from `1` to `R`:
     - Accumulate numerator terms: $(N - R + 1) \times (N - R + 2) \times \dots \times N$ modulo $10^9 + 7$.
     - Accumulate denominator terms: $1 \times 2 \times \dots \times R$ modulo $10^9 + 7$.
4. **Modular Division via Fermat's Little Theorem**:
   - Since $MOD = 10^9 + 7$ is prime, division by `denominator` modulo $MOD$ is equivalent to multiplying by `denominator`$^{MOD - 2} \pmod{MOD}$.
   - Compute `inverseDenominator = modPow(denominator, MOD - 2)` using binary exponentiation (`modPow`).
5. **Final Result**:
   - Multiply `numerator` by `inverseDenominator` modulo $10^9 + 7$ and cast to `int`.

---

### 🧩 Algorithm

1. **Combinatorial Representation**:
   $$\text{Result} = \binom{n + k - 1}{2k} \pmod{10^9 + 7}$$

2. **Binary Exponentiation (`modPow`)**:
   - Computes $(base^{exp}) \pmod{MOD}$ in $O(\log exp)$ time by squaring the base and halving the exponent.

3. **Fermat's Little Theorem (Modular Inverse)**:
   $$\frac{A}{B} \pmod M \equiv A \times B^{M-2} \pmod M \quad \text{when } M \text{ is prime}$$

---

### ✅ Why This Works

- **Combinatorial Correctness**: Mapping the $k$ segments (with potential shared endpoints) to picking $2k$ boundaries from $n + k - 1$ points uniquely accounts for all valid line segment arrangements.
- **Arithmetic Safety**: 
  - Using `long` variables prevents overflow before applying modulo operations during multiplications.
  - Applying Fermat's Little Theorem works because $MOD = 10^9 + 7$ is prime and $1 \le denominator < MOD$, guaranteeing that `denominator` and $MOD$ are coprime.

---

### ⏱️ Complexity

- **Time Complexity**: $O(\min(k, n - k) + \log(\text{MOD}))$
  - The loop runs $R \le \min(2k, n - k - 1)$ times, which is bounded by $O(n)$.
  - `modPow` computes $(10^9 + 7 - 2)$ in $O(\log(\text{MOD})) \approx 30$ steps.
  - Overall time complexity is $O(n)$, drastically faster than standard $O(n \cdot k)$ dynamic programming solutions.

- **Space Complexity**: $O(1)$ auxiliary space.
  - Only a few primitive `long` variables (`N`, `R`, `numerator`, `denominator`, `inverseDenominator`) are used.

---

### 🧠 DSA Pattern

- **Math / Combinatorics** (Combination $\binom{N}{R}$)
- **Modular Arithmetic** (Modular Inverse via Fermat's Little Theorem)
- **Bit Manipulation / Binary Exponentiation** (Exponentiation by Squaring in `modPow`)

---

### ⚠️ Common Mistakes

1. **Standard Division under Modulo**:
   Attempting `numerator / denominator % MOD` directly without using modular inverse would lead to incorrect results because standard integer division does not preserve equivalence under modular arithmetic.

2. **Integer Overflow**:
   Performing `numerator * (N - R + i)` without casting intermediate variables to `long` or taking `% MOD` at each step would cause integer overflow before modulo is applied.

---

### 🚀 Optimization Notes

- **Optimal Time & Space**: This solution is mathematically optimal, running in $O(n)$ time and $O(1)$ space instead of allocating an $O(n \cdot k)$ dynamic programming matrix.
- **Symmetry Trick**: The line `R = Math.min(R, N - R)` effectively cuts the loop iterations in half when $2k > \frac{N}{2}$.
