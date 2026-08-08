class Solution {
    public int[] validSequence(String a, String b) {
        int n = a.length(), m = b.length(), l[] = new int[m], r[] = new int[m], j = 0, u = 0;
        for (int i = m - 1, k = n - 1; i >= 0; i--) {
            while (k >= 0 && a.charAt(k) != b.charAt(i)) k--;
            l[i] = k--;
        }
        for (int i = 0; i < m; i++) {
            while (j < n && a.charAt(j) != b.charAt(i) && (u == 1 || (i < m - 1 && j >= l[i + 1]))) j++;
            if (j == n) return new int[0];
            if (a.charAt(j) != b.charAt(i)) u = 1;
            r[i] = j++;
        }
        return r;
    }
}