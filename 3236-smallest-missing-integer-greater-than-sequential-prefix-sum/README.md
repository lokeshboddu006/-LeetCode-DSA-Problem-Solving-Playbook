<h2><a href="https://leetcode.com/problems/smallest-missing-integer-greater-than-sequential-prefix-sum">Smallest Missing Integer Greater Than Sequential Prefix Sum</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given a <strong>0-indexed</strong> array of integers <code>nums</code>.</p>

<p>A prefix <code>nums[0..i]</code> is <strong>sequential</strong> if, for all <code>1 &lt;= j &lt;= i</code>, <code>nums[j] = nums[j - 1] + 1</code>. In particular, the prefix consisting only of <code>nums[0]</code> is <strong>sequential</strong>.</p>

<p>Return <em>the <strong>smallest</strong> integer</em> <code>x</code> <em>missing from</em> <code>nums</code> <em>such that</em> <code>x</code> <em>is greater than or equal to the sum of the <strong>longest</strong> sequential prefix.</em></p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3,2,5]
<strong>Output:</strong> 6
<strong>Explanation:</strong> The longest sequential prefix of nums is [1,2,3] with a sum of 6. 6 is not in the array, therefore 6 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [3,4,5,1,12,14,13]
<strong>Output:</strong> 15
<strong>Explanation:</strong> The longest sequential prefix of nums is [3,4,5] with a sum of 12. 12, 13, and 14 belong to the array while 15 does not. Therefore 15 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 50</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 50</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution breaks the problem down into two key steps:

1. **Calculate the sum of the longest sequential prefix**:
   Start with the first element `nums[0]`. Keep extending the prefix and adding elements as long as each consecutive element is exactly `1` greater than the previous one (`nums[i] == nums[i - 1] + 1`). Stop as soon as this contiguous chain breaks.

2. **Find the smallest missing integer $\ge$ prefix sum**:
   Collect all elements of `nums` into a lookup set (`HashSet`). Starting from the computed prefix sum `s`, check if `s` is present in the set. If it is, increment `s` by 1 and check again. The first value of `s` not found in the set is your answer.

---

### 🔍 Approach

1. **Sequential Prefix Sum**:
   - Initialize `s = nums[0]` to hold the sum and `i = 1` as the pointer to inspect subsequent elements.
   - Run a `while` loop with condition `i < nums.length && nums[i] == nums[i - 1] + 1`.
   - In each iteration, add `nums[i]` to `s` and increment `i` using post-increment (`s += nums[i++]`).

2. **Populate HashSet for Fast Lookups**:
   - Create a `HashSet<Integer> h`.
   - Iterate through every element `x` in `nums` and add it to `h`. This allows $O(1)$ average time complexity checks for presence in the array.

3. **Find the Smallest Missing Integer**:
   - Run a `while` loop with condition `h.contains(s)`.
   - As long as `s` exists in the set, increment `s` (`s++`).
   - Once `s` is not found in `h`, return `s`.

---

### 🧩 Algorithm

1. Initialize `s = nums[0]` and `i = 1`.
2. While `i < nums.length` and `nums[i] == nums[i - 1] + 1`:
   - `s = s + nums[i]`
   - `i = i + 1`
3. Create `HashSet` `h` and add all elements from `nums` into `h`.
4. While `h.contains(s)` is true:
   - `s = s + 1`
5. Return `s`.

---

### ✅ Why This Works

- **Sequential Prefix Correctness**: The problem defines a sequential prefix as starting at index `0` where every element `nums[j] = nums[j - 1] + 1`. By starting `i` at `1` and strictly checking `nums[i] == nums[i - 1] + 1`, the code correctly accumulates the sum of the longest valid sequential prefix and stops at the exact point of non-consecutiveness.
- **Smallest Missing Candidate**: The problem asks for the smallest integer $x \ge \text{sum}$ missing from `nums`. Starting candidate `s` at the prefix sum and incrementing by $1$ on set collisions guarantees that the search checks candidates in strictly increasing order. The first candidate missing from `h` is mathematically guaranteed to be the smallest missing integer $\ge$ the prefix sum.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$ where $N$ is the length of `nums`.
  - **Sequential prefix loop**: Traverses at most $N$ elements, taking $\mathcal{O}(N)$ time.
  - **HashSet insertion**: Inserting $N$ elements into a hash set takes $\mathcal{O}(N)$ average time.
  - **Missing number lookup loop**: Since `nums` contains $N$ elements, `h.contains(s)` can return `true` at most $N$ times. Thus, the loop runs at most $N + 1$ times, taking $\mathcal{O}(N)$ average time.
  - Overall time complexity is $\mathcal{O}(N)$.

- **Space Complexity**: $\mathcal{O}(N)$.
  - Storing the array elements in the `HashSet` requires auxiliary space proportional to the number of distinct elements, which is at most $N$.

---

### 🧠 DSA Pattern

- **Hashing**: Storing array elements in a `HashSet` to achieve $O(1)$ average-time checks when searching for missing integers.
- **Array Traversal / Simulation**: Iterating through the prefix of the array to compute the initial sum according to specific sequence rules.

---

### ⚠️ Common Mistakes

- **Not stopping the prefix sum early**: Conflating a sequential prefix (which must start at index 0 and be strictly contiguous) with sequential subsequences elsewhere in the array.
- **Single element array edge case**: Forgetting that an array of length 1 (or a sequence where index 1 breaks the rule) still has a valid sequential prefix consisting of `nums[0]`. Initializing `s = nums[0]` handles this correctly.
- **Off-by-one loop checks**: Accidentally comparing `nums[i]` with `nums[i]` or going out of bounds when checking `nums[i - 1] + 1`.

---

### 🚀 Optimization Notes

- **Optimal Time Complexity**: The solution already achieves the optimal linear time complexity $\mathcal{O}(N)$.
- **Constant Space / Micro-optimization**: Since the problem constraints specify $N \le 50$ and $nums[i] \le 50$, a primitive boolean array or `BitSet` (e.g. `boolean[] present = new boolean[101]`) could be used instead of `HashSet<Integer>` to avoid object allocation and boxing overhead. However, `HashSet` is clean, concise, and completely optimal within given limits.
