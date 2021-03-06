package com.robertx22.age_of_exile.database.data.salvage_outputs;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SalvageOutput implements ISerializedRegistryEntry<SalvageOutput>, IAutoGson<SalvageOutput> {
    public static SalvageOutput SERIALIZER = new SalvageOutput();

    LevelRange levelRange;
    List<WeightedItem> outputs = new ArrayList<>();
    String id = "";
    int weight = 1000;

    public boolean isForItem(GearItemData gear) {
        return gear.lvl >= levelRange.getMinLevel();
    }

    public List<ItemStack> getResult(GearItemData gear) {

        ItemStack stack = new ItemStack(RandomUtils.weightedRandom(outputs)
            .getItem());
        stack.setCount(Math.max(1, (int) gear.getRarity()
            .valueMulti()));

        List<ItemStack> list = new ArrayList<>();
        list.add(stack);

        return list;
    }

    public SalvageOutput(List<WeightedItem> outputs, String id, LevelRange range) {
        this.outputs = outputs;
        this.id = id;
        this.levelRange = range;
    }

    public SalvageOutput() {
    }

    @Override
    public Class<SalvageOutput> getClassForSerialization() {
        return SalvageOutput.class;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SALVAGE_OUTPUT;
    }

    @Override
    public String GUID() {
        return id;
    }
}
