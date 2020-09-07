package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachedSpell {

    HashMap<ActivatedOn.Activation, List<ComponentPart>> components = new HashMap<>();

    public void tryActivate(ActivatedOn.Activation type, SpellCtx ctx) {

        for (Map.Entry<ActivatedOn.Activation, List<ComponentPart>> entry : components.entrySet()) {
            if (entry.getKey() == type) {
                entry.getValue()
                    .forEach(v -> v.tryActivate(ctx));
            }
        }

    }

}
