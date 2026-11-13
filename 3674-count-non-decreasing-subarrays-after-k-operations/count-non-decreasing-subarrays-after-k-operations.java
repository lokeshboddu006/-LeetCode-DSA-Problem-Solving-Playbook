class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        Deque<Integer> starts = new ArrayDeque<>();  
        long ans = 0, water = 0;
        int n = nums.length, r = n - 1;
        for (int l = n - 1; l >= 0; l--) {
            int x = nums[l];
            while (!starts.isEmpty() && nums[starts.peekFirst()] <= x) {  
                int s = starts.pollFirst();
                int end = starts.isEmpty() ? r + 1 : starts.peekFirst();
                water += (long) (x - nums[s]) * (end - s);
            }
            starts.offerFirst(l);
            while (water > k) { 
                water -= nums[starts.peekLast()] - nums[r];
                if (starts.peekLast() == r)
                    starts.pollLast();
                r--;
            }
            ans += r - l + 1;
        }
        return ans;
    }
}