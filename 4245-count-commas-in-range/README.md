<h2><a href="https://leetcode.com/problems/count-commas-in-range">Count Commas in Range</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>You are given an integer <code>n</code>.</p>

<p>Return the <strong>total</strong> number of commas used when writing all integers from <code>[1, n]</code> (inclusive) in <strong>standard</strong> number formatting.</p>

<p>In <strong>standard</strong> formatting:</p>

<ul>
	<li>A comma is inserted after <strong>every three</strong> digits from the right.</li>
	<li>Numbers with <strong>fewer</strong> than 4 digits contain no commas.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 1002</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>The numbers <code>&quot;1,000&quot;</code>, <code>&quot;1,001&quot;</code>, and <code>&quot;1,002&quot;</code> each contain one comma, giving a total of 3.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 998</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<p>All numbers from 1 to 998 have fewer than four digits. Therefore, no commas are used.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

In standard number formatting, a comma is inserted every three digits from the right:
- Numbers from $1$ to $999$ have **0** commas.
- Numbers from $1,000$ to $999,999$ have at least **1** comma (the thousands separator).
- Numbers from $1,000,000$ to $999,999,999$ have a **2nd** comma (the millions separator).
- Numbers $1,000,000,000$ and above have a **3rd** comma (the billions separator).

Instead of formatting each number individually or iterating from $1$ to $n$, this solution counts comma contributions cumulatively:
1. Every number $\ge 1,000$ adds **1** comma.
2. Every number $\ge 1,000,000$ adds a **2nd** comma.
3. Every number $\ge 1,000,000,000$ adds a **3rd** comma.

By calculating how many numbers fall into or exceed each threshold, we get the total number of commas directly.

---

### 🔍 Approach

1. **Initialize Counter:**
   - Create a single variable `commas = 0` to store the running sum of commas.

2. **Check $1^{\text{st}}$ Comma Threshold ($\ge 1,000$):**
   - If $n \ge 1000$, all numbers from $1000$ to $n$ have at least 1 comma.
   - The count of such numbers is $n - 999$. We add this to `commas`.

3. **Check $2^{\text{nd}}$ Comma Threshold ($\ge 1,000,000$):**
   - If $n \ge 1000000$, all numbers from $1,000,000$ to $n$ receive a second comma.
   - The count of such numbers is $n - 999999$. We add this to `commas`.

4. **Check $3^{\text{rd}}$ Comma Threshold ($\ge 1,000,000,000$):**
   - If $n \ge 1000000000$, all numbers from $1,000,000,000$ to $n$ receive a third comma.
   - The count of such numbers is $n - 999999999$. We add this to `commas`.

5. **Return Result:**
   - Return `commas`.

---

### 🧩 Algorithm

The total count is given by evaluating cumulative range counts:

$$\text{Total Commas} = \sum_{k \in \{10^3, 10^6, 10^9\}} \max(0, n - (k - 1))$$

---

### ✅ Why This Works

This approach works because comma additions are independent layer-by-layer:
- A number $x$ in $[1000, 999999]$ triggers only the first `if`, contributing $1$ to the total count.
- A number $x$ in $[1000000, 999999999]$ triggers the first two `if` statements, contributing $1 + 1 = 2$ to the total count.
- A number $x \ge 1000000000$ triggers all three `if` statements, contributing $1 + 1 + 1 = 3$ to the total count.

Subtracting $(k - 1)$ instead of $k$ correctly includes the boundary number $k$ itself in the range $[k, n]$.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(1)$
  The code executes a constant number of conditional checks and basic arithmetic operations regardless of the input magnitude $n$.

- **Space Complexity:** $\mathcal{O}(1)$
  Only a single integer variable `commas` is used.

---

### 🧠 DSA Pattern

- **Math / Range Counting:** Breaking down digit/formatting boundaries into threshold ranges to perform $O(1)$ counting instead of simulation.

---

### ⚠️ Common Mistakes

1. **Off-by-One Boundary Errors:**
   Writing `n - 1000` instead of `n - 999`. Since `1000` itself has a comma, the number of integers in $[1000, n]$ is $n - 1000 + 1 = n - 999$.

2. **Simulation / Loop Approach:**
   Iterating from $1$ to $n$ and formatting each integer as a string to count commas. This leads to $\mathcal{O}(n)$ time and would be inefficient or TLE for large inputs.

---

### 🚀 Optimization Notes

- **Problem Constraints vs General Code:**
  The problem constraints state $1 \le n \le 10^5$. Under these specific limits, $n$ will never exceed $999,999$, meaning the conditions `n >= 1000000` and `n >= 1000000000` will never evaluate to `true`.
- **Generalization:**
  Even though those larger checks are technically dead code under $n \le 10^5$, having them makes the implementation robust for any standard 32-bit positive integer up to $2 \times 10^9$.
- The solution is already **time and space optimal** ($\mathcal{O}(1)$ time, $\mathcal{O}(1)$ space).
