import java.util.*;

class Solution {
    public int largestInteger(int[] a, int k) {
        int n = a.length, m = -1;
        int[] f = new int[51];
        for (int x : a) f[x]++;
        
        if (k == n) {
            for (int x : a) m = Math.max(m, x);
            return m;
        }
        if (k == 1) {
            for (int x : a) if (f[x] == 1) m = Math.max(m, x);
            return m;
        }
        
        if (f[a[0]] == 1) m = Math.max(m, a[0]);
        if (f[a[n - 1]] == 1) m = Math.max(m, a[n - 1]);
        return m;
    }
}
