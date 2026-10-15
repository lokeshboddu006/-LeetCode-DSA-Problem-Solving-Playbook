class Solution {
    public int sumCounts(List<Integer> nums) {
        int sum = 0;
        int len = nums.size();
        for (int i = 0; i < len; i++) {
            boolean[] seen = new boolean[101];
            int distinctCount = 0;
            for (int j = i; j < len; j++) {
                int num = nums.get(j);
                if (!seen[num]) {
                    seen[num] = true;
                    distinctCount++;
                }
                sum += distinctCount * distinctCount;
            }
        }
        return sum;
    }
}