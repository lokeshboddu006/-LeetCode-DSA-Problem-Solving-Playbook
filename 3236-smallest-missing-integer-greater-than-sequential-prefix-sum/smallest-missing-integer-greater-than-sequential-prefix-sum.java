class Solution {
    public int missingInteger(int[] nums) {
        int s = nums[0], i = 1;
        while (i < nums.length && nums[i] == nums[i - 1] + 1) s += nums[i++];
        HashSet<Integer> h = new HashSet<>();
        for (int x : nums) h.add(x);
        while (h.contains(s)) s++;
        return s;
    }
}