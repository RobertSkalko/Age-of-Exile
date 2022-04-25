package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.CommonSpellBuilders;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.SpellEntityBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class ShamanSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        CommonSpellBuilders.curse(SpellKeys.CURSE_OF_AGONY, "Curse of Agony", NegativeEffects.AGONY, 15);
        CommonSpellBuilders.curse(SpellKeys.CURSE_OF_WEAKNESS, "Curse of Weakness", NegativeEffects.WEAKNESS, 20);
        CommonSpellBuilders.curse(SpellKeys.CURSE_OF_DESPAIR, "Curse of Despair", NegativeEffects.DESPAIR, 5);

        SpellBuilder.of(SpellKeys.METEOR_STRIKE, SpellConfiguration.Builder.onJumpCritImbue(8, 20 * 30, 3)
                , "Meteor Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you.")
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.Sound.play(SoundRefs.EXPLOSION))
            .onCast(PartBuilder.Particles.swordSweep())

            .onCast(PartBuilder.Damage.inFront(SpellCalcs.METEOR_STRIKE, 2D, 3D)
                .addPerEntityHit(PartBuilder.Particles.groundEdge(ParticleTypes.FLAME, 45D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 15D, 1D))
            )
            .build();

        SpellBuilder.of(SpellKeys.TIDAL_WAVE, SpellConfiguration.Builder.onJumpCritImbue(12, 20 * 45, 5)
                , "Tidal Wave",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you")
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.GENERIC_SPLASH))

            .onCast(PartBuilder.Particles.swordSweep())

            .onCast(PartBuilder.Damage.inFront(SpellCalcs.TIDAL_WAVE, 2D, 3D)
                .addPerEntityHit(PartBuilder.Particles.groundEdge(ParticleTypes.DRIPPING_WATER, 30D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.DRIPPING_WATER, 50D, 1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 1D, 1D))
                .addPerEntityHit(PartBuilder.Sound.play(SoundEvents.GENERIC_HURT))
            )

            .build();

        SpellBuilder.of(SpellKeys.VENOM_STRIKE, SpellConfiguration.Builder.onJumpCritImbue(14, 20 * 45, 3)
                , "Venom Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you and leave a poison trail.")
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.GENERIC_SPLASH))

            .onCast(PartBuilder.Particles.swordSweep())

            .onCast(PartBuilder.Damage.inFront(SpellCalcs.VENOM_STRIKE, 2D, 3D)
                .addPerEntityHit(PartBuilder.Particles.groundEdge(ParticleTypes.SNEEZE, 30D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.SNEEZE, 50D, 1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 1D, 1D))
                .addPerEntityHit(PartBuilder.Sound.play(SoundEvents.GENERIC_HURT))

                .addPerEntityHit(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 5)))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 5)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, true)
                    .put(MapField.IS_BLOCK_FALLING, false)))

            )
            .onTick("block", PartBuilder.Particles.ground(ParticleTypes.SNEEZE, 10D, 2D, 0.2D))
            .onTick("block", PartBuilder.Damage.aoe(SpellCalcs.POISON_CLOUD, 3D)
                .onTick(20D)
                .addPerEntityHit(PartBuilder.Sound.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
            )

            .build();

        CommonSpellBuilders.totemSpell(SlashBlocks.GUARD_TOTEM.get(), SpellKeys.DAMAGE_TOTEM, SpellConfiguration.Builder.instant(18, 20 * 30), "Damage Totem",
                Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.WITCH)
            .desc(
                "Summon a totem which damages enemies around it."
            )
            .addEntity(SpellEntityBuilder.of("block")
                .onTick(20, PartBuilder.Damage.aoe(SpellCalcs.DAMAGE_TOTEM, CommonSpellBuilders.DEFAULT_TOTEM_RADIUS)
                    .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.FLAME, 30D, 1D)
                        .addPerEntityHit(PartBuilder.Sound.play(SoundEvents.FIRE_EXTINGUISH))
                    )
                )
            )
            .build();

        CommonSpellBuilders.totemSpell(SlashBlocks.BLUE_TOTEM.get(), SpellKeys.MANA_TOTEM, SpellConfiguration.Builder.instant(18, 20 * 30), "Astral Totem",
                Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.WITCH)
            .desc(
                "Summon a totem which restores  mana to allies around it."
            )
            .addEntity(SpellEntityBuilder.of("block")
                .onTick(20, PartBuilder.Restore.Mana.aoe(SpellCalcs.TOTEM_MANA, CommonSpellBuilders.DEFAULT_TOTEM_RADIUS))
            )
            .build();

        CommonSpellBuilders.totemSpell(SlashBlocks.GREEN_TOTEM.get(), SpellKeys.HEAL_TOTEM, SpellConfiguration.Builder.instant(18, 20 * 30), "Rejuvenating Totem",
                Arrays.asList(SpellTag.totem, SpellTag.area), ParticleTypes.HAPPY_VILLAGER)
            .desc(
                "Summon a totem which restores  health to allies around it."
            )
            .addEntity(SpellEntityBuilder.of("block")
                .onTick(20, PartBuilder.Restore.Health.aoe(SpellCalcs.TOTEM_HEAL, CommonSpellBuilders.DEFAULT_TOTEM_RADIUS))
            )
            .build();

    }
}
