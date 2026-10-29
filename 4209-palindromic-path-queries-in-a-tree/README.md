<h2><a href="https://leetcode.com/problems/palindromic-path-queries-in-a-tree">Palindromic Path Queries in a Tree</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You are given an undirected tree with <code>n</code> nodes labeled 0 to <code>n - 1</code>. This is represented by a 2D array <code>edges</code> of length <code>n - 1</code>, where <code>edges[i] = [u<sub>i</sub>, v<sub>i</sub>]</code> indicates an undirected edge between nodes <code>u<sub>i</sub></code> and <code>v<sub>i</sub></code>.</p>

<p>You are also given a string <code>s</code> of length <code>n</code> consisting of lowercase English letters, where <code>s[i]</code> represents the character assigned to node <code>i</code>.</p>

<p>You are also given a string array <code>queries</code>, where each <code>queries[i]</code> is either:</p>

<ul>
	<li><code>&quot;update u<sub>i</sub> c&quot;</code>: Change the character at node <code>u<sub>i</sub></code> to <code>c</code>. Formally, update <code>s[u<sub>i</sub>] = c</code>.</li>
	<li><code>&quot;query u<sub>i</sub> v<sub>i</sub>&quot;</code>: Determine whether the string formed by the characters on the <strong>unique</strong> path from <code>u<sub>i</sub></code> to <code>v<sub>i</sub></code> (inclusive) can be <strong>rearranged</strong> into a <strong><span data-keyword="palindrome-string">palindrome</span></strong>.</li>
</ul>

<p>Return a boolean array <code>answer</code>, where <code>answer[j]</code> is <code>true</code> if the <code>j<sup>th</sup></code> query of type <code>&quot;query u<sub>i</sub> v<sub>i</sub>&quot;​​​​​​​</code> can be rearranged into a <strong>palindrome</strong>, and <code>false</code> otherwise.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 3, edges = [[0,1],[1,2]], s = &quot;aac&quot;, queries = [&quot;query 0 2&quot;,&quot;update 1 b&quot;,&quot;query 0 2&quot;]</span></p>

<p><strong>Output:</strong> <span class="example-io">[true,false]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li><code>&quot;query 0 2&quot;</code>: Path <code>0 &rarr; 1 &rarr; 2</code> gives <code>&quot;aac&quot;</code>, which can be rearranged to form <code>&quot;aca&quot;</code>, a palindrome. Thus, <code>answer[0] = true</code>.</li>
	<li><code>&quot;update 1 b&quot;</code>: Update node 1 to <code>&#39;b&#39;</code>, now <code>s = &quot;abc&quot;</code>.</li>
	<li><code>&quot;query 0 2&quot;</code>: Path characters are <code>&quot;abc&quot;</code>, which cannot be rearranged to form a palindrome. Thus, <code>answer[1] = false</code>.</li>
</ul>

<p>Thus, <code>answer = [true, false]</code>.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 4, edges = [[0,1],[0,2],[0,3]], s = &quot;abca&quot;, queries = [&quot;query 1 2&quot;,&quot;update 0 b&quot;,&quot;query 2 3&quot;,&quot;update 3 a&quot;,&quot;query 1 3&quot;]</span></p>

<p><strong>Output:</strong> <span class="example-io">[false,false,true]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li><code>&quot;query 1 2&quot;</code>: Path <code>1 &rarr; 0 &rarr; 2</code> gives <code>&quot;bac&quot;</code>, which cannot be rearranged to form a palindrome. Thus, <code>answer[0] = false</code>.</li>
	<li><code>&quot;update 0 b&quot;</code>: Update node 0 to <code>&#39;b&#39;</code>, now <code>s = &quot;bbca&quot;</code>.</li>
	<li><code>&quot;query 2 3&quot;</code>: Path <code>2 &rarr; 0 &rarr; 3</code> gives <code>&quot;cba&quot;</code>, which cannot be rearranged to form a palindrome. Thus, <code>answer[1] = false</code>.</li>
	<li><code>&quot;update 3 a&quot;</code>: Update node 3 to <code>&#39;a&#39;</code>, <code>s = &quot;bbca&quot;</code>.</li>
	<li><code>&quot;query 1 3&quot;</code>: Path <code>1 &rarr; 0 &rarr; 3</code> gives <code>&quot;bba&quot;</code>, which can be rearranged to form <code>&quot;bab&quot;</code>, a palindrome. Thus, <code>answer[2] = true</code>.</li>
</ul>

