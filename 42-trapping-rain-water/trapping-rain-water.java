class Solution {
    public int trap(int[] nums) {

        /**int n = nums.length;
        if(n < 3) return 0;
        int trappedWater = 0;
        for(int i = 0; i < n; i++){
            int leftMax = 0;
            for(int j = i; j >= 0; j--)
                leftMax = Math.max(nums[j], leftMax);
            int rightMax = 0;
            for(int j = i; j < n; j++)
                rightMax = Math.max(nums[j], rightMax);
            int h = Math.min(leftMax, rightMax);
            trappedWater = trappedWater + h - nums[i];
        }
        return trappedWater;**/

        int n = nums.length;
        if(n < 3) return 0;

        int[] leftMax = new int[n];
        leftMax[0] = nums[0];

        for(int i = 1; i < n; i++)
            leftMax[i] = Math.max(leftMax[i - 1], nums[i]);

        int[] rightMax = new int[n];
        rightMax[n - 1] = nums[n - 1];

        for(int i = n - 2; i >= 0; i--)
            rightMax[i] = Math.max(rightMax[i+1], nums[i]);

        int trapWater = 0;

        for(int i = 0; i < n; i++){
            int height = Math.min(leftMax[i], rightMax[i]);
            trapWater = trapWater + height - nums[i];
        }

        return trapWater;

   }
}