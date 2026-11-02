class Solution {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int med = nums[nums.length / 2];
        long mo= 0;
        for (int num : nums) {
            mo+= Math.abs(num - med);
        }
        return (int) mo;
    }
}