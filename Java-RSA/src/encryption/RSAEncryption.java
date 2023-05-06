package encryption;

import utils.MessagePair;

import java.math.BigInteger;

public class RSAEncryption {

    private BigInteger msg;
    private BigInteger n, e;
    private BigInteger cipher;

    public RSAEncryption(BigInteger message, MessagePair<BigInteger> publicKey) {
        msg = message;
        this.n = publicKey.getMsgl();
        this.e = publicKey.getMsgr();
        encrypt();
    }

    public void encrypt() {
        this.cipher = msg.modPow(e, n);
    }

    public String getCipherStr() {
        return new String(this.cipher.toByteArray());
    }

    public BigInteger getCipher() {
        return this.cipher;
    }
}
