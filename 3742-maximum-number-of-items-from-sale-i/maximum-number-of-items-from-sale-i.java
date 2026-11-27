class Solution {
    int dp[][]=new int[1000][1501];
    int free[];
    int mini, n;
    int solve(int i, int tot, int[][] items) {
        if(i>=items.length) return tot/mini;
        if(dp[i][tot]!=-1) return dp[i][tot];
        int take=0;
        if(tot>=items[i][1]) {
            take=free[i]+solve(i+1, tot-items[i][1], items);
        }
        int skip=solve(i+1, tot, items);
        return dp[i][tot]=Math.max(take, skip);
    }
    public int maximumSaleItems(int[][] items, int budget) {
        mini=Integer.MAX_VALUE;
        n=items.length;
        free=new int[n];
        for(int i=0; i<1000; i++) {
            java.util.Arrays.fill(dp[i], -1);
        }
        for(int i=0; i<n; i++) {
            mini=Math.min(mini, items[i][1]);
            for(int j=0; j<n; j++) {
                if(items[j][0]%items[i][0] == 0) free[i]++;
            }
        }
        return solve(0, budget, items);
    }
}