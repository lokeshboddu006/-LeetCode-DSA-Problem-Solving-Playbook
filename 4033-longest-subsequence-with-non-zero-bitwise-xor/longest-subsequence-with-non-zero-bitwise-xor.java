class Solution {
    public int longestSubsequence(int[] a) {
        int x = 0, z = 0, n = a.length;
        for (int v : a) {
            x ^= v;
            if (v == 0) z++;
        }
        return z == n ? 0 : (x != 0 ? n : n - 1);
    }
}
