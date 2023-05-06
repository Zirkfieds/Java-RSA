package utils;

public class RSAKeyPair<T> {
    private T msgl, msgr;

    public RSAKeyPair(T msgl, T msgr) {
        this.msgl = msgl;
        this.msgr = msgr;
    }

    public T getMsgl() {
        return msgl;
    }

    public void setMsgl(T msgl) {
        this.msgl = msgl;
    }

    public T getMsgr() {
        return msgr;
    }

    public void setMsgr(T msgr) {
        this.msgr = msgr;
    }

    @Override
    public String toString() {
        return "(" + msgl + ", " + msgr + ")";
    }
}
