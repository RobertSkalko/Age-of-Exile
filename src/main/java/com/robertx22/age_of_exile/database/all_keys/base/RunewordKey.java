package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RunewordKey extends DataKey<RuneWord> {

    public List<RuneType> runes = new ArrayList<>();
    public Supplier<List<Item>> items = () -> new ArrayList();

    public RunewordKey(String id, List<RuneType> runes) {
        super(id);
        this.runes = runes;

        AllDataKeys.RUNEWORDS.add(this);
    }

    public RunewordKey(String id, Supplier<List<Item>> items) {
        super(id);
        this.items = items;
        AllDataKeys.RUNEWORDS.add(this);
    }

    public ItemStack getStack() {
        ItemStack stack = new ItemStack(SlashItems.RUNEWORD.get());
        stack.getOrCreateTag()
            .putString("runeword", id);
        return stack;
    }

    @Override
    public RuneWord getFromRegistry() {
        return ExileDB.RuneWords()
            .get(id);
    }

    @Override
    public RuneWord getFromDataGen() {
        return null;
    }

    @Override
    public Class getDataClass() {
        return RuneWord.class;
    }
}
