package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.EntitySavedSpellData;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
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
        attached.onCast(SpellCtx.onCast(caster, data));
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
            this.spell.attached.on_cast.add(comp);
            return this;
        }

        public static String DEFAULT_EN_NAME = "default_entity_name";

        public Builder onTick(ComponentPart comp) {
            return this.addEffect(DEFAULT_EN_NAME, EntityActivation.ON_TICK, comp);
        }

        public Builder onExpire(ComponentPart comp) {
            return this.addEffect(DEFAULT_EN_NAME, EntityActivation.ON_EXPIRE, comp);
        }

        public Builder onHit(ComponentPart comp) {
            return this.addEffect(DEFAULT_EN_NAME, EntityActivation.ON_HIT, comp);
        }

        public Builder onTick(String entity, ComponentPart comp) {
            return this.addEffect(entity, EntityActivation.ON_TICK, comp);
        }

        public Builder onExpire(String entity, ComponentPart comp) {
            return this.addEffect(entity, EntityActivation.ON_EXPIRE, comp);
        }

        public Builder onHit(String entity, ComponentPart comp) {
            return this.addEffect(entity, EntityActivation.ON_HIT, comp);
        }

        private Builder addEffect(String entity, EntityActivation data, ComponentPart comp) {
            Objects.requireNonNull(data);
            Objects.requireNonNull(comp);

            if (!spell.attached.entity_components.containsKey(entity)) {
                spell.attached.entity_components.put(entity, new HashMap<>());
            }

            if (!spell.attached.entity_components.get(entity)
                .containsKey(data)) {
                spell.attached.entity_components.get(entity)
                    .put(data, new ArrayList<>());
            }

            this.spell.attached.getDataForEntity(entity)
                .get(data)
                .add(comp);

            return this;
        }

        public Spell build() {
            Objects.requireNonNull(spell);
            return spell;
        }

    }

}
