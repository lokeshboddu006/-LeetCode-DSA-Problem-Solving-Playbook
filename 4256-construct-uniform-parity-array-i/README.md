<h2><a href="https://leetcode.com/problems/construct-uniform-parity-array-i">Construct Uniform Parity Array I</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an array <code>nums1</code> of <code>n</code> <strong>distinct</strong> integers.</p>

<p>You want to construct another array <code>nums2</code> of length <code>n</code> such that the elements in <code>nums2</code> are either <strong>all odd or all even</strong>.</p>

<p>For each index <code>i</code>, you must choose <strong>exactly one</strong> of the following (in any order):</p>

<ul>
	<li><code>nums2[i] = nums1[i]</code></li>
	<li><code>nums2[i] = nums1[i] - nums1[j]</code>, for an index <code>j != i</code></li>
</ul>

<p>Return <code>true</code> if it is possible to construct such an array, otherwise, return <code>false</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums1 = [2,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Choose <code>nums2[0] = nums1[0] - nums1[1] = 2 - 3 = -1</code>.</li>
	<li>Choose <code>nums2[1] = nums1[1] = 3</code>.</li>
	<li><code>nums2 = [-1, 3]</code>, and both elements are odd. Thus, the answer is <code>true</code>​​​​​​​.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums1 = [4,6]</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>

<p><strong>Explanation:</strong>​​​​​​​</p>

<ul>
	<li>Choose <code>nums2[0] = nums1[0] = 4</code>.</li>
	<li>Choose <code>nums2[1] = nums1[1] = 6</code>.</li>
	<li><code>nums2 = [4, 6]</code>, and all elements are even. Thus, the answer is <code>true</code>.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == nums1.length &lt;= 100</code></li>
	<li><code>1 &lt;= nums1[i] &lt;= 100</code></li>
	<li><code>nums1</code> consists of distinct integers.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution is based on a simple parity rule: it is always mathematically guaranteed that a valid uniform parity array can be formed, regardless of the values in `nums1`.

1. **If all numbers in `nums1` are even:** We can simply choose `nums2[i] = nums1[i]` for every index. All numbers remain even, giving us an all-even array.
2. **If `nums1` contains at least one odd number:**
   - Any odd element `nums1[i]` can stay as `nums2[i] = nums1[i]` (which is odd).
   - Any even element `nums1[i]` can be transformed into an odd number by subtracting an odd element `nums1[j]` (`even - odd = odd`).
   - This allows us to make every element in `nums2` odd, giving us an all-odd array.

Since every array of integers either has no odd numbers or has at least one odd number, a uniform parity construction is **always possible**. Therefore, the method simply returns `true` unconditionally.

---

### 🔍 Approach

The user's code directly executes a single statement:

```java
return true;
```

- The code does not iterate over `nums1` or inspect its contents because the answer is invariant across all inputs.
- It bypasses any array allocation or simulation and returns `true` directly in constant time.

---

### 🧩 Algorithm

1. Return `true` unconditionally.

---

### ✅ Why This Works

Let $n$ be the length of `nums1`. We analyze two mutually exclusive cases that cover all possible inputs:

- **Case 1: No odd elements exist in `nums1` (all elements are even)**
  - For every index $i$, pick `nums2[i] = nums1[i]`.
  - Every `nums2[i]` is even.
  - Result: All elements in `nums2` are even (valid).

- **Case 2: At least one odd element exists in `nums1` at index $j$**
  - For each index $i$:
    - If `nums1[i]` is odd, pick `nums2[i] = nums1[i]` (remains odd).
    - If `nums1[i]` is even, pick `nums2[i] = nums1[i] - nums1[j]`. Since $\text{even} - \text{odd} = \text{odd}$, `nums2[i]` becomes odd.
  - Result: All elements in `nums2` are odd (valid).

Because these two cases cover 100% of valid inputs, a solution always exists, making `return true;` fully correct.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(1)$ — The code performs a single constant-time return operation without reading the input array.
- **Space Complexity:** $\mathcal{O}(1)$ — No extra memory or variables are allocated.

---

### 🧠 DSA Pattern

- **Math / Brainteaser**: Recognizing parity properties ($\text{even} - \text{odd} = \text{odd}$) to prove that the answer is always `true` without needing to simulate the array construction.

---

### ⚠️ Common Mistakes

- **Unnecessary Simulation**: Attempting to actually build `nums2` or loop through `nums1` to check elements, which adds overhead and complexity when a mathematical proof guarantees `true` for all inputs.

---

### 🚀 Optimization Notes

- This solution runs in $\mathcal{O}(1)$ time and $\mathcal{O}(1)$ space, which is the theoretical lower bound. It is already optimal.
