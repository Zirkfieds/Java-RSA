package key;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class SHA256 {
    private static final int[] kMap = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    private static final int[] h0 = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
    };

    private static final int blockBits = 512;
    private static final int blockBytes = blockBits >> 3;

    private static final int[] a = new int[64];
    private static final int[] h = new int[8];
    private static final int[] temp = new int[8];

    public static byte[] hash(byte[] message) {

        System.arraycopy(h0, 0, h, 0, h0.length);
        int[] words = padding(message);

        for (int i = 0, n = words.length >> 4; i < n; ++i) {

            System.arraycopy(words, i * 16, a, 0, 16);
            for (int t = 16; t < a.length; ++t) {
                a[t] = smallSig1(a[t - 2]) + a[t - 7] + smallSig0(a[t - 15]) + a[t - 16];
            }

            System.arraycopy(h, 0, temp, 0, h.length);

            for (int t = 0; t < a.length; ++t) {
                int t1 = temp[7] + bigSig1(temp[4]) + ch(temp[4], temp[5], temp[6]) + kMap[t] + a[t];
                int t2 = bigSig0(temp[0]) + maj(temp[0], temp[1], temp[2]);
                System.arraycopy(temp, 0, temp, 1, temp.length - 1);
                temp[4] += t1;
                temp[0] = t1 + t2;
            }

            for (int t = 0; t < h.length; ++t) {
                h[t] += temp[t];
            }
        }
        return toByteArray();
    }

    public static int[] padding(byte[] message) {
        // new message length: original + 1-bit and padding + 8-byte length
        int finalBlockLength = message.length % blockBytes;
        int blockCount = message.length / blockBytes + (finalBlockLength + 1 + 8 > blockBytes ? 2 : 1);

        final IntBuffer result = IntBuffer.allocate(blockCount * (blockBytes / Integer.BYTES));

        ByteBuffer buf = ByteBuffer.wrap(message);
        for (int i = 0, n = message.length / Integer.BYTES; i < n; ++i) {
            result.put(buf.getInt());
        }
        // copy the remaining bytes and append 1 bit followed by zeroes
        ByteBuffer remainder = ByteBuffer.allocate(4);
        remainder.put(buf).put((byte) 0b10000000).rewind();
        result.put(remainder.getInt());

        result.position(result.capacity() - 2);

        // place original message length as 64-bit integer at the end
        long msgLength = message.length * 8L;
        result.put((int) (msgLength >>> 32));
        result.put((int) msgLength);

        return result.array();
    }

    private static byte[] toByteArray() {
        ByteBuffer buf = ByteBuffer.allocate(SHA256.h.length * Integer.BYTES);
        for (int i : SHA256.h) {
            buf.putInt(i);
        }
        return buf.array();
    }

    private static int ch(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    private static int maj(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private static int bigSig0(int x) {
        return Integer.rotateRight(x, 2)
                ^ Integer.rotateRight(x, 13)
                ^ Integer.rotateRight(x, 22);
    }

    private static int bigSig1(int x) {
        return Integer.rotateRight(x, 6)
                ^ Integer.rotateRight(x, 11)
                ^ Integer.rotateRight(x, 25);
    }

    private static int smallSig0(int x) {
        return Integer.rotateRight(x, 7)
                ^ Integer.rotateRight(x, 18)
                ^ (x >>> 3);
    }

    private static int smallSig1(int x) {
        return Integer.rotateRight(x, 17)
                ^ Integer.rotateRight(x, 19)
                ^ (x >>> 10);
    }
}
