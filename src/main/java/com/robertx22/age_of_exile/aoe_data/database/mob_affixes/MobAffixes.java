package com.robertx22.age_of_exile.aoe_data.database.mob_affixes;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import net.minecraft.util.Formatting;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        new MobAffix("cold", "Cold", Water.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Water)), new StatModifier(1, 1, new AttackDamage(Water)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
            .setWeight(2000)
            .addToSerializables();
        new MobAffix("flaming", "Flaming", Fire.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Fire)), new StatModifier(1, 1, new AttackDamage(Fire)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
            .setWeight(2000)
            .addToSerializables();
        new MobAffix("lightning", "Thunder", Thunder.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Thunder)), new StatModifier(1, 1, new AttackDamage(Thunder)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
            .setWeight(2000)
            .addToSerializables();
        new MobAffix("venom", "Poison", Nature.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Nature)), new StatModifier(1, 1, new AttackDamage(Nature)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
            .setWeight(2000)
            .addToSerializables();

        new MobAffix("winter", "Lord of Winter", Water.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, ChanceToApplyEffect.FROSTBURN),
                new StatModifier(75, 75, new PhysConvertToEle(Water)),
                new StatModifier(1, 1, new AttackDamage(Water)),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Water.format + Water.icon)
            .setWeight(500)
            .addToSerializables();

        new MobAffix("fire_lord", "Lord of Fire", Fire.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, ChanceToApplyEffect.BURN),
                new StatModifier(75, 75, new PhysConvertToEle(Fire)),
                new StatModifier(1, 1, new AttackDamage(Fire)),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Fire.format + Fire.icon)
            .setWeight(500)
            .addToSerializables();

        new MobAffix("thunder_lord", "Lord of Thunder", Thunder.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, ChanceToApplyEffect.SHOCK),
                new StatModifier(75, 75, new PhysConvertToEle(Thunder)),
                new StatModifier(1, 1, new AttackDamage(Thunder)),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Thunder.format + Thunder.icon)
            .setWeight(500)
            .addToSerializables();

        new MobAffix("nature_lord", "Lord of Toxins", Nature.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, ChanceToApplyEffect.POISON),
                new StatModifier(75, 75, new PhysConvertToEle(Nature)),
                new StatModifier(1, 1, new AttackDamage(Nature)),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Nature.format + Nature.icon)
            .setWeight(500)
            .addToSerializables();

        new MobAffix("phys_lord", "Lord of Ruin", Formatting.GRAY)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(2, 2, new AttackDamage(Physical)),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .setWeight(500)
            .addToSerializables();

        new MobAffix("vampire", "Vampire Lord", Formatting.RED)
            .setMods(new StatModifier(25, 25, Health.getInstance()),
                new StatModifier(15, 15, Lifesteal.getInstance()),
                new StatModifier(15, 15, ExtraMobDropsStat.getInstance()))
            .setWeight(500)
            .addToSerializables();

    }
}
