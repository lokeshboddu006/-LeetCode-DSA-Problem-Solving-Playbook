class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] c = new int[n][n]; 
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int l = j - i + 1;
                c[i][j] = l;
                for (int d = 1; d < l; d++) {
                    if (l % d == 0) {
                        int x = 0;
                        for (int r = 0; r < d; r++) {
                            int a = i + r;
                            int b = j - d + 1 + r;
                            while (a < b) {
                                if (s.charAt(a) != s.charAt(b)) x++;
                                a += d;
                                b -= d;
                            }
                        }
                        c[i][j] = Math.min(c[i][j], x);
                    }
                }
            }
        }
        int[][] f = new int[n + 1][k + 1];
        for (int[] w : f) Arrays.fill(w, n);
        f[0][0] = 0;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                for (int p = 0; p < i - 1; p++) {
                    f[i][j] = Math.min(f[i][j], f[p][j - 1] + c[p][i - 1]);
                }
            }
        }        
        return f[n][k];
    }
}
