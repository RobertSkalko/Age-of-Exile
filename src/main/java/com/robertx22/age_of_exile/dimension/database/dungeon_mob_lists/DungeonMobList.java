package com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DungeonMobList implements ISerializedRegistryEntry<DungeonMobList>, IAutoGson<DungeonMobList> {
    public static DungeonMobList SERIALIZER = new DungeonMobList();

    public String id = "";

    public List<WeightedMobEntry> mobs = new ArrayList<>();
    public List<WeightedMobEntry> bosses = new ArrayList<>();

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

    public void spawnRandomMob(World world, BlockPos pos, int tier) {

        WeightedMobEntry random = RandomUtils.weightedRandom(mobs);

        EntityType type = Registry.ENTITY_TYPE.get(new Identifier(random.id));

        Entity en = type.create(world);

        if (en instanceof MobEntity) {
            MobEntity mob = (MobEntity) en;

            mob.initialize(
                (ServerWorldAccess) world,
                world.getLocalDifficulty(pos),
                SpawnReason.STRUCTURE,
                null, null);
        }

        world.spawnEntity(en);

    }
}
