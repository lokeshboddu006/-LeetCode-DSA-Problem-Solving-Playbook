class Solution {
     private boolean check(int[] nums, int k) {
        int n = nums.length, prev = 0;
        for (int i = 0; i < n; i += k) {
            int cnt = 0;
            int mini = nums[i], maxi = nums[i];
            for (int j = i; j < i + k - 1; j++) {
                if (nums[j] > nums[j + 1])
                    cnt++;
                mini = Math.min(mini, nums[j + 1]);
                maxi = Math.max(maxi, nums[j + 1]);
            }
            if (nums[i + k - 1] > nums[i])
                cnt++;
            if (cnt > 1 || prev > mini)
                return false;
            prev = maxi;
        }
        return true;
    }
    public int sortableIntegers(int[] nums) {
        int n = nums.length, ans = 0;
        for (int k = 1; k * k <= n; k++) {
            if (n % k == 0) {
                if (check(nums, k))
                    ans += k;
                int other = n / k;
                if (other != k && check(nums, other))
                    ans += other;
            }
        }
        return ans;
    }
}