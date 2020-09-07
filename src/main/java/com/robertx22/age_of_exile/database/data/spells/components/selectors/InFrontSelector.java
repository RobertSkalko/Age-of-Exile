package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class InFrontSelector extends BaseTargetSelector {

    public InFrontSelector() {
        super(Arrays.asList(DISTANCE, WIDTH, ENTITY_PREDICATE));
    }

    @Override
    public List<LivingEntity> get(LivingEntity caster, LivingEntity target, BlockPos pos, HashMap<String, Object> map) {
        EntityFinder.EntityPredicate predicate = ENTITY_PREDICATE.get(map);
        float distance = DISTANCE.get(map);
        float width = WIDTH.get(map);

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
            .finder(EntityFinder.SelectionType.IN_FRONT)
            .searchFor(predicate)
            .radius(width)
            .distance(distance);

        return finder.build();
    }

    @Override
    public String GUID() {
        return "in_front";
    }
}
