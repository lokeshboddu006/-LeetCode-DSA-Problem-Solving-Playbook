<h2><a href="https://leetcode.com/problems/minimum-sum-of-squared-difference">Minimum Sum of Squared Difference</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given two positive <strong>0-indexed</strong> integer arrays <code>nums1</code> and <code>nums2</code>, both of length <code>n</code>.</p>

<p>The <strong>sum of squared difference</strong> of arrays <code>nums1</code> and <code>nums2</code> is defined as the <strong>sum</strong> of <code>(nums1[i] - nums2[i])<sup>2</sup></code> for each <code>0 &lt;= i &lt; n</code>.</p>

<p>You are also given two positive integers <code>k1</code> and <code>k2</code>. You can modify any of the elements of <code>nums1</code> by <code>+1</code> or <code>-1</code> at most <code>k1</code> times. Similarly, you can modify any of the elements of <code>nums2</code> by <code>+1</code> or <code>-1</code> at most <code>k2</code> times.</p>

<p>Return <em>the minimum <strong>sum of squared difference</strong> after modifying array </em><code>nums1</code><em> at most </em><code>k1</code><em> times and modifying array </em><code>nums2</code><em> at most </em><code>k2</code><em> times</em>.</p>

<p><strong>Note</strong>: You are allowed to modify the array elements to become <strong>negative</strong> integers.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums1 = [1,2,3,4], nums2 = [2,10,20,19], k1 = 0, k2 = 0
<strong>Output:</strong> 579
<strong>Explanation:</strong> The elements in nums1 and nums2 cannot be modified because k1 = 0 and k2 = 0. 
The sum of square difference will be: (1 - 2)<sup>2 </sup>+ (2 - 10)<sup>2 </sup>+ (3 - 20)<sup>2 </sup>+ (4 - 19)<sup>2</sup>&nbsp;= 579.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1 = 1, k2 = 1
<strong>Output:</strong> 43
<strong>Explanation:</strong> One way to obtain the minimum sum of square difference is: 
- Increase nums1[0] once.
- Increase nums2[2] once.
The minimum of the sum of square difference will be: 
(2 - 5)<sup>2 </sup>+ (4 - 8)<sup>2 </sup>+ (10 - 7)<sup>2 </sup>+ (12 - 9)<sup>2</sup>&nbsp;= 43.
Note that, there are other ways to obtain the minimum of the sum of square difference, but there is no way to obtain a sum smaller than 43.</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == nums1.length == nums2.length</code></li>
	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= nums1[i], nums2[i] &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= k1, k2 &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks us to minimize the sum of squared differences $\sum (nums1[i] - nums2[i])^2$ using at most $k_1$ operations on $nums1$ and $k_2$ operations on $nums2$.

The core mathematical observation implemented in this solution is twofold:
1. **Operations are equivalent and additive:** Increasing $nums1[i]$ by 1 has the exact same impact on reducing $|nums1[i] - nums2[i]|$ as decreasing $nums2[i]$ by 1. Therefore, $k_1$ and $k_2$ can be merged into a single total budget $k = k1 + k2$.
2. **Greedy reduction of largest differences:** Because $x^2$ grows quadratically with $x$, reducing a larger difference (e.g., from $10$ to $9$, saving $100 - 81 = 19$) reduces the total sum of squares far more than reducing a smaller difference (e.g., from $3$ to $2$, saving $9 - 4 = 5$). Thus, we should always greedily decrement the largest current difference down towards smaller values.

Since the maximum possible element value is $10^5$, the maximum absolute difference is also at most $10^5$. Instead of sorting or using a Priority Queue, this solution uses **bucket counting (frequency array)** to count how many pairs have each difference value, and then processes differences from largest to smallest.

### 🔍 Approach

1. **Bucket Initialization & Aggregation:**
   - Create a frequency array `d` of size `100001` where `d[x]` stores the frequency of difference `x = |nums1[i] - nums2[i]|`.
   - Calculate total budget `k = (long) k1 + k2`.
   - Iterate through both arrays simultaneously:
     - Compute absolute difference `x`.
     - Increment `d[x]`.
     - Accumulate total difference `sum`.
     - Track the maximum difference `max`.

2. **Early Exit Condition:**
   - If total difference `sum <= k`, we have enough operations to reduce every difference to `0`. The function immediately returns `0`.

