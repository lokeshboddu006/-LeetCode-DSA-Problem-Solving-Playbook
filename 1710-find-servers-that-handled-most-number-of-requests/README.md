<h2><a href="https://leetcode.com/problems/find-servers-that-handled-most-number-of-requests">Find Servers That Handled Most Number of Requests</a></h2> <img src='https://img.shields.io/badge/Difficulty-Hard-red' alt='Difficulty: Hard' /><hr><p>You have <code>k</code> servers numbered from <code>0</code> to <code>k-1</code> that are being used to handle multiple requests simultaneously. Each server has infinite computational capacity but <strong>cannot handle more than one request at a time</strong>. The requests are assigned to servers according to a specific algorithm:</p>

<ul>
	<li>The <code>i<sup>th</sup></code> (0-indexed) request arrives.</li>
	<li>If all servers are busy, the request is dropped (not handled at all).</li>
	<li>If the <code>(i % k)<sup>th</sup></code> server is available, assign the request to that server.</li>
	<li>Otherwise, assign the request to the next available server (wrapping around the list of servers and starting from 0 if necessary). For example, if the <code>i<sup>th</sup></code> server is busy, try to assign the request to the <code>(i+1)<sup>th</sup></code> server, then the <code>(i+2)<sup>th</sup></code> server, and so on.</li>
</ul>

<p>You are given a <strong>strictly increasing</strong> array <code>arrival</code> of positive integers, where <code>arrival[i]</code> represents the arrival time of the <code>i<sup>th</sup></code> request, and another array <code>load</code>, where <code>load[i]</code> represents the load of the <code>i<sup>th</sup></code> request (the time it takes to complete). Your goal is to find the <strong>busiest server(s)</strong>. A server is considered <strong>busiest</strong> if it handled the most number of requests successfully among all the servers.</p>

<p>Return <em>a list containing the IDs (0-indexed) of the <strong>busiest server(s)</strong></em>. You may return the IDs in any order.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/09/08/load-1.png" style="width: 389px; height: 221px;" />
<pre>
<strong>Input:</strong> k = 3, arrival = [1,2,3,4,5], load = [5,2,3,3,3] 
<strong>Output:</strong> [1] 
<strong>Explanation:</strong> 
All of the servers start out available.
The first 3 requests are handled by the first 3 servers in order.
Request 3 comes in. Server 0 is busy, so it&#39;s assigned to the next available server, which is 1.
Request 4 comes in. It cannot be handled since all servers are busy, so it is dropped.
Servers 0 and 2 handled one request each, while server 1 handled two requests. Hence server 1 is the busiest server.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> k = 3, arrival = [1,2,3,4], load = [1,2,1,2]
<strong>Output:</strong> [0]
<strong>Explanation:</strong> 
The first 3 requests are handled by first 3 servers.
Request 3 comes in. It is handled by server 0 since the server is available.
Server 0 handled two requests, while servers 1 and 2 handled one request each. Hence server 0 is the busiest server.
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> k = 3, arrival = [1,2,3], load = [10,12,11]
<strong>Output:</strong> [0,1,2]
<strong>Explanation:</strong> Each server handles a single request, so they are all considered the busiest.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= k &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= arrival.length, load.length &lt;= 10<sup>5</sup></code></li>
	<li><code>arrival.length == load.length</code></li>
	<li><code>1 &lt;= arrival[i], load[i] &lt;= 10<sup>9</sup></code></li>
	<li><code>arrival</code> is <strong>strictly increasing</strong>.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The code simulates a server assignment process using two **Priority Queues (Min-Heaps)**:

1. **`busy`**: Tracks active servers ordered by their job completion time (`at`).
2. **`free`**: Tracks available servers.

To satisfy the requirement that request `i` should be assigned to server `(i % k)` or the next available server in cyclic order, the solution converts server IDs into a **virtual offset relative to the current request index `i`**. 

When server `x` becomes available during request `i`, it is inserted into the `free` min-heap using the value `i + Math.floorMod(x - i, k)`. This projects server `x` onto the smallest integer $\ge i$ such that `value % k == x`. The min-heap then automatically prioritizes available servers that are closest to `i % k` in clockwise order.

---

### 🔍 Approach

1. **Initialization**:
   - `free`: A `PriorityQueue<Integer>` storing virtual server indices. Initially populated with `0` to `k - 1`.
   - `busy`: A `PriorityQueue<Job>` storing active jobs ordered by finish time (`Job::at`).
   - `handled`: An array of size `k` tracking how many requests each server successfully handles.

