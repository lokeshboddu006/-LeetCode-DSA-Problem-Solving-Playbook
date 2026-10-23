class Solution {
    public int sortPermutation(int[] nums) {
        int lo = -1;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] != i) {
                lo &= nums[i];
                if (lo == 0) return 0;
            }
        return Math.max(lo, 0);
    }
}