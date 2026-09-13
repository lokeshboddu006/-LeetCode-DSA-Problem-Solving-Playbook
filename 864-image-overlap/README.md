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
Instead of testing all possible translation offsets across the entire $N \times N$ matrix and recalculating overlaps from scratch, this solution flips the perspective:

1. Collect the coordinates of all cells containing `1` in matrix `a` and matrix `b`.
2. For every pair of `1`s (one from `a` and one from `b`), calculate the displacement vector required to align them.
3. If multiple pairs of `1`s require the **exact same displacement vector**, shifting the image by that vector will align all those pairs simultaneously.
4. Count the frequency of each displacement vector using a hash map. The highest frequency corresponds to the maximum possible overlap.

### 🔍 Approach

1. **Coordinate Flattening & Encoding**:
   - The solution uses a single loop running from `0` to `n * n - 1` to scan both matrices `a` and `b`.
   - For a given index `i`, row is `i / n` and column is `i % n`.
   - Each 2D coordinate $(r, c)$ is encoded into a single integer using the formula:  
     $$\text{encoded} = r \times 100 + c$$
   - Encoded positions of `1`s in matrix `a` are stored in list `x`.
   - Encoded positions of `1`s in matrix `b` are stored in list `y`.

2. **Displacement Frequency Count**:
   - Nested loops iterate over every point `i` in `x` and every point `j` in `y`.
   - The difference `d = i - j` represents the encoded relative shift between the two points.
   - A `HashMap<Integer, Integer>` named `c` keeps track of how many times each difference `d` occurs.
   - Variable `m` maintains the maximum frequency observed so far.

3. **Result**:
   - Returns `m`, which is the maximum number of overlapping `1`s achievable with a single translation.

### 🧩 Algorithm

1. Initialize `x = []`, `y = []`, `c = Map()`, and `m = 0`.
2. For `i` from `0` to `n * n - 1`:
   - If `a[i / n][i % n] == 1`, add `(i / n) * 100 + (i % n)` to `x`.
   - If `b[i / n][i % n] == 1`, add `(i / n) * 100 + (i % n)` to `y`.
3. For each `i` in `x`:
   - For each `j` in `y`:
     - Set `d = i - j`.
     - Update `c[d] = c.getOrDefault(d, 0) + 1`.
     - Update `m = max(m, c[d])`.
4. Return `m`.

### ✅ Why This Works

- **Encoding Validity**:  
  Given $N \le 30$, both row $r$ and column $c$ range from $0$ to $29$.  
  The subtraction `d = i - j` evaluates to:  
  $$d = 100 \times (r_a - r_b) + (c_a - c_b)$$  
  Since $c_a - c_b \in [-29, 29]$, its magnitude is strictly less than $100$. This prevents column differences from interfering with the row difference factor ($100$). Thus, every distinct translation vector $(r_a - r_b, c_a - c_b)$ produces a unique difference integer `d`.

- **Correctness of Overlap Count**:  
  Each unique difference `d` represents a specific relative translation between image `a` and image `b`. Counting how many `(i, j)` pairs yield the same `d` directly counts how many `1` bits will overlap when that specific translation is applied.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N^2 + |x| \cdot |y|)$
  - Scanning both matrices takes $\mathcal{O}(N^2)$ time.
  - The double loop runs $|x| \times |y|$ times, where $|x|$ and $|y|$ are the number of `1`s in matrices `a` and `b`, respectively.
  - Worst-case occurs when all matrix entries are `1` ($|x| = |y| = N^2$), making the nested loop run $N^4$ times. Given $N \le 30$, $N^4 = 810,000$ operations, which easily runs within time limits.

- **Space Complexity**: $\mathcal{O}(N^2)$
  - Storing lists `x` and `y` takes up to $\mathcal{O}(N^2)$ space each.
  - The hash map `c` stores at most $\mathcal{O}(|x| \cdot |y|)$ entries, bounded by the number of possible unique shift vectors (at most $(2N - 1)^2$).

### 🧠 DSA Pattern

- **Hashing / Coordinate Encoding**: Encodes 2D coordinates into 1D integers and counts pairwise displacement frequency using a Hash Map.
- **Sparse Representation**: Operates directly on the coordinates of `1`s rather than checking all matrix cells for every shift.

### ⚠️ Common Mistakes

1. **Choosing a Multiplier Too Small**:  
   Using a multiplier less than $60$ (such as $N$ or $30$) would cause coordinate overlap because $c_a - c_b$ can be negative (down to $-29$). A multiplier of $100$ is sufficient for $N \le 30$.
2. **Forgetting Integer Division Rules**:  
   Using `i / n` and `i % n` inside a single loop requires careful handling of integer division to correctly compute 2D matrix indices.

### 🚀 Optimization Notes

- **Autoboxing and Object Overhead**:  
  Using `ArrayList<Integer>` and `HashMap<Integer, Integer>` introduces Java autoboxing overhead.
- **Array-Based Frequency Table**:  
  Since $r_a - r_b \in [-29, 29]$ and $c_a - c_b \in [-29, 29]$, the total number of distinct translation vectors is small ($59 \times 59 = 3481$). A 2D primitive integer array `int[60][60]` offset by $+30$ could replace `HashMap` entirely, drastically speeding up execution and eliminating garbage collection pressure.
