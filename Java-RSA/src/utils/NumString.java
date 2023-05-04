package utils;

import java.util.Arrays;

public class NumString {
    private String strString;
    private int[] numString;

    public NumString(String string) {
        this.strString = string;
        int len = string.length();
        numString = new int[len];
        for (int i = 0; i < len; i++) {
            numString[i] = string.charAt(i);
        }
    }

    public void setStrString(String strString) {
        this.strString = strString;
    }

    public void setNumString(int[] numString) {
        this.numString = numString;
    }

    public String getStrString() {
        return strString;
    }

    public int[] getNumString() {
        return numString;
    }

    @Override
    public String toString() {
        return "NumString{" +
                "strString='" + strString + '\'' +
                ", numString=" + Arrays.toString(numString) +
                '}';
    }
}
