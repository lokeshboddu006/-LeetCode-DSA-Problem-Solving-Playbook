import java.util.*;

class Solution {
    public long minOperations(int[] a, int x, int k) {
        int n = a.length;
        long[] w = new long[n - x + 1];
        TreeMap<Integer, Integer> l = new TreeMap<>();
        TreeMap<Integer, Integer> r = new TreeMap<>();
        int lSz = 0, rSz = 0;
        long s = 0, t = 0;

        for (int i = 0; i < n; i++) {
            if (lSz == 0 || a[i] <= l.lastKey()) {
                l.put(a[i], l.getOrDefault(a[i], 0) + 1);
                lSz++; s += a[i];
            } else {
                r.put(a[i], r.getOrDefault(a[i], 0) + 1);
                rSz++; t += a[i];
            }

            if (lSz > rSz + 1) {
                int m = l.lastKey();
                if (l.put(m, l.get(m) - 1) == 1) l.remove(m);
                lSz--; s -= m;
                r.put(m, r.getOrDefault(m, 0) + 1);
                rSz++; t += m;
            } else if (rSz > lSz) {
                int m = r.firstKey();
                if (r.put(m, r.get(m) - 1) == 1) r.remove(m);
                rSz--; t -= m;
                l.put(m, l.getOrDefault(m, 0) + 1);
                lSz++; s += m;
            }

            if (i >= x) {
                int o = a[i - x];
                if (l.containsKey(o)) {
                    if (l.put(o, l.get(o) - 1) == 1) l.remove(o);
                    lSz--; s -= o;
                } else {
                    if (r.put(o, r.get(o) - 1) == 1) r.remove(o);
                    rSz--; t -= o;
                }

                if (lSz > rSz + 1) {
                    int m = l.lastKey();
                    if (l.put(m, l.get(m) - 1) == 1) l.remove(m);
                    lSz--; s -= m;
                    r.put(m, r.getOrDefault(m, 0) + 1);
                    rSz++; t += m;
                } else if (rSz > lSz) {
                    int m = r.firstKey();
                    if (r.put(m, r.get(m) - 1) == 1) r.remove(m);
                    rSz--; t -= m;
                    l.put(m, l.getOrDefault(m, 0) + 1);
                    lSz++; s += m;
                }
            }

            if (i >= x - 1) {
                long m = l.lastKey();
                w[i - x + 1] = (m * lSz - s) + (t - m * rSz);
            }
        }

        long[][] d = new long[n + 1][k + 1];
        for (int i = 0; i <= n; i++) Arrays.fill(d[i], (long) 1e15);
        d[0][0] = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                if (d[i][j] >= 1e15) continue;
                d[i + 1][j] = Math.min(d[i + 1][j], d[i][j]);
                if (j < k && i + x <= n) {
                    d[i + x][j + 1] = Math.min(d[i + x][j + 1], d[i][j] + w[i]);
                }
            }
        }
        return d[n][k];
    }
}
