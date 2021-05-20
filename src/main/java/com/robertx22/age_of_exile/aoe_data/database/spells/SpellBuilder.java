package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.*;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class SpellBuilder {
    Spell spell;

    public static SpellBuilder breath(String id, String name, Elements ele, DefaultParticleType particle) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(2, 1), name,
            Arrays.asList(SpellTag.damage))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)
                .put(MapField.IS_SILENT, true))
                .addCondition(EffectCondition.CHANCE.create(20D)))
            .onHit(PartBuilder.damageInAoe(ValueCalculation.base(id, 2), ele, 1.5D))
            .onCast(PartBuilder.Particle.builder(particle, 50D, 0.3D)
                .set(MapField.MOTION, ParticleMotion.CasterLook.name())
                .set(MapField.HEIGHT, 1D)
                .build());
    }

    public static SpellBuilder of(String id, SpellConfiguration config, String name, List<SpellTag> tags) {
        SpellBuilder builder = new SpellBuilder();

        builder.spell = new Spell();
        builder.spell.identifier = id;
        builder.spell.config = config;
        builder.spell.locName = name;

        builder.spell.getConfig().tags = tags;

        return builder;
    }

    public static SpellBuilder aura(PlayStyle style, String id, String name, AuraSpellData aura) {
        SpellBuilder builder = new SpellBuilder();

        builder.spell = new Spell();
        builder.spell.identifier = id;
        builder.spell.config = SpellConfiguration.Builder.instant(0, 5);
        builder.spell.locName = name;
        builder.spell.config.castingWeapon = CastingWeapon.ANY_WEAPON;
        builder.spell.config.style = style;
        builder.spell.aura_data = aura;

        builder.spell.config.tags.add(SpellTag.aura);

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

    public SpellBuilder attackStyle(PlayStyle style) {
        this.spell.config.style = style;
        return this;
    }

    public SpellBuilder weaponReq(CastingWeapon wep) {
        this.spell.config.castingWeapon = wep;
        return this;
    }

    public SpellBuilder weight(int w) {
        this.spell.weight = w;
        return this;
    }

    public SpellBuilder addStat(StatModifier stat) {
        this.spell.statsForSkillGem.add(stat);
        return this;
    }

    public SpellBuilder disableInDimension(Identifier id) {
        this.spell.disabled_dims.add(id.toString());
        return this;
    }

    public SpellBuilder onCast(ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this;
    }

    public SpellBuilder onTick(ComponentPart comp) {
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder addSpecificAction(String id, ComponentPart comp) {
        this.addEntityAction(id, comp);
        return this;
    }

    public SpellBuilder onExpire(ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder onHit(ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_HIT);
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder manualDesc(String desc) {
        this.spell.locDesc = desc;
        return this;
    }

    public SpellBuilder onTick(String entity, ComponentPart comp) {
        return this.addEntityAction(entity, comp);
    }

    public SpellBuilder onCast(String entity, ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this.addEntityAction(entity, comp);
    }

    public SpellBuilder onExpire(String entity, ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
        return this.addEntityAction(entity, comp);
    }

    public SpellBuilder onHit(String entity, ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_HIT);
        return this.addEntityAction(entity, comp);
    }

    private SpellBuilder addEntityAction(String entity, ComponentPart comp) {
        Objects.requireNonNull(comp);

        if (!spell.attached.entity_components.containsKey(entity)) {
            spell.attached.entity_components.put(entity, new ArrayList<>());
        }

        this.spell.attached.getDataForEntity(entity)
            .add(comp);

        return this;
    }

    public SpellBuilder addEffectToTooltip(EffectCtx eff) {
        this.spell.effect_tip = eff.effectId;
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
