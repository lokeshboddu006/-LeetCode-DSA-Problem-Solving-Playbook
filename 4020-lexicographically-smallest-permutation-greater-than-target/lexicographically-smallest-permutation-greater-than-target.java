class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] cnt = new int[26];
        for (char ch : s.toCharArray()) {
            cnt[ch - 'a']++;
        }
        for (char ch : target.toCharArray()) {
            cnt[ch - 'a']--;
        }
        for (int i = target.length() - 1; i >= 0; i--) {
            int cur = target.charAt(i) - 'a';
            cnt[cur]++;
            boolean ok = true;
            for (int x : cnt) {
                if (x < 0) {
                    ok = false;
                    break;
                }
            }
            if (!ok) continue;
            int next = -1;
            for (int c = cur + 1; c < 26; c++) {
                if (cnt[c] > 0) {
                    next = c;
                    break;
                }
            }
            if (next == -1) continue;
            cnt[next]--;
            StringBuilder ans = new StringBuilder(target.substring(0, i));
            ans.append((char) ('a' + next));
            for (int c = 0; c < 26; c++) {
                while (cnt[c]-- > 0) {
                    ans.append((char) ('a' + c));
                }
            }
            return ans.toString();
        }
        return "";
    }
}