package com.robertx22.addon.infinite_dungeons.data_gen;

import com.robertx22.addon.infinite_dungeons.IDAddonIds;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate.LootCrateItem;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.rarity_gear.RarityGearCrateItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.infinite_dungeons.data_gen.builders.RewardListBuilder;
import com.robertx22.infinite_dungeons.database.db_types.layout.ShopReward;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.util.text.TextFormatting;

public class IDAddonRewardListAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        RewardListBuilder.of(IDAddonIds.RewardLists.GEM_BOX, TextFormatting.LIGHT_PURPLE)
            .jackpot(ShopReward.nbtItem(LootCrateItem.ofGem(GemItem.GemRank.GRAND), 1), 25)
            .add(ShopReward.nbtItem(LootCrateItem.ofGem(GemItem.GemRank.FLAWED), 1), 250)
            .add(ShopReward.nbtItem(LootCrateItem.ofGem(GemItem.GemRank.CHIPPED), 1), 500)
            .add(ShopReward.nbtItem(LootCrateItem.ofGem(GemItem.GemRank.CRACKED), 1), 1000)
            .build();

        RewardListBuilder.of(IDAddonIds.RewardLists.GEAR_CRATE, TextFormatting.LIGHT_PURPLE)
            .jackpot(ShopReward.nbtItem(RarityGearCrateItem.ofRarity(IRarity.UNIQUE_ID), 1), 25)
            .add(ShopReward.nbtItem(RarityGearCrateItem.ofRarity(IRarity.EPIC_ID), 1), 50)
            .add(ShopReward.nbtItem(RarityGearCrateItem.ofRarity(IRarity.RARE_ID), 1), 500)
            .add(ShopReward.nbtItem(RarityGearCrateItem.ofRarity(IRarity.UNCOMMON), 1), 1000)
            .build();

        RewardListBuilder.of(IDAddonIds.RewardLists.ANY_SLOT_GEAR_CRATE, TextFormatting.RED)
            .jackpot(ShopReward.nbtItem(StatSoulItem.ofAnySlotOfRarity(IRarity.UNIQUE_ID), 1), 20)
            .add(ShopReward.nbtItem(StatSoulItem.ofAnySlotOfRarity(IRarity.EPIC_ID), 1), 40)
            .add(ShopReward.nbtItem(StatSoulItem.ofAnySlotOfRarity(IRarity.RARE_ID), 1), 400)
            .add(ShopReward.nbtItem(StatSoulItem.ofAnySlotOfRarity(IRarity.UNCOMMON), 1), 2000)
            .build();

        RewardListBuilder.of(IDAddonIds.RewardLists.UPGRADE_BOX, TextFormatting.GOLD)
            .jackpot(ShopReward.item(CurrencyItems.LEGENDARY_PROT_SCROLL.get(), 1), 20)
            .add(ShopReward.item(CurrencyItems.PLUS_THREE_UPGRADE.get(), 3), 40)
            .add(ShopReward.item(CurrencyItems.PLUS_TWO_UPGRADE.get(), 6), 400)
            .add(ShopReward.item(CurrencyItems.PLUS_ONE_UPGRADE.get(), 9), 2000)
            .build();

    }

}
