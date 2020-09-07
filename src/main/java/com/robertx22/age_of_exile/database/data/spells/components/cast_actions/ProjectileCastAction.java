package com.robertx22.age_of_exile.database.data.spells.components.cast_actions;

import com.robertx22.age_of_exile.database.data.spells.ProjectileCastOptions;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import net.minecraft.entity.EntityType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class ProjectileCastAction extends CastAction {

    public ProjectileCastAction() {
        super(Arrays.asList(MapField.PROJECTILE_COUNT, MapField.PROJECTILE_SPEED, MapField.LIFESPAN_TICKS));
    }

    @Override
    public void onCast(SpellCtx ctx, HashMap<String, Object> map) {
        SpellCastContext cctx = new SpellCastContext(ctx.caster, 0, ctx.calculatedSpellData);

        Optional<EntityType<?>> projectile = EntityType.get(MapField.PROJECTILE_ENTITY.get(map));

        ProjectileCastOptions builder = new ProjectileCastOptions(cctx, projectile.get());
        builder.projectilesAmount = MapField.PROJECTILE_COUNT.get(map)
            .intValue();
        builder.shootSpeed = MapField.PROJECTILE_SPEED.get(map);

        builder.apart = 75;
        builder.cast();
    }

    @Override
    public String GUID() {
        return "projectile";
    }
}
