<h2><a href="https://leetcode.com/problems/removing-minimum-and-maximum-from-array">Removing Minimum and Maximum From Array</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a <strong>0-indexed</strong> array of <strong>distinct</strong> integers <code>nums</code>.</p>

<p>There is an element in <code>nums</code> that has the <strong>lowest</strong> value and an element that has the <strong>highest</strong> value. We call them the <strong>minimum</strong> and <strong>maximum</strong> respectively. Your goal is to remove <strong>both</strong> these elements from the array.</p>

<p>A <strong>deletion</strong> is defined as either removing an element from the <strong>front</strong> of the array or removing an element from the <strong>back</strong> of the array.</p>

<p>Return <em>the <strong>minimum</strong> number of deletions it would take to remove <strong>both</strong> the minimum and maximum element from the array.</em></p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,<u><strong>10</strong></u>,7,5,4,<u><strong>1</strong></u>,8,6]
<strong>Output:</strong> 5
<strong>Explanation:</strong> 
The minimum element in the array is nums[5], which is 1.
The maximum element in the array is nums[1], which is 10.
We can remove both the minimum and maximum by removing 2 elements from the front and 3 elements from the back.
This results in 2 + 3 = 5 deletions, which is the minimum number possible.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [0,<u><strong>-4</strong></u>,<u><strong>19</strong></u>,1,8,-2,-3,5]
<strong>Output:</strong> 3
<strong>Explanation:</strong> 
The minimum element in the array is nums[1], which is -4.
The maximum element in the array is nums[2], which is 19.
We can remove both the minimum and maximum by removing 3 elements from the front.
This results in only 3 deletions, which is the minimum number possible.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [<u><strong>101</strong></u>]
<strong>Output:</strong> 1
<strong>Explanation:</strong>  
There is only one element in the array, which makes it both the minimum and maximum element.
We can remove it with 1 deletion.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>5</sup> &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
	<li>The integers in <code>nums</code> are <strong>distinct</strong>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To remove both the minimum and maximum elements from the array with the fewest total deletions, you can only delete elements from the front or the back. 

Your solution finds the indices of the minimum and maximum elements in a single pass. After standardizing their positions, it tests every possible number of deletions from the front (from `0` up to `n` elements) and determines the exact number of extra deletions needed from the back to ensure both target elements are removed. By taking the minimum across all possibilities, your code guarantees finding the optimal solution.

### 🔍 Approach

1. **Find Target Indices**: 
   - Initialize `left = 0` and `right = 0`.
   - Iterate through the array starting from index `1` to `n - 1`.
   - Update `left` to store the index of the minimum element and `right` to store the index of the maximum element.

2. **Standardize Index Order**:
   - Perform a swap if `left < right`. 
   - **Important note on variable naming:** After this swap, `right` holds the **smaller** index (closer to index `0`), and `left` holds the **larger** index (closer to index `n - 1`).

3. **Try All Front Deletion Counts**:
   - Initialize `ans = n`.
   - Loop `i` from `0` to `n`, where `i` represents removing `i` elements from the front (covering indices `0` to `i - 1`).

4. **Calculate Back Deletions (`extra`)**:
   - **Case 1 (`right >= i`)**: Neither element was removed by deleting `i` elements from the front. To cover the element at `right` (and by extension `left`, since `left >= right`), back deletions must reach index `right`. The number of back deletions required is `n - right`.
   - **Case 2 (`left >= i`)**: The element at `right` was already removed from the front (since `right < i`), but the element at `left` was not. Back deletions must extend up to index `left`, requiring `n - left` deletions.
   - **Case 3 (Neither condition met)**: Both target elements were already removed by the `i` front deletions, so `extra = 0`.

5. **Update Result**:
   - For each `i`, calculate the total deletions `i + extra` and update `ans = Math.min(ans, i + extra)`.
   - Return `ans`.

### 🧩 Algorithm

1. **Min/Max Search**:
   $$\text{left} = \arg\min_k \text{nums}[k], \quad \text{right} = \arg\max_k \text{nums}[k]$$

2. **Index Ordering**:
   $$\text{if } \text{left} < \text{right}: \quad \text{swap}(\text{left}, \text{right})$$
   *(Invariant after swap: $\text{right} \le \text{left}$)*

3. **Exhaustive Evaluation**:
   $$\text{extra}(i) = \begin{cases} n - \text{right} & \text{if } \text{right} \ge i \\ n - \text{left} & \text{if } \text{left} \ge i \\ 0 & \text{otherwise} \end{cases}$$
   $$\text{ans} = \min_{0 \le i \le n} (i + \text{extra}(i))$$

---

### ✅ Why This Works

Any valid deletion sequence consists of removing some number of elements $i$ from the front and some number of elements $j$ from the back. Since the total length of the array is $n$, $i$ can range anywhere from $0$ to $n$. 

By iterating $i$ from $0$ to $n$, your loop systematically checks every possible front deletion count. For a fixed $i$, the conditional `if-else` block correctly computes the minimal back deletions required to make sure both the minimum and maximum elements are removed. Since all candidate split points between front and back deletions are evaluated, taking the overall minimum guarantees the optimal answer.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Scanning the array to find the min and max indices takes $n - 1$ operations.
  - The loop running from $i = 0$ to $i = n$ performs $n + 1$ iterations, where each iteration does $\mathcal{O}(1)$ work.
  - Total time spent is $(n - 1) + (n + 1) = 2n$ operations, which is $\mathcal{O}(n)$.

- **Space Complexity**: $\mathcal{O}(1)$
  - Only a few primitive integer variables (`n`, `left`, `right`, `ans`, `i`, `extra`, `temp`) are allocated. No extra data structures are used.

---

### 🧠 DSA Pattern

- **Linear Scan / Exhaustive Search**: The algorithm first scans linearly to find critical positions and then exhaustively evaluates all possible prefix-suffix deletion combinations.

---

### ⚠️ Common Mistakes

1. **Variable Reassignment Confusion**:
   - After the `if (left < right)` swap, the variable `right` holds the smaller index and `left` holds the larger index. Reading the code later might cause confusion if you expect `left` to be smaller than `right`.

2. **Off-by-One in Back Deletions**:
   - Removing elements from index `idx` to `n - 1` requires `n - idx` deletions. Mixing up `n - idx` with `n - idx - 1` is a common bug when calculating suffix removals.

3. **Loop Range**:
   - The loop runs up to `i <= n` (inclusive). Stopping at `i < n` would miss the candidate state where all elements are removed from the front.

---

### 🚀 Optimization Notes

- **Direct $O(1)$ Candidate Calculation**:
  While your solution runs in $\mathcal{O}(n)$ time and passes easily, the second loop iterating from `0` to `n` is not strictly necessary. 
  
  There are only 3 fundamental strategies to remove two indices `idx1` (smaller) and `idx2` (larger):
  1. **Remove both from the front**: `idx2 + 1`
  2. **Remove both from the back**: `n - idx1`
  3. **Remove `idx1` from front and `idx2` from back**: `(idx1 + 1) + (n - idx2)`

  Taking `Math.min` of these 3 formulas directly gives the answer in $\mathcal{O}(1)$ time after finding the min and max indices.
