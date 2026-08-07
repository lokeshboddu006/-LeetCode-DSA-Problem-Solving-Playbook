class Solution {
    public String smallestNumber(String n, long t) {
        long x = t;
        int a = 0, b = 0, c = 0, d = 0;
        while (x % 2 == 0) { a++; x /= 2; }
        while (x % 3 == 0) { b++; x /= 3; }
        while (x % 5 == 0) { c++; x /= 5; }
        while (x % 7 == 0) { d++; x /= 7; }
        if (x > 1) return "-1";

        int l = n.length();
        int[] v = new int[l];
        int z = -1;
        for (int i = 0; i < l; i++) {
            v[i] = n.charAt(i) - '0';
            if (v[i] == 0 && z == -1) z = i;
        }

        if (z != -1) {
            v[z] = 1;
            for (int i = z + 1; i < l; i++) v[i] = 1;
        }

        int[] p2 = new int[l + 1];
        int[] p3 = new int[l + 1];
        int[] p5 = new int[l + 1];
        int[] p7 = new int[l + 1];

        for (int i = 0; i < l; i++) {
            int k = v[i];
            p2[i + 1] = p2[i] + (k == 2 || k == 6 ? 1 : k == 4 ? 2 : k == 8 ? 3 : 0);
            p3[i + 1] = p3[i] + (k == 3 || k == 6 ? 1 : k == 9 ? 2 : 0);
            p5[i + 1] = p5[i] + (k == 5 ? 1 : 0);
            p7[i + 1] = p7[i] + (k == 7 ? 1 : 0);
        }

        if (z == -1 && p2[l] >= a && p3[l] >= b && p5[l] >= c && p7[l] >= d) {
            StringBuilder s = new StringBuilder();
            for (int k : v) s.append(k);
            return s.toString();
        }

        for (int i = l - 1; i >= 0; i--) {
            int start = (z != -1 && i >= z) ? 1 : (v[i] + (z != -1 && i == z ? 0 : (i == l - 1 && z == -1 ? 1 : 0)));
            if (z == -1 && i == l - 1) start = v[i] + 1;
            else if (z != -1 && i > z) start = 1;
            else if (z != -1 && i == z) start = 1;
            else start = v[i] + 1;

            for (int k = start; k <= 9; k++) {
                int r2 = Math.max(0, a - p2[i] - (k == 2 || k == 6 ? 1 : k == 4 ? 2 : k == 8 ? 3 : 0));
                int r3 = Math.max(0, b - p3[i] - (k == 3 || k == 6 ? 1 : k == 9 ? 2 : 0));
                int r5 = Math.max(0, c - p5[i] - (k == 5 ? 1 : 0));
                int r7 = Math.max(0, d - p7[i] - (k == 7 ? 1 : 0));

                int m = l - 1 - i;
                int req = r7 + r5 + (r3 + 1) / 2;
                int rem2 = Math.max(0, r2 - (r3 % 2 == 1 ? 1 : 0));
                req += (rem2 + 2) / 3;

                if (req <= m) {
                    v[i] = k;
                    String sxf = g(r2, r3, r5, r7, m);
                    StringBuilder s = new StringBuilder();
                    for (int j = 0; j <= i; j++) s.append(v[j]);
                    s.append(sxf);
                    return s.toString();
                }
            }
        }

        int m = l + 1;
        while (true) {
            int req = d + c + (b + 1) / 2;
            int rem2 = Math.max(0, a - (b % 2 == 1 ? 1 : 0));
            req += (rem2 + 2) / 3;
            if (req <= m) {
                return g(a, b, c, d, m);
            }
            m++;
        }
    }

    private String g(int a, int b, int c, int d, int m) {
        int e8 = a / 3;
        a %= 3;
        int e9 = b / 2;
        b %= 2;
        int e6 = 0;
        if (a > 0 && b > 0) {
            e6 = 1;
            a--;
            b--;
        }
        int e4 = a / 2;
        a %= 2;
        int e2 = a;
        int e3 = b;
        int e5 = c;
        int e7 = d;

        int tot = e2 + e3 + e4 + e5 + e6 + e7 + e8 + e9;
        int e1 = m - tot;

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < e1; i++) s.append('1');
        for (int i = 0; i < e2; i++) s.append('2');
        for (int i = 0; i < e3; i++) s.append('3');
        for (int i = 0; i < e4; i++) s.append('4');
        for (int i = 0; i < e5; i++) s.append('5');
        for (int i = 0; i < e6; i++) s.append('6');
        for (int i = 0; i < e7; i++) s.append('7');
        for (int i = 0; i < e8; i++) s.append('8');
        for (int i = 0; i < e9; i++) s.append('9');
        return s.toString();
    }
}