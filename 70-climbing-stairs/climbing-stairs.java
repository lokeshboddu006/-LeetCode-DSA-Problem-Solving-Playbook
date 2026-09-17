class Solution {
    public int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }
        int fs = 1;
        int sec = 1;
        
        for (int i = 2; i <= n; i++) {
            int third = fs + sec;
            fs = sec;
            sec = third;
        }
        return sec;
    }
}