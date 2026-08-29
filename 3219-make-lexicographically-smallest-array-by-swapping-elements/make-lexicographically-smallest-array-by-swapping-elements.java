import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] a, int k) {
        int n = a.length, r[] = new int[n];
        Integer[] p = new Integer[n];
        for (int i = 0; i < n; i++) p[i] = i;
        
        Arrays.sort(p, (i, j) -> Integer.compare(a[i], a[j]));

        for (int i = 0; i < n; ) {
            int j = i + 1;
            while (j < n && a[p[j]] - a[p[j - 1]] <= k) j++;
            
            Integer[] x = Arrays.copyOfRange(p, i, j);
            Arrays.sort(x);
            
            for (int t = 0; t < x.length; t++) {
                r[x[t]] = a[p[i + t]];
            }
            i = j;
        }
        return r;
    }
}