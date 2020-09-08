package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.bases.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.special.RangerArrowRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.trident.HolyTridentRenderer;
import com.robertx22.age_of_exile.mobs.renders.*;
import com.robertx22.age_of_exile.mobs.renders.skeleton.ModSkeletonRenderer;
import com.robertx22.age_of_exile.mobs.renders.zombie.ModZombieRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry.INSTANCE;

public class RenderRegister {

    public static void regRenders() {

        for (EntityType type : ENTITIES.ENTITY_THAT_USE_ITEM_RENDERS) {
            INSTANCE.register(type, (d, ctx) -> new MySpriteRenderer<>(d, MinecraftClient.getInstance()
                .getItemRenderer()));
        }

        INSTANCE.register(ENTITIES.HOLY_SPEAR, (d, ctx) -> new HolyTridentRenderer(d));
        INSTANCE.register(ENTITIES.THUNDER_SPEAR, (d, ctx) -> new TridentEntityRenderer(d));
        INSTANCE.register(ENTITIES.RANGER_ARROW, (d, ctx) -> new RangerArrowRenderer(d));

        INSTANCE.register(ENTITIES.ARCANE_SLIME, (d, ctx) -> new ModSlimeRenderer(d, "arcane_slime.png"));
        INSTANCE.register(ENTITIES.FIRE_SLIME, (d, ctx) -> new ModSlimeRenderer(d, "fire_slime.png"));
        INSTANCE.register(ENTITIES.WATER_SLIME, (d, ctx) -> new ModSlimeRenderer(d, "water_slime.png"));
        INSTANCE.register(ENTITIES.THUNDER_SLIME, (d, ctx) -> new ModSlimeRenderer(d, "thunder_slime.png"));
        INSTANCE.register(ENTITIES.NATURE_SLIME, (d, ctx) -> new ModSlimeRenderer(d, "nature_slime.png"));

        INSTANCE.register(ENTITIES.ARCANE_SPIDER, (d, ctx) -> new ModSpiderRenderer(d, "arcane_spider.png"));
        INSTANCE.register(ENTITIES.FIRE_SPIDER, (d, ctx) -> new ModSpiderRenderer(d, "fire_spider.png"));
        INSTANCE.register(ENTITIES.WATER_SPIDER, (d, ctx) -> new ModSpiderRenderer(d, "water_spider.png"));
        INSTANCE.register(ENTITIES.THUNDER_SPIDER, (d, ctx) -> new ModSpiderRenderer(d, "thunder_spider.png"));
        INSTANCE.register(ENTITIES.NATURE_SPIDER, (d, ctx) -> new ModSpiderRenderer(d, "nature_spider.png"));

        INSTANCE.register(ENTITIES.FIRE_ZOMBIE, (d, ctx) -> new ModZombieRenderer(d, "fire_zombie.png", "fire_zombie_outer.png"));
        INSTANCE.register(ENTITIES.WATER_ZOMBIE, (d, ctx) -> new ModZombieRenderer(d, "water_zombie.png", "water_zombie_outer.png"));
        INSTANCE.register(ENTITIES.NATURE_ZOMBIE, (d, ctx) -> new ModZombieRenderer(d, "nature_zombie.png", "nature_zombie_outer.png"));
        INSTANCE.register(ENTITIES.THUNDER_ZOMBIE, (d, ctx) -> new ModZombieRenderer(d, "thunder_zombie.png", "thunder_zombie_outer.png"));
        INSTANCE.register(ENTITIES.ARCANE_ZOMBIE, (d, ctx) -> new ModZombieRenderer(d, "arcane_zombie.png", "arcane_zombie_outer.png"));

        INSTANCE.register(ENTITIES.FIRE_MAGE, (d, ctx) -> new MageMobRenderer(d, "fire_mage.png"));
        INSTANCE.register(ENTITIES.WATER_MAGE, (d, ctx) -> new MageMobRenderer(d, "water_mage.png"));
        INSTANCE.register(ENTITIES.NATURE_MAGE, (d, ctx) -> new MageMobRenderer(d, "nature_mage.png"));
        INSTANCE.register(ENTITIES.THUNDER_MAGE, (d, ctx) -> new MageMobRenderer(d, "thunder_mage.png"));
        INSTANCE.register(ENTITIES.HEALER_MAGE, (d, ctx) -> new MageMobRenderer(d, "healer_mage.png"));

        INSTANCE.register(ENTITIES.FIRE_CHICKEN, (d, ctx) -> new ModChickenRenderer(d, "fire_chicken.png"));
        INSTANCE.register(ENTITIES.WATER_CHICKEN, (d, ctx) -> new ModChickenRenderer(d, "water_chicken.png"));
        INSTANCE.register(ENTITIES.NATURE_CHICKEN, (d, ctx) -> new ModChickenRenderer(d, "nature_chicken.png"));
        INSTANCE.register(ENTITIES.THUNDER_CHICKEN, (d, ctx) -> new ModChickenRenderer(d, "thunder_chicken.png"));

        INSTANCE.register(ENTITIES.FIRE_SKELETON, (d, ctx) -> new ModSkeletonRenderer(d, "stray.png", "fire_overlay.png"));
        INSTANCE.register(ENTITIES.WATER_SKELETON, (d, ctx) -> new ModSkeletonRenderer(d, "stray.png", "water_overlay.png"));
        INSTANCE.register(ENTITIES.NATURE_SKELETON, (d, ctx) -> new ModSkeletonRenderer(d, "stray.png", "nature_overlay.png"));
        INSTANCE.register(ENTITIES.THUNDER_SKELETON, (d, ctx) -> new ModSkeletonRenderer(d, "stray.png", "thunder_overlay.png"));

        INSTANCE.register(ENTITIES.GOLEM_BOSS, (d, ctx) -> new BossGolemRenderer(d));

        INSTANCE.register(ENTITIES.SIMPLE_BLOCK_ENTITY, (d, ctx) -> new FallingBlockEntityRenderer(d));

    }
}

