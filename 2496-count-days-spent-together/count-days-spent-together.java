class Solution {
    public int countDaysTogether(String a, String b, String c, String d) {
        return Math.max(0, Math.min(f(b), f(d)) - Math.max(f(a), f(c)) + 1);
    }
    int f(String s) {
        var m = (s.charAt(0) - '0') * 10 + (s.charAt(1) - '0');
        var q = (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0');
        return (275 * m) / 9 - 30 - 2 * ((m + 9) / 12) + q;
    }
}