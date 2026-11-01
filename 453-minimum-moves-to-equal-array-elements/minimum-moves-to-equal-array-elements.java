class Solution {
    public int minMoves(int[] nums) {
        int m= Integer.MAX_VALUE;
			for(int n:nums) m = Math.min(m,n);
			int ans=0;
			for(int n:nums) ans+=n-m;
			return ans;
    }
}