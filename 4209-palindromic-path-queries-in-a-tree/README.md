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

To determine if the characters on a path in a tree can be rearranged to form a palindrome, we only need to track the **parity (even/odd)** of each character's count along that path. A sequence of characters can be rearranged into a palindrome if and only if **at most one character has an odd frequency**.

Since there are only 26 lowercase English letters ('a' through 'z'), we can represent character frequency parities using a **26-bit bitmask**, where the $k$-th bit is `1` if the $k$-th character appears an odd number of times, and `0` if it appears an even number of times.

Updating character counts and querying path parities efficiently on a tree is handled using two key concepts:
1. **Euler Tour (DFS Order) + Fenwick Tree (BIT)**: Flatten the tree using entrance (`tin`) and exit (`tout`) times. Updating a node $u$'s character affects the root-to-node path parity for $u$ and all nodes in $u$'s subtree (range $[\text{tin}[u], \text{tout}[u]]$). Range updates with point queries on prefix paths are efficiently handled by a Binary Indexed Tree using XOR logic.
2. **Path Decomposition via LCA**: The XOR path sum between $u$ and $v$ can be derived from the root-to-$u$ and root-to-$v$ path sums combined with the LCA $w$:
   $$\text{path\_mask}(u, v) = \text{root\_mask}(u) \oplus \text{root\_mask}(v) \oplus \text{mask}(w)$$

### 🔍 Approach

1. **Tree Preprocessing & LCA Setup (`dfs`, `lca`)**:
   - Construct the adjacency list `g`.
   - Perform a Depth-First Search starting at node `0`:
     - Record entry time `tin[u]` and exit time `tout[u]` using a global `timer`.
     - Compute binary lifting table `up[j][u]` for $2^j$-th ancestors to support $O(\log N)$ LCA queries.
     - Compute node depths in `dep`.

2. **BIT Initialization**:
   - A Fenwick tree `bit` stores point updates for range maintenance.
   - For each node $u$, range update the subtree interval $[\text{tin}[u], \text{tout}[u]]$ with the node's initial character bitmask $1 \ll (s[u] - 'a')$.

3. **Range Updates via Difference Strategy (`update`, `add`, `sum`)**:
   - To update range $[\text{tin}[u], \text{tout}[u]]$ with value $x$, call `add(tin[u], x)` and `add(tout[u] + 1, x)`.
   - `sum(tin[x])` returns the prefix XOR sum, which yields the current XOR mask along the path from root `0` to node $x$.

4. **Query Processing (`palindromePath`)**:
   - Parse each query string by splitting on space `" "`.
   - **`update u c`**:
     - Compute the XOR difference between the old character mask and the new character mask: $(1 \ll (s[u] - 'a')) \oplus (1 \ll (c - 'a'))$.
     - Range-update $u$'s subtree with this difference and update `s[u] = c`.
   - **`query u v`**:
     - Find Lowest Common Ancestor $w = \text{lca}(u, v)$.
     - Compute the combined path bitmask $m = \text{sum}(\text{tin}[u]) \oplus \text{sum}(\text{tin}[v]) \oplus (1 \ll (s[w] - 'a'))$.
     - Check if $m$ has at most one bit set using the bitwise check `(m & (m - 1)) == 0`.

### 🧩 Algorithm

1. **Subtree Range Update / Root-to-Node Point Query via Fenwick Tree**:
   - Range update on $[\text{tin}[u], \text{tout}[u]]$ with bitmask $\Delta$:
     $$\text{bit}[\text{tin}[u]] \leftarrow \text{bit}[\text{tin}[u]] \oplus \Delta$$
     $$\text{bit}[\text{tout}[u] + 1] \leftarrow \text{bit}[\text{tout}[u] + 1] \oplus \Delta$$
   - Query root-to-$u$ path mask: $\text{sum}(\text{tin}[u]) = \bigoplus_{i \le \text{tin}[u]} \text{bit}[i]$.

