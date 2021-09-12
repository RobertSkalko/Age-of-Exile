package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class StrSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("shred", SpellConfiguration.Builder.instant(10, 20 * 1)
                    .setSwingArm()
                    .setChargesAndRegen("shred", 3, 20 * 15), "Shred",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .manualDesc(
                "Slash all nearby enemies, dealing "
                    + SpellCalcs.SHRED.getLocSpellTooltip() + " " + Elements.Physical.getIconNameDmg()
                    + " damage and reducing their defenses."
            )

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITHER_SKELETON_HURT, 1D, 1D))

            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 50D, 2.8D, 0.5D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ENCHANTED_HIT, 25D, 2D, 0.5D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 25D, 1D, 0.5D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ENCHANTED_HIT, 50D, 1D, 0.5D))

            .onCast(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.SHRED, 10))
                .enemiesInRadius(3D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.SHRED, Elements.Physical, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ModRegistry.PARTICLES.BLOOD_EXPLODE, 15D, 0.5D, 1D)))

            .build();



        /*
        SpellBuilder.of("thirst_strike", SpellConfiguration.Builder.instant(5, 15)
                    .setSwingArm(), "Thirsting Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))

            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("thirst_strike", 0.25F, 1), Elements.Physical, 1.25D, 1.25D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 75D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.healCaster(ValueCalculation.base("thirst_strike_heal", 1)))
            )
            .build();
        */

    }
}
