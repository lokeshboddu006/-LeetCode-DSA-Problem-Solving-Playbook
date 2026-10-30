<h2><a href="https://leetcode.com/problems/shortest-path-with-at-most-k-consecutive-identical-characters">Shortest Path With At Most K Consecutive Identical Characters</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer <code>n</code> representing the number of nodes in a <strong>directed weighted</strong> graph, numbered from 0 to <code>n - 1</code>. This is represented by a 2D integer array <code>edges</code>, where <code>edges[i] = [u<sub>i</sub>, v<sub>i</sub>, w<sub>i</sub>]</code> represents a directed edge from node <code>u<sub>i</sub></code> to node <code>v<sub>i</sub></code> with weight <code>w<sub>i</sub></code>.</p>

<p>You are also given a string <code>labels</code> of length <code>n</code>, where <code>labels[i]</code> is the character assigned to node <code>i</code>, and an integer <code>k</code>.</p>

<p>Return the <strong>minimum</strong> <strong>total</strong> edge weight of a path from node 0 to node <code>n - 1</code> such that the concatenation of the labels of the nodes along the path contains <strong>at most</strong> <code>k</code> <strong>consecutive</strong> <strong>identical</strong> characters. If no valid path exists, return -1.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 3, edges = [[0,1,1],[1,2,1],[0,2,3]], labels = &quot;aab&quot;, k = 1</span></p>

<p><strong>Output:</strong> <span class="example-io">3</span></p>

<p><strong>Explanation:</strong></p>

<p>The optimal valid path from node 0 to node 2 is as follows:</p>

<ul>
	<li>Use <code>edges[2] = [0, 2, 3]</code> to reach node 2 with a weight <code>w<sub>i</sub> = 3</code>.</li>
</ul>
The corresponding concatenation of labels is <code>&quot;ab&quot;</code>, which satisfies at most <code>k = 1</code> consecutive identical characters. Thus, the answer is 3.</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 3, edges = [[0,1,1],[1,2,1],[0,2,3]], labels = &quot;aab&quot;, k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>

<p><strong>Explanation:</strong></p>

<p>The optimal valid path from node 0 to node 2 is as follows:</p>

<ul>
	<li>Use <code>edges[0] = [0, 1, 1]</code> to reach node 1 with weight <code>w<sub>i</sub> = 1</code>.</li>
	<li>Use <code>edges[1] = [1, 2, 1]</code> to reach node 2 with weight <code>w<sub>i</sub> = 1</code>.</li>
</ul>
The corresponding concatenation of labels is <code>&quot;aab&quot;</code>, which satisfies at most <code>k = 2</code> consecutive identical characters. Thus, the answer is 2.</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 3, edges = [[0,1,1],[1,2,1]], labels = &quot;aaa&quot;, k = 2</span></p>

<p><strong>Output:</strong> <span class="example-io">-1</span></p>

<p><strong>Explanation:</strong></p>

<p>There is no valid path from node 0 to node 2 that satisfies at most <code>k = 2</code> consecutive identical characters. Thus, the answer is -1.</p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == labels.length &lt;= 5 * 10<sup>4</sup></code></li>
	<li><code>0 &lt;= edges.length &lt;= 5 * 10<sup>4</sup></code></li>
	<li><code>edges[i] == [u<sub>i</sub>, v<sub>i</sub>, w<sub>i</sub>]</code></li>
	<li><code>0 &lt;= u<sub>i</sub>, v<sub>i</sub> &lt;= n - 1</code></li>
	<li><code>u<sub>i</sub> != v<sub>i</sub></code></li>
	<li><code>1 &lt;= w<sub>i</sub> &lt;= 10<sup>4</sup></code></li>
	<li><code>labels</code> consists of lowercase English letters</li>
	<li><code>1 &lt;= k &lt;= 50</code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks for the shortest path from node `0` to node `n - 1` in a directed graph, subject to the condition that no more than `k` consecutive nodes on the path share the same character label.

Since edge weights are non-negative, **Dijkstra's Algorithm** is the natural choice. However, standard Dijkstra tracks only `dist[node]`. Here, whether a path can be extended safely depends not just on which node we are at, but also on the **current length of consecutive identical character labels** (the "run length").

To adapt Dijkstra:
1. Every state in the min-priority queue carries three pieces of information: `[distance, run_length, current_node]`.
2. As we visit a node `x`, we record the smallest run length seen so far at `x` in a `best` array. If we reach `x` again later with a run length that is greater than or equal to `best[x]`, that path is strictly worse or equal in distance (since Dijkstra processes states in order of non-decreasing distance) and has a worse/equal run length, so it can be safely skipped (pruned).

### 🔍 Approach

1. **Graph Construction**:
   - Build an adjacency list `graph` where `graph[u]` contains `[v, weight]` pairs representing directed edges from `u` to `v`.

