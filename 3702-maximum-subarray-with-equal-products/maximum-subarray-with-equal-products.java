class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length, m = 1;
        for (int i = 0; i < n; i++) {
            int p = nums[i], g = nums[i], l = nums[i];
            for (int j = i + 1; j < n; j++) {
                p *= nums[j];
                if (p > 25200) break;
                int x = g, y = nums[j];
                while (y != 0) {
                    int t = y;
                    y = x % y;
                    x = t;
                }
                g = x;
                x = l;
                y = nums[j];
                while (y != 0) {
                    int t = y;
                    y = x % y;
                    x = t;
                }
                l = (l * nums[j]) / x;
                if (l * g == p) {
                    m = Math.max(m, j - i + 1);
                }
            }
        }
        return m;
    }
}
