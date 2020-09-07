package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.ProjectileCastOptions;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonProjectileAction extends SpellAction {

    public SummonProjectileAction() {
        super(Arrays.asList(MapField.PROJECTILE_COUNT, MapField.PROJECTILE_SPEED, MapField.LIFESPAN_TICKS));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        SpellCastContext cctx = new SpellCastContext(ctx.caster, 0, ctx.calculatedSpellData);

        Optional<EntityType<?>> projectile = EntityType.get(data.get(MapField.PROJECTILE_ENTITY));

        ProjectileCastOptions builder = new ProjectileCastOptions(cctx, projectile.get());
        builder.projectilesAmount = data.get(MapField.PROJECTILE_COUNT)
            .intValue();
        builder.shootSpeed = data.get(MapField.PROJECTILE_SPEED);

        builder.apart = 75;
        builder.cast();
    }

    public MapHolder create(int projCount, float speed, EntityType type) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, (float) projCount);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(type)
            .toString());
        return c;
    }

    @Override
    public String GUID() {
        return "projectile";
    }

}
