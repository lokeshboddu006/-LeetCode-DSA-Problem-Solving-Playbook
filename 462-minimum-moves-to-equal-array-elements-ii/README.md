<h2><a href="https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii">Minimum Moves to Equal Array Elements II</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given an integer array <code>nums</code> of size <code>n</code>, return <em>the minimum number of moves required to make all array elements equal</em>.</p>

<p>In one move, you can increment or decrement an element of the array by <code>1</code>.</p>

<p>Test cases are designed so that the answer will fit in a <strong>32-bit</strong> integer.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,2,3]
<strong>Output:</strong> 2
<strong>Explanation:</strong>
Only two moves are needed (remember each move increments or decrements one element):
[<u>1</u>,2,3]  =&gt;  [2,2,<u>3</u>]  =&gt;  [2,2,2]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [1,10,2,9]
<strong>Output:</strong> 16
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>n == nums.length</code></li>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>9</sup> &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the minimum total number of increments/decrements needed to make all numbers in `nums` equal to some target value.

Mathematically, the point that minimizes the sum of absolute differences to a set of numbers $\sum |nums[i] - x|$ is the **median** of the array. By picking the median as the target element, the total distance traveled from all other elements to this target is minimized. 

Sorting the array allows us to quickly locate the median element at index `nums.length / 2`.

### 🔍 Approach

1. **Sort the Input Array**:
   - The code calls `Arrays.sort(nums)` to place all elements in non-decreasing order.

2. **Select the Median**:
   - `int med = nums[nums.length / 2];` picks the element at the middle index of the sorted array as the target value.

3. **Accumulate Total Moves**:
   - The code initializes a `long mo = 0;` to store the total moves safely without overflowing standard 32-bit integer limits during accumulation.
   - It iterates through each `num` in `nums`, computing `Math.abs(num - med)` and adding it to `mo`.

4. **Return Result**:
   - Finally, `(int) mo` casts the accumulator back to an integer and returns the total moves required.

### 🧩 Algorithm

- **Median Selection & Distance Summing**:
  - **Sorting step**: `nums` is sorted such that $\text{nums}[0] \le \text{nums}[1] \le \dots \le \text{nums}[n-1]$.
  - **Target choice**: $M = \text{nums}[\lfloor n / 2 \rfloor]$.
  - **Sum formula**: 
    $$\text{Total Moves} = \sum_{i=0}^{n-1} |\text{nums}[i] - M|$$

### ✅ Why This Works

- **Median Minimization Property**: For any pair of numbers $(a, b)$ with $a \le b$, picking any target $x$ in the range $[a, b]$ yields the minimum distance $|a - x| + |b - x| = b - a$. 
- If $n$ is odd, the median `nums[n / 2]` is the center element, balancing equal numbers of elements on both sides.
- If $n$ is even, any target value $x$ between `nums[n / 2 - 1]` and `nums[n / 2]` produces the exact same optimal sum of distances. Selecting `nums[n / 2]` is a valid choice in that range.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n \log n)$
  - `Arrays.sort(nums)` takes $\mathcal{O}(n \log n)$ time for primitive integers using Dual-Pivot Quicksort.
  - The subsequent linear loop takes $\mathcal{O}(n)$ time.
  - Overall time complexity is dominated by sorting: $\mathcal{O}(n \log n)$.

- **Space Complexity**: $\mathcal{O}(1)$ or $\mathcal{O}(\log n)$
  - The algorithm uses a few scalar variables (`med`, `mo`, `num`).
  - Depending on the Dual-Pivot Quicksort implementation in Java, array sorting uses $\mathcal{O}(\log n)$ stack space for recursion.

### 🧠 DSA Pattern

- **Math / Greedy** (Median minimization property)
- **Sorting**

### ⚠️ Common Mistakes

1. **Using Mean (Average) instead of Median**:
   - A common misconception is targeting the mean of the elements. The mean minimizes squared differences ($\sum (nums[i] - x)^2$), but the median minimizes absolute differences ($\sum |nums[i] - x|$).

2. **Integer Overflow during Summation**:
   - With up to $10^5$ elements and differences up to $2 \times 10^9$, the total sum can exceed `Integer.MAX_VALUE`. The code correctly uses a `long mo` variable to prevent intermediate overflow before casting back at the end.

3. **Overthinking Even vs. Odd Lengths**:
   - Trying to average the two middle elements when `nums.length` is even is unnecessary. Any integer value between the two central numbers gives the exact same minimal sum of absolute distances.

### 🚀 Optimization Notes

- **Quickselect Optimization**: Instead of fully sorting the array in $\mathcal{O}(n \log n)$, the median can be found in average $\mathcal{O}(n)$ time using the Quickselect algorithm (or finding the $n/2$-th smallest element). However, for $N = 10^5$, standard sorting is already very fast in Java.
- **Two-Pointer Alternative**: After sorting, moves can also be accumulated by pairing `nums[right] - nums[left]` using two pointers moving inwards, avoiding explicit extraction of the median. The submitted approach achieves the exact same mathematical result cleanly.
