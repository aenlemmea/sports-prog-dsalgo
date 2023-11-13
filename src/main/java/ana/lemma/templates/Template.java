package ana.lemma.templates;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

public class Template {

    // Character Array is involved? Try using a frequency table!

    void solve() {

    }

    void run() {
        is = System.in;
        out = new PrintWriter(System.out);

        long s = System.currentTimeMillis();
        solve();
        out.flush();
        //System.err.println(System.currentTimeMillis() - s + "ms");
    }

    public static void main(String[] args) throws IOException {
        new Template().run();
    }

    InputStream is;
    PrintWriter out;

    // Output

    private void dbg(Object... o) {
        System.err.println("[DEBUG]:: " + Arrays.deepToString(o));
    }

    // Input
    private final byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;

    private int readByte() {
        if (lenbuf == -1) throw new InputMismatchException();
        if (ptrbuf >= lenbuf) {
            ptrbuf = 0;
            try {
                lenbuf = is.read(inbuf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }

    private boolean isSpaceChar(int c) {
        return !(c >= 33 && c <= 126);
    }

    private int skip() {
        int b;
        while ((b = readByte()) != -1 && isSpaceChar(b)) ;
        return b;
    }

    private int ni() {
        int num = 0, b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }

        while (true) {
            if (b >= '0' && b <= '9') {
                num = num * 10 + (b - '0');
            } else {
                return minus ? -num : num;
            }
            b = readByte();
        }
    }

    private long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
        if (b == '-') {
            minus = true;
            b = readByte();
        }

        while (true) {
            if (b >= '0' && b <= '9') {
                num = num * 10 + (b - '0');
            } else {
                return minus ? -num : num;
            }
            b = readByte();
        }
    }

    private int[] na(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ni();
        return a;
    }

    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private char nc() {
        return (char) skip();
    }

    private char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while (p < n && !(isSpaceChar(b))) {
            buf[p++] = (char) b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private double nd() {
        return Double.parseDouble(ns());
    }

    // Utils

    /* To swap, use one:
        0. y = (x ^= (y ^= x)) ^ y
        1. b = (a + b) – (a = b);
        2. a += b – (b = a);
        3. a = a * b / (b = a)
        4. a = a ^ b ^ (b = a)
     */

    int gcd(int n1, int n2) {
        if (n1 == 0) {
            return n2;
        }

        if (n2 == 0) {
            return n1;
        }

        int n;
        for (n = 0; ((n1 | n2) & 1) == 0; n++) {
            n1 >>= 1;
            n2 >>= 1;
        }

        while ((n1 & 1) == 0) {
            n1 >>= 1;
        }

        do {
            while ((n2 & 1) == 0) {
                n2 >>= 1;
            }

            if (n1 > n2) {
                n1 = (n2 ^= (n1 ^= n2)) ^ n1;
            }
            n2 = (n2 - n1);
        } while (n2 != 0);
        return n1 << n;
    }

    static boolean fitsInInt(long x) {
        return (int) x == x;
    }

    static void chkUnnsryRnd(boolean condition) {
        if (!condition) {
            throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
        }
    }

    private static int sqrtFloor(int x) {
        return (int) Math.sqrt(x);
    }

    static int ltBrFr(int x, int y) {
        return ~~(x - y) >>> (Integer.SIZE - 1);
    }

    static int ltBrFr(long x, long y) {
        return (int) (~~(x - y) >>> (Long.SIZE - 1));
    }

    // sqrt and related methods from the guava library, name changes by me. Apache License 2.0
    public static int sqrt(int x, RoundingMode mode) {
        int sqrtFloor = sqrtFloor(x);
        switch (mode) {
            case UNNECESSARY:
                chkUnnsryRnd(sqrtFloor * sqrtFloor == x); // fall through
            case FLOOR:
            case DOWN:
                return sqrtFloor;
            case CEILING:
            case UP:
                return sqrtFloor + ltBrFr(sqrtFloor * sqrtFloor, x);
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN:
                int halfSquare = sqrtFloor * sqrtFloor + sqrtFloor;
                return sqrtFloor + ltBrFr(halfSquare, x);
            default:
                throw new AssertionError();
        }
    }

    public static long sqrt(long x, RoundingMode mode) {
        if (fitsInInt(x)) {
            return sqrt((int) x, mode);
        }
        long g = (long) Math.sqrt((double) x);
        long gSquared = g * g;
        switch (mode) {
            case UNNECESSARY:
                chkUnnsryRnd(gSquared == x);
                return g;
            case FLOOR:
            case DOWN:
                if (x < gSquared) {
                    return g - 1;
                }
                return g;
            case CEILING:
            case UP:
                if (x > gSquared) {
                    return g + 1;
                }
                return g;
            case HALF_DOWN:
            case HALF_UP:
            case HALF_EVEN:
                long sqrtFloor = g - ((x < gSquared) ? 1 : 0);
                long halfSquare = sqrtFloor * sqrtFloor + sqrtFloor;
                return sqrtFloor + ltBrFr(halfSquare, x);
        }
        throw new AssertionError();
    }

    public long iPower(int base, int k) {
        for (long accum = 1, b = base; ; k >>>= 1)
            switch (k) {
                case 0:
                    return accum;
                case 1:
                    return accum * b;
                default:
                    if ((k & 1) != 0)
                        accum *= b;
                    b *= b;
            }
    }

    public static long lPower(long x, int y) {
        long result = 1;
        while (y > 0) {
            if ((y & 1) == 0) {
                x *= x;
                y >>>= 1;
            } else {
                result *= x;
                y--;
            }
        }
        return result;
    }

    public static double pow(final double a, final double b) {
        final int x = (int) (Double.doubleToLongBits(a) >> 32);
        final int y = (int) (b * (x - 1072632447) + 1072632447);
        return Double.longBitsToDouble(((long) y) << 32);
    }

    long mpow(long a, long b) {
        final long mod = (long) 1e9 + 7;
        long result = 1;
        while (b > 0) {
            if (b % 2 == 1) result = (result * a) % mod;
            b /= 2;
            a = (a * a) % mod;
        }
        return result;
    }

    <T> String aToStr(T[] a) {
        return Arrays.toString(a).replace("[", "").replace("]", "").replace(", ", " ");
    }

    public static boolean aIntIsSorted(int[] array) {
        return IntStream.range(0, array.length - 1).noneMatch(i -> array[i] > array[i + 1]);
    }

    public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(array.length, endIndexExclusive) - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(final int[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }

    public static void reverse(final long[] array) {
        if (array != null) {
            reverse(array, 0, array.length);
        }
    }
}
