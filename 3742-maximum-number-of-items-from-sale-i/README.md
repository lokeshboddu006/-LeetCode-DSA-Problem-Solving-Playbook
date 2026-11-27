<h2><a href="https://leetcode.com/problems/maximum-number-of-items-from-sale-i">Maximum Number of Items From Sale I</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a 2D integer array <code>items</code>, where <code>items[i] = [factor<sub>i</sub>, price<sub>i</sub>]</code> represents the <code>i<sup>th</sup></code> item. You are also given an integer <code>budget</code>.</p>

<p>There are unlimited copies of each item available for purchase.You may buy any number of copies of any items such that the total cost of the purchased copies is at most <code>budget</code>.</p>

<p>After buying items, you may receive free copies according to the following rules:</p>

<ul>
	<li>For each item <code>i</code> that you bought <strong>at least one copy</strong> of, you receive <strong>one free copy</strong> of every item <code>j</code> such that <code>j != i</code> and <code>factor<sub>i</sub></code> divides <code>factor<sub>j</sub></code>.</li>
	<li>Buying multiple copies of the same item <code>i</code> does <strong>not</strong> give additional free copies through item <code>i</code>.</li>
	<li>The same item <code>j</code> can be received multiple times for free if it is received from purchases of different item types.</li>
</ul>

<p>Return the <strong>maximum total number of item copies</strong> you can obtain, including both purchased copies and free copies, while spending at most <code>budget</code> on purchased items.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">items = [[6,2],[2,6],[3,4]], budget = 9</span></p>

<p><strong>Output:</strong> <span class="example-io">4</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>You can buy 2 copies of item 0 and 1 copy of item 2 for a total cost of <code>2 * 2 + 4 = 8</code>, which is not greater than <code>budget = 9</code>.</li>
	<li>Buying item 2 gives 1 free copy of item 0, because <code>factor<sub>2</sub> = 3</code> divides <code>factor<sub>0</sub> = 6</code>.</li>
	<li>You leave with 3 purchased copies and 1 free copy, for a total of 4 item copies.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">items = [[2,4],[3,2],[4,1],[6,4],[12,4]], budget = 8</span></p>

<p><strong>Output:</strong> <span class="example-io">10</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>You can buy 1 copy of item 0, 1 copy of item 1, and 2 copies of item 2 for a total cost of <code>4 + 2 + 2 * 1 = 8</code>.</li>
	<li>Buying item 0 gives 1 free copy of items 2, 3, and 4.</li>
	<li>Buying item 1 gives 1 free copy of items 3 and 4.</li>
	<li>Buying item 2 gives 1 free copy of item 4.</li>
	<li>Thus, you receive 6 free copies. You leave with 4 purchased copies and 6 free copies, for a total of 10 item copies.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= items.length &lt;= 1000</code></li>
	<li><code>items[i] = [factor<sub>i</sub>, price<sub>i</sub>]</code></li>
	<li><code>1 &lt;= factor<sub>i</sub>, price<sub>i</sub> &lt;= 1500</code></li>
	<li><code>1 &lt;= budget &lt;= 1500</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the maximum total number of items (both purchased and free copies) we can get within a given `budget`.

When you purchase at least one copy of item `i`:
1. You get that copy (1 item).
2. You trigger free copies: every *other* item `j` whose factor `items[j][0]` is divisible by `items[i][0]` gives you 1 free copy.
3. Any additional copies of item `i` you buy only add 1 more copy per purchase and **do not** trigger any extra free copies.

This splits the decision for each item into two phases:
- **First copy of item `i`**: Costs `price_i` and yields `1 + bonus[i]` total items (where `bonus[i]` is the count of items `j != i` where `items[j][0] % items[i][0] == 0`).
- **Additional copies of item `i`**: Each extra copy costs `price_i` and yields only `1` additional item.

This structure maps naturally to a hybrid Knapsack problem: a **0/1 Knapsack** choice for the first copy (to gain the bonus once), followed by an **Unbounded Knapsack** choice for any subsequent copies of the same item.

---

### 🔍 Approach

1. **Precompute Bonuses (`bonus` array)**:
   - For each item `i`, iterate through all other items `j`. If `j != i` and `items[j][0] % items[i][0] == 0`, increment `bonus[i]`.
   - `bonus[i]` represents how many free copies item `i` yields when bought at least once.

2. **Dynamic Programming Setup**:
   - `dp[b]` represents the maximum total item copies obtainable with a budget of `b`.
   - Initialize `dp` of size `budget + 1` filled with `0`s.

