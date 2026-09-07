class Solution {
    public int distinctSubseqII(String s) {
        int m = (int) 1e9 + 7;
        int[] d = new int[26];
        int a = 0;
        
        for (int i = 0; i < s.length(); ++i) {
            int j = s.charAt(i) - 'a';
            int v = (a - d[j] + 1) % m;
            a = (a + v) % m;
            d[j] = (d[j] + v) % m;
        }
        
        return (a + m) % m;
    }
}