<p>Thus, <code>answer = [false, false, true]</code>.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == s.length &lt;= 5 * 10<sup>4</sup></code></li>
	<li><code>edges.length == n - 1</code></li>
	<li><code>edges[i] = [u<sub>i</sub>, v<sub>i</sub>]</code></li>
	<li><code>0 &lt;= u<sub>i</sub>, v<sub>i</sub> &lt;= n - 1</code></li>
	<li><code>s</code> consists of lowercase English letters.</li>
	<li>The input is generated such that <code>edges</code> represents a valid tree.</li>
	<li><code>1 &lt;= queries.length &lt;= 5 * 10<sup>4</sup></code>​​​​​​​
	<ul>
		<li><code>queries[i] = &quot;update u<sub>i</sub> c&quot;</code> or</li>
		<li><code>queries[i] = &quot;query u<sub>i</sub> v<sub>i</sub>&quot;</code></li>
		<li><code>0 &lt;= u<sub>i</sub>, v<sub>i</sub> &lt;= n - 1</code></li>
		<li><code>c</code> is a lowercase English letter.</li>
	</ul>
	</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

To determine if the characters along a tree path can be rearranged into a palindrome, we don't need to know the exact sequence of characters—only the **parity** (even or odd) of each character's count:
1. A set of characters can form a palindrome if and only if **at most one** character appears an odd number of times.
2. Since there are 26 lowercase English letters, the parity of character frequencies from the root to any node $u$ can be represented as a **26-bit bitmask**, where the $k$-th bit is `1` if character $k$ occurs an odd number of times, and `0` if even.

When querying the path between $u$ and $v$:
- The path mask is $mask(root \to u) \oplus mask(root \to v) \oplus mask(LCA(u, v))$.
- Nodes above $LCA(u, v)$ appear in both paths and cancel out under XOR.
- The $LCA(u, v)$ itself cancels out in $mask(root \to u) \oplus mask(root \to v)$, so we XOR its character bit back in manually.

When updating the character at node $u$:
- All nodes in $u$'s subtree have their root-to-node path bitmasks flipped by the difference between the old and new character bits.
- Using a DFS entry/exit time order (Euler tour), $u$'s subtree corresponds to a contiguous range $[in[u], out[u]]$.
- Range updates on this linear array are maintained efficiently using **Sqrt Decomposition** with lazy propagation arrays.

---

### 🔍 Approach

1. **Tree Traversal & Euler Tour (`dfs`)**:
   - Computes `in[u]` and `out[out]` entry/exit times to linearize subtrees into intervals.
   - Builds a binary lifting table `up[u][i]` for $O(\log N)$ Lowest Common Ancestor (LCA) queries.
   - Computes `pm[in[u]]`: the root-to-$u$ parity bitmask.

2. **Sqrt Decomposition Setup**:
   - Computes block size `bs = sqrt(n) + 1`.
   - Allocates `bl[]` array to store lazy XOR tags for full blocks.

3. **Handling Updates (`"update u c"`)**:
   - Calculates the bit difference: `diff = (1 << (ch[u] - 'a')) ^ (1 << (c - 'a'))`.
   - Modifies the range $[in[u], out[u]]$ in the linearized tree array:
     - Full blocks inside the range get `bl[b] ^= diff`.
     - Partial blocks at the boundaries loop element-by-element and XOR `diff` into `pm[i]`.
   - Updates `ch[u] = c` in the local character array.

4. **Handling Queries (`"query u v"`)**:
   - Finds $LCA(u, v)$ using binary lifting via `lca(u, v)`.
   - Retrieves the path mask for $u$ as `pm[in[u]] ^ bl[in[u] / bs]` and for $v$ as `pm[in[v]] ^ bl[in[v] / bs]`.
   - Combines them with $LCA$'s current character mask: `mask = mu ^ mv ^ (1 << (ch[lca] - 'a'))`.
   - Checks if `(mask & (mask - 1)) == 0` (i.e., at most 1 bit set).

---

### 🧩 Algorithm

#### Bitmask Parity Bitwise Property
- Character $c \in ['a' \dots 'z']$ mapped to bit shift `1 << (c - 'a')`.
- Bitwise XOR maintains frequency parity: $0 \oplus b = b$, $b \oplus b = 0$.

#### Path XOR Identity
$$\text{PathMask}(u, v) = \text{RootMask}(u) \oplus \text{RootMask}(v) \oplus (1 \ll (s[LCA(u, v)] - 'a'))$$

#### Subtree Range Mapping
- Subtree of node $u \iff$ index range $[in[u], out[u]]$ in DFS order.

#### Sqrt Decomposition Maintenance
- Point value at index $i$: $\text{Val}(i) = pm[i] \oplus bl[i / bs]$.
- Range update on $[L, R]$ with $\Delta$:
  - Full blocks $b \in (\text{block}(L), \text{block}(R))$: $bl[b] \leftarrow bl[b] \oplus \Delta$.
  - Partial blocks at ends: $pm[i] \leftarrow pm[i] \oplus \Delta$.

