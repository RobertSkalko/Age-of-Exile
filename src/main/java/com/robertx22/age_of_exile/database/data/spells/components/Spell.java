package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.EntitySavedSpellData;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Objects;

public class Spell implements IGUID {

    private String identifier;
    private AttachedSpell attached = new AttachedSpell();
    private SpellConfiguration config;

    static Gson GSON = new Gson();

    public void validate() {
        for (ComponentPart x : this.attached.getAllComponents()) {
            x.validate();
        }
    }

    transient String json;
    transient boolean isInit = false;

    public void onInit() {

        if (!isInit) {
            json = GSON.toJson(attached);
            this.isInit = true;

            this.validate();
        }
    }

    public AttachedSpell getAttachedSpell(LivingEntity caster) {

        onInit();

        JsonElement json2 = GSON.toJsonTree(attached);
        System.out.println(json2.toString());

        AttachedSpell calc = GSON.fromJson(json, AttachedSpell.class);
        // todo, lot player stats, and spell modifiers modify this

        return calc;

    }

    public void cast(LivingEntity caster) {
        AttachedSpell attached = getAttachedSpell(caster);
        EntitySavedSpellData data = EntitySavedSpellData.create(caster, this, attached);
        attached.tryActivate(Activation.ON_CAST, SpellCtx.onCast(caster, data));
    }

    @Override
    public String GUID() {
        return identifier;
    }

    public static class Builder {

        Spell spell;

        public static Builder of(String id, SpellConfiguration config) {
            Builder builder = new Builder();

            builder.spell = new Spell();
            builder.spell.identifier = id;
            builder.spell.config = config;

            return builder;

        }

        public Builder onCast(ComponentPart comp) {
            return this.addEffect(Activation.ON_CAST, comp);
        }

        public Builder onTick(ComponentPart comp) {
            return this.addEffect(Activation.ON_TICK, comp);
        }

        public Builder onExpire(ComponentPart comp) {
            return this.addEffect(Activation.ON_EXPIRE, comp);
        }

        public Builder onHit(ComponentPart comp) {
            return this.addEffect(Activation.ON_HIT, comp);
        }

        private Builder addEffect(Activation data, ComponentPart comp) {
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
