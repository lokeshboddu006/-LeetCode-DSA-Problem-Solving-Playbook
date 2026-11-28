<h2><a href="https://leetcode.com/problems/maximum-number-of-items-from-sale-ii">Maximum Number of Items From Sale II</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a 2D integer array <code>items</code>, where <code>items[i] = [factor<sub>i</sub>, price<sub>i</sub>]</code> represents the <code>i<sup>th</sup></code> item. You are also given an integer <code>budget</code>.</p>

<p>There are unlimited copies of each item available for purchase. You may buy any number of copies of any items such that the total cost of the purchased copies is at most <code>budget</code>.</p>

<p>After buying items, you may receive free copies according to the following rules:</p>

<ul>
	<li>Each purchased copy of item <code>i</code> can give you <strong>at most one</strong> free copy of another item <code>j</code>.</li>
	<li>The free item must satisfy <code>i != j</code> and <code>factor<sub>i</sub></code> divides <code>factor<sub>j</sub></code>.</li>
	<li>For each ordered pair <code>(i, j)</code>, you can receive a free copy of item <code>j</code> from purchases of item <code>i</code> <strong>at most once</strong>, regardless of how many copies of item <code>i</code> you buy.</li>
	<li>The same item <code>j</code> can be received multiple times for free if it is received from purchases of different item types.</li>
</ul>

<p>Return the <strong>maximum total number of item copies</strong> you can obtain, including both purchased copies and free copies, while spending at most <code>budget</code> on purchased items.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">items = [[1,6],[2,4],[3,5]], budget = 19</span></p>

<p><strong>Output:</strong> <span class="example-io">5</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>You can buy 2 copies of item 0 and 1 copy of item 1 for a total cost of <code>2 * 6 + 4 = 16</code>, which is not greater than <code>budget = 19</code>.</li>
	<li>One purchased copy of item 0 gives 1 free copy of item 1, because <code>factor<sub>0</sub> = 1</code> divides <code>factor<sub>1</sub> = 2</code>.</li>
	<li>The other purchased copy of item 0 gives 1 free copy of item 2, because <code>factor<sub>0</sub> = 1</code> divides <code>factor<sub>2</sub> = 3</code>.</li>
	<li>You leave with 3 purchased copies and 2 free copies, for a total of 5 item copies.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">items = [[2,8],[1,10],[6,6],[4,12],[5,20],[5,17]], budget = 35</span></p>

<p><strong>Output:</strong> <span class="example-io">7</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>You can buy 2 copies of item 0, 1 copy of item 1, and 1 copy of item 2 for a total cost of <code>2 * 8 + 10 + 6 = 32</code>, which is not greater than <code>budget = 35</code>.</li>
	<li>One purchased copy of item 0 gives 1 free copy of item 2, because <code>factor<sub>0</sub> = 2</code> divides <code>factor<sub>2</sub> = 6</code>.</li>
	<li>The other purchased copy of item 0 gives 1 free copy of item 3, because <code>factor<sub>0</sub> = 2</code> divides <code>factor<sub>3</sub> = 4</code>.</li>
	<li>The purchased copy of item 1 gives 1 free copy of item 2, because <code>factor<sub>1</sub> = 1</code> divides <code>factor<sub>2</sub> = 6</code>.</li>
	<li>Buying item 2 gives no free copy, because <code>factor<sub>2</sub> = 6</code> does not divide the factor of any other item.</li>
	<li>You leave with 4 purchased copies and 3 free copies, for a total of 7 item copies.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= items.length &lt;= 10<sup>5</sup></code></li>
	<li><code>items[i] = [factor<sub>i</sub>, price<sub>i</sub>]</code></li>
	<li><code>1 &lt;= factor<sub>i</sub> &lt;= items.length</code></li>
	<li><code>1 &lt;= price<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= budget &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem allows you to gain 1 free copy of another item $j$ for every purchased copy of item $i$, provided $factor_i$ divides $factor_j$ and you haven't already received a free copy of $j$ from purchases of item $i$. Thus, one purchased copy of item $i$ combined with one free eligible item $j$ effectively yields **2 items** for the cost of $price_i$, up to a maximum of $cnt$ pairs, where $cnt$ is the number of distinct items $j$ whose factors are multiples of $factor_i$.

The core intuition behind this solution is:
1. **Maximize 2-for-1 purchases first**: To maximize total items per dollar, buying items that grant a free item gives a yield of **2 items per unit cost** ($price_i$). We sort items by price to prioritize cheaper 2-for-1 pairs.
2. **Precompute Multiples**: Using a sieve-like approach, calculate for each factor value how many items have a factor that is a multiple of it.
3. **Greedy Selection**: For each item (in ascending order of price), buy as many copies as can yield free items (at most $cnt$ copies, bounded by the remaining budget).
4. **Spend Leftover Budget**: Once 2-for-1 opportunities are exhausted or no longer cost-effective, spend all remaining budget on the cheapest possible item (price `mn`) to get **1 item per unit cost**.

---

### 🔍 Approach

1. **Sorting by Price**:
   - `Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]))`: Sorts all items by their price in non-decreasing order so we inspect cheaper items first.

2. **Precomputing Divisor Counts (Sieve)**:
   - Find `mx`, the maximum factor value across all items.
   - Build a `freq` array of size `mx + 1` to store how many items have each factor value.
   - For every factor `i` from $1$ to `mx`, iterate over its multiples `j = 2*i, 3*i, ...` and accumulate `freq[j]` into `freq[i]`.
   - After this loop, `freq[factor]` holds the total count of items whose factor is a multiple of `factor` (including the item itself).

