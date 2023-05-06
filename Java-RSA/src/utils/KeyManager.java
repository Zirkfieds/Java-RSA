package utils;

import key.RSAKey;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class KeyManager {

    private RSAKey key;
    private RSAKeyPair<BigInteger> publicKey, privateKey;
    private String timestamp;

    private static final String fnReg = "\\d+[+|-].b64";

    private static final Map<String, String> fnMap = new HashMap<>();

    public KeyManager() {}

    public static void initFnMap() {
        fnMap.put("priv", "-");
        fnMap.put("pub", "+");
    }

    public void generateKeys() {
        this.key = new RSAKey();

        this.publicKey = key.getPublicKeyPair();
        this.privateKey = key.getPrivateKeyPair();

        this.timestamp = Long.toString(System.currentTimeMillis());
    }

    public void clearKeys() {
        this.key = null;
        this.publicKey = null;
        this.privateKey = null;
        this.timestamp = null;
        System.out.println("Stored keys cleared.");
    }

    public boolean checkKeys() {
        return this.publicKey != null && this.privateKey != null;
    }

    public RSAKeyPair<BigInteger> getPublicKey() {
        return publicKey;
    }

    public RSAKeyPair<BigInteger> getPrivateKey() {
        return privateKey;
    }

    public boolean readKeys(String fn) {

        if (!fn.matches(KeyManager.fnReg)) {
            System.out.println("Invalid filename.");
            return false;
        }

        try {
            FileInputStream fi = new FileInputStream(fn);
            String keyString = new String(Base64.getDecoder().decode(fi.readAllBytes()));

            String[] keys = keyString.split("@");
            if (keys.length != 2) {
                System.out.println("Compromised .b64 file.");
                return false;
            }
            System.out.println(Arrays.toString(keys));

            if (fn.charAt(6) == '-') {
                this.privateKey = new RSAKeyPair<>(new BigInteger(keys[0]), new BigInteger(keys[1]));
            } else {
                this.publicKey = new RSAKeyPair<>(new BigInteger(keys[0]), new BigInteger(keys[1]));
            }

            timestamp = fn.substring(0, 6);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean writeKeys() {

        if (!checkKeys()) {
            System.out.println("Compromised key structure.");
            return false;
        }

        // TODO: Enable timestamp in filename after file selector GUI is implemented
//        String baseFn = timestamp.substring(timestamp.length() - 6) + "_";
        String baseFn = "000000";
        String privFn = baseFn + fnMap.get("priv");
        String pubFn = baseFn + fnMap.get("pub");

        String privKeyPairStr = privateKey.getMsgl().toString() + "@" + privateKey.getMsgr().toString();
        byte[] encodedPrivKeyPair = Base64.getEncoder().encode(privKeyPairStr.getBytes());

        String pubKeyPairStr = publicKey.getMsgl().toString() + "@" + publicKey.getMsgr().toString();
        byte[] encodedPubKeyPair = Base64.getEncoder().encode(pubKeyPairStr.getBytes());

        try {
            FileOutputStream privFos = new FileOutputStream(privFn + ".b64");
            privFos.write(encodedPrivKeyPair);
            privFos.close();

            FileOutputStream pubFos = new FileOutputStream(pubFn + ".b64");
            pubFos.write(encodedPubKeyPair);
            pubFos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KeyManager{" +
                "publicKey=" + publicKey.toString() +
                ", privateKey=" + privateKey.toString() +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
