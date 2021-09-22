package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.player_skills.ingredient.CraftedConsumableData;
import com.robertx22.age_of_exile.player_skills.ingredient.CraftingProcessData;
import com.robertx22.age_of_exile.player_skills.ingredient.IngredientData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.vanilla_mc.items.loot_crate.LootCrateData;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

import java.util.ArrayList;
import java.util.List;

public class StackSaving {

    public static List<ItemstackDataSaver<? extends ICommonDataItem>> ALL = new ArrayList();

    public static ItemstackDataSaver<GearItemData> GEARS = new ItemstackDataSaver<>("GEAR_ITEM_DATA", GearItemData.class, () -> new GearItemData());
    public static ItemstackDataSaver<StatSoulData> STAT_SOULS = new ItemstackDataSaver<>("stat_soul", StatSoulData.class, () -> new StatSoulData());
    public static ItemstackDataSaver<LootCrateData> LOOT_CRATE = new ItemstackDataSaver<>("loot_crate", LootCrateData.class, () -> new LootCrateData());
    public static ItemstackDataSaver<IngredientData> INGREDIENTS = new ItemstackDataSaver<>("ingredient", IngredientData.class, () -> new IngredientData());
    public static ItemstackDataSaver<CraftedConsumableData> CRAFTED_CONSUMABLE = new ItemstackDataSaver<>("craft_cons", CraftedConsumableData.class, () -> new CraftedConsumableData());
    public static ItemstackDataSaver<CraftingProcessData> CRAFT_PROCESS = new ItemstackDataSaver<>("craft_proc", CraftingProcessData.class, () -> new CraftingProcessData());

    static {
        ALL.add(GEARS);
    }
}
