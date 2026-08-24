<h2><a href="https://leetcode.com/problems/sort-colors">Sort Colors</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an array <code>nums</code> with <code>n</code> objects colored red, white, or blue, sort them <strong><a href="https://en.wikipedia.org/wiki/In-place_algorithm" target="_blank">in-place</a> </strong>so that objects of the same color are adjacent, with the colors in the order red, white, and blue.</p>

<p>We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.</p>

<p>You must solve this problem without using the library&#39;s sort function.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [2,0,2,1,1,0]</span></p>

<p><strong>Output:</strong> <span class="example-io">[0,0,1,1,2,2]</span></p>

<p><strong>Explanation:</strong></p>

<p>The array has two 0s, two 1s, and two 2s. Sorting them in-place places all 0s first, then all 1s, then all 2s.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [2,0,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">[0,1,2]</span></p>

<p><strong>Explanation:</strong></p>

<p>The array has one each of 0, 1, and 2, arranged in-place in the order 0, 1, 2.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == nums.length</code></li>
	<li><code>1 &lt;= n &lt;= 300</code></li>
	<li><code>nums[i]</code> is either 0, 1, or 2.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong>&nbsp;Could you come up with a one-pass algorithm using only&nbsp;constant extra space?</p>

---

## 🧠 AI Explanation

### 💡 Intuition

The code implements the classic **Dutch National Flag algorithm** using three pointers (`low`, `mid`, and `high`). 

The key insight is to divide the array into four distinct logical regions as you iterate through it:
1. `nums[0 ... low - 1]`: Contains only `0`s (Red).
2. `nums[low ... mid - 1]`: Contains only `1`s (White).
3. `nums[mid ... high]`: Unexamined elements.
4. `nums[high + 1 ... n - 1]`: Contains only `2`s (Blue).

By checking the element at `mid`, the code determines which region that element belongs to and swaps it into its correct position, shrinking the unexamined window `[mid ... high]` until all elements are placed in order.

---

### 🔍 Approach

1. **Initialization**:
   - `low = 0`: Tracks the boundary where the next `0` should be placed.
   - `mid = 0`: The reader pointer that inspects the current element.
   - `high = n - 1`: Tracks the boundary where the next `2` should be placed.

2. **Main Loop (`while (mid <= high)`)**:
   - **When `nums[mid] == 0`**:
     - Swap `nums[mid]` with `nums[low]`.
     - Increment `low` to expand the `0`s boundary.
     - Increment `mid` because the element swapped from `low` is guaranteed to be a `1` (or `0` if `low == mid`), which has already been processed.
   - **When `nums[mid] == 1`**:
     - The element is already in the middle section. Just increment `mid`.
   - **When `nums[mid] == 2`**:
     - Swap `nums[mid]` with `nums[high]`.
     - Decrement `high` to expand the `2`s boundary.
     - **Crucially, do NOT increment `mid`** here because the value swapped from `high` has not been evaluated yet and must be checked in the next iteration.

3. **Helper Method (`swap`)**:
   - Performs a standard 3-step swap using a temporary variable `temp` to swap `nums[i]` and `nums[j]`.

---

### 🧩 Algorithm

- **Pattern**: 3-Pointer Partitioning (Dutch National Flag Algorithm).
- **Loop Invariants**:
  $$\text{All elements in } [0, \text{low} - 1] = 0$$
  $$\text{All elements in } [\text{low}, \text{mid} - 1] = 1$$
  $$\text{All elements in } [\text{high} + 1, n - 1] = 2$$

- **Termination Condition**:
  The loop stops when `mid > high`. At this point, the unexamined range `[mid, high]` becomes empty, meaning every element in the array satisfies the invariants and the array is fully sorted.

---

### ✅ Why This Works

- Swapping a `0` to index `low` ensures all `0`s stay at the beginning of the array. Since `low` only points to either `mid` or a `1` that was previously inspected, the value brought to `mid` after swapping with `low` is safe to move past, allowing `mid++`.
- Swapping a `2` to index `high` places it safely at the end. Since the element incoming from `high` was previously unexamined, `mid` stays at its current index to process this new value.
- Values of `1` naturally accumulate between `low` and `mid - 1` as `mid` advances and `low` stays behind.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$, where $n$ is the number of elements in `nums`. In every iteration of the `while` loop, either `mid` is incremented or `high` is decremented. Thus, the loop runs at most $n$ times in a single pass.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. The partitioning is done completely in-place using only three integer pointer variables (`low`, `mid`, `high`) and a temporary variable inside `swap`.

---

### 🧠 DSA Pattern

- **Two Pointers / Multi-Pointer** (Specifically 3-Pointer Dutch National Flag partitioning)

---

### ⚠️ Common Mistakes

1. **Incrementing `mid` when `nums[mid] == 2`**:
   Advancing `mid` after swapping with `high` is a common mistake. Because the element coming from index `high` was unexamined, incrementing `mid` prematurely would skip checking that element, leaving unsorted values in the array.
2. **Incorrect Loop Condition (`mid < high` vs `mid <= high`)**:
   Using `mid < high` instead of `mid <= high` misses examining the final element at index `high`, which can lead to incomplete sorting.

---

### 🚀 Optimization Notes

- This implementation is already optimal: it achieves the theoretical lower bound for this problem ($\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space in a single pass).
- No further optimizations are required.
