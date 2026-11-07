<h2><a href="https://leetcode.com/problems/process-tasks-using-servers">Process Tasks Using Servers</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given two <strong>0-indexed</strong> integer arrays <code>servers</code> and <code>tasks</code> of lengths <code>n</code>​​​​​​ and <code>m</code>​​​​​​ respectively. <code>servers[i]</code> is the <strong>weight</strong> of the <code>i<sup>​​​​​​th</sup></code>​​​​ server, and <code>tasks[j]</code> is the <strong>time needed</strong> to process the <code>j<sup>​​​​​​th</sup></code>​​​​ task <strong>in seconds</strong>.</p>

<p>Tasks are assigned to the servers using a <strong>task queue</strong>. Initially, all servers are free, and the queue is <strong>empty</strong>.</p>

<p>At second <code>j</code>, the <code>j<sup>th</sup></code> task is <strong>inserted</strong> into the queue (starting with the <code>0<sup>th</sup></code> task being inserted at second <code>0</code>). As long as there are free servers and the queue is not empty, the task in the front of the queue will be assigned to a free server with the <strong>smallest weight</strong>, and in case of a tie, it is assigned to a free server with the <strong>smallest index</strong>.</p>

<p>If there are no free servers and the queue is not empty, we wait until a server becomes free and immediately assign the next task. If multiple servers become free at the same time, then multiple tasks from the queue will be assigned <strong>in order of insertion</strong> following the weight and index priorities above.</p>

<p>A server that is assigned task <code>j</code> at second <code>t</code> will be free again at second <code>t + tasks[j]</code>.</p>

<p>Build an array <code>ans</code>​​​​ of length <code>m</code>, where <code>ans[j]</code> is the <strong>index</strong> of the server the <code>j<sup>​​​​​​th</sup></code> task will be assigned to.</p>

<p>Return <em>the array </em><code>ans</code>​​​​.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> servers = [3,3,2], tasks = [1,2,3,2,1,2]
<strong>Output:</strong> [2,2,0,2,1,2]
<strong>Explanation: </strong>Events in chronological order go as follows:
- At second 0, task 0 is added and processed using server 2 until second 1.
- At second 1, server 2 becomes free. Task 1 is added and processed using server 2 until second 3.
- At second 2, task 2 is added and processed using server 0 until second 5.
- At second 3, server 2 becomes free. Task 3 is added and processed using server 2 until second 5.
- At second 4, task 4 is added and processed using server 1 until second 5.
- At second 5, all servers become free. Task 5 is added and processed using server 2 until second 7.</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> servers = [5,1,4,3,2], tasks = [2,1,2,4,5,2,1]
<strong>Output:</strong> [1,4,1,4,1,3,2]
<strong>Explanation: </strong>Events in chronological order go as follows: 
- At second 0, task 0 is added and processed using server 1 until second 2.
- At second 1, task 1 is added and processed using server 4 until second 2.
- At second 2, servers 1 and 4 become free. Task 2 is added and processed using server 1 until second 4. 
- At second 3, task 3 is added and processed using server 4 until second 7.
- At second 4, server 1 becomes free. Task 4 is added and processed using server 1 until second 9. 
- At second 5, task 5 is added and processed using server 3 until second 7.
- At second 6, task 6 is added and processed using server 2 until second 7.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>servers.length == n</code></li>
	<li><code>tasks.length == m</code></li>
	<li><code>1 &lt;= n, m &lt;= 2 * 10<sup>5</sup></code></li>
	<li><code>1 &lt;= servers[i], tasks[j] &lt;= 2 * 10<sup>5</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The core problem requires assigning tasks to servers based on strict priority rules: lowest weight first, and then lowest server index to break ties. If no servers are available at second `j`, the next task must wait for the earliest server to become free.

Your solution manages this dynamic state using two Min-Heaps (Priority Queues):
1. **`free` heap**: Holds all currently idle servers, prioritized by `(weight, index)`.
2. **`busy` heap**: Holds all servers currently processing tasks, prioritized by `(free_time, weight, index)`.

By maintaining these two heaps, you can instantly fetch the best available server at any given second, or jump time forward to the earliest server release time if all servers are busy.

---

### 🔍 Approach

1. **Initialization**:
   - Create a `free` min-heap ordered primarily by `server weight` (`a[0] - b[0]`), then by `server index` (`a[1] - b[1]`).
   - Populate `free` with all given servers `[servers[i], i]`.
   - Create a `busy` min-heap storing `long[]` arrays structured as `[free_time, weight, index]`. It is ordered primarily by `free_time`, then by `weight`, then by `index`.

