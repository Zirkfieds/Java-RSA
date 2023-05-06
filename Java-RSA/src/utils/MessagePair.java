package utils;

public class MessagePair<T> {
    private T msgl, msgr;

    public MessagePair(T msgl, T msgr) {
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
}
