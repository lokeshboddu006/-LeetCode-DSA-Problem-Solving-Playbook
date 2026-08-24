<h2><a href="https://leetcode.com/problems/trapping-rain-water">Trapping Rain Water</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given <code>n</code> non-negative integers representing an elevation map where the width of each bar is <code>1</code>, compute how much water it can trap after raining.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img src="https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png" style="width: 412px; height: 161px;" />
<pre>
<strong>Input:</strong> height = [0,1,0,2,1,0,1,3,2,1,2,1]
<strong>Output:</strong> 6
<strong>Explanation:</strong> The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> height = [4,2,0,3,2,5]
<strong>Output:</strong> 9
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == height.length</code></li>
	<li><code>1 &lt;= n &lt;= 2 * 10<sup>4</sup></code></li>
	<li><code>0 &lt;= height[i] &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation behind this solution is that the amount of water trapped directly above any single bar at index `i` depends on two values:
1. The tallest bar to its left (including itself).
2. The tallest bar to its right (including itself).

The water level at position `i` is constrained by the **shorter** of these two peak heights (the bottleneck). Subtracting the bar's own height (`nums[i]`) from this boundary height gives the volume of trapped water above that specific bar. 

Instead of searching left and right for every single bar repeated in an $O(n^2)$ loop (as shown in the commented-out block), this solution precomputes the highest bar to the left and right for all indices beforehand using two auxiliary arrays, bringing the runtime down to linear time.

### 🔍 Approach

1. **Base Case Check**:
   - Check if the array size `n < 3`. If so, return `0` because trapped water requires at least two boundary bars and one middle bar.

2. **Prefix Maximum Array (`leftMax`)**:
   - Create an array `leftMax` of size `n`.
   - Set `leftMax[0] = nums[0]`.
   - Iterate forward from `i = 1` to `n - 1`, storing the cumulative maximum: `leftMax[i] = Math.max(leftMax[i - 1], nums[i])`.

3. **Suffix Maximum Array (`rightMax`)**:
   - Create an array `rightMax` of size `n`.
   - Set `rightMax[n - 1] = nums[n - 1]`.
   - Iterate backward from `i = n - 2` down to `0`, storing the cumulative maximum: `rightMax[i] = Math.max(rightMax[i + 1], nums[i])`.

4. **Calculate Trapped Water**:
   - Initialize `trapWater = 0`.
   - Iterate through every index `i` from `0` to `n - 1`:
     - Determine the water height boundary at `i`: `height = Math.min(leftMax[i], rightMax[i])`.
     - Add `height - nums[i]` to `trapWater`.

5. **Return Result**:
   - Return total `trapWater`.

### 🧩 Algorithm

The approach uses **Dynamic Programming / Array Precomputation (Prefix and Suffix Maximums)**:

- **Prefix Recurrence**:
  $$\text{leftMax}[i] = \max(\text{leftMax}[i - 1], \text{nums}[i]) \quad \text{for } 1 \le i < n$$

- **Suffix Recurrence**:
  $$\text{rightMax}[i] = \max(\text{rightMax}[i + 1], \text{nums}[i]) \quad \text{for } n - 2 \ge i \ge 0$$

- **Water Formula**:
  $$\text{trapped}[i] = \min(\text{leftMax}[i], \text{rightMax}[i]) - \text{nums}[i]$$
  $$\text{Total Water} = \sum_{i=0}^{n-1} \text{trapped}[i]$$

### ✅ Why This Works

- **Correct Boundary Determination**: At index `i`, water cannot overflow past `leftMax[i]` on the left side or `rightMax[i]` on the right side. The limiting barrier is always $\min(\text{leftMax}[i], \text{rightMax}[i])$.
- **Self-contained Calculation**: Since `leftMax[i]` includes `nums[i]` and `rightMax[i]` includes `nums[i]`, `height` will always be $\ge \text{nums}[i]$, ensuring `height - nums[i]` is never negative.
- **Complete Coverage**: By evaluating every bar independently and summing their trapped water, the total volume is correctly accumulated.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - Pass 1 (filling `leftMax`): $\mathcal{O}(n)$
  - Pass 2 (filling `rightMax`): $\mathcal{O}(n)$
  - Pass 3 (calculating total water): $\mathcal{O}(n)$
  - Overall time complexity is linear $\mathcal{O}(n)$.

- **Space Complexity**: $\mathcal{O}(n)$
  - Two auxiliary integer arrays (`leftMax` and `rightMax`) of length $n$ are allocated to store prefix and suffix maximums.

### 🧠 DSA Pattern

- **Dynamic Programming / Prefix-Suffix Arrays**: Precalculating historical max values from both directions (left-to-right prefix max and right-to-left suffix max) to answer range query bounds in $\mathcal{O}(1)$ time per element.

### ⚠️ Common Mistakes

1. **Off-by-one errors during array population**:
   - Initializing `rightMax` loop from `n - 1` instead of `n - 2` while trying to read `rightMax[i + 1]` would cause an `ArrayIndexOutOfBoundsException`.
2. **Confusing `Math.min` and `Math.max`**:
   - Using `Math.max` when calculating `height` instead of `Math.min` would incorrectly assume water can rise to the taller wall, causing overflow conceptually and mathematically.
3. **Forgetting edge cases**:
   - Omitting the `n < 3` check or failing to initialize index `0` and `n - 1` explicitly before starting the loops.

### 🚀 Optimization Notes

- **Time Optimality**: The solution achieves the optimal $\mathcal{O}(n)$ time complexity by replacing the commented-out nested loops ($\mathcal{O}(n^2)$) with linear passes.
- **Space Trade-off**: The solution uses $\mathcal{O}(n)$ space to store `leftMax` and `rightMax`. While this is very readable and straightforward to revise, extra memory is allocated for the arrays.
