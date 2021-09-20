package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.items.fishing.RawFishItem;
import com.robertx22.age_of_exile.player_skills.items.foods.*;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect.*;

public class FoodItems {

    public static void init() {

        for (EffectColor color : EffectColor.values()) {
            EXTRACT_MAP.put(color, Def.item(() -> new FoodExtractItem(color)));
        }

        reg(FoodType.APPLE, Arrays.asList(MANA_REGEN, HEALTH_REGEN));
        reg(FoodType.COOKIE, Arrays.asList(TREASURE_QUALITY, HEALTH_REGEN));
        reg(FoodType.BREW, Arrays.asList(CRITICAL, HEALING, PHYSICAL_DAMAGE, ELEMENTAL_RESISTANCE));
        reg(FoodType.JAM, Arrays.asList(DEF_PURPLE, DEF_BLUE, DEF_YELLOW, DEF_GREEN, DEF_GRAY));

        reg(FoodType.DRINK, Arrays.asList(EXPERIENCE));

        regFish(FoodType.FISH, Arrays.asList(FIRE_DAMAGE, WATER_DAMAGE, NATURE_DAMAGE));

        reg(FoodType.REGEN_MEAL, Arrays.asList(OUTSIDE_COMBAT_COMBO_REGEN, OUTSIDE_COMBAT_MANA_REGEN, OUTSIDE_COMBAT_HEALTH_REGEN));

    }

    public static HashMap<EffectColor, RegObj<FoodExtractItem>> EXTRACT_MAP = new HashMap<>();
    public static HashMap<Triple<FoodType, EffectColor, SkillItemTier>, RegObj<FarmingFoodItem>> MAP = new HashMap<>();
    public static HashMap<Triple<FoodType, EffectColor, SkillItemTier>, RegObj<RawFishItem>> RAW_FISH = new HashMap<>();

    static void regFish(FoodType type, List<FoodExileEffect> effects) {

        for (FoodExileEffect effect : effects) {
            for (SkillItemTier tier : SkillItemTier.values()) {
                RAW_FISH.put(Triple.of(type, effect.color, tier), Def.item(() -> new RawFishItem(tier, type, effect)));
                MAP.put(Triple.of(type, effect.color, tier), Def.item(() -> new FarmingFoodItem(type, effect, tier)));
            }
        }
    }

    static void reg(FoodType type, List<FoodExileEffect> effects) {
        List<FoodExileEffect.EffectColor> colors = new ArrayList<>();

        for (FoodExileEffect effect : effects) {
            for (SkillItemTier tier : SkillItemTier.values()) {

                MAP.put(Triple.of(type, effect.color, tier), Def.item(() -> new FarmingFoodItem(type, effect, tier)));

                if (tier == SkillItemTier.TIER3) {
                    if (colors.contains(effect.color)) {
                        throw new RuntimeException("Can't have 2 effects with same color!!!");
                    }
                    colors.add(effect.color);
                }

            }
        }
    }
}
