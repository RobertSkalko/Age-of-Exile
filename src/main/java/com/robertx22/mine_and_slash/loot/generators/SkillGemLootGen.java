package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class SkillGemLootGen extends BaseLootGen<SkillGemBlueprint> {

    public SkillGemLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return ModConfig.INSTANCE.DropRates.SKILL_GEM_DROPRATE.get()
            .floatValue();
    }

    @Override
    public LootType lootType() {
        return LootType.SkillGem;
    }

    @Override
    public ItemStack generateOne() {

        SkillGemBlueprint blueprint = new SkillGemBlueprint(info.level);

        ItemStack stack = blueprint.createStack();

        return stack;

    }

}
