class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        int left = (int) Arrays.stream(nums).filter(x -> x < target).count();  
        int right = (int) Arrays.stream(nums).filter(x -> x <= target).count();
        return IntStream.range(left, right).boxed().toList();
    }
}