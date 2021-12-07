package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISettableLevelTier;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

@Storable
public class StatSoulData implements ISalvagable, IRarity, ISettableLevelTier {

    @Store
    public int tier = 1;

    @Store
    public String slot = "";

    @Store
    public String rar = "";

    @Store
    public String uniq = "";

    @Store
    public boolean can_sal = true;

    @Store
    public GearItemData gear = null;

    public static StatSoulData anySlotOfRarity(String rar) {
        StatSoulData data = new StatSoulData();
        data.tier = -1; // todo idk
        data.rar = rar;
        return data;
    }

    public boolean canBeOnAnySlot() {
        return slot.isEmpty();
    }

    public void setCanBeOnAnySlot() {
        this.slot = "";
    }

    public ItemStack toStack() {

        ItemStack stack = new ItemStack(SlashItems.STAT_SOUL.get());

        StackSaving.STAT_SOULS.saveTo(stack, this);

        if (!slot.isEmpty()) {
            stack.getOrCreateTag()
                .putInt("CustomModelData", ExileDB.GearSlots()
                    .get(slot).model_num);
        }

        return stack;

    }

    public void insertAsUnidentifiedOn(ItemStack stack) {
        if (gear != null) {
            Gear.Save(stack, gear);
        } else {
            LoadSave.Save(this, stack.getOrCreateTag(), StatSoulItem.TAG);
        }
    }

    public GearItemData createGearData(@Nullable ItemStack stack) {

        int lvl = LevelUtils.tierToLevel(tier);

        GearBlueprint b = new GearBlueprint(lvl);
        b.level.set(lvl);
        b.rarity.set(ExileDB.GearRarities()
            .get(rar));

        UniqueGear uniq = ExileDB.UniqueGears()
            .get(this.uniq);

        if (uniq != null) {
            b.uniquePart.set(uniq);
            b.rarity.set(uniq.getUniqueRarity());
        }

        if (this.canBeOnAnySlot()) {
            GearSlot gearslot = ExileDB.GearSlots()
                .random();
            if (stack != null) {
                gearslot = GearSlot.getSlotOf(stack.getItem());
            }
            String slotid = gearslot.GUID();

            b.gearItemSlot.set(ExileDB.GearTypes()
                .getFilterWrapped(x -> x.gear_slot.equals(slotid))
                .random());
        } else {
            b.gearItemSlot.set(ExileDB.GearTypes()
                .getFilterWrapped(x -> x.gear_slot.equals(slot))
                .random());
        }

        GearItemData gear = b.createData();
        gear.can_sal = this.can_sal;
        return gear;
    }

    public boolean canInsertIntoStack(ItemStack stack) {

        if (stack.isEmpty()) {
            return false;
        }

        if (Gear.has(stack)) {
            return false;
        }

        if (this.gear != null) {
            return GearSlot.isItemOfThisSlot(gear.GetBaseGearType()
                .getGearSlot(), stack.getItem());
        }

        if (this.canBeOnAnySlot()) {
            GearSlot slot = GearSlot.getSlotOf(stack.getItem());
            if (slot != null && !slot.GUID()
                .isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        Boolean can = GearSlot.isItemOfThisSlot(ExileDB.GearSlots()
            .get(slot), stack.getItem());

        return can;
    }

    @Override
    public String getRarityRank() {
        return rar;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities()
            .get(rar);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        return GearItemData.getSalvagedResults(new GearItemData.SalvagedItemInfo(tier, getRarity()));
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return true;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }
}
