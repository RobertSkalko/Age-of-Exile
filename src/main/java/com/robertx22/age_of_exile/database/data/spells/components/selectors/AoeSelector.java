package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.utils.RandomUtils;
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
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, BlockPos pos, MapHolder data) {
        AllyOrEnemy predicate = data.getEntityPredicate();
        Double radius = data.get(RADIUS);

        radius *= ctx.calculatedSpellData.area_multi;

        Double chance = data.getOrDefault(SELECTION_CHANCE, 100D);

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
            .finder(EntityFinder.SelectionType.RADIUS)
            .searchFor(predicate)
            .height(data.getOrDefault(HEIGHT, radius))
            .radius(radius);

        if (chance < 100) {
            return finder.build()
                .stream()
                .filter(x -> RandomUtils.roll(chance))
                .collect(Collectors.toList());
        } else {
            return finder.build();
        }

    }

    public MapHolder enemiesInRadius(Double radius) {
        return create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies);
    }

    public MapHolder alliesInRadius(Double radius) {
        return create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies);
    }

    public MapHolder create(Double radius, EntityFinder.SelectionType type, AllyOrEnemy pred) {
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
