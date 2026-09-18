class Solution {
    private int getRight(
        String s,
        int L,
        int[] first,
        int[] last
    ) {

        int R =
            last[s.charAt(L) - 'a'];

        for (int i = L; i <= R; i++) {

            int c =
                s.charAt(i) - 'a';

            if (first[c] < L) {
                return -1;
            }

            R = Math.max(
                R,
                last[c]
            );
        }

        return R;
    }

    public List<String> maxNumOfSubstrings(
        String s
    ) {

        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

   
        for (int i = 0; i < n; i++) {

            int c =
                s.charAt(i) - 'a';

            first[c] =
                Math.min(first[c], i);

            last[c] = i;
        }

        List<int[]> intervals =
            new ArrayList<>();

        for (int c = 0; c < 26; c++) {

            if (first[c] == n) {
                continue;
            }

            int L = first[c];

            int R =
                getRight(
                    s,
                    L,
                    first,
                    last
                );

            if (R != -1) {
                intervals.add(
                    new int[]{L, R}
                );
            }
        }

        intervals.sort((a, b) -> {

            if (a[1] != b[1]) {
                return Integer.compare(
                    a[1],
                    b[1]
                );
            }

            return Integer.compare(
                b[0],
                a[0]
            );
        });

        List<String> ans =
            new ArrayList<>();

        int lastEnd = -1;

        for (int[] interval : intervals) {

            int L = interval[0];
            int R = interval[1];

            if (L > lastEnd) {

                ans.add(
                    s.substring(
                        L,
                        R + 1
                    )
                );

                lastEnd = R;
            }
        }

        return ans;
    }
}