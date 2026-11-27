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
The core idea behind your solution is to reduce the problem to a 0/1 Knapsack dynamic programming model:
1. **Binary Choice**: Buying at least one copy of item `i` grants 1 purchased copy of item `i` plus 1 free copy of every item `j` whose factor is a multiple of item `i`'s factor. Buying further copies of item `i` does not trigger additional free copies. Therefore, deciding whether to unlock item `i`'s bonus is a 0/1 decision.
2. **Precalculated Yield**: By checking divisibility `items[j][0] % items[i][0] == 0`, you precompute `free[i]`, which represents the total item count (purchased copy + all triggered free copies) gained by spending `items[i][1]` money on item `i`.
3. **Greedy Base Case**: Any remaining budget after making binary decisions for all items can no longer trigger new free copy bonuses. To maximize the remaining copies, that unused budget should be spent entirely on the single cheapest item (`mini`).

---

### 🔍 Approach
1. **Precomputation (`maximumSaleItems` method)**:
   - Track `mini`, the minimum price across all items in `items`.
   - Allocate `free` array of size `n`.
   - For each item `i`, iterate through all items `j`:
     - If `items[j][0] % items[i][0] == 0`, increment `free[i]`. (When `j == i`, `items[i][0] % items[i][0] == 0` is `true`, so `free[i]` counts 1 purchased copy of item `i` plus 1 free copy for every valid `j != i`).
   - Fill the memoization grid `dp[1000][1501]` with `-1`.

2. **Recursion with Memoization (`solve` method)**:
   - `solve(i, tot, items)` calculates the maximum total item copies reachable considering items from index `i` onward with remaining budget `tot`.
   - **Base Case**: If `i >= items.length`, return `tot / mini` (buying as many copies of the cheapest item as possible with leftover money).
   - **Memoization Check**: Return `dp[i][tot]` if it has already been computed.
   - **Take Choice**: If `tot >= items[i][1]`, buy 1 copy of item `i`. This contributes `free[i]` copies and consumes `items[i][1]` from `tot`, recursing to `solve(i + 1, tot - items[i][1], items)`.
   - **Skip Choice**: Skip buying item `i` for bonus activation, recursing to `solve(i + 1, tot, items)`.
   - Return and store `dp[i][tot] = Math.max(take, skip)`.

---

### 🧩 Algorithm

- **Precomputation**:
  $$\text{free}[i] = \sum_{j=0}^{n-1} [\text{items}[j][0] \pmod{\text{items}[i][0]} == 0]$$
  $$\text{mini} = \min_{0 \le i < n} (\text{items}[i][1])$$

- **DP State**:
  `dp[i][tot]` = Maximum total items obtainable from index `i` to `n - 1` with remaining budget `tot`.

- **Base Case**:
  $$\text{solve}(n, \text{tot}) = \lfloor \frac{\text{tot}}{\text{mini}} \rfloor$$

- **Transitions**:
  $$\text{solve}(i, \text{tot}) = \max(\text{skip}, \text{take})$$
  where:
  - $\text{skip} = \text{solve}(i + 1, \text{tot})$
  - $\text{take} = \begin{cases} \text{free}[i] + \text{solve}(i + 1, \text{tot} - \text{items}[i][1]) & \text{if } \text{tot} \ge \text{items}[i][1] \\ 0 & \text{otherwise} \end{cases}$

---

### ✅ Why This Works
- **Correctness of 0/1 framing**: Each item's rule states that free copies are triggered once per item type bought. Thus, purchasing at least 1 copy of item `i` gives `free[i]` items at the cost of `items[i][1]`. 
- **Correctness of `free[i]` calculation**: Since `items[i][0] % items[i][0] == 0` is true, `j == i` contributes `1` to `free[i]` (the copy actually bought). Every other `j != i` where `items[j][0]` is divisible by `items[i][0]` adds `1` for its free copy.
- **Correctness of leftover budget**: Any remaining budget `tot` after considering all item indices cannot unlock any new item-level free bonuses. Thus, spending all remaining `tot` on the item with minimum price (`mini`) guarantees the maximum possible additional copies ($\lfloor \text{tot} / \text{mini} \rfloor$).

---

### ⏱️ Complexity

- **Time Complexity**: 
  - Precomputation: $O(n^2)$ to compare factor divisibility across all pairs $(i, j)$ and find `mini`.
  - DP Recursion: $O(n \cdot B)$ unique states, where $n = \text{items.length}$ and $B = \text{budget}$. Each state transition is $O(1)$.
  - **Total Time Complexity**: $O(n^2 + n \cdot B)$.

- **Space Complexity**:
  - `dp` array: $O(1000 \times 1501)$ static array storage ($O(N \cdot B)$).
  - `free` array: $O(n)$.
  - Call stack depth: $O(n)$ recursion depth.
  - **Total Space Complexity**: $O(N \cdot B)$ auxiliary space.

---

### 🧠 DSA Pattern
- **Dynamic Programming (0/1 Knapsack Variation)**
- **Precomputation**

---

### ⚠️ Common Mistakes

1. **Hardcoded Array Bounds Risk**:
   - `dp` is statically sized `new int[1000][1501]`. If `items.length` ever exceeded 1000, indexing `dp[i]` would throw an `ArrayIndexOutOfBoundsException`.
2. **Implicit Copy Count in `free[i]`**:
   - It is easy to forget that `free[i]` already includes the 1 purchased copy of item `i` (because `i % i == 0`). Adding `1` separately would double-count the purchased copy.
3. **Array Re-initialization Scope**:
   - `dp` is declared as an instance variable of `Solution`. If multiple test cases run on the same class instance, only the first 1000 rows are reset in `maximumSaleItems`. If `n < 1000`, unnecessary work is done resetting unused rows; if $N > 1000$, it would overflow.

---

### 🚀 Optimization Notes

- **Dynamic DP Array Sizing**:
  - Allocating `dp` dynamically based on actual inputs, e.g., `new int[n][budget + 1]`, avoids hardcoding array limits and saves initialization time when $n < 1000$.
- **Space Reduction to 1D DP**:
  - Since transition at state `i` only depends on state `i + 1`, the 2D matrix could be compressed to a 1D DP array of size `budget + 1` processed in reverse, reducing space complexity from $O(n \cdot B)$ to $O(B)$.
