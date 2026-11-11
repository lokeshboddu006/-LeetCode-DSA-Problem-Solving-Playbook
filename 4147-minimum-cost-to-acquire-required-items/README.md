<h2><a href="https://leetcode.com/problems/minimum-cost-to-acquire-required-items">Minimum Cost to Acquire Required Items</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given five integers <code>cost1</code>, <code>cost2</code>, <code>costBoth</code>, <code>need1</code>, and <code>need2</code>.</p>

<p>There are three types of items available:</p>

<ul>
	<li>An item of <strong>type 1</strong> costs <code>cost1</code> and contributes 1 unit to the type 1 requirement only.</li>
	<li>An item of <strong>type 2</strong> costs <code>cost2</code> and contributes 1 unit to the type 2 requirement only.</li>
	<li>An item of <strong>type 3</strong> costs <code>costBoth</code> and contributes 1 unit to <strong>both</strong> type 1 and type 2 requirements.</li>
</ul>

<p>You must collect enough items so that the total contribution toward type 1 is <strong>at least</strong> <code>need1</code> and the total contribution toward type 2 is <strong>at least</strong> <code>need2</code>.</p>

<p>Return an integer representing the <strong>minimum</strong> possible total cost to achieve these requirements.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">cost1 = 3, cost2 = 2, costBoth = 1, need1 = 3, need2 = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>After buying three type 3 items, which cost <code>3 * 1 = 3</code>, the total contribution to type 1 is 3 (<code>&gt;= need1 = 3</code>) and to type 2 is 3 (<code>&gt;= need2 = 2</code>).<br data-end="229" data-start="226" />
Any other valid combination would cost more, so the minimum total cost is 3.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">cost1 = 5, cost2 = 4, costBoth = 15, need1 = 2, need2 = 3</span></p>

<p><strong>Output:</strong> <span class="example-io">22</span></p>

<p><strong>Explanation:</strong></p>

<p>We buy <code>need1 = 2</code> items of type 1 and <code>need2 = 3</code> items of type 2: <code>2 * 5 + 3 * 4 = 10 + 12 = 22</code>.<br />
Any other valid combination would cost more, so the minimum total cost is 22.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">cost1 = 5, cost2 = 4, costBoth = 15, need1 = 0, need2 = 0</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<p>Since no items are required (<code>need1 = need2 = 0</code>), we buy nothing and pay 0.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= cost1, cost2, costBoth &lt;= 10<sup>6</sup></code></li>
	<li><code>0 &lt;= need1, need2 &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation is that any valid strategy comes down to deciding how many type 3 items (which cover both requirements) to purchase versus buying individual type 1 and type 2 items.

Because the cost changes linearly as you buy more type 3 items, the optimal solution must occur at one of three critical boundary points:
1. **0 type 3 items:** Fulfill both requirements entirely with individual type 1 and type 2 items.
2. **`min(need1, need2)` type 3 items:** Buy just enough type 3 items to fully cover the smaller requirement, then fulfill the remainder of the larger requirement using individual items.
3. **`max(need1, need2)` type 3 items:** Buy enough type 3 items to cover the larger requirement completely, even if it results in buying excess units for the smaller requirement.

Your implementation works by explicitly calculating the total cost for these three candidate strategies and taking the minimum among them.

---

### 🔍 Approach

1. **Calculate Boundaries:**
   - `min = Math.min(need1, need2)`: The quantity of type 3 items needed to satisfy the smaller need.
   - `max = Math.max(need1, need2)`: The quantity of type 3 items needed to satisfy the larger need.

2. **Evaluate Strategy 1 (No Type 3 items):**
   - Calculate `(long) need1 * cost1 + (long) need2 * cost2` and store the result in `ans`.
   - Cast operands to `long` to prevent 32-bit integer overflow.

