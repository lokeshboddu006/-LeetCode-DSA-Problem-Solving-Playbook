<h2><a href="https://leetcode.com/problems/minimum-moves-to-equal-array-elements">Minimum Moves to Equal Array Elements</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given an integer array <code>nums</code> of size <code>n</code>, return <em>the minimum number of moves required to make all array elements equal</em>.</p>

<p>In one move, you can increment <code>n - 1</code> elements of the array by <code>1</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3]
<strong>Output:</strong> 3
<strong>Explanation:</strong> Only three moves are needed (remember each move increments two elements):
[1,2,3]  =&gt;  [2,3,3]  =&gt;  [3,4,3]  =&gt;  [4,4,4]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,1,1]
<strong>Output:</strong> 0
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == nums.length</code></li>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>9</sup> &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
	<li>The answer is guaranteed to fit in a <strong>32-bit</strong> integer.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Instead of simulating the process of adding $1$ to $n - 1$ elements in every move, we can look at the relative differences between elements. 

Incrementing $n - 1$ elements by $1$ is relative-mathematically identical to **decrementing a single element by $1$**. To make all elements equal, every element $x$ needs to be reduced down to the minimum value in the array (`lo`). 

Therefore, the minimum number of moves required is simply the sum of differences between each element $x$ and the global minimum value `lo`.

### 🔍 Approach

1. **Find the Minimum Element (`lo`)**:
   - The code uses Java Streams (`Arrays.stream(nums).min().getAsInt()`) to scan the array `nums` and extract the smallest value, assigning it to `lo`.

2. **Calculate Total Moves**:
   - Initialize `moves = 0`.
   - Iterate through each integer `x` in `nums`.
   - In each iteration, add `x - lo` to `moves`. This represents how many "decrements" (or relative moves) it takes to bring `x` down to the minimum value `lo`.

3. **Return Result**:
   - Return `moves`, which contains the sum $\sum (x - \text{lo})$.

### 🧩 Algorithm

- **Mathematical Transformation**:
  - Let $S$ be the initial sum of the array, $n$ be the array length, and $m$ be the number of moves.
  - Each move increases $n - 1$ elements by $1$, so the sum after $m$ moves becomes $S + m \times (n - 1)$.
  - Since the minimum element increases by $1$ in every move (as it is always among the $n - 1$ smallest elements in a move until all become equal), the final target value for every element is $\text{lo} + m$.
  - Equating total final sums: 
    $$S + m(n - 1) = n \times (\text{lo} + m)$$
    $$S + mn - m = n \cdot \text{lo} + mn$$
    $$m = S - n \cdot \text{lo} = \sum_{x \in \text{nums}} (x - \text{lo})$$

### ✅ Why This Works

Every move increases the gap between the unmodified element and the rest of the elements by 1, which reduces the distance of all elements relative to the baseline (the minimum value). Since the minimum element must eventually reach the same final value as all other elements, each element $x$ requires exactly $x - \text{lo}$ operations to align with the rest of the array. Summing $(x - \text{lo})$ for all elements gives the exact minimum total moves.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$
  - `Arrays.stream(nums).min()` visits all $n$ elements once to find the minimum value.
  - The `for-each` loop iterates over all $n$ elements once to accumulate `moves += x - lo`.
  - Total time complexity is $\mathcal{O}(n) + \mathcal{O}(n) = \mathcal{O}(n)$.

- **Space Complexity:** $\mathcal{O}(1)$ (or $\mathcal{O}(1)$ auxiliary space beyond primitive variable overheads)
  - The computation only uses a few integer primitive variables (`lo`, `moves`, `x`). No extra heap allocations or data structures are created.

### 🧠 DSA Pattern

- **Math** (Relative Invariant / Problem Reduction)
- **Greedy** (Using the minimum element as the anchor reference)

### ⚠️ Common Mistakes

1. **Integer Overflow in Summation**:
   - Although the problem statement guarantees that the final answer fits in a 32-bit signed integer (`int`), calculating $\sum x - n \cdot \text{lo}$ as a single sum step ($S - n \cdot \text{lo}$) directly could overflow 32-bit integer limits if $S$ or $n \cdot \text{lo}$ exceeds $2^{31} - 1$. Subtracting `lo` from `x` inside the loop (`moves += x - lo`) helps prevent intermediate overflow.

2. **Simulating the Moves**:
   - Attempting to increment $n - 1$ elements step-by-step using simulation leads to Time Limit Exceeded ($\mathcal{O}(n \cdot \text{moves})$ time complexity).

### 🚀 Optimization Notes

- **Stream Overhead**:
  - `Arrays.stream(nums).min().getAsInt()` creates a Stream pipeline, which introduces minor object overhead and autoboxing/unboxing overhead in Java compared to a simple primitive `for` loop.
  - Replacing the stream call with a standard `for` loop to find the minimum in a single pass can yield a small execution speedup, though both are asymptotically optimal $\mathcal{O}(n)$.
- The solution is already optimal in terms of asymptotic time ($\mathcal{O}(n)$) and space ($\mathcal{O}(1)$) complexity.
