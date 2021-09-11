package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class InFrontSelector extends BaseTargetSelector {

    public InFrontSelector() {
        super(Arrays.asList(DISTANCE, WIDTH, ENTITY_PREDICATE));
    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        AllyOrEnemy predicate = data.getEntityPredicate();
        float distance = data.get(DISTANCE)
            .floatValue();
        float width = data.get(WIDTH)
            .floatValue();

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
            .finder(EntityFinder.SelectionType.IN_FRONT)
            .searchFor(predicate)
            .radius(Math.min(width, distance))
            .distance(distance);

        return finder.build();
    }

    public MapHolder create(Double distance, Double width, AllyOrEnemy pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(DISTANCE, distance);
        d.put(WIDTH, width);
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "in_front";
    }
}
