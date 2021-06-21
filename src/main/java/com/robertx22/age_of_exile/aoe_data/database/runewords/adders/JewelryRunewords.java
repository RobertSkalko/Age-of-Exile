package com.robertx22.age_of_exile.aoe_data.database.runewords.adders;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

import static com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem.RuneType.*;

public class JewelryRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

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
            GearSlots.NECKLACE,
            Arrays.asList(
                new StatModifier(0.5F, 2, HealthRegen.getInstance(), ModType.FLAT),
                new StatModifier(5, 15, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
            ),
            Arrays.asList(XER, WIR, ORU))
            .addToSerializables();

    }
}
