class Solution {
    public int myAtoi(String s) {
        int i = 0, n = s.length(), g = 1;
        long r = 0;
        while (i < n && s.charAt(i) == ' ') i++;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            g = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }
        while (i < n && Character.isDigit(s.charAt(i))) {
            r = r * 10 + (s.charAt(i) - '0');
            if (g * r > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (g * r < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            i++;
        }
        return (int) (g * r);
    }
}