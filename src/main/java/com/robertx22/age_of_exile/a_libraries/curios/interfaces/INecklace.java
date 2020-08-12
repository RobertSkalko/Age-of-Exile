package com.robertx22.age_of_exile.a_libraries.curios.interfaces;

import com.robertx22.age_of_exile.a_libraries.curios.CurioSlots;

public interface INecklace extends ICuriosType {

    @Override
    public default String curioTypeName() {
        return CurioSlots.NECKLACE.name;
    }

}
