package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.spells.components.AuraSpellData;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.CastSpeed;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class AuraSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.aura("haste_aura", "Haste Aura", new AuraSpellData(0.25F,
            Arrays.asList(
                new StatModifier(5, 20, CastSpeed.getInstance()),
                new StatModifier(4, 15, AttackSpeed.getInstance())
            )))
            .build();

        SpellBuilder.aura("armor_aura", "Armor Aura", new AuraSpellData(0.2F,
            Arrays.asList(
                new StatModifier(20, 50, Armor.getInstance(), ModType.LOCAL_INCREASE)
            )))
            .build();

        SpellBuilder.aura("dodge_aura", "Dodge Aura", new AuraSpellData(0.2F,
            Arrays.asList(
                new StatModifier(50, 125, DodgeRating.getInstance())
            )))
            .build();

        SpellBuilder.aura("ms_aura", "Magic Shield Aura", new AuraSpellData(0.2F,
            Arrays.asList(
                new StatModifier(10, 25, MagicShield.getInstance())
            )))
            .build();

        SpellBuilder.aura("hp_reg_aura", "Health Regeneration Aura", new AuraSpellData(0.1F,
            Arrays.asList(
                new StatModifier(2, 5, Health.getInstance())
            )))
            .build();

        SpellBuilder.aura("mana_reg_aura", "Mana Regeneration Aura", new AuraSpellData(0.1F,
            Arrays.asList(
                new StatModifier(2, 5, Health.getInstance())
            )))
            .build();

        SpellBuilder.aura("ele_res_aura", "Elemental Resistance Aura", new AuraSpellData(0.3F,
            Arrays.asList(
                new StatModifier(15, 25, new ElementalResist(Elements.Elemental))
            )))
            .build();

        SpellBuilder.aura("crit_aura", "True Hit Aura", new AuraSpellData(0.25F,
            Arrays.asList(
                new StatModifier(15, 25, Accuracy.getInstance()),
                new StatModifier(5, 10, CriticalHit.getInstance())
            )))
            .build();

    }
}
