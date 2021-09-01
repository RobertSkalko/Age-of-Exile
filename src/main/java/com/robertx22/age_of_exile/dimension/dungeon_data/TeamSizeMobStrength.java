package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class TeamSizeMobStrength {

    public int hp;
    public int dmg;
    public int regen;

    public TeamSizeMobStrength(int hp, int dmg, int regen) {
        this.hp = hp;
        this.dmg = dmg;
        this.regen = regen;
    }

    public void addStats(EntityCap.UnitData data) {

        ExactStatData.noScaling(hp, ModType.GLOBAL_INCREASE, Health.getInstance()
                .GUID())
            .applyStats(data);
        ExactStatData.noScaling(dmg, ModType.FLAT, Stats.TOTAL_DAMAGE.get()
                .GUID())
            .applyStats(data);
        ExactStatData.noScaling(regen, ModType.PERCENT, HealthRegen.getInstance()
                .GUID())
            .applyStats(data);

    }
}
