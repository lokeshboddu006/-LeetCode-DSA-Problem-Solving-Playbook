class Solution {
public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int low = 1;
        int high = n;
        int ans = n;
        while(low<=high){
            int mid = low + (high-low)/2;
            if(check(nums,mid)){     
                low = mid+1;
            }else{
                ans = mid;
                high = mid-1;
            }
        }
        return ans;
    }
    public boolean check(int nums[],int len){
        HashMap<String,Integer> hm = new HashMap<>();
        int prime1 = 31;
        int prime2= 37;
        int mod1 = (int)1e9+7;
        int mod2 = (int)1e9+9; 
        long power1 = 1;
        long power2 = 1;    
        long hash1 = 0;
        long hash2 = 0;        
        for(int i = 0;i<len;i++){
            hash1 = ((hash1 * prime1)%mod1 + nums[i])%mod1;
            hash2 = ((hash2 * prime2)%mod2 + nums[i])%mod2;
        }
        for(int i = 1;i<len;i++) {
            power1 = (power1 * prime1)%mod1;
            power2 = (power2 * prime2)%mod2;
        }
        hm.put(hash1+"-"+hash2,1);
        for(int i = len;i<nums.length;i++){          
            hash1 = (((hash1 -(nums[i-len]*power1)) )%mod1 + mod1)%mod1;
            hash1 = ((hash1 * prime1)%mod1 + nums[i])%mod1;
            hash2 = (((hash2 -(nums[i-len]*power2)))%mod2 + mod2)%mod2;
            hash2 = ((hash2 * prime2)%mod2 + nums[i])%mod2;
            hm.put(hash1+"-"+hash2,hm.getOrDefault(hash1+"-"+hash2,0)+1);
        }
        for(int val : hm.values()){
            if(val==1) return false;
        }
        return true;
    }
}