package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.CommonSpellBuilders;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.NewSpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.SpellEntityBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.VanillaEffectActionBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonProjectileAction;
import com.robertx22.age_of_exile.database.data.spells.components.entity_predicates.SpellEntityPredicate;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class RangerSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        CommonSpellBuilders.trap(SpellKeys.FROST_TRAP.id, "Frost Trap", ParticleTypes.ITEM_SNOWBALL, SpellCalcs.FROST_TRAP)
            .build();
        CommonSpellBuilders.trap(SpellKeys.POISON_TRAP.id, "Poison Trap", ParticleTypes.ITEM_SLIME, SpellCalcs.POISON_TRAP)
            .build();
        CommonSpellBuilders.trap(SpellKeys.FIRE_TRAP.id, "Fire Trap", ParticleTypes.FLAME, SpellCalcs.FIRE_TRAP)
            .build();

        SpellBuilder.of(SpellKeys.EXPLOSIVE_ARROW,
                SpellConfiguration.Builder.arrowImbue(10, 20 * 10), "Explosive Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .manualDesc("Shoot an arrow that does ")
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))

            .onExpire(PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 15D, 3D))
            .onExpire(PartBuilder.Sound.play(SoundEvents.ARROW_HIT, 1D, 1D))
            .onExpire(PartBuilder.Sound.play(SoundEvents.GENERIC_EXPLODE, 2D, 1D))
            .onExpire(PartBuilder.Damage.aoe(SpellCalcs.EXPLOSIVE_ARROW, 3D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D))))

            .onTick(PartBuilder.Particles.tickAoe(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .onTick(PartBuilder.Particles.tickAoe(1D, ParticleTypes.EXPLOSION, 1D, 0.1D))
            .onTick(PartBuilder.Sound.play(SoundEvents.GENERIC_EXPLODE, 1D, 1D)
                .onTick(2D))

            .build();

        SpellBuilder.of(SpellKeys.MAKE_ARROWS, SpellConfiguration.Builder.instant(10, 20 * 60 * 5)
                , "Produce Arrows",
                Arrays.asList())
            .manualDesc("Produce a stack of arrows.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.ITEM_PICKUP))
            .onCast(PartBuilder.Sound.play(SoundRefs.DING))
            .onCast(PartBuilder.justAction(SpellAction.CASTER_USE_COMMAND.create("/give @s minecraft:arrow 64")))
            .build();

        SpellBuilder.of(SpellKeys.FROST_ARROW,
                SpellConfiguration.Builder.arrowImbue(8, 20 * 15), "Frost Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Shoot an arrow that goes through enemies and deals dmg in radius and slows.")

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.Sound.play(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                .put(MapField.PROJECTILE_SPEED, 1D)
                .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                .put(MapField.GRAVITY, false)))

            .onTick(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 50D, 0.3D))

            .onTick(PartBuilder.Damage.aoe(SpellCalcs.FROST_ARROW, 6D)
                .addEntityPredicate(SpellEntityPredicate.DID_NOT_AFFECT_BY_ENTITY.create())
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 500d, 1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.INSTANT_EFFECT, 500d, 1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.FIREWORK, 50D, 1D))
                .addPerEntityHit(PartBuilder.Sound.play(SoundRefs.HURT))
                .addPerEntityHit(PartBuilder.Sound.play(SoundRefs.EXPLOSION))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.MARK_AS_AFFECTED_BY_ENTITY.create()))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D)))
                .onTick(1D))
            .build();

        SpellBuilder.of(SpellKeys.ENDER_ARROW,
                SpellConfiguration.Builder.arrowImbue(8, 20 * 15), "Ender Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Shoot an arrow that damages an enemy and teleports you towards it.")

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                .put(MapField.GRAVITY, true)))

            .onTick(PartBuilder.Particles.aoe(ParticleTypes.WITCH, 100D, 0.1D))

            .onExpire(PartBuilder.Damage.aoe(SpellCalcs.ENDER_ARROW, 1D))
            .onExpire(PartBuilder.Particles.aoe(ParticleTypes.WITCH, 500D, 2D))
            .onExpire(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.CASTER.create()))
            .onExpire(PartBuilder.Sound.play(SoundEvents.ENDERMAN_TELEPORT))
            .build();

        SpellBuilder.of(SpellKeys.POISON_ARROWS,
                SpellConfiguration.Builder.arrowImbue(8, 20 * 15, 3), "Poison Arrows",
                Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Shoots 3 poisonous arrows in an arc, creating a field of poison when they hit an enemy.")
            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT))
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT))
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(3D)
                .put(MapField.GRAVITY, false)
                .put(MapField.PROJECTILE_SPEED, 2D)
            ))

            .onTick(PartBuilder.Particles.aoe(ParticleTypes.SNEEZE, 5D, 0.1D))

            .onHit(PartBuilder.Damage.aoe(SpellCalcs.POISON_ARROW, 2D))
            .onHit(PartBuilder.Particles.aoe(ParticleTypes.SNEEZE, 100D, 1D))
            .onHit(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 5)))
            .onHit(PartBuilder.Sound.play(SoundRefs.HURT))
            .onHit(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 5)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.Damage.aoe(SpellCalcs.POISON_ARROW, 2D)
                .onTick(30D)
                .addPerEntityHit(PartBuilder.Sound.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
            )
            .build();

        SpellBuilder.of(SpellKeys.SURVIVAL_POTION, SpellConfiguration.Builder.instant(20, 60 * 20 * 3), "Survival Potion",
                Arrays.asList(SpellTag.heal)
            )
            .manualDesc("Drink a potion, healing you")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.EFFECT, 100D, 1.5D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.HEART, 50D, 1.5D))
            .onCast(PartBuilder.Restore.Health.caster(SpellCalcs.HUNTER_POTION_HEAL))
            .build();

        SpellBuilder.of(SpellKeys.ICE_NEEDLES, SpellConfiguration.Builder.instant(8, 20 * 15), "Ice Needles",
                Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

            .manualDesc("Summon Icy Needles around you, damaging all enemies hit.")
            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)

            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT))
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT))
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT))

            .onCast(new VanillaEffectActionBuilder(Effects.MOVEMENT_SLOWDOWN).giveToSelfOnly()
                .seconds(3)
                .build())

            .onTick(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 1D, 0.3D))

            .onHit(PartBuilder.Damage.aoe(SpellCalcs.ICE_NEEDLES, 2D))

            .onHit(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 100D, 1D))
            .onHit(PartBuilder.Sound.play(SoundRefs.HURT))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 3)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(15D)
                        .put(MapField.GRAVITY, false)
                        .put(MapField.LIFESPAN_TICKS, 20D)
                        .put(MapField.PITCH_ZERO, true)
                        .put(MapField.PROJECTILES_APART, 360D)
                        .put(MapField.PROJECTILE_SPEED, 1.5D)
                        .put(MapField.GRAVITY, false)
                    )
                    .onTick(3D)
            )
            .onTick("block", PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT)
                .onTick(3D))
            .build();

        NewSpellBuilder.of(SpellKeys.POISON_DEVICE, SpellConfiguration.Builder.instant(8, 20 * 15), "Poisoner Device")

            .tags(SpellTag.projectile, SpellTag.area, SpellTag.damage)

            .desc("Summons a device that shoots out poisonous arrows.")

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)

            .onCast(PartBuilder.Sound.play(SoundEvents.ANVIL_PLACE))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)
                .put(MapField.ENTITY_NAME, "sight")
            ))

            .addEntity(SpellEntityBuilder.of("sight")
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.SMOKER, 20 * 6D)
                    .put(MapField.ENTITY_NAME, "trap")
                    .put(MapField.FIND_NEAREST_SURFACE, true)
                    .put(MapField.IS_BLOCK_FALLING, false)))
            )
            .addEntity(SpellEntityBuilder.of("trap")
                .onTick(5, PartBuilder.Sound.play(SoundEvents.WITCH_THROW))

                .onTick(5, PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(4D)
                        .put(MapField.GRAVITY, false)
                        .put(MapField.HEIGHT, 0.5)
                        .put(MapField.PITCH_ZERO, true)
                        .put(MapField.POS_SOURCE, SummonProjectileAction.PositionSource.SOURCE_ENTITY.name())
                        .put(MapField.ENTITY_NAME, "projectile")
                        .put(MapField.LIFESPAN_TICKS, 30D)
                        .put(MapField.PROJECTILES_APART, 360D)
                        .put(MapField.PROJECTILE_SPEED, 0.5D)
                    )
                )
            )
            .addEntity(SpellEntityBuilder.of("projectile")
                .onTick(PartBuilder.Particles.aoe(ParticleTypes.SNEEZE, 1D, 0.1D))
                .onExpire(PartBuilder.Damage.aoe(SpellCalcs.POISON_ARROW, 1D))
            )
            .build();
    }

}
