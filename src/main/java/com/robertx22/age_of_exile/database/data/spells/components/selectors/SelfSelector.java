package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SelfSelector extends BaseTargetSelector {

    public SelfSelector() {
        super(Arrays.asList());
    }

    @Override
    public List<LivingEntity> get(LivingEntity caster, LivingEntity target, BlockPos pos, HashMap<String, Object> map) {
        return Arrays.asList(caster);
    }

    @Override
    public String GUID() {
        return "self";
    }
}
