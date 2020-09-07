package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.BaseFieldNeeder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.List;

public abstract class BaseTargetSelector extends BaseFieldNeeder implements IGUID {

    public BaseTargetSelector(List<MapField> requiredPieces) {
        super(requiredPieces);
    }

    public abstract List<LivingEntity> get(LivingEntity caster, LivingEntity target, BlockPos pos, HashMap<String, Object> map);

}
