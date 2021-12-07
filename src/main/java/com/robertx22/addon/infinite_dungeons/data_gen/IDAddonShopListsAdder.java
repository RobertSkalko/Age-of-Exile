package com.robertx22.addon.infinite_dungeons.data_gen;

import com.robertx22.addon.infinite_dungeons.IDAddonIds;
import com.robertx22.infinite_dungeons.database.db_types.ShopList;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopCost;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopEntry;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopReward;
import com.robertx22.infinite_dungeons.database.ids.DungeonDifficultiesIds;
import com.robertx22.infinite_dungeons.database.ids.DungeonLayoutIds;
import com.robertx22.infinite_dungeons.item.RewardCrateItem;
import com.robertx22.infinite_dungeons.main.DungeonItems;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class IDAddonShopListsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new ShopList().edit(x -> {
                x.setIdForMineAndSlashModPerLayoutId(DungeonLayoutIds.DIAMOND_LICH_CRYPT);
                x.shop_entries.addAll(defaultMineAndSlashShops(DungeonItems.SKELETAL_COIN.get()));
                x.shop_entries.add(new ShopEntry(ShopCost.skeletalCoins(25),
                    ShopReward.nbtItem(RewardCrateItem.create(IDAddonIds.RewardLists.GEM_BOX), 1),
                    DungeonDifficultiesIds.DIFF_3, 1));
            })
            .addToSerializables();

        new ShopList().edit(x -> {
                x.setIdForMineAndSlashModPerLayoutId(DungeonLayoutIds.ABANDONED_PRISON);
                x.shop_entries.addAll(defaultMineAndSlashShops(DungeonItems.SKELETAL_COIN.get()));
                x.shop_entries.add(new ShopEntry(ShopCost.skeletalCoins(25),
                    ShopReward.nbtItem(RewardCrateItem.create(IDAddonIds.RewardLists.GEAR_CRATE), 1),
                    DungeonDifficultiesIds.DIFF_3, 1));
            })
            .addToSerializables();

        new ShopList().edit(x -> {
                x.setIdForMineAndSlashModPerLayoutId(DungeonLayoutIds.UNDEAD_GRAVEYARD);
                x.shop_entries.addAll(defaultMineAndSlashShops(DungeonItems.SKELETAL_COIN.get()));
                x.shop_entries.add(new ShopEntry(ShopCost.skeletalCoins(50),
                    ShopReward.nbtItem(RewardCrateItem.create(IDAddonIds.RewardLists.ANY_SLOT_GEAR_CRATE), 1),
                    DungeonDifficultiesIds.DIFF_4, 1));
            })
            .addToSerializables();

    }

    static List<ShopEntry> defaultMineAndSlashShops(Item coin) {
        List<ShopEntry> list = new ArrayList();
        list.add(new ShopEntry(cost(coin, 25), ShopReward.item(Items.ENDER_PEARL, 3), DungeonDifficultiesIds.DIFF_0, 1));
        list.add(new ShopEntry(cost(coin, 25), ShopReward.item(Items.ENDER_EYE, 1), DungeonDifficultiesIds.DIFF_5, 1));
        list.add(new ShopEntry(cost(coin, 15), ShopReward.item(Items.LEATHER, 9), DungeonDifficultiesIds.DIFF_2, 3));
        list.add(new ShopEntry(cost(coin, 30), ShopReward.item(Items.DIAMOND, 3), DungeonDifficultiesIds.DIFF_3, 1));
        return list;
    }

    static ShopCost cost(Item item, int amount) {
        return new ShopCost(ForgeRegistries.ITEMS.getKey(item)
            .toString(), amount);
    }

}
