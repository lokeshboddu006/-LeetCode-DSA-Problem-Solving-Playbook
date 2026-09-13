<h2><a href="https://leetcode.com/problems/image-overlap">Image Overlap</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given two images, <code>img1</code> and <code>img2</code>, represented as binary, square matrices of size <code>n x n</code>. A binary matrix has only <code>0</code>s and <code>1</code>s as values.</p>

<p>We <strong>translate</strong> one image however we choose by sliding all the <code>1</code> bits left, right, up, and/or down any number of units. We then place it on top of the other image. We can then calculate the <strong>overlap</strong> by counting the number of positions that have a <code>1</code> in <strong>both</strong> images.</p>

<p>Note also that a translation does <strong>not</strong> include any kind of rotation. Any <code>1</code> bits that are translated outside of the matrix borders are erased.</p>

<p>Return <em>the largest possible overlap</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/09/overlap1.jpg" style="width: 450px; height: 231px;" />
<pre>
<strong>Input:</strong> img1 = [[1,1,0],[0,1,0],[0,1,0]], img2 = [[0,0,0],[0,1,1],[0,0,1]]
<strong>Output:</strong> 3
<strong>Explanation:</strong> We translate img1 to right by 1 unit and down by 1 unit.
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/09/overlap_step1.jpg" style="width: 450px; height: 105px;" />
The number of positions that have a 1 in both images is 3 (shown in red).
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/09/overlap_step2.jpg" style="width: 450px; height: 231px;" />
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> img1 = [[1]], img2 = [[1]]
<strong>Output:</strong> 1
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> img1 = [[0]], img2 = [[0]]
<strong>Output:</strong> 0
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == img1.length == img1[i].length</code></li>
	<li><code>n == img2.length == img2[i].length</code></li>
	<li><code>1 &lt;= n &lt;= 30</code></li>
	<li><code>img1[i][j]</code> is either <code>0</code> or <code>1</code>.</li>
	<li><code>img2[i][j]</code> is either <code>0</code> or <code>1</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of this solution is to simulate all possible 2D translations (shifts) between two binary matrices `A` and `B` by testing all combinations of horizontal and vertical offset values.

Instead of physically moving matrix elements around in memory, the code keeps the matrices stationary and uses offset indices (`xShift`, `yShift`) to define the overlapping region between them. By iterating through all valid shifts along the X and Y axes and evaluating both shift directions (left/right and up/down by swapping matrix roles), the solution counts how many matching `1`s fall into the same overlapping coordinates and tracks the maximum count observed across all valid shift configurations.

### 🔍 Approach

1. **Outer Loop (`largestOverlap`)**:
   - The method iterates through every possible row offset `yShift` from `0` to `N - 1` and column offset `xShift` from `0` to `N - 1`.
   - For each offset pair `(xShift, yShift)`, it computes the overlap in two orientations:
     1. Shifting matrix `A` relative to `B`: `shiftAndCount(xShift, yShift, A, B)`
     2. Shifting matrix `B` relative to `A`: `shiftAndCount(xShift, yShift, B, A)`
   - The global maximum `maxOverlaps` is updated with the highest overlap found.

2. **Helper Function (`shiftAndCount`)**:
   - Takes shift distances `xShift` and `yShift`, a reference matrix `M`, and a target matrix `R`.
   - It simultaneously evaluates two horizontal shift directions for the current vertical shift:
     - **Up-and-Left direction (`leftShiftCount`)**: Compares sub-region `M[mRow][mCol]` with `R[rRow][rCol]`, starting from `mRow = yShift` and `mCol = xShift`, while `rRow` and `rCol` start at `0`.
     - **Up-and-Right direction (`rightShiftCount`)**: Compares sub-region `M[mRow][rCol]` with `R[rRow][mCol]`.
   - It checks if both corresponding cells equal `1` (`M[...] == 1 && M[...] == R[...]`).
   - Returns the maximum overlap count between the left and right shifts computed in that pass (`Math.max(leftShiftCount, rightShiftCount)`).

### 🧩 Algorithm

