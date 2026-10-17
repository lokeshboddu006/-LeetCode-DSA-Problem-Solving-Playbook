class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        long[] keyed = new long[n];  
        for (int i = 0; i < n; i++) keyed[i] = (long) digitSum(nums[i]) << 32 | nums[i];
        Arrays.sort(keyed);
        Map<Integer, Integer> rank = new HashMap<>();  
        for (int k = 0; k < n; k++) rank.put((int) keyed[k], k);
        int[] home = new int[n];  
        for (int i = 0; i < n; i++) home[i] = rank.get(nums[i]);
        int swaps = 0;
        for (int i = 0; i < n; i++) {
            while (home[i] != i) {  
                int j = home[i];
                home[i] = home[j];
                home[j] = j;
                swaps++;
            }
        }
        return swaps;
    }
    private int digitSum(int x) {
        int s = 0;
        for (; x > 0; x /= 10) s += x % 10;
        return s;
    }
}