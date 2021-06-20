package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.BLOCKS;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class UtilitySpells implements ExileRegistryInit {
    public static String DASH_ID = "dash";

    @Override
    public void registerAll() {

        SpellBuilder.of("mage_circle", SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Mage Circle", Arrays.asList(SpellTag.movement))

            .manualDesc(
                "Summon a Magic Circle. Standing in it provides you a buff." +
                    " After a certain duration you will be teleported to its location.")

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 10)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.CASTER.create()))
            .onExpire(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))

            .onTick("block", PartBuilder.giveSelfExileEffect(BeneficialEffects.MAGE_CIRCLE, 20D)
                .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.alliesInRadius(2D)))

            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 3D, 1.2D, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
            .build();

        SpellBuilder.of(DASH_ID, SpellConfiguration.Builder.instant(10, 15)
                    .setScaleManaToPlayer()
                    .setChargesAndRegen("dash", 3, 20 * 30)
                , "Dash",
                Arrays.asList(SpellTag.movement, SpellTag.technique))

            .manualDesc(
                "Dash in your direction and gain slowfall.")

            .weaponReq(CastingWeapon.NON_MAGE_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.CASTER_USE_COMMAND.create("effect give @p minecraft:slow_falling 1 1 true")))
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1.6D)
                .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.POOF, 20D, 1D)
                    .put(MapField.Y_RANDOM, 0.5D)
                    .put(MapField.MOTION, ParticleMotion.CasterLook.name())
                    .put(MapField.SET_ADD, SetAdd.ADD.name()))
                .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.WHITE_ASH, 20D, 1D)
                    .put(MapField.Y_RANDOM, 0.5D)
                    .put(MapField.MOTION, ParticleMotion.CasterLook.name())
                    .put(MapField.SET_ADD, SetAdd.ADD.name())))

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 1D, ParticleMotion.CasterLook))
                .addTarget(TargetSelector.CASTER.create()))
            .build();

        SpellBuilder.of("conjure_ender_chest", SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 2, 40)
                    .setScaleManaToPlayer(),
                "Conjure Ender Chest",
                Arrays.asList())
            .manualDesc(
                "Allows you to access your Ender Chest from any place.")

            .attackStyle(PlayStyle.magic)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.PORTAL, 100D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.OPEN_ENDER_CHEST.create()))
            .disableInDimension(DimensionIds.DUNGEON_DIMENSION)
            .build();

        SpellBuilder.of("water_breath", SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 5, 40)
                    .setScaleManaToPlayer(),
                "Water Breathing",
                Arrays.asList())
            .manualDesc(
                "Give Water Breathing to allies around you.")

            .attackStyle(PlayStyle.magic)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_SPLASH, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 3D))
            .onCast(PartBuilder.giveEffectToAlliesInRadius(StatusEffects.WATER_BREATHING, 20D * 60D * 3, 5D))
            .build();

        SpellBuilder.of("night_vision", SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 5, 40)
                    .setScaleManaToPlayer(),
                "Night Vision",
                Arrays.asList())
            .manualDesc(
                "Give Night Vision to allies around you.")

            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_LINGERING_POTION_THROW, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 100D, 3D))
            .onCast(PartBuilder.giveEffectToAlliesInRadius(StatusEffects.NIGHT_VISION, 20D * 60D * 3, 5D))
            .build();

        SpellBuilder.of("banish", SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Banish", Arrays.asList())
            .manualDesc(
                "Summon a Magic circle that banishes enemies in the area, levitating them for a certain duration.")

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 5)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.SET, 0.1D, ParticleMotion.Upwards))
                .onTick(1D)
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))

            .onTick("block", PartBuilder.playSound(SoundEvents.BLOCK_SOUL_SOIL_HIT, 0.5D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(40D)))

            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.SOUL_FIRE_FLAME, 15D, 3D, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
            .build();

        SpellBuilder.of("jump_field", SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Jump Field", Arrays.asList(SpellTag.movement))
            .manualDesc(
                "Summon a Jump Field, stepping on it will propel you upwards at high speeds.")

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 5)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.SET, 2D, ParticleMotion.Upwards))
                .onTick(1D)
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies))
            )
            .onTick("block", PartBuilder.playSound(SoundEvents.BLOCK_SOUL_SOIL_HIT, 0.5D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(40D)))
            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 15D, 3D, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
            .build();

        SpellBuilder.of("levitation", SpellConfiguration.Builder.instant(1, 1)
                    .setScaleManaToPlayer(), "Levitation",
                Arrays.asList(SpellTag.movement))

            .manualDesc(
                "Levitate in the air, but you can't move around besides upwards.")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_SMOKER_SMOKE, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.SET, 0.3D, ParticleMotion.Upwards))
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.particleOnTick(1D, ParticleTypes.SOUL, 5D, 0.5D))
            .onCast(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 5D, 0.5D))
            .build();

        SpellBuilder.of("refresh", SpellConfiguration.Builder.nonInstant(40, 20 * 60 * 3, 20)
                    .setScaleManaToPlayer(), "Refresh",
                Arrays.asList())

            .manualDesc(
                "Refreshes all your spell cooldowns by 1 minute.")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(ModRegistry.SOUNDS.FREEZE, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.REFRESH_COOLDOWNS_BY_X_TICKS.create(20 * 60D))
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.DRIPPING_WATER, 50D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 1.5D))
            .build();
    }
}
