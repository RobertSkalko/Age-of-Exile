package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.components.activated_on.ActivatedOn;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.EntitySavedSpellData;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Spell implements IGUID {

    private String identifier;
    private List<ActivationCost> costs = new ArrayList<>();
    private AttachedSpell attached = new AttachedSpell();

    static Gson GSON = new Gson();

    public AttachedSpell getAttachedSpell(LivingEntity caster) {

        String json = GSON.toJson(attached);
        JsonElement json2 = GSON.toJsonTree(attached);

        System.out.println(json2.toString());

        AttachedSpell calc = GSON.fromJson(json, AttachedSpell.class);
        // todo, lot player stats, and spell modifiers modify this

        return calc;

    }

    public void cast(LivingEntity caster) {
        AttachedSpell attached = getAttachedSpell(caster);
        EntitySavedSpellData data = EntitySavedSpellData.create(this, attached);
        attached.tryActivate(ActivatedOn.Activation.ON_CAST, SpellCtx.onCast(caster, data));
    }

    @Override
    public String GUID() {
        return identifier;
    }

    public static class Builder {

        Spell spell;

        public static Builder of(String id) {
            Builder builder = new Builder();

            builder.spell = new Spell();
            builder.spell.identifier = id;

            return builder;

        }

        public Builder addEffect(ActivatedOn.Activation data, ComponentPart comp) {
            Objects.requireNonNull(data);
            Objects.requireNonNull(comp);

            if (!spell.attached.components.containsKey(data)) {
                spell.attached.components.put(data, new ArrayList<>());
            }

            this.spell.attached.components.get(data)
                .add(comp);
            return this;
        }

        public Spell build() {
            Objects.requireNonNull(spell);
            return spell;
        }

    }

}
