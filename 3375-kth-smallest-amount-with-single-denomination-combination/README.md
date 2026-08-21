<h2><a href="https://leetcode.com/problems/kth-smallest-amount-with-single-denomination-combination">Kth Smallest Amount With Single Denomination Combination</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given an integer array <code>coins</code> representing coins of different denominations and an integer <code>k</code>.</p>

<p>You have an infinite number of coins of each denomination. However, you are <strong>not allowed</strong> to combine coins of different denominations.</p>

<p>Return the <code>k<sup>th</sup></code> <strong>smallest</strong> amount that can be made using these coins.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block" style="
    border-color: var(--border-tertiary);
    border-left-width: 2px;
    color: var(--text-secondary);
    font-size: .875rem;
    margin-bottom: 1rem;
    margin-top: 1rem;
    overflow: visible;
    padding-left: 1rem;
">
<p><strong>Input:</strong> <span class="example-io" style="
    font-family: Menlo,sans-serif;
    font-size: 0.85rem;
">coins = [3,6,9], k = 3</span></p>

<p><strong>Output:</strong> <span class="example-io" style="
    font-family: Menlo,sans-serif;
    font-size: 0.85rem;
"> 9</span></p>

<p><strong>Explanation:</strong> The given coins can make the following amounts:<br />
Coin 3 produces multiples of 3: 3, 6, 9, 12, 15, etc.<br />
Coin 6 produces multiples of 6: 6, 12, 18, 24, etc.<br />
Coin 9 produces multiples of 9: 9, 18, 27, 36, etc.<br />
All of the coins combined produce: 3, 6, <u><strong>9</strong></u>, 12, 15, etc.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block" style="
    border-color: var(--border-tertiary);
    border-left-width: 2px;
    color: var(--text-secondary);
    font-size: .875rem;
    margin-bottom: 1rem;
    margin-top: 1rem;
    overflow: visible;
    padding-left: 1rem;
">
<p><strong>Input:</strong><span class="example-io" style="
    font-family: Menlo,sans-serif;
    font-size: 0.85rem;
"> coins = [5,2], k = 7</span></p>

<p><strong>Output:</strong><span class="example-io" style="
    font-family: Menlo,sans-serif;
    font-size: 0.85rem;
"> 12 </span></p>

<p><strong>Explanation:</strong> The given coins can make the following amounts:<br />
Coin 5 produces multiples of 5: 5, 10, 15, 20, etc.<br />
Coin 2 produces multiples of 2: 2, 4, 6, 8, 10, 12, etc.<br />
All of the coins combined produce: 2, 4, 5, 6, 8, 10, <u><strong>12</strong></u>, 14, 15, etc.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= coins.length &lt;= 15</code></li>
	<li><code>1 &lt;= coins[i] &lt;= 25</code></li>
	<li><code>1 &lt;= k &lt;= 2 * 10<sup>9</sup></code></li>
	<li><code>coins</code> contains pairwise distinct integers.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the $k$-th smallest amount that can be formed using multiples of single coin denominations. 

If we choose a target amount $m$, we can ask: *"How many distinct multiples of any coin in `coins` are less than or equal to $m$?"*

Because this count function is **monotonically increasing** (as $m$ increases, the number of valid multiples up to $m$ never decreases), we can binary search for the target value $m$. 

To compute the exact count of unique multiples up to $m$, we use the **Inclusion-Exclusion Principle**:
- Adding multiples of individual coins overcounts values that are multiples of multiple coins (e.g., both 2 and 3 count 6).
- Subtracting multiples of LCMs of pairs of coins removes the double-counting, but subtracts common multiples of triples too many times.
- Alternating signs based on subset sizes (odd size = add, even size = subtract) gives the exact count of distinct multiples $\le m$.

### 🔍 Approach

1. **Find Upper Bound & Define Range**:
   - Compute `minCoin`, the smallest denomination in `coins`.
   - The $k$-th smallest valid amount cannot exceed `minCoin * k` because using only the smallest coin gives $k$ valid multiples up to `minCoin * k`.
   - Set binary search bounds: `low = 1`, `high = minCoin * k`.

2. **Binary Search on the Answer**:
   - Compute `mid = low + (high - low) / 2`.
   - Call `countMultiples(coins, mid)` to determine how many unique coin multiples exist in the range $[1, \text{mid}]$.
   - If `countMultiples >= k`, `mid` could be our answer, so store `ans = mid` and search the lower half (`high = mid - 1`).
   - Otherwise, `mid` is too small, so search the upper half (`low = mid + 1`).

