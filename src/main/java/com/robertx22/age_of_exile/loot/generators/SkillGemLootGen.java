package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class SkillGemLootGen extends BaseLootGen<SkillGemBlueprint> {

    public SkillGemLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) ModConfig.get().DropRates.SKILL_GEM_DROPRATE;
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
