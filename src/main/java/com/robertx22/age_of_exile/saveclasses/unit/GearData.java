package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RepairUtils;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class GearData {

    public ItemStack stack;
    public GearItemData gear;
    public EquipmentSlotType slot;
    public int percentStatUtilization = 100; // todo if stats change stat utilization, they need special handling..

    public GearData(ItemStack stack, EquipmentSlotType slot, EntityData data) {
        this.stack = stack;
        if (stack != null) {
            this.gear = Gear.Load(stack);
        }
        this.slot = slot;

        calcStatUtilization(data);

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GearData == false) {
            return false;
        }
        GearData other = (GearData) obj;

        return (ItemStack.matches(stack, other.stack));
    }

    private void calcStatUtilization(EntityData data) {
        if (slot == EquipmentSlotType.OFFHAND) {
            if (gear != null && gear.GetBaseGearType()
                .isWeapon()) {
                GearItemData mainhand = Gear.Load(data.getEntity()
                    .getMainHandItem());

                if (mainhand != null) {
                    if (mainhand.GetBaseGearType().weapon_type == gear.GetBaseGearType().weapon_type) {
                        percentStatUtilization = gear.GetBaseGearType().weapon_offhand_stat_util;
                        return;
                    }
                }

                percentStatUtilization = 0;
                return;
            }
        }
    }

    public boolean isUsableBy(EntityData data) {
        if (stack == null) {
            return false;
        }
        if (gear == null) {
            return false;
        }

        if (stack.isDamageableItem()) {
            if (RepairUtils.isItemBroken(stack)) {
                return false;
            }
        }
        if (!gear.isValidItem()) {
            return false;
        }

        if (data.getLevel() < gear.lvl) {
            return false;
        }

        BaseGearType type = gear.GetBaseGearType();

        if (type.isWeapon()) {

            if (type.isMeleeWeapon()) {
                if (slot == EquipmentSlotType.OFFHAND) {
                    return true;
                }
            }

            return slot == EquipmentSlotType.MAINHAND; // ranged weapon
        }
        if (type.tags.contains(BaseGearType.SlotTag.chest)) {
            return slot == EquipmentSlotType.CHEST;
        }
        if (type.tags.contains(BaseGearType.SlotTag.pants)) {
            return slot == EquipmentSlotType.LEGS;
        }
        if (type.tags.contains(BaseGearType.SlotTag.boots)) {
            return slot == EquipmentSlotType.FEET;
        }
        if (type.tags.contains(BaseGearType.SlotTag.helmet)) {
            return slot == EquipmentSlotType.HEAD;
        }
        if (type.tags.contains(BaseGearType.SlotTag.jewelry_family)) {
            return slot == null;
        }
        if (type.tags.contains(BaseGearType.SlotTag.offhand_family)) {
            return slot == EquipmentSlotType.OFFHAND;
        }

        return false;
    }
}
