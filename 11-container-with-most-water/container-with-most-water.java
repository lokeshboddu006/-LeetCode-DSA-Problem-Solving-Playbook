class Solution {
    public int maxArea(int[] nums) {
        int n = nums.length;
        int res = 0;

        /**for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                int len = Math.min(nums[i], nums[j]);
                int wid = j - i;
                int area = len * wid;
                res = Math.max(res, area);
            }
        }
        return res;**/

        int low = 0, high = n - 1;
        while(low < high){
            int wid = high - low;
            int len = Math.min(nums[low], nums[high]);
            int area = wid * len;
            res = Math.max(res, area);
            if(nums[low] < nums[high])
                low++;
            else
                high--;
        }
        return res;
    }
}