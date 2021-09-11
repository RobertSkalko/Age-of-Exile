package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class SongSpells implements ExileRegistryInit {

    static void song(String id, String name, EffectCtx effect) {

        SpellBuilder.of(id, SpellConfiguration.Builder.nonInstant(10, 20 * 10, 30)
                , name,
                Arrays.asList(SpellTag.area, SpellTag.song))
            .manualDesc(
                "Give a stack of " + effect.locname + " to all allies around you."
            )
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.NOTE, 50D, 3D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, effect.effectId, 20 * 30D))
            .build();
    }

    @Override
    public void registerAll() {

        song("song_of_valor", "Song of Valor", BeneficialEffects.VALOR);
        song("song_of_perseverance", "Song of Perseverance", BeneficialEffects.PERSEVERANCE);
        song("song_of_vigor", "Song of Vigor", BeneficialEffects.VIGOR);

        SpellBuilder.of("power_chord", SpellConfiguration.Builder.instant(7, 15)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Power Chord",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))
            .onHit(PartBuilder.damage(SpellCalcs.POWER_CHORD, Elements.Elemental)
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.CHARM, 6)))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
            .build();
    }
}
