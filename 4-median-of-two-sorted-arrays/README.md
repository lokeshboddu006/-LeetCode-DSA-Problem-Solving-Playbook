<h2><a href="https://leetcode.com/problems/median-of-two-sorted-arrays">Median of Two Sorted Arrays</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given two sorted arrays <code>nums1</code> and <code>nums2</code> of size <code>m</code> and <code>n</code> respectively, return <strong>the median</strong> of the two sorted arrays.</p>

<p>The overall run time complexity should be <code>O(log (m+n))</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums1 = [1,3], nums2 = [2]
<strong>Output:</strong> 2.00000
<strong>Explanation:</strong> merged array = [1,2,3] and median is 2.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums1 = [1,2], nums2 = [3,4]
<strong>Output:</strong> 2.50000
<strong>Explanation:</strong> merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>nums1.length == m</code></li>
	<li><code>nums2.length == n</code></li>
	<li><code>0 &lt;= m &lt;= 1000</code></li>
	<li><code>0 &lt;= n &lt;= 1000</code></li>
	<li><code>1 &lt;= m + n &lt;= 2000</code></li>
	<li><code>-10<sup>6</sup> &lt;= nums1[i], nums2[i] &lt;= 10<sup>6</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The standard way to find the median of two separate sorted collections is to first combine them into a single sorted collection. 

In this implementation, you take advantage of the fact that both input arrays `a` and `b` are already sorted. By applying the two-pointer merge step from Merge Sort, you build a single, fully sorted array `merged` containing all elements from both arrays. Once the merged array is constructed, finding the median is straightforward based on whether the total count of elements is odd or even.

### 🔍 Approach

1. **Initialization**:
   - Store the sizes `n = a.length` and `m = b.length`.
   - Compute `total = n + m` and allocate a new integer array `merged` of size `total`.
   - Initialize three pointers: `i = 0` (for array `a`), `j = 0` (for array `b`), and `k = 0` (for array `merged`).

2. **Merging the Arrays**:
   - Run a `while (i < n && j < m)` loop to compare elements from `a` and `b`:
     - If `a[i] < b[j]`, place `a[i]` into `merged[k]` and increment `i` and `k`.
     - Otherwise, place `b[j]` into `merged[k]` and increment `j` and `k`.
   - Use two cleanup `while` loops (`while (i < n)` and `while (j < m)`) to append any remaining elements from `a` or `b` into `merged`.

3. **Computing the Median**:
   - Check if `total` is odd (`total % 2 == 1`):
     - If odd, the median is the exact middle element: `merged[total / 2]`.
   - If even:
     - The median is the average of the two middle elements: `(merged[(total / 2) - 1] + merged[total / 2]) / 2.0`.
     - Dividing by `2.0` ensures the result is calculated as a floating-point number (`double`) rather than truncated integer division.

### 🧩 Algorithm

**Two-Pointer Array Merge & Direct Median Indexing**

- **Loop Invariant**: At index `k`, the array `merged[0 ... k-1]` contains the $k$ smallest elements from `a` and `b` in strictly non-decreasing order.
- **Selection Condition**:
  $$\text{merged}[k] = \begin{cases} a[i], & \text{if } a[i] < b[j] \\ b[j], & \text{otherwise} \end{cases}$$
- **Median Formula**:
  $$\text{Median} = \begin{cases} \text{merged}\left[\lfloor \frac{N}{2} \rfloor\right], & \text{if } N \text{ is odd} \\[6pt] \frac{\text{merged}\left[\frac{N}{2} - 1\right] + \text{merged}\left[\frac{N}{2}\right]}{2.0}, & \text{if } N \text{ is even} \end{cases}$$
  where $N = \text{total}$.

### ✅ Why This Works

- **Correct Order Guaranteed**: Comparing the current smallest uncopied elements from `a` and `b` guarantees that elements are inserted into `merged` in ascending order.
- **Complete Coverage**: The two secondary `while` loops ensure no elements are left behind once one array is exhausted.
- **Exact Median Definition**: In any 0-indexed sorted array of length $N$:
  - If $N$ is odd, index $N / 2$ leaves an equal number of elements on its left and right.
  - If $N$ is even, indices $N/2 - 1$ and $N/2$ represent the middle pair, whose arithmetic mean gives the correct median.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n + m)$
  - The code iterates through both arrays `a` and `b` element-by-element to populate `merged`. Each element from both arrays is visited and copied exactly once.
- **Space Complexity**: $\mathcal{O}(n + m)$
  - Additional memory is allocated for the `merged` array of size `total = n + m`.

### 🧠 DSA Pattern

- **Two Pointers** (specifically, the Merge Step of Merge Sort)

### ⚠️ Common Mistakes

1. **Integer Division Truncation**: Writing `/ 2` instead of `/ 2.0` when averaging the two middle elements. In Java, integer division truncates decimal places (e.g., `(2 + 3) / 2` evaluates to `2` instead of `2.5`). The code avoids this by using floating-point division `/ 2.0`.
2. **Index Out of Bounds**: Incorrectly using `total / 2 + 1` or off-by-one errors when indexing the middle elements.
3. **Handling Equal Values**: When `a[i] == b[j]`, the `else` block executes and copies `b[j]`. This preserves correctness since order between identical values does not affect sorting.

### 🚀 Optimization Notes

- **Extra Space**: The implementation allocates an $O(n + m)$ auxiliary array `merged`. Since you only need the median element(s) at indices around `total / 2`, you do not strictly need to store the entire array; you could keep track of only the current and previous elements using counter variables until reaching the target middle indices.
- **Early Termination**: The merging loop continues through all $n + m$ elements. In practice, you only need to simulate merging up to index `total / 2` to find the median.
- **Given Constraints**: Since $m + n \le 2000$, this $\mathcal{O}(n + m)$ linear solution performs at most 2,000 operations, making it extremely fast and easily acceptable within standard time limits.
