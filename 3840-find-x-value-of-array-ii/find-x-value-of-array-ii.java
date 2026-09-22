class Data {

    int[] pre;
    int mul;

    static int k;

    public Data(int mul) {
        pre = new int[k];
        this.mul = mul % k;
    }

    static Data getMergedData(Data d1, Data d2) {

        if (d1 == null)
            return d2;

        if (d2 == null)
            return d1;

        Data d = new Data(d1.mul * d2.mul);
        for (int i = 0; i < k; i++)
            d.pre[i] = d1.pre[i];
        for (int i = 0; i < k; i++)
            d.pre[(d1.mul * i) % k] += d2.pre[i];

        return d;
    }
}


class SegmentTree {

    Data[] tree;
    int[] arr;
    int len;

    public SegmentTree(int[] arr, int k) {

        this.arr = arr;
        len = arr.length;

        int i = 1;

        while (i < len)
            i <<= 1;

        tree = new Data[i * 2 - 1];

        build(0, 0, arr.length - 1, arr);
    }

    public Data query(int l, int r) {

        if (l > r)
            throw new IllegalArgumentException(
                "l is bigger than r"
            );

        if (l < 0 || r >= len)
            throw new IllegalArgumentException(
                "Invalid index values"
            );

        return queryHelper(0, len - 1, 0, l, r);
    }

    public void setVal(int i, int newVal) {
        if (arr[i] % Data.k == newVal % Data.k)
            return;

        arr[i] = newVal;

        updateHelper(
            0,
            len - 1,
            0,
            newVal,
            i
        );
    }

    private void build(
        int i,
        int left,
        int right,
        int[] arr
    ) {

        if (left == right) {

            tree[i] = new Data(arr[left]);

            for (int j = 0; j < Data.k; j++) {

                tree[i].pre[j] =
                    arr[left] % Data.k == j
                    ? 1
                    : 0;
            }

            return;
        }

        int mid = (left + right) / 2;

        build(
            2 * i + 1,
            left,
            mid,
            arr
        );

        build(
            2 * i + 2,
            mid + 1,
            right,
            arr
        );

        tree[i] =
            Data.getMergedData(
                tree[2 * i + 1],
                tree[2 * i + 2]
            );
    }

    private Data queryHelper(
        int l,
        int r,
        int i,
        int lq,
        int rq
    ) {
        if (l >= lq && r <= rq)
            return tree[i];
        if (lq > r || rq < l)
            return null;

        int mid = (l + r) / 2;

        Data left =
            queryHelper(
                l,
                mid,
                2 * i + 1,
                lq,
                rq
            );

        Data right =
            queryHelper(
                mid + 1,
                r,
                2 * i + 2,
                lq,
                rq
            );

        return Data.getMergedData(left, right);
    }

    private void updateHelper(
        int l,
        int r,
        int i,
        int newVal,
        int indexToUpdate
    ) {
        if (l == r) {

            for (int j = 0; j < Data.k; j++)
                tree[i].pre[j] =
                    newVal % Data.k == j
                    ? 1
                    : 0;

            tree[i].mul = newVal % Data.k;

            return;
        }

        int mid = (l + r) / 2;

        if (indexToUpdate <= mid) {

            updateHelper(
                l,
                mid,
                2 * i + 1,
                newVal,
                indexToUpdate
            );

        } else {

            updateHelper(
                mid + 1,
                r,
                2 * i + 2,
                newVal,
                indexToUpdate
            );
        }
        tree[i] =
            Data.getMergedData(
                tree[2 * i + 1],
                tree[2 * i + 2]
            );
    }
}
class Solution {
    public int[] resultArray(
        int[] nums,
        int k,
        int[][] queries
    ) {
        Data.k = k;
        SegmentTree st =
            new SegmentTree(nums, k);
        int[] ans =
            new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            st.setVal(q[0], q[1]);
            ans[i] =
                st.query(
                    q[2],
                    nums.length - 1
                ).pre[q[3]];
        }

        return ans;
    }
}