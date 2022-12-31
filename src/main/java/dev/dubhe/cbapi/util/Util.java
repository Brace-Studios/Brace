package dev.dubhe.cbapi.util;

public class Util {
    public static String describeError(Throwable throwable) {
        if (throwable.getCause() != null) {
            return describeError(throwable.getCause());
        } else {
            return throwable.getMessage() != null ? throwable.getMessage() : throwable.toString();
        }
    }
}
