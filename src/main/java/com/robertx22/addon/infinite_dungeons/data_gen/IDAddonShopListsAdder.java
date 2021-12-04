package com.robertx22.addon.infinite_dungeons.data_gen;

import com.robertx22.infinite_dungeons.database.db_types.layout.ShopCost;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopEntry;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopReward;
import com.robertx22.infinite_dungeons.database.ids.DungeonDifficultiesIds;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class IDAddonShopListsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

    }

    //todo
    static List<ShopEntry> defaultMineAndSlashShops() {
        List<ShopEntry> list = new ArrayList();
        list.add(new ShopEntry(ShopCost.voidCoins(10), ShopReward.item(Items.ENDER_PEARL, 3), DungeonDifficultiesIds.DIFF_0, 1));
        list.add(new ShopEntry(ShopCost.voidCoins(25), ShopReward.item(Items.ENDER_EYE, 1), DungeonDifficultiesIds.DIFF_5, 1));
        list.add(new ShopEntry(ShopCost.voidCoins(15), ShopReward.item(Items.LEATHER, 9), DungeonDifficultiesIds.DIFF_2, 3));
        list.add(new ShopEntry(ShopCost.voidCoins(20), ShopReward.item(Items.DIAMOND, 3), DungeonDifficultiesIds.DIFF_3, 1));
        return list;
    }

}
