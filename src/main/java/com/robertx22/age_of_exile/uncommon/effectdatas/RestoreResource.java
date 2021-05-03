package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ModifyResourceContext;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;

import java.util.function.Predicate;

public class RestoreResource {

    public enum RestoreType {
        LEECH, REGENERATION, HEAL;
    }

    public RestoreResource(RestoreType restoreType, ResourceType resource, float amount, Predicate<DamageEffect> predicate) {
        this.resource = resource;
        this.amount = amount;
        this.predicate = predicate;
        this.restoreType = restoreType;
    }

    public RestoreResource(RestoreType restoreType, ResourceType resource, float amount) {
        this.resource = resource;
        this.amount = amount;
        this.restoreType = restoreType;
    }

    public RestoreType restoreType;
    public ResourceType resource;
    public float amount;

    public Predicate<DamageEffect> predicate = x -> true;

    public void tryRestore(DamageEffect effect) {
        if (predicate.test(effect)) {
            if (amount > 0) {
                effect.sourceData.getResources()
                    .modify(new ModifyResourceContext(effect.sourceData, effect.source, this.resource, amount,
                        ResourcesData.Use.RESTORE
                    ));
            }
        }
    }
}
