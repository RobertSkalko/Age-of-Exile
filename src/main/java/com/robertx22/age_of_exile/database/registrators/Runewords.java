package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
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

        RuneWord.create(
            "scholar",
            "scholar",
            BaseGearType.SlotFamily.Jewelry,
            Arrays.asList(
                new StatModifier(5, 15, BonusExp.getInstance(), ModType.FLAT)
            ),
            Arrays.asList(ITA, MOS))
            .addToSerializables();

    }
}
