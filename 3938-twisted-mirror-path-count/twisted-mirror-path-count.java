class Solution {
    static final int[][][] mat = {{{1, 1}, {1, 1}}, {{0, 1}, {1, 0}}};
    static final int MOD = 1000000007;
    public int uniquePaths(int[][] A) {
        int n = A[0].length, left = 0;
        int[] dp = new int[n];
        dp[0] = 1;
        for (int[] row : A) {
            left = 0;
            for (int j = 0; j < n; j++) {
                int top = dp[j], c = row[j];
                dp[j] = (left * mat[c][1][0] + top * mat[c][1][1]) % MOD;
                left  = (left * mat[c][0][0] + top * mat[c][0][1]) % MOD;
            }
        }
        return left;
    }
}