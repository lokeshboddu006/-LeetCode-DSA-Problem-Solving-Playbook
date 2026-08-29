<h2><a href="https://leetcode.com/problems/make-lexicographically-smallest-array-by-swapping-elements">Make Lexicographically Smallest Array by Swapping Elements</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a <strong>0-indexed</strong> array of <strong>positive</strong> integers <code>nums</code> and a <strong>positive</strong> integer <code>limit</code>.</p>

<p>In one operation, you can choose any two indices <code>i</code> and <code>j</code> and swap <code>nums[i]</code> and <code>nums[j]</code> <strong>if</strong> <code>|nums[i] - nums[j]| &lt;= limit</code>.</p>

<p>Return <em>the <strong>lexicographically smallest array</strong> that can be obtained by performing the operation any number of times</em>.</p>

<p>An array <code>a</code> is lexicographically smaller than an array <code>b</code> if in the first position where <code>a</code> and <code>b</code> differ, array <code>a</code> has an element that is less than the corresponding element in <code>b</code>. For example, the array <code>[2,10,3]</code> is lexicographically smaller than the array <code>[10,2,3]</code> because they differ at index <code>0</code> and <code>2 &lt; 10</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,5,3,9,8], limit = 2
<strong>Output:</strong> [1,3,5,8,9]
<strong>Explanation:</strong> Apply the operation 2 times:
- Swap nums[1] with nums[2]. The array becomes [1,3,5,9,8]
- Swap nums[3] with nums[4]. The array becomes [1,3,5,8,9]
We cannot obtain a lexicographically smaller array by applying any more operations.
Note that it may be possible to get the same result by doing different operations.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,7,6,18,2,1], limit = 3
<strong>Output:</strong> [1,6,7,18,1,2]
<strong>Explanation:</strong> Apply the operation 3 times:
- Swap nums[1] with nums[2]. The array becomes [1,6,7,18,2,1]
- Swap nums[0] with nums[4]. The array becomes [2,6,7,18,1,1]
- Swap nums[0] with nums[5]. The array becomes [1,6,7,18,1,2]
We cannot obtain a lexicographically smaller array by applying any more operations.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,7,28,19,10], limit = 3
<strong>Output:</strong> [1,7,28,19,10]
<strong>Explanation:</strong> [1,7,28,19,10] is the lexicographically smallest array we can obtain because we cannot apply the operation on any two indices.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= limit &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem allows swapping any two elements if their absolute difference is at most `k`. Because the swap operation is transitive (if $A$ can swap with $B$ and $B$ can swap with $C$, then $A$ can swap with $C$), all elements that can directly or indirectly reach each other form a **connected component** (or group).

Within any connected component, we can rearrange the elements into any order we like among their original positions. To make the final array **lexicographically smallest**, we should place the smallest available values of each group into the smallest available original positions (indices) of that group.

By sorting the indices based on their values, elements that belong to the same connected component will appear consecutively because adjacent values in sorted order with a difference $\le k$ bridge the connection between elements.

### 🔍 Approach

1. **Initialize and Sort Indices**:
   - Create an array `p` of size `n` populated with indices `0` to `n - 1`.
   - Sort `p` using `Arrays.sort` with a custom comparator: `(i, j) -> Integer.compare(a[i], a[j])`. This arranges the indices in non-decreasing order of their values in `a`.

2. **Group Connected Components (Two Pointers)**:
   - Use two pointers `i` and `j` to scan through the sorted indices `p`.
   - Advance `j` as long as `a[p[j]] - a[p[j - 1]] <= k`. This identifies a contiguous segment `p[i ... j-1]` where every element is connected to the next.

3. **Collect and Sort Original Positions**:
   - Extract the subset of indices for the current group into a new array `x = Arrays.copyOfRange(p, i, j)`.
   - Sort `x` in standard ascending order so that `x[0] < x[1] < ... < x[x.length - 1]`. These represent the original positional indices where the group's elements resided.

4. **Assign Values Greedily**:
   - Iterate through the group with index `t`: place the $t$-th smallest value `a[p[i + t]]` into the $t$-th smallest original position `x[t]` of the result array `r`:
     `r[x[t]] = a[p[i + t]]`.

5. **Advance and Return**:
   - Set `i = j` to process the next connected group.
   - Once all elements are processed, return `r`.

### 🧩 Algorithm

- **Group Identification**:
  - Iterate `i` from `0` to `n - 1`.
  - Expand `j = i + 1` while `j < n` and `a[p[j]] - a[p[j - 1]] <= k`.
- **Index Sorting & Value Placement**:
  - `x = p[i ... j-1]`
  - `sort(x)`
  - `r[x[t]] = a[p[i + t]]` for $0 \le t < |x|$
  - `i = j`

### ✅ Why This Works

- **Reachability Invariant**: Sorting values allows us to identify all reachable pairs efficiently. If `a[p[j]] - a[p[j-1]] > k`, then no element from `p[0 ... j-1]` can reach any element from `p[j ... n-1]`, ensuring groups are completely partitioned correctly.
- **Optimal Placement**: Within each group, every element can move to any position occupied by the group. To minimize the array lexicographically, the smallest index in the group must receive the smallest value in the group, the second smallest index receives the second smallest value, and so on.

### ⏱️ Complexity

- **Time Complexity**: 
  - Sorting `p` takes $O(n \log n)$ time.
  - The outer loop and `j` pointer scan `p` linearly in $O(n)$ total iterations across all groups.
  - Sorting each temporary sub-array `x` takes $O(|x| \log |x|)$. Since the sum of lengths $\sum |x| = n$, the total time spent sorting all `x` arrays is bounded by $O(n \log n)$.
  - **Total Time Complexity**: $O(n \log n)$.

- **Space Complexity**:
  - Result array `r` takes $O(n)$ space.
  - Wrapped index array `p` takes $O(n)$ space.
  - Sub-array `x` allocation takes up to $O(n)$ space across all groups.
  - **Total Space Complexity**: $O(n)$.

### 🧠 DSA Pattern

- **Sorting**: Used to order both values (to identify components) and indices (to assign values sequentially).
- **Two Pointers / Sliding Window**: Used to segment `p` into contiguous connected components.
- **Greedy Strategy**: Matching the smallest indices to the smallest values in each group.

### ⚠️ Common Mistakes

- **Primitive Array Sorting with Custom Comparator**: Java's `Arrays.sort` with a `Comparator` requires object reference types (`Integer[]` instead of `int[]`). Using primitive `int[]` would fail compilation when passing a custom lambda comparator.
- **Index Out of Bounds during Grouping**: Forgetting `j < n` before checking `a[p[j]] - a[p[j - 1]] <= k` would cause an `ArrayIndexOutOfBoundsException`. The code correctly places `j < n` first in the `while` condition.

### 🚀 Optimization Notes

- **Object Overhead**: Using `Integer[] p` incurs boxing and memory overhead. Creating a custom pair or flattening indices into dynamic primitives would reduce GC pressure, though $O(n \log n)$ time complexity remains unchanged.
- **Repeated Sub-array Allocations**: `Arrays.copyOfRange(p, i, j)` allocates a new `Integer[]` for every connected component. Sorting positions in-place or using primitive arrays could reduce memory allocations.
- **Optimality**: Asymptotically, the solution is already optimal ($O(n \log n)$ time and $O(n)$ extra space).
