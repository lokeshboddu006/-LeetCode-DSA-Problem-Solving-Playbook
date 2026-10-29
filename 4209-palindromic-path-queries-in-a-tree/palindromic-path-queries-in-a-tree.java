class Solution {
    int[] in, out, dep, pm, bl;
    int[][] up;
    int t, bs;
    char[] ch;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        in = new int[n];
        out = new int[n];
        dep = new int[n];
        up = new int[n][16];
        ch = s.toCharArray();
        pm = new int[n + 1];
        t = 0;
        dfs(0, 0, 0, adj, 0);
        bs = (int) Math.sqrt(n) + 1;
        bl = new int[(n / bs) + 2];
        List<Boolean> ans = new ArrayList<>();
        for (String q : queries) {
            String[] p = q.split(" ");
            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);
                int diff = (1 << (ch[u] - 'a')) ^ (1 << (c - 'a'));
                int L = in[u], R = out[u];
                int sb = L / bs, eb = R / bs;
                if (sb == eb) {
                    for (int i = L; i <= R; i++) pm[i] ^= diff;
                } else {
                    for (int i = L; i < (sb + 1) * bs; i++) pm[i] ^= diff;
                    for (int b = sb + 1; b < eb; b++) bl[b] ^= diff;
                    for (int i = eb * bs; i <= R; i++) pm[i] ^= diff;
                }
                ch[u] = c;
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);
                int lca = lca(u, v);
                int mu = pm[in[u]] ^ bl[in[u] / bs];
                int mv = pm[in[v]] ^ bl[in[v] / bs];
                int ml = 1 << (ch[lca] - 'a');
                int mask = mu ^ mv ^ ml;
                ans.add((mask & (mask - 1)) == 0);
            }
        }
        return ans;
    }
    void dfs(int u, int p, int d, List<List<Integer>> adj, int m) {
        in[u] = ++t;
        dep[u] = d;
        up[u][0] = p;
        for (int i = 1; i < 16; i++) {
            up[u][i] = up[up[u][i - 1]][i - 1];
        }
        int nm = m ^ (1 << (ch[u] - 'a'));
        pm[in[u]] = nm;
        for (int v : adj.get(u)) {
            if (v != p) dfs(v, u, d + 1, adj, nm);
        }
        out[u] = t;
    }
    int lca(int u, int v) {
        if (dep[u] < dep[v]) {
            int tmp = u; u = v; v = tmp;
        }
        for (int i = 15; i >= 0; i--) {
            if (dep[u] - (1 << i) >= dep[v]) u = up[u][i];
        }
        if (u == v) return u;
        for (int i = 15; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }
        return up[u][0];
    }
}