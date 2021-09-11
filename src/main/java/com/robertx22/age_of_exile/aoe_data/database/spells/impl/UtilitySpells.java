package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

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
import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.BLOCKS;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class UtilitySpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

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

    }
}
