package encryption;

import key.KeyPair;
import utils.LongPair;
import utils.NumTools;

public class Encrypter {

    private LongPair k;

    public Encrypter(LongPair k) {
        this.k = k;
    }

    public NumString operate(NumString text) {

        long k1, k2;
        k1 = k.getProd();
        k2 = k.getK();
        System.out.println(k1 + " " + k2 + " " + text);

        NumString ns = new NumString();

        for (int i = 0; i < text.length(); i++) {
            long newChar = NumTools.modExp(text.charAt(i), k2, k1);
            ns.append(newChar);
        }
        ns.syncStrHex();
        return ns;
    }


}
