<h2><a href="https://leetcode.com/problems/circle-and-rectangle-overlapping">Circle and Rectangle Overlapping</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a circle represented as <code>(radius, xCenter, yCenter)</code> and an axis-aligned rectangle represented as <code>(x1, y1, x2, y2)</code>, where <code>(x1, y1)</code> are the coordinates of the bottom-left corner, and <code>(x2, y2)</code> are the coordinates of the top-right corner of the rectangle.</p>

<p>Return <code>true</code><em> if the circle and rectangle are overlapped otherwise return </em><code>false</code>. In other words, check if there is <strong>any</strong> point <code>(x<sub>i</sub>, y<sub>i</sub>)</code> that belongs to the circle and the rectangle at the same time.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/02/20/sample_4_1728.png" style="width: 258px; height: 167px;" />
<pre>
<strong>Input:</strong> radius = 1, xCenter = 0, yCenter = 0, x1 = 1, y1 = -1, x2 = 3, y2 = 1
<strong>Output:</strong> true
<strong>Explanation:</strong> Circle and rectangle share the point (1,0).
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> radius = 1, xCenter = 1, yCenter = 1, x1 = 1, y1 = -3, x2 = 2, y2 = -1
<strong>Output:</strong> false
</pre>

<p><strong class="example">Example 3:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/02/20/sample_2_1728.png" style="width: 150px; height: 135px;" />
<pre>
<strong>Input:</strong> radius = 1, xCenter = 0, yCenter = 0, x1 = -1, y1 = 0, x2 = 0, y2 = 1
<strong>Output:</strong> true
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= radius &lt;= 2000</code></li>
	<li><code>-10<sup>4</sup> &lt;= xCenter, yCenter &lt;= 10<sup>4</sup></code></li>
	<li><code>-10<sup>4</sup> &lt;= x1 &lt; x2 &lt;= 10<sup>4</sup></code></li>
	<li><code>-10<sup>4</sup> &lt;= y1 &lt; y2 &lt;= 10<sup>4</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To determine if a circle and an axis-aligned rectangle overlap, you need to find the point inside or on the boundary of the rectangle that is **closest** to the center of the circle. 

If the distance from the circle's center `(xCenter, yCenter)` to this closest point `(closestX, closestY)` is less than or equal to the circle's `radius`, then at least one point is shared between the circle and rectangle, meaning they overlap.

### 🔍 Approach

1. **Find the Closest X-Coordinate (`closestX`)**:
   - Clamp `xCenter` into the range $[x1, x2]$ using `Math.max(x1, Math.min(xCenter, x2))`.
   - If `xCenter` is to the left of the rectangle ($xCenter < x1$), `closestX` becomes $x1$.
   - If `xCenter` is to the right of the rectangle ($xCenter > x2$), `closestX` becomes $x2$.
   - If `xCenter` is within the rectangle's horizontal bounds, `closestX` remains `xCenter`.

2. **Find the Closest Y-Coordinate (`closestY`)**:
   - Clamp `yCenter` into the range $[y1, y2]$ using `Math.max(y1, Math.min(yCenter, y2))`.
   - Similarly, if `yCenter` is below $y1$, `closestY` becomes $y1$; if above $y2$, it becomes $y2$; otherwise, it stays `yCenter`.

3. **Calculate Delta Offsets**:
   - Compute $dx = xCenter - closestX$ and $dy = yCenter - closestY$.

4. **Compare Squared Distance with Squared Radius**:
   - Compute $dx^2 + dy^2$ and check if it is $\le radius^2$.
   - Comparing squared values avoids using floating-point operations like `Math.sqrt()`.

### 🧩 Algorithm

1. $closestX = \max(x1, \min(xCenter, x2))$
2. $closestY = \max(y1, \min(yCenter, y2))$
3. $dx = xCenter - closestX$
4. $dy = yCenter - closestY$
5. **Return** $dx^2 + dy^2 \le radius^2$

### ✅ Why This Works

An axis-aligned rectangle is defined by independent horizontal $[x1, x2]$ and vertical $[y1, y2]$ intervals. Because these dimensions are orthogonal, clamping the circle's center independently along the X and Y axes yields the exact point within the rectangle that minimizes Euclidean distance to `(xCenter, yCenter)`.

If this minimum distance squared is within $radius^2$, the circle covers that closest point, proving an overlap.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(1)$. The algorithm executes a fixed number of basic arithmetic and comparison operations (`Math.max`, `Math.min`, multiplications, additions).
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. Only a few primitive integer variables (`closestX`, `closestY`, `dx`, `dy`) are allocated.

### 🧠 DSA Pattern

- **Math / Geometry**: Clamping coordinates to find the nearest point on an Axis-Aligned Bounding Box (AABB) and using squared Euclidean distance comparison.

### ⚠️ Common Mistakes

1. **Precision Issues with Floating-Point Math**: Using `Math.sqrt(dx * dx + dy * dy) <= radius` introduces precision errors due to floating-point representation. Comparing squared distances directly is exact and safer.
2. **Integer Overflow**: In problems with larger constraints (e.g., $10^9$), calculating $dx^2 + dy^2$ could overflow standard 32-bit signed integers. Given this problem's constraints (coordinates between $-10^4$ and $10^4$), $dx$ is at most $20,000$, so $dx^2 \le 4 \times 10^8$, which fits safely inside Java's 32-bit `int` (up to $\approx 2.14 \times 10^9$).
3. **Incorrect Clamping Logic**: Mixing up `Math.min` and `Math.max` order when restricting values to $[x1, x2]$.

### 🚀 Optimization Notes

- The solution is already fully optimal in both time ($\mathcal{O}(1)$) and space ($\mathcal{O}(1)$).
- Avoiding floating-point arithmetic (`Math.sqrt`) is the primary performance optimization and prevents potential floating-point inaccuracies.
