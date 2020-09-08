package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachedSpell {

    HashMap<Activation, List<ComponentPart>> components = new HashMap<>();

    public void tryActivate(Activation type, SpellCtx ctx) {

        for (Map.Entry<Activation, List<ComponentPart>> entry : components.entrySet()) {
            if (entry.getKey() == type) {
                entry.getValue()
                    .forEach(v -> v.tryActivate(ctx));
            }
        }

    }

    public List<ComponentPart> getAllComponents() {
        List<ComponentPart> list = new ArrayList<>();
        for (Map.Entry<Activation, List<ComponentPart>> entry : components.entrySet()) {
            list.addAll(entry.getValue());
        }
        return list;
    }

}
