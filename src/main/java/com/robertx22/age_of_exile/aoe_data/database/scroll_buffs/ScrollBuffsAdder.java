package com.robertx22.age_of_exile.aoe_data.database.scroll_buffs;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ScrollBuffsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        ScrollBuff.of("mage", "Of the Mage", "Magic",
            new StatModifier(10, 30, SpellDamage.getInstance()),
            new StatModifier(10, 30, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        ScrollBuff.of("hunter", "Of the Hunter", "Hunting",
            new StatModifier(10, 30, AttackStyleDamage.RANGED),
            new StatModifier(10, 30, CriticalDamage.getInstance(), ModType.LOCAL_INCREASE)
        );
        ScrollBuff.of("warrior", "Of the Warrior", "Barbarism",
            new StatModifier(10, 30, AttackStyleDamage.MELEE),
            new StatModifier(8, 25, AttackSpeed.getInstance(), ModType.FLAT)
        );
        ScrollBuff.of("paladin", "Of the Paladin", "Righteousness",
            new StatModifier(10, 30, AttackStyleDamage.MELEE),
            new StatModifier(10, 30, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        ScrollBuff.of("fire_dmg", "Of Firestorms", "Fire",
            new StatModifier(10, 50, new ElementalDamageBonus(Elements.Fire), ModType.FLAT)
        );
        ScrollBuff.of("cold_dmg", "Of Snowstorms", "Cold",
            new StatModifier(10, 50, new ElementalDamageBonus(Elements.Fire), ModType.FLAT)
        );

    }
}
