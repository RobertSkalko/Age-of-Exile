package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DamageOverTime;
import com.robertx22.age_of_exile.database.data.stats.types.offense.ProjectileDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.SpellCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ResourceOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.aura.ReducedManaReserved;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.speed.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.speed.CastSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ManaCost;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class Perks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.stat("int", new OptScaleExactStat(1, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, Strength.INSTANCE, ModType.FLAT));

        PerkBuilder.stat("cast_speed", new OptScaleExactStat(3, CastSpeed.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(-2, ManaCost.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, AttackSpeed.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, ProjectileDamage.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, CriticalDamage.getInstance(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, CriticalHit.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, ReducedManaReserved.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Lifesteal.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, SpellCriticalDamage.getInstance(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, SpellCriticalHit.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, SpellDamage.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, HealPower.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.BURN, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.FROSTBURN, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, ChanceToApplyEffect.POISON, ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Armor.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Mana.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(1, AttackStyleDamage.MAGIC, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, AttackStyleDamage.MELEE, ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, AttackStyleDamage.RANGED, ModType.FLAT));

        PerkBuilder.stat("mana_on_hit", new OptScaleExactStat(3, new ResourceOnHit(new ResourceOnHit.Info(ResourceType.MANA, AttackType.ATTACK)), ModType.FLAT));

        new ElementalSpellDamage(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                PerkBuilder.stat(x.GUID() + "_and_dot", new OptScaleExactStat(1, x, ModType.FLAT), new OptScaleExactStat(3, new DamageOverTime(x.getElement()), ModType.FLAT));
            });

        new SpecificWeaponDamage(WeaponTypes.Sword).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
            });

        new SpecificElementalWeaponDamage(WeaponTypes.Sword).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));

            });

        new ElementalDamageBonus(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
            });

        new ElementalResist(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(4, x, ModType.FLAT));

            });

        new ElementalPenetration(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(5, x, ModType.FLAT));
            });

        new AttackDamage(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.LOCAL_INCREASE));

            });

    }
}
