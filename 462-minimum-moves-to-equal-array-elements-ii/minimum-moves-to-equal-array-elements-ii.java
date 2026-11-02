class Solution {
    private static final Random RNG = new Random();
    public int minMoves2(int[] nums) {
        int med = quickselect(nums, nums.length / 2), moves = 0;
        for (int x : nums) moves += Math.abs(x - med);
        return moves;
    }
    private static int quickselect(int[] a, int k) {
        int lo = 0, hi = a.length - 1;
        while (true) {
            int pivot = a[lo + RNG.nextInt(hi - lo + 1)];
            int lt = lo, i = lo, gt = hi;  
            while (i <= gt) {
                if (a[i] < pivot) swap(a, lt++, i++);
                else if (a[i] > pivot) swap(a, i, gt--);
                else i++;
            }
            if (k < lt) hi = lt - 1;
            else if (k > gt) lo = gt + 1;
            else return pivot;
        }
    }
    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}