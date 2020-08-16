package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.special.RangerArrowRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.trident.HolyTridentRenderer;
import com.robertx22.age_of_exile.mobs.slimes.ModSlimeRenderer;
import com.robertx22.age_of_exile.mobs.spiders.ModSpiderRenderer;
import com.robertx22.age_of_exile.mobs.zombies.ModZombieRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            INSTANCE.register(type, (dispatcher, context) -> new MySpriteRenderer<>(dispatcher, MinecraftClient.getInstance()
                .getItemRenderer()));
        }

        INSTANCE.register(ENTITIES.HOLY_SPEAR, (dispatcher, context) -> new HolyTridentRenderer(dispatcher));
        INSTANCE.register(ENTITIES.THUNDER_SPEAR, (dispatcher, context) -> new TridentEntityRenderer(dispatcher));
        INSTANCE.register(ENTITIES.RANGER_ARROW, (dispatcher, context) -> new RangerArrowRenderer(dispatcher));

        INSTANCE.register(ENTITIES.ARCANE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "arcane_slime.png"));
        INSTANCE.register(ENTITIES.FIRE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "fire_slime.png"));
        INSTANCE.register(ENTITIES.WATER_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "water_slime.png"));
        INSTANCE.register(ENTITIES.THUNDER_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "thunder_slime.png"));
        INSTANCE.register(ENTITIES.NATURE_SLIME, (dispatcher, context) -> new ModSlimeRenderer(dispatcher, "nature_slime.png"));

        INSTANCE.register(ENTITIES.ARCANE_SPIDER, (dispatcher, context) -> new ModSpiderRenderer(dispatcher, "arcane_spider.png"));
        INSTANCE.register(ENTITIES.FIRE_SPIDER, (dispatcher, context) -> new ModSpiderRenderer(dispatcher, "fire_spider.png"));
        INSTANCE.register(ENTITIES.WATER_SPIDER, (dispatcher, context) -> new ModSpiderRenderer(dispatcher, "water_spider.png"));
        INSTANCE.register(ENTITIES.THUNDER_SPIDER, (dispatcher, context) -> new ModSpiderRenderer(dispatcher, "thunder_spider.png"));
        INSTANCE.register(ENTITIES.NATURE_SPIDER, (dispatcher, context) -> new ModSpiderRenderer(dispatcher, "nature_spider.png"));

        INSTANCE.register(ENTITIES.FIRE_ZOMBIE, (dispatcher, context) -> new ModZombieRenderer(dispatcher, "fire_zombie.png", "fire_zombie_outer.png"));
        INSTANCE.register(ENTITIES.WATER_ZOMBIE, (dispatcher, context) -> new ModZombieRenderer(dispatcher, "water_zombie.png", "water_zombie_outer.png"));
        INSTANCE.register(ENTITIES.NATURE_ZOMBIE, (dispatcher, context) -> new ModZombieRenderer(dispatcher, "nature_zombie.png", "nature_zombie_outer.png"));
        INSTANCE.register(ENTITIES.THUNDER_ZOMBIE, (dispatcher, context) -> new ModZombieRenderer(dispatcher, "thunder_zombie.png", "thunder_zombie_outer.png"));
        INSTANCE.register(ENTITIES.ARCANE_ZOMBIE, (dispatcher, context) -> new ModZombieRenderer(dispatcher, "arcane_zombie.png", "arcane_zombie_outer.png"));

    }
}

