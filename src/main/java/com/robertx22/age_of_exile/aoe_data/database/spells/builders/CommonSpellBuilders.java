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
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
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

    public static SpellBuilder trap(String id, String name, BasicParticleType particle, ValueCalculation dmg) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(7, 100), name,
                Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .manualDesc(
                "Throw out a trap that stays on the ground and activates when an enemy approaches to deal damage in area around itself."
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundRefs.FISHING_THROW))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.TRIPWIRE_HOOK, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 100D, true)))

            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.TRAP.get(), 20 * 4D)
                .put(MapField.ENTITY_NAME, "trap")
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("trap", PartBuilder.Particles.aoe(particle, 5D, 0.5D)
                .onTick(2D))

            .onExpire("trap", PartBuilder.Damage.aoe(dmg, 3D))
            .onExpire("trap", PartBuilder.Particles.aoe(particle, 300D, 3D))
            .onExpire("trap", PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 3D, 0.5D))
            .onExpire("trap", PartBuilder.Particles.aoe(ParticleTypes.SMOKE, 100D, 3D))
            .onExpire("trap", PartBuilder.Sound.play(SoundEvents.GENERIC_EXPLODE, 1D, 1D));

    }
}
