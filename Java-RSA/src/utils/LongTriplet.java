package utils;

public class LongTriplet {

    private long prod;
    private long pubk;
    private long prvk;

    public LongTriplet(long prod, long pubk, long prvk) {
        this.prod = prod;
        this.pubk = pubk;
        this.prvk = prvk;
    }

    public long getProd() {
        return prod;
    }

    public void setProd(long prod) {
        this.prod = prod;
    }

    public long getPubk() {
        return pubk;
    }

    public void setPubk(long pubk) {
        this.pubk = pubk;
    }

    public long getPrvk() {
        return prvk;
    }

    public void setPrvk(long prvk) {
        this.prvk = prvk;
    }

    @Override
    public String toString() {
        return "LongTriplet{" +
                "prod=" + prod +
                ", pubk=" + pubk +
                ", prvk=" + prvk +
                '}';
    }
}
