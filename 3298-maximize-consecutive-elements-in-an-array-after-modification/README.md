<h2><a href="https://leetcode.com/problems/maximize-consecutive-elements-in-an-array-after-modification">Maximize Consecutive Elements in an Array After Modification</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given a <strong>0-indexed</strong> array <code>nums</code> consisting of <strong>positive</strong> integers.</p>

<p>Initially, you can increase the value of <strong>any</strong> element in the array by <strong>at most</strong> <code>1</code>.</p>

<p>After that, you need to select <strong>one or more</strong> elements from the final array such that those elements are <strong>consecutive</strong> when sorted in increasing order. For example, the elements <code>[3, 4, 5]</code> are consecutive while <code>[3, 4, 6]</code> and <code>[1, 1, 2, 3]</code> are not.<!-- notionvc: 312f8c5d-40d0-4cd1-96cc-9e96a846735b --></p>

<p>Return <em>the <strong>maximum</strong> number of elements that you can select</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,1,5,1,1]
<strong>Output:</strong> 3
<strong>Explanation:</strong> We can increase the elements at indices 0 and 3. The resulting array is nums = [3,1,5,2,1].
We select the elements [<u><strong>3</strong></u>,<u><strong>1</strong></u>,5,<u><strong>2</strong></u>,1] and we sort them to obtain [1,2,3], which are consecutive.
It can be shown that we cannot select more than 3 consecutive elements.</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,4,7,10]
<strong>Output:</strong> 1
<strong>Explanation:</strong> The maximum consecutive elements that we can select is 1.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>6</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea behind this solution is that sorting the array allows us to build consecutive sequences incrementally. For every number `x` in the sorted array, we have two choices for how `x` can contribute to a consecutive sequence:
1. **Option 1**: Increment `x` to `x + 1`. This value can extend a consecutive sequence that currently ends at `x`.
2. **Option 2**: Leave `x` as `x`. This value can extend a consecutive sequence that currently ends at `x - 1`.

By maintaining a dynamic programming array where `dp[v]` holds the length of the longest consecutive sequence ending at value `v`, we can process elements one by one and update the maximum sequence lengths achievable.

### 🔍 Approach

1. **Sort the Array**: 
   `Arrays.sort(nums)` ensures elements are processed in ascending order. Processing smaller values first guarantees that when we evaluate `x`, all potential sequences ending at `x` or `x - 1` have already been computed from smaller elements.

2. **Allocate DP Array**:
   `int[] dp = new int[nums[nums.length - 1] + 2]` creates a lookup table sized to accommodate the maximum possible value (`max_val + 1`). `dp[v]` stores the length of the longest consecutive sequence ending at integer `v`.

3. **Process Each Element**:
   For every number `x` in `nums`:
   - `dp[x + 1] = dp[x] + 1`: We check if incrementing `x` to `x + 1` extends an existing sequence ending at `x`.
   - `dp[x] = dp[x - 1] + 1`: We check if keeping `x` as `x` extends an existing sequence ending at `x - 1`.

   *Crucial Detail*: `dp[x + 1]` is updated **before** `dp[x]`. This ordering ensures `dp[x + 1]` reads the previous value of `dp[x]` before `dp[x]` gets overwritten by the current element `x`.

4. **Find the Global Maximum**:
   `Arrays.stream(dp).max().getAsInt()` scans the `dp` array and returns the length of the longest consecutive sequence found across all possible ending values.

### 🧩 Algorithm

- **DP State**: 
  `dp[v]` = length of the longest consecutive sequence ending with value `v`.

- **DP Transitions** (processed in order for each `x` in sorted `nums`):
  1. $dp[x + 1] = dp[x] + 1$
  2. $dp[x] = dp[x - 1] + 1$

- **Invariant**:
  At any point during the loop, `dp[v]` represents the max sequence length ending at `v` constructed from a subset of the elements visited so far, with each element modified at most once by $+1$.

### ✅ Why This Works

- **Correct Sequence Continuation**: Incrementing `x` to `x + 1` directly connects to any sequence ending at `x`. Keeping `x` unchanged directly connects to any sequence ending at `x - 1`.
- **Order of Updates Prevents Reuse**: Updating `dp[x + 1]` first uses `dp[x]` as it was *before* considering the current element `x`. If `dp[x]` were updated first, then `dp[x + 1]` would mistakenly count the current element twice (once as `x` and once as `x + 1`).
- **Sorting Ensures Subproblem Order**: Since `nums` is sorted, when processing `x`, any number that could form `x - 1` or `x` has already been processed.

### ⏱️ Complexity

- **Time Complexity**: $O(N \log N + M)$, where $N$ is the number of elements in `nums` and $M = \max(\text{nums})$.
  - `Arrays.sort(nums)` takes $O(N \log N)$ time.
  - The loop iterates through `nums`, taking $O(N)$ time.
  - `Arrays.stream(dp).max()` scans the array of size $M + 2$, taking $O(M)$ time.
- **Space Complexity**: $O(M)$ auxiliary space.
  - The `dp` array size is directly proportional to the maximum value in `nums` ($\max(\text{nums}) + 2$).

### 🧠 DSA Pattern

- **Dynamic Programming** (1D Tabulation / State Transition on Values)
- **Sorting**

### ⚠️ Common Mistakes

1. **Reversing Update Order**: 
   If you execute `dp[x] = dp[x - 1] + 1` before `dp[x + 1] = dp[x] + 1`, `dp[x + 1]` will use the updated `dp[x]` value, effectively allowing a single element `x` to be used twice in the same sequence.

2. **Array Index Out of Bounds**:
   If the DP array is initialized to size `nums[nums.length - 1] + 1` instead of `+ 2`, accessing `dp[x + 1]` for the maximum element `x` will throw an `ArrayIndexOutOfBoundsException`.

### 🚀 Optimization Notes

1. **Stream Overhead & Traversal**:
   Instead of using `Arrays.stream(dp).max().getAsInt()` which introduces stream overhead and iterates over the entire $O(M)$ space, you could track the maximum value during the loop with a running `maxLen` variable (`maxLen = Math.max(maxLen, Math.max(dp[x], dp[x + 1]))`).

2. **Space Efficiency**:
   Because $M = \max(\text{nums})$ can be up to $10^6$, allocating an array of size $10^6 + 2$ takes a few megabytes of memory. Using a `HashMap<Integer, Integer>` instead of a fixed-size array would reduce space complexity to $O(N)$, though the array approach benefits from direct indexing speed and cache locality.
