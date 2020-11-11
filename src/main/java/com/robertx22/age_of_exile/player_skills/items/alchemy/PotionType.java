package com.robertx22.age_of_exile.player_skills.items.alchemy;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public enum PotionType {
    HEALTH("Health", "health", ResourcesData.Type.HEALTH, () -> ModRegistry.GEAR_MATERIALS.LIFE),
    MANA("Mana", "mana", ResourcesData.Type.MANA, () -> ModRegistry.GEAR_MATERIALS.MANA),
    MAGIC_SHIELD("Magic Shield", "magic_shield", ResourcesData.Type.MAGIC_SHIELD, () -> ModRegistry.GEAR_MATERIALS.ARCANA);

    public String word;
    public String id;
    public ResourcesData.Type resource;
    public Supplier<Item> craftItem;

    PotionType(String word, String id, ResourcesData.Type resource, Supplier<Item> craftItem) {
        this.word = word;
        this.id = id;
        this.resource = resource;
        this.craftItem = craftItem;
    }
}
