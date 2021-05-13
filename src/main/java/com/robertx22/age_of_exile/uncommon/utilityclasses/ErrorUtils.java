package com.robertx22.age_of_exile.uncommon.utilityclasses;

public class ErrorUtils {

    public static void ifFalse(boolean bool, String str) {
        if (!bool) {
            throw new RuntimeException(str);
        }
    }

    public static void ifFalse(boolean bool) {
        if (!bool) {
            throw new RuntimeException("");
        }
    }
}
