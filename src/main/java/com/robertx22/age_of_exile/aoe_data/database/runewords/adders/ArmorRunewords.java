package com.robertx22.age_of_exile.aoe_data.database.runewords.adders;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

import static com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem.RuneType.*;

public class ArmorRunewords implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        RuneWord.create(
            "sentinel",
            "Sentinel",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(-5, -5, new MaxElementalResist(Elements.Fire), ModType.FLAT),
                new StatModifier(-5, -5, new MaxElementalResist(Elements.Water), ModType.FLAT),
                new StatModifier(-5, -5, new MaxElementalResist(Elements.Nature), ModType.FLAT),
                new StatModifier(-5, -5, new MaxElementalResist(Elements.Thunder), ModType.FLAT),
                new StatModifier(7, 18, new ElementalResist(Elements.Elemental), ModType.FLAT)
            ),
            Arrays.asList(CEN, DOS, ANO, TOQ))
            .addToSerializables();

        RuneWord.create(
            "all_knowing",
            "All Knowing",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(new StatModifier(0.1F, 0.2F, AllAttributes.getInstance())),
            Arrays.asList(ENO, HAR, XER))
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

        RuneWord.create(
            "shadow",
            "Shadow",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(10, 20, DodgeRating.getInstance(), ModType.FLAT),
                new StatModifier(20, 60, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
            ),
            Arrays.asList(XER, CEN, ORU, MOS))
            .addToSerializables();

        RuneWord.create(
            "rock_golem",
            "Rock Golem",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(10, 20, Armor.getInstance(), ModType.FLAT),
                new StatModifier(20, 60, Armor.getInstance(), ModType.LOCAL_INCREASE)
            ),
            Arrays.asList(XER, CEN, ORU, ITA))
            .addToSerializables();
    }
}