3. **Evaluate Strategy 2 (`min` Type 3 items + Individual items):**
   - Buy `min` type 3 items at cost `min * costBoth`.
   - Buy remaining type 1 items (`need1 - min`) at `cost1`.
   - Buy remaining type 2 items (`need2 - min`) at `cost2`.
   - Note that for one of the needs, the remaining amount will be `0`.
   - Update `ans` using `Math.min`.

4. **Evaluate Strategy 3 (`max` Type 3 items):**
   - Buy `max` type 3 items at cost `max * costBoth`.
   - This satisfies both requirements (possibly over-fulfilling the smaller one).
   - Update `ans` using `Math.min`.

5. **Return Answer:**
   - Return the minimum total cost calculated (`ans`).

---

### 🧩 Algorithm

The code evaluates a piecewise linear cost function at its boundary points:

1. **Candidate 1 ($k = 0$ type 3 items):**
   $$\text{Cost}_1 = \text{need1} \times \text{cost1} + \text{need2} \times \text{cost2}$$

2. **Candidate 2 ($k = \min(\text{need1}, \text{need2})$ type 3 items):**
   $$\text{Cost}_2 = \text{min} \times \text{costBoth} + (\text{need1} - \text{min}) \times \text{cost1} + (\text{need2} - \text{min}) \times \text{cost2}$$

3. **Candidate 3 ($k = \max(\text{need1}, \text{need2})$ type 3 items):**
   $$\text{Cost}_3 = \text{max} \times \text{costBoth}$$

4. **Result:**
   $$\text{ans} = \min(\text{Cost}_1, \text{Cost}_2, \text{Cost}_3)$$

---

### ✅ Why This Works

Let $k$ be the number of type 3 items purchased.
- For $0 \le k \le \min(\text{need1}, \text{need2})$, every type 3 item saves purchasing 1 type 1 item and 1 type 2 item. The cost per type 3 item added changes by a constant slope of $(\text{costBoth} - \text{cost1} - \text{cost2})$.
- For $\min(\text{need1}, \text{need2}) \le k \le \max(\text{need1}, \text{need2})$, every additional type 3 item only satisfies the remaining requirement (the larger one), changing the cost by a constant slope of $(\text{costBoth} - \text{cost}_{\text{remaining}})$.
- For $k > \max(\text{need1}, \text{need2})$, buying extra type 3 items provides no benefit and strictly increases cost since costs are non-negative.

Because the total cost function is piecewise linear with respect to $k$, its minimum must occur at one of the inflection points: $k = 0$, $k = \min(\text{need1}, \text{need2})$, or $k = \max(\text{need1}, \text{need2})$. By checking these three choices, your code guarantees finding the overall minimum cost.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(1)$ — The solution performs a fixed number of basic arithmetic calculations and comparisons (`Math.min`, `Math.max`), running in constant time.
- **Space Complexity:** $\mathcal{O}(1)$ — Only a few `long` primitive variables (`min`, `max`, `ans`) are used, requiring constant auxiliary space.

---

### 🧠 DSA Pattern

- **Math / Case Analysis** (Evaluating a convex/piecewise linear function at critical boundary points).

---

### ⚠️ Common Mistakes

1. **32-bit Integer Overflow:**
   - `need1` and `need2` can be up to $10^9$, and costs can be up to $10^6$. Their product can reach $10^{15}$, which exceeds Java's 32-bit signed integer maximum ($\approx 2 \times 10^9$).
   - Your code correctly avoids this by casting operands to `(long)` before performing multiplication (e.g., `(long) need1 * cost1`).

2. **Forgetting Over-Satisfaction:**
   - It is easy to mistakenly assume you should never buy more type 3 items than `min(need1, need2)`. However, if `costBoth` is cheaper than individual item costs, buying `max` type 3 items can be cheaper even if some type 1 or type 2 contributions are wasted. Your code correctly accounts for candidate 3.

---

### 🚀 Optimization Notes

- The submitted solution is already **optimal** in both time complexity ($\mathcal{O}(1)$) and space complexity ($\mathcal{O}(1)$).
- Explicit `(long)` type casting is properly applied to prevent integer overflow. No further optimizations are required.
