package com.robertx22.age_of_exile.database.registry.empty_entries;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;

import java.util.Arrays;
import java.util.List;

public class EmptyBaseGearType extends BaseGearType {

    public EmptyBaseGearType() {
        super("", LevelRanges.ENDGAME, "");
    }

    @Override
    public GearSlot getGearSlot() {
        return GearSlots.BOW;
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList();
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.sword);
    }

}