2. **Path XOR Combination**:
   $$\text{path\_mask}(u, v) = \text{sum}(\text{tin}[u]) \oplus \text{sum}(\text{tin}[v]) \oplus (1 \ll (s[\text{lca}(u, v)] - 'a'))$$

3. **Palindrome Validity Check**:
   $$\text{is\_palindrome} = (m \ \& \ (m - 1)) == 0$$
   *(Evaluates to `true` if $m = 0$ or $m$ is a power of 2, meaning $\le 1$ character has odd frequency).*

### ✅ Why This Works

- **Bitwise XOR for Parity**: Because $x \oplus x = 0$, characters appearing an even number of times cancel out, leaving a `1` bit only for characters with odd frequencies.
- **Subtree Contiguity**: Every node $v$ in $u$'s subtree has $\text{tin}[u] \le \text{tin}[v] \le \text{tout}[u]$. By applying a range update on $[\text{tin}[u], \text{tout}[u]]$, every node in $u$'s subtree incorporates the change into its `sum(tin[v])` query.
- **LCA Correction**: When XORing the root-to-$u$ and root-to-$v$ path masks, all nodes on the path from root to $\text{parent}(\text{lca}(u, v))$ appear in both paths and cancel out to `0`. Node $w = \text{lca}(u, v)$ also appears in both root-to-$u$ and root-to-$v$ paths and cancels out. XORing $w$'s individual character mask $1 \ll (s[w] - 'a')$ explicitly re-includes $w$ back into the path XOR mask.

### ⏱️ Complexity

- **Time Complexity**:
  - **Preprocessing**: $O(N \log N)$ for DFS, binary lifting table initialization, and BIT updates for $N$ nodes.
  - **Updates**: $O(\log N)$ per update query (2 Fenwick tree modifications).
  - **Queries**: $O(\log N)$ per path query ($O(\log N)$ for binary lifting LCA + 2 Fenwick tree prefix sum queries).
  - **Total Time Complexity**: $O((N + Q) \log N)$, where $N$ is the number of nodes and $Q$ is the number of queries.

- **Space Complexity**:
  - Binary lifting table `up[17][N]`: $O(N \log N)$.
  - Adjacency list, entry/exit arrays, Fenwick tree array `bit`: $O(N)$.
  - Total Space Complexity: $O(N \log N)$.

### 🧠 DSA Pattern

- **Binary Lifting (Lowest Common Ancestor - LCA)**
- **Euler Tour Technique (Subtree flattening via DFS `tin`/`tout`)**
- **Fenwick Tree / Binary Indexed Tree (Range Updates via Difference Array)**
- **Bitmasking (Parity Tracking)**

### ⚠️ Common Mistakes

1. **Forgetting to re-include the LCA node**: XORing root-to-$u$ and root-to-$v$ cancels out LCA $w$ along with its ancestors. Forgetting to XOR $(1 \ll (s[w] - 'a'))$ back into $m$ misses $w$'s character.
2. **Updating `s[u]` prematurely**: Changing `s[u]` before computing $(1 \ll (s[u] - 'a')) \oplus (1 \ll (c - 'a'))$ would result in XORing `0` instead of the difference mask.
3. **1-based vs 0-based BIT Indexing**: `timer` starts at 1 (`++timer`), ensuring BIT queries `sum(tin[u])` use valid 1-based indices without accessing index 0.

### 🚀 Optimization Notes

- **String Parsing Overhead**: The loop uses `q.split(" ")` and `Integer.parseInt()`. Creating intermediate string arrays for every query introduces object allocation overhead in Java.
- **DFS Recursion Depth**: For tall or degenerate trees (chain graph), standard recursive DFS can reach depth $N = 50,000$, which risks stack overflow on environments with tight stack limits.
- **Bitmask Check Efficiency**: Checking `(m & (m - 1)) == 0` is an $O(1)$ bit manipulation trick that avoids counting set bits manually via loops.
