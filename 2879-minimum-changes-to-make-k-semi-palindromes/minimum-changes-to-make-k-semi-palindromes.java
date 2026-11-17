class Solution {
    private int check(String s, int start, int d, int num) {
        int changes = 0;
        for (int offset = 0; offset < d; offset++) {
            for (int i = start + offset, j = i + d * (num - 1); i < j; i += d, j -= d) {
                if (s.charAt(i) != s.charAt(j)) {
                    changes++;
                }
            }
        }
        return changes;
    }
    private int calculate(String s, int i, int j, int[][] cache) {
        if (cache[i][j] >= 0) {
            return cache[i][j];
        }
        int len = j - i + 1;
        if (len == 1) {
            cache[i][j] = Integer.MAX_VALUE;
            return cache[i][j];
        }
        cache[i][j] = len - 1;
        for (int d = 1; d * d <= len; d++) {
            if (len % d == 0) {
                int num = len / d;
                cache[i][j] = Math.min(cache[i][j], check(s, i, d, num));
                if (d != num && d != 1) {
                    cache[i][j] = Math.min(cache[i][j], check(s, i, num, d));
                }
            }
        }
        return cache[i][j];
    }
    private int dfs(String s, int i, int k, int[][] dp, int[][] cache) {
        int n = s.length();
        if (i >= n) {
            return k == 0 ? 0 : Integer.MAX_VALUE;
        }
        if (dp[i][k] >= 0) {
            return dp[i][k];
        }
        if (n - i < k + k) {
            dp[i][k] = Integer.MAX_VALUE;
            return dp[i][k];
        }
        if (k == 1) {
            dp[i][k] = calculate(s, i, n - 1, cache);
            return dp[i][k];
        }
        if (k == 0) {
            dp[i][k] = Integer.MAX_VALUE;
            return dp[i][k];
        }
        dp[i][k] = Integer.MAX_VALUE;
        for (int j = i; j < n; j++) {
            int temp = dfs(s, j + 1, k - 1, dp, cache);
            if (temp < Integer.MAX_VALUE) {
                int changes = calculate(s, i, j, cache);
                if (changes < Integer.MAX_VALUE) {
                    dp[i][k] = Math.min(dp[i][k], temp + changes);
                }
            }
        }
        return dp[i][k];
    }
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] dp = new int[n][k + 1];
        int[][] cache = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
            Arrays.fill(cache[i], -1);
        }
        return dfs(s, 0, k, dp, cache);
    }
}