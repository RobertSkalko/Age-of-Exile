package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class TankSpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("undying_will", SpellConfiguration.Builder.instant(7, 20 * 60 * 2), "Undying Will",
            Arrays.asList())
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.UNDYING_WILL, 20D * 10))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 50D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 50D, 1D))
            .build();
    }
}
