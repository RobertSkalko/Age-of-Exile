package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueStaffs implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                ModRegistry.UNIQUE_GEARS.BURNING_BRILLIANCE_STAFF,
                "burning_brilliance",
                "Burning Brilliance",
                BaseGearWeapons.STAFF.values())
            .setReplacesName()
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.staff, Number.FULL, Elements.Fire)
            ))
            .stats(Arrays.asList(
                new StatModifier(5, 30, Stats.ELE_DOT_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new StatModifier(5, 15, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new StatModifier(5, 20, Stats.INCREASED_AREA.get(), ModType.FLAT),
                new StatModifier(1, 5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN))
            ))
            .req(new StatRequirement().setWis(0.4F)
                .setInt(0.6F))
            .build();

    }
}
