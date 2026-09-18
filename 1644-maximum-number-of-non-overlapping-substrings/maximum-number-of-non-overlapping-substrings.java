import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int[] l = new int[26];
        int[] r = new int[26];
        Arrays.fill(l, -1);
        int n = s.length();
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (l[c] == -1) l[c] = i;
            r[c] = i;
        }
        List<String> ans = new ArrayList<>();
        int last = -1;
        for (int i = 0; i < n; i++) {
            if (i != l[s.charAt(i) - 'a']) continue;
            int e = r[s.charAt(i) - 'a'];
            int k = i;
            while (k <= e && l[s.charAt(k) - 'a'] >= i) {
                e = Math.max(e, r[s.charAt(k) - 'a']);
                k++;
            }
            if (k > e) {
                if (i <= last) ans.set(ans.size() - 1, s.substring(i, e + 1));
                else ans.add(s.substring(i, e + 1));
                last = e;
            }
        }
        return ans;
    }
}
