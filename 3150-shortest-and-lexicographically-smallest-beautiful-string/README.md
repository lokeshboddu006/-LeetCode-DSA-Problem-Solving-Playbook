<h2><a href="https://leetcode.com/problems/shortest-and-lexicographically-smallest-beautiful-string">Shortest and Lexicographically Smallest Beautiful String</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given a binary string <code>s</code> and a positive integer <code>k</code>.</p>

<p>A substring of <code>s</code> is <strong>beautiful</strong> if the number of <code>1</code>&#39;s in it is exactly <code>k</code>.</p>

<p>Let <code>len</code> be the length of the <strong>shortest</strong> beautiful substring.</p>

<p>Return <em>the lexicographically <strong>smallest</strong> beautiful substring of string </em><code>s</code><em> with length equal to </em><code>len</code>. If <code>s</code> doesn&#39;t contain a beautiful substring, return <em>an <strong>empty</strong> string</em>.</p>

<p>A string <code>a</code> is lexicographically <strong>larger</strong> than a string <code>b</code> (of the same length) if in the first position where <code>a</code> and <code>b</code> differ, <code>a</code> has a character strictly larger than the corresponding character in <code>b</code>.</p>

<ul>
	<li>For example, <code>&quot;abcd&quot;</code> is lexicographically larger than <code>&quot;abcc&quot;</code> because the first position they differ is at the fourth character, and <code>d</code> is greater than <code>c</code>.</li>
</ul>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;100011001&quot;, k = 3
<strong>Output:</strong> &quot;11001&quot;
<strong>Explanation:</strong> There are 7 beautiful substrings in this example:
1. The substring &quot;<u>100011</u>001&quot;.
2. The substring &quot;<u>1000110</u>01&quot;.
3. The substring &quot;<u>10001100</u>1&quot;.
4. The substring &quot;1<u>00011001</u>&quot;.
5. The substring &quot;10<u>0011001</u>&quot;.
6. The substring &quot;100<u>011001</u>&quot;.
7. The substring &quot;1000<u>11001</u>&quot;.
The length of the shortest beautiful substring is 5.
The lexicographically smallest beautiful substring with length 5 is the substring &quot;11001&quot;.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;1011&quot;, k = 2
<strong>Output:</strong> &quot;11&quot;
<strong>Explanation:</strong> There are 3 beautiful substrings in this example:
1. The substring &quot;<u>101</u>1&quot;.
2. The substring &quot;1<u>011</u>&quot;.
3. The substring &quot;10<u>11</u>&quot;.
The length of the shortest beautiful substring is 2.
The lexicographically smallest beautiful substring with length 2 is the substring &quot;11&quot;.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;000&quot;, k = 1
<strong>Output:</strong> &quot;&quot;
<strong>Explanation:</strong> There are no beautiful substrings in this example.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 100</code></li>
	<li><code>1 &lt;= k &lt;= s.length</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the shortest substring of `s` that contains exactly `k` ones. If there are multiple substrings with the minimal length, we need the one that is lexicographically smallest.

Given the small constraint ($N \le 100$), your implementation takes a direct brute-force approach with early stopping (pruning):
1. Test every possible substring starting at index `i` and ending at index `j`.
2. Keep count of how many `'1'`s are in the substring.
3. If the count exceeds `k`, stop expanding from index `i` because adding more characters will only keep or increase the number of `'1'`s.
4. Whenever the count is exactly `k`, compare this substring against the best answer found so far based on length and lexicographical order.

### 🔍 Approach

1. **Initialization**:
   - `ans`: Stores the best candidate string found so far, initialized to `""`.
   - `n`: Stores the length of the input string `s`.

2. **Outer Loop (`i` from `0` to `n - 1`)**:
   - Sets the starting index of the substring.
   - Resets `oneCnt = 0` and initializes a `StringBuilder cur` to accumulate characters for the current substring starting at `i`.

