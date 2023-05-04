import org.testng.annotations.Test;

import key.KeyPair;
import key.hash.SHA256;
import encryption.Encrypter;
import utils.LongPair;
import utils.StringTools;

import java.io.UnsupportedEncodingException;

public class tests {

    @Test
    public void testKPG() {
        KeyPair kpg = new KeyPair(5, 9, false);
        kpg.keyPairGeneration();
        System.out.println(kpg);
    }

    @Test
    public void testEncryption() {

        KeyPair kpg = new KeyPair(1, 2, false);
        kpg.keyPairGeneration();
        LongPair pub = kpg.getPublicKey();
        LongPair priv = kpg.getPrivateKey();


        String original = "你好世界！Hello World!";
        Encrypter enc = new Encrypter(pub);
        String cipher = enc.operate(original);
        Encrypter dec = new Encrypter(priv);
        String plain = dec.operate(cipher);
        System.out.println(original + "->" +  cipher + " -> " + plain);

    }


    @Test
    public void testHash() {

        String original = "BlockChain";
        byte[] hashed = SHA256.hash(original.getBytes());

        System.out.println("Public Key: " + original);

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hashed.length; i++) {
            hexString.append(String.format("%02x", hashed[i]));
        }

        System.out.println("Hashed (Used for transmission) in hex: " + hexString);

        String parsedString;
        try {
            parsedString = StringTools.hex2str(hexString.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Hashed(Used for transmission) raw: " + parsedString);
    }
}
