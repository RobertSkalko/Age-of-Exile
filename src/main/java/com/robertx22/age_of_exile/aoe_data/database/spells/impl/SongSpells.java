package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class SongSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("power_chord", SpellConfiguration.Builder.instant(7, 15)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Power Chord",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))
            .onHit(PartBuilder.damage(SpellCalcs.POWER_CHORD, Elements.Elemental)
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.CHARM, 6)))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
            .build();
    }
}
