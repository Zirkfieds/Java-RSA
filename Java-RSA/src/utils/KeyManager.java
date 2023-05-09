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
    private RSAKeyPair<BigInteger> publicKey, privateKey, thirdPartyKey;
    private String timestamp;

    private static final String fnReg = "[0-9AB]+[+|-].b64";

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
        this.thirdPartyKey = null;
        this.timestamp = null;
        System.out.println("Stored keys cleared.");
    }

    public boolean checkKeys() {
        return this.publicKey != null && this.privateKey != null;
    }

    public void setPublicKey(RSAKeyPair<BigInteger> publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(RSAKeyPair<BigInteger> privateKey) {
        this.privateKey = privateKey;
    }

    public void setThirdPartyKey(RSAKeyPair<BigInteger> thirdPartyKey) {
        this.thirdPartyKey = thirdPartyKey;
    }

    public RSAKeyPair<BigInteger> getPublicKey() {
        return publicKey;
    }

    public RSAKeyPair<BigInteger> getPrivateKey() {
        return privateKey;
    }

    public RSAKeyPair<BigInteger> getThirdPartyKey() {
        return thirdPartyKey;
    }

    public int readKeys(String fn, boolean isThirdParty) {

        if (!fn.matches(KeyManager.fnReg)) {
            System.out.println("Invalid filename.");
            return 0;
        }

        int flag = 0;

        try {
            FileInputStream fi = new FileInputStream(fn);
            String keyString = new String(Base64.getDecoder().decode(fi.readAllBytes()));

            String[] keys = keyString.split("@");
            if (keys.length != 2) {
                System.out.println("Compromised .b64 file.");
                return 0;
            }

            if (fn.charAt(6) == '-') {
                this.privateKey = new RSAKeyPair<>(new BigInteger(keys[0]), new BigInteger(keys[1]));
                flag = -1;
            } else {
                if (isThirdParty) {
                    this.thirdPartyKey = new RSAKeyPair<>(new BigInteger(keys[0]), new BigInteger(keys[1]));
                } else {
                    this.publicKey = new RSAKeyPair<>(new BigInteger(keys[0]), new BigInteger(keys[1]));
                }
                flag = 1;
            }

            timestamp = fn.substring(0, 6);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return flag;
    }

    public String getKeyFileName(boolean isPrivateKey) {
        return timestamp.substring(timestamp.length() - 6) +
                ((isPrivateKey) ? fnMap.get("priv") : fnMap.get("pub")) +
                ".b64";
    }

    public boolean writeKeys() {

        if (!checkKeys()) {
            System.out.println("Compromised key structure.");
            return false;
        }

        String baseFn = timestamp.substring(timestamp.length() - 6);

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
                ", thirdPartyKey=" + thirdPartyKey.toString() +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
