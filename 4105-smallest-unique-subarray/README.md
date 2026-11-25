<h2><a href="https://leetcode.com/problems/smallest-unique-subarray">Smallest Unique Subarray</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given an integer array <code>nums</code>.</p>

<p>Find the <strong>minimum </strong>length of a <span data-keyword="subarray">subarray</span> that is <strong>not</strong> <strong>identical</strong> to any other <strong>subarray</strong> in <code>nums</code>.</p>

<p>Return an integer denoting the <strong>minimum possible length</strong> of such a <strong>subarray</strong>.</p>

<p>Two <strong>subarrays</strong> are considered identical if they have the same length and the same elements in corresponding positions.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [3,3,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Subarrays of length 1: <code>[3]</code> &rarr; appears 3 times</li>
	<li>Subarrays of length 2: <code>[3, 3]</code> &rarr; appears 2 times</li>
	<li>Subarrays of length 3: <code>[3, 3, 3]</code> &rarr; appears once</li>
</ul>

<p>The subarray <code>[3, 3, 3]</code> is unique, so the smallest unique subarray length is 3.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [2,1,2,3,3]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong></p>

<p>Subarrays of length 1:</p>

<ul>
	<li><code>[2]</code> &rarr; appears 2 times</li>
	<li><code>[1]</code> &rarr; appears once</li>
	<li><code>[3]</code> &rarr; appears 2 times</li>
</ul>
The subarray <code>[1]</code> is unique, so the smallest unique subarray length is 1.</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">nums = [1,1,2,2,1]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>Subarrays of length 1:</p>

<ul>
	<li><code>[1]</code> &rarr; appears 3 times</li>
	<li><code>[2]</code> &rarr; appears 2 times</li>
</ul>

<p>Subarrays of length 2:</p>

<ul>
	<li><code>[1, 1]</code> &rarr; appears once</li>
	<li><code>[1, 2]</code> &rarr; appears once</li>
	<li><code>[2, 2]</code> &rarr; appears once</li>
	<li><code>[2, 1]</code> &rarr; appears once</li>
</ul>
There is at least one subarray of length 2 that is unique, so the smallest unique subarray length is 2.</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition
The problem asks for the minimum length of a subarray that occurs exactly once in `nums`.

The solution relies on a key observation: **Monotonicity**.
If there exists a unique subarray of length $L$, then extending that subarray by adding adjacent elements to length $L+1$ will also result in a unique subarray (because if the longer subarray occurred elsewhere, the smaller subarray inside it would also have occurred elsewhere). 

Because of this property, we can **Binary Search on the Answer** (the length of the subarray). For a candidate length `mid`, we check if *at least one* unique subarray of length `mid` exists:
- If a unique subarray exists at length `mid`, `mid` is a potential answer, so we record it and search for a smaller length (`high = mid - 1`).
- If all subarrays of length `mid` appear 2 or more times, no unique subarray of length `mid` exists, so we must search for a larger length (`low = mid + 1`).

To quickly check uniqueness for all subarrays of a given length, the code uses a **Double Polynomial Rolling Hash (Rabin-Karp)** with a frequency table (`HashMap`).

---

### 🔍 Approach

1. **Binary Search Outer Loop (`smallestUniqueSubarray`)**:
   - `low = 1`, `high = n`, and `ans = n`.
   - Computes `mid = low + (high - low) / 2`.
   - Calls `check(nums, mid)`:
     - If `check(nums, mid)` returns `true` (meaning *no* unique subarray exists of length `mid`), move `low = mid + 1`.
     - If `check(nums, mid)` returns `false` (meaning *at least one* unique subarray exists of length `mid`), record `ans = mid` and move `high = mid - 1`.

2. **Subarray Frequency Check (`check`)**:
   - Initializes two rolling hashes using two pairs of (prime base, modulo):
     - `prime1 = 31`, `mod1 = 10^9 + 7`
     - `prime2 = 37`, `mod2 = 10^9 + 9`
   - **First Window**: Calculates `hash1` and `hash2` for the prefix `nums[0 ... len-1]`.
   - **Power Precomputation**: Computes $power1 = 31^{len-1} \pmod{10^9+7}$ and $power2 = 37^{len-1} \pmod{10^9+9}$ to easily remove the outgoing element when sliding the window.
   - **HashMap Entry**: Combines `hash1` and `hash2` into a string key `"hash1-hash2"` and inserts it into `hm`.
   - **Sliding Window**: Loops from `i = len` to `n - 1`:
     - Subtracts the contribution of `nums[i - len]` from the hashes (adjusting for negative modulo values).
     - Shifts the hash by multiplying by the prime base and adds the new element `nums[i]`.
     - Updates the frequency count of `"hash1-hash2"` in `hm`.
   - **Uniqueness Check**:
     - Iterates through the frequency values of `hm`.
     - If any frequency is `1`, a unique subarray of length `len` exists, so it immediately returns `false`.
     - If all frequencies are $> 1$, no unique subarray of length `len` exists, so it returns `true`.

---

### 🧩 Algorithm

1. **Binary Search**: Standard binary search over the range of possible subarray lengths $[1, n]$.
2. **Double Polynomial Rolling Hash**:
   - For a window $nums[i \dots i + len - 1]$:
     $$\text{hash}_1 = \left( \sum_{k=0}^{len-1} nums[i + k] \cdot 31^{len - 1 - k} \right) \bmod (10^9 + 7)$$
     $$\text{hash}_2 = \left( \sum_{k=0}^{len-1} nums[i + k] \cdot 37^{len - 1 - k} \right) \bmod (10^9 + 9)$$
   - Sliding transition to drop `nums[i - len]` and add `nums[i]`:
     $$\text{hash} \leftarrow ((\text{hash} - \text{nums}[i - len] \cdot \text{power}) \bmod \text{mod} + \text{mod}) \bmod \text{mod}$$
     $$\text{hash} \leftarrow (\text{hash} \cdot \text{prime} + \text{nums}[i]) \bmod \text{mod}$$

---

### ✅ Why This Works

- **Monotonic Property**: If length $L$ contains a unique subarray, length $L+1$ must also contain at least one unique subarray. Thus, binary search correctly identifies the boundary where uniqueness first appears.
- **Rabin-Karp Rolling Hash**: Recomputing hashes from scratch for every subarray would take $O(n \cdot len)$ time. Rolling hash updates each window shift in $O(1)$ time.
- **Double Hashing**: Using two independent modulos ($10^9+7$ and $10^9+9$) and two bases ($31$ and $37$) makes the probability of a hash collision negligible without needing expensive element-by-element array comparisons.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(n \log n)$
  - Binary search performs $\mathcal{O}(\log n)$ iterations.
  - In each iteration, `check()` slides a window across $n$ elements in $\mathcal{O}(n)$ time.
  - Overall time complexity is $\mathcal{O}(n \log n)$.

- **Space Complexity**: $\mathcal{O}(n)$
  - In `check()`, the `HashMap` stores at most $n - len + 1 \le n$ string keys and frequency counts.

---

### 🧠 DSA Pattern

- **Binary Search on Answer** (Searching for the minimum valid length)
- **Rolling Hash / Rabin-Karp Algorithm**
- **Sliding Window**
- **Hashing / Frequency Map**

---

### ⚠️ Common Mistakes

1. **Base Primes vs Element Magnitude**:
   - The code uses prime bases `31` and `37`, but `nums[i]` can be up to $10^5$. In standard polynomial hashing, the base should ideally be strictly greater than any individual element value to avoid structural collisions (e.g., base $> 10^5$). However, using double hashing significantly lowers collision risk in practice.
2. **Modulo Underflow Handling**:
   - When subtracting `nums[i-len] * power1`, the result can be negative. The code correctly handles this by adding `mod1` before taking the second modulo operation: `(((hash1 - ...) % mod1 + mod1) % mod1)`.
3. **Inverted Boolean Semantics**:
   - Notice that `check` returns `false` when a unique subarray is found, and `true` when all subarrays are duplicates. Misinterpreting this return value during binary search updates (`low` vs `high`) would lead to wrong results.

---

### 🚀 Optimization Notes

- **String Key Overhead**: Creating string keys `hash1 + "-" + hash2` creates $O(n)$ new `String` objects per binary search iteration. This introduces heavy Garbage Collection overhead in Java.
- **Bitwise Hash Packing**: Since `hash1` and `hash2` fit within standard 32-bit signed integers, both can be combined into a single 64-bit `long` key:
  ```java
  long combinedKey = ((long) hash1 << 32) | (hash2 & 0xFFFFFFFFL);
  ```
  Using `HashMap<Long, Integer>` avoids String allocations entirely and accelerates map operations.
