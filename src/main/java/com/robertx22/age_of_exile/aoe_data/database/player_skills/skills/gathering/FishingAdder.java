package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusRequirement;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Triple;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.TIERED;

public class FishingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(3, PlayerSkillEnum.FISHING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();
        b.addBonusYieldMasteryLevelStats(PlayerSkillEnum.FISHING);
        b.skill.exp_per_action = 50;

        b.addTieredDrops(0.5F, x -> TIERED.INK_TIER_MAP.get(x));

        for (FoodExileEffect.EffectColor color : FoodExileEffect.EffectColor.values()) {
            for (SkillItemTier tier : SkillItemTier.values()) {

                Triple<FoodType, FoodExileEffect.EffectColor, SkillItemTier> key = Triple.of(FoodType.FISH, color, tier);
                if (ModRegistry.FOOD_ITEMS.RAW_FISH.containsKey(key)) {
                    Item rawfish = ModRegistry.FOOD_ITEMS.RAW_FISH.get(key);

                    BonusRequirement req = BonusRequirement.NONE;

                    if (color == FoodExileEffect.EffectColor.BLUE) {
                        req = BonusRequirement.COLD_BIOME;
                    }
                    if (color == FoodExileEffect.EffectColor.RED) {
                        req = BonusRequirement.HOT_BIOME;
                    }
                    if (color == FoodExileEffect.EffectColor.GREEN) {
                        req = BonusRequirement.SWAMP_BIOME;
                    }
                    if (color == FoodExileEffect.EffectColor.YELLOW) {
                        req = BonusRequirement.MOUNTAIN_BIOME;
                    }

                    DropRewardsBuilder skillDrops = DropRewardsBuilder.of(0.3F, tier, req);
                    skillDrops.dropReward(new SkillDropReward(100, rawfish, new MinMax(1, 3)));
                    b.skill.dropTables.add(skillDrops.build());
                }
            }
        }
        return b.build();
    }
}
