class Solution {
    int n, m = -1;
    String t, r = "";
    int[] c = new int[26];

    public String lexPalindromicPermutation(String s, String target) {
        n = s.length(); t = target;
        for (char x : s.toCharArray()) c[x - 'a']++;
        int o = 0;
        for (int i = 0; i < 26; i++) {
            if (c[i] % 2 != 0) { o++; m = i; }
            c[i] /= 2;
        }
        if (o > 1) return "";
        f(0, new char[(n + 1) / 2], false);
        return r;
    }

    boolean f(int i, char[] p, boolean g) {
        if (i == p.length) {
            StringBuilder b = new StringBuilder();
            for (int k = 0; k < n / 2; k++) b.append(p[k]);
            if (n % 2 != 0) b.append(p[n / 2]);
            for (int k = n / 2 - 1; k >= 0; k--) b.append(p[k]);
            String s = b.toString();
            if (s.compareTo(t) > 0) { r = s; return true; }
            return false;
        }
        if (n % 2 != 0 && i == n / 2) {
            p[i] = (char) ('a' + m);
            if (!g && p[i] < t.charAt(i)) return false;
            return f(i + 1, p, g || (p[i] > t.charAt(i)));
        }
        for (int j = 0; j < 26; j++) {
            if (c[j] > 0) {
                char ch = (char) ('a' + j);
                if (!g && ch < t.charAt(i)) continue;
                c[j]--; p[i] = ch;
                if (f(i + 1, p, g || (ch > t.charAt(i)))) return true;
                c[j]++;
            }
        }
        return false;
    }
}
