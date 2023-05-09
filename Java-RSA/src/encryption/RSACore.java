package encryption;

import key.Authentication;
import key.DigitalSignature;
import utils.KeyManager;
import utils.RSAKeyPair;

import java.math.BigInteger;
import java.util.Base64;

public class RSACore {

    private final KeyManager selfKeyManager; // Key Triplet
    private BigInteger message;

    public RSACore() {
        selfKeyManager = new KeyManager();
        message = null;
    }

    public void setMessage(String message) {
        this.message = new BigInteger(message.getBytes());
    }
    public void setMessage(BigInteger message) {
        this.message = message;
    }

    public void setReceiverPublicKey(RSAKeyPair<BigInteger> thirdPartyKey) {
        selfKeyManager.setThirdPartyKey(thirdPartyKey);
    }

    public void setSelfKeys(RSAKeyPair<BigInteger> privateKey, RSAKeyPair<BigInteger> publicKey) {
        selfKeyManager.setPrivateKey(privateKey);
        selfKeyManager.setPublicKey(publicKey);
    }

    public BigInteger encryptMessage() {
        RSAKeyPair<BigInteger> receiverPubKey = selfKeyManager.getThirdPartyKey();
//        System.out.println(message + " | " + receiverPubKey + " | Task: Encrypt");
        if (receiverPubKey != null) {
            RSAEncryption enc = new RSAEncryption(message, receiverPubKey);
            return enc.getCipher();
        }
        return null;
    }

    public BigInteger generateSignature(BigInteger cipher) {
        RSAKeyPair<BigInteger> selfPrivateKey = selfKeyManager.getPrivateKey();
        if (selfPrivateKey != null) {
            DigitalSignature ds = new DigitalSignature(cipher, selfPrivateKey);
            return ds.generateSignature();
        }
        return null;
    }

    public boolean verifySignature(BigInteger cipherText, BigInteger signature) {
        RSAKeyPair<BigInteger> receiverPubKey = selfKeyManager.getThirdPartyKey();
        if (receiverPubKey != null) {
            Authentication auth = new Authentication(cipherText, signature, receiverPubKey);
            return auth.authenticate();
        }
        return false;
    }

    public String decryptMessage() {
        RSAKeyPair<BigInteger> selfPrivateKey = selfKeyManager.getPrivateKey();
        if (selfPrivateKey != null) {
            RSADecryption dec = new RSADecryption(message, selfPrivateKey);
            return dec.getPlainStr();
        }
        return null;
    }
}
