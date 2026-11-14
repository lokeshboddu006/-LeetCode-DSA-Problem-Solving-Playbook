
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int ans = Integer.MAX_VALUE;
        for (int size = l; size <= r; size++) {
            int sum = 0;
            for (int i = 0; i < size; i++) {
                sum += nums.get(i);
            }
            if (sum > 0) {
                ans = Math.min(ans, sum);
            }
            for (int i = size; i < n; i++) {
                sum += nums.get(i);
                sum -= nums.get(i - size);

                if (sum > 0) {
                    ans = Math.min(ans, sum);
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}