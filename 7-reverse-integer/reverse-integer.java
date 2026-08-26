class Solution {
    public int reverse(int x) {
        long r = 0;
        while (x != 0) {
            r = r * 10 + x % 10;
            x /= 10;
        }
        return (r < Integer.MIN_VALUE || r > Integer.MAX_VALUE) ? 0 : (int) r;
    }
}