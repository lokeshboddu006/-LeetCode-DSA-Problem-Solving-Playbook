<h2><a href="https://leetcode.com/problems/score-of-parentheses">Score of Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a balanced parentheses string <code>s</code>, return <em>the <strong>score</strong> of the string</em>.</p>

<p>The <strong>score</strong> of a balanced parentheses string is based on the following rule:</p>

<ul>
	<li><code>&quot;()&quot;</code> has score <code>1</code>.</li>
	<li><code>AB</code> has score <code>A + B</code>, where <code>A</code> and <code>B</code> are balanced parentheses strings.</li>
	<li><code>(A)</code> has score <code>2 * A</code>, where <code>A</code> is a balanced parentheses string.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;()&quot;
<strong>Output:</strong> 1
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(())&quot;
<strong>Output:</strong> 2
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;()()&quot;
<strong>Output:</strong> 2
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= s.length &lt;= 50</code></li>
	<li><code>s</code> consists of only <code>&#39;(&#39;</code> and <code>&#39;)&#39;</code>.</li>
	<li><code>s</code> is a balanced parentheses string.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code processes the string by keeping track of the current score at the active nesting level (`res`) and using a **Stack** to save outer context scores whenever a new nested expression begins.

When you encounter an opening parenthesis `(`, you step one level deeper into the nesting structure. You save whatever score was already computed at the current level onto the stack and reset the current level's score (`res = 0`) to start evaluating the inner expression.

When you encounter a closing parenthesis `)`, you wrap up the current inner level:
- If `res` is `0`, it means you just saw an immediate `()` pair, which contributes a base score of `1`.
- If `res` is greater than `0`, it means you just closed a nested expression `(A)`, which multiplies the inner score by `2` (`2 * res`).
- Finally, you pop the outer score from the stack and add the newly completed score to it.

### 🔍 Approach

1. **Initialization**:
   - Create a stack `Stack<Integer> st` to store outer scores when entering deeper levels.
   - Initialize `int res = 0` to maintain the accumulated score at the current depth.

2. **Traversal**:
   - Convert `s` to a character array and loop through each character `ch`:
     - **If `ch == '('`**:
       - Push the current accumulated score `res` onto `st`.
       - Reset `res = 0` to compute the score inside this new set of parentheses.
     - **If `ch == ')'`**:
       - Calculate the score of the newly closed group: `Math.max(res * 2, 1)`.
         - If `res == 0` (empty inside `()`), `Math.max(0, 1)` yields `1`.
         - If `res > 0` (enclosing `(A)`), `Math.max(res * 2, 1)` yields `2 * res`.
       - Pop the outer score stored before this group started (`st.pop()`).
       - Add the popped score to the newly evaluated group score and update `res`.

3. **Return**:
   - After processing all characters, `res` holds the total score of the balanced parentheses string.

### 🧩 Algorithm

1. Set `res = 0`, initialize `st`.
2. For each character `ch` in `s`:
   - If `ch == '('`:
     - `st.push(res)`
     - `res = 0`
   - Else (`ch == ')'`):
     - `res = st.pop() + Math.max(res * 2, 1)`
3. Return `res`.

### ✅ Why This Works

- **Base Case `()`**: When `res` is `0` upon seeing `)`, `Math.max(0, 1)` cleanly handles the base rule where `()` equals `1`.
- **Enclosing `(A)`**: When `res > 0`, `res` holds the total score $A$ of the inner expression. `Math.max(res * 2, 1)` correctly evaluates to $2 \times A$.
- **Concatenation `AB`**: Before starting `B`, the score of `A` is pushed to the stack. When `B` finishes, its score is added to `A` via `st.pop() + score_of_B`, correctly forming $A + B$.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of string `s`. The algorithm iterates through the characters of string `s` once, performing $\mathcal{O}(1)$ push and pop operations per character.
- **Space Complexity:** $\mathcal{O}(N)$. In the worst case (e.g., deep nesting like `(((())))`), the stack `st` can store up to $N / 2$ elements. Additionally, `s.toCharArray()` creates a character array of size $N$.

### 🧠 DSA Pattern

- **Stack** (used for tracking state during nested structure traversal)

### ⚠️ Common Mistakes

1. **Forgetting to reset `res` to `0` when pushing**: If `res` is not reset to `0` upon opening a `(`, the inner expression evaluation will incorrectly inherit previous outer scores instead of starting fresh.
2. **Confusing multiplication and base cases**: Mixing up when to multiply by 2 vs. adding 1. Using `Math.max(res * 2, 1)` elegantly unifies both cases because `0 * 2 = 0 < 1`.

### 🚀 Optimization Notes

- **Language Overhead**: Java's `java.util.Stack` extends `Vector` and is synchronized, incurring minor overhead. Using `Deque<Integer> stack = new ArrayDeque<>()` or a primitive array `int[]` as a manual stack can reduce dynamic allocation overhead.
- **String Conversion**: `s.charAt(i)` inside a standard `for` loop avoids creating a new `char[]` array via `s.toCharArray()`, saving $\mathcal{O}(N)$ extra heap memory.
