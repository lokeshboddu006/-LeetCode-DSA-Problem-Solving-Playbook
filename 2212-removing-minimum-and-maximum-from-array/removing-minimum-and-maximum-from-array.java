class Solution {
    public int minimumDeletions(int[] a) {
        int n = a.length, l = 0, h = 0, i = 0;
        for (; i < n; i++) {
            if (a[i] < a[l]) l = i;
            if (a[i] > a[h]) h = i;
        }
        i = Math.min(l, h);
        int j = Math.max(l, h);
        return Math.min(j + 1, Math.min(n - i, i + 1 + n - j));
    }
}
