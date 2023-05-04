package key;

import utils.LongPair;
import utils.LongTriplet;
import utils.NumTools;

public class KeyPair {

    private long p;
    private long q;

    private LongPair publicKey;
    private LongPair privateKey;

    public KeyPair(int p, int q, boolean given) {
        if (!given) {
            this.p = NumTools.genPrime(p);
            long tq;
            do {
                tq = NumTools.genPrime(q);
            } while (tq == this.p);
            this.q = tq;
        } else {
            this.p = p;
            this.q = q;
        }
    }

    public LongPair getPublicKey() {
        return publicKey;
    }

    public LongPair getPrivateKey() {
        return privateKey;
    }

    public void keyPairGeneration() {
        LongTriplet lt = genKeyPair(); // TODO: CHANGE THE PARAMETERS TO NULL LATER
        this.publicKey = new LongPair(lt.getProd(), lt.getPubk());
        this.privateKey = new LongPair(lt.getProd(), lt.getPrvk());
    }

    private LongTriplet genKeyPair() {
        long prod = p * q;
        long phi = (p - 1) * (q - 1);
        long e;
        do {
            e = NumTools.genNum(0, phi);
        } while (!NumTools.isCoprime(e, phi));
        long i = NumTools.invMod(e, phi);
        return new LongTriplet(prod, e, i);
    }

    private LongTriplet genKeyPair(long given) {
        long prod = p * q;
        long phi = (p - 1) * (q - 1);
        long e = given;
        long i = NumTools.invMod(e, phi);
        return new LongTriplet(prod, e, i);
    }

    @Override
    public String toString() {
        return "KeyPair{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                '}';
    }
}
