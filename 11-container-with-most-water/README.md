<h2><a href="https://leetcode.com/problems/container-with-most-water">Container With Most Water</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer array <code>height</code> of length <code>n</code>. There are <code>n</code> vertical lines drawn such that the two endpoints of the <code>i<sup>th</sup></code> line are <code>(i, 0)</code> and <code>(i, height[i])</code>.</p>

<p>Find two lines that together with the x-axis form a container, such that the container contains the most water.</p>

<p>Return <em>the maximum amount of water a container can store</em>.</p>

<p><strong>Notice</strong> that you may not slant the container.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/07/17/question_11.jpg" style="width: 600px; height: 287px;" />
<pre>
<strong>Input:</strong> height = [1,8,6,2,5,4,8,3,7]
<strong>Output:</strong> 49
<strong>Explanation:</strong> The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> height = [1,1]
<strong>Output:</strong> 1
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == height.length</code></li>
	<li><code>2 &lt;= n &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= height[i] &lt;= 10<sup>4</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The goal is to find two vertical lines that, together with the x-axis, form a container holding the maximum area of water. The area between two lines at indices `low` and `high` is determined by two factors:
1. **Width**: The horizontal distance between the lines (`high - low`).
2. **Height**: The bottleneck vertical line, which is the shorter of the two heights (`Math.min(nums[low], nums[high])`).

Your implementation starts with the maximum possible width by placing pointers at the extreme ends of the array (`low = 0` and `high = n - 1`). To find a larger area while the width naturally decreases as pointers move inward, we must try to find taller lines.

The key observation is: moving the pointer pointing to the taller line will never increase the area, because the height remains limited by the shorter line while the width decreases. Thus, the only chance to achieve a larger area is to move the pointer associated with the **shorter line** inward, hoping to find a taller replacement line.

---

### 🔍 Approach

1. **Initialization**:
   - `n`: Stores the length of the array `nums`.
   - `res`: Initialized to `0` to keep track of the maximum area encountered.
   - *(Note: A brute-force nested loop approach was commented out in your solution, which originally computed area for all pairs in $O(N^2)$ time).*

2. **Two Pointers Traversal**:
   - Set `low = 0` (left side) and `high = n - 1` (right side).
   - Enter a loop that runs while `low < high`.

3. **Area Computation**:
   - `wid = high - low`: Calculates the horizontal distance between current indices.
   - `len = Math.min(nums[low], nums[high])`: Finds the maximum height water can reach without overflowing (the shorter line).
   - `area = wid * len`: Calculates the area formed by the current container.
   - `res = Math.max(res, area)`: Updates `res` if the current `area` is greater than the previous maximum.

4. **Greedy Pointer Advancement**:
   - Check if `nums[low] < nums[high]`:
     - If true, `low++` (advance the left pointer to search for a taller left boundary).
     - Else (`nums[low] >= nums[high]`), `high--` (decrement the right pointer to search for a taller right boundary).

5. **Return**:
   - Once `low` meets or crosses `high`, return `res`.

---

### 🧩 Algorithm

This solution implements a **Two-Pointer Greedy Search**:

- **Initial State**: $low = 0, high = n - 1, res = 0$
- **Loop Condition**: $low < high$
- **Area Calculation**: 
  $$\text{area}(low, high) = (high - low) \times \min(nums[low], nums[high])$$
- **State Transition Rule**:
  $$\text{Next State} = \begin{cases} (low + 1, high) & \text{if } nums[low] < nums[high] \\ (low, high - 1) & \text{otherwise} \end{cases}$$

---

### ✅ Why This Works

At any step $(low, high)$, suppose $nums[low] < nums[high]$. 

If we were to keep $low$ fixed and move $high$ inward to any index $k$ (where $low < k < high$):
- The new width $(k - low)$ would strictly be smaller than $(high - low)$.
- The new height $\min(nums[low], nums[k])$ can at best be equal to $nums[low]$ (it can never exceed $nums[low]$).

Therefore, for all $k$, $\text{area}(low, k) \le \text{area}(low, high)$. Moving $high$ while keeping $low$ fixed can never yield a larger area than what we have already evaluated. Hence, it is safe to eliminate index $low$ from further consideration by incrementing `low`. This greedy choice guarantees that we never discard a pair that could give a larger max area.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$
  - In each iteration of the `while` loop, either `low` is incremented or `high` is decremented.
  - The distance between `low` and `high` decreases by $1$ per step, leading to at most $n - 1$ steps.
- **Space Complexity**: $\mathcal{O}(1)$
  - Uses only a few primitive integer variables (`n`, `res`, `low`, `high`, `wid`, `len`, `area`), requiring constant extra space.

---

### 🧠 DSA Pattern

- **Two Pointers** (Shrinking Window)
- **Greedy**

---

### ⚠️ Common Mistakes

1. **Incorrect Pointer Advancement Logic**: Moving the pointer with the taller height instead of the shorter height. Doing so skips potential maximum area candidates.
2. **Loop Condition Errors**: Using `low <= high` instead of `low < high`. When `low == high`, `wid = 0` which yields an area of `0`, making the extra iteration redundant.
3. **Equal Height Handling**: Worrying about whether to increment `low` or decrement `high` when `nums[low] == nums[high]`. Your code uses `else high--`, which is completely correct because if both heights are equal, neither can yield a larger area with any inner bar unless both boundaries change.

---

### 🚀 Optimization Notes

- **Optimal Time & Space**: Your active two-pointer approach operates in optimal $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ auxiliary space.
- **Micro-Optimization (Optional)**: Instead of re-evaluating area step-by-step when moving inward, you could skip indices whose height is smaller than or equal to the previous bounding height (`len`), but this does not change the $\mathcal{O}(n)$ asymptotic time complexity.
