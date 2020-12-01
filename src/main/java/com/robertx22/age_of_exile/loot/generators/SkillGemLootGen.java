package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class SkillGemLootGen extends BaseLootGen<GearBlueprint> {

    public SkillGemLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return (float) (ModConfig.get().DropRates.SKILL_GEM_DROPRATE);
    }

    @Override
    public LootType lootType() {
        return LootType.SkillGem;
    }

    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public ItemStack generateOne() {

        SkillGem gem = Database.SkillGems()
            .getWrapped()
            .random();

        ItemStack stack = new ItemStack(ModRegistry.SKILL_GEMS.MAP.get(ImmutablePair.of(gem.attribute, gem.type)));

        SkillGemData data = new SkillGemData();

        data.id = gem.identifier;
        data.stat_perc = RandomUtils.RandomRange(10, 100);
        data.lvl = info.level;
        data.rar = Database.GearRarities()
            .random()
            .GUID(); // TODO

        data.saveToStack(stack);

        return stack;

    }

}

