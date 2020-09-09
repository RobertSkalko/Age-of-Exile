package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.ExilePotionAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ParticleInRadiusAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonProjectileAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.POTION_ID;

public class MapHolder {

    public String type;

    private HashMap<String, Object> map = new HashMap<>();

    private ValueCalculationData calc = null;

    public <T> MapHolder put(MapField<T> field, T t) {
        if (field == MapField.VALUE_CALCULATION) {
            calc = (ValueCalculationData) t;
            return this;
        }

        this.map.put(field.GUID(), t);
        return this;
    }

    public <T> T get(MapField<T> field) {
        if (field == MapField.VALUE_CALCULATION) {
            return (T) calc;
        }
        return (T) map.get(field.GUID());
    }

    public StatusEffect getPotion() {
        return Registry.STATUS_EFFECT.get(new Identifier(get(POTION_ID)));
    }

    public Elements getElement() {
        return Elements.valueOf(get(MapField.ELEMENT));
    }

    public DashUtils.Way getPushWay() {
        return DashUtils.Way.valueOf(get(MapField.PUSH_WAY));
    }

    public ExilePotionAction.PotionAction getPotionAction() {
        return ExilePotionAction.PotionAction.valueOf(get(MapField.POTION_ACTION));
    }

    public SummonProjectileAction.ShootWay getOrDefault(SummonProjectileAction.ShootWay way) {
        String f = getOrDefault(MapField.SHOOT_DIRECTION, "");
        if (!f.isEmpty() && SummonProjectileAction.ShootWay.valueOf(f) != null) {
            return SummonProjectileAction.ShootWay.valueOf(f);
        } else {
            return way;
        }
    }

    public SummonProjectileAction.PositionSource getOrDefault(SummonProjectileAction.PositionSource way) {
        String f = getOrDefault(MapField.POS_SOURCE, "");
        if (!f.isEmpty() && SummonProjectileAction.PositionSource.valueOf(f) != null) {
            return SummonProjectileAction.PositionSource.valueOf(f);
        } else {
            return way;
        }
    }

    public DefaultParticleType getParticle() {
        return (DefaultParticleType) Registry.PARTICLE_TYPE.get(new Identifier(get(MapField.PARTICLE_TYPE)));
    }

    public Block getBlock() {
        return Registry.BLOCK.get(new Identifier(get(MapField.BLOCK)));
    }

    public SoundEvent getSound() {
        return Registry.SOUND_EVENT.get(new Identifier(get(MapField.SOUND)));
    }

    public EntityFinder.SelectionType getSelectionType() {
        return EntityFinder.SelectionType.valueOf(get(MapField.SELECTION_TYPE));
    }

    public ParticleInRadiusAction.Shape getParticleShape() {
        String str = get(MapField.PARTICLE_SHAPE);

        if (str != null && !str.isEmpty()) {
            return ParticleInRadiusAction.Shape.valueOf(str);
        } else {
            return ParticleInRadiusAction.Shape.CIRCLE;
        }
    }

    public EntityFinder.EntityPredicate getEntityPredicate() {
        return EntityFinder.EntityPredicate.valueOf(get(MapField.ENTITY_PREDICATE));
    }

    public <T> T getOrDefault(MapField<T> field, T defa) {
        return (T) map.getOrDefault(field.GUID(), defa);
    }

}
