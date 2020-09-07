package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class Spell {

    private String identifier;
    private List<ActivationCost> costs = new ArrayList<>();
    private AttachedSpell attached = new AttachedSpell();

    static Gson GSON = new Gson();

    public AttachedSpell getAttachedSpell(LivingEntity caster) {
        AttachedSpell calc = GSON.fromJson(GSON.toJson(this), AttachedSpell.class);
        // todo, lot player stats, and spell modifiers modify this

        return calc;

    }

    public static class Builder {

        Spell spell;

        public static Builder of(String id) {
            Builder builder = new Builder();

            Spell spell = new Spell();
            spell.identifier = id;

            return builder;

        }

        public Builder addOnCast(ComponentPart part) {
            return addEffect(ActivationTypeData.createOnCast(), part);
        }

        public Builder addEffect(ActivationTypeData data, ComponentPart comp) {
            if (!spell.attached.components.containsKey(data)) {
                spell.attached.components.put(data, new ArrayList<>());
            }

            this.spell.attached.components.get(data)
                .add(comp);
            return this;
        }

        public Spell build() {
            return spell;
        }

    }

}
