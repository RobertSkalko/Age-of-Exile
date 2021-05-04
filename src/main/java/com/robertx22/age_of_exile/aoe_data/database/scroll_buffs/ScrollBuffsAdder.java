package com.robertx22.age_of_exile.aoe_data.database.scroll_buffs;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ManaCost;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

public class ScrollBuffsAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        ScrollBuff.of("mage", "Of the Mage", "Veneficus",
            new StatModifier(10, 30, SpellDamage.getInstance()),
            new StatModifier(10, 30, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        ScrollBuff.of("hunter", "Of the Hunter", "Venator",
            new StatModifier(10, 30, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new StatModifier(10, 30, Stats.CRIT_DAMAGE.get(), ModType.LOCAL_INCREASE)
        );
        ScrollBuff.of("warrior", "Of the Warrior", "Torpent",
            new StatModifier(10, 30, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new StatModifier(8, 25, AttackSpeed.getInstance(), ModType.FLAT)
        );
        ScrollBuff.of("paladin", "Of the Paladin", "Fortissimus",
            new StatModifier(10, 30, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new StatModifier(10, 30, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        ScrollBuff.of("crit", "Of Criticals", "Verum",
            new StatModifier(10, 30, Stats.CRIT_DAMAGE.get()),
            new StatModifier(10, 30, Stats.SPELL_CRIT_DAMAGE.get())
        );

        ScrollBuff.of("costly_spell_dmg", "Of Sacrificial Magic", "Feodo",
            new StatModifier(20, 40, SpellDamage.getInstance()),
            new StatModifier(10, 30, ManaCost.getInstance())
        );

        eleDmg("fire_dmg", "Of Firestorms", "Ignis", Elements.Fire);
        eleDmg("cold_dmg", "Of Snowstorms", "Frigus", Elements.Water);
        eleDmg("poison_dmg", "Of Calamity", "Venemun", Elements.Nature);
        eleDmg("light_dmg", "Of Sunshine", "Lux", Elements.Nature);
        eleDmg("dark_dmg", "Of Curses", "Maledictum", Elements.Nature);

    }

    void eleDmg(String id, String name, String desc, Elements ele) {
        ScrollBuff.of(id, name, desc,
            new StatModifier(10, 50, Stats.ELEMENTAL_DAMAGE.get(ele), ModType.FLAT)
        );
    }
}
