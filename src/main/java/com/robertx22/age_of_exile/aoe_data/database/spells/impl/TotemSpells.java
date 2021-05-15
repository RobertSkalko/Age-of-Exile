package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;
import java.util.List;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TotemSpells implements ISlashRegistryInit {

    static Double RADIUS = 3D;

    SpellBuilder of(Block block, String id, SpellConfiguration config, String name, List<SpellTag> tags, DefaultParticleType particle) {

        return SpellBuilder.of(id, config, name, tags)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(block, 20D * 15D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 0.5D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(40D)))

            .onTick("block", PartBuilder.particleOnTick(2D, particle, 2D, 0.5D))
            .onTick("block", PartBuilder.groundEdgeParticles(particle, 50D, RADIUS, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(20D)));

    }

    @Override
    public void registerAll() {

        of(ModRegistry.BLOCKS.BLUE_TOTEM, "astral_totem", SpellConfiguration.Builder.instant(18, 20 * 30), "Astral Totem",
            Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.WITCH)
            .onTick("block", PartBuilder.restoreManaInRadius(ValueCalculation.base("totem_mana", 5), RADIUS)
                .onTick(20D))
            .build();

        of(ModRegistry.BLOCKS.GREEN_TOTEM, "rejuv_totem", SpellConfiguration.Builder.instant(18, 20 * 30), "Rejuvenating Totem",
            Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.HAPPY_VILLAGER)
            .onTick("block", PartBuilder.healInAoe(ValueCalculation.base("totem_heal", 3), RADIUS)
                .onTick(20D))
            .build();

    }
}
