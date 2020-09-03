package com.robertx22.age_of_exile.datapacks.seriazables;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
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
    public String loc_name_id;
    public int weight;
    public String gear_slot;

    public SerializableBaseGearType() {
        super("", null, "");
    }

    @Override
    public String locNameLangFileGUID() {
        return loc_name_id;
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

    @Override
    public GearSlot getGearSlot() {
        return SlashRegistry.GearSlots()
            .get(gear_slot);
    }

}
