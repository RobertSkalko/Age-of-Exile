package com.robertx22.age_of_exile.player_skills.items.alchemy;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public enum PotionType {
    HEALTH("Health", "health", ResourceType.HEALTH, () -> ModRegistry.MISC_ITEMS.BLOOD_WHEAT),
    MANA("Mana", "mana", ResourceType.MANA, () -> ModRegistry.MISC_ITEMS.ARCANE_WHEAT);

    public String word;
    public String id;
    public ResourceType resource;
    public Supplier<Item> craftItem;

    PotionType(String word, String id, ResourceType resource, Supplier<Item> craftItem) {
        this.word = word;
        this.id = id;
        this.resource = resource;
        this.craftItem = craftItem;
    }
}
