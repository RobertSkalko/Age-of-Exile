package com.robertx22.exiled_lib.registry.empty_entries;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import net.minecraft.item.Item;

import java.util.List;

public class EmptyBaseGearType extends BaseGearType {

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
    public List<SlotTag> getTags() {
        return null;
    }

    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public String locNameForLangFile() {
        return null;
    }

    @Override
    public String GUID() {
        return null;
    }
}
