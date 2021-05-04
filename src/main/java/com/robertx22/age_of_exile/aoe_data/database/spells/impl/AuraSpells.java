package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.spells.components.AuraSpellData;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.speed.CastSpeed;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class AuraSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.aura(AttackPlayStyle.ranged, "haste_aura", "Haste Aura", new AuraSpellData(0.25F,
            Arrays.asList(
                new StatModifier(5, 20, CastSpeed.getInstance()),
                new StatModifier(4, 15, AttackSpeed.getInstance())
            )))
            .build();

        SpellBuilder.aura(AttackPlayStyle.melee, "armor_aura", "Armor Aura", new AuraSpellData(0.2F,
            Arrays.asList(
                new StatModifier(2, 4, Armor.getInstance(), ModType.FLAT)
            )))
            .build();

        SpellBuilder.aura(AttackPlayStyle.ranged, "dodge_aura", "Dodge Aura", new AuraSpellData(0.2F,
            Arrays.asList(
                new StatModifier(2, 4, DodgeRating.getInstance(), ModType.FLAT)
            )))
            .build();

        SpellBuilder.aura(AttackPlayStyle.melee, "hp_reg_aura", "Health Regeneration Aura", new AuraSpellData(0.1F,
            Arrays.asList(
                new StatModifier(1, 4, HealthRegen.getInstance())
            )))
            .build();

        SpellBuilder.aura(AttackPlayStyle.magic, "mana_reg_aura", "Mana Regeneration Aura", new AuraSpellData(0.1F,
            Arrays.asList(
                new StatModifier(1, 4, ManaRegen.getInstance())
            )))
            .build();

        SpellBuilder.aura(AttackPlayStyle.magic, "ele_res_aura", "Elemental Resistance Aura", new AuraSpellData(0.3F,
            Arrays.asList(
                new StatModifier(15, 25, new ElementalResist(Elements.Elemental))
            )))
            .build();

        SpellBuilder.aura(AttackPlayStyle.ranged, "crit_aura", "True Hit Aura", new AuraSpellData(0.25F,
            Arrays.asList(
                new StatModifier(15, 25, Stats.ACCURACY.get()),
                new StatModifier(5, 10, Stats.CRIT_CHANCE.get())
            )))
            .build();

    }
}
