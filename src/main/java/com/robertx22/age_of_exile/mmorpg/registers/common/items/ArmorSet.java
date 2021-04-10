package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseArmorItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials.ArmorType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArmorSet extends BaseItemRegistrator {

    public static enum SlotEnum {
        CHEST("chest", EquipmentSlot.CHEST),
        PANTS("pants", EquipmentSlot.LEGS),
        BOOTS("boots", EquipmentSlot.FEET),
        HELMET("helmet", EquipmentSlot.HEAD);

        public String id;
        public EquipmentSlot slot;

        SlotEnum(String id, EquipmentSlot slot) {
            this.id = id;
            this.slot = slot;
        }
    }

    LevelRange range;
    ArmorType type;

    public HashMap<EquipmentSlot, Item> ITEMS = new HashMap<>();
    public HashMap<EquipmentSlot, String> IDS = new HashMap<>();

    static String path = "uniques/armor/";

    static List<SlotEnum> SLOTS = Arrays.asList(SlotEnum.BOOTS, SlotEnum.PANTS, SlotEnum.CHEST, SlotEnum.HELMET);

    public ArmorSet(String id, LevelRange range, ArmorType type) {
        this.range = range;
        this.type = type;

        for (SlotEnum slot : SLOTS) {

            EquipmentSlot eqslot = slot.slot;
            String uniqid = id + "_" + slot.id;

            IDS.put(eqslot, uniqid);
            ITEMS.put(eqslot, item(new BaseArmorItem(ArmorTier.from(range), type, slot.id, eqslot, true), path + uniqid));

        }

    }
}
