package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

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
            Arrays.asList(new StatModifier(0.1F, 0.2F, AllAttributes.getInstance())),
            Arrays.asList(ENO, HAR, XER))
            .addToSerializables();

    }
}
