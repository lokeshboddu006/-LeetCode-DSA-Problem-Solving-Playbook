class Solution {
    public double findMedianSortedArrays(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        int total = n + m;
        int[] merged = new int[total];
        int i = 0, j = 0, k = 0;
        
        while (i < n && j < m) {
            if (a[i] < b[j]) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }
        
        while (i < n) {
            merged[k++] = a[i++];
        }
        
        while (j < m) {
            merged[k++] = b[j++];
        }
        
        if (total % 2 == 1) {
            return merged[total / 2];
        } else {
            return (merged[(total / 2) - 1] + merged[total / 2]) / 2.0;
        }
    }
}