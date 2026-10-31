class Solution {
    public int uniquePaths(int[][] grid) {
        final int mod = 1_000_000_007;
        int[] arr = new int[grid[0].length]; 
        arr[0] = 1;
        for (int[] row : grid) {
            int ri = 0;  
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 1) { 
                    int t = ri;
                    ri = arr[j];
                    arr[j] = t;
                } else {  
                    ri = arr[j] = (ri + arr[j]) % mod;
                }
            }
        }
        return arr[arr.length - 1];
    }
}