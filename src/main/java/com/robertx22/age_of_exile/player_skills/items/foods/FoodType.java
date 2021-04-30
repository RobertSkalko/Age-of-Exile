package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.Triple;

public enum FoodType {

    APPLE("Apple", "apple", StatusEffects.WATER_BREATHING, Items.APPLE, Items.COOKED_SALMON),
    DRINK("Drink", "drink", null, null, Items.COOKED_BEEF),
    JAM("Jam", "jam", StatusEffects.FIRE_RESISTANCE, Items.MELON, Items.COOKED_SALMON),
    PIE("Pie", "pie", StatusEffects.HASTE, Items.PUMPKIN_PIE, Items.PUMPKIN_PIE),
    COOKIE("Cookie", "cookie", StatusEffects.SPEED, Items.COOKIE, Items.COOKED_CHICKEN),
    FISH("Fish", "fish", ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, Items.COOKED_COD, Items.COOKED_COD),
    BREW("Brew", "brew", StatusEffects.NIGHT_VISION, Items.MUSHROOM_STEW, Items.COOKED_SALMON);

    public String word;
    public String id;
    public StatusEffect effect;
    private Item vanillaCraftingItem;

    public Item foodValueItem;

    FoodType(String word, String id, StatusEffect effect, Item vanillaCraftingItem, Item foodValueItem) {
        this.word = word;
        this.id = id;
        this.effect = effect;
        this.foodValueItem = foodValueItem;
        this.vanillaCraftingItem = vanillaCraftingItem;
    }

    public Item getCraftItem(SkillItemTier tier, FoodExileEffect.EffectColor color) {
        if (this == FISH) {
            return ModRegistry.FOOD_ITEMS.RAW_FISH.get(Triple.of(FISH, color, tier));
        }
        return vanillaCraftingItem;
    }
}
