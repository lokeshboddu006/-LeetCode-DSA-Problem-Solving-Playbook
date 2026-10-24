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
The problem asks for the total number of days Alice and Bob overlap in Rome. Alice is present during the interval defined by `[arriveAlice, leaveAlice]` (`[a, b]`), and Bob is present during `[arriveBob, leaveBob]` (`[c, d]`).

Instead of iterating through dates or constructing a calendar lookup array, your solution converts every `"MM-DD"` date string into an **absolute 1-based day of the year** (from 1 to 365) using an explicit mathematical formula. Once dates are converted to linear integers, the problem reduces to finding the length of the intersection of two 1D closed intervals $[A_1, A_2]$ and $[B_1, B_2]$.

### 🔍 Approach
1. **Date Parsing & Conversion (`f(String s)`)**:
   - Extract the 2-digit month `m` from characters at index `0` and `1`: `(s.charAt(0) - '0') * 10 + (s.charAt(1) - '0')`.
   - Extract the 2-digit day `q` from characters at index `3` and `4`: `(s.charAt(3) - '0') * 10 + (s.charAt(4) - '0')`.
   - Compute the day of the year using the closed-form equation:
     $$\text{day} = \frac{275 \cdot m}{9} - 30 - 2 \cdot \left(\frac{m + 9}{12}\right) + q$$
     (using integer arithmetic). This formula calculates the total number of days preceding month `m` in a standard 365-day non-leap year and adds the current day of the month `q`.

2. **Interval Overlap Calculation (`countDaysTogether`)**:
   - `a` = `arriveAlice`, `b` = `leaveAlice`, `c` = `arriveBob`, `d` = `leaveBob`.
   - Convert all 4 dates into year-day integers using `f(...)`.
   - The overlap period starts at the latest of the two arrival days: `Math.max(f(a), f(c))`.
   - The overlap period ends at the earliest of the two departure days: `Math.min(f(b), f(d))`.
   - The total overlapping days in an inclusive range $[start, end]$ is $(end - start + 1)$.
   - Wrap with `Math.max(0, ...)` so that if there is no overlap ($end < start$), the function safely returns `0`.

### 🧩 Algorithm
- **Date Conversion Formula**:
  $$f(m, q) = \lfloor \frac{275 \times m}{9} \rfloor - 30 - 2 \times \lfloor \frac{m + 9}{12} \rfloor + q$$
- **1D Range Intersection**:
  $$\text{Overlap Start} = \max(f(a), f(c))$$
  $$\text{Overlap End} = \min(f(b), f(d))$$
  $$\text{Days Together} = \max(0, \text{Overlap End} - \text{Overlap Start} + 1)$$

### ✅ Why This Works
- **Mathematical Date Encoding**: In a non-leap year, month lengths alternate mostly between 31 and 30 days, with February having 28 days. The linear approximation $\lfloor \frac{275 \cdot m}{9} \rfloor - 30$ models the month lengths closely, and the term $-2 \cdot \lfloor \frac{m + 9}{12} \rfloor$ subtracts 2 days for February once $m \ge 3$. This maps every valid date uniquely to its ordinal day in $[1, 365]$.
- **Correct Overlap Logic**: For two continuous inclusive segments $[L_1, R_1]$ and $[L_2, R_2]$, their common points form the segment $[\max(L_1, L_2), \min(R_1, R_2)]$. The number of discrete points in $[X, Y]$ is $Y - X + 1$ when $Y \ge X$. When $Y < X$, `Math.max(0, ...)` handles the non-overlapping condition.

### ⏱️ Complexity
- **Time Complexity:** $\mathcal{O}(1)$. String index lookups, basic arithmetic, and `Math.max`/`Math.min` calls all run in constant time.
- **Space Complexity:** $\mathcal{O}(1)$. The method uses only a few scalar primitive variables (`m`, `q`), allocating no extra memory.

### 🧠 DSA Pattern
- **Math / Direct Date Arithmetic**: Using a closed-form formula to map dates into a 1D coordinate system without auxiliary arrays.
- **Interval Overlap**: Finding the intersection length of two 1D segments using $\max(\text{starts})$ and $\min(\text{ends})$.

### ⚠️ Common Mistakes
- **Formula Sensitivity**: The formula `(275 * m) / 9 - 30 - 2 * ((m + 9) / 12) + q` relies heavily on integer truncation rules in Java. Changing integer division or parentheses would produce incorrect day values.
- **Leap Year Assumption**: This formula specifically models a non-leap year year (28 days in February). It would be incorrect if applied to leap years without adjusting the February constant.
- **Inclusive Range Off-By-One**: Omitting the `+ 1` when calculating range length would underestimate the days spent together by 1.

### 🚀 Optimization Notes
- The solution is already optimal in both time ($\mathcal{O}(1)$) and space ($\mathcal{O}(1)$).
- By leveraging date arithmetic directly in `f(String s)`, it avoids allocating lookup arrays or executing iteration loops.
