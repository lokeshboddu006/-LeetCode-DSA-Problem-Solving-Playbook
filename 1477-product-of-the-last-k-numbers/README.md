<h2><a href="https://leetcode.com/problems/product-of-the-last-k-numbers">Product of the Last K Numbers</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Design an algorithm that accepts a stream of integers and retrieves the product of the last <code>k</code> integers of the stream.</p>

<p>Implement the <code>ProductOfNumbers</code> class:</p>

<ul>
	<li><code>ProductOfNumbers()</code> Initializes the object with an empty stream.</li>
	<li><code>void add(int num)</code> Appends the integer <code>num</code> to the stream.</li>
	<li><code>int getProduct(int k)</code> Returns the product of the last <code>k</code> numbers in the current list. You can assume that always the current list has at least <code>k</code> numbers.</li>
</ul>

<p>The test cases are generated so that, at any time, the product of any contiguous sequence of numbers will fit into a single 32-bit integer without overflowing.</p>

<p>&nbsp;</p>
<p><strong class="example">Example:</strong></p>

<pre>
<strong>Input</strong>
[&quot;ProductOfNumbers&quot;,&quot;add&quot;,&quot;add&quot;,&quot;add&quot;,&quot;add&quot;,&quot;add&quot;,&quot;getProduct&quot;,&quot;getProduct&quot;,&quot;getProduct&quot;,&quot;add&quot;,&quot;getProduct&quot;]
[[],[3],[0],[2],[5],[4],[2],[3],[4],[8],[2]]

<strong>Output</strong>
[null,null,null,null,null,null,20,40,0,null,32]

<strong>Explanation</strong>
ProductOfNumbers productOfNumbers = new ProductOfNumbers();
productOfNumbers.add(3);        // [3]
productOfNumbers.add(0);        // [3,0]
productOfNumbers.add(2);        // [3,0,2]
productOfNumbers.add(5);        // [3,0,2,5]
productOfNumbers.add(4);        // [3,0,2,5,4]
productOfNumbers.getProduct(2); // return 20. The product of the last 2 numbers is 5 * 4 = 20
productOfNumbers.getProduct(3); // return 40. The product of the last 3 numbers is 2 * 5 * 4 = 40
productOfNumbers.getProduct(4); // return 0. The product of the last 4 numbers is 0 * 2 * 5 * 4 = 0
productOfNumbers.add(8);        // [3,0,2,5,4,8]
productOfNumbers.getProduct(2); // return 32. The product of the last 2 numbers is 4 * 8 = 32 
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>0 &lt;= num &lt;= 100</code></li>
	<li><code>1 &lt;= k &lt;= 4 * 10<sup>4</sup></code></li>
	<li>At most <code>4 * 10<sup>4</sup></code> calls will be made to <code>add</code> and <code>getProduct</code>.</li>
	<li>The product of the stream at any point in time will fit in a <strong>32-bit</strong> integer.</li>
</ul>

<p>&nbsp;</p>
<strong>Follow-up: </strong>Can you implement <strong>both</strong> <code>GetProduct</code> and <code>Add</code> to work in <code>O(1)</code> time complexity instead of <code>O(k)</code> time complexity?

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation is that range products can be calculated using **prefix products**, similar to how range sums are calculated using prefix sums. 

If we maintain a list of running cumulative products, the product of the last $k$ non-zero elements can be computed in constant time by dividing the total cumulative product by the cumulative product just before the last $k$ elements:

$$\text{Product of last } k \text{ elements} = \frac{\text{PrefixProduct}[n-1]}{\text{PrefixProduct}[n-1-k]}$$

However, zero breaks multiplication division because dividing by zero is undefined, and any sequence containing a zero yields a product of `0`. The submitted solution handles this by **resetting the prefix product list** whenever a `0` is added. 

- If `num == 0`, we clear our memory of past products because any future `getProduct(k)` that extends back to or beyond this `0` will evaluate to `0`.
- If `k` is smaller than the length of the current sequence since the last zero, the target window contains no zeros, and we can safely divide prefix products.
- If `k` is greater than or equal to the size of our current list, it means the query window reaches back to or beyond the last zero, so the result must be `0`.

---

### 🔍 Approach

1. **Initialization (`ProductOfNumbers()` constructor):**
   - The solution initializes an `ArrayList<Integer> l` with a dummy base element `1`. This handles 1-based indexing calculations smoothly and avoids special cases when asking for the product of all numbers added so far.

