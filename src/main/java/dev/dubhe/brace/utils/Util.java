package dev.dubhe.brace.utils;

public class Util {
    public static String describeError(Throwable throwable) {
        if (throwable.getCause() != null) {
            return describeError(throwable.getCause());
        } else {
            return throwable.getMessage() != null ? throwable.getMessage() : throwable.toString();
        }
    }
}