2. **Processing Requests**:
   For each request `i` at time `arrival[i]` with duration `load[i]`:
   - **Release Completed Jobs**: Check the `busy` heap. While the earliest completing job has `at <= arrival[i]`, remove it. For the freed server `x`, compute its virtual position `i + Math.floorMod(x - i, k)` and insert it into `free`.
   - **Assign Server**:
     - If `free` is empty, all servers are currently busy; the request is dropped.
     - Otherwise, poll the top element from `free`. Retrieve the true server ID via `x = free.poll() % k`.
     - Schedule the job by adding `new Job(arrival[i] + load[i], x)` into `busy`.
     - Increment `handled[x]`.

3. **Finding Busiest Server(s)**:
   - Calculate the maximum request count among all servers using `Arrays.stream(handled).max()`.
   - Filter and collect all server indices `0` to `k - 1` whose handled count matches this maximum.

---

### 🧩 Algorithm

#### Server Distance Indexing
When server `x` is freed during request `i`, the relative offset to `x` from index `i` modulo `k` is calculated as:
$$\text{offset} = (x - i) \bmod k$$

In Java, `Math.floorMod(x - i, k)` ensures a non-negative result in the range $[0, k - 1]$. The virtual value pushed to `free` is:
$$\text{virtual\_index} = i + \text{Math.floorMod}(x - i, k)$$

When polling from `free`:
$$\text{server\_id} = \text{virtual\_index} \pmod k$$

#### Discrete Event Simulation
- **State**:
  - `free`: Min-heap of available server representations.
  - `busy`: Min-heap of `Job(at, server)` sorted by `at`.
  - `handled`: Counts of requests per server.

---

### ✅ Why This Works

- **Chronological Simulation**: The `busy` heap ensures that all servers finishing their work prior to or at `arrival[i]` are marked free before attempting to process request `i`.
- **Cyclic Order Selection**: Pushing `i + Math.floorMod(x - i, k)` into `free` positions server `x` relative to current request `i`. Because `free` is a min-heap, popping from `free` gives the server with the smallest non-negative cyclic distance from `i % k`.
- **Modulus Recovery**: Modulo operation `x = free.poll() % k` accurately converts the projected virtual index back to the real server index in $[0, k - 1]$.

---

### ⏱️ Complexity

- **Time Complexity**: 
  - Heap initialization: $O(k \log k)$ to add all servers to `free`.
  - Event loop: For $N$ requests, each job is pushed to and popped from `busy` at most once ($O(N \log k)$ operations), and inserted/extracted from `free` ($O(N \log k)$ operations).
  - Max stream evaluation and filtering: $O(k)$.
  - **Total Time Complexity**: $\mathcal{O}((N + k) \log k)$, where $N$ is the number of requests and $k$ is the number of servers.

- **Space Complexity**:
  - `free` heap holds at most $k$ integers.
  - `busy` heap holds at most $k$ `Job` records.
  - `handled` array takes $O(k)$ space.
  - **Total Space Complexity**: $\mathcal{O}(k)$.

---

### 🧠 DSA Pattern

- **Heap / Priority Queue**: Used for both time-based event processing (`busy` queue) and dynamic priority assignment (`free` queue).
- **Simulation**: Event-driven simulation tracking hardware resource allocation over time.

---

### ⚠️ Common Mistakes & Implementation Limitations

1. **Stale Virtual Indices in `free`**:
   - Because virtual indices `i + Math.floorMod(x - i, k)` depend on step `i` at insertion time, if a server stays in `free` across multiple requests without being polled, its virtual index in `free` remains fixed to the request step when it was freed. Under certain sequences of idle requests, its position relative to a much later request $i'$ might not strictly reflect the closest distance to $i' \pmod k$.

2. **Using `%` instead of `Math.floorMod`**:
   - Standard `%` operator in Java returns negative results when the dividend is negative (e.g., `(2 - 5) % 3 == -1`). Using `Math.floorMod` ensures non-negative distances.

---

### 🚀 Optimization Notes

- **Java `record`**: The code uses Java 14+ `record Job(int at, int server) {}`, which provides an immutable data holder with minimal boilerplate.
- **Java Streams**: `Arrays.stream(handled).max()` and `IntStream.range(...)` provide concise result aggregation, though standard `for` loops could reduce object overhead slightly.
