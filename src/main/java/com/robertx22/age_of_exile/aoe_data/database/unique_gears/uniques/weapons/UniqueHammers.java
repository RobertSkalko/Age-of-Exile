package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons;

import com.robertx22.age_of_exile.aoe_data.database.DataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

import java.util.Arrays;

public class UniqueHammers implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                ModRegistry.UNIQUE_GEARS.ROSE_HAMMER,
                "rose_hammer",
                "Rose Dragon",
                BaseGearWeapons.HAMMER.values())
            .setReplacesName()
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.hammer, DataHelper.Number.HALF, Elements.Physical),
                getAttackDamageStat(WeaponTypes.hammer, DataHelper.Number.HALF, Elements.Nature)
            ))
            .stats(Arrays.asList(
                new StatModifier(25, 25, Stats.DOT_DAMAGE.get(), ModType.FLAT),
                new StatModifier(5, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Nature), ModType.FLAT),
                new StatModifier(5, 25, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT),
                new StatModifier(5, 10, SpecialStats.PERC_SELF_HP_DMG_WHEN_IMMOBILZING, ModType.FLAT)
            ))
            .req(new StatRequirement().setStr(0.5F)
                .setVit(0.75F))
            .build();
    }
}

