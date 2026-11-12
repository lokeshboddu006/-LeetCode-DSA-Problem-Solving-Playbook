class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        int n = nums.length;
        int maxLen = 1, ic = 1, dc = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                ic++;
                dc = 1;
            } else if (nums[i] < nums[i - 1]) {
                dc++;
                ic = 1;
            } else {
                ic = 1;
                dc = 1;
            }
            maxLen = Math.max(maxLen, Math.max(ic, dc));
        }
        return maxLen;
    }
}