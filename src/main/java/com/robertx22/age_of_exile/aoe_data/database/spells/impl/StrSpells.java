package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class StrSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("shred", SpellConfiguration.Builder.instant(10, 20 * 1)
                    .setSwingArm()
                , "Shred",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .manualDesc(
                "Slash all nearby enemies, dealing  damage and reducing their defenses."
            )

            .onCast(PartBuilder.Sound.play(SoundEvents.WITHER_SKELETON_HURT, 1D, 1D))

            .onCast(PartBuilder.Particles.swordSweep())
            .onCast(PartBuilder.Particles.groundEdge(ParticleTypes.CRIT, 50D, 2.8D, 0.5D))
            .onCast(PartBuilder.Particles.groundEdge(ParticleTypes.ENCHANTED_HIT, 25D, 2D, 0.5D))
            .onCast(PartBuilder.Particles.groundEdge(ParticleTypes.CRIT, 25D, 1D, 0.5D))
            .onCast(PartBuilder.Particles.groundEdge(ParticleTypes.ENCHANTED_HIT, 50D, 1D, 0.5D))

            .onCast(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.SHRED, 10))
                .enemiesInRadius(3D))
            .onCast(PartBuilder.Damage.aoe(SpellCalcs.SHRED, 3D)
                .addPerEntityHit(PartBuilder.Particles.groundEdge(ParticleTypes.ENCHANTED_HIT, 15D, 0.5D, 1D)))

            .build();

    }
}
