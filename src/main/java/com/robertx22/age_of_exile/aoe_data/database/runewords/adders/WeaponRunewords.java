package com.robertx22.age_of_exile.aoe_data.database.runewords.adders;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
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
                new StatModifier(0.1F, 0.4F, 0.4F, 1, new AttackDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(0.1F, 0.4F, 0.4F, 1, new AttackDamage(Elements.Water), ModType.FLAT),
                new StatModifier(0.1F, 0.4F, 0.4F, 1, new AttackDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(0.1F, 0.4F, 0.4F, 1, new AttackDamage(Elements.Nature), ModType.FLAT)
            ),
            Arrays.asList(CEN, DOS, ANO, TOQ))
            .addToSerializables();

        // ele wep variations
        RuneWord.create(
            "venom",
            "Venom",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Nature), ModType.FLAT),
                new StatModifier(4, 15, ChanceToApplyEffect.POISON, ModType.FLAT)
            ),
            Arrays.asList(DOS, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "dream_of_ice",
            "Dream of Ice",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Water), ModType.FLAT),
                new StatModifier(4, 15, ChanceToApplyEffect.CHILL, ModType.FLAT)
            ),
            Arrays.asList(TOQ, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "dawn_light",
            "Dawn Light",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(4, 15, ChanceToApplyEffect.STATIC, ModType.FLAT)
            ),
            Arrays.asList(CEN, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "blaze_fury",
            "Blaze Fury",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(4, 15, ChanceToApplyEffect.BURN, ModType.FLAT)
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
                new StatModifier(2, 13, CriticalHit.getInstance(), ModType.FLAT),
                new StatModifier(5, 25, CriticalDamage.getInstance(), ModType.FLAT)
            ),
            Arrays.asList(YUN, MOS, NOS, XER))
            .addToSerializables();

    }
}
