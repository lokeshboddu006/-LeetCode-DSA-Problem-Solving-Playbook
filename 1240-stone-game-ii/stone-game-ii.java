class Solution {
    public int stoneGameII(int[] p) {
        int n = p.length;        
        int[] s = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            s[i] = s[i + 1] + p[i];
        }
        int[][] f = new int[n + 1][n + 1];        
        for (int i = n - 1; i >= 0; i--) {
            for (int m = 1; m <= n; m++) {
                if (i + 2 * m >= n) {
                    f[i][m] = s[i];
                } else {
                    int v = Integer.MAX_VALUE;
                    for (int x = 1; x <= 2 * m; x++) {
                        v = Math.min(v, f[i + x][Math.max(m, x)]);
                    }
                    f[i][m] = s[i] - v;
                }
            }
        }
        
        return f[0][1];
    }
}
