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

The key observation behind this solution is that sorting the input array allows us to transform the 3Sum problem into a sequence of 2Sum problems solved with the **Two Pointers** technique.

Once the array is sorted in ascending order:
1. Fixing one element `nums[i]` reduces the task to finding two other numbers in the remaining subarray (`nums[i+1]` to `nums[nums.length - 1]`) that sum to `-nums[i]`.
2. Duplicate numbers become adjacent, which allows us to skip duplicates easily at both the fixed-element level and the two-pointer level to avoid duplicate triplets in the output.
3. If the fixed element `nums[i]` is greater than zero, no three numbers starting at or after `i` can sum to `0` because all subsequent elements are also positive. This allows an early exit (`break`).

### 🔍 Approach

1. **Sort the Array**:
   - `Arrays.sort(nums)` puts the elements in non-decreasing order.

2. **Outer Loop (Fixing the First Element)**:
   - Iterate index `i` from `0` up to `nums.length - 3`.
   - **Early Exit**: If `nums[i] > 0`, break the loop immediately, as no combination of three positive numbers can yield a sum of `0`.
   - **Skip Duplicate First Elements**: If `i > 0` and `nums[i] == nums[i - 1]`, skip this iteration using `continue` to avoid processing the same value for the first element of a triplet.

3. **Inner Loop (Two Pointers)**:
   - Initialize two pointers: `left = i + 1` and `right = nums.length - 1`.
   - While `left < right`, compute `sum = nums[i] + nums[left] + nums[right]`:
     - **If `sum == 0`**:
       - Add `[nums[i], nums[left], nums[right]]` to `result`.
       - Advance `left` past any duplicate values: `while (left < right && nums[left] == nums[left + 1]) left++;`
       - Decrement `right` past any duplicate values: `while (left < right && nums[right] == nums[right - 1]) right--;`
       - Finally, move both pointers inward (`left++`, `right--`) to search for new pairs.
     - **If `sum < 0`**:
       - The current sum is too small. Increment `left++` to move toward larger values.
     - **If `sum > 0`**:
       - The current sum is too large. Decrement `right--` to move toward smaller values.

4. **Return Result**:
   - Return the `result` list containing all unique triplets.

### 🧩 Algorithm

- **Sorting**: $O(N \log N)$ pre-processing.
- **Two-Pointer Traversal Invariants**:
  - `nums` is sorted: `nums[0] <= nums[1] <= ... <= nums[N - 1]`.
  - For a fixed index `i`, searching in `[left, right]` finds all valid pairs `(nums[left], nums[right])` such that `nums[left] + nums[right] == -nums[i]`.
  - Increments of `left` increase the total sum; decrements of `right` decrease the total sum.

```text
Algorithm State Transition for a Fixed i:
------------------------------------------
sum = nums[i] + nums[left] + nums[right]

           /  left++                         (if sum < 0)
Next state -  right--                        (if sum > 0)
           \  Record triplet, skip dupes,    (if sum == 0)
              left++, right--
```

### ✅ Why This Works

- **Coverage**: By iterating through each possible first element `nums[i]` and using two pointers across the remaining elements to the right, every possible valid combination of indices `(i, left, right)` with `i < left < right` is implicitly considered.
- **Uniqueness**: 
  - Checking `i > 0 && nums[i] == nums[i - 1]` ensures the first element of any returned triplet is unique across outer iterations.
  - The inner `while` loops skip duplicate values of `nums[left]` and `nums[right]` after finding a match, ensuring no duplicate pairs are recorded for the same `nums[i]`.
- **Correctness of Early Break**: Since `nums` is sorted, `nums[i] > 0` implies `nums[left] > 0` and `nums[right] > 0`. Three positive numbers can never sum to zero.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^2)$
  - Sorting takes $\mathcal{O}(N \log N)$.
  - The outer loop runs $N - 2$ times.
  - The inner two-pointer scan traverses the remaining array in $\mathcal{O}(N)$ steps in total across all iterations for a given `i`.
  - Overall time complexity is dominated by the two nested loops: $\mathcal{O}(N^2)$.

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space (excluding space needed for the output list `result`).
  - *Note*: `Arrays.sort()` in Java for primitives uses Dual-Pivot Quicksort, which uses $\mathcal{O}(\log N)$ space on the call stack.

### 🧠 DSA Pattern

- **Two Pointers** (on a sorted array)

### ⚠️ Common Mistakes

1. **Incorrect Duplicate Checking on First Element**:
   Writing `nums[i] == nums[i + 1]` instead of `i > 0 && nums[i] == nums[i - 1]` would skip the first occurrence instead of subsequent ones, missing valid triplets like `[-1, -1, 2]`.

2. **Index Out of Bounds during Inner Duplicate Skip**:
   Forgetting the `left < right` condition inside `while (left < right && nums[left] == nums[left + 1])` could cause an `ArrayIndexOutOfBoundsException` when pointers reach the boundary.

3. **Forgetting Final Pointer Advance**:
   After the inner duplicate skipping loops run, failing to execute `left++` and `right--` would leave the pointers sitting on the last duplicate element, creating an infinite loop.

### 🚀 Optimization Notes

- **Optimal Time Complexity**: This $\mathcal{O}(N^2)$ solution is optimal for the 3Sum problem using comparison-based techniques.
- **Early Termination Pruning**: The condition `if (nums[i] > 0) break;` provides an effective early exit in scenarios with many positive numbers.
