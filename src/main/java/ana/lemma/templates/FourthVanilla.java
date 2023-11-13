package ana.lemma.templates;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FourthVanilla {
    void solve() throws IOException {
        int m = 7 - Math.max(f.nextInt(), f.nextInt());
        int igcd = gcd(m, 6);
        f.write((m / igcd) + "/" + (6 / igcd));
    }

    FastIO f;

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

    void run() throws IOException {
        f = new FastIO();
        solve();
        f.close();
    }

    public static void main(String[] args) throws IOException {
        new FourthVanilla().run();
    }

}
class FastIO {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private DataOutputStream dout;
    private byte[] readBuffer;
    private byte[] writeBuffer;
    private int bufferPointer;
    private int bytesRead;
    private int pointer;
    public FastIO() {
        din = new DataInputStream(System.in);
        dout = new DataOutputStream(System.out);
        readBuffer = new byte[BUFFER_SIZE];
        writeBuffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
        pointer = 0;

    }
    public FastIO(String file_name) throws IOException {
        din = new DataInputStream(new FileInputStream(file_name));
        dout = new DataOutputStream(Files.newOutputStream(Paths.get(file_name)));
        writeBuffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
        pointer = 0;
    }
    public String readLine() throws IOException {
        byte[] buf = new byte[64];
        int cnt = 0, c;
        while ((c = read()) != -1) {
            if (c == '\n')
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }
    public String nextString() throws IOException{

        // Skip all whitespace characters from the stream
        byte c = read();
        while(Character.isWhitespace(c)){
            c = read();
        }

        StringBuilder builder = new StringBuilder();
        builder.append((char)c);
        c = read();
        while(!Character.isWhitespace(c)){
            builder.append((char)c);
            c = read();
        }

        return builder.toString();
    }
    public int nextInt() throws IOException {
        int ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }
    public int[] nextIntArray(int n) throws IOException {
        int arr[] = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = nextInt();
        }
        return arr;
    }
    public long nextLong() throws IOException {
        long ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (neg)
            return -ret;
        return ret;
    }
    public long[] nextLongArray(int n) throws IOException {
        long arr[] = new long[n];
        for(int i = 0; i < n; i++){
            arr[i] = nextLong();
        }
        return arr;
    }
    public char nextChar() throws IOException{
        byte c = read();
        while(Character.isWhitespace(c)){
            c = read();
        }
        return (char) c;
    }
    public double nextDouble() throws IOException {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();

        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (c == '.') {
            while ((c = read()) >= '0' && c <= '9') {
                ret += (c - '0') / (div *= 10);
            }
        }

        if (neg)
            return -ret;
        return ret;
    }
    public double[] nextDoubleArray(int n) throws IOException {
        double arr[] = new double[n];
        for(int i = 0; i < n; i++){
            arr[i] = nextDouble();
        }
        return arr;
    }
    private void fillBuffer() throws IOException {
        bytesRead = din.read(readBuffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1)
            writeBuffer[0] = -1;
    }
    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return readBuffer[bufferPointer++];
    }

    public void close() throws IOException {
        if (din != null)
            din.close();
        if(dout != null)
            dout.close();
    }
    public void writeBytes(byte arr[]) throws IOException {

        int bytesToWrite = arr.length;

        if (pointer + bytesToWrite >= BUFFER_SIZE) {
            flush();
        }

        for (int i = 0; i < bytesToWrite; i++) {
            writeBuffer[pointer++] = arr[i];
        }
    }

    public void fastWrite(String str) throws IOException {
        writeBytes(str.getBytes());
    }

    public void fastWriteLine(String str) throws IOException {
        str+="\n";
        writeBytes(str.getBytes());
    }
    public void write(String str) throws IOException {
        writeBytes(str.getBytes());
        flush();
    }
    public void writeLine(String str) throws IOException {
        str+="\n";
        writeBytes(str.getBytes());
        flush();
    }
    public void flush() throws IOException {
        dout.write(writeBuffer, 0, pointer);
        dout.flush();
        pointer = 0;
    }
}
