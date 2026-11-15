<h2><a href="https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum">Number of Sub-arrays With Odd Sum</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given an array of integers <code>arr</code>, return <em>the number of subarrays with an <strong>odd</strong> sum</em>.</p>

<p>Since the answer can be very large, return it modulo <code>10<sup>9</sup> + 7</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> arr = [1,3,5]
<strong>Output:</strong> 4
<strong>Explanation:</strong> All subarrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
All sub-arrays sum are [1,4,9,3,8,5].
Odd sums are [1,9,3,5] so the answer is 4.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> arr = [2,4,6]
<strong>Output:</strong> 0
<strong>Explanation:</strong> All subarrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
All sub-arrays sum are [2,6,12,4,10,6].
All sub-arrays have even sum and the answer is 0.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> arr = [1,2,3,4,5,6,7]
<strong>Output:</strong> 16
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= arr.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= arr[i] &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The sum of any subarray from index `j` to index `i` can be expressed as the difference between two prefix sums:
$$\text{subarraySum}(j, i) = \text{prefixSum}[i] - \text{prefixSum}[j-1]$$

For a subarray sum to be **odd**, the two prefix sums ($\text{prefixSum}[i]$ and $\text{prefixSum}[j-1]$) must have **opposite parities** (one must be odd and the other even):
- $\text{Odd} - \text{Even} = \text{Odd}$
- $\text{Even} - \text{Odd} = \text{Odd}$

Instead of storing full prefix sum values, your solution tracks only the **parity** (even vs. odd, i.e., sum modulo 2) of the prefix sum as it iterates through the array. At each element, the number of valid subarrays ending at the current position is simply the count of previously seen prefix sums that have the opposite parity.

### 🔍 Approach

1. **Initialize State**:
   - `final int mod = 1000000007;`: Target modulo constant specified by the problem.
   - `int even = 1, odd = 0;`: Keeps track of how many prefix sums encountered so far are even vs. odd. `even` starts at `1` to represent the empty prefix (sum = `0` at index `-1`), which is an even sum.
   - `long res = 0;`: Stores the running total of subarrays with odd sums.
   - `int sum = 0;`: Tracks the parity (`0` for even, `1` for odd) of the running prefix sum.

2. **Iterate Through `arr`**:
   - For each element `x` in `arr`:
     - Update the prefix sum parity: `sum = (sum + x) % 2;`.
     - **If `sum == 1` (current prefix sum parity is odd):**
       - Any previously seen **even** prefix sum subtracted from this odd prefix sum produces an **odd** subarray sum.
       - Add `even` to `res` modulo `mod`.
       - Increment `odd++` because the current prefix sum is odd.
     - **Else (`sum == 0`, current prefix sum parity is even):**
       - Any previously seen **odd** prefix sum subtracted from this even prefix sum produces an **odd** subarray sum.
       - Add `odd` to `res` modulo `mod`.
       - Increment `even++` because the current prefix sum is even.

3. **Return Result**:
   - Cast `res` to `int` and return.

### 🧩 Algorithm

- **Prefix Parity Invariant**:
  - `even` holds the count of prefixes in `arr[0...i-1]` (including the empty prefix) whose sum is even.
  - `odd` holds the count of prefixes in `arr[0...i-1]` whose sum is odd.

- **State Transition**:
  For element $i$:
  $$\text{sum}_i = (\text{sum}_{i-1} + x) \pmod 2$$

  $$\text{res}_i = 
  \begin{cases} 
  (\text{res}_{i-1} + \text{even}) \pmod{10^9 + 7} & \text{if } \text{sum}_i = 1 \\ 
  (\text{res}_{i-1} + \text{odd}) \pmod{10^9 + 7} & \text{if } \text{sum}_i = 0 
  \end{cases}$$

### ✅ Why This Works

- **Base Case Handling**: Setting `even = 1` initially accounts for subarrays that start at index `0`. If a prefix sum up to index `i` is odd, subtracting the base prefix sum of `0` (which is even) yields an odd subarray sum from index `0` to `i`.
- **Exhaustive & Non-overlapping Count**: At each step `i`, checking the counter of opposite parity counts every single valid subarray ending at index `i`. Since every subarray ends at some index `i \in [0, N-1]`, summing these counts yields the exact total number of odd-sum subarrays without double counting.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the number of elements in `arr`. The solution uses a single loop through `arr`, performing constant time $\mathcal{O}(1)$ arithmetic operations per element.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space. Only scalar variables (`mod`, `even`, `odd`, `res`, `sum`) are used.

### 🧠 DSA Pattern

- **Prefix Sum / Dynamic Parity Counting**: Storing frequencies of prefix sum parities in scalar counters instead of maintaining an explicit hash map or prefix array.

### ⚠️ Common Mistakes

1. **Forgetting `even = 1` Initialization**: If `even` is initialized to `0` instead of `1`, subarrays starting at index `0` with an odd sum will not be counted.
2. **Modulo Handling**: Forgetting to apply `% mod` when accumulating `res` could lead to integer overflow if `res` were a 32-bit `int`. Using `long res` and modulating at each step avoids overflow.
3. **Negative Number Handling (Contextual Risk)**: `(sum + x) % 2` works correctly here because array elements are guaranteed to be positive (`1 <= arr[i] <= 100`). In Java, the `%` operator on negative numbers can return negative results, so if negative numbers were allowed, `(sum + x % 2 + 2) % 2` or bitwise XOR would be needed.

### 🚀 Optimization Notes

- The time complexity ($\mathcal{O}(N)$) and space complexity ($\mathcal{O}(1)$) are already **optimal**.
- **Bitwise XOR Optimization**: Since `sum` only tracks parity (`0` or `1`), updating `sum` could be done using bitwise operations:
  ```java
  sum ^= (x & 1);
  ```
  This avoids the modulo `% 2` division operation inside the loop, though modern compilers optimize this automatically.
