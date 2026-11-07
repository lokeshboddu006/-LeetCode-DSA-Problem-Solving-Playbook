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

The core idea behind this solution is to simulate the task assignment process using **two min-heaps (PriorityQueues)** to track the state of servers over time:

1. **Available Servers Heap (`a`)**: Stores all currently free servers. It prioritizes servers by lowest weight first, and then by lowest server index.
2. **Busy Servers Heap (`b`)**: Stores servers currently processing tasks. It prioritizes servers by the time they become free first, followed by weight, and then index.

Instead of simulating time second-by-second (which would be too slow if `c` becomes large), the code uses a variable `c` to track the current simulation time. If no servers are free when a task needs to be processed, the current time `c` fast-forwards directly to when the earliest busy server finishes.

### 🔍 Approach

1. **Heap Initialization**:
   - `a` is initialized as a Min-Heap of `[weight, index]`.
   - `b` is initialized as a Min-Heap of `[freeTime, weight, index]`.
   - Populate `a` with all servers initially (since all servers are available at second `0`).

2. **Iterating Through Tasks**:
   Loop through each task `t[j]` from `j = 0` to `t.length - 1`:
   - **Time Alignment**: Update `c = Math.max(c, j)` to ensure current time is at least task insertion time `j`.
   - **Fast-Forwarding**: If `a` is empty (no free servers), update `c = Math.max(c, b.peek()[0])` to jump time forward to the instant the earliest working server becomes free.
   - **Releasing Servers**: Execute a `while` loop to pop all servers from `b` whose `freeTime <= c`. Put them back into `a` as available servers `[weight, index]`.
   - **Assigning Task**:
     - Poll the best available server `u` from `a` (which guarantees the smallest weight, then index).
     - Store its index `u[1]` in the result array `r[j]`.
     - Push server `u` to busy heap `b` with its new free time: `[c + t[j], weight, index]`.

3. **Return Result**:
   - After assigning all `m` tasks, return the result array `r`.

### 🧩 Algorithm

The solution implements a **Min-Heap-Based Event Simulation**:

- **Available Priority Queue Order (`a`)**:
  Compare element `x = [weight, index]` and `y = [weight, index]`:
  $$\text{Priority}(x) < \text{Priority}(y) \iff (x_0 < y_0) \lor (x_0 = y_0 \land x_1 < y_1)$$

- **Busy Priority Queue Order (`b`)**:
  Compare element `x = [freeTime, weight, index]` and `y = [freeTime, weight, index]`:
  $$\text{Priority}(x) < \text{Priority}(y) \iff (x_0 < y_0) \lor (x_0 = y_0 \land x_1 < y_1) \lor (x_0 = y_0 \land x_1 = y_1 \land x_2 < y_2)$$

- **Time Transition**:
  $$c \leftarrow \max(c, j)$$
  If $a$ is empty: $c \leftarrow \max(c, b.\text{peek}()[0])$

### ✅ Why This Works

1. **Correct Server Selection**: PriorityQueue `a` sorts servers by `[weight, index]`. When `a.poll()` is called, it guarantees picking the free server with the minimal weight and minimal index, satisfying the problem's strict selection rule.
2. **Correct Processing Timeline**: PriorityQueue `b` keeps track of busy servers ordered by `freeTime`. Whenever current time `c` reaches or passes a server's `freeTime`, that server is popped from `b` and pushed back into `a`.
3. **No Redundant Time Steps**: Fast-forwarding `c` to `b.peek()[0]` when `a` is empty ensures the simulation skips idle seconds efficiently without losing temporal ordering.

### ⏱️ Complexity

- **Time Complexity**: $\mathcal{O}((n + m) \log n)$
  - Inserting $n$ servers into heap `a` initially takes $\mathcal{O}(n \log n)$.
  - Processing each of the $m$ tasks involves heap operations (`offer`/`poll`) on `a` and `b`. Since at most $n$ servers exist across both heaps, each operation takes $\mathcal{O}(\log n)$.
  - Overall time complexity is $\mathcal{O}((n + m) \log n)$, which comfortably runs within the given limits ($n, m \le 2 \cdot 10^5$).

- **Space Complexity**: $\mathcal{O}(n + m)$
  - Heaps `a` and `b` hold at most $n$ server elements combined: $\mathcal{O}(n)$ space.
  - Output array `r` takes $\mathcal{O}(m)$ space.

### 🧠 DSA Pattern

- **Heap / Priority Queue**: Dual min-heaps to handle two dynamic subsets (available vs. busy).
- **Simulation / Event-Driven Programming**: Advancing current time based on events (task arrival time `j` or server release time `b.peek()[0]`).

### ⚠️ Common Mistakes

1. **Integer Overflow in Comparators**: Using subtraction like `x[0] - y[0]` in comparators can lead to integer overflow if values can be negative or large. Here, `freeTime` can reach up to $2 \cdot 10^5 + 2 \cdot 10^5 \times 2 \cdot 10^5 \approx 4 \cdot 10^{10}$, which **exceeds 32-bit Integer range** (`2 * 10^9`). 
   - *In this code*: The user safely used `Integer.compare(x[0], y[0])` for `b`'s `freeTime`, avoiding overflow bugs when comparing large time values!
2. **Forgetting Time Jumps**: Simply incrementing time `c++` one second at a time would result in a **Time Limit Exceeded (TLE)** error. Fast-forwarding `c = Math.max(c, b.peek()[0])` when `a` is empty is critical.
3. **Task Queue Processing Order**: Modifying `c` without checking `c = Math.max(c, j)` would break the requirement that task `j` is inserted at second `j`.

### 🚀 Optimization Notes

- **Optimal Strategy**: The implementation already achieves optimal asymptotic time complexity $\mathcal{O}((n + m) \log n)$.
- **Primitive Array Heap Elements**: Small integer arrays `int[]` are used as heap elements. While creating many `int[]` objects can cause garbage collection overhead in Java, it remains efficient enough to pass within time limits.
