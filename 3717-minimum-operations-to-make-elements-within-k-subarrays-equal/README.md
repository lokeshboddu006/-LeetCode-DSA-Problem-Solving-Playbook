<h2><a href="https://leetcode.com/problems/minimum-operations-to-make-elements-within-k-subarrays-equal">Minimum Operations to Make Elements Within K Subarrays Equal</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given an integer array <code>nums</code> and two integers, <code>x</code> and <code>k</code>. You can perform the following operation any number of times (<strong>including zero</strong>):</p>

<ul>
	<li>Increase or decrease any element of <code>nums</code> by 1.</li>
</ul>

<p>Return the <strong>minimum</strong> number of operations needed to have <strong>at least</strong> <code>k</code> <em>non-overlapping <span data-keyword="subarray-nonempty">subarrays</span></em> of size <strong>exactly</strong> <code>x</code> in <code>nums</code>, where all elements within each subarray are equal.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [5,-2,1,3,7,3,6,4,-1], x = 3, k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">8</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Use 3 operations to add 3 to <code>nums[1]</code> and use 2 operations to subtract 2 from <code>nums[3]</code>. The resulting array is <code>[5, 1, 1, 1, 7, 3, 6, 4, -1]</code>.</li>
	<li>Use 1 operation to add 1 to <code>nums[5]</code> and use 2 operations to subtract 2 from <code>nums[6]</code>. The resulting array is <code>[5, 1, 1, 1, 7, 4, 4, 4, -1]</code>.</li>
	<li>Now, all elements within each subarray <code>[1, 1, 1]</code> (from indices 1 to 3) and <code>[4, 4, 4]</code> (from indices 5 to 7) are equal. Since 8 total operations were used, 8 is the output.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [9,-2,-2,-2,1,5], x = 2, k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Use 3 operations to subtract 3 from <code>nums[4]</code>. The resulting array is <code>[9, -2, -2, -2, -2, 5]</code>.</li>
	<li>Now, all elements within each subarray <code>[-2, -2]</code> (from indices 1 to 2) and <code>[-2, -2]</code> (from indices 3 to 4) are equal. Since 3 operations were used, 3 is the output.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>6</sup> &lt;= nums[i] &lt;= 10<sup>6</sup></code></li>
	<li><code>2 &lt;= x &lt;= nums.length</code></li>
	<li><code>1 &lt;= k &lt;= 15</code></li>
	<li><code>2 &lt;= k * x &lt;= nums.length</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To minimize the total operations to make all elements in a subarray of size `x` equal, the optimal value to transform all elements into is the **median** of that subarray. The total cost for a window of size `x` converted to median $M$ is $\sum |nums[j] - M|$.

This solution breaks the problem into two distinct stages:
1. **Sliding Window Cost Calculation**: Compute the cost to equalize every valid subarray of length `x` using a Fenwick Tree (Binary Indexed Tree) combined with coordinate compression and binary lifting to locate the median and compute prefix sums in logarithmic time.
2. **Dynamic Programming**: Select $k$ non-overlapping subarrays of size `x` with minimum total cost using a space-optimized 1D rolling-array DP.

---

### 🔍 Approach

#### 1. Coordinate Compression
Since values in `nums` can be negative or large ($-10^6$ to $10^6$), direct array indexing is impossible. 
- The solution clones and sorts `nums` into `vals`, removing duplicates to leave $m$ unique sorted elements.
- Each original element `nums[i]` is mapped to its 1-based rank `rk[i]` in `vals` using `Arrays.binarySearch`.

#### 2. Sliding Window with Fenwick Tree (BIT)
- Maintains two BITs: `cnt` (frequency of values in the current window of size `x`) and `sm` (sum of actual values in the current window).
- Iterates `i` from `0` to `n - 1`:
  - Adds `nums[i]` to `cnt` and `sm` at position `rk[i]`.
  - Maintains a window of size `x` by removing `nums[i - x]` when `i >= x`.
  - When $i \ge x - 1$, a full window of size `x` is active:
    - Finds the median rank using **binary lifting** on `cnt` to find the smallest index `pos` where cumulative element count reaches `need = (x + 1) / 2`.
    - Identifies `med = vals[pos]`.
    - Accumulates $s_0$ (sum of elements $\le med$) and $c_0$ (count of elements $\le med$).
    - Computes window operation cost:
      $$\text{cost} = (med \times c_0 - s_0) + ((total - s_0) - med \times (x - c_0))$$
    - Stores the result in `cost[i - x + 1]`.

