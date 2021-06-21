package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueDaggers implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                ModRegistry.UNIQUE_GEARS.DEATH_CHILL_DAGGER,
                "death_chill",
                "Death's Chill",
                BaseGearWeapons.DAGGER.values())
            .setReplacesName()
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.dagger, Number.FULL, Elements.Water)
            ))
            .stats(Arrays.asList(
                new StatModifier(2, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)), ModType.FLAT),
                new StatModifier(5, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT),
                new StatModifier(5, 25, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
                new StatModifier(20, 30, Stats.ELE_DAMAGE_WHEN_TARGET_IS_LOW_HP.get(Elements.Water), ModType.FLAT)
            ))
            .req(new StatRequirement().setAgi(0.75F)
                .setDex(0.5F))
            .build();
    }

}

