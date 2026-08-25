<h2><a href="https://leetcode.com/problems/add-two-numbers">Add Two Numbers</a></h2> <img src='https://img.shields.io/badge/Difficulty-Medium-orange' alt='Difficulty: Medium' /><hr><p>You are given two <strong>non-empty</strong> linked lists representing two non-negative integers. The digits are stored in <strong>reverse order</strong>, and each of their nodes contains a single digit. Add the two numbers and return the sum&nbsp;as a linked list.</p>

<p>You may assume the two numbers do not contain any leading zero, except the number 0 itself.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/10/02/addtwonumber1.jpg" style="width: 483px; height: 342px;" />
<pre>
<strong>Input:</strong> l1 = [2,4,3], l2 = [5,6,4]
<strong>Output:</strong> [7,0,8]
<strong>Explanation:</strong> 342 + 465 = 807.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> l1 = [0], l2 = [0]
<strong>Output:</strong> [0]
</pre>

<p><strong class="example">Example 3:</strong></p>

<pre>
<strong>Input:</strong> l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
<strong>Output:</strong> [8,9,9,9,0,0,0,1]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in each linked list is in the range <code>[1, 100]</code>.</li>
	<li><code>0 &lt;= Node.val &lt;= 9</code></li>
	<li>It is guaranteed that the list represents a number that does not have leading zeros.</li>
</ul>

---

## 🧠 AI Explanation

### 💡 Intuition

Since the input linked lists store digits in **reverse order**, the heads of `l1` and `l2` represent the least significant digits (ones place). This aligns directly with grade-school addition, where you add digits from right to left while keeping track of a `carry`.

Your implementation simulates this column-by-column addition sequentially. At each position, it adds the corresponding digits from `l1` and `l2` along with the current `carry`, extracts the single-digit value for the new node (`sum % 10`), and computes the new `carry` (`sum / 10`) for the next iteration.

### 🔍 Approach

1. **Dummy Head Sentinel (`dummyHead`)**:
   - You instantiate `ListNode dummyHead = new ListNode(0)` and set `current = dummyHead`.
   - Using a dummy node avoids writing special conditional logic for setting the head of the output linked list on the first iteration.

2. **Carry Variable (`carry`)**:
   - Initialized to `0` to store any value overflowing into the next position (e.g., when $7 + 5 = 12$, digit is $2$ and carry is $1$).

3. **Combined While Loop (`l1 != null || l2 != null || carry != 0`)**:
   - The loop runs as long as **at least one** list has nodes remaining, **or** a leftover `carry` needs to be appended as a final node (e.g., $99 + 1 = 100$).

4. **Digit Extraction & Safe Null Checking**:
   - `val1` takes `l1.val` if `l1` is not `null`, otherwise `0`.
   - `val2` takes `l2.val` if `l2` is not `null`, otherwise `0`.
   - This handles lists of unequal lengths seamlessly by treating missing nodes as value `0`.

5. **Sum & Carry Calculation**:
   - `int sum = val1 + val2 + carry;`
   - `carry = sum / 10;` (integer division extracts the tens digit).

6. **Appending Node & Advancing Pointers**:
   - `current.next = new ListNode(sum % 10);` creates a new node with the ones digit.
   - `current = current.next;` moves the result pointer forward.
   - `l1` and `l2` are individually advanced to `.next` if they are non-null.

7. **Return Result**:
   - Returns `dummyHead.next`, which points to the real start of the generated linked list.

### 🧩 Algorithm

- **Loop Invariant**: At the start of iteration $k$, `current` points to the $(k-1)$-th node of the resulting sum list, and `carry` contains the carry digit from position $k-1$.
- **Step-by-step Transformation**:
  $$\text{val}_1 = (\text{l1} \neq \text{null}) ? \text{l1.val} : 0$$
  $$\text{val}_2 = (\text{l2} \neq \text{null}) ? \text{l2.val} : 0$$
  $$\text{sum} = \text{val}_1 + \text{val}_2 + \text{carry}$$
  $$\text{carry} = \lfloor \text{sum} / 10 \rfloor$$
  $$\text{new\_digit} = \text{sum} \pmod{10}$$

### ✅ Why This Works

- **Reverse Order Aligning**: Because the linked lists are stored reversed, iteration moving forward (`node.next`) corresponds directly to moving from lower place values (1s, 10s, 100s) to higher place values.
- **Unequal List Lengths**: Ternary checks `(l1 != null) ? l1.val : 0` pad shorter numbers with zero without breaking traversal.
- **Final Overflow**: Including `carry != 0` in the loop condition guarantees that when addition produces an extra digit at the end (such as `5 + 5 = 10`), a final node containing `1` is appended even after both `l1` and `l2` become `null`.

### ⏱️ Complexity

- **Time Complexity:** $\mathcal{O}(\max(N, M))$ where $N$ is the length of list `l1` and $M$ is the length of list `l2`. The loop executes at most $\max(N, M) + 1$ times.
- **Space Complexity:** $\mathcal{O}(\max(N, M))$ auxiliary space to create the returned linked list, which will contain at most $\max(N, M) + 1$ nodes.

### 🧠 DSA Pattern

- **Linked List Traversal with Dummy Head**
- **Simulation / Elementary Arithmetic**

### ⚠️ Common Mistakes

1. **Omitting `carry != 0` in loop condition**: If the loop only checks `l1 != null || l2 != null`, an addition like `5 + 5` would result in `[0]` instead of `[0, 1]` because the final carry is dropped.
2. **Dereferencing `null`**: Accessing `l1.val` or `l1.next` without checking `l1 != null` when lists are of unequal lengths leads to a `NullPointerException`.
3. **Returning `dummyHead` instead of `dummyHead.next`**: Returning `dummyHead` includes the extra dummy `0` prefix in the output.

### 🚀 Optimization Notes

- **Optimal Solution**: This implementation is already optimal in time ($\mathcal{O}(\max(N, M))$) and space ($\mathcal{O}(\max(N, M))$).
- **Clean Structure**: Handling list length imbalances and leftover carries inside a single unified `while` loop is concise and avoids redundant post-processing loops.
