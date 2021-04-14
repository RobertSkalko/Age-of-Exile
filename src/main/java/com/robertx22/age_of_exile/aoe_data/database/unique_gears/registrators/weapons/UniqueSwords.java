package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.weapons;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseGearWeapons;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.offense.NightDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CooldownReduction;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class UniqueSwords implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.VOID_SWORD,
            "void_sword",
            "Grasp of the Void",
            BaseGearWeapons.SWORD.values())
            .baseStats(Arrays.asList(
                getAttackDamageStat(WeaponTypes.Sword, Number.HALF, Elements.Physical),
                getAttackDamageStat(WeaponTypes.Sword, Number.HALF, Elements.Dark)
            ))
            .stats(Arrays.asList(
                new StatModifier(10, 20, CriticalDamage.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, new ElementalDamageBonus(Elements.Dark), ModType.FLAT),
                new StatModifier(10, 10, CooldownReduction.getInstance(), ModType.FLAT),
                new StatModifier(2, 4, Lifesteal.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, NightDamage.getInstance(), ModType.FLAT),
                new StatModifier(5, 10, ChanceToApplyEffect.TORMENT, ModType.FLAT)
            ))
            .req(new StatRequirement().setStr(0.4F)
                .setAgi(0.6F))
            .build();
    }
}
