package dev.dubhe.brace.utils.tools;

import java.io.IOException;
import java.io.InputStream;

public abstract class StreamTool {
    public static byte[] readStream(InputStream stream) throws IOException {
        byte[] result = new byte[0];
        byte[] buffer = new byte[1024];
        int len;
        while ((len = stream.read(buffer)) != -1) {
            byte[] newResult = new byte[result.length + len];
            System.arraycopy(result, 0, newResult, 0, result.length);
            System.arraycopy(buffer, 0, newResult, result.length, len);
            result = newResult;
        }
        return result;
    }
}
