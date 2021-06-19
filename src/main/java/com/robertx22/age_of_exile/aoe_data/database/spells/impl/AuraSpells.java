package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.spells.components.AuraSpellData;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.Arrays;

public class AuraSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.aura(PlayStyle.melee, "unwavering_stance", "Unwavering Stance", new AuraSpellData(0.2F,
                Arrays.asList(
                    new StatModifier(-10, -25, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee)),
                    new StatModifier(-10, -20, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.ranged)),
                    new StatModifier(-10, -15, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.magic)),
                    new StatModifier(-10, -10, DatapackStats.MOVE_SPEED)
                )))
            .build();

        SpellBuilder.aura(PlayStyle.melee, "taunt_stance", "Taunt Stance", new AuraSpellData(0.2F,
                Arrays.asList(
                    new StatModifier(1, 1, Stats.GIVE_EFFECT_TO_SELF_ON_TICK.get(BeneficialEffects.TAUNT_STANCE))
                )))
            .addEffectToTooltip(BeneficialEffects.TAUNT_STANCE)
            .build();

        SpellBuilder.aura(PlayStyle.melee, "hp_reg_aura", "Health Regeneration Aura", new AuraSpellData(0.1F,
                Arrays.asList(
                    new StatModifier(1, 4, HealthRegen.getInstance())
                )))
            .build();

        SpellBuilder.aura(PlayStyle.ranged, "haste_aura", "Haste Aura", new AuraSpellData(0.25F,
                Arrays.asList(
                    new StatModifier(5, 20, Stats.CAST_SPEED.get()),
                    new StatModifier(4, 15, Stats.ATTACK_SPEED.get())
                )))
            .build();

        SpellBuilder.aura(PlayStyle.melee, "armor_aura", "Armor Aura", new AuraSpellData(0.2F,
                Arrays.asList(
                    new StatModifier(15, 30, Armor.getInstance(), ModType.FLAT)
                )))
            .build();

        SpellBuilder.aura(PlayStyle.ranged, "dodge_aura", "Living Shadow", new AuraSpellData(0.2F,
                Arrays.asList(
                    new StatModifier(15, 30, DodgeRating.getInstance(), ModType.FLAT)
                )))
            .build();

        SpellBuilder.aura(PlayStyle.magic, "mana_reg_aura", "Mana Regeneration Aura", new AuraSpellData(0.1F,
                Arrays.asList(
                    new StatModifier(1, 4, ManaRegen.getInstance())
                )))
            .build();

        SpellBuilder.aura(PlayStyle.magic, "ele_res_aura", "Elemental Resistance Aura", new AuraSpellData(0.3F,
                Arrays.asList(
                    new StatModifier(10, 20, new ElementalResist(Elements.Elemental))
                )))
            .build();

        SpellBuilder.aura(PlayStyle.ranged, "crit_aura", "True Hit Aura", new AuraSpellData(0.25F,
                Arrays.asList(
                    new StatModifier(15, 25, Stats.ACCURACY.get()),
                    new StatModifier(5, 10, Stats.CRIT_CHANCE.get())
                )))
            .build();

    }
}
