<h2><a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array">Remove Duplicates from Sorted Array</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given an integer array <code>nums</code> sorted in <strong>non-decreasing order</strong>, remove the duplicates <a href="https://en.wikipedia.org/wiki/In-place_algorithm" target="_blank"><strong>in-place</strong></a> such that each unique element appears only <strong>once</strong>. The <strong>relative order</strong> of the elements should be kept the <strong>same</strong>.</p>

<p>Consider the number of <em>unique elements</em> in&nbsp;<code>nums</code> to be <code>k<strong>​​​​​​​</strong></code>​​​​​​​. <meta charset="UTF-8" />After removing duplicates, return the number of unique elements&nbsp;<code>k</code>.</p>

<p><meta charset="UTF-8" />The first&nbsp;<code>k</code>&nbsp;elements of&nbsp;<code>nums</code>&nbsp;should contain the unique numbers in <strong>sorted order</strong>. The remaining elements beyond index&nbsp;<code>k - 1</code>&nbsp;can be ignored.</p>

<p><strong>Custom Judge:</strong></p>

<p>The judge will test your solution with the following code:</p>

<pre>
int[] nums = [...]; // Input array
int[] expectedNums = [...]; // The expected answer with correct length

int k = removeDuplicates(nums); // Calls your implementation

assert k == expectedNums.length;
for (int i = 0; i &lt; k; i++) {
    assert nums[i] == expectedNums[i];
}
</pre>

<p>If all assertions pass, then your solution will be <strong>accepted</strong>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,1,2]
<strong>Output:</strong> 2, nums = [1,2,_]
<strong>Explanation:</strong> Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [0,0,1,1,1,2,2,3,3,4]
<strong>Output:</strong> 5, nums = [0,1,2,3,4,_,_,_,_,_]
<strong>Explanation:</strong> Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 3 * 10<sup>4</sup></code></li>
	<li><code>-100 &lt;= nums[i] &lt;= 100</code></li>
	<li><code>nums</code> is sorted in <strong>non-decreasing</strong> order.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

In a sorted array, duplicate values are guaranteed to be adjacent to each other. 

The core idea of your solution is to use two pointers:
1. A **read pointer** (`o`) that scans through the array starting from index `1` to find unique numbers by comparing each element with its immediate predecessor (`nums[o-1]`).
2. A **write pointer** (`h`) that tracks where the next unique element should be placed in the front portion of the array.

Whenever `nums[o]` differs from `nums[o-1]`, it means a new unique number has been encountered. You then advance the write pointer `h` and copy `nums[o]` to `nums[h]`.

---

### 🔍 Approach

1. **Initialization**:
   - `n = nums.length`: Stores the total size of the input array.
   - `h = 0`: Represents the index of the last placed unique element (starts at index `0` since `nums[0]` is always unique by default).
   - `o = 1`: The pointer iterating through the array starting from index `1`.

2. **Traversal Loop (`while (o < n)`)**:
   - Check if `nums[o] == nums[o-1]`:
     - If true, `nums[o]` is a duplicate of the element right before it. Increment `o` and skip the rest of the loop using `continue`.
   - If `nums[o] != nums[o-1]`:
     - A new unique element is found.
     - Increment `h` (`h++`) to make space for the new unique value.
     - Overwrite `nums[h]` with `nums[o]`.
     - Increment `o` (`o++`) to move to the next element.

3. **Return**:
   - Return `h + 1`. Since `h` is the `0`-based index of the last placed unique element, `h + 1` represents the count of unique elements in `nums`.

---

### 🧩 Algorithm

**Two-Pointer (Read / Write Pointer)**

- **Invariant**: At any step, the subarray `nums[0...h]` contains the unique elements seen so far in sorted order.
- **Pointer `o`**: Scans from index `1` to `n - 1`.
- **Condition**: 
  - Duplicate check: `nums[o] == nums[o-1]`
  - Write operation: `h = h + 1`, `nums[h] = nums[o]`

---

### ✅ Why This Works

- Because the array is sorted in non-decreasing order, all identical elements are contiguous. Comparing `nums[o]` with `nums[o-1]` guarantees that every duplicate group is recognized immediately upon encountering its second element.
- The first element `nums[0]` is naturally unique, so `h` starts at `0`.
- Writing unique elements sequentially to `nums[h]` ensures that the first `h + 1` elements of the array contain only unique values in their original relative order.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$, where $n$ is the length of `nums`. The read pointer `o` inspects each element of the array from index `1` to `n - 1` exactly once.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. The modification is done in-place using only integer variables (`n`, `h`, `o`).

---

### 🧠 DSA Pattern

- **Two Pointers** (Fast / Slow or Read / Write pointers)

---

### ⚠️ Common Mistakes

1. **Returning `h` instead of `h + 1`**: Since `h` is zero-indexed, returning `h` directly would be off by one (e.g., if there is 1 unique element, `h` stays `0`, so returning `h + 1` yields `1`).
2. **Starting pointer `o` from index `0`**: Comparing `nums[o]` with `nums[o-1]` when `o = 0` would trigger an `ArrayIndexOutOfBoundsException`.
3. **Redundant `o++` operations**: Note that `o++` is written both inside the `if` block before `continue` and after `nums[h] = nums[o]`. If `o++` were missed in either branch, it would lead to an infinite loop.

---

### 🚀 Optimization Notes

- **Optimal Complexity**: The solution is already optimal with $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space complexity.
- **Code Structure**: Notice that `o++` is performed in both branches of the conditional logic. Removing `o++` from the `if` block and placing a single `o++` outside the `if` block simplifies the control flow and avoids duplicate increments.
