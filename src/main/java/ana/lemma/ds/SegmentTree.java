package ana.lemma.ds;


import java.util.Arrays;

public class SegmentTree {

    public SegmentTree(int sz) {
        this.n = sz;
        st = new int[4 * n];
        lazy = new int[4 * n];
        Arrays.fill(lazy, -1);
    }

    public SegmentTree(int[] a) {
        this(a.length);
        A = a;
        build(1, 0, n - 1);
    }

    private final int n;
    private final int[] st;
    private int[] A;
    private final int[] lazy;

    private int l(int p) {
        return p << 1;
    }

    private int r(int p) {
        return (p << 1) + 1;
    }

    private int cut(int a, int b) { // Resolve conflict if two branches are query return candidates.
        if (a == -1) return b;
        if (b == -1) return a;

        return Math.min(a, b);
    }

    private void build(int p, int L, int R) {
        if (L == R) st[p] = A[L]; // Base. If both ranges are same, tree has that val.
        else { // Split and repeat;
            int m = (L + R) / 2;
            build(l(p), L, m);
            build(r(p), m + 1, R);
            st[p] = cut(st[l(p)], st[r(p)]); // Resolve conflict if two min same;
        }
    }


    private void propagate(int p, int L, int R) { // How does this work?
        if (lazy[p] != -1) { // we have a flag
            st[p] = lazy[p];
            if (L != R) {
                lazy[l(p)] = lazy[r(p)] = lazy[p];
            } else {
                A[L] = lazy[p];
            }
            lazy[p] = -1; // erase lazy flag
        }
    }

    private int RMQ(int p, int L, int R, int i, int j) { // The soul of segment tree
        propagate(p, L, R);
        if (i > j) return -1; // Not possible
        if ((L >= i) && (R <= j)) return st[p]; // Found!

        int m = (L + R) / 2;
        return cut(RMQ(l(p), L, m, i, Math.min(m, j)), RMQ(r(p), m + 1, R, Math.max(i, m + 1), j)); // Two branch
        // fits the query so resolve conflict.
    }

    private boolean update(int p, int L, int R, int i, int j, int val) {
        propagate(p, L, R);
        if (i > j) return false;

        if ((L >= i) && (R <= j)) {
            lazy[p] = val;
            propagate(p, L, R);
        } else {
            int m = (L + R) / 2;
            update(l(p), L, m, i, Math.min(m, j), val);
            update(r(p), m + 1, R, Math.max(i, m + 1), j, val);
            int leftSubTree = (lazy[l(p)] != -1) ? lazy[l(p)] : st[l(p)];
            int rightSubTree = (lazy[l(p)] != -1) ? lazy[l(p)] : st[r(p)];
            st[p] = (leftSubTree <= rightSubTree) ? st[l(p)] :st[r(p)];
        }
        return true;
    }

    public boolean update(int i, int j, int val) {
        return update(1, 0, n - 1, i, j, val);
    }

    public int RMQ(int i, int j) {
        return RMQ(1, 0, n - 1, i, j);
    }

    public static void main(String[] args) {
        int[] A = {18, 17, 13, 19, 15, 11, 20, 99};
        SegmentTree st = new SegmentTree(A);

        System.out.print("     idx 0, 1, 2, 3, 4, 5, 6, 7\n");
        System.out.print("     A is {18,17,13,19,15,11,20,oo}\n");
        System.out.println("RMQ(3, 4) = " + st.RMQ(3, 4));
    }
}
