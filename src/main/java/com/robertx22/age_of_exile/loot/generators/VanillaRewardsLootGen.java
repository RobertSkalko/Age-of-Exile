package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class VanillaRewardsLootGen extends BaseLootGen<GearBlueprint> {

    public VanillaRewardsLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return (float) (ModConfig.get().DropRates.LVLING_REWARD_DROPRATE);
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
        return this.info.level > 10;
    }

    @Override
    public ItemStack generateOne() {
        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.VANILLA_EXP_CHEST);

        float multi = LevelUtils.getMaxLevelMultiplier(info.level);

        int exp;
        if (multi < 0.5F) {
            exp = 50;
        } else if (multi < 0.8F) {
            exp = 100;
        } else {
            exp = 250;
        }
        stack.setTag(new NbtCompound());
        stack.getTag()
            .putInt("exp", exp);

        return stack;

    }

}

