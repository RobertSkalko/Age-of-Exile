package com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class DungeonMobList implements ISerializedRegistryEntry<DungeonMobList>, IAutoGson<DungeonMobList> {
    public static DungeonMobList SERIALIZER = new DungeonMobList();

    public String id = "";

    public List<WeightedMobEntry> mobs = new ArrayList<>();
    public List<WeightedMobEntry> bosses = new ArrayList<>();

    public Identifier getIconId() {
        return Ref.guiId("dungeon/icons/" + id);
    }

    @Override
    public Class<DungeonMobList> getClassForSerialization() {
        return DungeonMobList.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.DUNGEON_MOB_LIST;
    }

    @Override
    public String GUID() {
        return id;
    }

    public LivingEntity spawnBoss(ServerWorld world, BlockPos pos, int tier) {
        WeightedMobEntry random = RandomUtils.weightedRandom(bosses);

        EntityType type = Registry.ENTITY_TYPE.get(new Identifier(random.id));

        LivingEntity en = (LivingEntity) type.create(world);

        Load.Unit(en)
            .setRarity(IRarity.BOSS_ID);

        setup(en, pos, world);

        return en;
    }

    public LivingEntity spawnRandomMob(ServerWorld world, BlockPos pos, int tier) {

        WeightedMobEntry random = RandomUtils.weightedRandom(mobs);

        EntityType type = Registry.ENTITY_TYPE.get(new Identifier(random.id));

        LivingEntity en = (LivingEntity) type.create(world);

        setup(en, pos, world);

        return en;
    }

    private void setup(LivingEntity en, BlockPos pos, ServerWorld world) {

        en.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);

        if (en instanceof MobEntity) {
            MobEntity mob = (MobEntity) en;

            mob.setPersistent();

            mob.initialize(
                world,
                world.getLocalDifficulty(pos),
                SpawnReason.MOB_SUMMONED,
                null, null);

        }

        world.spawnEntityAndPassengers(en);

    }
}
