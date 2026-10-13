class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        for(int i = 0;i<n;i++ ){
            if(nums[i]<0){
                nums[i] = -1*nums[i];
            }
        }
        Arrays.sort(nums);
        long ns = 0;
        for(int i =0;i<n/2;i++){
            ns+= (long) (nums[i]*nums[i]); 
        }
        long ps =0;

        for(int i =n/2;i<n;i++){
            ps+= (long) (nums[i]*nums[i]); 
        }
       long ans = ps-ns;
       return ans;
    }
}