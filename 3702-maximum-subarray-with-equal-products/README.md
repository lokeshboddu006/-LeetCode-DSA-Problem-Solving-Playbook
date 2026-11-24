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

The problem requires finding the maximum length of a subarray where the product of all its elements is equal to the product of its Least Common Multiple (LCM) and Greatest Common Divisor (GCD), i.e., `prod(arr) == lcm(arr) * gcd(arr)`.

Given the small array size ($N \le 100$) and small value bounds ($nums[i] \le 10$), your solution incrementally expands every possible starting index $i$ to ending index $j$. As each element $nums[j]$ is added to the subarray, the running product, GCD, and LCM are updated in $O(1)$ amortized steps. A threshold check on the running product prevents integer overflow and cuts off impossible subarrays early.

### 🔍 Approach

1. **Initialization**:
   - `n = nums.length` stores the length of the input array.
   - `m = 1` tracks the maximum length of a valid product-equivalent subarray found so far.

2. **Outer Loop (Subarray Start `i`)**:
   - Iterates `i` from `0` to `n - 1`.
   - Initializes running state for the single-element subarray starting at index `i`:
     - `p = nums[i]` (running product)
     - `g = nums[i]` (running GCD)
     - `l = nums[i]` (running LCM)

3. **Inner Loop (Subarray End `j`)**:
   - Iterates `j` from `i + 1` to `n - 1` to incrementally extend the subarray `nums[i..j]`.
   - Updates the product: `p *= nums[j]`.
   - **Pruning Check**: `if (p > 25200) break;`. Because elements are $\le 10$, a product exceeding $25200$ will either overflow standard 32-bit integers or violate valid condition constraints, so the inner loop breaks early.
   - **GCD Update**:
     - Uses inline Euclidean algorithm to calculate $\gcd(g, nums[j])$.
     - Updates `g` to this new GCD value.
   - **LCM Update**:
     - Computes $\gcd(l, nums[j])$ using the inline Euclidean algorithm.
     - Updates `l` using the standard relationship: $\text{LCM}(l, nums[j]) = \frac{l \times nums[j]}{\gcd(l, nums[j])}$.
   - **Condition Evaluation**:
     - Checks if `l * g == p`.
     - If true, updates `m = Math.max(m, j - i + 1)`.

4. **Return Result**:
   - Returns `m`, the maximum valid subarray length found.

### 🧩 Algorithm

The algorithm uses **Subarray Range Expansion with Incremental State Updates**:

1. For each fixed starting index $i$:
   $$\text{State at } j = i: \quad p = nums[i], \quad g = nums[i], \quad l = nums[i]$$
2. For each element $nums[j]$ added at index $j > i$:
   $$p_j = p_{j-1} \times nums[j]$$
   $$g_j = \gcd(g_{j-1}, nums[j])$$
   $$l_j = \frac{l_{j-1} \times nums[j]}{\gcd(l_{j-1}, nums[j])}$$
3. Check invariant/condition:
   $$\text{Valid if } l_j \times g_j == p_j$$

### ✅ Why This Works

- **Incremental Correctness**: The GCD and LCM of a subarray `nums[i..j]` can be computed directly from the GCD and LCM of `nums[i..j-1]` combined with `nums[j]`:
  - $\gcd(a_1, a_2, \dots, a_k) = \gcd(\gcd(a_1, \dots, a_{k-1}), a_k)$
  - $\text{lcm}(a_1, a_2, \dots, a_k) = \text{lcm}(\text{lcm}(a_1, \dots, a_{k-1}), a_k)$
- **Euclidean Algorithm**: The `while (y != 0)` loops correctly compute the greatest common divisor of two integers via repeated remainder operations.
- **Bounding Overflow**: The maximum possible LCM of numbers from $1$ to $10$ is $\text{lcm}(1, 2, \dots, 10) = 2520$. Stopping when $p > 25200$ safely avoids integer overflow in 32-bit signed integers while ensuring no valid subarray configuration is missed.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N^2 \cdot \log(\max(nums)))$
  - Outer loop runs $N$ times.
  - Inner loop runs up to $N$ times.
  - Inside the inner loop, two GCD computations run in $\mathcal{O}(\log(\max(nums)))$ steps.
  - Due to the early exit `if (p > 25200) break;`, the inner loop terminates after very few iterations in practice, making execution fast.

- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - Only uses a few primitive integer variables (`n`, `m`, `p`, `g`, `l`, `x`, `y`, `t`). No dynamic data structures are allocated.

### 🧠 DSA Pattern

- **Math** (GCD & LCM properties, Euclidean algorithm)
- **Brute Force / Enumeration** (All contiguous subarrays via nested loops)

### ⚠️ Common Mistakes

1. **Integer Overflow**: Multiplying running products `p *= nums[j]` and `l * nums[j]` can quickly exceed integer limits if not bounded. Your guard `p > 25200` protects against this.
2. **Variable Overwriting**: Inlining the GCD calculation twice requires resetting intermediate variables (`x` and `y`) carefully before each loop so `g` and `l` are updated correctly.

### 🚀 Optimization Notes

- **Code Readability**: The Euclidean GCD algorithm is duplicated twice inside the inner loop (once for updating `g` and once for updating `l`). Extracting this into a private helper function like `private int gcd(int a, int b)` would eliminate redundant code without affecting performance.
- **Pruning**: The early exit `if (p > 25200) break;` is an effective optimization tailored to the problem constraints ($nums[i] \le 10$).
