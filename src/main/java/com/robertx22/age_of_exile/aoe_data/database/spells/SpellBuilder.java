package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.components.*;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellBuilder {
    Spell spell;

    public static SpellBuilder of(String id, SpellConfiguration config, String name, List<SkillGemTag> tags) {
        SpellBuilder builder = new SpellBuilder();

        builder.spell = new Spell();
        builder.spell.identifier = id;
        builder.spell.config = config;
        builder.spell.locName = name;

        builder.spell.getConfig().tags = tags;

        return builder;
    }

    public static SpellBuilder aura(AttackPlayStyle style, String id, String name, AuraSpellData aura) {
        SpellBuilder builder = new SpellBuilder();

        builder.spell = new Spell();
        builder.spell.identifier = id;
        builder.spell.config = SpellConfiguration.Builder.instant(0, 5);
        builder.spell.locName = name;
        builder.spell.config.castingWeapon = CastingWeapon.ANY_WEAPON;
        builder.spell.config.style = style;
        builder.spell.aura_data = aura;

        builder.spell.config.tags.add(SkillGemTag.AURA);

        return builder;
    }

    public static SpellBuilder forEffect() {
        SpellBuilder builder = new SpellBuilder();
        builder.spell = new Spell();
        builder.spell.identifier = "";
        builder.spell.config = new SpellConfiguration();
        builder.spell.locName = "";

        return builder;
    }

    public SpellBuilder attackStyle(AttackPlayStyle style) {
        this.spell.config.style = style;
        return this;
    }

    public SpellBuilder weaponReq(CastingWeapon wep) {
        this.spell.config.castingWeapon = wep;
        return this;
    }

    public SpellBuilder onCast(ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this;
    }

    public SpellBuilder onTick(ComponentPart comp) {
        return this.addEffect(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder onExpire(ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
        return this.addEffect(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder onHit(ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_HIT);
        return this.addEffect(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder onTick(String entity, ComponentPart comp) {
        return this.addEffect(entity, comp);
    }

    public SpellBuilder onCast(String entity, ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this.addEffect(entity, comp);
    }

    public SpellBuilder onExpire(String entity, ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
        return this.addEffect(entity, comp);
    }

    public SpellBuilder onHit(String entity, ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_HIT);
        return this.addEffect(entity, comp);
    }

    private SpellBuilder addEffect(String entity, ComponentPart comp) {
        Objects.requireNonNull(comp);

        if (!spell.attached.entity_components.containsKey(entity)) {
            spell.attached.entity_components.put(entity, new ArrayList<>());
        }

        this.spell.attached.getDataForEntity(entity)
            .add(comp);

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