3. **Process Each Item (Two-Step Knapsack Update)**:
   - For item `i` with cost `cost = items[i][1]`:
     - **Phase 1 (First Copy - 0/1 Knapsack)**:
       - Clone `dp` into `ndp`.
       - Iterate `b` backwards from `budget` down to `cost`.
       - Transition: `ndp[b] = Math.max(ndp[b], dp[b - cost] + 1 + bonus[i])`. This ensures that the first copy of item `i` (and its associated `bonus[i]`) is taken at most once relative to previous items.
       - Reassign `dp = ndp`.
     - **Phase 2 (Additional Copies - Unbounded Knapsack)**:
       - Iterate `b` forwards from `cost` up to `budget`.
       - Transition: `dp[b] = Math.max(dp[b], dp[b - cost] + 1)`. Moving forward allows buying multiple extra copies of item `i`, with each additional copy contributing `1` item cost-efficiently.

4. **Result Extraction**:
   - Iterate over all entries in `dp` and find the maximum value `ans`.

---

### 🧩 Algorithm

#### 1. Bonus Precomputation
$$\text{bonus}[i] = \sum_{j \neq i, \text{items}[j][0] \pmod{\text{items}[i][0]} = 0} 1$$

#### 2. DP Transitions per Item $i$
Let $\text{cost} = \text{items}[i][1]$ and $B = \text{budget}$.

- **Step 1: First copy transition (0/1 Knapsack style)**
  $$\text{ndp}[b] = \max\Big(\text{dp}[b], \text{dp}[b - \text{cost}] + 1 + \text{bonus}[i]\Big) \quad \text{for } b = B \text{ down to } \text{cost}$$
  $$\text{dp} \leftarrow \text{ndp}$$

- **Step 2: Additional copies transition (Unbounded Knapsack style)**
  $$\text{dp}[b] = \max\Big(\text{dp}[b], \text{dp}[b - \text{cost}] + 1\Big) \quad \text{for } b = \text{cost} \text{ up to } B$$

#### 3. Invariant
At the end of processing item $i$, `dp[b]` holds the maximum items obtainable using a subset of the first $i+1$ item types with spending at most $b$.

---

### ✅ Why This Works

- **Correct Bonus Counting**: By using a backwards 0/1 knapsack step (`ndp` filled from `dp`), the bonus `1 + bonus[i]` is granted **at most once** for item `i`.
- **Correct Additional Purchase Handling**: The forward loop directly on `dp` allows buying extra copies of item `i` after the initial copy has been considered. Because `dp` already accounts for the initial copy (if optimal), subsequent transitions add `+1` copy for each additional `cost` spent.
- **Budget Constraint**: Array bounds $0 \le b \le \text{budget}$ naturally enforce that total spending never exceeds `budget`.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^2 + N \times B)$
  - Precomputing `bonus`: two nested loops over $N$ items take $\mathcal{O}(N^2)$ time.
  - DP updates: for each of the $N$ items, two loops iterate through at most $B = \text{budget}$ states, taking $\mathcal{O}(N \times B)$ time.
  - Overall time complexity is $\mathcal{O}(N^2 + N \cdot \text{budget})$.

- **Space Complexity**: $\mathcal{O}(N + B)$
  - `bonus` array requires $\mathcal{O}(N)$ space.
  - `dp` and `ndp` arrays require $\mathcal{O}(\text{budget})$ space.
  - Overall auxiliary space is $\mathcal{O}(N + \text{budget})$.

---

### 🧠 DSA Pattern

- **Dynamic Programming (Knapsack Variant)**: Combines 0/1 Knapsack (for single-use bonus) and Unbounded Knapsack (for unlimited additional item purchases).

---

### ⚠️ Common Mistakes

1. **Combining First Copy and Extra Copies in a Single Unbounded Loop**:
   If you try to run an unbounded loop using `1 + bonus[i]`, you would erroneously apply `bonus[i]` for every copy of item `i` bought, rather than just the first one.

2. **Updating `dp` In-Place During the First Copy Loop**:
   If the backwards loop for the first copy is not done carefully (or if a separate clone `ndp` is not used), you risk taking the initial copy (and its bonus) multiple times within the same item iteration.

3. **Counting Self-Divisibility**:
   The bonus calculation explicitly checks `i != j`. Failing to exclude `i == j` would double-count item `i` itself as a bonus item.

---

### 🚀 Optimization Notes

- **Unused Alias Variable**: The line `int[][] val = items;` creates an unused alias and can be removed cleanly.
- **Space Optimization**: `ndp` cloning is done per item loop iteration. While `dp.clone()` works fine for $B \le 1500$, using two pre-allocated arrays and swapping them avoids repeated allocation.
- **Divisibility Optimization**: Pre-sorting or grouping items by factor could speed up factor-divisibility checking if $N$ were much larger, though for $N \le 1000$ the $\mathcal{O}(N^2)$ check is fast enough.
