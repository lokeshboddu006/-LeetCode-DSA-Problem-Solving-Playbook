<h2><a href="https://leetcode.com/problems/3sum">3Sum</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given an integer array nums, return all the triplets <code>[nums[i], nums[j], nums[k]]</code> such that <code>i != j</code>, <code>i != k</code>, and <code>j != k</code>, and <code>nums[i] + nums[j] + nums[k] == 0</code>.</p>

<p>Notice that the solution set must not contain duplicate triplets.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [-1,0,1,2,-1,-4]
<strong>Output:</strong> [[-1,-1,2],[-1,0,1]]
<strong>Explanation:</strong> 
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
Notice that the order of the output and the order of the triplets does not matter.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [0,1,1]
<strong>Output:</strong> []
<strong>Explanation:</strong> The only possible triplet does not sum up to 0.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [0,0,0]
<strong>Output:</strong> [[0,0,0]]
<strong>Explanation:</strong> The only possible triplet sums up to 0.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>3 &lt;= nums.length &lt;= 3000</code></li>
	<li><code>-10<sup>5</sup> &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea behind your solution is to reduce the 3Sum problem to a set of 2Sum problems on a sorted array using the **Two Pointers** technique:

1. By sorting the array, any triplet that sums to zero can be represented as `nums[i] + nums[l] + nums[h] = 0`, which rearranges to `nums[l] + nums[h] = -nums[i]`.
2. Fixing `nums[i]` leaves a target value `t = -nums[i]`. You then search for two numbers in the remaining sorted sub-array to the right (`l = i + 1` to `h = n - 1`) whose sum equals `t`.
3. Sorting also makes it straightforward to avoid duplicate triplets by simply skipping over adjacent elements with identical values.

### 🔍 Approach

1. **Sort the input**: `Arrays.sort(nums)` puts the array in non-decreasing order.
2. **Outer Loop (Fix First Element)**:
   - Iterate index `i` from `0` to `n - 1`.
   - **Duplicate Check for `i`**: If `i > 0` and `nums[i] == nums[i - 1]`, skip to the next iteration (`continue`) to prevent generating duplicate triplets starting with the same value.
3. **Set Up Two Pointers**:
   - Set the target `t = -nums[i]`.
   - Initialize `l = i + 1` (left pointer) and `h = n - 1` (right pointer).
4. **Inner Loop (Find Matching Pair)**:
   - While `l < h`:
     - Calculate `sum = nums[l] + nums[h]`.
     - **Match Found (`sum == t`)**:
       - Construct the triplet `[nums[i], nums[l], nums[h]]` and append it to `res`.
       - Move pointers inward: `l++` and `h--`.
       - **Duplicate Checks for `l` and `h`**:
         - While `l < h` and `nums[l] == nums[l - 1]`, increment `l`.
         - While `l < h` and `nums[h] == nums[h + 1]`, decrement `h`.
     - **Sum Too Small (`sum < t`)**: Advance `l++` to increase the sum.
     - **Sum Too Large (`sum > t`)**: Decrease `h--` to decrease the sum.
5. **Return Result**: Once all iterations finish, return `res`.

### 🧩 Algorithm

- **Sorting Step**: $O(N \log N)$ pre-processing.
- **Fixed Pivot + Two-Pointer Search**:
  - Outer loop fixes $nums[i]$ for each $i \in [0, n-1]$.
  - Target equation: $nums[l] + nums[h] = -nums[i]$.
  - Two-pointer contraction:
    $$\begin{cases} 
    \text{add triplet, } l \leftarrow l+1, h \leftarrow h-1 & \text{if } nums[l] + nums[h] == -nums[i] \\
    l \leftarrow l+1 & \text{if } nums[l] + nums[h] < -nums[i] \\
    h \leftarrow h-1 & \text{if } nums[l] + nums[h] > -nums[i]
    \end{cases}$$
  - Unique triplet condition enforced by skipping:
    - $nums[i] == nums[i-1]$
    - $nums[l] == nums[l-1]$ after moving $l$
    - $nums[h] == nums[h+1]$ after moving $h$

### ✅ Why This Works

- **Correctness of Two Pointers**: Because `nums` is sorted, if `nums[l] + nums[h] < t`, increasing `l` is the only move that can possibly increase the sum toward `t`. Similarly, if `nums[l] + nums[h] > t`, decreasing `h` is the only move that can decrease the sum.
- **Completeness**: Scanning from both ends ensures no valid pair $(l, h)$ is missed for a given index $i$.
- **Uniqueness**: 
  - Duplicate choices for $nums[i]$ are bypassed at the outer loop (`i > 0 && nums[i] == nums[i-1]`).
  - Duplicate choices for $nums[l]$ and $nums[h]$ are bypassed inside the `sum == t` block immediately after moving $l$ and $h$.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^2)$
  - Sorting takes $\mathcal{O}(N \log N)$.
  - The outer loop runs $N$ times. In each iteration, the two pointers `l` and `h` traverse the remaining elements in $\mathcal{O}(N)$ total steps. Thus, the nested loops take $\mathcal{O}(N^2)$ time.
  - Overall time complexity is dominated by $\mathcal{O}(N^2)$.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space (or $\mathcal{O}(\log N)$ for standard Java primitive `Arrays.sort` stack space), excluding the memory required to store the returned list `res`.

### 🧠 DSA Pattern

- **Two Pointers** (Two-pointer search on a sorted array)
- **Sorting**

### ⚠️ Common Mistakes

1. **Incorrect Duplicate Check Logic**: 
   - Doing `l++` before checking `nums[l] == nums[l-1]` is critical in your code structure. If you check `nums[l] == nums[l+1]` before incrementing, you risk incorrect bound handling or skipping valid first combinations.
2. **Missing `l < h` in Skip Loops**:
   - In the inner duplicate-skipping `while` loops (`while(l < h && nums[l] == nums[l-1])`), forgetting `l < h` could lead to `ArrayIndexOutOfBoundsException` or pointers crossing over incorrectly.
3. **Checking `i > 0` for outer loop**:
   - Forgetting the condition `i > 0` before checking `nums[i] == nums[i-1]` would cause an `ArrayIndexOutOfBoundsException` at `i = 0`.

### 🚀 Optimization Notes

- **Optimal Time Complexity**: $\mathcal{O}(N^2)$ is optimal for comparison-based 3Sum solutions without extra hash memory overhead.
- **Possible Micro-Optimization (Early Exit)**:
  - Inside the outer loop, if `nums[i] > 0`, you could immediately `break` because `nums` is sorted, and three positive numbers cannot sum to `0`.
  - You could also add `if (i + 2 < n && nums[i] + nums[i+1] + nums[i+2] > 0) break;` to stop early when even the smallest possible triplet with `nums[i]` exceeds zero.
