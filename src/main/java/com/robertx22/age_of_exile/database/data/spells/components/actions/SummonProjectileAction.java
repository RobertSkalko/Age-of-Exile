package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonProjectileAction extends SpellAction {

    public SummonProjectileAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_COUNT, MapField.ITEM, MapField.PROJECTILE_SPEED, MapField.LIFESPAN_TICKS));
    }

    public enum ShootWay {
        FROM_PLAYER_VIEW, DOWN
    }

    public enum PositionSource {
        CASTER, SOURCE_ENTITY
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Optional<EntityType<?>> projectile = EntityType.byString(data.get(MapField.PROJECTILE_ENTITY));

        PositionSource posSource = data.getOrDefault(PositionSource.CASTER);
        ShootWay shootWay = data.getOrDefault(ShootWay.FROM_PLAYER_VIEW);

        Vector3d pos = ctx.caster.position();
        if (posSource == PositionSource.SOURCE_ENTITY) {
            pos = ctx.sourceEntity.position();
        }
        boolean silent = data.getOrDefault(MapField.IS_SILENT, false);

        ProjectileCastHelper builder = new ProjectileCastHelper(pos, data, ctx.caster, projectile.get(), ctx.calculatedSpellData);
        builder.projectilesAmount = data.get(MapField.PROJECTILE_COUNT)
            .intValue() + ctx.calculatedSpellData.extra_proj;
        builder.silent = silent;

        builder.shootSpeed = data.get(MapField.PROJECTILE_SPEED)
            .floatValue();

        builder.shootSpeed *= ctx.calculatedSpellData.proj_speed_multi;

        builder.apart = data.getOrDefault(MapField.PROJECTILES_APART, 75D)
            .floatValue();

        if (posSource == PositionSource.SOURCE_ENTITY) {
            builder.pitch = ctx.sourceEntity.xRot;
            builder.yaw = ctx.sourceEntity.yRot;
        }
        if (shootWay == ShootWay.DOWN) {
            builder.fallDown = true;
        }

        builder.cast();
    }

    public MapHolder create(Item item, Double projCount, Double speed, EntityType type, Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, projCount);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getKey(item)
            .toString());
        c.put(MapField.GRAVITY, gravity);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(type)
            .toString());
        c.type = GUID();
        return c;
    }

    public MapHolder create(Item item, Double speed, EntityType type, Double lifespan) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, 1D);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getKey(item)
            .toString());
        c.put(MapField.GRAVITY, true);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(type)
            .toString());
        c.type = GUID();
        return c;
    }

    public MapHolder createArrow(Double projCount) {
        MapHolder c = createBase(projCount, 3D, 80D, true);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(SlashEntities.SIMPLE_ARROW.get())
            .toString());
        return c;
    }

    public MapHolder createFallingArrow(Double speed) {
        MapHolder c = createBase(1D, speed, 60D, true);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(SlashEntities.SIMPLE_ARROW.get())
            .toString());
        c.put(MapField.POS_SOURCE, PositionSource.SOURCE_ENTITY.name());
        c.put(MapField.SHOOT_DIRECTION, ShootWay.DOWN.name());
        return c;
    }

    public MapHolder createTrident(Double projCount, Double speed, Double lifespan) {
        MapHolder c = createBase(projCount, speed, lifespan, true);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(SlashEntities.SIMPLE_TRIDENT.get())
            .toString());
        return c;
    }

    private MapHolder createBase(Double projCount, Double speed, Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, projCount);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getKey(Items.AIR)
            .toString());
        c.put(MapField.GRAVITY, gravity);
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "projectile";
    }

}
