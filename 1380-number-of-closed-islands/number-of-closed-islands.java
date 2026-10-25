class Solution {
    int m, n;
    public int closedIsland(int[][] g) {
        m = g.length; 
        n = g[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    if (g[i][j] == 0) d(g, i, j);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 0) {
                    d(g, i, j);
                    ans++;
                }
            }
        }
        return ans;
    }
    void d(int[][] g, int r, int c) {
        if (r < 0 || c < 0 || r >= m || c >= n || g[r][c] == 1) return;
        g[r][c] = 1;
        d(g, r + 1, c);
        d(g, r - 1, c);
        d(g, r, c + 1);
        d(g, r, c - 1);
    }
}
