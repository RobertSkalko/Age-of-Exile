package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

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

    public Elements getElement() {
        return Elements.valueOf(get(MapField.ELEMENT));
    }

    public DefaultParticleType getParticle() {
        return (DefaultParticleType) Registry.PARTICLE_TYPE.get(new Identifier(get(MapField.PARTICLE_TYPE)));
    }

    public SoundEvent getSound() {
        return Registry.SOUND_EVENT.get(new Identifier(get(MapField.SOUND)));
    }

    public EntityFinder.SelectionType getSelectionType() {
        return EntityFinder.SelectionType.valueOf(get(MapField.SELECTION_TYPE));
    }

    public EntityFinder.EntityPredicate getEntityPredicate() {
        return EntityFinder.EntityPredicate.valueOf(get(MapField.ENTITY_PREDICATE));
    }

    public <T> T getOrDefault(MapField<T> field, T defa) {
        return (T) map.getOrDefault(field.GUID(), defa);
    }

}
