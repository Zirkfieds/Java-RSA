import encryption.RSACore;
import encryption.RSAEncryption;
import encryption.RSADecryption;
import key.Authentication;
import key.DigitalSignature;
import key.RSAKey;
import key.SHA256;
import org.testng.annotations.Test;
import utils.KeyManager;
import utils.RSAKeyPair;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

public class RSATest {

    private BigInteger a, b;

    @Test
    public void auth() {

        KeyManager.initFnMap();

        // A is the sender, B is the receiver
        KeyManager kA = new KeyManager();
        KeyManager kB = new KeyManager();
//        kA.generateKeys();
//        kB.generateKeys();

        kA.readKeys("AAAAAA+.b64", false);
        kA.readKeys("AAAAAA-.b64", false);
        kA.readKeys("BBBBBB+.b64", true);

        kB.readKeys("BBBBBB+.b64", false);
        kB.readKeys("BBBBBB-.b64", false);
        kB.readKeys("AAAAAA+.b64", true);

        RSAKeyPair<BigInteger> publicKeyA = kA.getPublicKey();
        RSAKeyPair<BigInteger> privateKeyA = kA.getPrivateKey();

        RSAKeyPair<BigInteger> publicKeyB = kB.getPublicKey();
        RSAKeyPair<BigInteger> privateKeyB = kB.getPrivateKey();

        String message = "A->B";

        // Encode the original message in base64
        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
        BigInteger plainText = new BigInteger(encodedMessage.getBytes());
        System.out.println(message + " -> " + encodedMessage);

        // A encrypts the plain text using B's public key
        RSAEncryption encMsg = new RSAEncryption(plainText, kA.getThirdPartyKey());
        BigInteger cipherText = encMsg.getCipher();
        System.out.println(cipherText.toString());

        // A generates signature using A's private key
        DigitalSignature ds = new DigitalSignature(cipherText, kA.getPrivateKey());
        BigInteger signature = ds.generateSignature();

        System.out.println(cipherText);
        System.out.println(signature);

        // B verifies the integrity of the message using A's public key
        Authentication auth = new Authentication(cipherText, signature, kB.getThirdPartyKey());
        if (auth.authenticate()) {
            System.out.println("Verified.");
            // B decrypts the cipher text using B's private key if the message is verified
            RSADecryption dec = new RSADecryption(cipherText, kB.getPrivateKey());
            // Decode the deciphered message
            String encodedReceivedMessage = dec.getPlainStr();
            System.out.println(encodedReceivedMessage + " -> " + new String(Base64.getDecoder().decode(encodedReceivedMessage)));
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
