package com.robertx22.age_of_exile.uncommon.utilityclasses;

public class Safety {
    public static void logIfNull(Object obj, String msg) {
        if (obj == null) {
            System.out.println(msg);
        }
    }
}
