package com.robertx22.age_of_exile.a_libraries.curios.interfaces;

import com.robertx22.age_of_exile.a_libraries.curios.CurioSlots;

public interface ISalvageBag extends ICuriosType {
    @Override
    public default String curioTypeName() {
        return CurioSlots.SALVAGE_BAG.name;
    }
}