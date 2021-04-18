package com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BaseLeatherArmors;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class FabledArmor implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.UNSEEING_EYE,
            "unseeing_eye",
            "Eternal Void, Unseeing Eye",
            "",
            BaseLeatherArmors.HELMETS.values())
            .setFabled()
            .setReplacesName()
            .baseStats(
                getStat(ArmorStat.HEALTH, ArmorType.MEDIUM, ArmorSlot.HELMET),
                getStat(ArmorStat.DODGE, ArmorType.MEDIUM, ArmorSlot.HELMET),
                new StatModifier(10, 25, new ElementalResist(Elements.Dark), ModType.FLAT),
                new StatModifier(10, 25, new ElementalResist(Elements.Light), ModType.FLAT)
            )
            .stats(Arrays.asList(
                new StatModifier(5, 10, new ElementalDamageBonus(Elements.Physical), ModType.FLAT),
                new StatModifier(5, 15, ArmorPenetration.getInstance(), ModType.FLAT),
                new StatModifier(10, 15, CriticalDamage.getInstance(), ModType.FLAT),
                new StatModifier(3, 6, AttackStyleDamage.MELEE, ModType.FLAT),
                new StatModifier(2, 5, Dexterity.INSTANCE, ModType.FLAT),
                new StatModifier(2, 5, Agility.INSTANCE, ModType.FLAT),
                new StatModifier(10, 10, SpecialStats.VOID_EYE, ModType.FLAT)
            ))
            .req(new StatRequirement()
                .setDex(0.5F)
                .setStr(0.5F)
                .setAgi(0.75F))
            .devComment("Domain of heaven and earth: eternal void. Item meant for assasins. Edgy and shit")
            .build();

    }
}
