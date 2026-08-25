class Solution {

    public int missingMultiple(int[] nums, int k) {
        boolean[] arr = new boolean[201];
        for (int i : nums) {
            arr[i] = true;
        }
        int ans = k;
        while (arr[ans]) {
            ans += k;
        }
        return ans;
    }
}