class Solution {
    public int minOperations(int[] nums) {
         int n = nums.length, shift = 0;
        while (nums[shift] != 0) shift++;

        boolean along = true, against = true;
        for (int k = 0; k < n && (along || against); k++) {
            int ring = nums[(shift + k) % n];  
            along &= ring == k;  
            against &= ring == (n - k) % n; 
        }
        if (along) return Math.min(shift, n - shift + 2); 
        if (against) return Math.min(shift + 2, n - shift); 
        return -1;
    }
}