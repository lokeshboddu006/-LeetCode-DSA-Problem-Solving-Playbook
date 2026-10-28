class Solution {
    public int minCost(int[][] grid) {
         int n = grid[0].length;
        boolean[][] dp = new boolean[n][1024];  
        dp[0][0] = true;
        for (int[] row : grid)
            for (int j = 0; j < n; j++) {
                boolean[] next = new boolean[1024];
                for (int x = 0; x < 1024; x++)
                    if (dp[j][x] || j > 0 && dp[j - 1][x]) next[x ^ row[j]] = true;  
                dp[j] = next;
            }
        int x = 0;
        while (!dp[n - 1][x]) x++;
        return x;
    }
}