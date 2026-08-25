<h2><a href="https://leetcode.com/problems/smallest-missing-multiple-of-k">Smallest Missing Multiple of K</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given an integer array <code>nums</code> and an integer <code>k</code>, return the <strong>smallest positive multiple</strong> of <code>k</code> that is <strong>missing</strong> from <code>nums</code>.</p>

<p>A <strong>multiple</strong> of <code>k</code> is any positive integer divisible by <code>k</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [8,2,3,4,6], k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">10</span></p>

<p><strong>Explanation:</strong></p>

<p>The multiples of <code>k = 2</code> are 2, 4, 6, 8, 10, 12... and the smallest multiple missing from <code>nums</code> is 10.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,4,7,10,15], k = 5</span></p>

<p><strong>Output:</strong> <span class="example-io">5</span></p>

<p><strong>Explanation:</strong></p>

<p>The multiples of <code>k = 5</code> are 5, 10, 15, 20... and the smallest multiple missing from <code>nums</code> is 5.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 100</code></li>
	<li><code>1 &lt;= k &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To find the smallest positive missing multiple of `k`, the solution keeps track of which numbers exist in `nums` using a boolean lookup array (`arr`). 

Once all elements from `nums` are marked as present (`true`), the code starts checking positive multiples of `k` sequentially ($k, 2k, 3k, \dots$). The first multiple that is not marked in the boolean array is the smallest missing multiple.

### 🔍 Approach

1. **Boolean Lookup Table Initialization**:
   - `boolean[] arr = new boolean[201];`: Creates a direct-address array initialized to `false`. The size `201` is specifically chosen based on the constraint $nums[i] \le 100$ and $k \le 100$.

2. **Marking Present Numbers**:
   - `for (int i : nums)`: Iterates through each element `i` in the input array `nums` and sets `arr[i] = true`.

3. **Searching for the Missing Multiple**:
   - `int ans = k;`: Initializes `ans` to `k`, which is the first positive multiple of `k`.
   - `while (arr[ans]) { ans += k; }`: Keeps adding `k` to `ans` as long as `ans` is found in `arr`. The loop stops at the first multiple `ans` where `arr[ans]` is `false`.

4. **Return Result**:
   - Returns `ans`, which is the smallest positive multiple of `k` absent from `nums`.

### 🧩 Algorithm

1. Allocate a boolean array `arr` of size `201`.
2. For each number `x` in `nums`, set `arr[x] = true`.
3. Set candidate answer `ans = k`.
4. While `arr[ans] == true`:
   - `ans = ans + k`
5. Return `ans`.

### ✅ Why This Works

- **Correctness**: The loop starts at $k$ (the smallest positive multiple of $k$) and steps through subsequent multiples $2k, 3k, \dots$ in strictly increasing order. Because it checks multiples in order, the first multiple for which `arr[ans]` is `false` is guaranteed to be the *smallest* missing positive multiple.
- **Array Bounds Safety**: Since the constraint guarantees $nums[i] \le 100$, elements in `nums` will only mark indices up to `100` as `true`. If index `100` is marked `true` and $k = 100$, the loop will check index `100`, increment `ans` to `200`, and then check `arr[200]`. Since index `200` is `false`, the loop terminates. Choosing size `201` ensures `arr[200]` is within bounds without throwing an `ArrayIndexOutOfBoundsException`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N + \frac{M}{k})$ where $N$ is the length of `nums` and $M$ is the maximum value in `nums` ($M \le 100$).
  - Filling the boolean array takes $\mathcal{O}(N)$ time.
  - The `while` loop executes at most $\frac{100}{k} + 1$ times because any number greater than $100$ cannot be in `nums` and will immediately terminate the loop. Thus, total time complexity is effectively $\mathcal{O}(N)$.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space.
  - The boolean array `arr` has a fixed size of `201`, requiring constant memory regardless of the size of `nums`.

### 🧠 DSA Pattern

- **Hashing / Direct Addressing**: Using an array index as a key to achieve $\mathcal{O}(1)$ lookups for presence checking.

### ⚠️ Common Mistakes

1. **Array Index Out of Bounds**:
   - Sizing `arr` to `101` instead of `201`. If $100$ is present in `nums` and $k = 100$, `ans` increments from $100$ to $200$. Evaluating `arr[200]` on a size-101 array would crash with an `ArrayIndexOutOfBoundsException`.
2. **Starting `ans` at 0**:
   - Multiples of $k$ are defined as positive integers divisible by $k$ ($k, 2k, 3k, \dots$). Starting `ans = 0` would incorrectly evaluate $0$ as a potential answer.

### 🚀 Optimization Notes

- The solution is already optimal for the given problem constraints.
- Using a primitive `boolean[]` of fixed size `201` is faster and uses less memory than dynamic hashing structures due to direct memory indexing and zero object overhead.
