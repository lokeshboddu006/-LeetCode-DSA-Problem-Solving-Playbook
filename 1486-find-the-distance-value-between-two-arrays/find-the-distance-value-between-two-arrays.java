class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        TreeSet<Integer> sorted = new TreeSet<>();
        for (int b : arr2) sorted.add(b);
        return (int) Arrays.stream(arr1).filter(a -> {
            Integer b = sorted.ceiling(a - d); 
            return b == null || b > a + d;
        }).count();
    }
}