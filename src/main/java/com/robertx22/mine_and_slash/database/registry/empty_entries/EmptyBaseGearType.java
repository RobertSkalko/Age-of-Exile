package com.robertx22.mine_and_slash.database.registry.empty_entries;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.registrators.LevelRanges;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;

import java.util.List;

public class EmptyBaseGearType extends BaseGearType {

    public EmptyBaseGearType() {
        super("", LevelRanges.ENDGAME, "");
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return null;
    }

    @Override
    public List<StatModifier> baseStats() {
        return null;
    }

    @Override
    public TagList getTags() {
        return null;
    }

}
