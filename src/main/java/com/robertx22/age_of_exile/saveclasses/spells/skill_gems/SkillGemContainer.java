package com.robertx22.age_of_exile.saveclasses.spells.skill_gems;

import info.loenwind.autosave.annotations.Storable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

@Storable
public class SkillGemContainer implements Inventory {

    public List<ItemStack> stacks = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);

    static int SIZE = 20;

    public enum SlotType {
        SKILL_GEM, SUPPORT_GEM
    }

    public enum Places {

        B1(0, 0, EquipmentSlot.HEAD, SlotType.SUPPORT_GEM),
        B2(1, 0, EquipmentSlot.HEAD, SlotType.SKILL_GEM),
        B3(2, 0, EquipmentSlot.HEAD, SlotType.SUPPORT_GEM),

        B4(3, 1, EquipmentSlot.CHEST, SlotType.SUPPORT_GEM),
        B5(4, 1, EquipmentSlot.CHEST, SlotType.SUPPORT_GEM),
        B6(5, 1, EquipmentSlot.CHEST, SlotType.SKILL_GEM),
        B7(6, 1, EquipmentSlot.CHEST, SlotType.SUPPORT_GEM),
        B8(7, 1, EquipmentSlot.CHEST, SlotType.SUPPORT_GEM),

        B9(8, 2, EquipmentSlot.LEGS, SlotType.SUPPORT_GEM),
        B10(9, 2, EquipmentSlot.LEGS, SlotType.SUPPORT_GEM),
        B11(10, 2, EquipmentSlot.LEGS, SlotType.SKILL_GEM),
        B12(11, 2, EquipmentSlot.LEGS, SlotType.SUPPORT_GEM),
        B13(12, 2, EquipmentSlot.LEGS, SlotType.SUPPORT_GEM),

        B14(13, 3, EquipmentSlot.FEET, SlotType.SUPPORT_GEM),
        B15(14, 3, EquipmentSlot.FEET, SlotType.SKILL_GEM),
        B16(15, 3, EquipmentSlot.FEET, SlotType.SUPPORT_GEM),

        B17(16, 4, null, SlotType.SKILL_GEM),
        B18(17, 4, null, SlotType.SKILL_GEM),
        B19(18, 4, null, SlotType.SKILL_GEM),
        B20(19, 4, null, SlotType.SKILL_GEM);

        public int index;
        public int place;
        public EquipmentSlot equipSlot;
        public SlotType slotType;

        Places(int index, int place, EquipmentSlot equipSlot, SlotType slotType) {
            this.index = index;
            this.place = place;
            this.equipSlot = equipSlot;
            this.slotType = slotType;
        }
    }

    public List<ItemStack> getSupportGemsOf(int place) {
        List<ItemStack> list = new ArrayList<>();

        for (Places en : Places.values()) {
            if (en.slotType == SlotType.SUPPORT_GEM) {
                if (en.place == place) {
                    list.add(stacks.get(en.index));
                }
            }
        }
        return list;
    }

    public ItemStack getSkillGemOf(int place) {
        for (Places en : Places.values()) {
            if (en.slotType == SlotType.SUPPORT_GEM) {
                return stacks.get(en.index);
            }
        }
        System.out.print("No skill gem found for place " + place);
        return ItemStack.EMPTY;
    }

    @Override

    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return stacks.get(0);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return stacks.set(slot, ItemStack.EMPTY);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        stacks.set(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }
}
