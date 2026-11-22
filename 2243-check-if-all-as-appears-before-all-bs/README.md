<h2><a href="https://leetcode.com/problems/check-if-all-as-appears-before-all-bs">Check if All A's Appears Before All B's</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given a string <code>s</code> consisting of <strong>only</strong> the characters <code>&#39;a&#39;</code> and <code>&#39;b&#39;</code>, return <code>true</code> <em>if <strong>every</strong> </em><code>&#39;a&#39;</code> <em>appears before <strong>every</strong> </em><code>&#39;b&#39;</code><em> in the string</em>. Otherwise, return <code>false</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;aaabbb&quot;
<strong>Output:</strong> true
<strong>Explanation:</strong>
The &#39;a&#39;s are at indices 0, 1, and 2, while the &#39;b&#39;s are at indices 3, 4, and 5.
Hence, every &#39;a&#39; appears before every &#39;b&#39; and we return true.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;abab&quot;
<strong>Output:</strong> false
<strong>Explanation:</strong>
There is an &#39;a&#39; at index 2 and a &#39;b&#39; at index 1.
Hence, not every &#39;a&#39; appears before every &#39;b&#39; and we return false.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;bbb&quot;
<strong>Output:</strong> true
<strong>Explanation:</strong>
There are no &#39;a&#39;s, hence, every &#39;a&#39; appears before every &#39;b&#39; and we return true.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 100</code></li>
	<li><code>s[i]</code> is either <code>&#39;a&#39;</code> or <code>&#39;b&#39;</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks whether every `'a'` in the string appears before every `'b'`. Rephrasing this condition: **no `'a'` should ever appear after a `'b'` has already been seen**. 

Your solution uses a boolean flag to track whether a `'b'` has been encountered yet. As you scan the string from left to right, once you hit a `'b'`, you turn the flag on. If you ever see an `'a'` while that flag is active, you immediately know the order is invalid.

### 🔍 Approach

1. **State Tracking (`boolean f = false`)**:
   - The boolean variable `f` acts as a flag representing whether a `'b'` has been encountered so far in the string scan. It starts as `false`.

2. **Single Pass Iteration (`for (int i = 0; i < s.length(); i++)`)**:
   - You iterate through the string character by character from index `0` to `s.length() - 1`.

3. **Violation Check (`if (f && s.charAt(i) == 'a')`)**:
   - Inside the loop, you first check if `f` is `true` (meaning at least one `'b'` appeared at a smaller index) **and** the current character `s.charAt(i)` is `'a'`.
   - If both conditions are met, an `'a'` appears after a `'b'`, violating the requirement. You immediately return `false`.

4. **Flag Update (`if (s.charAt(i) == 'b') f = true;`)**:
   - If the current character is `'b'`, you set `f = true` to record that a `'b'` has now been seen for all subsequent characters.

5. **Final Return (`return true;`)**:
   - If the loop finishes without finding any invalid `'a'`, every `'a'` came before every `'b'` (or the string consisted purely of `'a'`s or `'b'`s). You return `true`.

### 🧩 Algorithm

This is a **Single-Pass State Machine** / **Flag-based Linear Scan**:

- **State**: `f` $\in \{\text{false}, \text{true}\}$
  - `f = false`: No `'b'` seen yet.
  - `f = true`: At least one `'b'` has been seen.
- **Invariant**: `f` is `true` if and only if there exists some index $j < i$ such that $s[j] = \text{'b'}$.
- **Transition / Rule**:
  - If $s[i] = \text{'a'}$ and $f = \text{true} \implies$ return `false` (invalid state).
  - If $s[i] = \text{'b'} \implies f = \text{true}$.

### ✅ Why This Works

The condition *"all 'a's appear before all 'b's"* is logically equivalent to *"there is no index $i < j$ such that $s[i] = \text{'b'}$ and $s[j] = \text{'a'}$"*.

By maintaining `f = true` after the first `'b'`, `f` serves as a memory of whether any preceding character was a `'b'`. Checking `f && s.charAt(i) == 'a'` for every character directly checks for the existence of an invalid pair $(i, j)$ where $s[i] = \text{'b'}$ and $s[j] = \text{'a'}$. Because you check this continuously across a single left-to-right pass, any violation is guaranteed to be detected.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(N)$, where $N$ is the length of the string `s`. You inspect each character at most once, and perform $\mathcal{O}(1)$ operations per character. The early return can make it even faster in practice for invalid strings.
- **Space Complexity:** $\mathcal{O}(1)$. You only use a single primitive boolean variable `f` and an integer loop index `i`, requiring constant additional memory.

### 🧠 DSA Pattern

- **State Tracking / Flag Maintenance**
- **Linear Scan (Single Pass)**

### ⚠️ Common Mistakes

1. **Changing Flag Position / Order Sensitivity**: 
   If someone sets `f = true` before checking if the character is `'a'`, it wouldn't affect this specific code because a single character cannot be both `'a'` and `'b'` simultaneously. However, in similar state-machine problems, updating state before validating can lead to false positives.
2. **Not Returning Early**: 
   Continuing the loop after detecting an invalid `'a'` is unnecessary. Your solution correctly exits immediately with `return false;`.

### 🚀 Optimization Notes

- **Optimal Time & Space:** This solution is already optimal in terms of time ($\mathcal{O}(N)$) and auxiliary space ($\mathcal{O}(1)$).
- **Readability Suggestion:** The variable name `f` could be renamed to something more descriptive like `seenB` or `hasSeenB` to make the code self-documenting for revision.
