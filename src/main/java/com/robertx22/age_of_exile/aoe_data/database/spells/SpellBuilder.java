package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SpellBuilder {
    Spell spell;

    public static SpellBuilder breath(String id, String name, Elements ele, BasicParticleType particle) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(2, 1), name,
                Arrays.asList(SpellTag.damage))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                .put(MapField.IS_SILENT, true)))
            .onHit(PartBuilder.damageInAoe(SpellCalcs.BREATH, ele, 1.5D)
                .addCondition(EffectCondition.IS_NOT_ON_COOLDOWN.create("breath"))
                .addActions(SpellAction.SET_ON_COOLDOWN.create("breath", 20D)))
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

    public SpellBuilder disableInDimension(ResourceLocation id) {
        this.spell.disabled_dims.add(id.toString());
        return this;
    }

    public SpellBuilder onCast(ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this;
    }

    public SpellBuilder teleportForward() {
        this.onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 1D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))
            .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.CASTER.create()));
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

        if (!spell.locDesc.isEmpty()) {
            throw new RuntimeException("Already set manual desc!");
        }

        this.spell.manual_tip = true;
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
