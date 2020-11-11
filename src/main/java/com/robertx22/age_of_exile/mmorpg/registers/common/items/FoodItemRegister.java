package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.vanilla_mc.items.foods.*;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.vanilla_mc.items.foods.FoodExileEffect.*;

public class FoodItemRegister extends BaseItemRegistrator {

    public HashMap<FoodTier, FoodMaterialTierItem> MAT_TIER_MAP = new HashMap<>();
    public HashMap<EffectColor, FoodExtractItem> EXTRACT_MAP = new HashMap<>();

    public HashMap<Triple<FoodType, EffectColor, FoodTier>, FarmingFoodItem> MAP = new HashMap<>();

    public FoodItemRegister() {

        for (FoodTier tier : FoodTier.values()) {
            MAT_TIER_MAP.put(tier, item(new FoodMaterialTierItem(tier)));
        }
        for (EffectColor color : EffectColor.values()) {
            EXTRACT_MAP.put(color, item(new FoodExtractItem(color)));
        }

        reg(FoodType.APPLE, Arrays.asList(MANA_REGEN, HEALTH_REGEN, MAGIC_SHIELD_REGEN));
        reg(FoodType.COOKIE, Arrays.asList(TREASURE_QUALITY, HEALTH_REGEN, MAGIC_SHIELD_REGEN));
        reg(FoodType.BREW, Arrays.asList(CRITICAL, HEALING, PHYSICAL_DAMAGE, ELEMENTAL_RESISTANCE));
        reg(FoodType.BEER, Arrays.asList(SPELL_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, NATURE_DAMAGE, THUNDER_DAMAGE));
        reg(FoodType.JAM, Arrays.asList(SPELL_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, NATURE_DAMAGE, THUNDER_DAMAGE));
        reg(FoodType.PIE, Arrays.asList(SPELL_DAMAGE, FIRE_DAMAGE, WATER_DAMAGE, NATURE_DAMAGE, THUNDER_DAMAGE));

    }

    void reg(FoodType type, List<FoodExileEffect> effects) {
        List<FoodExileEffect.EffectColor> colors = new ArrayList<>();

        for (FoodExileEffect effect : effects) {
            for (FoodTier tier : FoodTier.values()) {

                FarmingFoodItem item = this.item(new FarmingFoodItem(type, effect, tier));
                MAP.put(Triple.of(type, effect.color, tier), item);

                if (tier == FoodTier.ANGELIC) {
                    if (colors.contains(effect.color)) {
                        throw new RuntimeException("Can't have 2 effects with same color!!!");
                    }
                    colors.add(effect.color);
                }
            }
        }
    }
}
