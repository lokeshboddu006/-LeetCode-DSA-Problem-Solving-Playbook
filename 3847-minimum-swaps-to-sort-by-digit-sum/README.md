<h2><a href="https://leetcode.com/problems/minimum-swaps-to-sort-by-digit-sum">Minimum Swaps to Sort by Digit Sum</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an array <code>nums</code> of <strong>distinct</strong> positive integers. You need to sort the array in <strong>increasing</strong> order based on the sum of the digits of each number. If two numbers have the same digit sum, the <strong>smaller</strong> number appears first in the sorted order.</p>

<p>Return the <strong>minimum</strong> number of swaps required to rearrange <code>nums</code> into this sorted order.</p>

<p>A <strong>swap</strong> is defined as exchanging the values at two distinct positions in the array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [37,100]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Compute the digit sum for each integer: <code>[3 + 7 = 10, 1 + 0 + 0 = 1] &rarr; [10, 1]</code></li>
	<li>Sort the integers based on digit sum: <code>[100, 37]</code>. Swap <code>37</code> with <code>100</code> to obtain the sorted order.</li>
	<li>Thus, the minimum number of swaps required to rearrange <code>nums</code> is 1.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [22,14,33,7]</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Compute the digit sum for each integer: <code>[2 + 2 = 4, 1 + 4 = 5, 3 + 3 = 6, 7 = 7] &rarr; [4, 5, 6, 7]</code></li>
	<li>Sort the integers based on digit sum: <code>[22, 14, 33, 7]</code>. The array is already sorted.</li>
	<li>Thus, the minimum number of swaps required to rearrange <code>nums</code> is 0.</li>
</ul>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [18,43,34,16]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Compute the digit sum for each integer: <code>[1 + 8 = 9, 4 + 3 = 7, 3 + 4 = 7, 1 + 6 = 7] &rarr; [9, 7, 7, 7]</code></li>
	<li>Sort the integers based on digit sum: <code>[16, 34, 43, 18]</code>. Swap <code>18</code> with <code>16</code>, and swap <code>43</code> with <code>34</code> to obtain the sorted order.</li>
	<li>Thus, the minimum number of swaps required to rearrange <code>nums</code> is 2.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>nums</code> consists of <strong>distinct</strong> positive integers.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To find the minimum number of swaps needed to sort `nums` by digit sum (with original value as a tie-breaker), we need to determine each element's target index in the fully sorted array. Once every element is mapped to its target sorted index, the problem reduces to finding the minimum swaps required to restore a permutation to its natural order—a classic permutation cycle decomposition problem.

To avoid creating custom classes or pair comparators, your implementation encodes both sorting criteria—the digit sum and the original integer—into a single 64-bit primitive `long`. Bit-shifting the digit sum into the upper 32 bits ensures that standard numeric sorting automatically sorts by digit sum first, and by the original number second.

### 🔍 Approach

1. **Key Generation & Bit Packing**:
   - Iterate through `nums` and compute the sum of digits for each integer using the helper method `digitSum(x)`.
   - Bit-pack the digit sum into the upper 32 bits and the original number into the lower 32 bits of a `long`:
     `keyed[i] = (long) digitSum(nums[i]) << 32 | nums[i]`

2. **Sorting & Rank Allocation**:
   - Sort the `keyed` array using `Arrays.sort(keyed)`.
   - Cast the lower 32 bits back to an `int` (`(int) keyed[k]`) to recover the original number, and record its sorted index `k` in the `rank` `HashMap`.

3. **Constructing the `home` Permutation**:
   - Create an array `home` of size `n`, where `home[i]` represents the target sorted index of the element currently located at index `i` in `nums`.

4. **Permutation Cycle Decomposition**:
   - Iterate through each index `i` from `0` to `n - 1`.
   - While the element at index `i` is not at its correct sorted position (`home[i] != i`):
     - Identify its target index `j = home[i]`.
     - Swap the target indices in the `home` array so that the element at `i` is placed into its correct position `j` (`home[j] = j`), and bring `home[j]`'s previous value to `home[i]`.
     - Increment `swaps`.

### 🧩 Algorithm

1. **Bit Packing Strategy**:
   $$\text{keyed}[i] = (\text{digitSum}(nums[i]) \ll 32) \mid nums[i]$$

2. **Cycle Resolution (Permutation Swaps)**:
   - Any permutation of distinct elements can be decomposed into disjoint cyclic paths.
   - For a cycle of length $L$, the minimum number of swaps to place all elements in their correct indices is $L - 1$.
   - The loop `while (home[i] != i)` iteratively shortens the cycle by placing one element in its correct position per swap until `home[i] == i`.

### ✅ Why This Works

- **Correct Ordering**: Shifting `digitSum` by 32 bits moves it to the most significant bits of a 64-bit integer. Because `nums[i] <= 10^9 < 2^{31}-1`, `nums[i]` fits entirely within the lower 32 bits. Comparing two `long` values compares the upper 32 bits first (digit sum) and uses the lower 32 bits (original value) as a tie-breaker.
- **Minimal Swaps via Cycle Decomposition**: Each swap in the cycle resolution loop places at least one element into its final destination index `j` (`home[j] = j`). Since an array of $N$ elements with $C$ disjoint cycles requires exactly $N - C$ total swaps to be sorted, resolving each cycle greedy-style guarantees the minimum total swaps.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N \log N)$
  - Computing digit sums takes $\mathcal{O}(N \cdot \log_{10}(\text{max\_val})) = \mathcal{O}(N)$ operations because each number has at most 10 digits.
  - Sorting the primitive array `keyed` of length $N$ takes $\mathcal{O}(N \log N)$ time.
  - Building `rank` and `home` takes $\mathcal{O}(N)$ average time.
  - The cycle decomposition loop visits each index at most a constant number of times and performs at most $N - 1$ swaps total, running in $\mathcal{O}(N)$ time.
  - Overall Time Complexity: **$\mathcal{O}(N \log N)$** (dominated by sorting).

- **Space Complexity**: $\mathcal{O}(N)$
  - `keyed` array requires $\mathcal{O}(N)$ space for $N$ `long` elements.
  - `rank` hash map requires $\mathcal{O}(N)$ space for $N$ entries.
  - `home` array requires $\mathcal{O}(N)$ space.
  - Overall Auxiliary Space Complexity: **$\mathcal{O}(N)$**.

### 🧠 DSA Pattern

- **Permutation Cycle Decomposition / Cycle Sort**
- **Bit Manipulation (Bit Packing 2 values into 1 `long`)**
- **Sorting and Hashing**

### ⚠️ Common Mistakes

- **Type Casting in Bit Shifts**: Forgetting to cast `digitSum(nums[i])` to `long` before shifting by 32 bits (`(long) digitSum(nums[i]) << 32`) would cause standard 32-bit integer bitwise shift behavior, resulting in arithmetic overflow or unexpected zeroes.
- **Incorrect Cycle Pointer Updating**: When resolving cycles in place, updating `home[i]` before reading `home[j]` or failing to mark `home[j] = j` would result in an infinite `while` loop.

### 🚀 Optimization Notes

- **HashMap Overhead**: Using `HashMap<Integer, Integer>` introduces object boxing (`Integer`) and hash table lookup overhead. Since elements are distinct, storing indices directly in a pair/2D array or sorting an index array `Integer[] indices` directly using a custom comparator could avoid hashing overhead.
- **Bit Packing Efficiency**: Using primitive `long` bit packing is a clever technique that avoids object allocation overhead for custom `Pair` objects during the sorting phase.
