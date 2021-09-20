package com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.IFormattableTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RuneWordStationTile extends BaseModificationStation {

    public static List<Integer> RUNE_SLOTS = Arrays.asList(0, 1, 2, 3, 4, 5);
    public static List<Integer> ITEM_SLOT = Arrays.asList(6);
    public static List<Integer> STORAGE_SLOTS = Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
    public static Integer SLOT_COUNT = RUNE_SLOTS.size() + ITEM_SLOT.size() + STORAGE_SLOTS.size();

    public ItemStack getGearStack() {
        return itemStacks[ITEM_SLOT.get(0)];
    }

    public RuneWordStationTile() {
        super(SlashBlockEntities.RUNEWORD.get(), SLOT_COUNT);
    }

    @Override
    public void tick() {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {

        ItemStack stack = getGearStack();

        List<ItemStack> runes = new ArrayList<>();

        for (int slot : RUNE_SLOTS) {
            runes.add(this.itemStacks[slot]);
        }
        runes.removeIf(x -> x.isEmpty() || !(x.getItem() instanceof RuneItem));

        List<String> runeStrings = runes.stream()
            .map(x -> ((RuneItem) x.getItem()).type.id)
            .collect(Collectors.toList());

        RuneWord word = null;

        List<RuneWord> list = ExileDB.RuneWords()
            .getFiltered(x -> x.runesCanActivateRuneWord(runeStrings, true) && x.canApplyOnItem(stack));

        if (!list.isEmpty()) {
            word = list.get(0);
        }

        if (word != null) {

            runes.forEach(x -> x.shrink(1));

            GearBlueprint b = new GearBlueprint(Items.AIR, Load.Unit(player)
                .getLevel());
            UniqueGear uniq = ExileDB.UniqueGears()
                .get(word.uniq_id);
            b.uniquePart.set(uniq);
            b.rarity.set(uniq.getUniqueRarity());

            GearItemData gear = b.createData();

            gear.saveToStack(stack);

            System.out.println("sdsdsd");
        }

        return false;
    }

    @Override
    public Container createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new RuneWordStationContainer(num, inventory, this, this.getBlockPos());
    }

    @Override
    public IFormattableTextComponent getDisplayName() {
        return CLOC.blank("block.mmorpg.socket_station");
    }
}