2. **Iterating Through Tasks**:
   - Loop through tasks using index `j` (which also represents time second `j`).
   - **Release Finished Servers**: Before assigning task `j`, check `busy.peek()[0] <= j`. Transfer all servers whose `free_time` is less than or equal to `j` back into the `free` heap.
   - **Case 1: At least one server is free (`!free.isEmpty()`)**:
     - Extract the top server `cur` from `free`.
     - Store its index in `ans[j] = cur[1]`.
     - Move it to `busy` with release time set to `j + tasks[j]`.
   - **Case 2: All servers are busy (`free.isEmpty()`)**:
     - Poll the server `cur` from `busy` that finishes the earliest (at timestamp `cur[0]`).
     - Assign task `j` to this server: `ans[j] = (int)cur[2]`.
     - Re-insert it into `busy` with updated completion time: `cur[0] + tasks[j]`. This implicitly fast-forwards time for task `j` without executing empty loop iterations.

3. **Return Result**:
   - Return the filled `ans` array containing the server assigned to each task.

---

### 🧩 Algorithm

This solution implements a **Two-Heap Event Simulation**:

- **`free` heap comparator**: 
  $$\text{Compare}(a, b) = \begin{cases} 
  a[0] - b[0], & \text{if } a[0] \neq b[0] \\ 
  a[1] - b[1], & \text{otherwise} 
  \end{cases}$$

- **`busy` heap comparator**: 
  Lexicographical ordering on `(free\_time, weight, index)`.

- **State Transitions**:
  - Task $j$ at time $j$:
    - $\text{busy} \xrightarrow{\text{free\_time} \le j} \text{free}$
    - If $\text{free} \neq \emptyset$: $\text{free} \xrightarrow{\text{poll}} \text{busy}$ with new free time $j + \text{tasks}[j]$
    - If $\text{free} = \emptyset$: $\text{busy} \xrightarrow{\text{poll standard top}} \text{busy}$ with new free time $\text{busy.top.free\_time} + \text{tasks}[j]$

---

### ✅ Why This Works

- **Correct Priority Ordering**: The `free` heap guarantees that the server with the smallest weight (and smallest index on ties) is always polled first.
- **Timestamp Integrity**: The `while` loop checks `busy.peek()[0] <= j`, ensuring that all servers that finish on or before second `j` become available before task `j` is processed.
- **Implicit Time-Jumping**: When `free` is empty, pulling directly from `busy` correctly picks the server that will be available earliest. Computing `cur[0] + tasks[j]` correctly simulates assigning task `j` at time `cur[0]` as soon as the server becomes free, without needing a step-by-step time loop.

---

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}((N + M) \log N)$
  - Inserting $N$ servers into the `free` heap takes $\mathcal{O}(N \log N)$.
  - For each of the $M$ tasks, each server enters and leaves the `free` and `busy` heaps a constant number of times across the algorithm. Each heap operation takes $\mathcal{O}(\log N)$ because the heaps contain at most $N$ elements at any point.
  - Total time complexity: $\mathcal{O}((N + M) \log N)$.

- **Space Complexity:** $\mathcal{O}(N + M)$
  - `free` heap stores up to $N$ `int[]` elements ($\mathcal{O}(N)$).
  - `busy` heap stores up to $N$ `long[]` elements ($\mathcal{O}(N)$).
  - `ans` array stores $M$ integers ($\mathcal{O}(M)$).

---

### 🧠 DSA Pattern

- **Heap / Priority Queue**: Dual-heap design (one heap for available resources, one heap for scheduled events/in-flight resources).
- **Simulation**: Event-driven queue simulation.

---

### ⚠️ Common Mistakes

1. **Integer Overflow on Time**: `j + tasks[j]` or accumulating busy times `cur[0] + tasks[j]` can exceed $2^{31} - 1$ when processing many tasks. Your solution avoids this by storing times as `long` inside `busy` (`long[]`).
2. **Forgetting Tie-Breaking Criteria**: Comparing only `free_time` in `busy` without considering `weight` and `index` can cause incorrect server selection when multiple servers free up at the exact same timestamp. Your 3-tier comparator handles this correctly.
3. **Simulating Second-by-Second**: Incrementing a `time` variable by `1` per step can result in TLE (Time Limit Exceeded) when server execution times are large (up to $2 \cdot 10^5$). Jumping time using `cur[0]` when `free` is empty avoids TLE.

---

### 🚀 Optimization Notes

- The solution is optimal in terms of asymptotic time complexity $\mathcal{O}((N + M) \log N)$.
- Object allocation inside loops: `new int[]` and `new long[]` are created frequently during execution. Creating a custom class or using fixed primitive arrays could marginally improve execution time and memory footprint in Java, but the current approach is clean and well-structured.
