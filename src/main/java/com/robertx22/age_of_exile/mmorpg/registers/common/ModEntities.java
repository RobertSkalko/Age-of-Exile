package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.spells.entities.SimpleArrowEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleTridentEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mobs.mages.FireMage;
import com.robertx22.age_of_exile.mobs.mages.HealerMage;
import com.robertx22.age_of_exile.mobs.mages.NatureMage;
import com.robertx22.age_of_exile.mobs.mages.WaterMage;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ModEntities {

    public List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
    public List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

    public ModEntities() {

    }

    static EntityDimensions mageDim = new EntityDimensions(0.5F, 2.2F, true);

    public EntityType<SimpleProjectileEntity> SIMPLE_PROJECTILE = projectile(SimpleProjectileEntity::new, "spell_projectile");
    public EntityType<SimpleArrowEntity> SIMPLE_ARROW = projectile(SimpleArrowEntity::new, "spell_arrow");
    public EntityType<SimpleArrowEntity> SIMPLE_BONE_PROJECTILE = projectile(SimpleArrowEntity::new, "spell_bone_projectile");
    public EntityType<StationaryFallingBlockEntity> SIMPLE_BLOCK_ENTITY = projectile(StationaryFallingBlockEntity::new, "spell_block_entity", false);
    public EntityType<SimpleTridentEntity> SIMPLE_TRIDENT = projectile(SimpleTridentEntity::new, "spell_trident", false);

    public EntityType<FireMage> FIRE_MAGE = mob(FireMage::new, "fire_mage", mageDim);
    public EntityType<WaterMage> WATER_MAGE = mob(WaterMage::new, "water_mage", mageDim);
    public EntityType<NatureMage> NATURE_MAGE = mob(NatureMage::new, "nature_mage", mageDim);
    public EntityType<HealerMage> HEALER_MAGE = mob(HealerMage::new, "healer_mage", mageDim);

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
        if (!sw.toServerWorld()
            .getGameRules()
            .getBoolean(GameRules.DO_MOB_SPAWNING)) {
            return false;
        }
        return HostileEntity.canSpawnInDark((EntityType<? extends HostileEntity>) type, sw, spawnReason, pos, random);
    }

    private <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory,
                                                        String id, boolean itemRender) {

        EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory).dimensions(new EntityDimensions(0.5F, 0.5F, true))
            .trackedUpdateRate(10)
            .build();

        Registry.register(Registry.ENTITY_TYPE, new Identifier(Ref.MODID, id), type);

        ENTITY_TYPES.add(type);

        if (itemRender) {
            ENTITY_THAT_USE_ITEM_RENDERS.add(type);
        }

        return type;
    }

}


