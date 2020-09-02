package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

import static com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem.RuneType.*;

public class Runewords implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        RuneWord.create(
            "all_knowing",
            "All Knowing",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(new StatModifier(0.1F, 0.2F, AllAttributes.getInstance())),
            Arrays.asList(ENO, HAR, XER))
            .addToSerializables();

        RuneWord.create(
            "ele_fury",
            "Elemental Fury",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(0.2F, 0.4F, 0.4F, 1, new WeaponDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(0.2F, 0.4F, 0.4F, 1, new WeaponDamage(Elements.Water), ModType.FLAT),
                new StatModifier(0.2F, 0.4F, 0.4F, 1, new WeaponDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(0.2F, 0.4F, 0.4F, 1, new WeaponDamage(Elements.Nature), ModType.FLAT)
            ),
            Arrays.asList(CEN, DOS, ANO, TOQ))
            .addToSerializables();

        // ele wep variations
        RuneWord.create(
            "venom",
            "Venom",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(0.4F, 0.6F, 0.6F, 1.5F, new WeaponDamage(Elements.Nature), ModType.FLAT),
                new StatModifier(4, 20, ChanceToApplyEffect.POISON, ModType.FLAT)
            ),
            Arrays.asList(DOS, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "dream_of_ice",
            "Dream of Ice",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(0.4F, 0.6F, 0.6F, 1.5F, new WeaponDamage(Elements.Water), ModType.FLAT),
                new StatModifier(4, 20, ChanceToApplyEffect.CHILL, ModType.FLAT)
            ),
            Arrays.asList(TOQ, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "dawn_light",
            "Dawn Light",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(0.4F, 0.6F, 0.6F, 1.5F, new WeaponDamage(Elements.Thunder), ModType.FLAT),
                new StatModifier(4, 20, ChanceToApplyEffect.STATIC, ModType.FLAT)
            ),
            Arrays.asList(CEN, ITA, NOS))
            .addToSerializables();

        RuneWord.create(
            "blaze_fury",
            "Blaze Fury",
            BaseGearType.SlotFamily.Weapon,
            Arrays.asList(
                new StatModifier(0.4F, 0.6F, 0.6F, 1.5F, new WeaponDamage(Elements.Fire), ModType.FLAT),
                new StatModifier(4, 20, ChanceToApplyEffect.BURN, ModType.FLAT)
            ),
            Arrays.asList(ANO, ITA, NOS))
            .addToSerializables();

        // ele wep variations

        RuneWord.create(
            "scholar",
            "Scholar",
            BaseGearType.SlotFamily.Jewelry,
            Arrays.asList(
                new StatModifier(5, 15, BonusExp.getInstance(), ModType.FLAT)
            ),
            Arrays.asList(ITA, MOS))
            .addToSerializables();

        RuneWord.create(
            "infinity",
            "Infinity",
            BaseGearType.SlotFamily.Jewelry,
            Arrays.asList(
                new StatModifier(0.5F, 2, MagicShieldRegen.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
            ),
            Arrays.asList(XER, WIR, ORU))
            .addToSerializables();

        RuneWord.create(
            "starlight",
            "Starlight",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(2, 6, MagicShield.getInstance(), ModType.FLAT),
                new StatModifier(0.5F, 2, MagicShieldRegen.getInstance(), ModType.FLAT)
            ),
            Arrays.asList(XER, CEN, ORU, NOS))
            .addToSerializables();

    }
}
