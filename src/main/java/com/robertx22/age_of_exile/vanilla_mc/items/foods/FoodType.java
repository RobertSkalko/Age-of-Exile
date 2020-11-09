package com.robertx22.age_of_exile.vanilla_mc.items.foods;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum FoodType {
    APPLE("Apple", StatusEffects.WATER_BREATHING, Items.APPLE),
    BEER("Beer", ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, Items.HAY_BLOCK),
    JAM("Jam", StatusEffects.FIRE_RESISTANCE, Items.HONEY_BOTTLE),
    PIE("Pie", StatusEffects.HASTE, Items.PUMPKIN_PIE),
    COOKIE("Cookie", StatusEffects.SPEED, Items.COOKIE),
    BREW("Brew", StatusEffects.NIGHT_VISION, Items.MUSHROOM_STEM);

    public String word;
    public StatusEffect effect;
    public Item vanillaCraftingItem;

    FoodType(String word, StatusEffect effect, Item vanillaCraftingItem) {
        this.word = word;
        this.effect = effect;
        this.vanillaCraftingItem = vanillaCraftingItem;
    }
}
