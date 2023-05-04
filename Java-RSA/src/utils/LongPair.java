package utils;

public class LongPair {

    private long prod;
    private long k;

    public LongPair(long prod, long k) {
        this.prod = prod;
        this.k = k;
    }

    public long getProd() {
        return prod;
    }

    public void setProd(long prod) {
        this.prod = prod;
    }

    public long getK() {
        return k;
    }

    public void setK(long k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "LongPair{" +
                "prod=" + prod +
                ", k=" + k +
                '}';
    }
}