---

### ✅ Why This Works

1. **Path Parity Property**:
   - Ancestors strictly above $LCA(u, v)$ are visited twice when taking $(root \to u) \oplus (root \to v)$, so their bits cancel out to `0`.
   - Nodes strictly inside the path from $u$ to $LCA$ and $v$ to $LCA$ are included once.
   - $LCA(u, v)$ is included twice in $(root \to u) \oplus (root \to v)$ (so it cancels out); XORing $s[LCA]$ once more restores its contribution.

2. **Subtree Update Correctness**:
   - DFS entry/exit times ensure that every descendant $v$ of $u$ satisfies $in[u] \le in[v] \le out[u]$.
   - XORing `diff` over $[in[u], out[u]]$ updates $root \to v$ mask for every $v$ in $u$'s subtree simultaneously.

3. **At Most One Odd Frequency Check**:
   - `mask & (mask - 1)` drops the lowest set bit of `mask`.
   - If `mask & (mask - 1) == 0`, `mask` has either 0 set bits (all even counts) or 1 set bit (exactly one odd count), which guarantees characters can be rearranged into a palindrome.

---

### ⏱️ Complexity

- **Time Complexity**:
  - **Preprocessing (DFS + Binary Lifting)**: $O(N \log N)$ to fill `up` table and run DFS.
  - **Update Query**: $O(\sqrt{N})$ per update due to Sqrt Decomposition range update.
  - **Path Query**: $O(\log N)$ for binary lifting LCA + $O(1)$ block lookup.
  - **Total Time**: $O(N \log N + Q_u \sqrt{N} + Q_q \log N)$ where $Q_u$ is the number of updates and $Q_q$ is the number of path queries.

- **Space Complexity**:
  - $O(N \log N)$ for `up` array of size $N \times 16$.
  - $O(N)$ for DFS arrays (`in`, `out`, `dep`, `pm`, `bl`, `ch`).
  - **Total Space**: $O(N \log N)$ auxiliary space.

---

### 🧠 DSA Pattern

- **Euler Tour Technique (Tree Flattening)**: Transforms subtree updates on a tree into range updates on a 1D array using DFS entry/exit timestamps.
- **Sqrt Decomposition**: Range updates and point queries on the flattened tree array.
- **Binary Lifting**: Computes Lowest Common Ancestor (LCA) in $O(\log N)$ time.
- **Bit Manipulation**: 26-bit integers to track parity of character frequencies and Brian Kernighan's bit manipulation (`mask & (mask - 1)`) to check for $\le 1$ odd frequency.

---

### ⚠️ Common Mistakes

1. **Binary Lifting Table Depth Limit**:
   - `up` table size is `[n][16]`. Max ancestor depth reachable is $2^{15} = 32,768$.
   - Since $N \le 50,000$, if the tree degenerates into a line of depth $50,000$, jumps with $2^{15}$ will not be sufficient to jump $50,000$ levels, causing inaccurate LCA queries. The table second dimension should be at least $17$ ($2^{16} = 65,536 > 50,000$).

2. **1-based vs 0-based Timer Indexing**:
   - `in[u] = ++t` starts $t$ at index 1. Array allocation for `pm` is `n + 1`, matching this 1-based indexing, but block array sizing must carefully account for maximum possible index `(n / bs) + 2`.

3. **Forgetting to Restore LCA Character**:
   - XORing root-to-$u$ and root-to-$v$ masks causes $LCA(u, v)$'s character to be XORed twice (cancelled out). Forgetting to XOR $s[LCA]$ back in leads to wrong path masks.

---

### 🚀 Optimization Notes

- **Query String Parsing Overhead**:
  - The solution uses `q.split(" ")` and `Integer.parseInt()` inside the main loop for each query.
  - String splitting allocates multiple array and string objects per query. In Java, processing $5 \cdot 10^4$ queries this way creates significant GC pressure. Using standard custom parsing or pre-parsed inputs avoids high execution time overhead.

- **Segment Tree / Fenwick Tree Alternative for Updates**:
  - Sqrt decomposition provides $O(\sqrt{N})$ range update and $O(1)$ point query.
  - A Fenwick tree / Segment Tree with lazy propagation could achieve $O(\log N)$ updates instead of $O(\sqrt{N})$. However, given constraints ($N, Q \le 50,000$), $Q \sqrt{N} \approx 50000 \times 224 \approx 1.1 \cdot 10^7$ operations, which easily runs within time limit.
