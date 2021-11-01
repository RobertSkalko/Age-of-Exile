package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.slot_specific_op.ArmorSpecific;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.slot_specific_op.RingSpecific;
import com.robertx22.age_of_exile.aoe_data.database.affixes.adders.slot_specific_op.WeaponOrNecklaceSpecific;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearSlotPowerAffixesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {
        new ArmorSpecific().registerAll();
        new RingSpecific().registerAll();
        new WeaponOrNecklaceSpecific().registerAll();
    }

}
