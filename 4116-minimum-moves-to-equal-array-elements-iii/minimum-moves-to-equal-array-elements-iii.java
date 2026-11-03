class Solution {
    public int minMoves(int[] nums) {
        int mx = Integer.MIN_VALUE;
        int sm = 0;
        for(int num : nums) {
            sm += num;
            mx = Math.max(num,mx);
        }
        return nums.length*mx-sm;
    }
}