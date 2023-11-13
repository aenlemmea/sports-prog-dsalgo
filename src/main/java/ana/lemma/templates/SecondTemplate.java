package ana.lemma.templates;

public class SecondTemplate {

    void solve() {

    }

    public static void main(String[] args) {
        new SecondTemplate().run();
    }

    void run() {
        hP = new HyperPrinter(System.out);
        hS = new HyperScanner(System.in);

        long s = System.currentTimeMillis();
        solve();
        hP.flush();
        //System.err.println(System.currentTimeMillis() - s + "ms");
    }

    HyperPrinter hP;
    HyperScanner hS;

    private void dbg(Object... o) {
        System.err.println("[DEBUG]:: " + java.util.Arrays.deepToString(o));
    }

    private int ni() {
        return hS.nextInt();
    }

    private long nl() {
        return hS.nextLong();
    }

    private double nd() {
        return hS.nextDouble();
    }

    private String ns() {
        return hS.next();
    }

    private char nc() {
        // Ugly. Will fix later!
        return hS.nextChar();
    }

    private int[] na(int n) {
        return hS.nextIntArray(n);
    }
}

class HyperScanner {
    private final java.io.InputStream in;
    private final byte[] buffer = new byte[1024];
    private int ptr = 0;
    private int buflen = 0;

    private static final long LONG_MAX_TENTHS = 922337203685477580L;
    private static final int LONG_MAX_LAST_DIGIT = 7;
    private static final int LONG_MIN_LAST_DIGIT = 8;

    public HyperScanner(java.io.InputStream in) {
        this.in = in;
    }

    public HyperScanner(java.io.File file) throws java.io.IOException {
        this(new java.io.BufferedInputStream(java.nio.file.Files.newInputStream(file.toPath())));
    }

    public HyperScanner() {
        this(System.in);
    }

    private boolean hasNextByte() {
        if (ptr < buflen) {
            return true;
        } else {
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return buflen > 0;
        }
    }

    private int readByte() {
        if (hasNextByte()) return buffer[ptr++];
        else return -1;
    }

    private static boolean isPrintableChar(int c) {
        return 33 <= c && c <= 126;
    }

    public boolean hasNextInverted() {
        while (hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;
        return !hasNextByte();
    }

    public String next() {
        if (hasNextInverted()) throw new java.util.NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        int b = readByte();
        while (isPrintableChar(b)) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    public char nextChar() {
        int b = readByte();
        return isPrintableChar(readByte()) ? new StringBuilder().appendCodePoint(b).charAt(0) : (char) b;
    }

    public long nextLong() {
        if (hasNextInverted()) throw new java.util.NoSuchElementException();
        long n = 0;
        boolean minus = false;
        int b = readByte();
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        if (b < '0' || '9' < b) {
            throw new NumberFormatException();
        }
        while (true) {
            if ('0' <= b && b <= '9') {
                int digit = b - '0';
                if (n >= LONG_MAX_TENTHS) {
                    if (n == LONG_MAX_TENTHS) {
                        if (minus) {
                            if (digit <= LONG_MIN_LAST_DIGIT) {
                                n = -n * 10 - digit;
                                b = readByte();
                                if (!isPrintableChar(b)) {
                                    return n;
                                } else if (b < '0' || '9' < b) {
                                    throw new NumberFormatException(
                                            String.format("%d%s... is not number", n, (char) b)
                                    );
                                }
                            }
                        } else {
                            if (digit <= LONG_MAX_LAST_DIGIT) {
                                n = n * 10 + digit;
                                b = readByte();
                                if (!isPrintableChar(b)) {
                                    return n;
                                } else if (b < '0' || '9' < b) {
                                    throw new NumberFormatException(
                                            String.format("%d%s... is not number", n, (char) b)
                                    );
                                }
                            }
                        }
                    }
                    throw new ArithmeticException(
                            String.format("%s%d%d... overflows long.", minus ? "-" : "", n, digit)
                    );
                }
                n = n * 10 + digit;
            } else if (b == -1 || !isPrintableChar(b)) {
                return minus ? -n : n;
            } else {
                throw new NumberFormatException();
            }
            b = readByte();
        }
    }

    public int nextInt() {
        long nl = nextLong();
        if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
        return (int) nl;
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public long[] nextLongArray(int length) {
        long[] array = new long[length];
        for (int i = 0; i < length; i++) array[i] = this.nextLong();
        return array;
    }

    public long[] nextLongArray(int length, java.util.function.LongUnaryOperator map) {
        long[] array = new long[length];
        for (int i = 0; i < length; i++) array[i] = map.applyAsLong(this.nextLong());
        return array;
    }

    public int[] nextIntArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) array[i] = this.nextInt();
        return array;
    }

    public int[] nextIntArray(int length, java.util.function.IntUnaryOperator map) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) array[i] = map.applyAsInt(this.nextInt());
        return array;
    }

    public double[] nextDoubleArray(int length) {
        double[] array = new double[length];
        for (int i = 0; i < length; i++) array[i] = this.nextDouble();
        return array;
    }

    public double[] nextDoubleArray(int length, java.util.function.DoubleUnaryOperator map) {
        double[] array = new double[length];
        for (int i = 0; i < length; i++) array[i] = map.applyAsDouble(this.nextDouble());
        return array;
    }

    public long[][] nextLongMatrix(int height, int width) {
        long[][] mat = new long[height][width];
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++) {
                mat[h][w] = this.nextLong();
            }
        return mat;
    }

    public int[][] nextIntMatrix(int height, int width) {
        int[][] mat = new int[height][width];
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++) {
                mat[h][w] = this.nextInt();
            }
        return mat;
    }

    public double[][] nextDoubleMatrix(int height, int width) {
        double[][] mat = new double[height][width];
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++) {
                mat[h][w] = this.nextDouble();
            }
        return mat;
    }

    public char[][] nextCharMatrix(int height, int width) {
        char[][] mat = new char[height][width];
        for (int h = 0; h < height; h++) {
            String s = this.next();
            for (int w = 0; w < width; w++) {
                mat[h][w] = s.charAt(w);
            }
        }
        return mat;
    }
}

