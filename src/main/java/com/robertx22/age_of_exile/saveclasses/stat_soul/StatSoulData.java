package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.Item;
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

    public ItemStack toStack() {

        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.STAT_SOUL);

        StackSaving.STAT_SOULS.saveTo(stack, this);

        stack.getOrCreateTag()
            .putInt("CustomModelData", ExileDB.GearSlots()
                .get(slot).custom_model_data_num);

        return stack;

    }

    public GearItemData createGearData() {

        GearBlueprint b = new GearBlueprint(Items.AIR, tier * 10);
        b.rarity.set(ExileDB.GearRarities()
            .get(rar));
        b.gearItemSlot.set(ExileDB.GearTypes()
            .getFilterWrapped(x -> x.gear_slot.equals(slot))
            .random());

        return b.createData();
    }

    public boolean itemMatches(Item item) {
        return BaseGearType.isGearOfThisType(ExileDB.GearSlots()
            .get(slot), item);
    }

}
