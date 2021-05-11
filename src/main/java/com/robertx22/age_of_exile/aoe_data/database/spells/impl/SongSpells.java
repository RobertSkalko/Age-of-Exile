package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class SongSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("song_of_valor", SpellConfiguration.Builder.nonInstant(10, 20 * 10, 20 * 2)
            , "Song of Valor",
            Arrays.asList(SpellTag.area, SpellTag.song))
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.NOTE, 50D, 3D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(3D, BeneficialEffects.VALOR.effectId, 20 * 15D))
            .build();

        SpellBuilder.of("power_chord", SpellConfiguration.Builder.instant(7, 15)
                .setSwingArm(), "Power Chord",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculation.base("power_chord", 7), Elements.Elemental))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
            .build();
    }
}
