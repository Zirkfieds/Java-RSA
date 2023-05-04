package utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringTools {

    private static final int unpackLen = 2;

    public static String hex2str(String hs) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < hs.length(); i += unpackLen) {
            String str = hs.substring(i, i + unpackLen);
            baos.write(Integer.parseInt(str, 16));
        }
        return baos.toString(StandardCharsets.UTF_8).replace((char) 0, ' ').strip();
    }
}
