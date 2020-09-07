package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public class TargetSelector extends BaseTargetSelector {

    public TargetSelector() {
        super(Arrays.asList());
    }

    @Override
    public List<LivingEntity> get(LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        return Arrays.asList(target);
    }

    @Override
    public String GUID() {
        return "target";
    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        return c;
    }
}

