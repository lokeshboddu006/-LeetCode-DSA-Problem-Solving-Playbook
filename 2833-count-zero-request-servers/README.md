<h2><a href="https://leetcode.com/problems/count-zero-request-servers">Count Zero Request Servers</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given an integer <code>n</code> denoting the total number of servers and a <strong>2D</strong> <strong>0-indexed </strong>integer array <code>logs</code>, where <code>logs[i] = [server_id, time]</code> denotes that the server with id <code>server_id</code> received a request at time <code>time</code>.</p>

<p>You are also given an integer <code>x</code> and a <strong>0-indexed</strong> integer array <code>queries</code>.</p>

<p>Return <em>a <strong>0-indexed</strong> integer array</em> <code>arr</code> <em>of length</em> <code>queries.length</code> <em>where</em> <code>arr[i]</code> <em>represents the number of servers that <strong>did not receive</strong> any requests during the time interval</em> <code>[queries[i] - x, queries[i]]</code>.</p>

<p>Note that the time intervals are inclusive.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> n = 3, logs = [[1,3],[2,6],[1,5]], x = 5, queries = [10,11]
<strong>Output:</strong> [1,2]
<strong>Explanation:</strong> 
For queries[0]: The servers with ids 1 and 2 get requests in the duration of [5, 10]. Hence, only server 3 gets zero requests.
For queries[1]: Only the server with id 2 gets a request in duration of [6,11]. Hence, the servers with ids 1 and 3 are the only servers that do not receive any requests during that time period.

</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> n = 3, logs = [[2,4],[2,1],[1,2],[3,1]], x = 2, queries = [3,4]
<strong>Output:</strong> [0,1]
<strong>Explanation:</strong> 
For queries[0]: All servers get at least one request in the duration of [1, 3].
For queries[1]: Only server with id 3 gets no request in the duration [2,4].

</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= logs.length &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= queries.length &lt;= 10<sup>5</sup></code></li>
	<li><code><font face="monospace">logs[i].length == 2</font></code></li>
	<li><code>1 &lt;= logs[i][0] &lt;= n</code></li>
	<li><code>1 &lt;= logs[i][1] &lt;= 10<sup>6</sup></code></li>
	<li><code>1 &lt;= x &lt;= 10<sup>5</sup></code></li>
	<li><code>x &lt;&nbsp;queries[i]&nbsp;&lt;= 10<sup>6</sup></code></li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

The problem asks us to find how many servers received **zero** requests in the time window `[t - x, t]` for each query time `t`.

Instead of calculating the active servers from scratch for every query, the key observation is that if we process the queries in **increasing order of time**, the window `[t - x, t]` monotonically shifts forward
. This allows us to use an **offline query approach** with a **sliding window / two pointers** over sorted logs:

1. As time `t` increases, the right boundary `t` moves right, allowing us to add new logs into our window.
2. The left boundary `t - x` also moves right, allowing us to remove outdated logs that occurred strictly before `t - x`.
3. By maintaining a frequency array of server requests in the current window and a count of currently active servers, the answer for any query is simply `n - active`.

### 🔍 Approach

1. **Sort the Logs**:
   - Sort `logs` in non-decreasing order based on timestamp `logs[i][1]`.

2. **Sort Query Indices (Offline Processing)**:
   - Create an array `q` of integer indices `[0, 1, ..., queries.length - 1]`.
   - Sort `q` based on the query time values `queries[q[i]]` in ascending order. This preserves the original query index so results can be placed into the correct index in `ans`.

3. **Initialize Tracking Variables**:
   - `cnt`: A 1-indexed frequency array of size `n + 1` where `cnt[s]` stores how many requests server `s` has inside the current time window `[t - x, t]`.
   - `active`: Number of unique servers that have at least 1 request in the current window.
   - `l` and `r`: Pointers for the start and end of the relevant logs in the sorted `logs` array.
   - `ans`: Output array of size `queries.length`.

