class Solution {
    public int countCommas(int n) {
        int commas = 0;
        if (n >= 1000) {
            commas += n - 999;
        }
        if (n >= 1000000) {
            commas += n - 999999;
        }
        if (n >= 1000000000) {
            commas += n - 999999999;
        }
        return commas; 
    }
}