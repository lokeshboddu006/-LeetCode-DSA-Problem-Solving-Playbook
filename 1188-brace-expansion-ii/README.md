<h2><a href="https://leetcode.com/problems/brace-expansion-ii">Brace Expansion II</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Under the grammar given below, strings can represent a set of lowercase words. Let&nbsp;<code>R(expr)</code>&nbsp;denote the set of words the expression represents.</p>

<p>The grammar can best be understood through simple examples:</p>

<ul>
	<li>Single letters represent a singleton set containing that word.
	<ul>
		<li><code>R(&quot;a&quot;) = {&quot;a&quot;}</code></li>
		<li><code>R(&quot;w&quot;) = {&quot;w&quot;}</code></li>
	</ul>
	</li>
	<li>When we take a comma-delimited list of two or more expressions, we take the union of possibilities.
	<ul>
		<li><code>R(&quot;{a,b,c}&quot;) = {&quot;a&quot;,&quot;b&quot;,&quot;c&quot;}</code></li>
		<li><code>R(&quot;{{a,b},{b,c}}&quot;) = {&quot;a&quot;,&quot;b&quot;,&quot;c&quot;}</code> (notice the final set only contains each word at most once)</li>
	</ul>
	</li>
	<li>When we concatenate two expressions, we take the set of possible concatenations between two words where the first word comes from the first expression and the second word comes from the second expression.
	<ul>
		<li><code>R(&quot;{a,b}{c,d}&quot;) = {&quot;ac&quot;,&quot;ad&quot;,&quot;bc&quot;,&quot;bd&quot;}</code></li>
		<li><code>R(&quot;a{b,c}{d,e}f{g,h}&quot;) = {&quot;abdfg&quot;, &quot;abdfh&quot;, &quot;abefg&quot;, &quot;abefh&quot;, &quot;acdfg&quot;, &quot;acdfh&quot;, &quot;acefg&quot;, &quot;acefh&quot;}</code></li>
	</ul>
	</li>
</ul>

<p>Formally, the three rules for our grammar:</p>

<ul>
	<li>For every lowercase letter <code>x</code>, we have <code>R(x) = {x}</code>.</li>
	<li>For expressions <code>e<sub>1</sub>, e<sub>2</sub>, ... , e<sub>k</sub></code> with <code>k &gt;= 2</code>, we have <code>R({e<sub>1</sub>, e<sub>2</sub>, ...}) = R(e<sub>1</sub>) &cup; R(e<sub>2</sub>) &cup; ...</code></li>
	<li>For expressions <code>e<sub>1</sub></code> and <code>e<sub>2</sub></code>, we have <code>R(e<sub>1</sub> + e<sub>2</sub>) = {a + b for (a, b) in R(e<sub>1</sub>) &times; R(e<sub>2</sub>)}</code>, where <code>+</code> denotes concatenation, and <code>&times;</code> denotes the cartesian product.</li>
</ul>

<p>Given an expression representing a set of words under the given grammar, return <em>the sorted list of words that the expression represents</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> expression = &quot;{a,b}{c,{d,e}}&quot;
<strong>Output:</strong> [&quot;ac&quot;,&quot;ad&quot;,&quot;ae&quot;,&quot;bc&quot;,&quot;bd&quot;,&quot;be&quot;]
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> expression = &quot;{{a,z},a{b,c},{ab,z}}&quot;
<strong>Output:</strong> [&quot;a&quot;,&quot;ab&quot;,&quot;ac&quot;,&quot;z&quot;]
<strong>Explanation:</strong> Each distinct word is written only once in the final answer.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= expression.length &lt;= 60</code></li>
	<li><code>expression[i]</code> consists of <code>&#39;{&#39;</code>, <code>&#39;}&#39;</code>, <code>&#39;,&#39;</code>or lowercase English letters.</li>
	<li>The given&nbsp;<code>expression</code>&nbsp;represents a set of words based on the grammar given in the description.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The algorithm treats the expression as a hierarchical formal grammar with two main operators: **Union** (represented by commas `,`) and **Concatenation** (implicit adjacency of tokens/braced groups). 

Because union (commas) has lower binding precedence than concatenation, your approach uses a **Divide and Conquer / Parsing** hierarchy:
1. First, search for a top-level comma (a comma not nested inside any curly braces). If found, split the string at that comma and take the set union of the evaluated left and right sub-expressions.
2. If no top-level comma exists, check if the entire expression is wrapped in a matching pair of outermost braces `{...}`. If so, strip them and evaluate the inner content.
3. Otherwise, the expression must be a sequence of concatenated parts (e.g., individual characters or braced sub-expressions adjacent to each other). Evaluate each adjacent part and perform a Cartesian product with the accumulated result set.

`TreeSet` is used throughout the recursion to automatically keep generated strings unique and lexicographically sorted.

---

### 🔍 Approach

1. **`braceExpansionII(String expression)`**:
   - Calls the helper method `dfs(expression, 0, expression.length() - 1)` to evaluate the full range of the expression.
   - Converts the resulting `TreeSet<String>` (which is sorted and deduplicated) into an `ArrayList<String>` and returns it.

