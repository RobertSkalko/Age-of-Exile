package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkBuilder;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
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

        PerkBuilder.stat("int", new OptScaleExactStat(1, Intelligence.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, Dexterity.INSTANCE, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, Strength.INSTANCE, ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, CriticalDamage.getInstance(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, CriticalHit.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(5, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE));

        PerkBuilder.stat(new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, Armor.getInstance(), ModType.LOCAL_INCREASE));
        PerkBuilder.stat(new OptScaleExactStat(3, MagicShield.getInstance(), ModType.LOCAL_INCREASE));

        new ElementalSpellDamage(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
            });

        new ElementalResist(Elements.Nature).generateAllPossibleStatVariations()
            .forEach(x -> {
                PerkBuilder.stat(x.GUID(), new OptScaleExactStat(4, x, ModType.FLAT));
            });

        SlashRegistry.Spells()
            .getList()
            .forEach(x -> {
                PerkBuilder.spell(x);
            });
    }
}
