class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length(), c = 0, e = -1;
        for (int i = 0; i < 2 * n; i++) {
            int l = i / 2, r = l + i % 2;
            while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                if (r - l + 1 >= k) {
                    if (l > e) {
                        c++;
                        e = r;
                    }
                    break;
                }
                l--; r++;
            }
        }
        return c;
    }
}
