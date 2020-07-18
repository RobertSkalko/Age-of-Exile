package com.robertx22.mine_and_slash.a_libraries.curios.interfaces;

import com.robertx22.mine_and_slash.uncommon.interfaces.IGearItem;

public interface ICuriosType extends IGearItem {

    String curioTypeName();

    default boolean rightClickEquip() {
        return true;
    }

}
