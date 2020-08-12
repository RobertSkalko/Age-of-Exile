package com.robertx22.age_of_exile.database.data.gear_types.curios;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseCurio;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class OccultRing extends BaseCurio {

    public OccultRing(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement();
    }

    @Override
    public GearSlot getGearSlot() {
        return GearSlots.RING;
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(new StatModifier(1, 2, ManaRegen.getInstance(), ModType.FLAT));
    }

    @Override
    public int Weight() {
        return super.Weight() * 2;
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.ring, SlotTag.jewelry_family);
    }

}
