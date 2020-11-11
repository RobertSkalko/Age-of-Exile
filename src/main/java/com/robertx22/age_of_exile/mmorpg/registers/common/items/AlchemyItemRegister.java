package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.alchemy.AlchemyPotionItem;
import com.robertx22.age_of_exile.player_skills.items.alchemy.MysteriousPlantItem;
import com.robertx22.age_of_exile.player_skills.items.alchemy.PotionType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;

public class AlchemyItemRegister extends BaseItemRegistrator {

    public HashMap<SkillItemTier, MysteriousPlantItem> MAT_TIER_MAP = new HashMap<>();

    public HashMap<ImmutablePair<SkillItemTier, PotionType>, AlchemyPotionItem> POTIONS_MAP = new HashMap();

    public AlchemyItemRegister() {

        for (SkillItemTier tier : SkillItemTier.values()) {
            MAT_TIER_MAP.put(tier, item(new MysteriousPlantItem(tier)));
        }

        for (SkillItemTier tier : SkillItemTier.values()) {
            for (PotionType type : PotionType.values()) {
                POTIONS_MAP.put(ImmutablePair.of(tier, type), item(new AlchemyPotionItem(type, tier)));
            }
        }

    }
}
