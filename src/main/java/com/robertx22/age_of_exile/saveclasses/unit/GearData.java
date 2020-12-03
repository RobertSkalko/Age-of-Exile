package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RepairUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class GearData {

    public ItemStack stack;
    public GearItemData gear;
    public EquipmentSlot slot;
    public int percentStatUtilization = 100; // todo if stats change stat utilization, they need special handling..

    public GearData(ItemStack stack, EquipmentSlot slot, EntityCap.UnitData data) {
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

        return (ItemStack.areEqual(stack, other.stack));
    }

    private void calcStatUtilization(EntityCap.UnitData data) {
        if (slot == EquipmentSlot.OFFHAND) {
            if (gear != null && gear.GetBaseGearType()
                .isWeapon()) {
                percentStatUtilization = 15; // TODO
            }
        }
    }

    public boolean isUsableBy(EntityCap.UnitData data) {
        if (stack == null) {
            return false;
        }
        if (gear == null) {
            return false;
        }
        if (stack.isDamageable()) {
            if (RepairUtils.isItemBroken(stack)) {
                return false;
            }
        }
        if (!gear.isValidItem()) {
            return false;
        }
        if (!gear.isIdentified()) {
            return false;
        }
        if (data.getLevel() < gear.level) {
            return false;
        }

        BaseGearType type = gear.GetBaseGearType();

        if (type.isWeapon()) {
            return slot == EquipmentSlot.MAINHAND; // ranged weapon
        }
        if (type.tags.contains(BaseGearType.SlotTag.chest)) {
            return slot == EquipmentSlot.CHEST;
        }
        if (type.tags.contains(BaseGearType.SlotTag.pants)) {
            return slot == EquipmentSlot.LEGS;
        }
        if (type.tags.contains(BaseGearType.SlotTag.boots)) {
            return slot == EquipmentSlot.FEET;
        }
        if (type.tags.contains(BaseGearType.SlotTag.helmet)) {
            return slot == EquipmentSlot.HEAD;
        }
        if (type.tags.contains(BaseGearType.SlotTag.jewelry_family)) {
            return slot == null;
        }
        if (type.tags.contains(BaseGearType.SlotTag.offhand_family)) {
            return slot == EquipmentSlot.OFFHAND;
        }

        return false;
    }
}
