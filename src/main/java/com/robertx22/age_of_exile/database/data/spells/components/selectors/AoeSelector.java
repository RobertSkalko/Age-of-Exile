package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class AoeSelector extends BaseTargetSelector {

    public AoeSelector() {
        super(Arrays.asList(RADIUS, SELECTION_TYPE, ENTITY_PREDICATE));
    }

    @Override
    public List<LivingEntity> get(LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        EntityFinder.EntityPredicate predicate = data.getEntityPredicate();
        Double radius = data.get(RADIUS);

        Double chance = data.getOrDefault(SELECTION_CHANCE, 100D);

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
            .finder(EntityFinder.SelectionType.RADIUS)
            .searchFor(predicate)
            .height(data.getOrDefault(HEIGHT, radius))
            .radius(radius);

        return finder.build()
            .stream()
            .filter(x -> RandomUtils.roll(chance))
            .collect(Collectors.toList());
    }

    public MapHolder create(Double radius, EntityFinder.SelectionType type, EntityFinder.EntityPredicate pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(RADIUS, radius);
        d.put(SELECTION_TYPE, type.name());
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "aoe";
    }
}
