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

To minimize the number of operations required to make all elements in a subarray equal, the optimal target value for every element in that subarray is its **median**. The sum of absolute differences from all elements to the median gives the minimal cost for that specific window of size `x`.

Your solution breaks the problem into two distinct phases:
1. **Sliding Window Median Calculation**: Dynamically compute the cost of converting each window of size `x` to its median using two balanced `TreeMap` structures.
2. **Dynamic Programming Selection**: Choose `k` non-overlapping subarrays of size `x` from the computed window costs such that the total cost is minimized.

### 🔍 Approach

1. **Precomputing Window Costs (`w`)**:
   - Create an array `w` of length `n - x + 1` where `w[i]` stores the minimum operations needed to make all elements equal in the subarray `a[i ... i + x - 1]`.
   - Use two sorted frequency maps (`TreeMap<Integer, Integer>`):
     - `l`: Stores the smaller half of the elements in the current window.
     - `r`: Stores the larger half of the elements in the current window.
   - Maintain running sums (`s` for `l`, `t` for `r`) and element counts (`lSz` for `l`, `rSz` for `r`).
   - For every index `i`:
     - **Insert `a[i]`**: Place `a[i]` in `l` if it's less than or equal to `l.lastKey()`, otherwise place it in `r`.
     - **Rebalance**: Keep `lSz` equal to `rSz` or `rSz + 1`.
     - **Remove Outgoing Element**: When `i >= x`, remove `a[i - x]` from whichever map it belongs to and rebalance again.
     - **Compute Cost**: When `i >= x - 1`, the median is `m = l.lastKey()`. The cost for the window starting at `i - x + 1` is computed as:
       $$\text{cost} = (m \times lSz - s) + (t - m \times rSz)$$

2. **Selecting $k$ Subarrays via Dynamic Programming (`d`)**:
   - Define DP state `d[i][j]`: the minimum operations to select `j` non-overlapping subarrays of size `x` within the prefix `a[0 ... i-1]`.
   - Initialize `d` with a large value `1e15` representing infinity, and `d[0][0] = 0`.
   - Iterate through indices `i` from `0` to `n - 1` and counts `j` from `0` to `k`:
     - **Option 1 (Skip element `i`)**: Pass the minimum cost to the next index without starting a subarray:
       `d[i + 1][j] = min(d[i + 1][j], d[i][j])`
     - **Option 2 (Pick subarray starting at `i`)**: If `j < k` and `i + x <= n`, pick the subarray of length `x` starting at index `i` (cost `w[i]`):
       `d[i + x][j + 1] = min(d[i + x][j + 1], d[i][j] + w[i])`

3. **Result**:
   - `d[n][k]` holds the minimum total operations needed to pick `k` non-overlapping subarrays of length `x`.

### 🧩 Algorithm

1. **Sliding Window Median Formula**:
   - Median $m = \text{l.lastKey()}$
   - Window Cost $w[\text{start}] = (m \cdot lSz - s) + (t - m \cdot rSz)$

2. **DP Recurrence**:
   - State: $d[i][j]$ = minimum cost using prefix of length $i$ with $j$ subarrays picked.
   - Transitions:
     $$d[i + 1][j] = \min(d[i + 1][j], \; d[i][j])$$
     $$d[i + x][j + 1] = \min(d[i + x][j + 1], \; d[i][j] + w[i]) \quad \text{for } j < k \text{ and } i + x \le n$$

3. **Map Invariant**:
   - $lSz \in \{rSz, rSz + 1\}$ at all times.

### ✅ Why This Works

- **Median Minimization Property**: For any sequence of numbers, the value $m$ that minimizes $\sum |a_i - m|$ is the median of the sequence. Splitting elements into lower half $l$ and upper half $r$ allows calculating $\sum_{x \in l} (m - x) + \sum_{x \in r} (x - m)$ efficiently as $(m \cdot lSz - s) + (t - m \cdot rSz)$.
- **DP Optimal Substructure**: Selecting non-overlapping subarrays of fixed length $x$ guarantees that picking a subarray starting at index $i$ jumps directly to index $i + x$, maintaining non-overlapping boundaries correctly.

### ⏱️ Complexity

- **Time Complexity**: 
  - **Sliding Window**: $O(n \log x)$ — Each of the $n$ elements is added and removed from a `TreeMap` of size at most $x$.
  - **DP State Transitions**: $O(n \cdot k)$ — Iterating over $n$ indices and $k$ subarray counts with $O(1)$ transitions per state.
  - **Total Time Complexity**: $\mathcal{O}(n \log x + n \cdot k)$
- **Space Complexity**: 
  - $\mathcal{O}(n)$ for array `w`.
  - $\mathcal{O}(n \cdot k)$ for DP matrix `d`.
  - $\mathcal{O}(x)$ for the two `TreeMap` data structures `l` and `r`.
  - **Total Space Complexity**: $\mathcal{O}(n \cdot k)$

### 🧠 DSA Pattern

- **Sliding Window + Median (Two Heaps / Two TreeMaps)**
- **Dynamic Programming (Knapsack-like / Interval Selection)**

### ⚠️ Common Mistakes

1. **`TreeMap.put()` return value reliance**:
   - The code uses `if (l.put(o, l.get(o) - 1) == 1) l.remove(o);`. In Java, `Map.put` returns the *previous* value associated with the key. Checking `== 1` correctly identifies that the frequency was 1 prior to decrement, meaning it became 0 after the put and must be removed.
2. **Integer Overflow**:
   - Elements can range from $-10^6$ to $10^6$, and window sizes $x$ up to $10^5$. Sums `s` and `t` as well as costs `w[i]` can exceed 32-bit signed integers. The implementation correctly uses `long` for `s`, `t`, `w`, and `d`.
3. **DP Infinity Threshold**:
   - Using `(long) 1e15` for unvisited DP states avoids overflow when adding `w[i]` (which could happen if `Long.MAX_VALUE` were used).

### 🚀 Optimization Notes

- **Memory Optimization**: The DP table `d` is of size $(n + 1) \times (k + 1)$. Since state $i + 1$ only depends on state $i$ (and state $i + x$ takes transitions from $i$), the DP could technically be compressed, but given $k \le 15$ and $n \le 10^5$, the $(n + 1) \times (k + 1)$ `long` array fits well within standard memory limits (~12 MB).
- **Tree Maps vs. Dual Priority Queue / Indexed Data Structure**: `TreeMap` provides easy `firstKey()` and `lastKey()` access in $O(\log U)$ time (where $U$ is the number of unique elements in the window), which is clean and efficient enough for this problem.
