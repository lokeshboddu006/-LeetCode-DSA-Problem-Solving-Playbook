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

The solution tackles the problem of picking up to 4 non-overlapping weighted intervals to maximize total weight, breaking ties by choosing the lexicographically smallest set of original indices.

The key intuition behind this approach is:
1. **Sorting by End Time:** Sorting intervals by their end points ($r_i$) allows us to process intervals sequentially from left to right. When considering a current interval, any valid non-overlapping predecessor must end *strictly before* the current interval's start time ($l_i$).
2. **Dynamic Programming with Selection History:** Since we need both the maximum score and the exact sequence of original indices (sorted lexicographically), each DP state stores a `Node` object containing the total weight (`score`) and an array of selected original indices (`ids`).
3. **Binary Search for Predecessor:** Since intervals are sorted by end time, we can use binary search (`lowerBound`) to quickly find the maximum number of intervals `p` that end strictly before the current interval starts.

---

### 🔍 Approach

#### 1. Interval Augmentation and Sorting
- The 2D array `arr` stores elements as `[left, right, weight, originalIndex]`.
- `arr` is sorted in ascending order of end time (`right`).
- A auxiliary 1D array `ends` is created to store sorted end times, making binary search straightforward.

#### 2. Custom Comparison (`better` method)
The helper method `better(Node a, Node b)` determines which DP node is preferred:
- Compares total `score`: prefers the node with the larger score.
- If scores are equal: compares original index arrays `ids` element by element to prefer the lexicographically smaller sequence of original indices.

#### 3. Binary Search (`lowerBound` method)
- `lowerBound(ends, i - 1, left)` searches within `ends[0 ... i-2]` for the first index where `ends[mid] >= left`.
- The returned index `p` represents the count of intervals in the sorted prefix that end *strictly before* `left` (i.e., non-overlapping with the current interval).

#### 4. DP State and Transitions
- `dp[k][i]` represents the best `Node` using at most `k` non-overlapping intervals selected from the first `i` sorted intervals (`arr[0 ... i-1]`).
- For each interval `i` (1-indexed prefix) and for each `k` from 1 to 4:
  - **Option 1 (Skip interval $i-1$):** `dp[k][i] = dp[k][i-1]`
  - **Option 2 (Take interval $i-1$):** Combine `dp[k-1][p]` with the current interval:
    - Add current interval's original index into the set using `addSorted()`.
    - Create a new `Node` with updated `score` and sorted `ids`.
    - If this `take` node is `better` than current `dp[k][i]`, overwrite `dp[k][i]`.

#### 5. Extracting Result
- Iterate through `k = 1` to `4` for `dp[k][n]` and pick the best overall `Node` using `better()`.
- Return `answer.ids`.

---

### 🧩 Algorithm

#### State Representation
- `dp[k][i]`: A `Node(score, ids)` object representing the optimal selection of at most `k` non-overlapping intervals from sorted prefix `0` to `i-1`.

#### Initialization
- `dp[0][i] = Node(0, [])` for all $0 \le i \le n$ (using 0 intervals yields score 0 and empty index list).

#### Recurrence Relation
For $i$ from $1$ to $n$ and $k$ from $1$ to $4$:
- Let $p = \text{lowerBound}(\text{ends}, i - 1, \text{left}_i)$
- $\text{skip} = dp[k][i-1]$
- $\text{take} = \text{Node}(dp[k-1][p].\text{score} + \text{weight}_i, \text{addSorted}(dp[k-1][p].\text{ids}, \text{originalIndex}_i))$
- $dp[k][i] = \text{better}(\text{take}, \text{skip}) ? \text{take} : \text{skip}$

---

### ✅ Why This Works

1. **Strict Non-overlapping Condition:**
   - `lowerBound` finds the first index where `ends[mid] >= left`.
   - All intervals before index `p` have `ends[j] < left`. Thus, using `dp[k-1][p]` guarantees that none of the previously chosen intervals overlap with the current interval.

2. **Lexicographical Tie-breaking:**
   - Every time a node is created, `addSorted` maintains the chosen original indices in sorted order.
   - `better()` enforces exact lexicographical comparison on arrays of original indices when scores match.

3. **Prefix Subproblem Optimality:**
   - Processing intervals by end time guarantees that subproblems `dp[k-1][p]` only contain intervals completely to the left of the current interval, preserving the optimal substructure property of DP.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N \log N)$
  - Sorting $N$ intervals by end time takes $\mathcal{O}(N \log N)$.
  - For each of the $N$ intervals, `lowerBound` takes $\mathcal{O}(\log N)$.
  - The outer loop runs $N$ times, inner loop runs $K = 4$ times. Array copies/sorts inside `addSorted` take $\mathcal{O}(K \log K) = \mathcal{O}(1)$ operations since $K \le 4$.
  - Total time: $\mathcal{O}(N \log N + N \cdot K \log N) = \mathcal{O}(N \log N)$.

- **Space Complexity:** $\mathcal{O}(N)$
  - Sorting requires $\mathcal{O}(N)$ extra space for `arr` and `ends`.
  - The DP table of size $(K + 1) \times (N + 1) = 5 \times (N + 1)$ stores $O(N)$ `Node` objects, each containing an integer array of size at most $4$.
  - Total space: $\mathcal{O}(N)$.

---

### 🧠 DSA Pattern

- **Dynamic Programming (Weighted Interval Scheduling variant)**
- **Binary Search (`lowerBound`)**
- **Custom Sorting & Tie-Breaking**

---

### ⚠️ Common Mistakes

1. **Incorrect Non-overlapping Boundary (`>=` vs `>`):**
   - Two intervals are overlapping if they share any point (e.g., $r_i = l_j$ overlaps). `ends[mid] >= left` ensures all valid preceding intervals end strictly before `left` (`ends[j] < left`).
2. **Forgetting Original Indices After Sorting:**
   - Sorting by end time alters the original relative order. Storing `originalIndex` in `arr[i][3]` prevents returning sorted array indices instead of input array indices.
3. **Improper Lexicographical Comparison:**
   - Comparing index arrays lexicographically requires sorting the chosen indices of each node (`addSorted`) so that standard array comparison yields the correct order.

---

### 🚀 Optimization Notes

- **Fixed Parameter $K = 4$:** Because $K$ is bounded by 4, copying arrays of length up to 4 in `addSorted` takes negligible constant time.
- **DP Table Space:** Notice that `dp[k][i]` references `dp[k-1][p]` where `p` can be any previous index $\le i-1$. Thus, full row history across $i$ must be retained (we cannot compress $i$ to $\mathcal{O}(1)$ space easily without preserving previous states).
