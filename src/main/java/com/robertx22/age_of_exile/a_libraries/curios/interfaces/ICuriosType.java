package com.robertx22.age_of_exile.a_libraries.curios.interfaces;

import com.robertx22.age_of_exile.uncommon.interfaces.IGearItem;

public interface ICuriosType extends IGearItem {

    String curioTypeName();

    default boolean rightClickEquip() {
        return true;
    }

}
