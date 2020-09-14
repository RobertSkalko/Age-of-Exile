package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.SimpleArrowEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.SimpleTridentEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.bosses.GolemBossEntity;
import com.robertx22.age_of_exile.mobs.chickens.ModChicken;
import com.robertx22.age_of_exile.mobs.mages.*;
import com.robertx22.age_of_exile.mobs.skeletons.ModSkeleton;
import com.robertx22.age_of_exile.mobs.slimes.ModSlime;
import com.robertx22.age_of_exile.mobs.spiders.ModSpider;
import com.robertx22.age_of_exile.mobs.zombies.ModZombie;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ModEntities {

    public List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
    public List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

    public ModEntities() {

    }

    static EntityDimensions spiderDim = new EntityDimensions(1.4F, 0.9F, false);
    static EntityDimensions zombieDim = new EntityDimensions(0.6F, 1.95F, false);
    static EntityDimensions mageDim = new EntityDimensions(0.5F, 2.2F, true);
    static EntityDimensions skeleDim = new EntityDimensions(0.5F, 2, true);
    static EntityDimensions chickenDim = new EntityDimensions(0.7F, 0.5F, true);

    public EntityType<SimpleProjectileEntity> SIMPLE_PROJECTILE = projectile(SimpleProjectileEntity::new, "spell_projectile");
    public EntityType<SimpleArrowEntity> SIMPLE_ARROW = projectile(SimpleArrowEntity::new, "spell_arrow");
    public EntityType<StationaryFallingBlockEntity> SIMPLE_BLOCK_ENTITY = projectile(StationaryFallingBlockEntity::new, "spell_block_entity", false);
    public EntityType<SimpleTridentEntity> SIMPLE_TRIDENT = projectile(SimpleTridentEntity::new, "spell_trident", false);

    public EntityType<ModSlime> ARCANE_SLIME = mob(ModSlime::new, "arcane_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<ModSlime> FIRE_SLIME = mob(ModSlime::new, "fire_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<ModSlime> WATER_SLIME = mob(ModSlime::new, "water_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<ModSlime> THUNDER_SLIME = mob(ModSlime::new, "thunder_slime", new EntityDimensions(2.04F, 2.04F, false));
    public EntityType<ModSlime> NATURE_SLIME = mob(ModSlime::new, "nature_slime", new EntityDimensions(2.04F, 2.04F, false));

    public EntityType<ModSpider> ARCANE_SPIDER = mob(ModSpider::new, "arcane_spider", spiderDim);
    public EntityType<ModSpider> FIRE_SPIDER = mob(ModSpider::new, "fire_spider", spiderDim);
    public EntityType<ModSpider> WATER_SPIDER = mob(ModSpider::new, "water_spider", spiderDim);
    public EntityType<ModSpider> THUNDER_SPIDER = mob(ModSpider::new, "thunder_spider", spiderDim);
    public EntityType<ModSpider> NATURE_SPIDER = mob(ModSpider::new, "nature_spider", spiderDim);

    public EntityType<ModZombie> ARCANE_ZOMBIE = mob(ModZombie::new, "arcane_zombie", zombieDim);
    public EntityType<ModZombie> FIRE_ZOMBIE = mob(ModZombie::new, "fire_zombie", zombieDim);
    public EntityType<ModZombie> WATER_ZOMBIE = mob(ModZombie::new, "water_zombie", zombieDim);
    public EntityType<ModZombie> THUNDER_ZOMBIE = mob(ModZombie::new, "thunder_zombie", zombieDim);
    public EntityType<ModZombie> NATURE_ZOMBIE = mob(ModZombie::new, "nature_zombie", zombieDim);

    public EntityType<FireMage> FIRE_MAGE = mob(FireMage::new, "fire_mage", mageDim);
    public EntityType<WaterMage> WATER_MAGE = mob(WaterMage::new, "water_mage", mageDim);
    public EntityType<ThunderMage> THUNDER_MAGE = mob(ThunderMage::new, "thunder_mage", mageDim);
    public EntityType<NatureMage> NATURE_MAGE = mob(NatureMage::new, "nature_mage", mageDim);
    public EntityType<HealerMage> HEALER_MAGE = mob(HealerMage::new, "healer_mage", mageDim);

    public EntityType<ModChicken> FIRE_CHICKEN = mob(ModChicken::new, "fire_chicken", chickenDim);
    public EntityType<ModChicken> WATER_CHICKEN = mob(ModChicken::new, "water_chicken", chickenDim);
    public EntityType<ModChicken> NATURE_CHICKEN = mob(ModChicken::new, "nature_chicken", chickenDim);
    public EntityType<ModChicken> THUNDER_CHICKEN = mob(ModChicken::new, "thunder_chicken", chickenDim);

    public EntityType<ModSkeleton> FIRE_SKELETON = mob(ModSkeleton::new, "fire_skeleton", skeleDim);
    public EntityType<ModSkeleton> WATER_SKELETON = mob(ModSkeleton::new, "water_skeleton", skeleDim);
    public EntityType<ModSkeleton> THUNDER_SKELETON = mob(ModSkeleton::new, "thunder_skeleton", skeleDim);
    public EntityType<ModSkeleton> NATURE_SKELETON = mob(ModSkeleton::new, "nature_skeleton", skeleDim);

    public EntityType<GolemBossEntity> GOLEM_BOSS = mob(GolemBossEntity::new, "boss_golem", new EntityDimensions(1.4F, 2.7F, true));

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

        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntities::canMyMobSpawn);
        return type;
    }

    public static boolean canMyMobSpawn(EntityType<? extends MobEntity> type, ServerWorldAccess sw, SpawnReason spawnReason, BlockPos pos, Random random) {

        if (sw.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }

        if (!WorldAreas.getArea((World) sw, pos)
            .getAreaModifier()
            .canMobSpawn(type)) {
            return false;
        }
        return HostileEntity.canSpawnInDark((EntityType<? extends HostileEntity>) type, sw, spawnReason, pos, random);
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


