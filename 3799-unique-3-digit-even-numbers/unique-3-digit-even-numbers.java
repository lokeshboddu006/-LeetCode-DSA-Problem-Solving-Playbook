class Solution {
    public int totalNumbers(int[] d) {
        int[] c = new int[10];
        for (int x : d) c[x]++;
        int n = 0;
        
        for (int i = 100; i < 1000; i += 2) {
            int x = i / 100, y = (i / 10) % 10, z = i % 10;
            c[x]--; c[y]--; c[z]--;
            if (c[x] >= 0 && c[y] >= 0 && c[z] >= 0) n++;
            c[x]++; c[y]++; c[z]++;
        }
        
        return n;
    }
}
