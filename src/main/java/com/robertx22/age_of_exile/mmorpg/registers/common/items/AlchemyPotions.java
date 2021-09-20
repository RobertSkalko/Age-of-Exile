package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.alchemy.AlchemyPotionItem;
import com.robertx22.age_of_exile.player_skills.items.alchemy.PotionType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;

public class AlchemyPotions {

    public static HashMap<ImmutablePair<SkillItemTier, PotionType>, RegObj<AlchemyPotionItem>> POTIONS_MAP = new HashMap();

    public static void init() {

        for (SkillItemTier tier : SkillItemTier.values()) {
            for (PotionType type : PotionType.values()) {
                POTIONS_MAP.put(ImmutablePair.of(tier, type), Def.item(() -> new AlchemyPotionItem(type, tier)));
            }
        }

    }
}
