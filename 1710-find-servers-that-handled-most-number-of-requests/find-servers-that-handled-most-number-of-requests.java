import java.util.*;
class Solution {
    public List<Integer> busiestServers(int k, int[] a, int[] t) {
        int[] c = new int[k];
        TreeSet<Integer> free = new TreeSet<>();
        for (int i = 0; i < k; i++) free.add(i);
        PriorityQueue<int[]> busy = new PriorityQueue<>((x, y) -> x[0] - y[0]);
        for (int i = 0; i < a.length; i++) {
            int arrival = a[i];
            while (!busy.isEmpty() && busy.peek()[0] <= arrival) {
                free.add(busy.poll()[1]);
            }
            if (free.isEmpty()) continue;
            
            Integer s = free.ceiling(i % k);
            if (s == null) s = free.first();
            c[s]++;
            free.remove(s);
            busy.offer(new int[]{arrival + t[i], s});
        }
        int m = 0;
        for (int x : c) m = Math.max(m, x);
        List<Integer> r = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (c[i] == m) r.add(i);
        }
        return r;
    }
}