3. **Inclusion-Exclusion via Bitmasking (`countMultiples`)**:
   - Iterate through all $2^n - 1$ non-empty subsets of `coins` using bitmasks from `mask = 1` to `(1 << n) - 1`.
   - For each bitmask:
     - Count set bits (`bitCount`) to track subset size.
     - Calculate the Least Common Multiple (`currentLcm`) of all coins present in the bitmask using `lcm(a, b) = (a / gcd(a, b)) * b`.
     - Early exit: If `currentLcm` exceeds $m$, break early since $m / \text{currentLcm}$ would be $0$.
     - If `currentLcm <= m`:
       - If `bitCount` is odd, add `m / currentLcm` to `count`.
       - If `bitCount` is even, subtract `m / currentLcm` from `count`.

4. **GCD and LCM Helpers**:
   - `gcd(a, b)` uses the Euclidean algorithm recursively.
   - `lcm(a, b)` divides `a` by `gcd(a, b)` first before multiplying by `b` to prevent premature integer overflow.

### 🧩 Algorithm

1. **Upper Bound Selection**:
   $$ \text{high} = \min(\text{coins}) \times k $$

2. **Inclusion-Exclusion Formula**:
   $$\text{Count}(m) = \sum_{S \subseteq \text{coins}, S \neq \emptyset} (-1)^{|S| - 1} \left\lfloor \frac{m}{\text{LCM}(S)} \right\rfloor$$

3. **Binary Search Predicate**:
   Find the minimum $m$ such that $\text{Count}(m) \ge k$.

### ✅ Why This Works

- **Monotonicity**: The number of valid multiples $\le m$ strictly increases or stays the same as $m$ grows. This monotonicity guarantees that binary search will correctly converge on the smallest $m$ where the count reaches at least $k$.
- **Correct Counting via Inclusion-Exclusion**: Any integer $x \le m$ that is a multiple of at least one coin in `coins` is counted exactly once by the inclusion-exclusion formula, accurately resolving overlapping multiples.
- **Valid Answer Guarantee**: The smallest value $m$ satisfying $\text{Count}(m) \ge k$ is guaranteed to be an actual multiple of at least one coin (if it were not a multiple of any coin, $\text{Count}(m)$ would equal $\text{Count}(m-1)$, so binary search would have continued searching lower).

### ⏱️ Complexity

- **Time Complexity**: 
  - $N = \text{coins.length} \le 15$.
  - The binary search takes $O(\log(\min(\text{coins}) \cdot k))$ iterations. Given $k \le 2 \cdot 10^9$ and $\min(\text{coins}) \le 25$, the binary search runs at most $\approx 46$ iterations.
  - In each iteration, `countMultiples` iterates over $2^N - 1$ bitmasks. For each mask, computing LCM of up to $N$ elements takes $O(N \log(\max(\text{coins})))$.
  - **Overall Time Complexity**: $O(2^N \cdot N \cdot \log(\max(\text{coins})) \cdot \log(\min(\text{coins}) \cdot k))$. With $N \le 15$, $2^{15} = 32,768$, making this extremely fast and well within execution limits.

- **Space Complexity**:
  - $O(1)$ auxiliary space beyond the logarithmic recursion stack for `gcd`.

### 🧠 DSA Pattern

- **Binary Search on Answer**
- **Inclusion-Exclusion Principle (Bitmasking)**
- **Number Theory (GCD / LCM)**

### ⚠️ Common Mistakes

1. **Integer Overflow in LCM**:
   - Writing `(a * b) / gcd(a, b)` instead of `(a / gcd(a, b)) * b` causes immediate overflow for larger values of $a$ and $b$.
2. **Missing Overflow Prevention during Subset LCM**:
   - Accumulating `currentLcm` without checking if it exceeds $m$ can overflow `long`. The guard `if (currentLcm > m) break;` is critical for both safety and speed.
3. **Tight Upper Bound Selection**:
   - Setting `high` too small (e.g., just $k$) would cause binary search to fail. Setting `high = minCoin * k` guarantees that at least $k$ multiples exist within the search range.

### 🚀 Optimization Notes

- **Early Break in Mask Loop**: Breaking out of the inner bitwise loop when `currentLcm > m` avoids unnecessary GCD/LCM computations and guards against `long` overflow.
- **Division before Multiplication**: Doing `(a / gcd(a, b)) * b` prevents intermediate numeric overflow when computing LCM.