#### 3. Dynamic Programming for Subarray Selection
- Uses two rolling arrays `prev` and `cur` of size `n + 1`.
- Outer loop runs $k$ times (for selecting $1, 2, \dots, k$ non-overlapping subarrays).
- Inner loop `i` runs from `x` to `n` (representing array prefix length $i$):
  - Transition: `cur[i] = min(cur[i - 1], prev[i - x] + cost[i - x])`
    - Option 1 (`cur[i - 1]`): Do not select a subarray ending at index `i - 1`.
    - Option 2 (`prev[i - x] + cost[i - x]`): Select a subarray of length `x` ending at index `i - 1` (starting at `i - x`).
- Swaps `prev` and `cur` arrays after each of the $k$ iterations.
- Final answer is stored in `prev[n]`.

---

### 🧩 Algorithm

#### Window Cost Calculation (BIT Binary Lifting & Cost Formula)
- Find median bucket index `pos` using binary lifting on `cnt` array:
  - Loop bit width `pw` from `top` down to `1`:
  - If `pos + pw <= m` and `cnt[pos + pw] < rem`, advance `pos += pw`, subtract `cnt[pos]` from `rem`, and add `sm[pos]` to `s0`.
- Split costs into lower half and upper half around median:
  $$\text{Cost}_{\text{lower}} = med \cdot c_0 - s_0$$
  $$\text{Cost}_{\text{upper}} = (total - s_0) - med \cdot (x - c_0)$$

#### DP Recurrence
Let $DP[j][i]$ be the minimum cost to pick $j$ non-overlapping subarrays of size $x$ within prefix of length $i$:

$$DP[j][i] = \begin{cases} 
\infty & \text{if } i < x \\
\min(DP[j][i - 1], DP[j - 1][i - x] + \text{cost}[i - x]) & \text{if } i \ge x 
\end{cases}$$

---

### ✅ Why This Works

1. **Median Minimization**: Mathematically, $\sum_{j} |A_j - c|$ is minimized when $c$ is the median of sequence $A$.
2. **BIT Binary Lifting**: Binary lifting on a Fenwick tree finds the median index in $O(\log m)$ time instead of $O(\log^2 m)$ required by standard binary search with range queries.
3. **Partitioned Operations**: Splitting sum into elements $\le med$ and $> med$ enables exact calculation of total absolute differences without traversing individual window elements.
4. **Dynamic Programming**: Choosing non-overlapping subarrays reduces to standard interval picking, where skipping index $i$ or placing a window of length $x$ ending at $i$ covers all valid non-overlapping configurations optimally.

---

### ⏱️ Complexity

- **Time Complexity**: 
  - Coordinate Compression: $O(N \log N)$ sorting + $O(N \log N)$ binary search rank assignment.
  - Window Cost Computation: $O(N \log M)$ where $M \le N$ is the count of unique elements. Each step updates BIT in $O(\log M)$ and performs binary lifting in $O(\log M)$.
  - Dynamic Programming: $O(K \times N)$ total state transitions ($K$ iterations, $N$ states per iteration).
  - **Overall Time Complexity**: $\mathcal{O}(N \log N + K \cdot N)$. Given $N \le 10^5$ and $K \le 15$, this easily runs well within limits.

- **Space Complexity**:
  - `vals`, `rk`, `cnt`, `sm`: $O(N)$ space.
  - `cost` array: $O(N)$ space.
  - DP arrays `prev` and `cur`: $O(N)$ space.
  - **Overall Space Complexity**: $\mathcal{O}(N)$.

---

### 🧠 DSA Pattern

- **Fenwick Tree / Binary Indexed Tree (BIT)** with **Binary Lifting**
- **Sliding Window**
- **Dynamic Programming** (Space-Optimized / Rolling Array)
- **Coordinate Compression**

---

### ⚠️ Common Mistakes

1. **Integer Overflow**: Element sums ($s_0$, $total$) and window costs can exceed $2^{31}-1$. The implementation correctly uses `long` for `total`, `sm`, `cost`, and DP state values to avoid overflow.
2. **BIT Binary Lifting Setup**: Initializing `top` as the largest power of 2 $\le m$ is crucial. Incorrect `top` power calculation leads to missing ranks during binary lifting.
3. **1-Based vs 0-Based Fenwick Tree Indexing**: Fenwick trees require 1-based indexing (`rk[i] = binarySearch(...) + 1`). Incorrect base indexing breaks lowbit updates (`p += p & -p`).

---

### 🚀 Optimization Notes

- **$O(\log M)$ Median Query via Binary Lifting**: Instead of performing binary search combined with prefix queries (which would be $O(\log^2 M)$), the code queries the BIT using tree binary lifting in a single $O(\log M)$ pass.
- **Space-Optimized DP**: Instead of keeping a full 2D matrix of size $(K + 1) \times (N + 1)$, the code uses two 1D arrays `prev` and `cur` swapped per subproblem count, reducing auxiliary DP space from $O(K \cdot N)$ to $O(N)$.
