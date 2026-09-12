class Solution {

    static class Pair {
        long sum;
        List<Integer> ids;

        Pair(long sum, List<Integer> ids) {
            this.sum = sum;
            this.ids = ids;
        }

        Pair copy() {
            return new Pair(sum, new ArrayList<>(ids));
        }
    }

    Pair[][] dp;
    int[] next;

    Pair better(Pair a, Pair b) {
        if (a.sum != b.sum)
            return a.sum > b.sum ? a : b;

        Collections.sort(a.ids);
        Collections.sort(b.ids);

        for (int i = 0; i < Math.min(a.ids.size(), b.ids.size()); i++) {
            if (!a.ids.get(i).equals(b.ids.get(i)))
                return a.ids.get(i) < b.ids.get(i) ? a : b;
        }

        return a.ids.size() <= b.ids.size() ? a : b;
    }

    int lowerBound(List<List<Integer>> in, List<Integer> order, int target) {
        int l = 0, r = order.size();

        while (l < r) {
            int m = l + (r - l) / 2;

            if (in.get(order.get(m)).get(0) >= target)
                r = m;
            else
                l = m + 1;
        }

        return l;
    }

    Pair solve(List<List<Integer>> in, List<Integer> order,
               int pos, int count) {

        if (pos == order.size() || count == 4)
            return new Pair(0, new ArrayList<>());

        if (dp[pos][count] != null)
            return dp[pos][count].copy();

        Pair skip = solve(in, order, pos + 1, count);

        int id = order.get(pos);

        Pair take = solve(
            in,
            order,
            next[pos],
            count + 1
        );

        take.sum += in.get(id).get(2);
        take.ids.add(id);

        dp[pos][count] = better(skip, take);

        return dp[pos][count].copy();
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        List<Integer> order = new ArrayList<>();

        for (int i = 0; i < n; i++)
            order.add(i);

        order.sort((a, b) ->
            Integer.compare(
                intervals.get(a).get(0),
                intervals.get(b).get(0)
            )
        );

        next = new int[n];

        for (int i = 0; i < n; i++) {
            int id = order.get(i);
            next[i] = lowerBound(
                intervals,
                order,
                intervals.get(id).get(1) + 1
            );
        }

        dp = new Pair[n][4];

        List<Integer> ans = solve(intervals, order, 0, 0).ids;
        Collections.sort(ans);

        int[] res = new int[ans.size()];

        for (int i = 0; i < ans.size(); i++)
            res[i] = ans.get(i);

        return res;
    }
}