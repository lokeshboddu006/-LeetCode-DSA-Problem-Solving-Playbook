<h2><a href="https://leetcode.com/problems/count-non-decreasing-subarrays-after-k-operations">Count Non-Decreasing Subarrays After K Operations</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given an array <code>nums</code> of <code>n</code> integers and an integer <code>k</code>.</p>

<p>For each subarray of <code>nums</code>, you can apply <strong>up to</strong> <code>k</code> operations on it. In each operation, you increment any element of the subarray by 1.</p>

<p><strong>Note</strong> that each subarray is considered independently, meaning changes made to one subarray do not persist to another.</p>

<p>Return the number of subarrays that you can make <strong>non-decreasing</strong> ​​​​​after performing at most <code>k</code> operations.</p>

<p>An array is said to be <strong>non-decreasing</strong> if each element is greater than or equal to its previous element, if it exists.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [6,3,1,2,4,4], k = 7</span></p>

<p><strong>Output:</strong> <span class="example-io">17</span></p>

<p><strong>Explanation:</strong></p>

<p>Out of all 21 possible subarrays of <code>nums</code>, only the subarrays <code>[6, 3, 1]</code>, <code>[6, 3, 1, 2]</code>, <code>[6, 3, 1, 2, 4]</code> and <code>[6, 3, 1, 2, 4, 4]</code> cannot be made non-decreasing after applying up to k = 7 operations. Thus, the number of non-decreasing subarrays is <code>21 - 4 = 17</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [6,3,1,3,6], k = 4</span></p>

<p><strong>Output:</strong> <span class="example-io">12</span></p>

<p><strong>Explanation:</strong></p>

<p>The subarray <code>[3, 1, 3, 6]</code> along with all subarrays of <code>nums</code> with three or fewer elements, except <code>[6, 3, 1]</code>, can be made non-decreasing after <code>k</code> operations. There are 5 subarrays of a single element, 4 subarrays of two elements, and 2 subarrays of three elements except <code>[6, 3, 1]</code>, so there are <code>1 + 5 + 4 + 2 = 12</code> subarrays that can be made non-decreasing.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= k &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To count how many subarrays starting at index `l` can be made non-decreasing within `k` operations, we can fix the left endpoint `l` and find the rightmost index `r` such that the operations needed for `nums[l..r]` do not exceed `k`. 

Making an array non-decreasing with minimum operations means each element `nums[i]` (for $i \in [l, r]$) must be bumped up to match the maximum value seen so far to its left in the range $[l, i]$. The total operations needed (referred to as `water` in your code) is:
$$\text{water} = \sum_{i=l}^{r} (\max_{l \le j \le i} \text{nums}[j] - \text{nums}[i])$$

Your implementation iterates `l` backwards from `n - 1` down to `0`. By moving leftwards:
1. `nums[l]` becomes the new candidate maximum that can overwrite smaller peak values to its right.
2. A **monotonic deque** (`starts`) keeps track of the indices of non-increasing peak values.
3. When `nums[l]` is greater than previous peaks in the deque, those smaller peaks get overwritten, increasing `water` by the extra height difference over the ranges those peaks controlled.
4. If `water` exceeds `k`, we shrink the window from the right by decrementing `r` until `water <= k`.

---

### 🔍 Approach

1. **Initialization**:
   - `starts`: A `Deque<Integer>` storing indices of governing peaks in the current window $[l, r]$.
   - `r = n - 1`: The right boundary of the current valid sliding window.
   - `water = 0`: The total operations needed to make `nums[l..r]` non-decreasing.
   - `ans = 0`: Accumulator for total valid subarrays.

2. **Iterate Left Boundary (`l` from `n - 1` down to `0`)**:
   - Set `x = nums[l]`.
   - **Absorb smaller peaks to the right**:
     - While `starts` is not empty and `nums[starts.peekFirst()] <= x`:
       - Remove index `s = starts.pollFirst()`.
       - Find the boundary `end`: if `starts` is empty, the range managed by `s` extended up to `r + 1`; otherwise, it extends up to the next peak index `starts.peekFirst()`.
       - All elements in $[s, \text{end} - 1]$ were previously elevated to `nums[s]`. Now they must be elevated to `x`.
       - Add the incremental cost to `water`: `(long) (x - nums[s]) * (end - s)`.
   - **Push `l` to Deque**:
     - Insert `l` at the front (`starts.offerFirst(l)`). `nums[l]` is now the highest peak starting at index `l`.

