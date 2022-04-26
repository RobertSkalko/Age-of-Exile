package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;
import java.util.List;

// todo add stuff here
public class CommonSpellBuilders {
    public static void buffSongSpell(String id, String name, EffectCtx effect) {

        SpellBuilder.of(id, SpellConfiguration.Builder.instant(10, 20 * 10)
                , name,
                Arrays.asList(SpellTag.area, SpellTag.song))
            .manualDesc(
                "Give a stack of " + effect.locname + " to all allies around you."
            )
            .onCast(PartBuilder.Sound.play(SoundEvents.NOTE_BLOCK_CHIME, 1D, 1D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.NOTE, 50D, 3D))

            .onCast(new ExileEffectActionBuilder(effect).radius(8)
                .seconds(30)
                .build())

            .build();
    }

    public static NewSpellBuilder buffSelfSpell(SpellKey key,
                                                SpellConfiguration config,
                                                String name,
                                                EffectCtx ctx,
                                                int seconds) {

        return NewSpellBuilder.of(key, config, name)
            .desc("Give self effect:")
            .onCast(PartBuilder.Sound.play(SoundRefs.DING_LOW_PITCH))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.ENCHANTED_HIT, 150D, 2D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.CRIT, 25D, 2D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.EFFECT, 100D, 2D))

            .onCast(new ExileEffectActionBuilder(ctx).giveToSelfOnly()
                .seconds(seconds)
                .build());
    }

    public static NewSpellBuilder buffAlliesSpell(SpellKey key,
                                                  SpellConfiguration config,
                                                  String name,
                                                  EffectCtx ctx,
                                                  int seconds) {

        return NewSpellBuilder.of(key, config, name)
            .desc("Give allies effect:")
            .onCast(PartBuilder.Sound.play(SoundRefs.DING_LOW_PITCH))

            .onCast(PartBuilder.Particles.aoe(ParticleTypes.ENCHANTED_HIT, 250D, 3D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.CRIT, 55D, 3D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.EFFECT, 200D, 3D))

            .onCast(new ExileEffectActionBuilder(ctx).radius(8)
                .seconds(seconds)
                .build());

    }

    public static Double DEFAULT_TOTEM_RADIUS = 3D;

    public static NewSpellBuilder totemSpell(Block block, SpellKey key, SpellConfiguration config, String name, List<SpellTag> tags, BasicParticleType particle) {

        return NewSpellBuilder.of(key, config, name)
            .tags(tags)
            .onCast(PartBuilder.Sound.play(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

            .addEntity(SpellEntityBuilder.defaultId()
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(block, 20D * 7.5D)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, false)))
            )

            .addEntity(SpellEntityBuilder.of("block")
                .onTick(PartBuilder.Sound.play(SoundEvents.NOTE_BLOCK_CHIME, 0.5D, 1D)
                    .addCondition(EffectCondition.EVERY_X_TICKS.create(20D)))
                .onTick(PartBuilder.Particles.tickAoe(2D, particle, 2D, 0.5D))
                .onTick(PartBuilder.Particles.groundEdge(particle, 50D, DEFAULT_TOTEM_RADIUS, 0.5D)
                    .addCondition(EffectCondition.EVERY_X_TICKS.create(20D))))

            ;

    }

    public static void curse(SpellKey key, String name, EffectCtx effect, int seconds) {
        NewSpellBuilder.of(key, SpellConfiguration.Builder.nonInstant(10, 20 * 30, 20)
                , name)
            .tags(SpellTag.area, SpellTag.curse)
            .desc("Curse enemies with " + effect.locname + " and deal dmg")

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

            .addEntity(SpellEntityBuilder.defaultId()
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 1D)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, false)))
            )

            .addEntity(SpellEntityBuilder.of("block")
                .onExpire(PartBuilder.Sound.play(SoundEvents.WITHER_SKELETON_HURT, 1D, 1D))
                .onExpire(PartBuilder.Damage.aoe(SpellCalcs.CURSE, 3D))

                .onExpire(new ExileEffectActionBuilder(effect).seconds(seconds)
                    .radius(3)
                    .targetEnemies()
                    .build()
                    .addPerEntityHit(
                        PartBuilder.justAction(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.SMOKE, 100D, 0.4D)
                            .put(MapField.HEIGHT, 2.2D)
                        )))
            )

            .build();
    }

}
