package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class Perks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        Perk start = new Perk();
        start.is_entry = true;
        start.identifier = "start";
        start.type = Perk.PerkType.START;
        start.addToSerializables();

        Perk start2 = new Perk();
        start2.is_entry = true;
        start2.identifier = "talent_start";
        start2.type = Perk.PerkType.START;
        start2.one_of_a_kind = "start";
        start2.addToSerializables();

        PerkBuilder.stat("int", new OptScaleExactStat(1, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, Strength.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("wis", new OptScaleExactStat(1, Wisdom.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("vit", new OptScaleExactStat(1, Vitality.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("wil", new OptScaleExactStat(1, Willpower.INSTANCE, ModType.FLAT));

        PerkBuilder.bigStat("big_int", new OptScaleExactStat(3, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_dex", new OptScaleExactStat(3, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_str", new OptScaleExactStat(3, Strength.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_wis", new OptScaleExactStat(3, Wisdom.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_vit", new OptScaleExactStat(3, Vitality.INSTANCE, ModType.FLAT));
        PerkBuilder.bigStat("big_wil", new OptScaleExactStat(3, Willpower.INSTANCE, ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, CriticalDamage.getInstance(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, CriticalHit.getInstance(), ModType.FLAT));

        PerkBuilder.bigStat("big_crit_dmg", new OptScaleExactStat(5, CriticalDamage.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat("big_crit_hit", new OptScaleExactStat(2, CriticalHit.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, SpellDamage.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat("spell_damage_big", new OptScaleExactStat(6, SpellDamage.getInstance(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(4, HealPower.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(5, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.BURN, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.CHILL, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.STATIC, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.POISON, ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Armor.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, MagicShield.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Mana.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(1, AttackStyleDamage.MAGIC, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, AttackStyleDamage.MELEE, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, AttackStyleDamage.RANGED, ModType.FLAT));

        PerkBuilder.bigStat("big_dodge", new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.bigStat("big_armor", new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.bigStat("big_magic_shield", new OptScaleExactStat(10, MagicShield.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.bigStat("big_health", new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.bigStat("big_mana", new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.bigStat("big_magic_shield_regen", new OptScaleExactStat(10, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.bigStat("big_health_regen", new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.bigStat("big_mana_regen", new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));

        new ElementalSpellDamage(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                PerkBuilder.bigStat(x.GUID() + "_big", new OptScaleExactStat(5, x, ModType.FLAT));
            });

        new SpecificWeaponDamage(WeaponTypes.Sword).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
                PerkBuilder.bigStat(x.GUID() + "_big", new OptScaleExactStat(4, x, ModType.FLAT));
            });

        new SpecificElementalWeaponDamage(WeaponTypes.Sword).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                PerkBuilder.bigStat(x.GUID() + "_big", new OptScaleExactStat(5, x, ModType.FLAT));
            });

        new ElementalDamageBonus(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
                PerkBuilder.stat(x.GUID() + "_big", new OptScaleExactStat(5, x, ModType.FLAT));
            });

        new ElementalResist(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(4, x, ModType.FLAT));
                PerkBuilder.bigStat(x.GUID() + "_big", new OptScaleExactStat(8, x, ModType.FLAT));
            });

        new ElementalPenetration(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(5, x, ModType.FLAT));
            });

        new AttackDamage(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.LOCAL_INCREASE));
                PerkBuilder.bigStat(x.GUID() + "_big", new OptScaleExactStat(5, x, ModType.LOCAL_INCREASE));
            });

        SlashRegistry.Spells()
            .getSerializable()
            .forEach(spell -> {
                PerkBuilder.spell(spell);
            });

    }
}