3. **Shrink Right Boundary (`r`) when `water > k`**:
   - While `water > k`:
     - The element `nums[r]` is governed by the rightmost peak in the deque, `starts.peekLast()`.
     - Removing index `r` reduces `water` by `nums[starts.peekLast()] - nums[r]`.
     - If `r` itself was a peak index (`starts.peekLast() == r`), pop it from the back of the deque.
     - Decrement `r`.

4. **Count Subarrays**:
   - For the current left endpoint `l`, any right endpoint from `l` to `r` forms a valid non-decreasing subarray.
   - Add `r - l + 1` to `ans`.

---

### 🧩 Algorithm

1. `starts` $\gets$ empty `Deque`
2. `ans` $\gets 0$, `water` $\gets 0$, `r` $\gets n - 1$
3. Loop `l` from $n - 1$ down to $0$:
   - $x \gets \text{nums}[l]$
   - While `starts` is not empty and $\text{nums}[\text{starts.peekFirst()}] \le x$:
     - $s \gets \text{starts.pollFirst()}$
     - $\text{end} \gets \text{starts.isEmpty()} \text{ ? } r + 1 : \text{starts.peekFirst()}$
     - $\text{water} \gets \text{water} + (x - \text{nums}[s]) \times (\text{end} - s)$
   - $\text{starts.offerFirst}(l)$
   - While $\text{water} > k$:
     - $\text{water} \gets \text{water} - (\text{nums}[\text{starts.peekLast()}] - \text{nums}[r])$
     - If $\text{starts.peekLast()} == r$: $\text{starts.pollLast()}$
     - $r \gets r - 1$
   - $\text{ans} \gets \text{ans} + (r - l + 1)$
4. Return `ans`

---

### ✅ Why This Works

- **Correctness of `water` update (Left expansion)**: When `l` moves left to a larger value `x`, any block of elements to the right previously topped at `nums[s] <= x` must now be topped at `x`. The number of elements in that block is `end - s`. Thus, updating `water` by `(x - nums[s]) * (end - s)` accurately adds the exact extra operations required.
- **Correctness of `water` reduction (Right shrinkage)**: The element at index `r` is elevated to `nums[starts.peekLast()]`. When `r` is excluded from the window, we simply subtract its contribution `nums[starts.peekLast()] - nums[r]` from `water`.
- **Monotonic Property**: Because `water` increases monotonically when extending `l` and decreases when shrinking `r`, two pointers combined with the monotonic deque efficiently find the maximal valid `r` for each `l`.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Each index is added to `starts` once at `offerFirst(l)`.
  - Each index is removed at most once from the front (`pollFirst`) and at most once from the back (`pollLast`).
  - The right pointer `r` decreases at most $n$ times across the entire runtime.
  - Therefore, all operations run in linear time.

- **Space Complexity**: $\mathcal{O}(n)$
  - The `ArrayDeque` (`starts`) stores at most $n$ indices in the worst case.

---

### 🧠 DSA Pattern

- **Monotonic Deque** (maintains indices of peak elements for fast range height updating)
- **Sliding Window / Two Pointers** (moving right-to-left while contracting `r`)

---

### ⚠️ Common Mistakes

1. **Integer Overflow**: 
   - `water` and `ans` can quickly exceed the 32-bit integer limit ($2^{31}-1$). Casting `(x - nums[s]) * (end - s)` to `long` and declaring `water` as `long` avoids overflow.
2. **Incorrect Range Calculation on Pop**:
   - Calculating `end` incorrectly when popping from the deque front would miscount how many elements are updated to height `x`. Using `starts.isEmpty() ? r + 1 : starts.peekFirst()` correctly captures the right boundary of the affected block.
3. **Forgetting to Remove Boundary Peak**:
   - When decrementing `r`, if `r` itself was a peak stored in `starts`, failing to check `starts.peekLast() == r` and popping it would leave an invalid index in the deque.

---

### 🚀 Optimization Notes

- The solution is already **optimal** with $\mathcal{O}(n)$ time complexity and $\mathcal{O}(n)$ auxiliary space.
- A minor language-level optimization would be using a raw primitive `int[]` array to implement the deque instead of `ArrayDeque<Integer>` to eliminate object boxing/unboxing overhead in Java, but the current implementation is already clean and optimal in terms of asymptotic complexity.
