<h2><a href="https://leetcode.com/problems/maximum-score-of-non-overlapping-intervals">Maximum Score of Non-overlapping Intervals</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given a 2D integer array <code>intervals</code>, where <code>intervals[i] = [l<sub>i</sub>, r<sub>i</sub>, weight<sub>i</sub>]</code>. Interval <code>i</code> starts at position <code>l<sub>i</sub></code> and ends at <code>r<sub>i</sub></code>, and has a weight of <code>weight<sub>i</sub></code>. You can choose <em>up to</em> 4 <strong>non-overlapping</strong> intervals. The <strong>score</strong> of the chosen intervals is defined as the total sum of their weights.</p>

<p>Return the <span data-keyword="lexicographically-smaller-array">lexicographically smallest</span> array of at most 4 indices from <code>intervals</code> with <strong>maximum</strong> score, representing your choice of non-overlapping intervals.</p>

<p>Two intervals are said to be <strong>non-overlapping</strong> if they do not share any points. In particular, intervals sharing a left or right boundary are considered overlapping.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">intervals = [[1,3,2],[4,5,2],[1,5,5],[6,9,3],[6,7,1],[8,9,1]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[2,3]</span></p>

<p><strong>Explanation:</strong></p>

<p>You can choose the intervals with indices 2, and 3 with respective weights of 5, and 3.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">intervals = [[5,8,1],[6,7,7],[4,7,3],[9,10,6],[7,8,2],[11,14,3],[3,5,5]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[1,3,5,6]</span></p>

<p><strong>Explanation:</strong></p>

<p>You can choose the intervals with indices 1, 3, 5, and 6 with respective weights of 7, 6, 3, and 5.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= intevals.length &lt;= 5 * 10<sup>4</sup></code></li>
	<li><code>intervals[i].length == 3</code></li>
	<li><code>intervals[i] = [l<sub>i</sub>, r<sub>i</sub>, weight<sub>i</sub>]</code></li>
	<li><code>1 &lt;= l<sub>i</sub> &lt;= r<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= weight<sub>i</sub> &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of your solution is to convert the problem of picking up to 4 non-overlapping intervals into a **0/1 Knapsack-style Dynamic Programming** problem.

By first sorting all interval indices based on their start times, you can process intervals sequentially from left to right. For any interval, you have two choices:
1. **Skip** the interval and move to the next interval in sorted order.
2. **Take** the interval, add its weight to the total, record its original index, and jump directly to the next non-overlapping interval (found using binary search).

To track both the weight sum and the selected interval indices for tie-breaking, you encapsulate these into a custom `Pair` class and compute state comparisons using a helper comparison function (`better`).

---

### 🔍 Approach

1. **Sorting Indices (`order`)**:
   - You create an `order` list containing indices `0` to `n - 1`.
   - You sort `order` based on the starting position of intervals (`intervals.get(a).get(0)`).

2. **Precomputing Non-Overlapping Next Indices (`next`)**:
   - For each sorted position `i`, you need to know which sorted position comes immediately after interval `i` finishes.
   - You use `lowerBound` to binary search for the first interval in `order` whose start time is $\ge \text{current end time} + 1$ (`intervals.get(id).get(1) + 1`).
   - You store this in `next[i]`.

3. **Helper Data Structure & Tie-Breaking (`Pair` and `better`)**:
   - `Pair` stores `sum` (accumulated weight) and `ids` (list of original indices).
   - `better(a, b)` compares two solution outcomes:
     - Higher `sum` wins.
     - If sums are tied, it sorts both index lists and compares them element-wise to pick the **lexicographically smaller** sequence of indices.

4. **Recursive DP with Memoization (`solve`)**:
   - **State**: `solve(pos, count)` represents the best outcome starting from index `pos` in `order`, having already picked `count` intervals.
   - **Base Cases**: If `pos == order.size()` or `count == 4`, return a `Pair(0, [])`.
   - **Memoization Check**: If `dp[pos][count]` exists, return a **deep copy** using `dp[pos][count].copy()`.
   - **Transitions**:
     - `skip`: Call `solve(in, order, pos + 1, count)`.
     - `take`: Call `solve(in, order, next[pos], count + 1)`, then add the current interval's weight and include its index in `take.ids`.
   - Take the better of `skip` and `take`, store its copy in `dp[pos][count]`, and return it.

