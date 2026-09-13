import java.util.*;

class Solution {
    public int largestOverlap(int[][] a, int[][] b) {
        int n = a.length, m = 0;
        List<Integer> x = new ArrayList<>(), y = new ArrayList<>();
        Map<Integer, Integer> c = new HashMap<>();
        
        for (int i = 0; i < n * n; i++) {
            if (a[i / n][i % n] == 1) x.add((i / n) * 100 + (i % n));
            if (b[i / n][i % n] == 1) y.add((i / n) * 100 + (i % n));
        }
        
        for (int i : x) {
            for (int j : y) {
                int d = i - j;
                c.put(d, c.getOrDefault(d, 0) + 1);
                m = Math.max(m, c.get(d));
            }
        }
        return m;
    }
}
