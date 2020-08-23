package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.spells.entities.cloud.ArrowStormEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.cloud.BlizzardEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.cloud.ThunderstormEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.cloud.VolcanoEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.proj.*;
import com.robertx22.age_of_exile.database.data.spells.entities.single_target_bolt.FireballEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.single_target_bolt.FrostballEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.single_target_bolt.PoisonBallEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.trident.SpearOfJudgementEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.trident.ThunderspearEntity;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.chickens.FireChicken;
import com.robertx22.age_of_exile.mobs.chickens.NatureChicken;
import com.robertx22.age_of_exile.mobs.chickens.ThunderChicken;
import com.robertx22.age_of_exile.mobs.chickens.WaterChicken;
import com.robertx22.age_of_exile.mobs.mages.*;
import com.robertx22.age_of_exile.mobs.skeletons.FireSkeleton;
import com.robertx22.age_of_exile.mobs.skeletons.NatureSkeleton;
import com.robertx22.age_of_exile.mobs.skeletons.ThunderSkeleton;
import com.robertx22.age_of_exile.mobs.skeletons.WaterSkeleton;
import com.robertx22.age_of_exile.mobs.slimes.*;
import com.robertx22.age_of_exile.mobs.spiders.*;
import com.robertx22.age_of_exile.mobs.zombies.*;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EntityRegister {

    public List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
    public List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

    public HashMap<EntityType, Boolean> DAYLIGHT_MOBS = new HashMap<>();

    public EntityRegister() {
        DAYLIGHT_MOBS.put(FIRE_CHICKEN, true);
        DAYLIGHT_MOBS.put(WATER_CHICKEN, true);
        DAYLIGHT_MOBS.put(NATURE_CHICKEN, true);
        DAYLIGHT_MOBS.put(THUNDER_CHICKEN, true);

        DAYLIGHT_MOBS.put(FIRE_SPIDER, true);
        DAYLIGHT_MOBS.put(WATER_SPIDER, true);
        DAYLIGHT_MOBS.put(NATURE_SPIDER, true);
        DAYLIGHT_MOBS.put(THUNDER_SPIDER, true);
    }

    static EntityDimensions spiderDim = new EntityDimensions(1.4F, 0.9F, false);
    static EntityDimensions zombieDim = new EntityDimensions(0.6F, 1.95F, false);
    static EntityDimensions mageDim = new EntityDimensions(0.5F, 2.2F, true);
    static EntityDimensions skeleDim = new EntityDimensions(0.5F, 2, true);
    static EntityDimensions chickenDim = new EntityDimensions(0.7F, 0.5F, true);

    public EntityType<ThunderstormEntity> THUNDERSTORM = projectile(ThunderstormEntity::new, "thunderstorm");
    public EntityType<TridentEntity> THUNDER_SPEAR = projectile(ThunderspearEntity::new, "thunder_spear", false);
    public EntityType<SpearOfJudgementEntity> HOLY_SPEAR = projectile(SpearOfJudgementEntity::new, "holy_spear", false);
    public EntityType<LightningTotemEntity> LIGHTNING_TOTEM = projectile(LightningTotemEntity::new, "lightning_totem");

    public EntityType<FireballEntity> FIREBOLT = projectile(FireballEntity::new, "fireball");
    public EntityType<FireBombEntity> FIRE_BOMB = projectile(FireBombEntity::new, "fire_bomb");
    public EntityType<ThrowFlameEntity> THROW_FLAMES = projectile(ThrowFlameEntity::new, "seeker_flame");
    public EntityType<VolcanoEntity> VOLCANO = projectile(VolcanoEntity::new, "volcano");
    public EntityType<PoisonBallEntity> POISON_BALL = projectile(PoisonBallEntity::new, "poison_ball");

    public EntityType<FrostballEntity> FROSTBOLT = projectile(FrostballEntity::new, "frostball");
    public EntityType<WhirlpoolEntity> WHIRPOOL = projectile(WhirlpoolEntity::new, "whirlpool");
    public EntityType<BlizzardEntity> BLIZZARD = projectile(BlizzardEntity::new, "blizzard");
    public EntityType<TidalWaveEntity> TIDAL_WAVE = projectile(TidalWaveEntity::new, "tidal_wave");

    public EntityType<RangerArrowEntity> RANGER_ARROW = projectile(RangerArrowEntity::new, "ranger_arrow");
    public EntityType<ArrowStormEntity> ARROW_STORM = projectile(ArrowStormEntity::new, "arrow_storm");

    public EntityType<ArcaneSlime> ARCANE_SLIME = mob(ArcaneSlime::new, "arcane_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<FireSlime> FIRE_SLIME = mob(FireSlime::new, "fire_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<WaterSlime> WATER_SLIME = mob(WaterSlime::new, "water_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<ThunderSlime> THUNDER_SLIME = mob(ThunderSlime::new, "thunder_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<NatureSlime> NATURE_SLIME = mob(NatureSlime::new, "nature_slime", new EntityDimensions(2.04F, 2.04F, false));

    public EntityType<ArcaneSpider> ARCANE_SPIDER = mob(ArcaneSpider::new, "arcane_spider", spiderDim);
    public EntityType<FireSpider> FIRE_SPIDER = mob(FireSpider::new, "fire_spider", spiderDim);
    public EntityType<WaterSpider> WATER_SPIDER = mob(WaterSpider::new, "water_spider", spiderDim);
    public EntityType<ThunderSpider> THUNDER_SPIDER = mob(ThunderSpider::new, "thunder_spider", spiderDim);
    public EntityType<NatureSpider> NATURE_SPIDER = mob(NatureSpider::new, "nature_spider", spiderDim);

    public EntityType<ArcaneZombie> ARCANE_ZOMBIE = mob(ArcaneZombie::new, "arcane_zombie", zombieDim);
    public EntityType<FireZombie> FIRE_ZOMBIE = mob(FireZombie::new, "fire_zombie", zombieDim);
    public EntityType<WaterZombie> WATER_ZOMBIE = mob(WaterZombie::new, "water_zombie", zombieDim);
    public EntityType<ThunderZombie> THUNDER_ZOMBIE = mob(ThunderZombie::new, "thunder_zombie", zombieDim);
    public EntityType<NatureZombie> NATURE_ZOMBIE = mob(NatureZombie::new, "nature_zombie", zombieDim);

    public EntityType<FireMage> FIRE_MAGE = mob(FireMage::new, "fire_mage", mageDim);
    public EntityType<WaterMage> WATER_MAGE = mob(WaterMage::new, "water_mage", mageDim);
    public EntityType<ThunderMage> THUNDER_MAGE = mob(ThunderMage::new, "thunder_mage", mageDim);
    public EntityType<NatureMage> NATURE_MAGE = mob(NatureMage::new, "nature_mage", mageDim);
    public EntityType<HealerMage> HEALER_MAGE = mob(HealerMage::new, "healer_mage", mageDim);

    public EntityType<FireChicken> FIRE_CHICKEN = mob(FireChicken::new, "fire_chicken", chickenDim);
    public EntityType<WaterChicken> WATER_CHICKEN = mob(WaterChicken::new, "water_chicken", chickenDim);
    public EntityType<NatureChicken> NATURE_CHICKEN = mob(NatureChicken::new, "nature_chicken", chickenDim);
    public EntityType<ThunderChicken> THUNDER_CHICKEN = mob(ThunderChicken::new, "thunder_chicken", chickenDim);

    public EntityType<FireSkeleton> FIRE_SKELETON = mob(FireSkeleton::new, "fire_skeleton", skeleDim);
    public EntityType<WaterSkeleton> WATER_SKELETON = mob(WaterSkeleton::new, "water_skeleton", skeleDim);
    public EntityType<ThunderSkeleton> THUNDER_SKELETON = mob(ThunderSkeleton::new, "thunder_skeleton", skeleDim);
    public EntityType<NatureSkeleton> NATURE_SKELETON = mob(NatureSkeleton::new, "nature_skeleton", skeleDim);

    public EntityType<SeedEntity> SEED = projectile(SeedEntity::new, "seed_entity");

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id) {

        return projectile(factory, id, true);

    }

    private <T extends MobEntity> EntityType<T> mob(EntityType.EntityFactory<T> factory,
                                                    String id, EntityDimensions size) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MONSTER, factory).dimensions(size)
            .build();
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Ref.MODID, id), type);
        ENTITY_TYPES.add(type);

        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EntityRegister::canMyMobSpawn);

        return type;
    }

    public static boolean canMyMobSpawn(EntityType<? extends MobEntity> type, ServerWorldAccess sw, SpawnReason spawnReason, BlockPos pos, Random random) {

        if (sw.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }

        if (spawnReason == SpawnReason.SPAWNER) {
            return true;
        }

        if (!WorldAreas.getArea((World) sw, pos)
            .getAreaModifier()
            .canMobSpawn(type)) {
            return false;
        }

        if (sw.toServerWorld()
            .isDay()) {

            boolean isdaylightmob = ModRegistry.ENTITIES.DAYLIGHT_MOBS.getOrDefault(type, false);
            if (isdaylightmob) {
                return true;
            }
            if (!ModConfig.get().Server.ALLOW_EXILE_MOBS_DAY_SPAWNS) {
                return false;
            }
            DimensionConfig dimConfig = SlashRegistry.getDimensionConfig(sw.toServerWorld());
            if (LevelUtils.determineLevelPerDistanceFromSpawn(sw.toServerWorld(), pos, dimConfig) < ModConfig.get().Server.LVL_WHEN_MOBS_START_SPAWNING_IN_DAYLIGHT) {
                return false; // otherwise lvl 1 newbies are screwed
            }
            PlayerEntity nearest = PlayerUtils.nearestPlayer(sw.toServerWorld(), pos);
            if (nearest != null) {
                double distance = nearest.getBlockPos()
                    .getSquaredDistance(pos);
                if (distance < 3000) {
                    return false;
                }
            }

        }

        return true;

    }

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id, boolean itemRender) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory).dimensions(new EntityDimensions(0.5F, 0.5F, true))
            .build();

        Registry.register(Registry.ENTITY_TYPE, new Identifier(Ref.MODID, id), type);

        ENTITY_TYPES.add(type);

        if (itemRender) {
            ENTITY_THAT_USE_ITEM_RENDERS.add(type);
        }

        return type;
    }

}