5. **Reconstructing Final Answer**:
   - Call `solve(intervals, order, 0, 0)`.
   - Retrieve the optimal index list `ans`, sort it in ascending order, and return it as a 1D array `res`.

---

### 🧩 Algorithm

- **DP State Representation**:
  - `dp[pos][count]`: stores the optimal `Pair` (maximum weight sum and lexicographically smallest index selection) considering intervals from index `pos` onward when `count` intervals have been selected so far.

- **Transitions**:
  $$\text{skip} = \text{solve}(pos + 1, count)$$
  $$\text{take} = \text{solve}(next[pos], count + 1) + \text{weight}[id]$$
  $$dp[pos][count] = \text{better}(\text{skip}, \text{take})$$

- **Binary Search Condition (`lowerBound`)**:
  Find the smallest index $m \in [0, N]$ such that:
  $$\text{intervals}[\text{order}[m]][0] \ge \text{intervals}[id][1] + 1$$

---

### ✅ Why This Works

- **Non-overlapping Guarantee**: Searching for $\ge \text{end\_time} + 1$ ensures that picked intervals never share any coordinate points.
- **Optimal Substructure**: Decisions made at step `pos` depend only on how many intervals remain available (`4 - count`) and the earliest available starting position (`pos`).
- **Defensive Copying**: Calling `.copy()` when returning cached DP states ensures that state modifications in one recursive path (such as `take.ids.add(id)`) do not corrupt cached results used by other recursive paths.
- **Tie-Breaking Rule**: `better()` explicitly sorts and compares lists of original indices lexicographically, ensuring compliance with the problem's tie-breaking criteria.

---

### ⏱️ Complexity

- **Time Complexity**: 
  - **Index Sorting**: $O(N \log N)$ to sort `order`.
  - **Precomputing `next` Array**: $N$ binary searches over $N$ elements $\rightarrow O(N \log N)$.
  - **DP Transitions**: There are $N \times 4$ total states. At each state, state transitions take $O(1)$ time (since list lengths in `better()` are at most 4, list sorting inside `better()` runs in $O(4 \log 4) = O(1)$).
  - **Total Time Complexity**: $\mathcal{O}(N \log N)$.

- **Space Complexity**:
  - `dp` array of size $N \times 4$ holding `Pair` objects: $\mathcal{O}(N)$.
  - `next` array of size $N$: $\mathcal{O}(N)$.
  - Recursion stack: Up to $\mathcal{O}(N)$ depth.
  - **Total Space Complexity**: $\mathcal{O}(N)$.

---

### 🧠 DSA Pattern

- **Dynamic Programming (Top-Down with Memoization)**
- **Binary Search (Lower Bound)**
- **Coordinate Sorting / Index Mapping**

---

### ⚠️ Common Mistakes

1. **State Mutation Without Copying**:
   - In top-down DP where objects (like `Pair`) are returned and modified (`take.ids.add(id)`), forgetting to perform a deep copy (`copy()`) causes reference leaks where modifying `take` mutates the cached object inside `dp[pos][count]`. Your solution avoids this by using `.copy()`.
2. **Boundary Condition Off-By-One**:
   - Passing `intervals.get(id).get(1) + 1` to `lowerBound` is critical because intervals sharing boundaries (e.g. $[1, 3]$ and $[3, 5]$) overlap. Searching for $\ge r_i + 1$ guarantees strict non-overlapping choices.
3. **In-place List Sorting in Comparators**:
   - `Collections.sort(a.ids)` mutates `a.ids` in place during comparison. While harmless here because `copy()` creates fresh lists, in-place sorting within comparative helpers can be dangerous if un-copied references are stored.

---

### 🚀 Optimization Notes

- **Object Allocation Overhead**:
  - Creating new `Pair` objects and cloning lists on every DP return call causes significant heap allocations and garbage collection overhead in Java.
- **Avoiding In-State List Manipulations**:
  - Instead of carrying `List<Integer> ids` through every DP state and comparing lists in `better()`, you could store only the optimal numeric weight in DP, and reconstruct the chosen indices after the DP completes by re-evaluating decisions (`skip` vs `take`).
