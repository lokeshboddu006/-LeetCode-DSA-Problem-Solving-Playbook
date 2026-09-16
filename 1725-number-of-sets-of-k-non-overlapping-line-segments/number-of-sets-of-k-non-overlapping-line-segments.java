class Solution {
    private static final long MOD = 1_000_000_007L;
    private long modPow(long base, long exp) {
        long result = 1;
        while (exp > 0) {
            if ((exp & 1L) != 0) {
                result = result * base % MOD;
            }
            base = base * base % MOD;
            exp >>= 1;
        }
        return result;
    }
    public int numberOfSets(int n, int k) {
        long N = n + k - 1L;
        long R = 2L * k;
        R = Math.min(R, N - R);
        long numerator = 1;
        long denominator = 1;
        for (long i = 1; i <= R; i++) {
            numerator = numerator * (N - R + i) % MOD;
            denominator = denominator * i % MOD;
        }
        long inverseDenominator = modPow(denominator, MOD - 2);
        return (int) (numerator * inverseDenominator % MOD);
    }
}