class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        String ans = "";
        int n = s.length();

        for (int i = 0; i < n; i++) {

            int oneCnt = 0;
            StringBuilder cur = new StringBuilder();

            for (int j = i; j < n; j++) {

                cur.append(s.charAt(j));

                if (s.charAt(j) == '1')
                    oneCnt++;

                // More than k ones can never become valid again
                if (oneCnt > k)
                    break;

                if (oneCnt == k) {
                    String curStr = cur.toString();

                    if (ans.isEmpty() ||
                        curStr.length() < ans.length() ||
                        (curStr.length() == ans.length() && curStr.compareTo(ans) < 0)) {

                        ans = curStr;
                    }
                }
            }
        }
        return ans;
    }
}