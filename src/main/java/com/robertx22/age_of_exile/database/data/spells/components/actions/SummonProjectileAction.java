package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICMainTooltip;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class SummonProjectileAction extends SpellAction implements ICMainTooltip {

    public SummonProjectileAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_COUNT, MapField.ITEM, MapField.PROJECTILE_SPEED, MapField.LIFESPAN_TICKS));
    }

    @Override
    public List<MutableText> getLines(AttachedSpell spell, MapHolder data) {

        TooltipInfo info = new TooltipInfo(ClientOnly.getPlayer());
        List<MutableText> list = new ArrayList<>();

        int amount = data.get(MapField.PROJECTILE_COUNT)
            .intValue();

        if (amount > 1) {
            list.add(new LiteralText("Shoot " + amount + " Projectiles"));

        } else {
            list.add(new LiteralText("Shoot a Projectile"));
        }

        list.addAll(spell.getTooltipForEntity(info, spell, data.get(MapField.ENTITY_NAME)));

        return list;
    }

    public enum ShootWay {
        FROM_PLAYER_VIEW, DOWN
    }

    public enum PositionSource {
        CASTER, SOURCE_ENTITY
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Optional<EntityType<?>> projectile = EntityType.get(data.get(MapField.PROJECTILE_ENTITY));

        PositionSource posSource = data.getOrDefault(PositionSource.CASTER);
        ShootWay shootWay = data.getOrDefault(ShootWay.FROM_PLAYER_VIEW);

        Vec3d pos = ctx.caster.getPos();
        if (posSource == PositionSource.SOURCE_ENTITY) {
            pos = ctx.sourceEntity.getPos();
        }

        ProjectileCastHelper builder = new ProjectileCastHelper(pos, data, ctx.caster, projectile.get(), ctx.calculatedSpellData);
        builder.projectilesAmount = data.get(MapField.PROJECTILE_COUNT)
            .intValue();
        builder.shootSpeed = data.get(MapField.PROJECTILE_SPEED)
            .floatValue();

        builder.apart = data.getOrDefault(MapField.PROJECTILES_APART, 75D)
            .floatValue();

        if (posSource == PositionSource.SOURCE_ENTITY) {
            builder.pitch = ctx.sourceEntity.pitch;
            builder.yaw = ctx.sourceEntity.yaw;
        }
        if (shootWay == ShootWay.DOWN) {
            builder.fallDown = true;
        }

        builder.cast();
    }

    public MapHolder create(Item item, Double projCount, Double speed, EntityType type, Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, projCount);
        c.put(MapField.ENTITY_NAME, Spell.Builder.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getId(item)
            .toString());
        c.put(MapField.GRAVITY, gravity);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(type)
            .toString());
        c.type = GUID();
        return c;
    }

    public MapHolder createArrow(Double projCount, Double speed, Double lifespan, boolean gravity) {
        MapHolder c = createBase(projCount, speed, lifespan, gravity);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(ModRegistry.ENTITIES.SIMPLE_ARROW)
            .toString());
        return c;
    }

    public MapHolder createFallingArrow(Double speed) {
        MapHolder c = createBase(1D, speed, 60D, true);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(ModRegistry.ENTITIES.SIMPLE_ARROW)
            .toString());
        c.put(MapField.POS_SOURCE, PositionSource.SOURCE_ENTITY.name());
        c.put(MapField.SHOOT_DIRECTION, ShootWay.DOWN.name());
        return c;
    }

    public MapHolder createTrident(Double projCount, Double speed, Double lifespan) {
        MapHolder c = createBase(projCount, speed, lifespan, true);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getId(ModRegistry.ENTITIES.SIMPLE_TRIDENT)
            .toString());
        return c;
    }

    private MapHolder createBase(Double projCount, Double speed, Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, projCount);
        c.put(MapField.ENTITY_NAME, Spell.Builder.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, Registry.ITEM.getId(Items.AIR)
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