3. **Inner Loop (`j` from `i` to `n - 1`)**:
   - Expands the current substring character-by-character by appending `s.charAt(j)` to `cur`.
   - Increment `oneCnt` whenever `s.charAt(j) == '1'`.

4. **Pruning Condition**:
   - `if (oneCnt > k) break;`
   - Once `oneCnt` exceeds `k`, no further substring starting at `i` and ending at `j' > j` can ever have `k` ones. Breaking early avoids useless work.

5. **Updating the Result**:
   - `if (oneCnt == k)`:
     - Convert `cur` to a string (`curStr`).
     - Update `ans = curStr` if:
       - `ans` is currently empty (first valid string found), OR
       - `curStr.length() < ans.length()` (found a strictly shorter valid string), OR
       - `curStr.length() == ans.length()` and `curStr.compareTo(ans) < 0` (same length, but lexicographically smaller).

6. **Return**:
   - Returns `ans` (or `""` if no valid substring was ever found).

### 🧩 Algorithm

- **Type**: Brute Force Substring Search with Pruning / Branch Cutting.
- **Invariant**: At step `(i, j)`, `cur` contains `s[i...j]` and `oneCnt` accurately reflects the count of `'1'`s in `s[i...j]`.
- **Pruning Rule**: Stop inner loop when `oneCnt > k`.
- **Selection Rule**:
  $$\text{ans} = \min_{\substack{i \le j \\ \text{count}(s[i..j], \text{'1'}) = k}} \left( s[i..j] \right)$$
  where minimization compares length first, then lexicographical order.

### ✅ Why This Works

1. **Exhaustive Coverage**: By considering all start indices `i` and end indices `j`, every possible valid substring with `k` ones is evaluated unless pruned.
2. **Correctness of Pruning**: Appending characters to a binary substring can only increase or keep its count of `'1'`s. Once `oneCnt > k`, it can never decrease back to `k`. Therefore, breaking out of the inner loop discards no valid candidates.
3. **Correct Comparison**: The conditional update ensures that shorter strings are prioritized over longer strings, and for ties in length, `compareTo` guarantees picking the lexicographically smaller string.

### ⏱️ Complexity

- **Time Complexity**: $O(N^3)$ worst-case.
  - The nested loops execute $O(N^2)$ times.
  - Inside the inner loop, when `oneCnt == k`, calling `cur.toString()` and `curStr.compareTo(ans)` takes up to $O(N)$ string comparison/copying time.
  - For $N \le 100$, $N^3 \approx 10^6$ operations, which easily passes well within the time limit.

- **Space Complexity**: $O(N)$ auxiliary space.
  - `StringBuilder cur` and `String curStr` can store strings up to length $N$.
  - `ans` stores a string of length at most $N$.

### 🧠 DSA Pattern

- **Brute Force Substring Generation** with **Pruning**
- **Two Loops / Nested Traversal**

### ⚠️ Common Mistakes

1. **Forgetting to break when `oneCnt > k`**: Without the `break`, the loop would continue scanning to the end of the string, wasting operations.
2. **Incorrect comparison order**: Checking `compareTo` before checking string lengths would lead to incorrect results (e.g., comparing `"100"` with `"11"` lexicographically rather than by length). Your code correctly checks length first.
3. **Modifying `ans` on `oneCnt >= k`**: Updating `ans` when `oneCnt > k` would incorporate invalid substrings.

### 🚀 Optimization Notes

- **Avoiding leading zeros**: Any substring starting with `'0'` and containing `k` ones can be trimmed from the left to remove the leading `'0'`. The trimmed version will have the same number of `'1'`s (`k`) but a strictly shorter length. Therefore, you could skip starting the outer loop at `i` if `s.charAt(i) == '0'`.
- **Sliding Window Alternative**: While your $O(N^3)$ solution easily passes for $N \le 100$, the problem can also be tracked using a two-pointer / sliding window approach to run in $O(N^2)$ or $O(N)$ time by maintaining the indices of `'1'`s. However, for $N \le 100$, your current code is concise and straightforward.