3. **Greedy 2-for-1 Purchase Loop**:
   - Identify `mn = items[0][1]`, which is the price of the cheapest item in the dataset.
   - Iterate through each item `item`:
     - Extract `factor = item[0]` and `price = item[1]`.
     - Check `if (price - mn > mn)` (i.e., `price > 2 * mn`): If buying 2 items at price `mn` (costing $2 \times mn$) is cheaper than or equal to buying 1 copy of this item with 1 free item (costing `price`), we break early because any subsequent items will be even more expensive and not worth taking for 2-for-1 pairings compared to single purchases at `mn`.
     - Calculate $cnt = freq[factor] - 1$, which is the number of other items eligible to be given as free copies for this item.
     - Determine `take = Math.min(cnt, budget / price)`, the maximum copies of this item we can buy that will each yield a free item without exceeding our `budget`.
     - Update `ans += take * 2` (each purchase gives 1 bought + 1 free copy).
     - Deduct `take * price` from `budget`.

4. **Spending Remaining Budget**:
   - `ans += budget / mn`: Buy as many copies of the cheapest item as possible with the remaining budget (yielding 1 item per purchase).
   - Return `ans`.

---

### 🧩 Algorithm

1. **Sorting**: Sort `items` array based on `item[1]` (price).
2. **Frequency Array & Harmonic Sieve**:
   - Count frequencies: $freq[x] = \text{count of items with factor } x$.
   - Sieve accumulation: 
     $$\text{freq}[i] \leftarrow \text{freq}[i] + \sum_{k=2}^{\lfloor mx / i \rfloor} \text{freq}[k \cdot i]$$
3. **Greedy Selection**:
   - For each item $i$ with $(factor_i, price_i)$:
     - If $price_i > 2 \cdot mn$, terminate the loop.
     - $cnt = freq[factor_i] - 1$.
     - $take = \min(cnt, \lfloor budget / price_i \rfloor)$.
     - $ans \leftarrow ans + 2 \cdot take$.
     - $budget \leftarrow budget - take \cdot price_i$.
4. **Remainder Calculation**:
   - $ans \leftarrow ans + \lfloor budget / mn \rfloor$.

---

### ✅ Why This Works

- **Divisor/Multiple Rule**: Item $i$ can give a free copy of item $j$ if $factor_i \mid factor_j$ and $i \neq j$. By computing $cnt = freq[factor_i] - 1$, we count exactly how many distinct items $j$ exist such that $factor_i$ divides $factor_j$. Since each item pair $(i, j)$ can only yield 1 free item total across all purchases of item $i$, buying up to $cnt$ copies of item $i$ guarantees that each copy yields 1 free item.
- **Greedy Efficiency**: A 2-for-1 pair at price $P$ gives an effective price of $P/2$ per item. By processing items in increasing order of price $P$, we grab the cheapest 2-for-1 pairs first. If $P > 2 \cdot mn$, effective price per item $P/2 > mn$, so buying individual items at $mn$ is strictly cheaper or equal in cost per item, justifying the early break condition `price - mn > mn`.

---

### ⏱️ Complexity

- **Time Complexity**:
  - **Sorting**: $\mathcal{O}(N \log N)$ where $N$ is the number of items.
  - **Sieve Precomputation**: Finding `mx` takes $\mathcal{O}(N)$. The sieve loop runs in $\mathcal{O}(M \log M)$ time, where $M = \max(factor_i) \le N$.
  - **Greedy Loop**: Runs in $\mathcal{O}(N)$ iterations.
  - **Total Time Complexity**: $\mathcal{O}(N \log N + M \log M)$, which comfortably runs within time limits for $N, M \le 10^5$.

- **Space Complexity**:
  - `freq` array uses $\mathcal{O}(M)$ auxiliary space where $M = \max(factor_i)$.
  - **Total Space Complexity**: $\mathcal{O}(M)$ auxiliary space (plus $\mathcal{O}(\log N)$ or $\mathcal{O}(N)$ for sorting depending on the implementation).

---

### 🧠 DSA Pattern

- **Greedy Strategy**: Sorting by price and taking the best 2-for-1 deals first.
- **Harmonic / Dirichlet Sieve (Number Theory)**: Efficiently calculating multi-item factor relationships by iterating over multiples $j = 2i, 3i, \dots$.

---

### ⚠️ Common Mistakes & Code Weaknesses

1. **Mutating `freq` values without accounting for overlapping usage**:
   - The code calculates $cnt = freq[factor] - 1$ for item $i$, assuming all $cnt$ multiple items are available as free copies. However, if multiple items share the same factor or overlapping multiples, the code doesn't track *which* free items have already been claimed by previous purchases. In some edge cases with identical or overlapping factors, this could potentially overcount available free items if the problem constraints strictly enforced unique item consumption (though here free copies depend only on distinct indices $j$).
2. **Integer Overflow Risk**:
   - `budget` can be up to $10^9$ and `ans` counts item quantities. While `ans` fits within standard 32-bit signed integer limits for `budget <= 10^9`, if `budget` or item count calculations exceeded $2 \cdot 10^9$, `ans` could overflow `int`.

---

### 🚀 Optimization Notes

- **Sieve Optimizations**: The sieve loop checks `if (freq[i] > 0)` before running the inner loop, which skips work for non-existent factors and speeds up execution when factor values are sparse.
- **Early Loop Break**: The condition `if (price - mn > mn)` (equivalent to `price > 2 * mn`) stops the greedy processing early as soon as purchasing 2-for-1 bundles becomes less cost-effective than buying single units of the minimum-price item.
