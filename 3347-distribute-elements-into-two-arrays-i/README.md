<h2><a href="https://leetcode.com/problems/distribute-elements-into-two-arrays-i">Distribute Elements Into Two Arrays I</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given a <strong>1-indexed</strong> array of <strong>distinct</strong> integers <code>nums</code> of length <code>n</code>.</p>

<p>You need to distribute all the elements of <code>nums</code> between two arrays <code>arr1</code> and <code>arr2</code> using <code>n</code> operations. In the first operation, append <code>nums[1]</code> to <code>arr1</code>. In the second operation, append <code>nums[2]</code> to <code>arr2</code>. Afterwards, in the <code>i<sup>th</sup></code> operation:</p>

<ul>
	<li>If the last element of <code>arr1</code> is<strong> greater</strong> than the last element of <code>arr2</code>, append <code>nums[i]</code> to <code>arr1</code>. Otherwise, append <code>nums[i]</code> to <code>arr2</code>.</li>
</ul>

<p>The array <code>result</code> is formed by concatenating the arrays <code>arr1</code> and <code>arr2</code>. For example, if <code>arr1 == [1,2,3]</code> and <code>arr2 == [4,5,6]</code>, then <code>result = [1,2,3,4,5,6]</code>.</p>

<p>Return <em>the array</em> <code>result</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,1,3]
<strong>Output:</strong> [2,3,1]
<strong>Explanation:</strong> After the first 2 operations, arr1 = [2] and arr2 = [1].
In the 3<sup>rd</sup> operation, as the last element of arr1 is greater than the last element of arr2 (2 &gt; 1), append nums[3] to arr1.
After 3 operations, arr1 = [2,3] and arr2 = [1].
Hence, the array result formed by concatenation is [2,3,1].
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [5,4,3,8]
<strong>Output:</strong> [5,3,4,8]
<strong>Explanation:</strong> After the first 2 operations, arr1 = [5] and arr2 = [4].
In the 3<sup>rd</sup> operation, as the last element of arr1 is greater than the last element of arr2 (5 &gt; 4), append nums[3] to arr1, hence arr1 becomes [5,3].
In the 4<sup>th</sup> operation, as the last element of arr2 is greater than the last element of arr1 (4 &gt; 3), append nums[4] to arr2, hence arr2 becomes [4,8].
After 4 operations, arr1 = [5,3] and arr2 = [4,8].
Hence, the array result formed by concatenation is [5,3,4,8].
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>3 &lt;= n &lt;= 50</code></li>
	<li><code>1 &lt;= nums[i] &lt;= 100</code></li>
	<li>All elements in <code>nums</code> are distinct.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code directly simulates the process specified in the problem rules by maintaining two dynamic collections (lists) that act as the two target arrays.

By initializing list `b` with `a[0]` and list `c` with `a[1]`, the solution establishes the starting state. For each remaining element from index `2` onwards, it inspects the last inserted element in both lists. Based on a direct comparison between these two last elements, the current value `a[i]` is appended to the appropriate list. Finally, both lists are flattened into a single output array sequentially.

### 🔍 Approach

1. **Initialization**:
   - Two dynamic lists, `b` and `c`, are declared using `ArrayList<Integer>`.
   - `b.add(a[0])` places the first element into array 1 (`b`).
   - `c.add(a[1])` places the second element into array 2 (`c`).

2. **Simulation Loop**:
   - A `for` loop iterates from index `i = 2` to `a.length - 1`.
   - In each iteration, `b.get(b.size() - 1)` retrieves the last element added to list `b`, and `c.get(c.size() - 1)` retrieves the last element added to list `c`.
   - **Condition Check**: `if (b.get(b.size() - 1) > c.get(c.size() - 1))`
     - If the last element of `b` is strictly greater than the last element of `c`, `a[i]` is appended to `b`.
     - Otherwise, `a[i]` is appended to `c`.

3. **Concatenation & Result Construction**:
   - An integer array `r` of size `a.length` is allocated to store the final result.
   - Pointer `k` tracks the insertion position in `r`.
   - A enhanced `for` loop iterates through `b` and copies each element into `r`.
   - Another enhanced `for` loop iterates through `c` and appends its elements directly after `b`'s elements in `r`.
   - The array `r` is returned.

### 🧩 Algorithm

1. Input array: `a` of size $n$.
2. Instantiate `b = [a[0]]` and `c = [a[1]]`.
3. For $i$ from $2$ to $n - 1$:
   - Let $\text{last}_b = b[b.\text{size}() - 1]$
   - Let $\text{last}_c = c[c.\text{size}() - 1]$
   - If $\text{last}_b > \text{last}_c$, $b.\text{append}(a[i])$
   - Else, $c.\text{append}(a[i])$
4. Construct array $r$ of size $n$.
5. Copy elements of $b$ followed by elements of $c$ into $r$.
6. Return $r$.

### ✅ Why This Works

- **Correct Initial State**: The problem specifies that the first element goes to the first array and the second element goes to the second array, which corresponds exactly to `b.add(a[0])` and `c.add(a[1])`.
- **Accurate Tail Comparison**: `b.get(b.size() - 1)` and `c.get(c.size() - 1)` always inspect the most recently added elements of `b` and `c` respectively, preserving the rule for element placement.
- **Order Preservation**: Standard list `add` operations preserve insertion order.
- **Proper Concatenation**: Copying all elements of `b` into `r` followed by all elements of `c` directly implements the required array concatenation.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n)$, where $n$ is the length of array `a`.
  - Initializing `b` and `c` takes $\mathcal{O}(1)$ time.
  - The loop runs $n - 2$ times. Inside the loop, `size()`, `get()`, and `add()` on an `ArrayList` run in $\mathcal{O}(1)$ amortized time.
  - The final copy loops traverse every element in `b` and `c` exactly once, taking $\mathcal{O}(n)$ total time.

- **Space Complexity**: $\mathcal{O}(n)$ auxiliary space.
  - List `b` and list `c` together store a total of $n$ elements across the simulation.
  - Array `r` requires $\mathcal{O}(n)$ space to store the returned result.

### 🧠 DSA Pattern

- **Array / Simulation**: The solution directly models the step-by-step rules specified in the problem statement using dynamic linear data structures (`ArrayList`).

### ⚠️ Common Mistakes

1. **Off-by-one errors in index fetching**: Using `b.get(b.size())` instead of `b.get(b.size() - 1)` would cause an `IndexOutOfBoundsException`.
2. **Comparing length instead of last element**: Confusing `b.size() > c.size()` with comparing the actual values at the end of the lists.
3. **Incorrect starting loop index**: Starting the iteration from index `0` or `1` instead of `2` would re-process elements that were already placed into `b` and `c`.

### 🚀 Optimization Notes

- **Autoboxing Overhead**: Using `ArrayList<Integer>` causes primitive `int` values to be boxed into `Integer` objects and unboxed during comparison.
- **Redundant List Access**: Instead of calling `b.get(b.size() - 1)` repeatedly, two primitive integer variables (`lastB` and `lastC`) could track the tail values, eliminating function call overhead.
- **Memory Allocation**: Array arrays of fixed size `n` with pointer indices could be used instead of `ArrayList` to avoid memory reallocations and dynamic resizing.
- Given the problem constraints ($n \le 50$), this linear simulation approach is optimal in asymptotic terms ($\mathcal{O}(n)$ time and space).
