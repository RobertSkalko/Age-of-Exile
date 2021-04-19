package com.robertx22.age_of_exile.aoe_data.database.dun_mob_list;

import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.WeightedMobEntry;
import net.minecraft.entity.EntityType;

public class DungeonMobListBuilder {

    DungeonMobList d = new DungeonMobList();

    public static DungeonMobListBuilder of(String id) {
        DungeonMobListBuilder b = new DungeonMobListBuilder();
        b.d.id = id;
        return b;

    }

    public DungeonMobListBuilder addMob(EntityType<?> type) {
        return addMob(type, 1000);
    }

    public DungeonMobListBuilder addMob(EntityType<?> type, int weight) {
        d.mobs.add(new WeightedMobEntry(weight, type));
        return this;
    }

    public DungeonMobListBuilder addBoss(EntityType<?> type) {
        return addBoss(type, 1000);
    }

    public DungeonMobListBuilder addBoss(EntityType<?> type, int weight) {
        d.bosses.add(new WeightedMobEntry(weight, type));
        return this;
    }

    public DungeonMobList build() {
        d.addToSerializables();
        return d;
    }

}
