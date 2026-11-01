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

Instead of physically simulating the process of incrementing $n - 1$ elements by $1$ in every step, your solution relies on a key mathematical equivalence:

* Incrementing $n - 1$ elements by $1$ relative to the remaining $1$ element is conceptually identical to **decrementing $1$ element by $1$** relative to the rest.

To bring all elements to the same value with the minimum number of steps, the optimal target value is the minimum element in the initial array. Each element $n$ in the array must be "reduced" to match this minimum value $m$. The number of steps required for a single element $n$ is simply $(n - m)$, and the total moves required is the sum of $(n - m)$ over all elements.

### 🔍 Approach

Your code executes in two distinct passes over the array:

1. **Find the Minimum Value (`m`)**:
   - Initialize `m` to `Integer.MAX_VALUE`.
   - Iterate through every element `n` in `nums` using a enhanced `for`-loop and update `m = Math.min(m, n)`.

2. **Calculate Total Moves (`ans`)**:
   - Initialize `ans = 0`.
   - Iterate through `nums` a second time using another enhanced `for`-loop.
   - For each element `n`, calculate its offset from the minimum element `(n - m)` and accumulate it into `ans`.

3. **Return Result**:
   - Return `ans` as the final minimum number of moves.

### 🧩 Algorithm

- **Mathematical Transformation**:
  $$\text{Total Moves} = \sum_{i=0}^{n-1} (nums[i] - m)$$
  where $m = \min(nums)$.

- **Loop Invariants**:
  - After the first loop, `m` is guaranteed to be $\min(nums[0 \dots n-1])$.
  - During the second loop, `ans` represents the running sum of differences for all elements processed so far.

### ✅ Why This Works

- Each move increases $n - 1$ elements by $1$, which increases the total sum of the array by $n - 1$ while keeping the relative difference between the incremented elements unchanged, but shrinking the gap between the un-incremented element and the rest.
- Equivalent view: Every operation reduces the gap between an element and the minimum element by $1$.
- Since the minimum element cannot be reduced below itself, every element $nums[i]$ needs exactly $nums[i] - m$ operations to be leveled with $m$. Summing these individual differences gives the exact minimum number of operations required.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of `nums`. The solution makes two full linear passes over the array: one pass to find the minimum element, and one pass to calculate the accumulated difference.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. The solution only allocates two primitive integer variables (`m` and `ans`) regardless of the size of the input array.

### 🧠 DSA Pattern

- **Math** (Mathematical Reduction / Relative Invariants)

### ⚠️ Common Mistakes

1. **Overthinking with Simulation or Sorting**: Trying to sort the array or simulate the increments directly leads to unnecessary $\mathcal{O}(N \log N)$ or $\mathcal{O}(N \cdot \text{moves})$ complexity, which would TLE (Time Limit Exceeded) for $N = 10^5$.
2. **Integer Overflow during Summation**: A common alternative formulation is $\text{sum}(nums) - n \times m$. Calculating $\text{sum}(nums)$ directly can overflow standard 32-bit signed integers if elements are large ($10^9$). Your approach of adding $(n - m)$ directly into `ans` helps reduce intermediate overflow risks, as long as the total answer fits in a 32-bit integer (which the problem guarantees).

### 🚀 Optimization Notes

- **Single Pass Alternative**: You could combine the minimum search and the array summation into a single pass if using the formula `sum - n * min`, but that risks integer overflow during the `sum` calculation if intermediate sums exceed `Integer.MAX_VALUE`.
- **Current Implementation**: Your implementation is already optimal in terms of time ($\mathcal{O}(N)$) and space ($\mathcal{O}(1)$), and avoids intermediate large-sum overflow by computing differences per element.
