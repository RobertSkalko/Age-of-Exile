package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public class SelfSelector extends BaseTargetSelector {

    public SelfSelector() {
        super(Arrays.asList());
    }

    @Override
    public List<LivingEntity> get(LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        return Arrays.asList(caster);
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "self";
    }
}
