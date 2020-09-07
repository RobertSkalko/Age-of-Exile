package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.ProjectileCastOptions;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonProjectileAction extends SpellAction {

    public SummonProjectileAction() {
        super(Arrays.asList(MapField.PROJECTILE_COUNT, MapField.ITEM, MapField.PROJECTILE_SPEED, MapField.LIFESPAN_TICKS));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        SpellCastContext cctx = new SpellCastContext(ctx.caster, 0, ctx.calculatedSpellData);

        Optional<EntityType<?>> projectile = EntityType.get(data.get(MapField.PROJECTILE_ENTITY));

        ProjectileCastOptions builder = new ProjectileCastOptions(cctx, projectile.get());
        builder.projectilesAmount = data.get(MapField.PROJECTILE_COUNT)
            .intValue();
        builder.shootSpeed = data.get(MapField.PROJECTILE_SPEED)
            .floatValue();

        builder.apart = 75;
        builder.cast();
    }

    public MapHolder create(Item item, Double projCount, Double speed, EntityType type, Double lifespan) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, projCount);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getId(item)
            .toString());
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(type)
            .toString());
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "projectile";
    }

}