2. **Appending Numbers (`add(int num)`):**
   - Checks if `num == 0`.
   - If `num == 0`: It reassigns `l` to a brand-new `ArrayList` containing only `[1]`. This discards all prefix products prior to the zero.
   - If `num != 0`: It takes the last cumulative product (`l.get(l.size() - 1)`), multiplies it by `num`, and appends the result to `l`.

3. **Retrieving Product (`getProduct(int k)`):**
   - Retrieves the current size of the list `n = l.size()`.
   - Checks if `k < n`:
     - If `k < n`, all $k$ numbers are after the last encountered zero. It returns `l.get(n - 1) / l.get(n - 1 - k)`.
     - If `k >= n`, the requested window extends back to or past a zero, so it returns `0`.

---

### 🧩 Algorithm

#### 1. Add Operation (`add(num)`)
- **If** $num = 0$:
  $$\text{reset } l \leftarrow [1]$$
- **Else**:
  $$l.\text{append}(l[\text{last}] \times num)$$

#### 2. Get Product Operation (`getProduct(k)`)
Let $n = |l|$.
$$\text{getProduct}(k) = \begin{cases} 
\frac{l[n - 1]}{l[n - 1 - k]} & \text{if } k < n \\
0 & \text{if } k \ge n 
\end{cases}$$

---

### ✅ Why This Works

- **Prefix Division Property:** Multiplication is associative and commutative. For a sequence of non-zero numbers $x_1, x_2, \dots, x_m$, the prefix products stored in $l$ are $l[i] = \prod_{j=1}^i x_j$ (with $l[0] = 1$). The product of the last $k$ numbers $x_{m-k+1} \times \dots \times x_m$ is equal to $\frac{l[m]}{l[m-k]}$, which corresponds to `l.get(n - 1) / l.get(n - 1 - k)`.
- **Zero Elimination:** Resetting the list when encountering `0` guarantees that `l` never contains `0` after the dummy element at index 0. This completely avoids division-by-zero errors.
- **Correct Zero Boundary Handling:** Since $l$ contains `1` plus all non-zero numbers added since the last zero, the size $n$ of $l$ is $(\text{count of non-zero numbers since last zero}) + 1$. Thus, if $k \ge n$, at least one zero must be present in the last $k$ numbers added overall, making the product $0$.

---

### ⏱️ Complexity

- **Time Complexity:**
  - `add(int num)`: $\mathcal{O}(1)$ amortized. Appending to an `ArrayList` takes $\mathcal{O}(1)$ amortized time. Reallocating a small list when `num == 0` also takes $\mathcal{O}(1)$ time.
  - `getProduct(int k)`: $\mathcal{O}(1)$ constant time. It performs a size check, two list lookups by index, and one division.

- **Space Complexity:**
  - $\mathcal{O}(N)$ where $N$ is the maximum number of consecutive non-zero elements added to the stream. In the worst case (no zeros), `l` stores $N + 1$ elements.

---

### 🧠 DSA Pattern

- **Prefix Sum / Prefix Product:** Maintaining cumulative values to answer range queries in $\mathcal{O}(1)$ time.

---

### ⚠️ Common Mistakes

1. **Incorrect Zero Handling:** Trying to keep `0` inside the prefix array leads to division by zero or invalid products.
2. **Off-by-One in Indexing:** Forgetting that $l$ has an initial element `1` at index `0`. Without this base element, `getProduct(k)` when $k$ equals the number of added non-zero elements would result in an index out-of-bounds error or require custom `if-else` branches.
3. **Misinterpreting `k >= n`:** Thinking that $k \ge n$ is an out-of-bounds error instead of correctly realizing it signifies that the requested sequence contains a zero.

---

### 🚀 Optimization Notes

- **Memory Re-allocation:** In `add(int num)`, when `num == 0`, the solution instantiates a new object using `l = new ArrayList<>(); l.add(1);`. While functionally correct, calling `l.clear(); l.add(1);` reuses the internal buffer of the existing `ArrayList`, reducing unnecessary object creations and garbage collection overhead.
- **Optimal Time Complexity:** Both operations already run in optimal $\mathcal{O}(1)$ time complexity, fulfilling the follow-up requirement of the problem.
