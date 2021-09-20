package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropTable;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.BonusRequirement;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.FoodItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Triple;

public class FishingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(3, PlayerSkillEnum.FISHING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();
        b.addBonusYieldMasteryLevelStats(PlayerSkillEnum.FISHING);
        b.skill.exp_per_action = 50;

        b.addTieredDrops(0.4F, x -> ProfessionItems.INK_TIER_MAP.get(x)
            .get(), SkillDropTable.INK_TAG);

        for (FoodExileEffect.EffectColor color : FoodExileEffect.EffectColor.values()) {
            for (SkillItemTier tier : SkillItemTier.values()) {

                Triple<FoodType, FoodExileEffect.EffectColor, SkillItemTier> key = Triple.of(FoodType.FISH, color, tier);
                if (FoodItems.RAW_FISH.containsKey(key)) {
                    Item rawfish = FoodItems.RAW_FISH.get(key)
                        .get();

                    BonusRequirement req = BonusRequirement.NONE;

                    for (int i = 0; i < 2; i++) {
                        float chance = 0.1F;

                        if (i == 0) {
                            chance = 0.3F;
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
                                req = BonusRequirement.DAY;
                            }
                            if (color == FoodExileEffect.EffectColor.PURPLE) {
                                req = BonusRequirement.NIGHT;
                            }
                        }

                        DropRewardsBuilder skillDrops = DropRewardsBuilder.of(chance, tier, req);
                        skillDrops.dropReward(new SkillDropReward(100, rawfish, new MinMax(1, 3)));
                        SkillDropTable rewards = skillDrops.build();
                        rewards.tag = SkillDropTable.FISH_TAG;
                        b.skill.dropTables.add(rewards);
                    }

                }
            }
        }
        return b.build();
    }
}
