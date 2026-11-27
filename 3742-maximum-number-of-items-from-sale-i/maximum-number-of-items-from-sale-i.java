class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int[][] val = items;

        int n = items.length;

        int[] bonus = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    bonus[i]++;
                }
            }
        }

        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {

            int cost = items[i][1];
            int[] ndp = dp.clone();
            for (int b = budget; b >= cost; b--) {
                ndp[b] = Math.max(ndp[b],dp[b - cost] + 1 + bonus[i]);
            }
            dp = ndp; 
            for (int b = cost; b <= budget; b++) {
                dp[b] = Math.max(dp[b],dp[b - cost] + 1
                );
            }
        }
        int ans = 0;
        for (int x : dp) ans = Math.max(ans, x);
        return ans;
    }
}