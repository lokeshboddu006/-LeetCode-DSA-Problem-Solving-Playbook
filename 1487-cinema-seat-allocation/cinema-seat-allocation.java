import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] r) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int[] x : r) {
            if (x[1] >= 2 && x[1] <= 9) {
                m.put(x[0], m.getOrDefault(x[0], 0) | (1 << (x[1] - 1)));
            }
        }
        int a = (n - m.size()) * 2;
        for (int b : m.values()) {
            boolean l = (b & 0b000011110) == 0;
            boolean rgt = (b & 0b111100000) == 0;
            boolean c = (b & 0b001111000) == 0;
            if (l && rgt) a += 2;
            else if (l || rgt || c) a += 1;
        }
        return a;
    }
}