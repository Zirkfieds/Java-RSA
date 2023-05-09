package key;

import utils.RSAKeyPair;

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

    private final static int KEY_SIZE = 2048;
    private final static Random RNG = new SecureRandom();

    public RSAKey() {
        p = BigInteger.probablePrime(KEY_SIZE, RNG);
        q = BigInteger.probablePrime(KEY_SIZE, RNG);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537); // A common choice
        d = e.modInverse(phi);
    }

    public RSAKeyPair<BigInteger> getPublicKeyPair() {
        return new RSAKeyPair<>(n, e);
    }

    public RSAKeyPair<BigInteger> getPrivateKeyPair() {
        return new RSAKeyPair<>(n, d);
    }

    @Override
    public String toString() {
        return "(n, e): (" + n.toString() + ", " + e.toString() + ")\n" +
                "(n, d): (" + n.toString() + ", " + d.toString() +")";
    }
}
