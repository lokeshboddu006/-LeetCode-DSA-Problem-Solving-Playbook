<h2><a href="https://leetcode.com/problems/find-the-distance-value-between-two-arrays">Find the Distance Value Between Two Arrays</a></h2> <img src='https://img.shields.io/badge/Difficulty-Easy-brightgreen' alt='Difficulty: Easy' /><hr><p>Given two integer arrays <code>arr1</code> and <code>arr2</code>, and the integer <code>d</code>, <em>return the distance value between the two arrays</em>.</p>

<p>The distance value is defined as the number of elements <code>arr1[i]</code> such that there is not any element <code>arr2[j]</code> where <code>|arr1[i]-arr2[j]| &lt;= d</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2
<strong>Output:</strong> 2
<strong>Explanation:</strong> 
For arr1[0]=4 we have: 
|4-10|=6 &gt; d=2 
|4-9|=5 &gt; d=2 
|4-1|=3 &gt; d=2 
|4-8|=4 &gt; d=2 
For arr1[1]=5 we have: 
|5-10|=5 &gt; d=2 
|5-9|=4 &gt; d=2 
|5-1|=4 &gt; d=2 
|5-8|=3 &gt; d=2
For arr1[2]=8 we have:
<strong>|8-10|=2 &lt;= d=2</strong>
<strong>|8-9|=1 &lt;= d=2</strong>
|8-1|=7 &gt; d=2
<strong>|8-8|=0 &lt;= d=2</strong>
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> arr1 = [1,4,2,3], arr2 = [-4,-3,6,10,20,30], d = 3
<strong>Output:</strong> 2
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> arr1 = [2,1,100,3], arr2 = [-5,-2,10,-3,7], d = 6
<strong>Output:</strong> 1
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= arr1.length, arr2.length &lt;= 500</code></li>
	<li><code>-1000 &lt;= arr1[i], arr2[j] &lt;= 1000</code></li>
	<li><code>0 &lt;= d &lt;= 100</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The condition $|a - b| \le d$ for an element $a \in \text{arr1}$ and $b \in \text{arr2}$ can be rewritten as:
$$a - d \le b \le a + d$$

To determine if an element $a$ from `arr1` satisfies the problem's condition, we need to verify that **no element $b$ in `arr2` falls inside the range $[a - d, a + d]$**.

Instead of comparing every element $a$ against every element $b$ in $O(N \times M)$ time, your solution converts `arr2` into a ordered set (`TreeSet`). For each value $a$, you search for the *smallest element in `arr2` that is at least $a - d$* using `TreeSet.ceiling(a - d)`. If this element doesn't exist, or if it lies strictly above $a + d$, then no element in `arr2` falls within $[a - d, a + d]$.

---

### 🔍 Approach

1. **Populate the TreeSet**:
   - Initialize a `TreeSet<Integer>` named `sorted`.
   - Iterate through `arr2` and insert each value into `sorted`. `TreeSet` automatically removes duplicates and maintains the elements in sorted order (using a Red-Black tree under the hood).

2. **Stream and Filter `arr1`**:
   - Use `Arrays.stream(arr1).filter(...)` to test each element `a` in `arr1`.

3. **Check Interval Condition**:
   - For a given `a`, call `sorted.ceiling(a - d)`. This returns the smallest element $b \in \text{arr2}$ such that $b \ge a - d$.
   - **Condition Evaluation**:
     - If `b == null`: There is no element in `arr2` that is $\ge a - d$, which means no element can fall in $[a - d, a + d]$.
     - If `b > a + d`: The smallest element $\ge a - d$ is already strictly greater than $a + d$. Therefore, no element in `arr2` lies in the range $[a - d, a + d]$.
   - If either condition holds, the predicate returns `true` (meaning `a` contributes to the distance value count).

4. **Count and Return**:
   - Count the total number of valid elements in `arr1` using `.count()` and cast the result to `int`.

---

### 🧩 Algorithm

1. **Build Balanced BST**:
   - Insert all $M$ elements of `arr2` into a `TreeSet`.

2. **Binary Search Range Check**:
   - For each element $a \in \text{arr1}$:
     - Query $b = \text{ceiling}(a - d)$.
     - Return `true` if $b$ is `null` or $b > a + d$.
     - Return `false` otherwise.

3. **Aggregate**:
   - Sum up the number of elements in `arr1` returning `true`.

---

### ✅ Why This Works

If there exists any element $x \in \text{arr2}$ such that $|a - x| \le d$, then $x \in [a - d, a + d]$.

- `ceiling(a - d)` finds the smallest element $b \in \text{arr2}$ satisfying $b \ge a - d$.
- If $b$ exists and $b \le a + d$, then $b$ is inside $[a - d, a + d]$, violating the condition for $a$.
- If $b$ does not exist ($b == \text{null}$), then every element in `arr2` is $< a - d$, so no element is in $[a - d, a + d]$.
- If $b > a + d$, then because $b$ is the smallest element $\ge a - d$, all elements in `arr2` are either $< a - d$ or $> a + d$. Thus, no element in `arr2` lies in $[a - d, a + d]$.

Hence, `b == null || b > a + d` correctly identifies when $a$ has no elements in `arr2` within distance $d$.

---

### ⏱️ Complexity

- **Time Complexity:** 
  - Building `TreeSet`: Inserting $M$ elements into a `TreeSet` takes $O(M \log M)$ time.
  - Processing `arr1`: For each of the $N$ elements in `arr1`, `ceiling()` takes $O(\log M)$ time.
  - Total Time Complexity: $O((N + M) \log M)$, where $N$ is the length of `arr1` and $M$ is the length of `arr2`.

- **Space Complexity:** 
  - $O(M)$ extra space to store the unique elements of `arr2` inside the `TreeSet`.

---

### 🧠 DSA Pattern

- **Binary Search Tree / Binary Search**: Using `TreeSet` (a Red-Black Tree in Java) to perform logarithmic range/ceiling lookup operations.
- **Functional Programming / Streams**: Using Java Streams API (`Arrays.stream().filter().count()`) to compute the final result concisely.

---

### ⚠️ Common Mistakes

1. **Confusing `ceiling` with `higher`**:
   - `ceiling(x)` returns the smallest element $\ge x$.
   - `higher(x)` returns the smallest element $> x$.
   - Using `higher(a - d)` would miss the edge case where an element in `arr2` is exactly equal to $a - d$.

2. **Null Checks**:
   - `TreeSet.ceiling()` returns `null` if no element in the tree is $\ge$ the search key. Forgetting to check `b == null` before comparing `b > a + d` would throw a `NullPointerException` due to unboxing `Integer` to `int`.

3. **Assuming `TreeSet` presumes duplicate logic**:
   - `TreeSet` automatically deduplicates identical elements from `arr2`. In this specific problem, duplicates do not change whether a valid distance exists in range $[a - d, a + d]$, so deduplication is harmless and safe.

---

### 🚀 Optimization Notes

- **Object Boxing Overhead**: Using `TreeSet<Integer>` creates object overhead (boxing primitives `int` to `Integer`) and pointer navigation overhead during tree traversals.
- **Alternative Implementation within the Same Idea**: Instead of a `TreeSet`, sorting a standard `int[]` array (`Arrays.sort(arr2)`) and performing `Arrays.binarySearch()` or a custom binary search avoids object allocation and tree node overhead, while retaining the $O((N + M) \log M)$ binary search approach.
- **Stream Overhead**: Java Streams introduce minor performance overhead compared to a plain `for` loop, though for the given constraints ($N, M \le 500$), this overhead is negligible.
