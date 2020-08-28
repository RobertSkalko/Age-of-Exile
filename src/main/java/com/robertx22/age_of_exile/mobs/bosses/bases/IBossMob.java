package com.robertx22.age_of_exile.mobs.bosses.bases;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mobs.bosses.GolemBossEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

import java.util.Arrays;
import java.util.List;

public interface IBossMob {
    BossData getBossData();

    static List<BossAndPattern> ALL_PATTERNS = Arrays.asList(
        new BossAndPattern(ModRegistry.ENTITIES.GOLEM_BOSS, GolemBossEntity.getBossPattern())
    );

    public class BossAndPattern {

        public EntityType<? extends MobEntity> type;
        public BlockPattern pattern;

        public BossAndPattern(EntityType<? extends MobEntity> type, BlockPattern pattern) {
            this.type = type;
            this.pattern = pattern;
        }
    }
}
