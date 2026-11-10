class Solution {
    public int climbStairs(int n, int[] costs) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        int c = 0;
        for(int i=1;i<=n;i++) {
            int min = Integer.MAX_VALUE;
            min = Math.min(min,c+1+costs[i-1]);
            if(b != Integer.MAX_VALUE) {
                min = Math.min(min,b+4+costs[i-1]);
            }
            if(a != Integer.MAX_VALUE) {
                min = Math.min(min,a+9+costs[i-1]);
            }
            a = b;
            b = c;
            c = min;
        }
        return c;
    }
}