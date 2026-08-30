<h2><a href="https://leetcode.com/problems/removing-minimum-and-maximum-from-array">Removing Minimum and Maximum From Array</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a <strong>0-indexed</strong> array of <strong>distinct</strong> integers <code>nums</code>.</p>

<p>There is an element in <code>nums</code> that has the <strong>lowest</strong> value and an element that has the <strong>highest</strong> value. We call them the <strong>minimum</strong> and <strong>maximum</strong> respectively. Your goal is to remove <strong>both</strong> these elements from the array.</p>

<p>A <strong>deletion</strong> is defined as either removing an element from the <strong>front</strong> of the array or removing an element from the <strong>back</strong> of the array.</p>

<p>Return <em>the <strong>minimum</strong> number of deletions it would take to remove <strong>both</strong> the minimum and maximum element from the array.</em></p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,<u><strong>10</strong></u>,7,5,4,<u><strong>1</strong></u>,8,6]
<strong>Output:</strong> 5
<strong>Explanation:</strong> 
The minimum element in the array is nums[5], which is 1.
The maximum element in the array is nums[1], which is 10.
We can remove both the minimum and maximum by removing 2 elements from the front and 3 elements from the back.
This results in 2 + 3 = 5 deletions, which is the minimum number possible.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [0,<u><strong>-4</strong></u>,<u><strong>19</strong></u>,1,8,-2,-3,5]
<strong>Output:</strong> 3
<strong>Explanation:</strong> 
The minimum element in the array is nums[1], which is -4.
The maximum element in the array is nums[2], which is 19.
We can remove both the minimum and maximum by removing 3 elements from the front.
This results in only 3 deletions, which is the minimum number possible.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> nums = [<u><strong>101</strong></u>]
<strong>Output:</strong> 1
<strong>Explanation:</strong>  
There is only one element in the array, which makes it both the minimum and maximum element.
We can remove it with 1 deletion.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>5</sup> &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
	<li>The integers in <code>nums</code> are <strong>distinct</strong>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To remove both the minimum and maximum elements from the array using only front or back deletions, you first need to locate where both elements reside.

Once you know their positions, there are only 3 possible strategies to remove both elements:
1. **Remove both from the front (left)**: Delete elements from the start until you reach the element that is further away from the front.
2. **Remove both from the back (right)**: Delete elements from the end until you reach the element that is further away from the back.
3. **Split deletions (front and back)**: Delete the element closer to the front from the front, and delete the element closer to the back from the back.

By finding the minimum cost among these 3 choices, you get the optimal number of deletions.

---

### 🔍 Approach

1. **Find Extremum Indices (`l` and `h`)**:
   - Initialize `l = 0` (index of minimum) and `h = 0` (index of maximum).
   - Loop through array `a` with variable `i` from index `0` to `n - 1`.
   - Update `l` whenever a smaller value is found (`a[i] < a[l]`).
   - Update `h` whenever a larger value is found (`a[i] > a[h]`).

2. **Normalize Indices (`i` and `j`)**:
   - Sort the two indices so that `i` is the smaller index and `j` is the larger index:
     - `i = Math.min(l, h)` (the index closer to the start).
     - `j = Math.max(l, h)` (the index closer to the end).
   - *(Note: Variable `i` is reused here after the loop finishes).*

3. **Calculate Options and Return Minimum**:
   - **Both from front**: Deleting up to index `j` from the left takes `j + 1` deletions.
   - **Both from back**: Deleting up to index `i` from the right takes `n - i` deletions.
   - **Split**: Deleting index `i` from the front takes `i + 1` deletions, and deleting index `j` from the back takes `n - j` deletions. Total = `i + 1 + n - j`.
   - Return `Math.min(j + 1, Math.min(n - i, i + 1 + n - j))`.

---

### 🧩 Algorithm

1. Initialize `n = a.length`, `l = 0`, `h = 0`, `i = 0`.
2. For `i` from `0` to `n - 1`:
   - If `a[i] < a[l]`, set `l = i`.
   - If `a[i] > a[h]`, set `h = i`.
3. Set `i = Math.min(l, h)` (first index).
4. Set `j = Math.max(l, h)` (second index).
5. Compute:
   $$\text{cost}_1 = j + 1$$
   $$\text{cost}_2 = n - i$$
   $$\text{cost}_3 = (i + 1) + (n - j)$$
6. Return $\min(\text{cost}_1, \text{cost}_2, \text{cost}_3)$.

---

### ✅ Why This Works

Because deletions are restricted strictly to the front and back of the array:
- Any deletion sequence that removes both elements must end up taking a contiguous prefix from the left, a contiguous suffix from the right, or both.
- Ordering `i` (smaller index) and `j` (larger index) ensures that:
  - `j + 1` covers both indices from the left.
  - `n - i` covers both indices from the right.
  - `(i + 1) + (n - j)` covers `i` from the left and `j` from the right without any overlapping index count.

Evaluating all 3 exhaustive possibilities guarantees finding the global minimum deletions needed.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$
  - A single pass over the array of size $n$ is required to locate the minimum and maximum elements. Ordering indices and computing the minimum of 3 values takes $\mathcal{O}(1)$ time.

- **Space Complexity:** $\mathcal{O}(1)$
  - Only primitive variables (`n`, `l`, `h`, `i`, `j`) are used, requiring constant additional memory.

---

### 🧠 DSA Pattern

- **Greedy / Math**: Evaluating all exhaustive structural options (3 choices) once key locations are determined.
- **Array / Linear Scan**: Single pass to locate min and max indices.

---

### ⚠️ Common Mistakes

1. **Variable Shadowing / Reuse Confusion**:
   - Reusing the loop counter variable `i` for `Math.min(l, h)` works correctly in this code because the loop has completed, but it can make the code harder to read during revision.
2. **Off-by-One Errors**:
   - For a 0-indexed position $x$:
     - Number of deletions from the front is $x + 1$.
     - Number of deletions from the back is $n - x$.
3. **Forgetting to Sort `l` and `h`**:
   - Calculating `i + 1 + n - j` requires that $i \le j$. If you don't take `min(l, h)` and `max(l, h)`, the split option formula would yield incorrect values when the maximum appears before the minimum.

---

### 🚀 Optimization Notes

- This solution is already optimal in terms of **Time Complexity ($\mathcal{O}(n)$)** and **Space Complexity ($\mathcal{O}(1)$)**.
- **Readability Improvement**: Instead of reusing `i` both as a loop index and as the smaller index variable, declaring `i` inside the loop `for (int k = 0; k < n; k++)` and using clear variable names like `first` and `second` would improve clarity without any overhead.
