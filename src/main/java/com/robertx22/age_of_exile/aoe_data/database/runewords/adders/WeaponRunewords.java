package com.robertx22.age_of_exile.aoe_data.database.runewords.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

import static com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem.RuneType.*;

public class WeaponRunewords implements ISlashRegistryInit {

    @Override
    public void registerAll() {
        RuneWord.create(
            "ele_fury",
            "Elemental Fury",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(0.1F, 0.1F, 0.4F, 1, new AttackDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(0.1F, 0.1F, 0.4F, 1, new AttackDamage(Elements.Water), ModType.FLAT),
                new StatModifier(0.1F, 0.1F, 0.4F, 1, new AttackDamage(Elements.Nature), ModType.FLAT)
            ),
            Arrays.asList(DOS, ANO, TOQ))
            .addToSerializables();

        // ele wep variations
        RuneWord.create(
            "venom",
            "Venom",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Nature), ModType.FLAT),
                new StatModifier(4, 15, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON), ModType.FLAT)
            ),
            Arrays.asList(DOS, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "dream_of_ice",
            "Dream of Ice",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Water), ModType.FLAT),
                new StatModifier(4, 15, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN), ModType.FLAT)
            ),
            Arrays.asList(TOQ, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "dawn_light",
            "Dawn Light",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 5, new AttackDamage(Elements.Light), ModType.FLAT)
            ),
            Arrays.asList(CEN, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "blaze_fury",
            "Blaze Fury",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(4, 15, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN), ModType.FLAT)
            ),
            Arrays.asList(ANO, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "momentum",
            "Momentum",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 1.5F, 1.5F, 2.5F, new AttackDamage(Elements.Physical), ModType.FLAT),
                new StatModifier(5, 15, ArmorPenetration.getInstance(), ModType.FLAT),
                new StatModifier(2, 13, Stats.CRIT_CHANCE.get(), ModType.FLAT),
                new StatModifier(5, 25, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
            ),
            Arrays.asList(YUN, MOS, NOS, XER))
            .addToSerializables();

    }
}