1. Initialize `maxOverlaps = 0`.
2. For each `yShift` from `0` to `N - 1`:
   - For each `xShift` from `0` to `N - 1`:
     - Calculate overlap score for `(xShift, yShift)` with `A` as base `M` and `B` as reference `R`.
     - Calculate overlap score for `(xShift, yShift)` with `B` as base `M` and `A` as reference `R`.
     - Update `maxOverlaps` with the maximum score obtained.
3. Return `maxOverlaps`.

#### Coordinate Mapping Invariant in `shiftAndCount`:
- `mRow` ranges from `yShift` to `N - 1`, while `rRow` ranges from `0` to `N - 1 - yShift`.
- `mCol` ranges from `xShift` to `N - 1`, while `rCol` ranges from `0` to `N - 1 - xShift`.

### ✅ Why This Works

- **Exhaustive Search**: Any valid 2D shift of one matrix over another can be represented by a vertical offset $\Delta y \in [-(N-1), N-1]$ and a horizontal offset $\Delta x \in [-(N-1), N-1]$.
- **Symmetry via Argument Swapping**: Iterating offsets `yShift, xShift` in non-negative ranges $[0, N-1]$ covers positive shifts. Swapping `A` and `B` in `shiftAndCount(xShift, yShift, B, A)` effectively handles negative shifts along the vertical direction.
- **Dual Horizontal Checks**: Inside `shiftAndCount`, calculating both `leftShiftCount` and `rightShiftCount` within the same nested loop covers both positive and negative horizontal shifts for a given vertical offset.
- **Correct Overlap Condition**: Checking `M[...] == 1 && M[...] == R[...]` ensures that positions are only counted when both overlapping cells contain a `1`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^4)$
  - There are $N$ choices for `yShift` and $N$ choices for `xShift`, giving $N^2$ shift pairs.
  - For each pair, `shiftAndCount` is called twice. Inside `shiftAndCount`, the nested loops run $(N - yShift) \times (N - xShift)$ times, performing $\mathcal{O}(1)$ operations per cell.
  - Summing over all shifts: $\sum_{y=0}^{N-1} \sum_{x=0}^{N-1} (N-y)(N-x) = \mathcal{O}(N^4)$.
  - Given $N \le 30$, $N^4 \approx 810,000$ operations, which easily runs within time limits.

- **Space Complexity**: $\mathcal{O}(1)$
  - The algorithm operates entirely in-place by indexing into the original 2D arrays `A` and `B`.
  - Only a few primitive integer counters (`maxOverlaps`, `leftShiftCount`, `rightShiftCount`, `rRow`, `rCol`) are created.

### 🧠 DSA Pattern

- **Brute Force / Matrix 2D Translation**: Iterating over all valid shift configurations $(xShift, yShift)$ and directly counting overlapping elements for each configuration.

### ⚠️ Common Mistakes

1. **Index Out of Bounds**: Incorrectly tracking dual pointers (`mRow`/`rRow` and `mCol`/`rCol`) can lead to array indexing errors if `rRow` or `rCol` exceeds matrix bounds. The code avoids this because the loop bound `mCol < M.length` limits total iterations to $N - xShift$, matching `rCol`'s range.
2. **Missing Negative Shifts**: Forgetting that sliding can occur in all 4 quadrant directions (up-left, up-right, down-left, down-right). This code addresses all 4 directions by combining dual horizontal checks (`leftShiftCount` / `rightShiftCount`) with swapping `A` and `B`.
3. **Double Counting Zero Overlaps**: Checking `M[...] == R[...]` without ensuring `M[...] == 1` would count matching `0`s as valid overlaps. The code correctly includes `M[...] == 1`.

### 🚀 Optimization Notes

- **Current Efficiency**: For $N \le 30$, this solution is straightforward and easily passes within standard time constraints.
- **Sparse Matrix Consideration**: The implementation checks every cell in the overlap range regardless of whether it contains `1` or `0`. If matrices are sparse (contain very few `1`s), storing coordinates of `1`s in lists and comparing offset differences would reduce unnecessary iterations. However, for dense matrices or small $N$, the direct array indexing in this code has low overhead and simple cache access.
