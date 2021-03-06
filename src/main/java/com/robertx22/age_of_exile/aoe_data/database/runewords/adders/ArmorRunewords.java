package com.robertx22.age_of_exile.aoe_data.database.runewords.adders;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

import static com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem.RuneType.*;

public class ArmorRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        RuneWord.create(
            "sentinel",
            "Sentinel",
            GearSlots.CHEST,
            Arrays.asList(
                new StatModifier(-5, -5, new MaxElementalResist(Elements.Elemental), ModType.FLAT),
                new StatModifier(5, 15, new ElementalResist(Elements.Elemental), ModType.FLAT)
            ),
            Arrays.asList(CEN, DOS, ANO, TOQ))
            .addToSerializables();

        RuneWord.create(
            "all_knowing",
            "All Knowing",
            GearSlots.HELMET,
            Arrays.asList(new StatModifier(0.05F, 0.1F, AllAttributes.getInstance())),
            Arrays.asList(ENO, HAR, XER))
            .addToSerializables();

        RuneWord.create(
            "starlight",
            "Starlight",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(2, 5, Health.getInstance(), ModType.FLAT),
                new StatModifier(0.5F, 2, ManaRegen.getInstance(), ModType.FLAT)
            ),
            Arrays.asList(XER, CEN, ORU, NOS))
            .addToSerializables();

        RuneWord.create(
            "shadow",
            "Shadow",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(2, 6, DodgeRating.getInstance(), ModType.FLAT),
                new StatModifier(20, 60, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
            ),
            Arrays.asList(XER, CEN, ORU, MOS))
            .addToSerializables();

        RuneWord.create(
            "rock_golem",
            "Rock Golem",
            BaseGearType.SlotFamily.Armor,
            Arrays.asList(
                new StatModifier(2, 6, Armor.getInstance(), ModType.FLAT),
                new StatModifier(20, 60, Armor.getInstance(), ModType.LOCAL_INCREASE)
            ),
            Arrays.asList(XER, CEN, ORU, ITA))
            .addToSerializables();
    }
}