class HyperPrinter extends java.io.PrintWriter {
    public HyperPrinter(java.io.PrintStream stream) {
        super(stream);
    }

    public HyperPrinter(java.io.File file) throws java.io.FileNotFoundException {
        super(new java.io.PrintStream(file));
    }

    public HyperPrinter() {
        super(System.out);
    }

    private static String dtos(double x) {
        StringBuilder sb = new StringBuilder();
        if (x < 0) {
            sb.append('-');
            x = -x;
        }
        x += Math.pow(10, -20) / 2;
        sb.append((long) x);
        sb.append(".");
        x -= (long) x;
        for (int i = 0; i < 20; i++) {
            x *= 10;
            sb.append((int) x);
            x -= (int) x;
        }
        return sb.toString();
    }

    @Override
    public void print(float f) {
        super.print(dtos(f));
    }

    @Override
    public void println(float f) {
        super.println(dtos(f));
    }

    @Override
    public void print(double d) {
        super.print(dtos(d));
    }

    @Override
    public void println(double d) {
        super.println(dtos(d));
    }

    public void printArray(int[] array) {
        this.printArray(array, " ");
    }

    public void printArray(int[] array, String separator, java.util.function.IntUnaryOperator map) {
        int n = array.length;
        if (n == 0) {
            super.println();
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            super.print(map.applyAsInt(array[i]));
            super.print(separator);
        }
        super.println(map.applyAsInt(array[n - 1]));
    }

    public void printArray(int[] array, java.util.function.IntUnaryOperator map) {
        this.printArray(array, " ", map);
    }

    public void printArray(int[] array, String separator) {
        int n = array.length;
        if (n == 0) {
            super.println();
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            super.print(array[i]);
            super.print(separator);
        }
        super.println(array[n - 1]);
    }

    public void printArray(long[] array, String separator) {
        this.printArray(java.util.Arrays.stream(array).mapToInt(Math::toIntExact).toArray(), separator);
    }

    public void printArray(long[] array) {
        this.printArray(array, " ");
    }

    public void printArray(long[] array, String separator, java.util.function.LongUnaryOperator map) {
        int n = array.length;
        if (n == 0) {
            super.println();
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            super.print(map.applyAsLong(array[i]));
            super.print(separator);
        }
        super.println(map.applyAsLong(array[n - 1]));
    }

    public void printArray(long[] array, java.util.function.LongUnaryOperator map) {
        this.printArray(array, " ", map);
    }
}

