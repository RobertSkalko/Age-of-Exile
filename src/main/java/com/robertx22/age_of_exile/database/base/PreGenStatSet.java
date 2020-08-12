package com.robertx22.age_of_exile.database.base;

import java.util.HashSet;
import java.util.List;

public class PreGenStatSet<T> {

    public PreGenStatSet(List<T> list) {
        this.list = new HashSet<>(list);

    }

    public HashSet<T> list = new HashSet<>();

}
