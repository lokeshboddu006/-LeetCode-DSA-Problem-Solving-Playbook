<h2><a href="https://leetcode.com/problems/longest-substring-without-repeating-characters">Longest Substring Without Repeating Characters</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>Given a string <code>s</code>, find the length of the <strong>longest</strong> <span data-keyword="substring-nonempty"><strong>substring</strong></span> without duplicate characters.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;abcabcbb&quot;
<strong>Output:</strong> 3
<strong>Explanation:</strong> The answer is &quot;abc&quot;, with the length of 3. Note that <code>&quot;bca&quot;</code> and <code>&quot;cab&quot;</code> are also correct answers.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;bbbbb&quot;
<strong>Output:</strong> 1
<strong>Explanation:</strong> The answer is &quot;b&quot;, with the length of 1.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> s = &quot;pwwkew&quot;
<strong>Output:</strong> 3
<strong>Explanation:</strong> The answer is &quot;wke&quot;, with the length of 3.
Notice that the answer must be a substring, &quot;pwke&quot; is a subsequence and not a substring.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>0 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s</code> consists of English letters, digits, symbols and spaces.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The key observation in your solution is that as you expand the right boundary of a window across the string, any repeating character requires moving the left boundary forward. 

Instead of moving the `left` pointer incrementally one character at a time using a inner loop, your implementation **jumps** the `left` pointer directly past the previous occurrence of the duplicated character. You achieve this by keeping track of the **most recent index** of every character seen so far in a hash map.

### 🔍 Approach

1. **State Initialization**:
   - `map`: A `HashMap<Character, Integer>` that maps each character to its most recently seen index in the string.
   - `maxLength`: Stores the maximum length of a valid substring found so far, initialized to `0`.
   - `left`: The start index of the current valid window, initialized to `0`.

2. **Window Expansion (`for` loop)**:
   - The `right` pointer iterates through the string index by index (`0` to `s.length() - 1`).
   - At each iteration, you retrieve the current character: `currentChar = s.charAt(right)`.

3. **Updating the Left Boundary**:
   - You check if `currentChar` is already in the map using `map.containsKey(currentChar)`.
   - If it exists, its previous index was `map.get(currentChar)`.
   - You update `left` using `Math.max(left, map.get(currentChar) + 1)`. Using `Math.max` ensures that `left` only moves forward and never moves backward if the duplicate character was seen before the current `left` boundary.

4. **Map Update & Length Calculation**:
   - You store/overwrite the character's current position: `map.put(currentChar, right)`.
   - You calculate the current window length as `right - left + 1` and update `maxLength` if this window is larger than the previous maximum.

5. **Return Result**:
   - After the loop finishes inspecting all characters, `maxLength` is returned.

### 🧩 Algorithm

- **Pattern**: Variable-size Sliding Window using a Hash Map for index lookup.
- **Window Invariant**: At the end of each iteration of `right`, the window `s[left ... right]` contains no duplicate characters.
- **State Transition for Window**:
  - When character $s[\text{right}]$ is processed:
    $$\text{left}_{\text{new}} = \max(\text{left}_{\text{old}}, \text{map}[s[\text{right}]] + 1)$$
    $$\text{maxLength}_{\text{new}} = \max(\text{maxLength}_{\text{old}}, \text{right} - \text{left}_{\text{new}} + 1)$$

### ✅ Why This Works

1. **No Missed Valid Substrings**: The `right` pointer examines every possible right boundary from `0` to `s.length() - 1`. For each `right`, the code calculates the minimum possible `left` index such that no duplicate exists in `s[left ... right]`.
2. **Monotonic `left` Pointer**: The `Math.max(left, map.get(currentChar) + 1)` operation guarantees that `left` never retreats. If a duplicate character's previous index is less than `left` (meaning it's already outside the active window), `left` stays unchanged.
3. **Correct Window Size**: The length of any zero-indexed window bounded by `[left, right]` inclusive is calculated as `right - left + 1`.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(N)$, where $N$ is the length of the string `s`.
  - The `right` pointer traverses the string in a single loop from `0` to $N - 1$.
  - Hash map operations (`containsKey`, `get`, `put`) execute in $\mathcal{O}(1)$ average time.
- **Space Complexity**: $\mathcal{O}(\min(N, M))$, where $N$ is the string length and $M$ is the size of the character set (ASCII/Unicode).
  - In the worst case, the hash map stores each unique character present in the input string.

### 🧠 DSA Pattern

- **Sliding Window**: Moving `left` and `right` pointers to maintain a dynamic range representing a valid substring.
- **Hashing**: Mapping `Character` to `Integer` (index) to perform $\mathcal{O}(1)$ jump lookups for the `left` pointer.

### ⚠️ Common Mistakes

1. **Forgetting `Math.max` when updating `left`**:
   - If you write `left = map.get(currentChar) + 1` without `Math.max`, `left` can move **backward** if an old duplicate character lies outside the current window.
   - Example: For `"abba"`, when reaching the second `'a'` at index 3, `map.get('a') + 1` would evaluate to `1`. But `left` is already at `2` (due to the duplicate `'b'`). Without `Math.max`, `left` would jump back to `1`, creating an invalid window containing two `'b'`s.

2. **Off-by-one errors in window length**:
   - Calculating window size as `right - left` instead of `right - left + 1`.

### 🚀 Optimization Notes

- **Object Autoboxing Overhead**:
  - `HashMap<Character, Integer>` incurs memory overhead and object boxing/unboxing costs for primitives `char` and `int`.
- **Fixed-Size Array as Direct Address Table**:
  - Since standard character sets (like ASCII or Extended ASCII) have a small bounded size, an `int[128]` or `int[256]` array initialized with `-1` can replace `HashMap` directly. This eliminates heap allocations and hash overhead while keeping the exact same two-pointer logic.
