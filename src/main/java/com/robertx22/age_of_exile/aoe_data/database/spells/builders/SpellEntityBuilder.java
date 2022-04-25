package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;

import java.util.ArrayList;
import java.util.List;

public class SpellEntityBuilder {

    public List<ComponentPart> list = new ArrayList<>();
    public String entity_id;

    private SpellEntityBuilder(String entity_id) {
        this.entity_id = entity_id;
    }

    public static SpellEntityBuilder of(String entity_id) {
        return new SpellEntityBuilder(entity_id);
    }

    public static SpellEntityBuilder defaultId() {
        return new SpellEntityBuilder(Spell.DEFAULT_EN_NAME);
    }

    public SpellEntityBuilder onExpire(ComponentPart... comps) {
        for (ComponentPart comp : comps) {
            comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
            list.add(comp);
        }
        return this;
    }

    public SpellEntityBuilder onHit(ComponentPart... comps) {
        for (ComponentPart comp : comps) {
            comp.addActivationRequirement(EntityActivation.ON_HIT);
            list.add(comp);
        }
        return this;
    }

    public SpellEntityBuilder onTick(int ticks, ComponentPart... comps) {
        for (ComponentPart comp : comps) {
            comp.addActivationRequirement(EntityActivation.ON_TICK);
            comp.addCondition(EffectCondition.EVERY_X_TICKS.create((double) ticks));
            list.add(comp);
        }
        return this;
    }

    public SpellEntityBuilder onTick(ComponentPart... comps) {
        return this.onTick(1, comps);
    }

}
