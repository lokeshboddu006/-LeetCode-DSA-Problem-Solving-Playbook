import java.util.Arrays;

class Solution {
    public int minSumOfLengths(int[] a, int t) {
        int n = a.length;
        int[] d = new int[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        
        int r = Integer.MAX_VALUE;
        int s = 0;
        int l = 0;
        
        for (int i = 0; i < n; i++) {
            s += a[i];
            while (s > t) {
                s -= a[l++];
            }
            if (s == t) {
                int len = i - l + 1;
                if (l > 0 && d[l - 1] != Integer.MAX_VALUE) {
                    r = Math.min(r, len + d[l - 1]);
                }
                d[i] = len;
            }
            if (i > 0) {
                d[i] = Math.min(d[i], d[i - 1]);
            }
        }
        return r == Integer.MAX_VALUE ? -1 : r;
    }
}
