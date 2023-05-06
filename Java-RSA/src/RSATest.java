import encryption.RSAEncryption;
import encryption.RSADecryption;
import key.Authentication;
import key.DigitalSignature;
import key.RSAKey;
import key.SHA256;
import org.testng.annotations.Test;
import utils.MessagePair;

import java.math.BigInteger;
import java.util.Arrays;

public class RSATest {

    private BigInteger a, b;

    @Test
    public void makeKey() {

        RSAKey k = new RSAKey();
        System.out.println(k);

        String message = "Hello World!你好世界！";

        RSAEncryption enc = new RSAEncryption(new BigInteger(message.getBytes()), k.getPublicKeyPair());
        BigInteger cbytes = enc.getCipher();

        RSADecryption dec = new RSADecryption(cbytes, k.getPrivateKeyPair());
        String pstr = dec.getPlainStr();

        System.out.println(message.equals(pstr));

    }

    @Test
    public void auth(){

        // A is the sender, B is the server/receiver
        RSAKey kA = new RSAKey();
        RSAKey kB = new RSAKey();

        MessagePair<BigInteger> publicKeyA = kA.getPublicKeyPair();
        MessagePair<BigInteger> privateKeyA = kA.getPrivateKeyPair();

        MessagePair<BigInteger> publicKeyB = kB.getPublicKeyPair();
        MessagePair<BigInteger> privateKeyB = kB.getPrivateKeyPair();

        String message = "Hello World!";
        BigInteger plainText = new BigInteger(message.getBytes());

        // A encrypts the plain text using B's public key
        RSAEncryption encMsg = new RSAEncryption(plainText, publicKeyB);
        BigInteger cipherText = encMsg.getCipher();

        // A generates signature using A's private key
        DigitalSignature ds = new DigitalSignature(cipherText, privateKeyA);
        BigInteger signature = ds.generateSignature();

        // B verifies the integrity of the message using A's public key
        Authentication auth = new Authentication(cipherText, signature, publicKeyA);
        if (auth.authenticate()) {
            System.out.println("Verified.");
            // B decrypts the cipher text using B's private key if the message is verified
            RSADecryption dec = new RSADecryption(cipherText, privateKeyB);
            BigInteger decryptedPlainText = dec.getPlain();
            System.out.println(dec.getPlainStr());
        } else {
            System.out.println("Not Verified.");
        }

    }

    @Test
    public void hashTest() {
        byte[] msg = "Hello".getBytes();
        System.out.println(Arrays.toString(SHA256.hash(msg)));
        System.out.println(Arrays.toString(SHA256.hash(msg)));
    }

}
