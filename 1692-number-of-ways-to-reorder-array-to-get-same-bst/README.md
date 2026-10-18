<h2><a href="https://leetcode.com/problems/number-of-ways-to-reorder-array-to-get-same-bst">Number of Ways to Reorder Array to Get Same BST</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given an array <code>nums</code> that represents a permutation of integers from <code>1</code> to <code>n</code>. We are going to construct a binary search tree (BST) by inserting the elements of <code>nums</code> in order into an initially empty BST. Find the number of different ways to reorder <code>nums</code> so that the constructed BST is identical to that formed from the original array <code>nums</code>.</p>

<ul>
	<li>For example, given <code>nums = [2,1,3]</code>, we will have 2 as the root, 1 as a left child, and 3 as a right child. The array <code>[2,3,1]</code> also yields the same BST but <code>[3,2,1]</code> yields a different BST.</li>
</ul>

<p>Return <em>the number of ways to reorder</em> <code>nums</code> <em>such that the BST formed is identical to the original BST formed from</em> <code>nums</code>.</p>

<p>Since the answer may be very large, <strong>return it modulo </strong><code>10<sup>9</sup> + 7</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2026/02/23/1569_ex0.png" style="width: 160px; height: 160px;" />
<pre>
<strong>Input:</strong> nums = [2,1,3]
<strong>Output:</strong> 1
<strong>Explanation:</strong> We can reorder nums to be [2,3,1] which will yield the same BST. There are no other ways to reorder nums which will yield the same BST.
</pre>

<p><strong class="example">Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2026/02/23/1569_ex.png" style="width: 270px; height: 270px;" />
<pre>
<strong>Input:</strong> nums = [3,4,5,1,2]
<strong>Output:</strong> 5
<strong>Explanation:</strong> The following 5 arrays will yield the same BST: 
[3,1,2,4,5]
[3,1,4,2,5]
[3,1,4,5,2]
[3,4,1,2,5]
[3,4,1,5,2]
</pre>

<p><strong class="example">Example 3:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2026/02/23/1569_ex2.png" style="width: 205px; height: 205px;" />
<pre>
<strong>Input:</strong> nums = [1,2,3]
<strong>Output:</strong> 0
<strong>Explanation:</strong> There are no other orderings of nums that will yield the same BST.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 1000</code></li>
	<li><code>1 &lt;= nums[i] &lt;= nums.length</code></li>
	<li>All integers in <code>nums</code> are <strong>distinct</strong>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the number of reorderings of `nums` that yield the exact same Binary Search Tree (BST).

When inserting elements into a BST:
1. The first element encountered always becomes the root node of the current subtree.
2. All elements smaller than the root are routed to the left subtree.
3. All elements larger than the root are routed to the right subtree.

To preserve the BST structure:
- The root of any subtree must always be processed first.
- The relative ordering of elements in the left subtree must be maintained amongst themselves.
- The relative ordering of elements in the right subtree must be maintained amongst themselves.
- However, elements belonging to the left subtree and right subtree can be **interleaved** in any order without affecting the BST structure, as long as their internal relative orderings are preserved.

If the left subtree has $L$ elements and the right subtree has $R$ elements, there are $\binom{L + R}{L}$ ways to pick positions for the left subtree elements out of the total $L + R$ available slots. The rest of the slots are automatically filled by the right subtree elements. 

Thus, your solution uses **Combinatorics** combined with **Divide and Conquer** to recursively compute the number of ways for each subtree.

---

### 🔍 Approach

1. **Precomputing Combinations (`comb` table)**:
   - In `numOfWays(int[] nums)`, you construct a 2D array `comb` of size `(n + 1) x (n + 1)` using Pascal's Triangle:
     `comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD`.
   - This allows $O(1)$ lookups for $\binom{n}{k} \pmod{10^9 + 7}$.

2. **Recursive Function (`solve`)**:
   - **Base Case**: If the list size `n <= 2`, there is only $1$ valid ordering for these elements (lists of size 0, 1, or 2 have no alternative orderings that preserve the BST structure).
   - **Root Selection**: Pick the first element `root = nums.get(0)`.
   - **Partitioning**: Iterate through the rest of the list from index `1` to `n - 1`. Place elements strictly smaller than `root` into the `left` list and larger elements into the `right` list.
   - **Recursive Step**: Recursively call `solve(left)` and `solve(right)` to get `leftWays` and `rightWays`.
   - **Interleaving Calculation**: 
     - Choose `left.size()` positions out of the remaining `n - 1` elements: `comb[n - 1][left.size()]`.
     - Multiply this combinatoric value by `leftWays` and `rightWays` modulo `MOD`.

