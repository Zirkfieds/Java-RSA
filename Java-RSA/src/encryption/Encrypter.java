package encryption;

import key.KeyPair;
import utils.LongPair;
import utils.NumTools;

public class Encrypter {

    private LongPair k;

    public Encrypter(LongPair k) {
        this.k = k;
    }

    public String operate(String text) {
        // TODO: Fix when p and q are very big by storing the actual hex array
        // TODO: Fix unsupported encoding issues (Chinese characters etc.)
        long k1, k2;
        k1 = k.getProd();
        k2 = k.getK();
        System.out.println(k1 + " " + k2 + " " + text);
        StringBuilder enc = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            long newChar = NumTools.modExp(text.charAt(i), k2, k1);
//            System.out.println((long)text.charAt(i) + " " +  (long) newChar);
            enc.append((char)newChar);
        }
        return enc.toString();
    }


}
