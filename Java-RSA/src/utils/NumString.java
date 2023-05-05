package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class NumString {
    private ArrayList<Long> numString;
    private String hexString;

    public NumString() {
        numString = new ArrayList<>();
        hexString = "";
    }

    public NumString(String string) {
        int len = string.length();
        numString = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            numString.add((long)string.charAt(i));
        }
        hexString = StringTools.lng2hex(numString);
    }

    public NumString(NumString nstring) {
        this.numString = nstring.getNumString();
        this.hexString = nstring.getHexString();
    }

    public void syncStrHex() {
        this.hexString = StringTools.lng2hex(numString);
    }

    public ArrayList<Long> getNumString() {
        return numString;
    }

    public String getHexString() {
        return hexString;
    }

    public boolean append(long ch) {
        return numString.add((long)ch);
    }

    @Override
    public String toString() {
        return "NumString{" +
                "numString=" + numString +
                '}';
    }

    public int length() {
        return numString.size();
    }

    public long charAt(int index) {
        return (long)(numString.get(index));
    }
}
