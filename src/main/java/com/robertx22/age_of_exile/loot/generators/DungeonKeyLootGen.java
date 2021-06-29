package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class DungeonKeyLootGen extends BaseLootGen<GearBlueprint> {

    public DungeonKeyLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return (float) (ModConfig.get().DropRates.DUNGEON_KEY_DROPRATE);
    }

    @Override
    public LootType lootType() {
        return LootType.LevelingRewards;
    }

    @Override
    public boolean condition() {
        if (info.favorRank != null) {
            if (!info.favorRank.drop_lvl_rewards) {
                return false;
            }
        }
        return this.info.level > 25;
    }

    @Override
    public ItemStack generateOne() {
        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.DUNGEON_KEY_MAP.get(DungeonKeyItem.KeyRarity.Common));

        return stack;

    }

}
