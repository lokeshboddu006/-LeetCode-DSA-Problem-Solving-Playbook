import java.util.*;

class Solution {
    public int[] resultArray(int[] a) {
        List<Integer> b = new ArrayList<>(), c = new ArrayList<>();
        b.add(a[0]);
        c.add(a[1]);
        
        for (int i = 2; i < a.length; i++) {
            if (b.get(b.size() - 1) > c.get(c.size() - 1)) {
                b.add(a[i]);
            } else {
                c.add(a[i]);
            }
        }
        
        int[] r = new int[a.length];
        int k = 0;
        for (int x : b) r[k++] = x;
        for (int x : c) r[k++] = x;
        
        return r;
    }
}