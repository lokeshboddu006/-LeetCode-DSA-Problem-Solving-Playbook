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

The problem asks for the number of subarrays that can be made non-decreasing using at most `k` total increment operations. 

In a non-decreasing array, every element must be at least as large as the element before it. Looking at this from **right to left**: if we have an element `nums[i]`, any element to its right that is smaller than `nums[i]` must be bumped up to at least `nums[i]` (or higher, if a larger value appears further left). 

Because a larger number at index `i` forces subsequent smaller elements to be increased to `nums[i]`, iterating backwards from `n - 1` down to `0` allows us to maintain the effective values of elements using a **Monotonic Deque**. 

As we expand our left boundary `i` further left:
1. `nums[i]` overwrites any smaller effective values to its right, adding to the total operation `cost`.
2. If `cost > k`, the current window `[i..j]` is invalid, so we contract from the right by shrinking `j` until `cost <= k`.
3. For a fixed left endpoint `i`, if `[i..j]` is valid, then all subarrays `[i..i]`, `[i..i+1]`, ..., `[i..j]` are also valid, contributing `j - i + 1` valid subarrays.

### 🔍 Approach

The solution implements a **Right-to-Left Sliding Window** combined with a **Monotonic Double-Ended Queue (Deque)**.

1. **Variables & Data Structures**:
   - `ans`: Keeps track of the total count of valid non-decreasing subarrays.
   - `cost`: Tracks the total operations required to make the current window `[i..j]` non-decreasing.
   - `dq`: A `Deque<int[]>` storing pairs of `[value, count]`. Each pair represents a block of contiguous elements in the window that currently have an effective value of `value`.
   - `i`: The left pointer of the window, moving backwards from `n - 1` down to `0`.
   - `j`: The right pointer of the window, starting at `n - 1`.

2. **Expanding the Window to the Left (`i` loop)**:
   - For each element `num = nums[i]`, initialize its block count `count = 1`.
   - **Monotonic Stack/Deque Push**: Check the back of `dq`. While the back pair has a value `dq.peekLast()[0] < num`:
     - Pop `back = dq.pollLast()`.
     - Since `num` is larger, all `back[1]` elements previously capped at `back[0]` must now be raised to `num`.
     - Increase `cost` by `(long) (num - back[0]) * back[1]`.
     - Add `back[1]` to `count`.
   - Offer `[num, count]` to the **back** of `dq`.

3. **Shrinking the Window from the Right (`while (cost > k)`)**:
   - If the total cost exceeds `k`, the window `[i..j]` is too large. We must remove element `nums[j]`:
     - Pop `front = dq.pollFirst()` from the **front** of `dq` (which represents the effective value of the rightmost element `nums[j]`).
     - Reduce `cost` by `(long) (front[0] - nums[j])`, as `nums[j]` is leaving the window and no longer needs to be raised to `front[0]`.
     - Decrement `j`.
     - If the block had more than 1 element (`front[1] > 1`), decrement its count and re-insert `[front[0], front[1] - 1]` at the **front** of `dq`.

4. **Counting Valid Subarrays**:
   - After ensuring `cost <= k`, all subarrays starting at `i` and ending anywhere from `i` to `j` are valid.
   - Add `j - i + 1` to `ans`.

### 🧩 Algorithm

1. Initialize `ans = 0`, `cost = 0`, `j = n - 1`, `dq = new ArrayDeque<>()`.
2. Loop `i` from `n - 1` down to `0`:
   - `num = nums[i]`, `count = 1`
   - While `!dq.isEmpty()` and `dq.peekLast()[0] < num`:
     - `back = dq.pollLast()`
     - `count += back[1]`
     - `cost += (long) (num - back[0]) * back[1]`
   - `dq.offerLast([num, count])`
   - While `cost > k`:
     - `front = dq.pollFirst()`
     - `cost -= (long) (front[0] - nums[j])`
     - `j--`
     - If `front[1] > 1`: `dq.offerFirst([front[0], front[1] - 1])`
   - `ans += j - i + 1`
3. Return `ans`.

### ✅ Why This Works

- **Correct Cost Tracking**: By traversing right-to-left, an element `nums[i]` dominates all smaller elements to its right up until an element greater than or equal to `nums[i]` was encountered. Grouping these elements into `[value, count]` blocks in the deque allows calculating the exact cost of raising elements in $O(1)$ amortized operations per block.
- **Monotonicity of the Window**: For a fixed left boundary `i`, as the right boundary `j` shrinks (moves left), the cost monotonic non-increases. Shrinking `j` properly subtracts `nums[j]`'s contribution `(front[0] - nums[j])` to restore `cost <= k`.
- **Counting Invariant**: If `[i..j]` requires $\le k$ operations, any subarray `[i..r]` for $i \le r \le j$ requires $\le$ operations than `[i..j]`, so all `j - i + 1` subarrays starting at `i` are guaranteed to be valid.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$
  - The outer loop runs $N$ times (from `n - 1` down to `0`).
  - Each element `nums[i]` is pushed into `dq` exactly once.
  - Each element/block is popped from the back of `dq` at most once throughout the entire execution.
  - The right pointer `j` is decremented at most $N$ times total in the inner `while (cost > k)` loop.
  - Thus, all operations inside the loops take amortized $\mathcal{O}(1)$ time, yielding an overall $\mathcal{O}(N)$ time complexity.

- **Space Complexity**: $\mathcal{O}(N)$
  - The double-ended queue `dq` stores at most $N$ elements in the worst case (e.g., when array elements are strictly increasing from right to left).

### 🧠 DSA Pattern

- **Sliding Window / Two Pointers** (Right-to-Left)
- **Monotonic Deque** (Maintaining aggregated `[value, count]` blocks)

### ⚠️ Common Mistakes

1. **Integer Overflow**: 
   - `(num - back[0]) * back[1]` can exceed the 32-bit signed integer limit (`10^9 * 10^5 = 10^14`). The solution correctly casts to `(long)` during cost updates: `(long) (num - back[0]) * back[1]`.
2. **Confusing Deque Operations**:
   - Adding/merging blocks when expanding `i` happens at the **back** of the deque.
   - Removing individual elements when shrinking `j` happens at the **front** of the deque.
   - Mixing up front and back deque operations would corrupt the window state.
3. **Traversing Left-to-Right Instead of Right-to-Left**:
   - Left-to-right processing requires knowing future maximums to calculate costs, whereas right-to-left allows direct propagation of the lower bound set by `nums[i]`.

### 🚀 Optimization Notes

- **Optimal Time Complexity**: The $\mathcal{O}(N)$ solution is asymptotically optimal for this problem.
- **Object Allocation Overhead**: `dq.offerLast(new int[]{num, count})` creates a new 2-element array object for each push. In Java performance-critical environments, using two parallel primitive arrays or a single flat array to emulate the deque could reduce garbage collection overhead, though `ArrayDeque<int[]>` is clean, readable, and well within time limits.
