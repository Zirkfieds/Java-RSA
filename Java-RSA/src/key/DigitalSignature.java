package key;

import utils.MessagePair;

import java.math.BigInteger;

public class DigitalSignature {

    private BigInteger cipherText;
    private BigInteger n, d;

    public DigitalSignature(BigInteger cipherText, MessagePair<BigInteger> key) {
        this.cipherText = cipherText;
        this.n = key.getMsgl();
        this.d = key.getMsgr();
    }

    public BigInteger generateSignature() {
        // A hashes the plain text as the signature
        BigInteger md = new BigInteger(1, SHA256.hash(cipherText.toByteArray()));
        // A encrypts the signature's hash value
        return md.modPow(d, n);
    }


}
