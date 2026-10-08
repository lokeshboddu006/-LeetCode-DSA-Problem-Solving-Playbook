<h2><a href="https://leetcode.com/problems/remove-outermost-parentheses">Remove Outermost Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>A valid parentheses string is either empty <code>&quot;&quot;</code>, <code>&quot;(&quot; + A + &quot;)&quot;</code>, or <code>A + B</code>, where <code>A</code> and <code>B</code> are valid parentheses strings, and <code>+</code> represents string concatenation.</p>

<ul>
	<li>For example, <code>&quot;&quot;</code>, <code>&quot;()&quot;</code>, <code>&quot;(())()&quot;</code>, and <code>&quot;(()(()))&quot;</code> are all valid parentheses strings.</li>
</ul>

<p>A valid parentheses string <code>s</code> is primitive if it is nonempty, and there does not exist a way to split it into <code>s = A + B</code>, with <code>A</code> and <code>B</code> nonempty valid parentheses strings.</p>

<p>Given a valid parentheses string <code>s</code>, consider its primitive decomposition: <code>s = P<sub>1</sub> + P<sub>2</sub> + ... + P<sub>k</sub></code>, where <code>P<sub>i</sub></code> are primitive valid parentheses strings.</p>

<p>Return <code>s</code> <em>after removing the outermost parentheses of every primitive string in the primitive decomposition of </em><code>s</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(()())(())&quot;
<strong>Output:</strong> &quot;()()()&quot;
<strong>Explanation:</strong> 
The input string is &quot;(()())(())&quot;, with primitive decomposition &quot;(()())&quot; + &quot;(())&quot;.
After removing outer parentheses of each part, this is &quot;()()&quot; + &quot;()&quot; = &quot;()()()&quot;.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(()())(())(()(()))&quot;
<strong>Output:</strong> &quot;()()()()(())&quot;
<strong>Explanation:</strong> 
The input string is &quot;(()())(())(()(()))&quot;, with primitive decomposition &quot;(()())&quot; + &quot;(())&quot; + &quot;(()(()))&quot;.
After removing outer parentheses of each part, this is &quot;()()&quot; + &quot;()&quot; + &quot;()(())&quot; = &quot;()()()()(())&quot;.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;()()&quot;
<strong>Output:</strong> &quot;&quot;
<strong>Explanation:</strong> 
The input string is &quot;()()&quot;, with primitive decomposition &quot;()&quot; + &quot;()&quot;.
After removing outer parentheses of each part, this is &quot;&quot; + &quot;&quot; = &quot;&quot;.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s[i]</code> is either <code>&#39;(&#39;</code> or <code>&#39;)&#39;</code>.</li>
	<li><code>s</code> is a valid parentheses string.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation is that a valid parentheses string can be decomposed into primitive valid components. Within each primitive component:
- The very first opening parenthesis `'('` starts the primitive unit when the depth of nested parentheses is `0`.
- The very last closing parenthesis `')'` closes the primitive unit, bringing the depth back down to `0`.

Instead of explicitly splitting the string into sub-strings or using a stack data structure, your implementation tracks the current nesting depth using an integer counter `depth`. By checking `depth`, the code selectively includes only the characters that lie strictly inside the outermost parentheses of each primitive block.

### 🔍 Approach

1. **Initialize helper variables:**
   - A `StringBuilder` named `answer` to construct the output string efficiently.
   - An integer `depth` initialized to `0` to keep track of the current nesting level.

2. **Iterate through the string:**
   - Loop through `s` character by character from index `0` to `s.length() - 1`.

3. **Handle opening parenthesis `'('`:**
   - Before updating `depth`, check if `depth > 0`.
   - If `depth > 0`, it means this `'('` is an internal parenthesis (not the start of a primitive group), so append it to `answer`.
   - Increment `depth` by `1`.

4. **Handle closing parenthesis `')'`:**
   - Decrement `depth` by `1` first.
   - After decrementing, check if `depth > 0`.
   - If `depth > 0`, it means this `')'` is an internal parenthesis (not the closing bracket of a primitive group), so append it to `answer`.

5. **Return result:**
   - Convert `answer` to a string using `answer.toString()` and return it.

### 🧩 Algorithm

The algorithm uses a balance counter invariant:

- **State:** `depth` represents the current level of open parentheses.
- **For `'('`:**
  - If `depth > 0`: `answer.append('(')`
  - Transition: `depth = depth + 1`
- **For `')'`:**
  - Transition: `depth = depth - 1`
  - If `depth > 0`: `answer.append(')')`

### ✅ Why This Works

- **Outer Opening Bracket (`'('` at `depth == 0`):**
  When a primitive component begins, `depth` is `0`. The check `depth > 0` fails, so this outer `'('` is not appended to `answer`. `depth` then becomes `1`.
- **Inner Parentheses:**
  Any subsequent `'('` sees `depth >= 1` (so `depth > 0` is true) and gets appended.
  Any matching inner `')'` decrements `depth` to a value $\ge 1$, so `depth > 0` remains true and it gets appended.
- **Outer Closing Bracket (`')'` returning to `depth == 0`):**
  When the final `')'` of a primitive group is encountered, decrementing `depth` brings it from `1` down to `0`. The check `depth > 0` fails, so this outer `')'` is omitted.

This ensures every character except the outermost boundaries of each primitive component is preserved in order.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of string `s`. The algorithm processes each character of `s` exactly once in a single pass, performing $\mathcal{O}(1)$ operations per character.
- **Space Complexity:** $\mathcal{O}(N)$ to store the output in the `StringBuilder`. The auxiliary space used (excluding the result container) is $\mathcal{O}(1)$ as it only uses an integer counter `depth`.

### 🧠 DSA Pattern

- **Counting / Depth Tracking** (Simulating stack depth without an actual stack)
- **String Manipulation / StringBuilder**

### ⚠️ Common Mistakes

1. **Incorrect order of checking vs. updating `depth`:**
   - For `'('`, checking `depth > 0` *after* `depth++` would accidentally include the outermost opening parenthesis.
   - For `')'`, checking `depth > 0` *before* `depth--` would accidentally include the outermost closing parenthesis.
2. **Using immutable String concatenation:**
   - Using string concatenation (`answer += c`) inside the loop instead of `StringBuilder` would result in an $\mathcal{O}(N^2)$ time complexity due to repeated string copying in Java.

### 🚀 Optimization Notes

- Your solution is already optimal in terms of both time ($\mathcal{O}(N)$) and auxiliary space ($\mathcal{O}(1)$).
- Avoiding an explicit `java.util.Stack` reduces memory allocation overhead and runtime performance cost while maintaining clear logic.
