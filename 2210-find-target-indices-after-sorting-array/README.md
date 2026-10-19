<h2><a href="https://leetcode.com/problems/find-target-indices-after-sorting-array">Find Target Indices After Sorting Array</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given a <strong>0-indexed</strong> integer array <code>nums</code> and a target element <code>target</code>.</p>

<p>A <strong>target index</strong> is an index <code>i</code> such that <code>nums[i] == target</code>.</p>

<p>Return <em>a list of the target indices of</em> <code>nums</code> after<em> sorting </em><code>nums</code><em> in <strong>non-decreasing</strong> order</em>. If there are no target indices, return <em>an <strong>empty</strong> list</em>. The returned list must be sorted in <strong>increasing</strong> order.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,5,2,3], target = 2
<strong>Output:</strong> [1,2]
<strong>Explanation:</strong> After sorting, nums is [1,<u><strong>2</strong></u>,<u><strong>2</strong></u>,3,5].
The indices where nums[i] == 2 are 1 and 2.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,5,2,3], target = 3
<strong>Output:</strong> [3]
<strong>Explanation:</strong> After sorting, nums is [1,2,2,<u><strong>3</strong></u>,5].
The index where nums[i] == 3 is 3.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,5,2,3], target = 5
<strong>Output:</strong> [4]
<strong>Explanation:</strong> After sorting, nums is [1,2,2,3,<u><strong>5</strong></u>].
The index where nums[i] == 5 is 4.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums[i], target &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Instead of physically sorting the array `nums` (which takes $O(N \log N)$ time), this solution leverages a counting-based mathematical property of sorted arrays:

1. In a sorted array, all elements strictly smaller than `target` must appear before any occurrences of `target`. Therefore, the number of elements strictly smaller than `target` gives the exact **starting index** (`left`) where `target` will first appear.
2. Similarly, all elements less than or equal to `target` will occupy indices from `0` up to `right - 1`, where `right` is the count of elements $\le \text{target}$.
3. Consequently, the target indices form a contiguous range of integers from `left` (inclusive) to `right` (exclusive), i.e., $[left, right)$.

### 🔍 Approach

1. **Calculate Starting Index (`left`)**:
   - `Arrays.stream(nums).filter(x -> x < target).count()`
   - Filters the array for elements strictly less than `target` and counts them. This count represents the 0-based index of the first occurrence of `target` after sorting.

2. **Calculate Exclusive Ending Index (`right`)**:
   - `Arrays.stream(nums).filter(x -> x <= target).count()`
   - Filters the array for elements less than or equal to `target` and counts them. This count represents the index where elements greater than `target` would begin.

3. **Construct the Result List**:
   - `IntStream.range(left, right).boxed().toList()`
   - Generates an integer range from `left` to `right - 1`.
   - `.boxed()` converts primitive `int` values into `Integer` objects.
   - `.toList()` collects the boxed integers into a `List<Integer>` and returns it.

### 🧩 Algorithm

1. Let $N$ be the length of `nums`.
2. Compute $left = \sum_{i=0}^{N-1} [nums[i] < target]$.
3. Compute $right = \sum_{i=0}^{N-1} [nums[i] \le target]$.
4. Construct and return the sequence $[left, left + 1, \dots, right - 1]$.

### ✅ Why This Works

When an array is sorted in non-decreasing order:
- The first $left$ positions (indices `0` to `left - 1`) are occupied by elements strictly smaller than `target`.
- The total count of elements smaller than or equal to `target` is $right$. Thus, indices `0` to `right - 1` contain all elements $\le target$.
- Subtracting the smaller elements from this set leaves the elements equal to `target`, which must sit in the index range `[left, right - 1]`.
- Java's `IntStream.range(left, right)` includes `left` and excludes `right`, perfectly matching the target index range `[left, right - 1]`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of `nums`.
  - The code streams through `nums` twice: once to count elements $< target$ and once for $\le target$.
  - Generating and boxing the range takes time proportional to the count of `target` elements, which is at most $N$.
  - Overall time complexity is linear $\mathcal{O}(N)$, which is faster than explicit sorting ($\mathcal{O}(N \log N)$).

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space (excluding the output list), or $\mathcal{O}(k)$ where $k$ is the number of occurrences of `target` in `nums` to store the returned list.

### 🧠 DSA Pattern

- **Math / Counting**: Using counting logic to determine the final sorted position of elements without performing a full sort (similar to the counting step in Counting Sort).

### ⚠️ Common Mistakes

1. **Confusing Range Bounds**:
   - Misunderstanding `IntStream.range(left, right)` as end-inclusive instead of end-exclusive. Because `right` is the count of elements $\le target$, the highest index is `right - 1`, making end-exclusive `IntStream.range(left, right)` correct.
2. **Empty Target Handling**:
   - If `target` is not present in `nums`, then $left$ will equal $right$. `IntStream.range(left, left)` naturally produces an empty stream, which returns an empty list as expected.

### 🚀 Optimization Notes

- **Linear vs $O(N \log N)$**: This solution is already asymptotically optimal ($\mathcal{O}(N)$ time), outperforming solutions that explicitly sort the array.
- **Pass Overhead**: The current implementation runs two separate Java Stream passes over `nums`. A single traditional `for` loop could count both `left` and `right` in a single pass, avoiding Stream creation overhead and reducing array passes from 2 to 1.
