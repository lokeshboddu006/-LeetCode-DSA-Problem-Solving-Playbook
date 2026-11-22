class Solution {
    public boolean checkString(String s) {
        boolean f=false;
        for(int i=0;i<s.length();i++){
            if(f && s.charAt(i)=='a'){
                return false;
            }
            if(s.charAt(i)=='b') f=true;
        }
        return true;
    }
}