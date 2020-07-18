package com.robertx22.mine_and_slash.a_libraries.curios.interfaces;

import com.robertx22.mine_and_slash.a_libraries.curios.CurioSlots;

public interface INecklace extends ICuriosType {

    @Override
    public default String curioTypeName() {
        return CurioSlots.NECKLACE.name;
    }

}
