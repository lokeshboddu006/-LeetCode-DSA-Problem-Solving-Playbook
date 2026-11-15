class Solution {
    public int numOfSubarrays(int[] arr) {
        final int mod = 1000000007;
        int even = 1, odd = 0;
        long res = 0;
        int sum = 0;
        for (int x : arr) {
            sum = (sum + x) % 2;
            if (sum == 1) {
                res = (res + even) % mod;
                odd++;
            } else {
                res = (res + odd) % mod;
                even++;
            }
        }
        return (int) res;
    }
}