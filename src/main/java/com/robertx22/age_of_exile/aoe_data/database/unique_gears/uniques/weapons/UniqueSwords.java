package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CooldownReduction;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

import java.util.Arrays;

public class UniqueSwords implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.VOID_SWORD,
            "void_sword",
            "Grasp of the Void",
            BaseGearWeapons.SWORD.values())
            .setReplacesName()
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.sword, Number.HALF, Elements.Physical),
                getAttackDamageStat(WeaponTypes.sword, Number.HALF, Elements.Dark)
            ))
            .stats(Arrays.asList(
                new StatModifier(10, 20, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
                new StatModifier(5, 15, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark), ModType.FLAT),
                new StatModifier(10, 10, CooldownReduction.getInstance(), ModType.FLAT),
                new StatModifier(2, 4, Stats.LIFESTEAL.get(), ModType.FLAT),
                new StatModifier(5, 15, Stats.NIGHT_DAMAGE.get(), ModType.FLAT),
                new StatModifier(5, 10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.TORMENT), ModType.FLAT)
            ))
            .req(new StatRequirement().setStr(0.4F)
                .setAgi(0.6F))
            .build();
    }
}
