import org.testng.annotations.Test;

import key.KeyPair;
import key.hash.SHA256;
import encryption.Encrypter;
import utils.LongPair;
import utils.NumString;
import utils.StringTools;

import java.io.UnsupportedEncodingException;

public class tests {

    @Test
    public void testEncryption() {

        KeyPair kpg = new KeyPair(4, 5, false);
        kpg.keyPairGeneration();
        LongPair pub = kpg.getPublicKey();
        LongPair priv = kpg.getPrivateKey();

        String[] testStrings = {
                "你好世界！Hello world! 这是一个长输入测试。This is a long input test.",
                "你好世界！这是一个长输入测试。",
                "Hello world! This is a long input test."
        };

        NumString original = new NumString(testStrings[0]);
        Encrypter enc = new Encrypter(pub);
        NumString cipher = enc.operate(original);
        Encrypter dec = new Encrypter(priv);
        NumString plain = dec.operate(cipher);

        System.out.println(
                original.getHexString()
                        + " -> " + cipher.getHexString()
                        + " -> " + plain.getHexString()
                        + "\n" + original.getHexString().equals(plain.getHexString())
        );
    }


    @Test
    public void testHash() {

        String original = "BlockChain";
        byte[] hashed = SHA256.hash(original.getBytes());

        System.out.println("Public Key: " + original);

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hashed.length; i++) {
            hexString.append(String.format("%0" + StringTools.unpackLen + "x", hashed[i]));
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
