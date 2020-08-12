package com.robertx22.age_of_exile.database.data.gear_types.leather;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseArmor;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.registrators.GearSlots;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BaseLeatherBoots extends BaseArmor {

    public BaseLeatherBoots(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(15, 40, DodgeRating.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public GearSlot getGearSlot() {
        return GearSlots.BOOTS;
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().dexterity(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.leather, SlotTag.boots, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.dexterity);
    }

}
