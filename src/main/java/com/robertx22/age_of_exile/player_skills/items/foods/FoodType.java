package com.robertx22.age_of_exile.player_skills.items.foods;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.FoodItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import org.apache.commons.lang3.tuple.Triple;

public enum FoodType {

    APPLE("Apple", "apple", null, Items.APPLE, Items.COOKED_SALMON),
    DRINK("Drink", "drink", null, null, Items.COOKED_BEEF),
    JAM("Jam", "jam", null, Items.MELON, Items.COOKED_SALMON),
    PIE("Pie", "pie", null, Items.PUMPKIN_PIE, Items.PUMPKIN_PIE),
    COOKIE("Cookie", "cookie", null, Items.COOKIE, Items.COOKED_CHICKEN),
    FISH("Fish", "fish", null, Items.COOKED_COD, Items.COOKED_COD),
    REGEN_MEAL("Meal", "meal", null, Items.BREAD, Items.BREAD),
    BREW("Brew", "brew", null, Items.MUSHROOM_STEW, Items.COOKED_SALMON);

    public String word;
    public String id;
    public Effect effect;
    private Item vanillaCraftingItem;

    public Item foodValueItem;

    FoodType(String word, String id, Effect effect, Item vanillaCraftingItem, Item foodValueItem) {
        this.word = word;
        this.id = id;
        this.effect = effect;
        this.foodValueItem = foodValueItem;
        this.vanillaCraftingItem = vanillaCraftingItem;
    }

    public Item getCraftItem(SkillItemTier tier, FoodExileEffect.EffectColor color) {
        if (this == FISH) {
            return FoodItems.RAW_FISH.get(Triple.of(FISH, color, tier))
                .get();
        }
        return vanillaCraftingItem;
    }
}
