<h2><a href="https://leetcode.com/problems/generate-parentheses">Generate Parentheses</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given <code>n</code> pairs of parentheses, write a function to <em>generate all combinations of well-formed parentheses</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<pre><strong>Input:</strong> n = 3
<strong>Output:</strong> ["((()))","(()())","(())()","()(())","()()()"]
</pre><p><strong class="example">Example 2:</strong></p>
<pre><strong>Input:</strong> n = 1
<strong>Output:</strong> ["()"]
</pre>
<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 8</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The solution generates valid parenthesis combinations using Depth-First Search (DFS) with backtracking.

Instead of generating all possible combinations of length $2n$ and filtering out invalid ones, the code builds valid strings incrementally:
1. It pre-places the first open bracket `"("` at the very beginning and reserves the last close bracket `")"` for the base case.
2. For $n$ pairs, this leaves $n - 1$ open brackets and $n - 1$ close brackets to be placed during the recursive search.
3. The variables `O` and `C` track the **remaining** open and close brackets to place.
4. To maintain a valid prefix at every step, a closing bracket is only added when the remaining close count `C` is greater than or equal to the remaining open count `O` (`C >= O`). This mathematical condition ensures we never place more closing brackets than opening brackets.

---

### 🔍 Approach

1. **Edge Case & Parameter Setup**:
   - `if (n-- == 1) return List.of("()");`: Handle $n = 1$ directly. Note that `n--` post-decrements `n`, so for $n > 1$, `n` becomes $n - 1$.
   - The initial recursive call `dfs(n, n, "(")` passes $n - 1$ remaining open brackets, $n - 1$ remaining close brackets, and the starting string `"("`.

2. **Recursive Traversal (`dfs`)**:
   - **Base Case**: When `O == 0 && C == 0`, all intermediate brackets have been placed. The code appends the final reserved `")"` (`res.add(s + ")")`) and returns.
   - **Branch 1 (Add Open Bracket)**: If `O > 0`, it place an open bracket `"("` by calling `dfs(O - 1, C, s + "(")`.
   - **Branch 2 (Add Close Bracket)**: If `C >= O`, it places a close bracket `")"` by calling `dfs(O, C - 1, s + ")")`.

3. **Implicit Backtracking**:
   - String concatenation (`s + "("` and `s + ")"`) creates new `String` instances per call, so the code does not need manual cleanup steps after returning from recursive branches.

---

### 🧩 Algorithm

- **Data Structure**: Recursion call stack and `ArrayList<String>` for storing results.
- **State Representation**: `dfs(O, C, s)` where:
  - `O`: Remaining open brackets to insert in intermediate steps.
  - `C`: Remaining close brackets to insert in intermediate steps.
  - `s`: Current prefix string built so far.
- **Invariants**:
  - Total open brackets placed so far in `s` = $1 + ((n_{\text{orig}} - 1) - O) = n_{\text{orig}} - O$.
  - Total close brackets placed so far in `s` = $(n_{\text{orig}} - 1) - C$.
  - Condition `C >= O` guarantees that $\text{open placed} > \text{close placed}$, preserving valid parenthesis balance throughout building `s`.

---

### ✅ Why This Works

A sequence of parentheses is valid if and only if at any prefix, $\text{count}("(") \ge \text{count}(")")$, and at the end, $\text{count}("(") == \text{count}(")")$.

In this implementation:
- Initial state has 1 open bracket placed and 1 close bracket saved for the end.
- At any point in `dfs`, open placed is $n_{\text{orig}} - O$ and close placed is $n_{\text{orig}} - 1 - C$.
- To place another close bracket, we require:
  $$\text{open placed} > \text{close placed}$$
  $$n_{\text{orig}} - O > n_{\text{orig}} - 1 - C \implies -O > -1 - C \implies C \ge O$$
- The condition `if (C >= O)` strictly enforces that a close bracket is added only when there is an unmatched open bracket available. This guarantees every string reaching the base case is well-formed.

---

### ⏱️ Complexity

- **Time Complexity:** $O\left(\frac{1}{n+1} \binom{2n}{n} \cdot n\right)$ or $O\left(\frac{4^n}{\sqrt{n}}\right)$.
  - The number of valid parenthesis combinations generated is the $n$-th Catalan number $C_n = \frac{1}{n+1}\binom{2n}{n}$.
  - Copying/building strings of length $2n$ at the leaf nodes takes $O(n)$ time per combination.
- **Space Complexity:** $O(n)$ auxiliary stack space.
  - The recursion tree depth is at most $2n - 2$.
  - String concatenation creates temporary `String` instances along each path of length $O(n)$.

---

### 🧠 DSA Pattern

- **Backtracking / DFS (Depth-First Search)**
- **State Pruning** (pruning invalid branches via `C >= O`)

---

### ⚠️ Common Mistakes

1. **Misunderstanding `n-- == 1`**: The post-decrement modifies `n` in place during the condition check. If $n = 3$, after the condition `n` becomes `2`, which represents $n - 1$ remaining brackets.
2. **Confusing Remaining Counts with Placed Counts**: `O` and `C` are **remaining** counts, not consumed counts. This is why the condition `C >= O` allows adding a closing bracket (it means fewer