2. **`dfs(String s, int l, int r)`**:
   - **Step 1: Top-level Split on Union (Commas)**
     - Iterates through `i` from `l` to `r` while tracking bracket depth with `balance`.
     - When `s.charAt(i) == ','` and `balance == 0`, a top-level split point is found.
     - It recursively evaluates `dfs(s, l, i - 1)` and `dfs(s, i + 1, r)`, merges their set outputs into `result`, and returns immediately.

   - **Step 2: Strip Outer Braces**
     - If no top-level comma was found, it checks if `s.charAt(l) == '{'` and its matching closing brace (found via `matchingBrace`) equals `r`.
     - If true, the entire substring is enclosed by a single pair of outer braces. It unwraps them by returning `dfs(s, l + 1, r - 1)`.

   - **Step 3: Concatenation (Cartesian Product)**
     - If the expression is neither a top-level comma split nor enclosed by outer braces, it represents concatenated terms.
     - Initializes `result` set with an empty string `""`.
     - Iterates `i` from `l` to `r`:
       - If `s.charAt(i) == '{'`, uses `matchingBrace(s, i)` to find the closing brace `j`, evaluates `dfs(s, i + 1, j - 1)`, and moves `i` to `j + 1`.
       - If `s.charAt(i)` is a letter, creates a singleton set with that character and increments `i` by 1.
       - Computes the Cartesian product between `result` and `part` (`a + b` for all combinations), updating `result`.

3. **`matchingBrace(String s, int start)`**:
   - Scans forward from index `start` (assuming `s.charAt(start) == '{'`), adjusting `balance` for `{` and `}`.
   - Returns the index where `balance` returns to `0`.

---

### 🧩 Algorithm

- **Top-level Union Split (Grammar OR)**:
  $$\text{dfs}(s, l, r) = \text{dfs}(s, l, i - 1) \cup \text{dfs}(s, i + 1, r) \quad \text{where } s[i] = ',' \text{ and balance} = 0$$

- **Brace Unwrapping**:
  $$\text{dfs}(s, l, r) = \text{dfs}(s, l + 1, r - 1) \quad \text{if } s[l] = '\{' \text{ and } \text{matchingBrace}(l) = r$$

- **Cartesian Product Concatenation (Grammar AND)**:
  $$\text{dfs}(s, l, r) = P_1 \times P_2 \times \dots \times P_k$$
  where each $P_m$ is either a single character set $\{c\}$ or an evaluated braced block $\text{dfs}(s, \text{start} + 1, \text{end} - 1)$.

---

### ✅ Why This Works

- **Operator Precedence**: By checking for top-level commas before processing adjacent concatenations, the code respects grammar precedence: union `,` has lower precedence than concatenation.
- **Top-level Level Balance**: Maintaining the `balance` integer guarantees that commas inside nested braces are ignored during top-level union splits, preserving the correct structure of nested expressions.
- **TreeSet Data Structure**: Automatically handles both requirements of the output: removing duplicate string combinations generated by unions/concatenations and maintaining lexicographical order without requiring an extra sorting step at the end.

---

### ⏱️ Complexity

- **Time Complexity**: 
  - **Parsing & Matching**: For a string of length $N$, finding matching braces or splitting on commas takes $O(N)$ scan per recursive call depth.
  - **Cartesian Product**: The number of words generated can grow exponentially depending on the nesting and options (e.g., $\{a,b\}\{c,d\}\dots$). If $K$ total unique strings are formed across all steps, constructing strings and inserting them into `TreeSet` takes $O(K \cdot L \log K)$, where $L$ is the maximum string length generated.
  - Given $N \le 60$, the total number of words and recursion depth remain small enough to run well within time limits.

- **Space Complexity**:
  - **Recursion Stack**: Bounded by the depth of nested braces, which is $O(N)$ in the worst case.
  - **Set Storage**: $O(K \cdot L)$ to store generated intermediate and final sets of strings in memory.

---

### 🧠 DSA Pattern

- **Divide and Conquer / Recursion (DFS)**
- **String Parsing (Grammar / Expression Parsing)**
- **Ordered Set (`TreeSet`) for deduplication and sorting**

---

### ⚠️ Common Mistakes

1. **Splitting on Nested Commas**: Forgetting to track bracket `balance` when searching for commas would incorrectly split nested expressions like `{a,{b,c}}` at the inner comma.
2. **Incorrect Matching Brace Indexing**: Confusing the matching brace of a prefix block with the end of the entire string (e.g., in `{a,b}{c,d}`, the first brace closes at index 4, not at the end of the string). Your code correctly distinguishes step 2 (entire string is wrapped) from step 3 (prefix is wrapped).
3. **Empty String Base Case for Concatenation**: When starting the concatenation loop, initializing `result` with an empty set instead of a set containing `""` would cause the Cartesian product loop `for (String a : result)` to never execute, producing an empty output.

---

### 🚀 Optimization Notes

- **Repeated Scans in `matchingBrace`**: The `matchingBrace` method performs a linear scan from `start` forward every time it is called. Pre-calculating matching brace pairs using a stack in a single $O(N)$ pass prior to running `dfs` would avoid redundant scanning, though for $N \le 60$ the current scan overhead is negligible.
- **String Manipulations**: Using `TreeSet` inserts at every Cartesian step guarantees sorted order and uniqueness early, but incurs $O(\log K)$ overhead per insertion. An alternative internal representation using a `HashSet` during recursion followed by a single sort at the end could reduce set insertion overhead, though the current `TreeSet` usage is clean and correct.
