package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SchoolBuilder.of("water", "Ocean")
            .addSpell(WaterSpells.FROSTBALL_ID, new PointData(0, 0))
            .addSpell(WaterSpells.FROST_NOVA_AOE, new PointData(3, 1))
            .build();

    }
}
