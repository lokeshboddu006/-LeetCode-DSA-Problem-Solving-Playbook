<h2><a href="https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero">Minimum Operations to Reduce X to Zero</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer array <code>nums</code> and an integer <code>x</code>. In one operation, you can either remove the leftmost or the rightmost element from the array <code>nums</code> and subtract its value from <code>x</code>. Note that this <strong>modifies</strong> the array for future operations.</p>

<p>Return <em>the <strong>minimum number</strong> of operations to reduce </em><code>x</code> <em>to <strong>exactly</strong></em> <code>0</code> <em>if it is possible</em><em>, otherwise, return </em><code>-1</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,1,4,2,3], x = 5
<strong>Output:</strong> 2
<strong>Explanation:</strong> The optimal solution is to remove the last two elements to reduce x to zero.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [5,6,7,8,9], x = 4
<strong>Output:</strong> -1
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [3,2,20,1,1,3], x = 10
<strong>Output:</strong> 5
<strong>Explanation:</strong> The optimal solution is to remove the last three elements and the first two elements (5 operations in total) to reduce x to zero.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>4</sup></code></li>
	<li><code>1 &lt;= x &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Instead of directly picking elements from the left and right ends of `nums` to sum up to `x`, your solution flips the problem upside down:

Removing elements from the outer edges to get a sum of `x` is identical to finding a **contiguous subarray in the middle** whose elements sum up to `target = total - x`.

To **minimize** the number of operations (number of elements removed from the ends), you need to **maximize** the length of the remaining middle subarray.

Since all numbers in `nums` are positive ($1 \le nums[i] \le 10^4$), adding elements increases the window sum and removing elements decreases it. This monotonic property allows a standard **sliding window (two pointers)** to find the longest subarray summing to `target` efficiently.

---

### 🔍 Approach

1. **Calculate Total Sum**:
   - Loop through `nums` to compute `total`.
   - Calculate `target = total - x`.

2. **Handle Special Edge Cases**:
   - `if (target < 0)`: Even using all elements, the sum is less than `x`. It's impossible to reach `x`, so return `-1`.
   - `if (target == 0)`: The sum of all elements equals `x`. We must remove all `n` elements, so return `n`.

3. **Sliding Window Search**:
   - Maintain `left` pointer starting at `0`, `sum` tracking current window sum, and `longest` tracking the max length of a subarray summing to `target`.
   - Iterate `right` from `0` to `n - 1`:
     - Add `nums[right]` to `sum`.
     - If `sum > target`, shrink the window from the left by subtracting `nums[left]` and incrementing `left` until `sum <= target`.
     - If `sum == target`, update `longest` with `Math.max(longest, right - left + 1)`.

4. **Compute Final Result**:
   - If `longest == -1`, no valid subarray was found, return `-1`.
   - Otherwise, the minimum operations required is `n - longest`.

---

### 🧩 Algorithm

1. **Total Sum Calculation**:
   $$\text{total} = \sum_{i=0}^{n-1} \text{nums}[i]$$
   $$\text{target} = \text{total} - x$$

2. **Sliding Window Monotonic Invariant**:
   - Since $nums[i] > 0$, advancing `right` increases `sum`.
   - Advancing `left` decreases `sum`.

3. **Window Shrink Condition**:
   $$\text{while } (\text{left} \le \text{right} \text{ and } \text{sum} > \text{target}): \quad \text{sum} \gets \text{sum} - \text{nums}[\text{left}], \quad \text{left} \gets \text{left} + 1$$

4. **Answer Mapping**:
   $$\text{Operations} = n - \text{longest}$$

---

### ✅ Why This Works

- **Equivalence**: Total sum of array is $S$. If outer elements sum to $x$, inner elements sum to $S - x$. Finding the shortest outer elements is strictly equivalent to finding the longest contiguous inner block with sum $S - x$.
- **Two Pointer Correctness**: Because all constraints specify $nums[i] \ge 1$, `sum` is strictly monotonic with respect to window boundaries. This guarantees that `right` expanding and `left` shrinking will never skip any valid subarray that sums to `target`.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Computing the total sum takes $\mathcal{O}(n)$.
  - The `right` pointer moves from `0` to `n - 1` once.
  - The `left` pointer moves forward at most $n$ times across the entire loop.
  - Overall time complexity is linear, $\mathcal{O}(n)$.

- **Space Complexity**: $\mathcal{O}(1)$
  - Only a few primitive integer variables (`n`, `total`, `target`, `left`, `right`, `sum`, `longest`) are used. No additional space allocated.

---

### 🧠 DSA Pattern

- **Sliding Window / Two Pointers**
- **Complementary Subarray Transformation** (Converting an outer prefix/suffix problem into an inner contiguous subarray problem)

---

### ⚠️ Common Mistakes

1. **Forgetting `target < 0` / `target == 0` Checks**:
   Without early returns, `target < 0` could cause unnecessary window processing or incorrect results, and `target == 0` requires properly returning `n`.
2. **Confusing Output**:
   Returning `longest` instead of `n - longest` (remember `longest` is the middle subarray size, whereas the question asks for the number of removed outer elements).
3. **Assuming this works with negative numbers**:
   This sliding window approach relies on $nums[i] \ge 1$. If negative values were present, `sum` wouldn't be monotonic, and this approach would fail (a HashMap prefix sum approach would be required instead).

---

### 🚀 Optimization Notes

- **Optimal Complexity**: The solution is already optimal with $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ auxiliary space.
- **Integer Overflow**: The maximum sum of all elements is $10^5 \times 10^4 = 10^9$, which fits comfortably inside a standard 32-bit signed Java `int` (up to $\approx 2.14 \times 10^9$). No `long` type is needed.
