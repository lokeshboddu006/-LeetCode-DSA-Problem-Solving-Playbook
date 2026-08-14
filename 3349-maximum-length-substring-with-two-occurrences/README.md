<h2><a href="https://leetcode.com/problems/maximum-length-substring-with-two-occurrences">Maximum Length Substring With Two Occurrences</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr>Given a string <code>s</code>, return the <strong>maximum</strong> length of a <span data-keyword="substring">substring</span>&nbsp;such that it contains <em>at most two occurrences</em> of each character.
<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;bcbbbcba&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">4</span></p>

<p><strong>Explanation:</strong></p>
The following substring has a length of 4 and contains at most two occurrences of each character: <code>&quot;bcbb<u>bcba</u>&quot;</code>.</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = &quot;aaaa&quot;</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>
The following substring has a length of 2 and contains at most two occurrences of each character: <code>&quot;<u>aa</u>aa&quot;</code>.</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>2 &lt;= s.length &lt;= 100</code></li>
	<li><code>s</code> consists only of lowercase English letters.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core idea of your solution is to use a **sliding window** defined by two pointers, `l` (left) and `r` (right), while maintaining a frequency count of characters within the current window `s[l...r]`.

As the right pointer `r` expands the window by incorporating one character at a time, only the frequency of that newly added character (`s.charAt(r)`) can potentially violate the constraint of having at most 2 occurrences. If its count exceeds 2, you shrink the window from the left by advancing `l` until the count of `s.charAt(r)` drops back to 2. At every step where the window is valid, you update the maximum length observed so far.

### 🔍 Approach

1. **Frequency Array Initialization**:
   - `int[] arr = new int[26];`: Uses a fixed-size integer array of size 26 to store the count of each lowercase letter ('a' through 'z') in the current window.

2. **Sliding Window Traversal**:
   - `int m = 0;`: Stores the maximum window length found.
   - `for (int r = 0, l = 0; r < s.length(); r++)`: Iterates through the string with `r` as the right boundary while maintaining `l` as the left boundary.

3. **Window Expansion & Constraint Check**:
   - `arr[s.charAt(r) - 'a']++;`: Increments the count of the character at index `r`.
   - `while (arr[s.charAt(r) - 'a'] > 2)`: Checks if the character just added broke the rule (i.e., appears more than twice).
     - Inside the loop, `arr[s.charAt(l) - 'a']--;` decrements the count of the character leaving the window at index `l`.
     - `l++;` moves the left pointer forward.

4. **Length Calculation**:
   - `m = Math.max(m, r - l + 1);`: After restoring a valid window, calculates the current window length (`r - l + 1`) and keeps the maximum.

5. **Result**:
   - Returns `m`, which represents the maximum valid substring length.

### 🧩 Algorithm

- **Data Structure**: Array `arr` of size 26 for $O(1)$ character frequency lookup.
- **Invariant**: At the end of each iteration of the outer loop, the substring `s[l...r]` contains at most 2 occurrences of any character.
- **Shrinking Condition**: `arr[s.charAt(r) - 'a'] > 2`.
- **Window Length**: `r - l + 1`.

### ✅ Why This Works

- **Targeted Validation**: When extending the window to `r`, only `s.charAt(r)` has its frequency increased. Therefore, no other character's count can exceed 2 in this step. Checking only `arr[s.charAt(r) - 'a'] > 2` is both necessary and sufficient to detect an invalid state.
- **Correct Window Shrinking**: Moving `l` rightward and decrementing character frequencies guarantees that `s.charAt(r)`'s count will eventually drop back to 2, restoring validity.
- **Max Length Guarantee**: Because `r` visits every possible right endpoint and `l` is kept as small as possible for each `r`, every maximal valid substring ending at `r` is evaluated.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of string `s`.
  - The right pointer `r` increments $N$ times.
  - The left pointer `l` increments at most $N$ times throughout the entire execution.
  - Each character is added to `arr` once and removed at most once.

- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space.
  - The array `arr` has a constant size of 26 regardless of the input string length $N$.

### 🧠 DSA Pattern

- **Sliding Window / Two Pointers**: Dynamically expands and contracts a continuous range `[l, r]` to satisfy a frequency constraint while maximizing the window size.

### ⚠️ Common Mistakes

1. **Incorrect Order in Shrinking Loop**: Decrementing `l` before reducing the frequency array (`l++` before `arr[...]--`) would cause the wrong character's count to be decremented, leading to incorrect window states and index errors.
2. **Using `if` Instead of `while`**: If the frequency could jump by more than 1 per step (though not possible here since `r` moves by 1), an `if` statement would fail. Using `while` ensures the invariant is fully restored before recording `m`.
3. **Off-by-one in Substring Length**: Forgetting the `+ 1` in `r - l + 1` when calculating window size.

### 🚀 Optimization Notes

- **Current Status**: Your solution is already time and space optimal ($\mathcal{O}(N)$ time, $\mathcal{O}(1)$ space).
- **Minor Micro-optimizations**:
  - Store `s.charAt(r) - 'a'` in a local variable (e.g., `int c = s.charAt(r) - 'a';`) inside the loop to avoid calling `s.charAt(r)` and recomputing the index multiple times per iteration.
  - Convert string to a character array (`char[] chs = s.toCharArray()`) before looping to avoid repeated bounds checks from `s.charAt()`.
