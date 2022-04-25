package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NewSpellBuilder {
    Spell spell;

    public static NewSpellBuilder of(SpellKey key, SpellConfiguration config, String name) {
        NewSpellBuilder builder = new NewSpellBuilder();

        builder.spell = new Spell();
        builder.spell.identifier = key.id;
        builder.spell.config = config;
        builder.spell.locName = name;

        return builder;
    }

    public NewSpellBuilder weaponReq(CastingWeapon wep) {
        this.spell.config.castingWeapon = wep;
        return this;
    }

    public NewSpellBuilder tags(SpellTag... tags) {
        this.spell.config.tags = new ArrayList<>(Arrays.asList(tags));
        return this;
    }

    public NewSpellBuilder tags(List<SpellTag> tags) {
        this.spell.config.tags = tags;
        return this;
    }

    public NewSpellBuilder desc(String desc) {
        if (!spell.locDesc.isEmpty()) {
            throw new RuntimeException("Already set  desc!");
        }
        this.spell.locDesc = desc;
        return this;
    }

    public NewSpellBuilder addEntity(SpellEntityBuilder entity) {
        if (!spell.attached.entity_components.containsKey(entity.entity_id)) {
            spell.attached.entity_components.put(entity.entity_id, entity.list);
        } else {
            spell.attached.entity_components.get(entity.entity_id)
                .addAll(entity.list);
        }
        return this;
    }

    public NewSpellBuilder onCast(ComponentPart... comp) {
        for (ComponentPart part : comp) {
            this.spell.attached.on_cast.add(part);
            part.addActivationRequirement(EntityActivation.ON_CAST);
        }
        return this;
    }

    public Spell build() {
        Objects.requireNonNull(spell);
        this.spell.addToSerializables();
        return spell;
    }

    public Spell buildForEffect() {
        Objects.requireNonNull(spell);
        return spell;
    }
}
