class Solution {
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        int i=0, j = n-1;
        while(i < j){
            if(nums[i] + nums[j] == target)
                return new int[]{i+1, j+1};
            else if(nums[i] + nums[j] < target)
                i++;
            else
                j--;
        }
        return new int[]{-1, -1};
    }
}