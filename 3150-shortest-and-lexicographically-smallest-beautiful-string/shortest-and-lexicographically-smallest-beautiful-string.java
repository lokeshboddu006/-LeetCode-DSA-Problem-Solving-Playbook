class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        String r = "";
        int n = s.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + k; j <= n; j++) {
                String t = s.substring(i, j);
                if (t.replace("0", "").length() == k) {
                    if (r.isEmpty() || t.length() < r.length() || (t.length() == r.length() && t.compareTo(r) < 0)) {
                        r = t;
                    }
                }
            }
        }
        return r;
    }
}
