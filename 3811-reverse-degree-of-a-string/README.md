<h2><a href="https://leetcode.com/problems/reverse-degree-of-a-string">Reverse Degree of a String</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given a string <code>s</code>, calculate its <strong>reverse degree</strong>.</p>

<p>The <strong>reverse degree</strong> is calculated as follows:</p>

<ol>
	<li>For each character, multiply its position in the <em>reversed</em> alphabet (<code>&#39;a&#39;</code> = 26, <code>&#39;b&#39;</code> = 25, ..., <code>&#39;z&#39;</code> = 1) with its position in the string <strong>(1-indexed)</strong>.</li>
	<li>Sum these products for all characters in the string.</li>
</ol>

<p>Return the <strong>reverse degree</strong> of <code>s</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;abc&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">148</span></p>

<p><strong>Explanation:</strong></p>

<table style="border: 1px solid black;">
	<tbody>
		<tr>
			<th style="border: 1px solid black;">Letter</th>
			<th style="border: 1px solid black;">Index in Reversed Alphabet</th>
			<th style="border: 1px solid black;">Index in String</th>
			<th style="border: 1px solid black;">Product</th>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;a&#39;</code></td>
			<td style="border: 1px solid black;">26</td>
			<td style="border: 1px solid black;">1</td>
			<td style="border: 1px solid black;">26</td>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;b&#39;</code></td>
			<td style="border: 1px solid black;">25</td>
			<td style="border: 1px solid black;">2</td>
			<td style="border: 1px solid black;">50</td>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;c&#39;</code></td>
			<td style="border: 1px solid black;">24</td>
			<td style="border: 1px solid black;">3</td>
			<td style="border: 1px solid black;">72</td>
		</tr>
	</tbody>
</table>

<p>The reversed degree is <code>26 + 50 + 72 = 148</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;zaza&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">160</span></p>

<p><strong>Explanation:</strong></p>

<table style="border: 1px solid black;">
	<tbody>
		<tr>
			<th style="border: 1px solid black;">Letter</th>
			<th style="border: 1px solid black;">Index in Reversed Alphabet</th>
			<th style="border: 1px solid black;">Index in String</th>
			<th style="border: 1px solid black;">Product</th>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;z&#39;</code></td>
			<td style="border: 1px solid black;">1</td>
			<td style="border: 1px solid black;">1</td>
			<td style="border: 1px solid black;">1</td>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;a&#39;</code></td>
			<td style="border: 1px solid black;">26</td>
			<td style="border: 1px solid black;">2</td>
			<td style="border: 1px solid black;">52</td>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;z&#39;</code></td>
			<td style="border: 1px solid black;">1</td>
			<td style="border: 1px solid black;">3</td>
			<td style="border: 1px solid black;">3</td>
		</tr>
		<tr>
			<td style="border: 1px solid black;"><code>&#39;a&#39;</code></td>
			<td style="border: 1px solid black;">26</td>
			<td style="border: 1px solid black;">4</td>
			<td style="border: 1px solid black;">104</td>
		</tr>
	</tbody>
</table>

<p>The reverse degree is <code>1 + 52 + 3 + 104 = 160</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 1000</code></li>
	<li><code>s</code> contains only lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation in your solution is that string processing can be done in a single linear pass by transforming character arithmetic and loop index values directly into the required math terms. 

Instead of constructing a reversed lookup mapping or a reversed alphabet string, your code uses relative offset arithmetic (`ch - 'a'`) to map each character to its 1-indexed position from the end of the alphabet (`26 - offset`). Combined with converting the loop index `i` into a 1-indexed string position (`i + 1`), you compute each character's contribution to the reverse degree on the fly and sum them up.

### 🔍 Approach

1. **Initialize Accumulator:** Declare an integer variable `sum` initialized to `0` to hold the total score.
2. **Iterate Through String:** Loop through string `s` from index `i = 0` up to `s.length() - 1`.
3. **Extract Character:** In each iteration, extract `char ch = s.charAt(i)`.
4. **Calculate Reversed Alphabet Index:** Compute `rv = 26 - (ch - 'a')`.
   - `'a' - 'a'` gives `0`, so `rv` becomes `26 - 0 = 26`.
   - `'z' - 'a'` gives `25`, so `rv` becomes `26 - 25 = 1`.
5. **Convert String Index:** Compute `p = i + 1` to convert the 0-based loop index into the required 1-based index position.
6. **Accumulate Product:** Multiply `rv` by `p` and add the result directly to `sum`.
7. **Return Result:** After the loop finishes, return `sum`.

### 🧩 Algorithm

This approach is a direct mathematical traversal:

$$\text{sum} = \sum_{i=0}^{n-1} \Big(26 - (\text{s}[i] - \text{'a'})\Big) \times (i + 1)$$

Where:
- $n$ is the length of string `s`.
- $\text{s}[i] - \text{'a'}$ converts character $\text{s}[i]$ to its 0-based standard alphabet position ($0$ for `'a'`, $25$ for `'z'`).
- $26 - (\text{s}[i] - \text{'a'})$ converts it to its 1-based reversed alphabet position ($26$ for `'a'`, $1$ for `'z'`).
- $i + 1$ converts the 0-based character index in the string to a 1-based position.

### ✅ Why This Works

- **ASCII Character Offset:** Subtraction between `char` primitive types in Java evaluates using their numeric ASCII values. Since lowercase English letters `'a'` through `'z'` are contiguous in ASCII, `ch - 'a'` evaluates to an integer range of `[0, 25]`.
- **Reversed Alphabet Mapping:** Subtracting `ch - 'a'` from `26` creates a linear mapping where `'a'` yields $26$, `'b'` yields $25$, ..., and `'z'` yields $1$. This exactly matches the reversed alphabet index requirement.
- **1-based Indexing:** Adding `1` to the loop counter `i` yields the 1-based string position $p$ for character $i$.
- Multiplying `rv` and `p` at each index and summing these products over the whole string guarantees the exact reverse degree is accumulated without modifying the string or requiring extra data structures.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(n)$, where $n$ is the length of the string `s`. The algorithm makes a single pass over the string, spending $\mathcal{O}(1)$ time per character.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. Memory usage is constant since only local primitive variables (`sum`, `i`, `ch`, `rv`, `p`) are created.

### 🧠 DSA Pattern

- Math
- String Iteration / Linear Scan

### ⚠️ Common Mistakes

- **Incorrect Offset Constant:** Using `25 - (ch - 'a')` instead of `26 - (ch - 'a')` would produce a 0-indexed reversed alphabet value ($25$ for `'a'`, $0$ for `'z'`), causing incorrect products (e.g., `'z'` contributing `0`).
- **Forgetting 1-Based Indexing:** Using `i` directly instead of `i + 1` would cause the first character at index `0` to contribute `0` to the final result.
- **Integer Overflow Considerations:** In problems with large string lengths, multiplying large indices with position values could exceed standard 32-bit integer limits (`Integer.MAX_VALUE`). However, for $n \le 1000$, the maximum sum is well within the bounds of standard 32-bit signed `int` (max single product is $26 \times 1000 = 26,000$, total maximum sum $\approx 1.3 \times 10^7$).

### 🚀 Optimization Notes

- The solution is already optimal with $\mathcal{O}(n)$ time and $\mathcal{O}(1)$ space complexity.
- Directly using `s.charAt(i)` avoids allocating a new character array in memory (such as with `s.toCharArray()`), keeping auxiliary space strictly minimal.