3. **Greedy Level-by-Level Reduction:**
   - Loop `i` backwards from `max` down to `1` as long as operations remain (`k > 0`):
     - `move = Math.min(k, d[i])`: Determine how many elements with difference `i` can be reduced by `1` using the remaining budget `k`.
     - `d[i] -= move`: Reduce the count of elements at level `i`.
     - `d[i - 1] += move`: Shift those reduced elements down to level `i - 1`.
     - `k -= move`: Decrement available operations.

4. **Result Calculation:**
   - Iterate through the frequency array from `0` to `max`.
   - For each difference `i`, add `(long) i * i * d[i]` to `ans`.
   - Return `ans`.

### 🧩 Algorithm

1. **Combined Budget:** $k = k_1 + k_2$
2. **Difference Bucketing:** $d[|nums1[i] - nums2[i]|] \gets d[|nums1[i] - nums2[i]|] + 1$
3. **Greedy Transition:**
   For $i = \text{max}$ down to $1$:
   $$\text{move} = \min(k, d[i])$$
   $$d[i] \gets d[i] - \text{move}$$
   $$d[i-1] \gets d[i-1] + \text{move}$$
   $$k \gets k - \text{move}$$
4. **Final Answer Formula:**
   $$\text{ans} = \sum_{i=0}^{\text{max}} i^2 \cdot d[i]$$

### ✅ Why This Works

- **Optimality of Greedy Strategy:** To minimize $\sum d_i^2$ subject to $\sum \text{reductions} \le k$, reducing the largest difference first gives the maximum delta drop in the squared total. Shifting elements from bucket `i` to bucket `i - 1` correctly simulates decrementing the largest differences first.
- **Handling Multiples at Same Level:** If `k < d[i]`, we reduce as many elements as possible from level `i` to `i - 1` and run out of operations. If `k >= d[i]`, all elements at level `i` drop to `i - 1`, and any accumulated elements at level `i - 1` will be processed in the next loop iteration.
- **Correct Overflow Management:** Sum of squared differences can exceed 32-bit integer limits, so `ans` and the multiplication `(long) i * i * d[i]` are performed using 64-bit integers (`long`).

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N + M)$ where $N$ is the length of `nums1` and $M$ is the maximum absolute difference ($\le 10^5$).
  - Array scan to populate buckets takes $\mathcal{O}(N)$ time.
  - Greedy distribution loop runs at most $M = 10^5$ times.
  - Final loop runs at most $M = 10^5$ times.
  - Overall time complexity is linear and highly optimal.

- **Space Complexity:** $\mathcal{O}(M)$ where $M = 100001$.
  - Auxiliary space is dominated by the fixed-size frequency array `d` of size `100001`, which takes constant $\mathcal{O}(1)$ relative to $N$, or $\mathcal{O}(\max(\text{diff}))$ in general.

### 🧠 DSA Pattern

- **Greedy Approach** (always reduce the largest difference)
- **Bucket / Frequency Counting** (counting difference frequencies to avoid sorting or heap operations)

### ⚠️ Common Mistakes

1. **Integer Overflow during Calculation:** Forgetting to cast `i * i * d[i]` to `long` before multiplication could lead to 32-bit signed integer overflow. The code correctly uses `(long) i * i * d[i]`.
2. **Treating $k_1$ and $k_2$ Separately:** Attempting to process $k_1$ on `nums1` and $k_2$ on `nums2` independently instead of combining them into $k = k_1 + k_2$.
3. **Hardcoding Array Bounds:** Relying on fixed array size `100001` works specifically because constraint $0 \le nums1[i], nums2[i] \le 10^5$ guarantees maximum difference of $10^5$. If constraints changed to $10^9$, this static bucket array would cause an `ArrayIndexOutOfBoundsException` or excessive memory usage.

### 🚀 Optimization Notes

- **Optimal Time Complexity:** This implementation runs in $\mathcal{O}(N + M)$ time, which is strictly better than the typical max-heap $\mathcal{O}((N + K) \log N)$ or sorting $\mathcal{O}(N \log N)$ approaches.
- **Fast Array Iteration:** Using direct array indexing on a flat primitive `int[]` array avoids heap allocations and dynamic object overhead.
