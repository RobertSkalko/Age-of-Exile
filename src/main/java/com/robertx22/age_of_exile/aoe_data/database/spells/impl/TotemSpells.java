package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;
import java.util.List;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TotemSpells implements ExileRegistryInit {

    static Double RADIUS = 3D;

    public static String MANA_TOTEM_ID = "astral_totem";
    public static String HEAL_TOTEM_ID = "rejuv_totem";
    public static String GUARD_TOTEM_ID = "guard_totem";

    SpellBuilder of(Block block, String id, SpellConfiguration config, String name, List<SpellTag> tags, DefaultParticleType particle) {

        return SpellBuilder.of(id, config, name, tags)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(block, 20D * 7.5D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 0.5D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(20D)))

            .onTick("block", PartBuilder.particleOnTick(2D, particle, 2D, 0.5D))
            .onTick("block", PartBuilder.groundEdgeParticles(particle, 50D, RADIUS, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(20D)));

    }

    @Override
    public void registerAll() {

        of(ModRegistry.BLOCKS.GUARD_TOTEM, GUARD_TOTEM_ID, SpellConfiguration.Builder.instant(18, 20 * 30), "Guarding Totem",
            Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.EFFECT)
            .manualDesc(
                "Summon a totem which gives " + SpellCalcs.TOTEM_GUARD.getLocSpellTooltip() + " shield to allies around it."
            )
            .onTick("block", PartBuilder.justAction(SpellAction.GIVE_SHIELD.create(SpellCalcs.TOTEM_GUARD, 10D))
                .addTarget(TargetSelector.AOE.alliesInRadius(3D))
                .onTick(20D))
            .build();

        of(ModRegistry.BLOCKS.BLUE_TOTEM, MANA_TOTEM_ID, SpellConfiguration.Builder.instant(18, 20 * 30), "Astral Totem",
            Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.WITCH)
            .manualDesc(
                "Summon a totem which restores " + SpellCalcs.TOTEM_MANA.getLocSpellTooltip() + " mana to allies around it."
            )
            .onTick("block", PartBuilder.restoreManaInRadius(SpellCalcs.TOTEM_MANA, RADIUS)
                .onTick(20D))
            .build();

        of(ModRegistry.BLOCKS.GREEN_TOTEM, HEAL_TOTEM_ID, SpellConfiguration.Builder.instant(18, 20 * 30), "Rejuvenating Totem",
            Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.HAPPY_VILLAGER)

            .manualDesc(
                "Summon a totem which restores " + SpellCalcs.TOTEM_HEAL.getLocSpellTooltip() + " health to allies around it."
            )
            .onTick("block", PartBuilder.healInAoe(SpellCalcs.TOTEM_HEAL, RADIUS)
                .onTick(20D))
            .build();

    }
}
