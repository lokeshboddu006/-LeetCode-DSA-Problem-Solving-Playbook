<h2><a href="https://leetcode.com/problems/count-days-spent-together">Count Days Spent Together</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Alice and Bob are traveling to Rome for separate business meetings.</p>

<p>You are given 4 strings <code>arriveAlice</code>, <code>leaveAlice</code>, <code>arriveBob</code>, and <code>leaveBob</code>. Alice will be in the city from the dates <code>arriveAlice</code> to <code>leaveAlice</code> (<strong>inclusive</strong>), while Bob will be in the city from the dates <code>arriveBob</code> to <code>leaveBob</code> (<strong>inclusive</strong>). Each will be a 5-character string in the format <code>&quot;MM-DD&quot;</code>, corresponding to the month and day of the date.</p>

<p>Return<em> the total number of days that Alice and Bob are in Rome together.</em></p>

<p>You can assume that all dates occur in the <strong>same</strong> calendar year, which is <strong>not</strong> a leap year. Note that the number of days per month can be represented as: <code>[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> arriveAlice = &quot;08-15&quot;, leaveAlice = &quot;08-18&quot;, arriveBob = &quot;08-16&quot;, leaveBob = &quot;08-19&quot;
<strong>Output:</strong> 3
<strong>Explanation:</strong> Alice will be in Rome from August 15 to August 18. Bob will be in Rome from August 16 to August 19. They are both in Rome together on August 16th, 17th, and 18th, so the answer is 3.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> arriveAlice = &quot;10-01&quot;, leaveAlice = &quot;10-31&quot;, arriveBob = &quot;11-01&quot;, leaveBob = &quot;12-31&quot;
<strong>Output:</strong> 0
<strong>Explanation:</strong> There is no day when Alice and Bob are in Rome together, so we return 0.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>All dates are provided in the format <code>&quot;MM-DD&quot;</code>.</li>
	<li>Alice and Bob&#39;s arrival dates are <strong>earlier than or equal to</strong> their leaving dates.</li>
	<li>The given dates are valid dates of a <strong>non-leap</strong> year.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation behind your solution is converting complex date strings (`"MM-DD"`) into absolute 1D integer offsets (day of the year, from day 1 to day 365). 

Once both Alice's date range `[arriveAlice, leaveAlice]` and Bob's date range `[arriveBob, leaveBob]` are mapped to standard 1D integer intervals `[as, aed]` and `[bst, bed]`, the problem reduces to finding the overlap of two intervals on a line. The overlap start is the maximum of the two start days, and the overlap end is the minimum of the two end days.

---

### 🔍 Approach

1. **Days in Month Mapping**:
   - You define a fixed array `days` containing the number of days in each of the 12 months for a non-leap year (`[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]`).

2. **Date to Day-of-Year Helper (`toDay`)**:
   - Parse month `m` from characters at indices `0` and `1` using `(s.charAt(0) - '0') * 10 + (s.charAt(1) - '0')`.
   - Parse day `d` from characters at indices `3` and `4` using `(s.charAt(3) - '0') * 10 + (s.charAt(4) - '0')`.
   - Start the sum with `res = d`.
   - Accumulate the days of all preceding full months (`i = 0` to `m - 2`) into `res`.
   - Return `res`, which gives the 1-based day of the year.

3. **Convert All 4 Dates**:
   - `as`: Alice's start day.
   - `aed`: Alice's end day.
   - `bst`: Bob's start day.
   - `bed`: Bob's end day.

4. **Calculate Overlap**:
   - `st = Math.max(as, bst)` finds the latest arrival day (when both are in Rome).
   - `ed = Math.min(aed, bed)` finds the earliest departure day (the last day both are still in Rome).
   - If `st <= ed`, the interval is valid, and the inclusive count is `ed - st + 1`. Otherwise, they never overlap, so return `0`.

---

### 🧩 Algorithm

1. **Date Parsing / Prefix Sum Calculation**:
   $$\text{toDay}(M, D) = D + \sum_{i=0}^{M-2} \text{days}[i]$$

2. **1D Interval Overlap Formula**:
   $$\text{st} = \max(\text{as}, \text{bst})$$
   $$\text{ed} = \min(\text{aed}, \text{bed})$$
   $$\text{Result} = \begin{cases} \text{ed} - \text{st} + 1 & \text{if } \text{st} \le \text{ed} \\ 0 & \text{if } \text{st} > \text{ed} \end{cases}$$

---

### ✅ Why This Works

- **Monotonic Calendar**: Days within a year increment continuously from day 1 to day 365. Converting dates to continuous numbers allows basic mathematical comparisons.
- **Inclusive Range Overlap**: For two closed intervals $[A, B]$ and $[C, D]$, the intersection interval is $[\max(A, C), \min(B, D)]$. If $\max(A, C) \le \min(B, D)$, the total number of integer points inside the inclusive interval is $(\min(B, D) - \max(A, C) + 1)$. If $\max(A, C) > \min(B, D)$, the intervals do not intersect.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(1)$
  - String parsing looks at a fixed 5-character string.
  - The loop in `toDay` runs at most 11 times (for December).
  - All operations take constant time.

- **Space Complexity**: $\mathcal{O}(1)$
  - Uses a fixed 12-element array `days` and a few integer variables.

---

### 🧠 DSA Pattern

- **Intervals / Overlap**: Finding the intersection of two 1D closed ranges using $\max(\text{starts})$ and $\min(\text{ends})$.
- **Prefix Sum / Data Transformation**: Converting a formatted 2D date standard `(Month, Day)` into a 1D linear integer scale (`Day of Year`).

---

### ⚠️ Common Mistakes

1. **Off-By-One in Month Summation**:
   - In `toDay`, writing `i < m` instead of `i < m - 1` would add the days of the current month twice (once in the loop and once via `res = d`).
2. **Inclusive Interval Off-by-One**:
   - Forgetting to add `+ 1` when calculating range length (`ed - st + 1`), which would undercount the actual number of days by 1.
3. **Negative Day Count**:
   - Returning `ed - st + 1` directly without checking `st <= ed`, which leads to negative outputs when Alice and Bob's trips do not overlap.

---

### 🚀 Optimization Notes

- The solution is already optimal in terms of both time ($\mathcal{O}(1)$) and space ($\mathcal{O}(1)$).
- A minor tweak in `toDay` could use a precomputed prefix sum array for `days` instead of looping, but since the month count is bounded by 12, the loop takes negligible CPU cycles.
