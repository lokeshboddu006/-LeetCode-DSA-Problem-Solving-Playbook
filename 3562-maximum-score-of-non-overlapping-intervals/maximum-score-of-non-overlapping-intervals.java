class Solution {
    static class Node {
        long score;
        int[] ids;

        Node(long score, int[] ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    private boolean better(Node a, Node b) {
        if (a == null) {
            return false;
        }

        if (b == null) {
            return true;
        }

        if (a.score != b.score) {
            return a.score > b.score;
        }

        int len = Math.min(a.ids.length, b.ids.length);

        for (int i = 0; i < len; i++) {
            if (a.ids[i] != b.ids[i]) {
                return a.ids[i] < b.ids[i];
            }
        }

        return a.ids.length < b.ids.length;
    }

    private int[] addSorted(int[] ids, int value) {
        int[] result = Arrays.copyOf(ids, ids.length + 1);

        result[ids.length] = value;

        Arrays.sort(result);

        return result;
    }

    private int lowerBound(long[] ends, int length, long target) {
        int left = 0;
        int right = length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (ends[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        final int K = 4;

        long[][] arr = new long[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        Arrays.sort(arr, (a, b) -> Long.compare(a[1], b[1]));

        long[] ends = new long[n];

        for (int i = 0; i < n; i++) {
            ends[i] = arr[i][1];
        }

        Node[][] dp = new Node[K + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            dp[0][i] = new Node(0, new int[0]);
        }

        for (int i = 1; i <= n; i++) {
            long left = arr[i - 1][0];
            long weight = arr[i - 1][2];
            int originalIndex = (int) arr[i - 1][3];

            int p = lowerBound(ends, i - 1, left);

            for (int k = 1; k <= K; k++) {

                dp[k][i] = dp[k][i - 1];

                if (dp[k - 1][p] != null) {

                    int[] ids = addSorted(
                        dp[k - 1][p].ids,
                        originalIndex
                    );

                    Node take = new Node(
                        dp[k - 1][p].score + weight,
                        ids
                    );

                    if (better(take, dp[k][i])) {
                        dp[k][i] = take;
                    }
                }
            }
        }

        Node answer = null;

        for (int k = 1; k <= K; k++) {
            if (better(dp[k][n], answer)) {
                answer = dp[k][n];
            }
        }

        return answer.ids;
    }
}