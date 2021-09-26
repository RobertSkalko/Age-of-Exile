package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.salvage_outputs.SalvageOutput;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Storable
public class StatSoulData implements ISalvagable {

    @Store
    public int tier = 1;

    @Store
    public String slot = "";

    @Store
    public String rar = "";

    @Store
    public String uniq = "";

    public ItemStack toStack() {

        ItemStack stack = new ItemStack(SlashItems.STAT_SOUL.get());

        StackSaving.STAT_SOULS.saveTo(stack, this);

        stack.getOrCreateTag()
            .putInt("CustomModelData", ExileDB.GearSlots()
                .get(slot).model_num);

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
    public List<ItemStack> getSalvageResult(float salvageBonus) {
        List<ItemStack> list = new ArrayList<>();
        try {
            SalvageOutput sal = RandomUtils.weightedRandom(ExileDB.SalvageOutputs()
                .getFiltered(x -> x.isForItem(LevelUtils.tierToLevel(tier)))
            );
            if (sal != null) {
                return sal.getResult(getRarity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    @Override
    public boolean isSalvagable(SalvageContext context) {
        return true;
    }
}
