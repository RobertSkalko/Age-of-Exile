package com.robertx22.age_of_exile.aoe_data.database.mob_affixes;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ISlashRegistryInit {

    static void eleAffix(String name, Elements element) {
        new MobAffix(element.guidName + "_mob_affix", name, element.format)
            .setMods(
                new StatModifier(75, 75, new PhysConvertToEle(element)),
                new StatModifier(1, 1, 1, 1, new AttackDamage(element), ModType.FLAT),
                new StatModifier(10, 10, ExtraMobDropsStat.getInstance()))
            .setWeight(2000)
            .addToSerializables();
    }

    @Override
    public void registerAll() {

        eleAffix("Freezing", Water);
        eleAffix("Flaming", Fire);
        eleAffix("Poisoned", Nature);
        eleAffix("Holy", Light);
        eleAffix("Cursed", Dark);

        new MobAffix("winter", "Lord of Winter", Water.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN)),
                new StatModifier(75, 75, new PhysConvertToEle(Water)),
                new StatModifier(1, 1, 1, 1, new AttackDamage(Water), ModType.FLAT),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Water.format + Water.icon)
            .setWeight(250)
            .addToSerializables();

        new MobAffix("fire_lord", "Lord of Fire", Fire.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN)),
                new StatModifier(75, 75, new PhysConvertToEle(Fire)),
                new StatModifier(1, 1, 1, 1, new AttackDamage(Fire), ModType.FLAT),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Fire.format + Fire.icon)
            .setWeight(250)
            .addToSerializables();

        new MobAffix("nature_lord", "Lord of Toxins", Nature.format)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(5, 5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON)),
                new StatModifier(75, 75, new PhysConvertToEle(Nature)),
                new StatModifier(1, 1, 1, 1, new AttackDamage(Nature), ModType.FLAT),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .icon(Nature.format + Nature.icon)
            .setWeight(250)
            .addToSerializables();

        new MobAffix("phys_lord", "Lord of Ruin", Formatting.GRAY)
            .setMods(
                new StatModifier(15, 15, Health.getInstance()),
                new StatModifier(2, 2, new AttackDamage(Physical)),
                new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
            .setWeight(250)
            .addToSerializables();

        new MobAffix("vampire", "Vampire Lord", Formatting.RED)
            .setMods(new StatModifier(25, 25, Health.getInstance()),
                new StatModifier(15, 15, Stats.LIFESTEAL.get()),
                new StatModifier(15, 15, ExtraMobDropsStat.getInstance()))
            .setWeight(500)
            .addToSerializables();

    }
}
