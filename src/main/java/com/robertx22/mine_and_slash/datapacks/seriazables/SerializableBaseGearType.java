package com.robertx22.mine_and_slash.datapacks.seriazables;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SerializableBaseGearType extends BaseGearType {

    public static SerializableBaseGearType EMPTY = new SerializableBaseGearType();

    public List<StatModifier> implicit_stats;
    public List<StatModifier> base_stats;
    public TagList tags;
    public String item_id;
    public StatRequirement stat_req;
    public WeaponTypes weapon_type;
    public String lang_name_id;
    public int weight;

    public SerializableBaseGearType() {
        super("", null, null, "");
    }

    @Override
    public String locNameLangFileGUID() {
        return lang_name_id;
    }

    @Override
    public WeaponTypes weaponType() {
        return weapon_type;
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
    public TagList getTags() {
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

}
