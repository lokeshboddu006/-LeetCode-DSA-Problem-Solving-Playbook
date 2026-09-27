<h2><a href="https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses">Reverse Substrings Between Each Pair of Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a string <code>s</code> that consists of lower case English letters and brackets.</p>

<p>Reverse the strings in each pair of matching parentheses, starting from the innermost one.</p>

<p>Your result should <strong>not</strong> contain any brackets.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(abcd)&quot;
<strong>Output:</strong> &quot;dcba&quot;
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(u(love)i)&quot;
<strong>Output:</strong> &quot;iloveu&quot;
<strong>Explanation:</strong> The substring &quot;love&quot; is reversed first, then the whole string is reversed.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(ed(et(oc))el)&quot;
<strong>Output:</strong> &quot;leetcode&quot;
<strong>Explanation:</strong> First, we reverse the substring &quot;oc&quot;, then &quot;etco&quot;, and finally, the whole string.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 2000</code></li>
	<li><code>s</code> only contains lower case English characters and parentheses.</li>
	<li>It is guaranteed that all parentheses are balanced.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

When reversing nested segments bounded by parentheses, every time you enter or exit a pair of parentheses, the direction in which characters should be read reverses. 

Instead of performing actual string reversals step-by-step (which would take $O(N^2)$ time), this solution treats each matching pair of parentheses as a **teleporter (or "wormhole")**. When you walk from left to right and hit a parenthesis, you jump directly to its matching pair and flip your walking direction (`dir = -dir`). 

This allows you to construct the final output in a single linear traversal of $O(N)$ time.

---

### 🔍 Approach

1. **First Pass — Pairing Parentheses**:
   - Maintain an integer array `link` of size `n` to map indices of matching brackets.
   - Use a `Stack<Integer> stk` to hold the indices of open parentheses `'('`.
   - Iterate through `s` with index `i`:
     - If `s.charAt(i)` is `'('`, push index `i` onto `stk`.
     - If `s.charAt(i)` is `')'`, pop the top index from `stk` (which is the matching `'('`) and create bidirectional links:
       `link[i] = poppedIndex` and `link[poppedIndex] = i`.

2. **Second Pass — Traversing with Teleportation**:
   - Initialize a `StringBuilder sb` for the output.
   - Start at index `i = 0` with traversal direction `dir = 1` (moving forward).
   - Loop as long as `i` stays within bounds (the loop step is `i += dir`):
     - If `s.charAt(i)` is a letter (`s.charAt(i) >= 'a'`), append it to `sb`.
     - If `s.charAt(i)` is a parenthesis (`'('` or `')'`):
       - Teleport to its paired parenthesis index: `i = link[i]`.
       - Flip the traversal direction: `dir = -dir`.
     - Advance `i` by `dir` at the end of each iteration.

3. **Return**:
   - Return `sb.toString()`.

---

### 🧩 Algorithm

1. **Parenthesis Matching**:
   - For all $0 \le i < n$:
     - If $s[i] == \text{'('} \implies \text{push}(i)$
     - If $s[i] == \text{')'} \implies j = \text{pop}()$; set $\text{link}[i] = j$ and $\text{link}[j] = i$.

2. **Directional Traversal State**:
   - State: $(i, \text{dir})$ initialized to $(0, 1)$.
   - Loop Condition: $0 \le i < n$, stepping by $i \leftarrow i + \text{dir}$.
   - Transition:
     - If $s[i]$ is a letter: $\text{append}(s[i])$
     - Else: $i \leftarrow \text{link}[i]$ and $\text{dir} \leftarrow -\text{dir}$

---

### ✅ Why This Works

Each nested parenthesis flips the reading order of its internal substring. Crossing a parenthesis boundary transitions the current state to the opposite reading direction starting from the other side of that parenthesis boundary. 

By jumping from `'('` to `')'` (or vice versa) via `link[i]` and negating `dir`, the traversal seamlessly skips the bracket itself while resuming character collection in the exact reversed order required. Because characters are visited in their final output sequence directly, the output is constructed correctly in linear time without mutating any strings.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$
  - Building the `link` array with a stack takes $\mathcal{O}(N)$ time.
  - The second pass visits each non-bracket character once and jumps across each bracket pair twice in total, resulting in $\mathcal{O}(N)$ overall steps.

- **Space Complexity**: $\mathcal{O}(N)$
  - The `link` array uses $\mathcal{O}(N)$ space.
  - The `Stack` uses $\mathcal{O}(N)$ space to track bracket indices.
  - The `StringBuilder` stores the final output of length at most $N$.

---

### 🧠 DSA Pattern

- **Stack** (for bracket matching)
- **Wormhole / Teleportation Traversal** (Simulation using paired indices and direction flipping)

---

### ⚠️ Common Mistakes

1. **Misunderstanding `i = link[i]` + `i += dir`**:
   - When a bracket is hit, `i` jumps to `link[i]`, and then the `for` loop's `i += dir` immediately moves `i` one step further in the *new* direction. Forgetting that `i += dir` happens at the end of the loop iteration can cause misinterpretation of how characters right after brackets are visited.
2. **Infinite Loops on Manual Index Manipulation**:
   - Negating `dir` without jumping via `link[i]` would cause the loop to shuttle infinitely between the same characters.

---

### 🚀 Optimization Notes

- This algorithm is already **optimal** with $\mathcal{O}(N)$ time and $\mathcal{O}(N)$ space complexity.
- Minor Java optimization: `java.util.Stack` incurs synchronized overhead; replacing it with `int[]` or `ArrayDeque<Integer>` as a stack would reduce slight runtime overhead, though for $N \le 2000$ the current implementation executes well within time limits.
