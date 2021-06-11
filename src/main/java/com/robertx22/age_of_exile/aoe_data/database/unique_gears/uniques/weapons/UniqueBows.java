package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

import java.util.Arrays;

public class UniqueBows implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.PIERCE_BOW,
            "pierce_bow",
            "Riddleprick",
            BaseGearWeapons.BOW.values())
            .setReplacesName()
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.bow, Number.FULL, Elements.Physical)
            ))
            .stats(Arrays.asList(
                    new StatModifier(0.1F, 0.2F, 0.6F, 1.2F, new AttackDamage(Elements.Physical), ModType.FLAT),
                    new StatModifier(5, 15, Stats.PROJECTILE_DAMAGE_RECEIVED.get(), ModType.FLAT),
                    new StatModifier(1, 1, Stats.PIERCING_PROJECTILES.get(), ModType.FLAT), // value 1 and 1? or 100/100?
                    new StatModifier(5, 5, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT)
            ))
            .req(new StatRequirement().setStr(0.4F) //change becuase im not sure what stats bows use
                .setAgi(0.6F))
            .build();
    }
}
