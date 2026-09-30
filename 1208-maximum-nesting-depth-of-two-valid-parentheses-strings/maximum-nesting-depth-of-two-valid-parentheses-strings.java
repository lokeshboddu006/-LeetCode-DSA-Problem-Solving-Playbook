class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int depth = 0;
        int[] res = new int[seq.length()];
        for (int i = 0; i < seq.length(); i++) {
            char ch = seq.charAt(i);
            if (ch == '(') {
                depth++;
                res[i] = depth % 2;
            } else {
                res[i] = depth % 2;
                depth--;
            }
        }
        return res;
    }
}