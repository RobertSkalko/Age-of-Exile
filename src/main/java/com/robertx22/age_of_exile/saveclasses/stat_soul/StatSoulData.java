package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Storable
public class StatSoulData {

    @Store
    public int tier = 1;

    @Store
    public String slot = "";

    @Store
    public String rar = "";

    @Store
    public String uniq = "";

    public ItemStack toStack() {

        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.STAT_SOUL);

        StackSaving.STAT_SOULS.saveTo(stack, this);

        stack.getOrCreateTag()
            .putInt("CustomModelData", ExileDB.GearSlots()
                .get(slot).custom_model_data_num);

        return stack;

    }

    public void insertAsUnidentifiedOn(ItemStack stack) {
        LoadSave.Save(this, stack.getOrCreateTag(), StatSoulItem.TAG);
    }

    public GearItemData createGearData() {

        int lvl = LevelUtils.tierToLevel(tier);

        GearBlueprint b = new GearBlueprint(Items.AIR, lvl);
        b.level.set(lvl);
        b.rarity.set(ExileDB.GearRarities()
            .get(rar));

        UniqueGear uniq = ExileDB.UniqueGears()
            .get(this.uniq);

        if (uniq != null) {
            b.uniquePart.set(uniq);
            b.rarity.set(uniq.getUniqueRarity());
        }

        b.gearItemSlot.set(ExileDB.GearTypes()
            .getFilterWrapped(x -> x.gear_slot.equals(slot))
            .random());

        return b.createData();
    }

    public boolean canInsertIntoStack(ItemStack stack) {

        if (Gear.has(stack)) {
            return false;
        }

        return GearSlot.isItemOfThisSlot(ExileDB.GearSlots()
            .get(slot), stack.getItem());
    }

}
