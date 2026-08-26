class Solution {
    public String convert(String s, int r) {
        if (r == 1 || s.length() <= r) return s;
        StringBuilder[] a = new StringBuilder[r];
        for (int i = 0; i < r; i++) a[i] = new StringBuilder();
        int k = 0, d = 1;
        for (char c : s.toCharArray()) {
            a[k].append(c);
            if (k == 0) d = 1;
            else if (k == r - 1) d = -1;
            k += d;
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder b : a) res.append(b);
        return res.toString();
    }
}