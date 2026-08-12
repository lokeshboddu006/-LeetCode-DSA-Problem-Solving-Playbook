<h2><a href="https://leetcode.com/problems/length-of-longest-subarray-with-at-most-k-frequency">Length of Longest Subarray With at Most K Frequency</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer array <code>nums</code> and an integer <code>k</code>.</p>

<p>The <strong>frequency</strong> of an element <code>x</code> is the number of times it occurs in an array.</p>

<p>An array is called <strong>good</strong> if the frequency of each element in this array is <strong>less than or equal</strong> to <code>k</code>.</p>

<p>Return <em>the length of the <strong>longest</strong> <strong>good</strong> subarray of</em> <code>nums</code><em>.</em></p>

<p>A <strong>subarray</strong> is a contiguous non-empty sequence of elements within an array.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3,1,2,3,1,2], k = 2
<strong>Output:</strong> 6
<strong>Explanation:</strong> The longest possible good subarray is [1,2,3,1,2,3] since the values 1, 2, and 3 occur at most twice in this subarray. Note that the subarrays [2,3,1,2,3,1] and [3,1,2,3,1,2] are also good.
It can be shown that there are no good subarrays with length more than 6.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,1,2,1,2,1,2], k = 1
<strong>Output:</strong> 2
<strong>Explanation:</strong> The longest possible good subarray is [1,2] since the values 1 and 2 occur at most once in this subarray. Note that the subarray [2,1] is also good.
It can be shown that there are no good subarrays with length more than 2.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [5,5,5,5,5,5,5], k = 4
<strong>Output:</strong> 4
<strong>Explanation:</strong> The longest possible good subarray is [5,5,5,5] since the value 5 occurs 4 times in this subarray.
It can be shown that there are no good subarrays with length more than 4.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>1 &lt;= k &lt;= nums.length</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea behind your solution is to use a **sliding window** maintained by two pointers (`i` and `j`) alongside a **frequency hash map** (`m`). 

As you expand the right boundary (`j`), you add elements to the current window and record their frequencies. If the frequency of the newly added element `nums[j]` exceeds `k`, the window becomes invalid. To restore validity, you shrink the window from the left by advancing the left boundary (`i`) and decrementing the frequency of `nums[i]` until the frequency of `nums[j]` drops back to `k` or below. At every step where the window is valid, you update the maximum length found so far.

---

### 🔍 Approach

1. **Initialization**:
   - `m`: A `HashMap<Integer, Integer>` to track the frequency of each number in the active window `[i, j]`.
   - `i`: The left pointer of the sliding window, initialized to `0`.
   - `j`: The right pointer of the sliding window, initialized to `0`.
   - `res`: Tracks the maximum valid subarray length found, initialized to `0`.

2. **Expanding the Window (`j` loop)**:
   - For each element `nums[j]`, you increment its count in the hash map using `m.put(nums[j], m.getOrDefault(nums[j], 0) + 1)`.

3. **Shrinking the Window (`while` loop)**:
   - Check if the current element `nums[j]` has exceeded the frequency threshold `k` (`m.get(nums[j]) > k`).
   - If it has, shrink the window from the left: decrement `m.get(nums[i])` by `1` and increment `i`.
   - Repeat this until `m.get(nums[j]) <= k`.

4. **Updating Answer & Moving Forward**:
   - Once the window `[i, j]` is guaranteed to be valid, compute the current window size `j - i + 1` and update `res = Math.max(res, j - i + 1)`.
   - Increment `j` to evaluate the next element.

5. **Return**:
   - Return `res` after processing all elements.

---

### 🧩 Algorithm

- **Loop Invariant**: At the end of each iteration of the outer `while` loop (before `j++`), the window `[i, j]` contains a contiguous subarray where every element occurs at most `k` times.
- **Window Shrink Condition**: `m.get(nums[j]) > k`.
- **Window Size Calculation**: `j - i + 1`.

---

### ✅ Why This Works

- **Preserving Subarray Continuity**: Moving `i` and `j` sequentially ensures that every window evaluated is a valid contiguous subarray of `nums`.
- **Targeted Shrinking**: Since only `nums[j]` was incremented in the outer loop, `nums[j]` is the only element that could have violated the condition `frequency <= k`. Therefore, shrinking the window from `i` until `nums[j]`'s count is at most `k` is both necessary and sufficient to restore window validity for all elements.
- **Completeness**: Because `j` inspects every possible ending position of a valid subarray, and `i` is moved as far left as validly possible for each `j`, no longer valid subarray can be missed.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of `nums`.
  - The right pointer `j` increments from `0` to $N - 1$ (executes $N$ times).
  - The left pointer `i` increments at most $N$ times across the entire runtime because it only moves forward.
  - Hash map operations (`get`, `put`, `getOrDefault`) take $\mathcal{O}(1)$ average time.
  - Thus, the total time complexity is linear, $\mathcal{O}(N)$.

- **Space Complexity**: $\mathcal{O}(N)$ in the worst case.
  - The `HashMap` stores frequencies for distinct elements inside the current window.
  - In the worst case (where all elements in `nums` are distinct), the map can store up to $N$ key-value pairs.

---

### 🧠 DSA Pattern

- **Sliding Window (Variable Length / Two Pointers)**
- **Hashing (Frequency Map)**

---

### ⚠️ Common Mistakes

1. **Incorrect Shrink Condition**: Checking if *any* element's frequency exceeds `k` inside the inner loop instead of specifically checking `m.get(nums[j])`. Since `nums[j]` is the only element whose frequency increases, checking `m.get(nums[j])` is sufficient and avoids unnecessary map iterations.
2. **Forgetting to Update Frequency Map on Shrink**: Incrementing `i++` without updating `m.put(nums[i], m.get(nums[i]) - 1)` first would leave stale frequency counts in the map.
3. **Off-by-One in Length Calculation**: Using `j - i` instead of `j - i + 1` for 0-indexed inclusive window boundaries.

---

### 🚀 Optimization Notes

- **Optimal Time Complexity**: The algorithm already achieves the optimal $\mathcal{O}(N)$ time complexity.
- **Map Cleanup**: The code leaves keys in `m` even when their associated count reaches `0`. While this does not affect correctness or time complexity, it leaves zero-frequency keys in memory.
- **Auto-boxing Overhead**: Java's `HashMap<Integer, Integer>` introduces overhead due to object boxing/unboxing and hash collisions. While it fits within time limits, using array-based frequency tables is usually faster when value ranges are small (though here `nums[i] <= 10^9`, so a hash-based structure or coordinate compression is required).
