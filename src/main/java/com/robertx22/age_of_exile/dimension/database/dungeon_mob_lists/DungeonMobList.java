package com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class DungeonMobList implements JsonExileRegistry<DungeonMobList>, IAutoGson<DungeonMobList> {
    public static DungeonMobList SERIALIZER = new DungeonMobList();

    public String id = "";

    public List<WeightedMobEntry> mobs = new ArrayList<>();
    public List<WeightedMobEntry> bosses = new ArrayList<>();

    @Override
    public Class<DungeonMobList> getClassForSerialization() {
        return DungeonMobList.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.DUNGEON_MOB_LIST;
    }

    @Override
    public String GUID() {
        return id;
    }

    public LivingEntity spawnBoss(ServerWorld world, BlockPos pos) {
        WeightedMobEntry random = RandomUtils.weightedRandom(bosses);

        EntityType type = Registry.ENTITY_TYPE.get(new ResourceLocation(random.id));

        LivingEntity en = (LivingEntity) type.create(world);

        Load.Unit(en)
            .setRarity(IRarity.BOSS_ID);

        setup(en, pos, world);

        return en;
    }

    public EntityType getRandomMob() {
        EntityType type = null;
        try {
            WeightedMobEntry random = RandomUtils.weightedRandom(mobs);
            type = Registry.ENTITY_TYPE.get(new ResourceLocation(random.id));
        } catch (Exception e) {
            e.printStackTrace();
            return EntityType.ZOMBIE;
        }

        return type;
    }

    public LivingEntity spawnMob(ServerWorld world, EntityType type, BlockPos pos) {

        try {

            LivingEntity en = (LivingEntity) type.create(world);

            setup(en, pos, world);

            return en;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void setup(LivingEntity en, BlockPos pos, ServerWorld world) {

        en.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);

        if (en instanceof MobEntity) {
            MobEntity mob = (MobEntity) en;

            mob.setPersistenceRequired();

            mob.finalizeSpawn(
                world,
                world.getCurrentDifficultyAt(pos),
                SpawnReason.MOB_SUMMONED,
                null, null);

        }

        world.addFreshEntityWithPassengers(en);

    }

    @Override
    public int Weight() {
        return 1000;
    }

}
