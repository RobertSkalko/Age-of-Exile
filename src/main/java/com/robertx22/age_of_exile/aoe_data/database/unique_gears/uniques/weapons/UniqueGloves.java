package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapons;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

import java.util.Arrays;

public class UniqueGloves implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.LIVING_INFERNO_GLOVE,
            "living_inferno",
            "Living Inferno",
            BaseGearWeapons.GLOVE.values())
            .setReplacesName()
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.glove, Number.FULL, Elements.Fire)
            ))
            .stats(Arrays.asList(
                new StatModifier(10, 20, Stats.CRIT_DAMAGE.get()),
                new StatModifier(5, 15, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
                new StatModifier(2, 4, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.attack))),
                new StatModifier(2, 4, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack))),
                new StatModifier(20, 30, Stats.CHANCE_TO_GIVE_EFFECT_ON_SELF.get(BeneficialEffects.LIVING_INFERNO))
            ))
            .req(new StatRequirement().setStr(0.4F)
                .setAgi(0.6F))
            .build();
    }
}

