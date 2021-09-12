package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.TotemSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.NatureSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.RangerSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SchoolBuilder.of("hunting", "Hunting")
            .addSpell(RangerSpells.ARROW_STORM, new PointData(0, 0))
            .addSpell(RangerSpells.MAKE_ARROWS, new PointData(10, 0))

            .addSpell(RangerSpells.DASH_ID, new PointData(5, 1))
            .addSpell(RangerSpells.HUNTER_POTION, new PointData(10, 1))

            .addSpell(RangerSpells.THE_HUNT, new PointData(10, 2))
            .addSpell(RangerSpells.POISON_ARROW, new PointData(2, 2))

            .addSpell(RangerSpells.CHARGED_BOLT, new PointData(0, 3))
            .addSpell(RangerSpells.BACKFLIP, new PointData(4, 3))

            .addSpell(RangerSpells.FIRE_TRAP, new PointData(6, 4))
            .addSpell(RangerSpells.FROST_TRAP, new PointData(7, 4))
            .addSpell(RangerSpells.POISON_TRAP, new PointData(8, 4))

            .addSpell(RangerSpells.EXPLOSIVE_ARROW_ID, new PointData(2, 5))

            .build();

        SchoolBuilder.of("nature", "Nature")
            .addSpell(NatureSpells.POISONBALL_ID, new PointData(0, 0))
            .addSpell(NatureSpells.POISON_WEAPONS, new PointData(7, 0))

            .addSpell(NatureSpells.THORN_ARMOR, new PointData(10, 1))
            .addSpell(NatureSpells.NATURE_BALM, new PointData(2, 1))

            .addSpell(NatureSpells.ENTANGLE_SEED, new PointData(4, 2))

            .addSpell(TotemSpells.HEAL_TOTEM_ID, new PointData(6, 2))
            .addSpell(TotemSpells.MANA_TOTEM_ID, new PointData(7, 2))
            .addSpell(TotemSpells.GUARD_TOTEM_ID, new PointData(8, 2))

            .addSpell(NatureSpells.POISON_CLOUD, new PointData(2, 3))

            .addSpell(NatureSpells.REFRESH, new PointData(10, 6))
            .build();

        SchoolBuilder.of("fire", "Fire")
            .addSpell(FireSpells.FIREBALL_ID, new PointData(0, 0))
            .addSpell(FireSpells.FLAME_STRIKE_ID, new PointData(5, 0))

            .addSpell(FireSpells.METEOR, new PointData(2, 3))

            .addSpell(FireSpells.OVERLOAD, new PointData(10, 5))

            .build();

        SchoolBuilder.of("water", "Ocean")
            .addSpell(WaterSpells.FROSTBALL_ID, new PointData(0, 0))
            .addSpell(WaterSpells.TIDAL_STRIKE, new PointData(4, 0))

            .addSpell(WaterSpells.FROST_NOVA_AOE, new PointData(2, 1))
            .addSpell(WaterSpells.WATER_BREATH, new PointData(10, 1))
            .addSpell(WaterSpells.FROST_ARMOR, new PointData(6, 1))

            .addSpell(WaterSpells.MAGE_CIRCLE, new PointData(10, 3))
            .build();

    }
}
