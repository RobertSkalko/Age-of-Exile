package com.robertx22.addon.infinite_dungeons.data_gen;

import com.robertx22.infinite_dungeons.data_gen.builders.RewardListBuilder;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopReward;
import com.robertx22.infinite_dungeons.database.ids.RewardListIds;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;

public class IDAddonRewardListAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        RewardListBuilder.of(RewardListIds.GOD_APPLE, TextFormatting.GOLD)
            .jackpot(ShopReward.item(Items.ENCHANTED_GOLDEN_APPLE, 1), 25)
            .add(ShopReward.item(Items.GOLD_INGOT, 3), 250)
            .add(ShopReward.item(Items.GOLD_NUGGET, 9), 500)
            .add(ShopReward.item(Items.GOLDEN_APPLE, 1), 200)
            .build();

    }

}
