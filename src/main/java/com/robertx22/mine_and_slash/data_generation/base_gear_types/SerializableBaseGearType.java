package com.robertx22.mine_and_slash.data_generation.base_gear_types;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SerializableBaseGearType extends BaseGearType {

    public static SerializableBaseGearType EMPTY = new SerializableBaseGearType();

    public List<StatModifier> implicit_stats;
    public List<StatModifier> base_stats;
    public List<SlotTag> tags;
    public String item_id;
    public StatRequirement stat_req;
    public String identifier;
    public String lang_name_id;
    public int weight;

    @Override
    public String locNameLangFileGUID() {
        return lang_name_id;
    }

    @Override
    public List<StatModifier> implicitStats() {
        return implicit_stats;
    }

    @Override
    public List<StatModifier> baseStats() {
        return base_stats;
    }

    @Override
    public List<SlotTag> getTags() {
        return tags;
    }

    @Override
    public Item getItem() {
        return Registry.ITEM.get(new Identifier(item_id));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return stat_req;
    }

    @Override
    public String locNameForLangFile() {
        return null;
    }

    @Override
    public String GUID() {
        return identifier;
    }

}
