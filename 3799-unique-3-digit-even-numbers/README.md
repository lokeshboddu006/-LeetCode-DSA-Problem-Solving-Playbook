<h2><a href="https://leetcode.com/problems/unique-3-digit-even-numbers">Unique 3-Digit Even Numbers</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an array of digits called <code>digits</code>. Your task is to determine the number of <strong>distinct</strong> three-digit even numbers that can be formed using these digits.</p>

<p><strong>Note</strong>: Each <em>copy</em> of a digit can only be used <strong>once per number</strong>, and there may <strong>not</strong> be leading zeros.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [1,2,3,4]</span></p>

<p><strong>Output:</strong> <span class="example-io">12</span></p>

<p><strong>Explanation:</strong> The 12 distinct 3-digit even numbers that can be formed are 124, 132, 134, 142, 214, 234, 312, 314, 324, 342, 412, and 432. Note that 222 cannot be formed because there is only 1 copy of the digit 2.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [0,2,2]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong> The only 3-digit even numbers that can be formed are 202 and 220. Note that the digit 2 can be used twice because it appears twice in the array.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [6,6,6]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong> Only 666 can be formed.</p>
</div>

<p><strong class="example">Example 4:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [1,3,5]</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong> No even 3-digit numbers can be formed.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>3 &lt;= digits.length &lt;= 10</code></li>
	<li><code>0 &lt;= digits[i] &lt;= 9</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Given the small array size constraint ($3 \le n \le 10$), you can form every possible 3-digit number by picking three distinct indices from the array `digits`. 

The key observations implemented in your solution are:
1. Three distinct positions in the array ($i, j, k$) represent the hundreds digit, tens digit, and units digit respectively.
2. A valid 3-digit even number requires:
   - The hundreds digit (`digits[i]`) cannot be zero (`digits[i] != 0`).
   - The units digit (`digits[k]`) must be even (`digits[k] % 2 == 0`).
3. To avoid counting duplicate numbers formed by identical digit values at different array positions, you insert each valid constructed number into a `HashSet`. The set automatically keeps only unique values.

---

### 🔍 Approach

1. **Storage Setup**: You initialize a `HashSet<Integer>` (named `map`) to keep track of all unique valid 3-digit even numbers encountered.
2. **Triple Nested Loop**:
   - Loop `i` from `0` to `n - 1`: selects the element for the hundreds position.
   - Loop `j` from `0` to `n - 1`: selects the element for the tens position.
   - Loop `k` from `0` to `n - 1`: selects the element for the units position.
3. **Distinct Index Check**: You verify `i != j && i != k && j != k` to ensure that the exact same element from the `digits` array is not reused within a single number.
4. **Leading Zero Check**: You verify `digits[i] != 0` so that numbers starting with `0` are excluded.
5. **Parity Check**: You verify `digits[k] % 2 == 0` to ensure the number is even.
6. **Form and Insert Number**: If all conditions pass, you compute `digits[i] * 100 + digits[j] * 10 + digits[k]` and add it to `map`.
7. **Return Count**: Finally, `map.size()` gives the total count of distinct valid 3-digit even numbers.

---

### 🧩 Algorithm

1. Initialize `map = new HashSet<Integer>()` and `n = digits.length`.
2. For each index $i \in [0, n-1]$:
   - For each index $j \in [0, n-1]$:
     - For each index $k \in [0, n-1]$:
       - Check if $i \neq j \land i \neq k \land j \neq k$.
       - Check if $digits[i] \neq 0$.
       - Check if $digits[k] \pmod 2 = 0$.
       - If all conditions are met:
         - Calculate $N = 100 \cdot digits[i] + 10 \cdot digits[j] + digits[k]$.
         - Add $N$ into `map`.
3. Return $|map|$.

---

### ✅ Why This Works

- **Index Uniqueness**: Checking $i \neq j \land i \neq k \land j \neq k$ guarantees that each element copy in `digits` is used at most once per 3-digit number.
- **No Leading Zeros**: Filtering out $digits[i] == 0$ ensures the hundreds digit is in the range $[1, 9]$, guaranteeing a valid 3-digit integer ($\ge 100$).
- **Even Parity**: Filtering for $digits[k] \pmod 2 == 0$ ensures the units digit is even, making the entire 3-digit number even.
- **Uniqueness via Set**: Using a `HashSet` automatically deduplicates identical 3-digit numbers generated from duplicate digit values in `digits`.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n^3)$
  The three nested loops iterate $n \times n \times n = n^3$ times. The operations inside (index checks, arithmetic, and set insertions) run in $\mathcal{O}(1)$ average time. Since $n \le 10$, $n^3 \le 1000$, which executes almost instantaneously.

- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  The `HashSet` stores at most 450 elements (the number of even 3-digit integers between 100 and 998 is bounded). Since the maximum possible size of the set is fixed and small, space complexity is $\mathcal{O}(1)$.

---

### 🧠 DSA Pattern

- **Brute Force / Permutation Generation**: Iterating over all triplets of indices $(i, j, k)$.
- **Hashing**: Using `HashSet` for automatic deduplication of generated numbers.

---

### ⚠️ Common Mistakes

1. **Variable Naming Confusion**: The `HashSet` is named `map`. While code logic is unaffected, calling a `Set` a `map` can cause minor readability confusion during revision.
2. **Forgetting Index Checks**: If the condition `i != j && i != k && j != k` were omitted or incomplete, the algorithm would incorrectly reuse the exact same array element multiple times.
3. **Checking Value Equality instead of Index Equality**: Checking `digits[i] != digits[j]` instead of `i != j` would incorrectly prevent using two different positions in `digits` that happen to hold the same digit value (e.g., `digits = [2, 2, 0]`). Your code correctly checks index inequality (`i != j`).

---

### 🚀 Optimization Notes

- **Loop Pruning**: Instead of checking `i != j && i != k && j != k` deep inside the nested loops, you could skip invalid indices early:
  - Inside the `j` loop: `if (j == i) continue;`
  - Inside the `k` loop: `if (k == i || k == j) continue;`
- **Given Constraints**: With $n \le 10$, your brute-force approach with $\mathcal{O}(n^3)$ iterations easily passes within time limits and requires no further algorithmic changes.
