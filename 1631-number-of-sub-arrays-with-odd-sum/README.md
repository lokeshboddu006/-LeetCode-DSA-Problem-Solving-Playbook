<h2><a href="https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum">Number of Sub-arrays With Odd Sum</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given an array of integers <code>arr</code>, return <em>the number of subarrays with an <strong>odd</strong> sum</em>.</p>

<p>Since the answer can be very large, return it modulo <code>10<sup>9</sup> + 7</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> arr = [1,3,5]
<strong>Output:</strong> 4
<strong>Explanation:</strong> All subarrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
All sub-arrays sum are [1,4,9,3,8,5].
Odd sums are [1,9,3,5] so the answer is 4.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> arr = [2,4,6]
<strong>Output:</strong> 0
<strong>Explanation:</strong> All subarrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
All sub-arrays sum are [2,6,12,4,10,6].
All sub-arrays have even sum and the answer is 0.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> arr = [1,2,3,4,5,6,7]
<strong>Output:</strong> 16
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= arr.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= arr[i] &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The sum of a subarray from index `i` to `j` can be represented using prefix sums as `prefixSum[j] - prefixSum[i - 1]`. For this subarray sum to be **odd**, one prefix sum must be **odd** and the other must be **even** (since $\text{odd} - \text{even} = \text{odd}$ and $\text{even} - \text{odd} = \text{odd}$).

Instead of counting valid subarrays step-by-step during iteration, your code takes a combinatorial approach:
1. Count how many non-empty prefix sums $P_1, P_2, \dots, P_n$ are **odd**.
2. Notice that the prefix sum before taking any elements ($P_0$) is `0`, which is always **even**.
3. Every pair of (1 odd prefix sum, 1 even prefix sum) uniquely defines a subarray with an odd sum.

Therefore:
$$\text{Total Odd Subarrays} = (\text{Total Odd Prefix Sums}) \times (\text{Total Even Prefix Sums})$$

If there are `oddCount` odd prefix sums among $P_1 \dots P_n$, then:
- Odd prefix sums count = `oddCount`
- Even prefix sums count (including $P_0 = 0$) = `(arr.length - oddCount) + 1`

Multiplying these gives:
$$\text{oddCount} \times ((\text{arr.length} - \text{oddCount}) + 1) = (\text{arr.length} - \text{oddCount}) \times \text{oddCount} + \text{oddCount}$$

Your code computes `(arr.length - oddCount) * oddCount` and adds it back into `oddCount`, which computes this exact combinatorial product in $O(1)$ post-processing after a single loop!

---

### 🔍 Approach

1. **Initialization**:
   - `prefixSum`: Accumulates the running sum of elements.
   - `oddCount`: Counts how many prefix sums $P_1, \dots, P_n$ are odd.

2. **Single Pass Loop (`for(int a : arr)`)**:
   - `prefixSum += a;`: Updates the running total.
   - `oddCount += prefixSum % 2;`: Adds `1` if `prefixSum` is odd, and `0` if it is even. Because elements are positive ($1 \le arr[i] \le 100$), `prefixSum % 2` is guaranteed to be either `0` or `1`.

3. **Combinatorial Post-Processing**:
   - At the end of the loop, `oddCount` holds the total count of odd prefix sums among $P_1 \dots P_n$.
   - `arr.length - oddCount` represents the count of even prefix sums among $P_1 \dots P_n$.
   - `oddCount += (arr.length - oddCount) * oddCount;` computes the number of odd-sum subarrays by pairing every odd prefix sum with every even prefix sum (including $P_0 = 0$).

4. **Modulo Reduction**:
   - Applies `% 1_000_000_007` to `oddCount` and casts it to an `int` before returning.

---

### 🧩 Algorithm

1. Set `oddCount = 0` and `prefixSum = 0`.
2. For each element $a$ in `arr`:
   - $prefixSum \leftarrow prefixSum + a$
   - $oddCount \leftarrow oddCount + (prefixSum \pmod 2)$
3. $oddCount \leftarrow oddCount + (N - oddCount) \times oddCount$, where $N = \text{arr.length}$.
4. Return $(oddCount \pmod{10^9 + 7})$ cast to `int`.

---

### ✅ Why This Works

- Every subarray sum ending at index $j$ starting after index $i-1$ is $P_j - P_{i-1}$.
- A difference $P_j - P_{i-1}$ is odd if and only if $P_j$ and $P_{i-1}$ have opposite parity (one odd, one even).
- By counting the total number of odd prefix sums $O$ and total number of even prefix sums $E$ across all indices from $0$ to $N$ (where index $0$ represents $P_0 = 0$), any choice of one odd prefix sum and one even prefix sum forms a valid subarray with an odd sum.
- Since $O = \text{oddCount}$ and $E = (N - \text{oddCount}) + 1$, the total number of valid pairs is $O \times E = \text{oddCount} \times (N - \text{oddCount} + 1)$, which your code correctly calculates.

---

### ⏱️ Complexity

- **Time Complexity**: $O(N)$, where $N$ is `arr.length`. The code scans the array once in a single `for-each` loop and performs a constant number of arithmetic operations afterwards.
- **Space Complexity**: $O(1)$ auxiliary space. Only two primitive `long` variables (`oddCount` and `prefixSum`) are maintained.

---

### 🧠 DSA Pattern

- **Prefix Sum**
- **Parity / Math / Combinatorics**

---

### ⚠️ Common Mistakes

1. **Integer Overflow During Multiplication**:
   - If `arr.length` is up to $10^5$, `(arr.length - oddCount) * oddCount` can reach up to $(5 \times 10^4)^2 = 2.5 \times 10^9$.
   - This value exceeds 32-bit signed integer capacity (`Integer.MAX_VALUE` $\approx 2.14 \times 10^9$).
   - Your solution avoids this overflow by declaring `long oddCount`, ensuring the product is evaluated using 64-bit precision before taking modulo $10^9 + 7$.

2. **Negative Modulo Assumption**:
   - In Java, `-3 % 2` equals `-1`. If `arr` contained negative numbers, `prefixSum % 2` could produce `-1`. However, given the problem constraint $1 \le arr[i] \le 100$, `prefixSum` is always non-negative, so `prefixSum % 2` safely returns `0` or `1`.

---

### 🚀 Optimization Notes

- The solution is already optimal in terms of both time complexity ($O(N)$) and space complexity ($O(1)$).
- Moving the combinatorial multiplication outside the loop avoids doing repeated additions/modulo operations inside the loop, making the constant factor very small.
