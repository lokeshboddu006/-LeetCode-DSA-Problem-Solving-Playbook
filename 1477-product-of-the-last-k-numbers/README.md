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

The key observation behind this solution is using **Prefix Products** combined with a **Reset Strategy** for zeroes.

1. **Prefix Products for Range Multiplication**: Just like prefix sums allow calculating range sums using subtraction, prefix products allow calculating range products using division:
   $$\text{Product of last } k \text{ numbers} = \frac{\text{PrefixProduct}[end]}{\text{PrefixProduct}[end - k]}$$

2. **Handling Zeros**: Division by zero is undefined, and once a zero enters a standard prefix product, all subsequent products become zero. Your implementation solves this cleanly: whenever `0` is added, the entire prefix array is cleared and reset. 

If $k$ asks for more numbers than have been added since the last zero (i.e., $k \ge \text{pref.size()}$), it guarantees that at least one `0` was part of those last $k$ numbers, so the answer is immediately `0`.

---

### 🔍 Approach

1. **Initialization (`ProductOfNumbers()`)**:
   - Maintains a dynamic list `pref` storing cumulative products of non-zero streams.
   - Starts with `pref = [1]`. This base value `1` serves as a dummy seed so division works cleanly even when asking for all elements added since the last zero.

2. **Adding a Number (`add(int num)`)**:
   - **When `num == 0`**: Clears the `pref` array and resets it to `[1]`. This discards all prefix history before the zero, effectively resetting the baseline.
   - **When `num != 0`**: Takes the last prefix product (`pref.get(pref.size() - 1)`), multiplies it by `num`, and appends it to `pref`.

3. **Querying Product of Last $k$ Numbers (`getProduct(int k)`)**:
   - Gets $n = \text{pref.size()}$.
   - **Condition `k >= n`**: Indicates that the last $k$ numbers extend past the location of the most recent `0` (or past the start of the stream). Since a zero was included in that range, it returns `0`.
   - **Condition `k < n`**: Calculates the range product using prefix division:
     `pref.get(n - 1) / pref.get(n - k - 1)`

---

### 🧩 Algorithm

- **Data Structure Invariant**: `pref[i]` stores the cumulative product of the non-zero sub-stream starting after the latest zero up to index $i$.
- **Reset Condition**:
  $$\text{if } num == 0 \implies \text{pref} = [1]$$
- **State Transition**:
  $$\text{pref}[i] = \text{pref}[i - 1] \times num \quad (num \neq 0)$$
- **Range Query Logic**:
  $$\text{getProduct}(k) = \begin{cases} 0 & \text{if } k \ge \text{pref.size()} \\ \frac{\text{pref}[n - 1]}{\text{pref}[n - k - 1]} & \text{if } k < \text{pref.size()} \end{cases}$$

---

### ✅ Why This Works

- **Correctness of Prefix Division**: For a sequence of non-zero numbers $a_1, a_2, \dots, a_m$, the product of the last $k$ elements is $a_{m-k+1} \times \dots \times a_m$. By prefix definition:
  $$\frac{\text{pref}[m]}{\text{pref}[m-k]} = \frac{a_1 \cdot a_2 \dots a_m}{a_1 \cdot a_2 \dots a_{m-k}} = a_{m-k+1} \dots a_m$$
- **Correctness of Zero Reset**: Because any range product containing $0$ evaluates to $0$, we only need to track numbers added *after* the most recent $0$. If $k$ exceeds the count of numbers added after the last zero, a zero is implicitly inside the requested window, making `0` the correct response.

---

### ⏱️ Complexity

- **Time Complexity**:
  - `add(int num)`: **$O(1)$ amortized**. Appending to `ArrayList` or clearing and appending `1` takes constant time.
  - `getProduct(int k)`: **$O(1)$**. Single size check, list index accesses, and division operation.

- **Space Complexity**:
  - **$O(N)$** total space, where $N$ is the number of non-zero elements added since the last zero. At most $O(Q)$ space where $Q$ is the total number of calls to `add`.

---

### 🧠 DSA Pattern

- **Prefix Sum / Prefix Product**: Maintaining cumulative values to perform $O(1)$ range queries.
- **Array Resets**: Handling invalidating elements (zeroes) by resetting state.

---

### ⚠️ Common Mistakes

1. **Off-by-one in Division Indexing**:
   - `pref.get(n - 1) / pref.get(n - k - 1)` relies on having the dummy `1` at index `0`. Missing the dummy element would lead to `IndexOutOfBoundsException` when $k$ equals the number of non-zero elements.
2. **Boundary Check Operator**:
   - Using `k > n` instead of `k >= n` would fail when $k$ equals $n$, because `pref` contains $n$ elements (1 dummy + $n-1$ numbers). So if $k = n$, it is asking for $n$ numbers, which includes the zero before the reset.

---

### 🚀 Optimization Notes

- **Optimal Time Complexity**: This solution already achieves $O(1)$ time complexity for both `add` and `getProduct`, meeting the follow-up requirement of the problem.
- **Space Optimization**: `ArrayList.clear()` retains the internal array capacity, avoiding dynamic array re-allocations on subsequent `add` operations after a zero.
