class Solution {
    public boolean uniformArray(int[] A) {
        int min = A[0], odd = 0;
        for (int x : A) {
            min = Math.min(min, x);
            odd |= x & 1;
        }
        return (min & 1) == odd;
    }
}