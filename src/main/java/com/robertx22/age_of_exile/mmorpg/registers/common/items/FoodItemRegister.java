package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.player_skills.items.foods.*;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect.*;

public class FoodItemRegister extends BaseItemRegistrator {

    public HashMap<EffectColor, FoodExtractItem> EXTRACT_MAP = new HashMap<>();

    public HashMap<Triple<FoodType, EffectColor, SkillItemTier>, FarmingFoodItem> MAP = new HashMap<>();

    public FoodItemRegister() {

        for (EffectColor color : EffectColor.values()) {
            EXTRACT_MAP.put(color, item(new FoodExtractItem(color)));
        }

        reg(FoodType.APPLE, Arrays.asList(MANA_REGEN, HEALTH_REGEN, MAGIC_SHIELD_REGEN));
        reg(FoodType.COOKIE, Arrays.asList(TREASURE_QUALITY, HEALTH_REGEN, MAGIC_SHIELD_REGEN));
        reg(FoodType.BREW, Arrays.asList(CRITICAL, HEALING, PHYSICAL_DAMAGE, ELEMENTAL_RESISTANCE));
        reg(FoodType.BEER, Arrays.asList(SPELL_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, NATURE_DAMAGE, THUNDER_DAMAGE));
        reg(FoodType.JAM, Arrays.asList(DEF_PURPLE, DEF_BLUE, DEF_YELLOW, DEF_GREEN, DEF_GRAY));
        reg(FoodType.PIE, Arrays.asList(SPELL_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, NATURE_DAMAGE, THUNDER_DAMAGE));

    }

    void reg(FoodType type, List<FoodExileEffect> effects) {
        List<FoodExileEffect.EffectColor> colors = new ArrayList<>();

        for (FoodExileEffect effect : effects) {
            for (SkillItemTier tier : SkillItemTier.values()) {

                FarmingFoodItem item = this.item(new FarmingFoodItem(type, effect, tier));
                MAP.put(Triple.of(type, effect.color, tier), item);

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
