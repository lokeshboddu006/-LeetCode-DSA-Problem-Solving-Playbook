<h2><a href="https://leetcode.com/problems/unique-3-digit-even-numbers">Unique 3-Digit Even Numbers</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an array of digits called <code>digits</code>. Your task is to determine the number of <strong>distinct</strong> three-digit even numbers that can be formed using these digits.</p>

<p><strong>Note</strong>: Each <em>copy</em> of a digit can only be used <strong>once per number</strong>, and there may <strong>not</strong> be leading zeros.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [1,2,3,4]</span></p>

<p><strong>Output:</strong> <span class="example-io">12</span></p>

<p><strong>Explanation:</strong> The 12 distinct 3-digit even numbers that can be formed are 124, 132, 134, 142, 214, 234, 312, 314, 324, 342, 412, and 432. Note that 222 cannot be formed because there is only 1 copy of the digit 2.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [0,2,2]</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong> The only 3-digit even numbers that can be formed are 202 and 220. Note that the digit 2 can be used twice because it appears twice in the array.</p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [6,6,6]</span></p>

<p><strong>Output:</strong> <span class="example-io">1</span></p>

<p><strong>Explanation:</strong> Only 666 can be formed.</p>
</div>

<p><strong class="example">Example 4:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">digits = [1,3,5]</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong> No even 3-digit numbers can be formed.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>3 &lt;= digits.length &lt;= 10</code></li>
	<li><code>0 &lt;= digits[i] &lt;= 9</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Instead of generating permutations from the input array `d` (which would require deduplication and complex handling of duplicate digits), your solution iterates over the **answer space** of all potential 3-digit even numbers. 

Since any valid 3-digit even number must fall in the range `[100, 998]` and end in an even digit, there are only 450 possible candidates. For each candidate number, you check if the input array `d` contains enough copies of each digit required to construct it.

### 🔍 Approach

1. **Frequency Array Initialization**:
   - You create a frequency array `c` of size 10 to store the count of each digit (`0` through `9`) in `d`.
   - Iterate through `d` and populate `c`.

2. **Iterate All Candidate 3-Digit Even Numbers**:
   - Loop `i` from `100` to `998` with a step size of `2` (`i += 2`). This guarantees every evaluated number is a 3-digit even integer with no leading zeros.

3. **Digit Extraction & Availability Check**:
   - For each candidate `i`, extract its digits:
     - `x = i / 100` (hundreds digit)
     - `y = (i / 10) % 10` (tens digit)
     - `z = i % 10` (units digit)
   - Decrement the available counts for `x`, `y`, and `z` in `c`.
   - If all three counts remain non-negative (`c[x] >= 0 && c[y] >= 0 && c[z] >= 0`), then the input array `d` possesses enough copies of each required digit to form `i`. Increment valid count `n`.

4. **State Restoration (Backtracking)**:
   - Restore the frequencies by incrementing `c[x]`, `c[y]`, and `c[z]` back to their original state before evaluating the next candidate.

5. **Return Result**:
   - Return the total valid count `n`.

### 🧩 Algorithm

1. **Build Count Map**:
   For each digit `x` in `d`:
   $$c[x] \leftarrow c[x] + 1$$

2. **Search Candidate Space**:
   For $i \in [100, 998]$ where $i \equiv 0 \pmod 2$:
   - $x \leftarrow \lfloor i / 100 \rfloor$
   - $y \leftarrow \lfloor i / 10 \rfloor \bmod 10$
   - $z \leftarrow i \bmod 10$
   - Decrement: $c[x] \leftarrow c[x] - 1, \; c[y] \leftarrow c[y] - 1, \; c[z] \leftarrow c[z] - 1$
   - Condition: If $c[x] \ge 0 \land c[y] \ge 0 \land c[z] \ge 0$, then $n \leftarrow n + 1$
   - Increment (Restore): $c[x] \leftarrow c[x] + 1, \; c[y] \leftarrow c[y] + 1, \; c[z] \leftarrow c[z] + 1$

### ✅ Why This Works

- **Uniqueness**: Iterating directly through unique integers ($100, 102, 104, \dots$) ensures each 3-digit even number is tested and counted at most once.
- **Constraints Handling**: Leading zeros are impossible because $i \ge 100$. Odd numbers are skipped because the loop increments by 2.
- **Duplicate Digits Handling**: Decrementing frequencies handles repeated digits correctly (e.g., if $i = 220$, $c[2]$ is decremented twice; if $c[2] \ge 0$ after both decrements, it proves `d` has at least two `2`s).

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(|d| + K)$, where $|d|$ is the length of `d` and $K = 450$ is the constant number of 3-digit even numbers. Since $|d| \le 10$, the execution takes **$\mathcal{O}(1)$** (constant) time.
- **Space Complexity**: **$\mathcal{O}(1)$** auxiliary space, because array `c` is fixed to size 10 regardless of the input length.

### 🧠 DSA Pattern

- **Frequency Array / Hashing**
- **Search Space Enumeration (Brute Force on Answer)**

### ⚠️ Common Mistakes

- **Incorrect Handling of Duplicate Digits**: Checking `c[x] > 0 && c[y] > 0 && c[z] > 0` directly without decrementing first would fail for numbers like `220` when there is only one `2` available in array `d`. Decrementing first correctly checks whether sufficient copies exist.
- **Forgetting to Restore State**: Missing the re-increment step (`c[x]++; c[y]++; c[z]++;`) would permanently mutate the frequency array and distort checks for subsequent loop iterations.

### 🚀 Optimization Notes

- This approach is already optimal.
- Checking candidates directly avoids generating permutations, sorting, or maintaining a set for uniqueness.