3. **Final Answer Adjustment**:
   - The total ways returned by `solve(nums)` includes the original array sequence.
   - The problem asks for the number of **reorderings** (excluding the original order), so you return `(ans - 1 + MOD) % MOD`.

---

### 🧩 Algorithm

#### Pascal's Triangle Combination Recurrence
$$\text{comb}[i][j] = (\text{comb}[i-1][j-1] + \text{comb}[i-1][j]) \bmod (10^9 + 7)$$

#### Recursive Divide and Conquer Transition
For a list $S$ of size $N$:
$$\text{solve}(S) = 
\begin{cases} 
1 & \text{if } N \le 2 \\
\left( \binom{N - 1}{|S_{\text{left}}|} \times \text{solve}(S_{\text{left}}) \times \text{solve}(S_{\text{right}}) \right) \bmod (10^9 + 7) & \text{if } N > 2
\end{cases}$$

#### Main Function Result
$$\text{result} = (\text{solve}(\text{nums}) - 1 + \text{MOD}) \bmod \text{MOD}$$

---

### ✅ Why This Works

- **Preserving Tree Hierarchy**: By selecting `nums.get(0)` as the root and partitioning the remaining elements into `left` and `right`, you guarantee that every subtree built recursively preserves its original parent-child relationships.
- **Combinatorial Interleaving**: Choosing $\binom{N - 1}{L}$ places $L$ left-subtree elements into $N-1$ remaining slots. Since left and right elements operate independently during BST insertion, any valid interleaving produces the identical BST as long as internal relative order is maintained.
- **Handling Modulo**: Taking `% MOD` at each multiplication step (`ways * leftWays % MOD * rightWays % MOD`) avoids 64-bit integer overflow during calculations.

---

### ⏱️ Complexity

- **Time Complexity**: 
  - **Pascal's Triangle Precomputation**: $O(N^2)$ to fill the `comb` table of size $(N+1) \times (N+1)$.
  - **Recursive Decomposition**: $O(N^2)$ in the worst case (a degenerate/skewed BST where partitioning takes $N + (N-1) + \dots + 1$ operations) or $O(N \log N)$ on average for balanced BSTs.
  - **Total Time Complexity**: $\mathcal{O}(N^2)$

- **Space Complexity**:
  - **Combination Table**: $O(N^2)$ space to store `comb[N+1][N+1]`.
  - **Recursion Stack & List Allocations**: $O(N^2)$ total memory allocated across recursive calls due to list creation for partition subsets, with a stack depth up to $O(N)$.
  - **Total Space Complexity**: $\mathcal{O}(N^2)$

---

### 🧠 DSA Pattern

- **Divide and Conquer**: Splitting the array into left and right subproblems based on the root node and combining their results.
- **Combinatorics (Pascal's Triangle Dynamic Programming)**: Precalculating combinations $\binom{n}{k}$ using DP to handle interleaving possibilities.

---

### ⚠️ Common Mistakes

1. **Integer Overflow on Multiplication**:
   - Multiplying two `long` values like `ways * leftWays` can overflow a standard 32-bit `int` before applying `% MOD`. Your code correctly uses `long` for `ways`, `leftWays`, `rightWays`, and the `comb` array.

2. **Negative Modulo Result**:
   - Subtracting 1 from `ans` could result in `-1` if `ans == 0`. Adding `MOD` before taking `% MOD` (`(ans - 1 + MOD) % MOD`) safely handles this edge case.

3. **Incorrect Base Case Value**:
   - Returning `0` instead of `1` when $N \le 2$ would cause all sub-products to evaluate to `0`. Returning `1` is correct because an empty, single-element, or two-element sequence has exactly 1 valid order.

---

### 🚀 Optimization Notes

1. **List Allocations in Recursion**:
   - In `solve(java.util.List<Integer> nums)`, new `ArrayList` instances (`left` and `right`) are created at every stack level. This causes frequent heap allocations.
2. **Array-to-List Overhead**:
   - The overloaded `solve(int[] nums)` creates an initial `ArrayList<Integer>` from the array, adding an extra setup pass over the input.
3. **Space for Combination Table**:
   - The `comb` matrix of size $1001 \times 1001$ uses roughly $8 \text{ MB}$ of memory, which easily fits within standard competitive programming limits for $N \le 1000$.
