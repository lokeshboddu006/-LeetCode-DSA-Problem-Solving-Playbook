class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<n; i++){
            if(i>0 && nums[i] == nums[i-1])
                continue;
            int t = -nums[i];
            int l = i+1, h = n-1;
            while(l< h){
                int sum = nums[l] + nums[h];
                if(sum == t){
                    res.add(new ArrayList<>(Arrays.asList(nums[i], nums[l], nums[h])));
                    l++;
                    h--;
                    while(l<h && nums[l] == nums[l-1])
                        l++;
                    while(l<h && nums[h] == nums[h+1])
                        h--;
                }
                else if(sum < t)
                    l++;
                else
                    h--;
            }
        }
        return res;
    }
}