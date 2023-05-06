package key;

import utils.MessagePair;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RSAKey {

    private BigInteger p;
    private BigInteger q;
    protected BigInteger n;
    protected BigInteger e;
    protected BigInteger phi;
    protected BigInteger d;

    private final static int KEY_SIZE = 1024;
    private final static Random RNG = new SecureRandom();

    public RSAKey() {
        p = BigInteger.probablePrime(KEY_SIZE, RNG);
        q = BigInteger.probablePrime(KEY_SIZE, RNG);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537); // A common choice
        d = e.modInverse(phi);
    }

    public MessagePair<BigInteger> getPublicKeyPair() {
        return new MessagePair<>(n, e);
    }

    public MessagePair<BigInteger> getPrivateKeyPair() {
        return new MessagePair<>(n, d);
    }

    @Override
    public String toString() {
        return "(n, e): (" + n.toString() + ", " + e.toString() + ")\n" +
                "(n, d): (" + n.toString() + ", " + d.toString() +")";
    }
}