4. **Sliding Window Processing**:
   - Iterate through each query index `i` in `q` with target time `t = queries[i]`:
     - **Expand Right (`r`)**: Advance `r` while `logs[r][1] <= t`. Increment `cnt[server]`. If `cnt[server]` becomes `1`, increment `active` (a new server became active).
     - **Shrink Left (`l`)**: Advance `l` while `logs[l][1] < t - x`. Decrement `cnt[server]`. If `cnt[server]` drops to `0`, decrement `active` (the server no longer has any requests in the current window).
     - **Record Result**: Store `n - active` in `ans[i]`.

5. **Return**: Return the populated `ans` array.

### 🧩 Algorithm

- **Data Structures**:
  - `Integer[] q`: Index array sorted by query times.
  - `int[] cnt`: Frequency array of size `n + 1`.
  - `int[] ans`: Output array.
- **Sliding Window Invariant**:
  - At the end of each query processing, the window `[l, r)` contains exactly all logs with timestamp `logs[k][1]` satisfying `t - x <= logs[k][1] <= t`.
  - `active` stores the count of indices `s` ($1 \le s \le n$) where `cnt[s] > 0`.

### ✅ Why This Works

- **Correct Window Boundaries**:
  - A log at time `log_t` is valid for query time `t` if `t - x <= log_t <= t`.
  - The right `while` loop processes all logs up to `t` (`logs[r][1] <= t`).
  - The left `while` loop removes all logs strictly earlier than `t - x` (`logs[l][1] < t - x`).
- **Monotonic Movement**:
  - Since queries are processed in ascending order of `t`, both `t` and `t - x` are non-decreasing.
  - Thus, `l` and `r` only move forward across the entire execution and never reset.
- **Accurate Server Count**:
  - Incremental updates to `active` when `cnt[server]` transitions $0 \to 1$ and $1 \to 0$ maintain the correct count of servers with $\ge 1$ request in $O(1)$ time per log modification.
  - `n - active` gives the exact count of servers with 0 requests.

### ⏱️ Complexity

- **Time Complexity**: $O(M \log M + Q \log Q)$
  - Sorting `logs` of length $M$: $O(M \log M)$.
  - Sorting `queries` index array of length $Q$: $O(Q \log Q)$.
  - Two pointers `l` and `r`: Each log is added by `r` at most once and removed by `l` at most once across all queries, taking $O(M)$ time total across the loop.
  - Overall Time Complexity: **$O(M \log M + Q \log Q)$**, where $M$ is `logs.length` and $Q$ is `queries.length`.

- **Space Complexity**: $O(n + Q + M)$
  - `cnt` array: $O(n)$ space for $n + 1$ elements.
  - `q` array and `ans` array: $O(Q)$ space.
  - Sorting helper memory for Java's `Arrays.sort`: $O(M + Q)$.
  - Overall Space Complexity: **$O(n + Q + M)$**.

### 🧠 DSA Pattern

- **Offline Queries**: Sorting queries so they can be processed in a convenient sequential order rather than the original input order.
- **Two Pointers / Sliding Window**: Maintaining a moving range over sorted data.

### ⚠️ Common Mistakes

1. **1-based Indexing Out of Bounds**:
   - Server IDs in the problem are 1-indexed (`1 <= server_id <= n`).
   - Using `new int[n]` for `cnt` instead of `new int[n + 1]` would cause an `ArrayIndexOutOfBoundsException`.
2. **Incorrect Bound Conditions**:
   - The interval `[t - x, t]` is inclusive.
   - Using `logs[l][1] <= t - x` to shrink the left bound would mistakenly remove valid logs occurring exactly at time `t - x`. The condition must strictly be `logs[l][1] < t - x`.
3. **Forgetting to Store Results in Original Query Indices**:
   - Because queries are sorted, assigning `ans[step] = n - active` directly by loop counter would place results in sorted query order rather than the requested query order. Using `ans[i] = n - active` where `i` is the original index correctly maps back to the output position.

### 🚀 Optimization Notes

- The solution is already **asymptotically optimal** ($O(M \log M + Q \log Q)$ time).
- In Java, sorting an `Integer[]` array incurs object-boxing overhead. While fast enough to pass within limits, using a primitive 2D array or dual primitive array with a custom primitive sorter could marginally improve runtime and reduce garbage collection overhead.
