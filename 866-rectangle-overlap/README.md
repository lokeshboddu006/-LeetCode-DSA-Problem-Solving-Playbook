<h2><a href="https://leetcode.com/problems/rectangle-overlap">Rectangle Overlap</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>An axis-aligned rectangle is represented as a list <code>[x1, y1, x2, y2]</code>, where <code>(x1, y1)</code> is the coordinate of its bottom-left corner, and <code>(x2, y2)</code> is the coordinate of its top-right corner. Its top and bottom edges are parallel to the X-axis, and its left and right edges are parallel to the Y-axis.</p>

<p>Two rectangles overlap if the area of their intersection is <strong>positive</strong>. To be clear, two rectangles that only touch at the corner or edges do not overlap.</p>

<p>Given two axis-aligned rectangles <code>rec1</code> and <code>rec2</code>, return <code>true</code><em> if they overlap, otherwise return </em><code>false</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> rec1 = [0,0,2,2], rec2 = [1,1,3,3]
<strong>Output:</strong> true
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> rec1 = [0,0,1,1], rec2 = [1,0,2,1]
<strong>Output:</strong> false
</pre><p><strong class="example">Example 3:</strong></p>
<pre><strong>Input:</strong> rec1 = [0,0,1,1], rec2 = [2,2,3,3]
<strong>Output:</strong> false
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>rec1.length == 4</code></li>
	<li><code>rec2.length == 4</code></li>
	<li><code>-10<sup>9</sup> &lt;= rec1[i], rec2[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>rec1</code> and <code>rec2</code> represent a valid rectangle with a non-zero area.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Two axis-aligned rectangles overlap if and only if their projections on both the X-axis and Y-axis overlap with a non-zero length.

Instead of checking complex geometric configurations or checking if one rectangle is completely outside another, this solution directly checks if the 1D interval `[r1[0], r1[2]]` overlaps with `[r2[0], r2[2]]` along the X-axis **AND** the 1D interval `[r1[1], r1[3]]` overlaps with `[r2[1], r2[3]]` along the Y-axis.

### 🔍 Approach

The method `isRectangleOverlap(int[] r1, int[] r2)` takes two arrays representing the coordinates:
- `r1 = [x1_1, y1_1, x2_1, y2_1]`
- `r2 = [x1_2, y1_2, x2_2, y2_2]`

The single `return` statement checks 4 simple boundary conditions connected by logical `AND` (`&&`):

1. **X-axis overlap condition (`r1[0] < r2[2] && r2[0] < r1[2]`):**
   - `r1[0] < r2[2]`: The bottom-left X-coordinate of `r1` is strictly to the left of the top-right X-coordinate of `r2`.
   - `r2[0] < r1[2]`: The bottom-left X-coordinate of `r2` is strictly to the left of the top-right X-coordinate of `r1`.

2. **Y-axis overlap condition (`r1[1] < r2[3] && r2[1] < r1[3]`):**
   - `r1[1] < r2[3]`: The bottom-left Y-coordinate of `r1` is strictly below the top-right Y-coordinate of `r2`.
   - `r2[1] < r1[3]`: The bottom-left Y-coordinate of `r2` is strictly below the top-right Y-coordinate of `r1`.

If all 4 conditions are `true`, the rectangles overlap with a positive area.

### 🧩 Algorithm

1. **Interval Overlap Rule:**
   Two intervals $[a_1, a_2]$ and $[b_1, b_2]$ overlap with non-zero length if and only if:
   $$\max(a_1, b_1) < \min(a_2, b_2)$$
   Which is logically equivalent to:
   $$(a_1 < b_2) \land (b_1 < a_2)$$

2. **2D Condition:**
   - X-overlap: `r1[0] < r2[2] && r2[0] < r1[2]`
   - Y-overlap: `r1[1] < r2[3] && r2[1] < r1[3]`
   - Final Result: `X-overlap && Y-overlap`

### ✅ Why This Works

For two axis-aligned rectangles to share a positive area, they must overlap in both orthogonal dimensions (horizontal and vertical). 

- Using strict inequality (`<`) ensures that if rectangles merely touch along an edge or at a corner point (e.g., `r1[2] == r2[0]`), the statement evaluates to `false`. This correctly enforces the requirement that the intersection area must be **positive**.
- If either the horizontal or vertical projection fails to overlap, the logical `&&` short-circuits to `false`, correctly identifying that no 2D overlap exists.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(1)$. The algorithm performs a constant number of comparisons (4 total) regardless of coordinate values.
- **Space Complexity:** $\mathcal{O}(1)$. No additional data structures or memory are allocated.

### 🧠 DSA Pattern

- **Math / Geometry** (1D Interval Overlap Projection)

### ⚠️ Common Mistakes

1. **Using `<=` instead of `<`:**
   Using `<=` would incorrectly count touching borders or shared corner points as overlapping rectangles, violating the positive area requirement.
2. **Confusing array indices:**
   Swapping X and Y indices (e.g., comparing `r1[0]` with `r2[3]`) is a common indexing bug when dealing with `[x1, y1, x2, y2]` representations.

### 🚀 Optimization Notes

This solution is already optimal in terms of both time ($\mathcal{O}(1)$) and space ($\mathcal{O}(1)$). There are no unnecessary operations or performance bottlenecks to improve.
