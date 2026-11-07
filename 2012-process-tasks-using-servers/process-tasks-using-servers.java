import java.util.PriorityQueue;

class Solution {
    public int[] assignTasks(int[] s, int[] t) {
        PriorityQueue<int[]> a = new PriorityQueue<>((x, y) -> x[0] != y[0] ? x[0] - y[0] : x[1] - y[1]);
        PriorityQueue<int[]> b = new PriorityQueue<>((x, y) -> x[0] != y[0] ? Integer.compare(x[0], y[0]) : (x[1] != y[1] ? x[1] - y[1] : x[2] - y[2]));
        
        for (int i = 0; i < s.length; i++) {
            a.offer(new int[]{s[i], i});
        }
        
        int[] r = new int[t.length];
        int c = 0;
        
        for (int j = 0; j < t.length; j++) {
            c = Math.max(c, j);
            if (a.isEmpty()) {
                c = Math.max(c, b.peek()[0]);
            }
            while (!b.isEmpty() && b.peek()[0] <= c) {
                int[] p = b.poll();
                a.offer(new int[]{p[1], p[2]});
            }
            int[] u = a.poll();
            r[j] = u[1];
            b.offer(new int[]{c + t[j], u[0], u[1]});
        }
        return r;
    }
}
