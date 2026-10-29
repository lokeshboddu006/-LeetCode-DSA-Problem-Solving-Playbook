class Solution {
    int n, LOG = 17, timer;
    List<Integer>[] g;
    int[][] up;
    int[] tin, tout, dep, bit;
    char[] s;
    void add(int i, int x) {
        for (; i <= n; i += i & -i) bit[i] ^= x;
    }
    int sum(int i) {
        int x = 0;
        for (; i > 0; i -= i & -i) x ^= bit[i];
        return x;
    }
    void update(int u, int x) {
        add(tin[u], x);
        add(tout[u]+1, x);
    }
    void dfs(int u, int p) {
        tin[u] = ++timer;
        up[0][u] = p;
        for (int j = 1; j < LOG; j++)
            up[j][u] = up[j-1][up[j-1][u]];
        for (int v : g[u]) if (v != p) {
            dep[v] = dep[u]+1;
            dfs(v, u);
        }
        tout[u] = timer;
    }
    int lca(int u, int v) {
        if (dep[u] < dep[v]) {
            int t = u; u = v; v = t;
        }
        for (int j = LOG-1; j >= 0; j--)
            if (dep[u]-(1<<j) >= dep[v]) u = up[j][u];
        if (u == v) return u;
        for (int j = LOG-1; j >= 0; j--)
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        return up[0][u];
    }
    public List<Boolean> palindromePath(int N, int[][] edges,
                                        String S, String[] queries) {
        n = N;
        s = S.toCharArray();
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        up = new int[LOG][n];
        tin = new int[n]; tout = new int[n]; dep = new int[n];
        bit = new int[n+2];
        dfs(0, 0);
        for (int u = 0; u < n; u++) update(u, 1 << (s[u]-'a'));
        List<Boolean> ans = new ArrayList<>();
        for (String q : queries) {
            String[] a = q.split(" ");
            int u = Integer.parseInt(a[1]);

            if (a[0].equals("update")) {
                char c = a[2].charAt(0);
                update(u, (1 << (s[u]-'a')) ^ (1 << (c-'a')));
                s[u] = c;
            } else {
                int v = Integer.parseInt(a[2]), w = lca(u, v);
                int m = sum(tin[u]) ^ sum(tin[v]) ^ (1 << (s[w]-'a'));
                ans.add((m & (m-1)) == 0);
            }
        }
        return ans;
    }
}