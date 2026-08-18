class Solution {
    public int removeDuplicates(int[] nums) {
       int n = nums.length;
       int h = 0, o = 1;
       while(o < n){
        if(nums[o] == nums[o-1]){
            o++;
            continue;
        }
        h++;
        nums[h] = nums[o];
        o++;
        } 
        return h+1;
    }
}