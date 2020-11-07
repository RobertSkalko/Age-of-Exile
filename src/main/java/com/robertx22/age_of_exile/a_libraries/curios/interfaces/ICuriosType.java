package com.robertx22.age_of_exile.a_libraries.curios.interfaces;

public interface ICuriosType {

    String curioTypeName();

    default boolean rightClickEquip() {
        return true;
    }

}
