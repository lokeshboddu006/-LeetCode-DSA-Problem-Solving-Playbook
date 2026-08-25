<h2><a href="https://leetcode.com/problems/smallest-missing-multiple-of-k">Smallest Missing Multiple of K</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given an integer array <code>nums</code> and an integer <code>k</code>, return the <strong>smallest positive multiple</strong> of <code>k</code> that is <strong>missing</strong> from <code>nums</code>.</p>

<p>A <strong>multiple</strong> of <code>k</code> is any positive integer divisible by <code>k</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [8,2,3,4,6], k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">10</span></p>

<p><strong>Explanation:</strong></p>

<p>The multiples of <code>k = 2</code> are 2, 4, 6, 8, 10, 12... and the smallest multiple missing from <code>nums</code> is 10.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,4,7,10,15], k = 5</span></p>

<p><strong>Output:</strong> <span class="example-io">5</span></p>

<p><strong>Explanation:</strong></p>

<p>The multiples of <code>k = 5</code> are 5, 10, 15, 20... and the smallest multiple missing from <code>nums</code> is 5.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 100</code></li>
	<li><code>1 &lt;= k &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code finds the smallest positive multiple of $k$ missing from `nums` by using a custom bitset built from a 2-element `long` array (`long[2]`). 

Since `nums[i]` is at most 100, `n / k` is at most 100. This means we only need to track around 100 possible multiples. A single 64-bit `long` stores bits 0 to 63, and a second `long` stores bits 64 to 127. 

The strategy is:
1. Map each present multiple $(1 \cdot k, 2 \cdot k, \dots)$ to a 0-based bit index ($0, 1, \dots$).
2. Set the corresponding bit in the bitset to `1`.
3. Locate the first `0` bit (the smallest missing index) using bitwise arithmetic and trailing zero counting.
4. Convert that index back into the actual multiple value.

---

### 🔍 Approach

1. **Bitset Initialization**:
   - `x` is defined as `long[] x = {0L, 0L}` providing 128 total bits.

2. **Populating the Bitset**:
   - Iterate through each number `n` in `nums`.
   - Check if `n` is divisible by $k$ (`n % k == 0`).
   - If divisible, calculate the 0-based multiple index: `i = n / k - 1`.
   - Determine which `long` in `x` holds bit `i`:
     - `i >> 6` (equivalent to `i / 64`) chooses index `0` or `1`.
     - `i & 63` (equivalent to `i % 64`) calculates the bit offset inside that `long`.
   - Perform `x[i >> 6] |= 1L << (i & 63)` to set the `i`-th bit to 1.

3. **Locating the First Missing Index**:
   - Check if `x[0] == -1L`. In two's complement 64-bit signed representation, `-1L` has all 64 bits set to `1` (all multiples $1 \cdot k$ through $64 \cdot k$ are present).
   - If `x[0] == -1L`, set `z = 1` to look in `x[1]`; otherwise, set `z = 0` to look in `x[0]`.

4. **Finding the Lowest 0-Bit**:
   - The expression `++x[z]` increments `x[z]` by 1.
   - Adding 1 to a binary integer flips all consecutive trailing `1`s to `0`s and flips the lowest `0` bit to `1`.
   - `++x[z] & -x[z]` isolates this lowest set bit (which corresponds to the lowest `0` bit in the original `x[z]`).
   - `Long.numberOfTrailingZeros(...)` gives the bit offset (0 to 63) of this missing index within `x[z]`.

5. **Reconstructing the Multiple**:
   - Total 0-indexed position = `z * 64 + bit_offset`.
   - The actual multiple is `(total_index + 1) * k`.

---

### 🧩 Algorithm

- **Bitset Marking**:
  $$\text{bit\_index} = \frac{n}{k} - 1$$
  $$\text{x}[\text{bit\_index} \gg 6] \gets \text{x}[\text{bit\_index} \gg 6] \lor (1\text{L} \ll (\text{bit\_index} \land 63))$$

- **Lowest Zero Bit Extraction**:
  $$\text{Isolate lowest set bit of } (X + 1): \quad Y = (X + 1) \mathbin{\&} -(X + 1)$$
  $$\text{Missing bit position inside block } z: \quad m = \text{numberOfTrailingZeros}(Y)$$

- **Result Calculation**:
  $$\text{Result} = ((z \times 64) + m + 1) \times k$$

---

### ✅ Why This Works

- **Range Coverage**: With array length up to 100 and maximum value 100, the maximum index `i` is at most 99, which easily fits inside 128 bits (2 `long` elements).
- **Correct Index Mapping**: The 1st multiple ($1 \cdot k$) maps to bit 0, 2nd multiple ($2 \cdot k$) to bit 1, etc.
- **Bit Arithmetic Logic**:
  - Suppose a `long` has lower bits set up to position $m-1$, and bit $m$ is `0`: `0b...01111`
  - Incrementing it turns it into: `0b...10000`
  - Bitwise AND with its two's complement negate (`val & -val`) isolates the single bit at position $m$.
  - `Long.numberOfTrailingZeros` returns $m$, which is precisely the lowest missing index offset.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of elements in `nums`. We scan `nums` once (up to 100 elements), followed by constant time $\mathcal{O}(1)$ bitwise operations.
- **Space Complexity:** $\mathcal{O}(1)$. Fixed array of 2 `long`s requiring only 16 bytes of additional memory.

---

### 🧠 DSA Pattern

- **Bit Manipulation / Custom Bitset**

---

### ⚠️ Common Mistakes

1. **Bit Shift Overflow**: Using `1` instead of `1L` in `1L << (i & 63)` would perform a 32-bit shift instead of 64-bit, leading to overflow for bit offsets $\ge 32$.
2. **Side Effects in Return Statement**: Modifying `x[z]` inline via `++x[z]` inside the return statement works fine because execution terminates immediately, but in larger contexts mutating array parameters in return statements can introduce side-effect bugs and reduce readability.
3. **1-Based vs 0-Based Alignment**: Forgetting to subtract 1 when inserting (`n / k - 1`) or forgetting to add 1 back when calculating the final answer (`+ 1`) would cause off-by-one errors.

---

### 🚀 Optimization Notes

- The solution is already time-optimal ($\mathcal{O}(N)$) and space-optimal ($\mathcal{O}(1)$).
- **Readability Improvement**: Instead of mutating `x[z]` using `++x[z] & -x[z]`, the lowest zero bit can be found directly without mutation using bitwise NOT:
  `Long.numberOfTrailingZeros(~x[z])`
  This achieves the exact same result cleanly without modifying `x[z]`.