2. **State Tracking Initialization**:
   - Create an array `best` of length `n`, initialized to `k + 1`. `best[x]` stores the smallest run length with which node `x` has been processed so far.
   - Initialize a min-heap `heap` ordered lexicographically using Java's `Arrays::compare`.
   - Push the starting state `[0, 1, 0]` into `heap`: distance `0`, run length `1` (for node 0's label), and node ID `0`.

3. **Dijkstra Traversal**:
   - While `heap` is not empty, pop the state `[d, run, x]` with the smallest distance `d`.
   - **Pruning Check**: If `run >= best[x]`, skip this state because a path with equal or smaller distance and a strictly shorter run length at node `x` was already processed.
   - **Destination Check**: If `x == n - 1`, immediately return `(int) d`. Because Dijkstra extracts states in non-decreasing order of distance, the first time node `n - 1` is popped, `d` is guaranteed to be the minimal weight sum for any valid path.
   - **Update Best**: Set `best[x] = run`.

4. **Edge Relaxation**:
   - For each neighbor `e = [y, weight]` of node `x`:
     - Calculate the new run length `r`:
       - If `labels.charAt(y) == labels.charAt(x)`, the run continues: `r = run + 1`.
       - Otherwise, the character changes, resetting the run: `r = 1`.
     - **Validity Check**: Check `if (r < best[y])`. Since `best[y]` is initialized to `k + 1`, this condition naturally ensures that `r <= k` (a run length of `k + 1` or higher is rejected).
     - If valid, add `[d + weight, r, y]` to the min-heap.

5. **Failure Return**:
   - If the queue becomes empty without reaching node `n - 1`, return `-1`.

### 🧩 Algorithm

#### State Representation
Each element in the heap is a 3-element `long[]` array:
- `s[0]`: Cumulative path distance `d`
- `s[1]`: Current consecutive identical label run length `run`
- `s[2]`: Current node index `x`

#### Key Conditions & Transitions
- **Heap Order**: `PriorityQueue<long[]>` sorted using `Arrays::compare` compares elements index by index (`d` first, then `run`, then `x`).
- **Pruning**: `if (run >= best[x]) continue;`
- **Transition**:
  $$\text{new\_run}(x \to y) = \begin{cases} \text{run} + 1 & \text{if } \text{labels}[y] == \text{labels}[x] \\ 1 & \text{otherwise} \end{cases}$$
- **Constraint Enforcement**: `r < best[y]` enforces $r \le k$ because $best[y] \le k + 1$.

---

### ✅ Why This Works

1. **Greedy Shortest Path Property**: Dijkstra extracts states in non-decreasing order of total weight `d`. Therefore, the first time node `n - 1` is popped from the heap, the path distance `d` is guaranteed to be optimal among all valid paths.
2. **Correctness of Pruning**: Suppose we reach node `x` with state $(d_1, run_1)$ and later with $(d_2, run_2)$. Because states are popped in non-decreasing distance order, $d_1 \le d_2$. If $run_2 \ge run_1$, state 2 is dominated by state 1 in both distance and remaining available run capacity. Thus, skipping state 2 never discards an optimal path.
3. **Implicit Boundary Enforcement**: Because `best[y]` starts at `k + 1`, any neighbor expansion resulting in $r > k$ (i.e., $r \ge k + 1$) will fail `r < best[y]` (since `best[y]` $\le k + 1$). This enforces the constraint at most $k$ consecutive identical characters without an explicit `r <= k` branch.

---

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}(k \cdot E \log(k \cdot N))$
  - Each node $x$ can be processed with at most $k$ distinct run lengths (since `best[x]` strictly decreases from at most $k$ down to $1$).
  - Therefore, there are at most $\mathcal{O}(k \cdot N)$ unique state extractions from the priority queue and at most $\mathcal{O}(k \cdot E)$ edge relaxations.
  - Heap operations take $\mathcal{O}(\log(k \cdot N))$ time.

- **Space Complexity**: $\mathcal{O}(N + E + k \cdot N)$
  - Adjacency list: $\mathcal{O}(N + E)$ space.
  - `best` array: $\mathcal{O}(N)$ space.
  - Heap: contains at most $\mathcal{O}(k \cdot N)$ elements simultaneously.

---

### 🧠 DSA Pattern

- **Dijkstra's Algorithm** (Shortest path on non-negatively weighted graphs)
- **Heap / Priority Queue**
- **State Space Augmentation** (Extending graph node states with auxiliary metadata: `(distance, run_length, node)`)

---

### ⚠️ Common Mistakes

1. **Forgetting State Pruning**: Simply keeping track of `visited[node]` as a `boolean` array would produce incorrect answers because a node might need to be re-visited with a smaller run length to reach the target legally.
2. **Relying on `best` Initialization for Constraint Checking**: The condition `if (r < best[y])` correctly rejects $r > k$ **only because** `best` is initialized to `k + 1`. If `best` were initialized to `Integer.MAX_VALUE`, invalid run lengths $r > k$ would wrongly be pushed into the queue.
3. **Using `Arrays::compare` Mindfully**: `Arrays::compare` compares `long[]` lexicographically starting from index 0. Reordering array elements (e.g., placing `x` at index 0 instead of `d`) would break Dijkstra's min-distance property.

---

### 🚀 Optimization Notes

- **Implicit Constraint Validation**: Combining the DP-like pruning check and the constraint limit into `r < best[y]` avoids extra `if` checks.
- **Using `long[]` for Heap Elements**: Prevents potential arithmetic overflow when adding edge weights (`d + e[1]`) before casting back to `int` upon return.
- **`Arrays::compare` Method Reference**: Passing `Arrays::compare` directly to the `PriorityQueue` constructor simplifies array comparisons without creating custom lambda comparator objects.
