package utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class StringTools {

    public static final int unpackLen = 6;

    public static String hex2str(String hs) throws UnsupportedEncodingException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        for (int i = 0; i < hs.length(); i += unpackLen) {
//            String str = hs.substring(i, i + unpackLen);
//            baos.write(Integer.parseInt(str, 16));
//        }
//        return baos.toString(StandardCharsets.UTF_8).replace((char) 0, ' ').strip();
        long[] longs = new long[hs.length() / unpackLen];
        for (int i = 0; i < hs.length(); i += unpackLen) {
            String str = hs.substring(i, i + unpackLen);
            longs[i / 16] = Long.parseLong(str, 16);
        }
        return Arrays.toString(longs);
    }

    public static String lng2hex(ArrayList<Long> lng) {
        StringBuilder hexString = new StringBuilder();
        for (long l : lng) {
            hexString.append(String.format("%0" + StringTools.unpackLen + "x", l));
        }
        return hexString.toString();
    }

}
