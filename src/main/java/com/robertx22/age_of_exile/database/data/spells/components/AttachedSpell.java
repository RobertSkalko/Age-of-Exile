package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachedSpell {

    HashMap<ActivationTypeData, List<ComponentPart>> components = new HashMap<>();

    List<ComponentPart> castActions = new ArrayList<>();

    public void tryActivate(ActivatedOn.ActivationType type, SpellCtx ctx) {

        for (Map.Entry<ActivationTypeData, List<ComponentPart>> entry : components.entrySet()) {
            if (entry.getKey().activationType == type) {
                entry.getValue()
                    .forEach(v -> v.tryActivate(ctx));
            }
        }

    }

}
