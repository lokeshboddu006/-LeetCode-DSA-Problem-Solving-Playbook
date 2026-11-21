class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        java.util.List<Integer>[] g = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new java.util.ArrayList<>();
        for (int i = 1; i < n; i++) g[parent[i]].add(i);
        int[] q = parent.clone();
        int[] l = new int[26];
        int[] b = new int[n];
        java.util.Arrays.fill(l, -1);
        int[] k = new int[n];
        boolean[] v = new boolean[n];
        int t = 0;
        k[t++] = 0;
        while (t > 0) {
            int u = k[t - 1];
            int c = s.charAt(u) - 'a';
            if (!v[u]) {
                v[u] = true;
                if (l[c] != -1) q[u] = l[c];
                b[u] = l[c];
                l[c] = u;
                for (int w : g[u]) k[t++] = w;
            } else {
                t--;
                l[c] = b[u];
            }
        }
        int[] a = new int[n];
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = 1;
            if (q[i] != -1) d[q[i]]++;
        }
        int h = 0, r = 0;
        for (int i = 0; i < n; i++) {
            if (d[i] == 0) k[r++] = i;
        }

        while (h < r) {
            int x = k[h++];
            int y = q[x];
            if (y != -1) {
                a[y] += a[x];
                d[y]--;
                if (d[y] == 0) k[r++] = y;
            }
        }

        return a;
    }
}
