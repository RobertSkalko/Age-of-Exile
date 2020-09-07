package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;

import java.util.HashMap;

public class MapHolder {

    public String type;

    private HashMap<String, Object> map = new HashMap<>();

    private ValueCalculationData calc = null;

    public <T> void put(MapField<T> field, T t) {
        if (field == MapField.VALUE_CALCULATION) {
            calc = (ValueCalculationData) t;
            return;
        }

        this.map.put(field.GUID(), t);
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
