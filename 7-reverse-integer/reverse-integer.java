class Solution {
    public int reverse(int x) {
        int r = 0;
        while (x != 0) {
            int m = x % 10;
            x /= 10;
            if (r > Integer.MAX_VALUE / 10 || (r == Integer.MAX_VALUE / 10 && m > 7)) return 0;
            if (r < Integer.MIN_VALUE / 10 || (r == Integer.MIN_VALUE / 10 && m < -8)) return 0;
            r = r * 10 + m;
        }
        return r;
    }
}
