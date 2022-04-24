package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class WarriorSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of(SpellKeys.METEOR_STRIKE, SpellConfiguration.Builder.onJumpCritImbue(8, 20 * 30, 3)
                , "Meteor Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you.")
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.playSound(SoundRefs.EXPLOSION))
            .onCast(PartBuilder.swordSweepParticles())

            .onCast(PartBuilder.damageInFront(SpellCalcs.METEOR_STRIKE, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 1D))
            )
            .build();

        SpellBuilder.of(SpellKeys.TIDAL_WAVE, SpellConfiguration.Builder.onJumpCritImbue(12, 20 * 45, 5)
                , "Tidal Wave",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you")
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.GENERIC_SPLASH))

            .onCast(PartBuilder.swordSweepParticles())

            .onCast(PartBuilder.damageInFront(SpellCalcs.TIDAL_WAVE, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.DRIPPING_WATER, 30D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.DRIPPING_WATER, 50D, 1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_HURT))
            )

            .build();

        SpellBuilder.of(SpellKeys.VENOM_STRIKE, SpellConfiguration.Builder.onJumpCritImbue(14, 20 * 45, 3)
                , "Venom Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you and leave a poison trail.")
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.GENERIC_SPLASH))

            .onCast(PartBuilder.swordSweepParticles())

            .onCast(PartBuilder.damageInFront(SpellCalcs.VENOM_STRIKE, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SNEEZE, 30D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.SNEEZE, 50D, 1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_HURT))

                .addPerEntityHit(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 5)))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 5)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, true)
                    .put(MapField.IS_BLOCK_FALLING, false)))

            )
            .onTick("block", PartBuilder.groundParticles(ParticleTypes.SNEEZE, 10D, 2D, 0.2D))
            .onTick("block", PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, 3D)
                .onTick(20D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
            )

            .build();

    }
}
