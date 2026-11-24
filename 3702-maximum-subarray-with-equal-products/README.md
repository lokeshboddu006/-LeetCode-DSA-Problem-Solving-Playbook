<h2><a href="https://leetcode.com/problems/maximum-subarray-with-equal-products">Maximum Subarray With Equal Products</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an array of <strong>positive</strong> integers <code>nums</code>.</p>

<p>An array <code>arr</code> is called <strong>product equivalent</strong> if <code>prod(arr) == lcm(arr) * gcd(arr)</code>, where:</p>

<ul>
	<li><code>prod(arr)</code> is the product of all elements of <code>arr</code>.</li>
	<li><code>gcd(arr)</code> is the <span data-keyword="gcd-function">GCD</span> of all elements of <code>arr</code>.</li>
	<li><code>lcm(arr)</code> is the <span data-keyword="lcm-function">LCM</span> of all elements of <code>arr</code>.</li>
</ul>

<p>Return the length of the <strong>longest</strong> <strong>product equivalent</strong> <span data-keyword="subarray-nonempty">subarray</span> of <code>nums</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,1,2,1,1,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">5</span></p>

<p><strong>Explanation:</strong>&nbsp;</p>

<p>The longest product equivalent subarray is <code>[1, 2, 1, 1, 1]</code>, where&nbsp;<code>prod([1, 2, 1, 1, 1]) = 2</code>,&nbsp;<code>gcd([1, 2, 1, 1, 1]) = 1</code>, and&nbsp;<code>lcm([1, 2, 1, 1, 1]) = 2</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [2,3,4,5,6]</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong>&nbsp;</p>

<p>The longest product equivalent subarray is <code>[3, 4, 5].</code></p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,2,3,1,4,5,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">5</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code relies on a key number theory property:
1. For **any** 2 numbers $a$ and $b$, the identity $a \times b = \text{lcm}(a, b) \times \text{gcd}(a, b)$ always holds. Thus, any subarray of length 2 automatically satisfies the condition `prod(arr) == lcm(arr) * gcd(arr)`.
2. For subarrays of length $\ge 3$, if all elements in the subarray are **pairwise coprime** ( meaning no two elements share a prime factor greater than 1), then:
   - $\text{gcd}(\text{subarray}) = 1$
   - $\text{lcm}(\text{subarray}) = \text{product}(\text{subarray})$
   - Therefore, $\text{prod} = \text{lcm} \times \text{gcd} = \text{prod} \times 1$.

By using a **sliding window**, the solution maintains a contiguous range of elements `[left, right]` where every element is coprime to the product of all existing elements in the window. 

---

### 🔍 Approach

1. **Initialization**:
   - `best = 2`: Sets the initial answer to 2, since any subarray of length 2 trivially satisfies `prod == lcm * gcd`.
   - `left = 0`: The left boundary of the sliding window.
   - `product = 1`: Holds the running product of elements inside the current sliding window `[left, right - 1]`.

2. **Expanding the Window (`right` pointer loop)**:
   - Iterates through `nums` using the `right` pointer and considers `x = nums[right]`.

3. **Shrinking the Window (`while` loop)**:
   - Before adding `x` to the window, checks `gcd(product, x) > 1`.
   - If `gcd(product, x) > 1`, `x` shares a common prime factor with at least one element in the current window.
   - To restore coprimality, the window shrinks from the left by dividing `product /= nums[left]` and incrementing `left++` until `x` is coprime with `product`.

4. **Updating Window & Result**:
   - `product *= x`: Includes the current element `x` into the window product.
   - `best = Math.max(best, right - left + 1)`: Updates the max subarray length found so far.

5. **Helper Function `gcd(a, b)`**:
   - Implements the standard recursive Euclidean algorithm to calculate the Greatest Common Divisor of two integers.

---

### 🧩 Algorithm

- **Pattern**: Sliding Window (Two Pointers) with Euclidean GCD.
- **Invariant**: At the end of each iteration of the `right` pointer, every pair of elements within `nums[left...right]` is pairwise coprime, ensuring `gcd(window) == 1` and `lcm(window) == product(window)`.

---

### ✅ Why This Works

- **Base Case (Length 2)**: Initializing `best = 2` guarantees that even if no valid pairwise coprime subarray of length $\ge 3$ exists, the answer defaults to 2 (as long as `nums.length >= 2`).
- **Pairwise Coprime Subarrays**: If `gcd(product, x) == 1` holds at every step of window expansion, no two numbers in `nums[left...right]` share any prime factor $> 1$. Under this condition:
  $$\text{gcd}(nums[left...right]) = 1$$
  $$\text{lcm}(nums[left...right]) = \prod_{i=left}^{right} nums[i] = \text{product}$$
  $$\text{lcm} \times \text{gcd} = \text{product} \times 1 = \text{product}$$
Thus, every window generated by this logic is guaranteed to be product-equivalent.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N \log(\text{max\_val}))$
  - The `right` pointer moves from `0` to `N-1`.
  - The `left` pointer moves forward at most `N` times across the entire execution.
  - The `gcd` function takes $\mathcal{O}(\log(\min(a, b)))$ time. Given $nums[i] \le 10$, $\text{gcd}$ operations execute in near $\mathcal{O}(1)$ time.
  - Overall time complexity is linear, $\mathcal{O}(N)$.

- **Space Complexity**: $\mathcal{O}(1)$
  - Only uses primitive variables (`best`, `left`, `product`, `right`, `x`).
  - The recursion stack for `gcd` takes at most $\mathcal{O}(\log(\text{max\_val}))$ call frames, which is $\le 4$ given $nums[i] \le 10$.

---

### 🧠 DSA Pattern

- **Sliding Window / Two Pointers**
- **Math / Number Theory (GCD & Coprimality)**

---

### ⚠️ Common Mistakes

1. **Integer Overflow Risk (General Case)**:
   - If array elements were large, multiplying numbers into `product` inside the sliding window could quickly overflow a 32-bit signed integer (`int`). 
   - However, for the given problem constraints ($nums[i] \le 10$), the only prime factors $\le 10$ are $2, 3, 5, 7$. The maximum pairwise coprime product inside any window cannot exceed $2 \times 3 \times 5 \times 7 = 210$, so overflow does not occur.

2. **Assuming length 1 or 2 require special handling**:
   - Length 1 is not possible per constraints ($nums.length \ge 2$). Length 2 always satisfies `a * b == lcm(a, b) * gcd(a, b)` regardless of whether $\text{gcd}(a, b) = 1$ or $> 1$. Initializing `best = 2` handles all length-2 cases correctly.

---

### 🚀 Optimization Notes

- **Optimal for Constraints**: Because $nums[i] \le 10$, `product` stays very small ($\le 210$). The solution is optimal, extremely clean, and executes in $O(N)$ time with $O(1)$ memory.
