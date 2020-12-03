package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.random_skill_gem_stats.RandomSkillGemStats;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.FilterListWrap;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.SkillGemRarityPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.SkillGemTypePart;
import com.robertx22.age_of_exile.loot.generators.stack_changers.DamagedGear;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class SkillGemBlueprint extends ItemBlueprint {

    public SkillGemBlueprint(int lvl) {
        super(lvl);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public SkillGemBlueprint(LootInfo info) {
        super(info);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);

    }

    public SkillGemRarityPart rarity = new SkillGemRarityPart(this);

    public SkillGemTypePart type = new SkillGemTypePart(this);

    @Override
    public RarityRegistryContainer<GearRarity> getRarityContainer() {
        return Database.GearRarities();
    }

    @Override
    ItemStack generate() {

        SkillGemData data = new SkillGemData();

        data.lvl = this.level.get();
        data.rar = rarity.get()
            .GUID();
        data.id = type.get()
            .GUID();
        data.stat_perc = data.getRarity().stat_percents.random();

        if (data.getSkillGem().type == SkillGemType.SUPPORT_GEM) {
            int randoms = RandomUtils.RandomRange(0, 3);
            for (int i = 0; i < randoms; i++) {
                FilterListWrap<RandomSkillGemStats> opt = Database.RandomSkilLGemStats()
                    .getFilterWrapped(x -> x.tags.stream()
                        .anyMatch(t -> data.getSkillGem().tags.contains(t)));

                if (!opt.list.isEmpty()) {
                    opt.random().stats.forEach(x -> data.random_stats.add(new StatModifier(x.min1, x.max1, x.GetStat(), x.getModType())));
                }
            }
        }

        ItemStack
            stack = new ItemStack(ModRegistry.SKILL_GEMS.MAP.get(ImmutablePair.of(data.getSkillGem().attribute, data.getSkillGem().type)));

        data.saveToStack(stack);

        return stack;
    }

}