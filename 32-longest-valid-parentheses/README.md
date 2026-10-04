<h2><a href="https://leetcode.com/problems/longest-valid-parentheses">Longest Valid Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>Given a string containing just the characters <code>&#39;(&#39;</code> and <code>&#39;)&#39;</code>, return <em>the length of the longest valid (well-formed) parentheses </em><span data-keyword="substring-nonempty"><em>substring</em></span>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;(()&quot;
<strong>Output:</strong> 2
<strong>Explanation:</strong> The longest valid parentheses substring is &quot;()&quot;.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;)()())&quot;
<strong>Output:</strong> 4
<strong>Explanation:</strong> The longest valid parentheses substring is &quot;()()&quot;.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;&quot;
<strong>Output:</strong> 0
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>0 &lt;= s.length &lt;= 3 * 10<sup>4</sup></code></li>
	<li><code>s[i]</code> is <code>&#39;(&#39;</code>, or <code>&#39;)&#39;</code>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of this solution is to use a **stack of indices** to track the boundaries of valid parentheses substrings.

Instead of pushing characters (`'('` or `')'`) onto the stack, pushing the **indices** allows us to easily calculate lengths of valid substrings using simple subtraction (`current_index - last_unmatched_boundary_index`).

By initializing the stack with `-1`, we establish a dummy boundary before the beginning of the string. Whenever a valid `")"` matches an `"("`, popping the matched index leaves the index of the last unmatched closing parenthesis (or the initial `-1`) at the top of the stack. The distance between the current index and that top element gives the length of the valid substring ending at the current index.

### 🔍 Approach

1. **Initialization**:
   - `res`: Tracks the length of the longest valid parentheses substring found so far, initialized to `0`.
   - `stack`: A stack of `Integer` indices.
   - `stack.push(-1)`: Pushes `-1` to serve as a base boundary for valid substrings starting at index `0`.

2. **Iterating Through the String**:
   - Loop `i` from `0` to `s.length() - 1`:
     - **If character is `'('`**:
       - Push index `i` onto `stack`. This acts as a potential start for a valid parentheses pair.
     - **If character is `')'`**:
       - Call `stack.pop()`. This pops either the index of the matching `'('` or the boundary marker.
       - **Check if `stack.isEmpty()`**:
         - If the stack is empty, it means this `')'` had no matching `'('` to pair with.
         - Push current index `i` onto `stack` to act as the new boundary for any future valid substrings.
       - **If stack is NOT empty**:
         - The current top of the stack (`stack.peek()`) represents the index right before the start of the current valid substring.
         - Compute the valid length as `i - stack.peek()`.
         - Update `res = Math.max(res, i - stack.peek())`.

3. **Return Result**:
   - Return `res` as the final maximum length.

### 🧩 Algorithm

- **Data Structure**: `Stack<Integer>` storing zero-based indices of characters.
- **Invariant**: The top of the stack after processing index `i` (when valid) is always the index of the character immediately preceding the current contiguous valid parentheses substring.
- **State Transitions**:
  - `s[i] == '('`: `stack.push(i)`
  - `s[i] == ')'`: 
    - `stack.pop()`
    - If `stack.isEmpty()`: `stack.push(i)` (reset boundary)
    - Else: `res = max(res, i - stack.peek())`

### ✅ Why This Works

- **Index Subtraction for Length**: If we match a pair, the length of the valid substring extending to index `i` is determined by how far back the continuous matching goes. Because matched `'('` indices are popped off, the element left directly beneath them is the boundary of the current valid segment.
- **Handling Unmatched `')'`**: When an unmatched `')'` is encountered, it resets the boundary because no valid substring can span across an invalid `')'`. Pushing `i` onto the stack serves as the new baseline index.
- **Base Boundary `-1`**: Starting with `-1` allows valid substrings starting at index `0` (e.g., `"()"` at `i = 1`) to be calculated correctly as `1 - (-1) = 2`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of the string `s`. The algorithm processes each character of the string exactly once in a single loop, performing $\mathcal{O}(1)$ stack operations (`push`, `pop`, `peek`) per iteration.
- **Space Complexity**: $\mathcal{O}(N)$ in the worst case (e.g., a string full of `'('` characters like `"((((("`), where the stack grows up to size $N + 1$.

### 🧠 DSA Pattern

- **Stack** (specifically tracking indices rather than values)

### ⚠️ Common Mistakes

1. **Forgetting the Initial Boundary (`-1`)**: Without pushing `-1` initially, calculating the length of a valid substring that starts at index `0` (like `"()"`) causes an issue because there is no reference boundary before index `0`.
2. **Not Handling Empty Stack After Pop**: Forgetting to check `stack.isEmpty()` after popping on a `')'` can lead to `EmptyStackException` when calling `stack.peek()`.
3. **Pushing Characters Instead of Indices**: Pushing character values `'('` onto the stack makes it difficult to compute the length of long nested or concatenated valid parentheses substrings in $\mathcal{O}(1)$ time.

### 🚀 Optimization Notes

- **Data Structure Overhead**: The code uses `java.util.Stack<Integer>`, which incurs synchronization overhead (since `Stack` extends `Vector`) as well as object boxing/unboxing overhead between `int` and `Integer`.
- **Alternative Implementation Detail**: Using a primitive integer array as a custom stack (e.g., `int[] stack = new int[n + 1]`) or `java.util.ArrayDeque<Integer>` would reduce memory overhead and avoid synchronized method calls, though the overall time complexity remains $\mathcal{O}(N)$.
