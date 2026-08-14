class Solution {
    public int maximumLengthSubstring(String s) {
        int[] arr = new int[26];
        int m = 0;
        for (int r = 0, l = 0; r < s.length(); r++) {
            arr[s.charAt(r) - 'a']++;
            while (arr[s.charAt(r) - 'a'] > 2) {
                arr[s.charAt(l) - 'a']--;
                l++;
            }
            m = Math.max(m, r - l + 1);
        }
        return m;
    }
}
