package encryption;

import utils.MessagePair;

import java.math.BigInteger;

public class RSADecryption {

    private BigInteger cipher;
    private BigInteger n, d;
    private BigInteger plain;
    
    public RSADecryption(BigInteger message, MessagePair<BigInteger> privateKey) {
        this.cipher = message;

        this.n = privateKey.getMsgl();
        this.d = privateKey.getMsgr();
        decrypt();
    }

    public void decrypt() {
        this.plain = cipher.modPow(d, n);
    }

    public String getPlainStr() {
        return new String(this.plain.toByteArray());
    }

    public BigInteger getPlain() {
        return this.plain;
    }
}
