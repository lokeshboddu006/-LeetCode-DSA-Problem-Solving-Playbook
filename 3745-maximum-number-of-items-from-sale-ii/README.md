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

The problem allows you to buy copies of an item `i` with factor $f_i$ and price $p_i$. For each copy of item `i` you buy, you can receive 1 free copy of a distinct item `j` whose factor $f_j$ is a multiple of $f_i$, provided you haven't claimed item `j` as a free item from item `i` before. 

This means that for item `i`, you can get at most `mult[f_i] - 1` free items in total across all your purchases of item `i`, where `mult[f_i]` is the total count of items in the dataset whose factor is a multiple of $f_i$. 

Hence, purchasing item `i` offers a **2-for-1 deal** (1 purchased + 1 free item for a cost of $p_i$) up to `mult[f_i] - 1` times. Any additional purchases beyond this limit (or purchases of items with 0 available free partners) yield only **1 item for price $p_i$**.

To maximize the total number of items within a given budget:
1. Greedily prioritize the cheapest 2-for-1 deals (buying 2 items for cost $p$).
2. Stop taking 2-for-1 deals if their cost $p$ exceeds $2 \times \text{minP}$ (where $\text{minP}$ is the overall minimum item price), because buying 2 individual items at price $\text{minP}$ costs $2 \times \text{minP}$, which would be cheaper or equal.
3. Spend all remaining budget on single items at the absolute cheapest unit price $\text{minP}$.

---

### 🔍 Approach

1. **Count Multiples (`cnt` and `mult` arrays)**:
   - `cnt[f]` stores how many items have `factor == f`.
   - The code iterates using a harmonic series loop (`for (int m = f; m <= n; m += f)`) to compute `mult[f]`, which is the total count of items whose factor is a multiple of `f`.

2. **Identify Candidate 2-for-1 Deals**:
   - The code finds the global minimum price `minP` across all items.
   - For each item `[f, p]`, the maximum number of 2-for-1 deals it can provide is `g = mult[f] - 1`.
   - If `g > 0`, the pair `{p, g}` is added to the candidate list `cand`.

3. **Sort and Process Deals Greedily**:
   - `cand` is sorted by price `p` in ascending order.
   - The code loops through the sorted candidates:
     - **Break Condition**: If `p > 2L * minP`, it stops processing further candidates because paying $p$ for 2 items is worse than paying $2 \times \text{minP}$ for 2 items.
     - **Take Deals**: Computes `can = Math.min((long)g, (B - spent) / p)`, which is the number of 2-for-1 deals taken for this item.
     - Updates `total += can * 2` and `spent += can * p`.

4. **Fill Remaining Budget**:
   - Any remaining budget `B - spent` is used to purchase single items at the minimum price `minP` by adding `(B - spent) / minP` to `total`.

---

### 🧩 Algorithm

1. **Frequency & Multiple Counting**:
   $$\text{cnt}[f] = \text{count of items with factor } f$$
   $$\text{mult}[f] = \sum_{m = k \cdot f \le n} \text{cnt}[m]$$

2. **Candidate Generation**:
   $$\text{for each item } (f, p): g = \text{mult}[f] - 1$$
   $$\text{If } g > 0, \text{ add } (p, g) \text{ to } \text{cand}$$

3. **Greedy Selection**:
   - Sort $\text{cand}$ ascending by price $p$.
   - While remaining budget $> 0$ and $p \le 2 \cdot \text{minP}$:
     $$\text{can} = \min\left(g, \lfloor \frac{\text{budget} - \text{spent}}{p} \rfloor\right)$$
     $$\text{total} \gets \text{total} + 2 \cdot \text{can}$$
     $$\text{spent} \gets \text{spent} + p \cdot \text{can}$$

4. **Final Single-Item Fill**:
   $$\text{total} \gets \text{total} + \lfloor \frac{\text{budget} - \text{spent}}{\text{minP}} \rfloor$$

---

### ✅ Why This Works

- **2-for-1 Maximum Capacity**: Each item $i$ can pair with at most $g = \text{mult}[f_i] - 1$ other items because each distinct multiple item $j$ can be claimed as a free item from item $i$ at most once.
- **Greedy Choice Property**: Buying 2 items for price $p_a$ is more cost-effective than buying 2 items for price $p_b$ if $p_a < p_b$. Sorting candidates by price guarantees we select the most cost-effective 2-item bundles first.
- **Cutoff Threshold ($2 \times \text{minP}$)**: If a bundle of 2 items costs $p > 2 \cdot \text{minP}$, we can instead buy 2 separate items at price $\text{minP}$ for a total cost of $2 \cdot \text{minP} < p$. Thus, no bundle with $p > 2 \cdot \text{minP}$ will ever be chosen.
- **Exhaustive Leftover Usage**: Once all beneficial 2-for-1 bundles are processed, the best remaining option for any leftover money is to buy single items at the minimum available unit price $\text{minP}$.

---

### ⏱️ Complexity

- **Time Complexity**:
  - **Multiple Computation**: $\mathcal{O}(N \log N)$ due to the harmonic series nested loops ($\sum_{f=1}^N \frac{N}{f} = N \ln N$).
  - **Sorting Candidates**: $\mathcal{O}(N \log N)$ to sort at most $N$ candidate pairs.
  - **Greedy Loop**: $\mathcal{O}(N)$ iteration over candidate list.
  - **Total Time Complexity**: $\mathcal{O}(N \log N)$, where $N$ is the number of items.

- **Space Complexity**:
  - `cnt` and `mult` arrays of size $N + 1$: $\mathcal{O}(N)$.
  - `cand` list storing at most $N$ elements: $\mathcal{O}(N)$.
  - **Total Space Complexity**: $\mathcal{O}(N)$.

---

### 🧠 DSA Pattern

- **Greedy Algorithm**: Picking the cheapest 2-for-1 bundles first, then filling remaining budget with the minimum cost item.
- **Math / Harmonic Series (Sieve-like iteration)**: Counting multiples in $\mathcal{O}(N \log N)$ time.
- **Sorting**: Ordering bundles by cost to ensure greedy choice validity.

---

### ⚠️ Common Mistakes

1. **Integer Overflow**: Budget values can be up to $10^9$ and multiplying bundle counts can exceed standard 32-bit signed integer limits. The solution correctly uses `long` variables (`spent`, `total`, `B`, `2L * minP`) during accumulation to prevent overflow.
2. **Missing the $2 \cdot \text{minP}$ Bound**: Forgetting to stop when $p > 2 \cdot \text{minP}$ would result in taking 2-for-1 deals that are actually more expensive than simply buying single items at price $\text{minP}$.
3. **Overcounting Free Items**: Assuming an item can produce an infinite number of free items. The code correctly restricts the available free copies to $g = \text{mult}[f] - 1$.

---

### 🚀 Optimization Notes

- **Harmonic Iteration Efficiency**: Computing multiples via `for (int m = f; m <= n; m += f)` runs in $\mathcal{O}(N \log N)$ time, avoiding an $O(N^2)$ brute-force divisor check.
- **Early Loop Termination**: The condition `if (p > 2L * minP) break;` avoids iterating through high-cost candidates once they are no longer cost-effective.
