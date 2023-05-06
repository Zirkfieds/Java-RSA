package key;

import utils.RSAKeyPair;

import java.math.BigInteger;

public class Authentication {

    private BigInteger n, e, d;
    private BigInteger cipherSignature;
    private BigInteger cipherText;

    public Authentication(
            BigInteger cipherText,
            BigInteger cipherSignature,
            RSAKeyPair<BigInteger> keyA) {
        this.cipherText = cipherText;
        this.cipherSignature = cipherSignature;
        this.n = keyA.getMsgl();
        this.e = keyA.getMsgr();
    }

    public boolean authenticate() {

        // B hashes the decrypted plain text
        BigInteger cipherHashed = new BigInteger(1, SHA256.hash(cipherText.toByteArray()));
        // B decrypts the signature with A's public key
        BigInteger decryptedSignature = cipherSignature.modPow(e, n);
        // B compares bewtween the hashed plain text and the A's signature
        return decryptedSignature.equals(cipherHashed);
    }


}
