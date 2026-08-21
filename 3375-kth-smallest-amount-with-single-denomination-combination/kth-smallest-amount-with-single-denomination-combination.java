class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long minCoin = coins[0];
        for (int c : coins) {
            minCoin = Math.min(minCoin, c);
        }

        long low = 1;
        long high = minCoin * k;
        long ans = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countMultiples(coins, mid) >= k) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    private long countMultiples(int[] coins, long m) {
        int n = coins.length;
        long count = 0;

        // Inclusion-Exclusion Principle using bitmasks
        for (int mask = 1; mask < (1 << n); mask++) {
            long currentLcm = 1;
            int bitCount = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bitCount++;
                    currentLcm = lcm(currentLcm, coins[i]);
                    if (currentLcm > m) break; // Avoid overflow / unnecessary computation
                }
            }

            if (currentLcm <= m) {
                if (bitCount % 2 == 1) {
                    count += m / currentLcm;
                } else {
                    count -= m / currentLcm;
                }
            }
        }
        return count;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}