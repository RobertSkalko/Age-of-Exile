package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ExileEffectActionBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.VanillaEffectActionBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class ElementalistSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        //buffs
        SpellBuilder.buffSelfSpell(SpellKeys.ICE_SHIELD,
                SpellConfiguration.Builder.instant(20, 60), "Ice Shield",
                BeneficialEffects.FROST_ARMOR, 30)
            .build();

        SpellBuilder.buffAlliesSpell(SpellKeys.NATURE_BALM,
                SpellConfiguration.Builder.instant(20, 60), "Nature's Balm",
                BeneficialEffects.REGENERATE, 15)
            .build();

        SpellBuilder.buffAlliesSpell(SpellKeys.FROST_STEPS,
                SpellConfiguration.Builder.instant(20, 60), "Frost Steps",
                BeneficialEffects.FROST_STEPS, 15)

            // todo this is a bit messy code
            // but basically when effects spawn an entity,
            // the entity's ontick calls the spell's components, not the effect's

            // .onTick("block", PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 25D, 0.5D)
            //   .onTick(1D))
            .onTick("block", PartBuilder.groundParticles(ParticleTypes.ITEM_SNOWBALL, 10D, 0.4D, 0.1D)
                .onTick(1D))

            .onTick("block", new ExileEffectActionBuilder(NegativeEffects.CHILL).targetEnemies()
                .radius(2)
                .seconds(5)
                .build())

            .build();
        //buffs

        SpellBuilder.of(SpellKeys.ICE_SNAKE, SpellConfiguration.Builder.instant(20, 100), "Ice Snake",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
            .manualDesc(
                "Summon an ice snake in your direction, slowing enemies.")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.FISHING_THROW_LOW_PITCH))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 0.7D, SlashEntities.SIMPLE_PROJECTILE.get(), 60D, false)
                .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                .put(MapField.GRAVITY, false)
            ))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 5D, 0.5D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 5D, 0.3D))

            .onTick(PartBuilder.playSound(SoundRefs.ICE_BREAK))

            .onTick(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.ICE, 20D * 1)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, -0.02D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, true))
                .onTick(2D))

            .onTick(PartBuilder.damageInAoe(SpellCalcs.ICE_SNAKE, 6D)
                .addCondition(EffectCondition.DID_NOT_AFFECT_ENTITY_ALREADY.create())
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_HURT))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.MARK_AS_AFFECTED_BY_ENTITY.create()))
                .onTick(1D))
            .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 1D, 2D))

            .build();

        SpellBuilder.of(SpellKeys.MAGIC_PROJECTILE, SpellConfiguration.Builder.instant(0, 15), "Magic Projectile",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
            .manualDesc(
                "Throw out a magical spark, dealing damage")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.FISHING_THROW_LOW_PITCH))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 8D, false)
            ))
            .onTick(PartBuilder.particleOnTick(4D, ParticleTypes.POOF, 1D, 0.1D))
            .onTick(PartBuilder.particleOnTick(2D, ParticleTypes.CLOUD, 1D, 0.2D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 1D, 0.01D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 5D, 0.3D))

            .onExpire(PartBuilder.damageInAoe(SpellCalcs.MAGIC_PROJECTILE, 2D))
            .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 1D, 2D))

            .onHit(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 3D, 1D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 3D, 1D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.POOF, 3D, 1D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.CRIT, 15D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.METEOR, SpellConfiguration.Builder.instant(18, 30), "Meteor",
                Arrays.asList(SpellTag.area, SpellTag.damage)
            )
            .manualDesc("Summon a meteor that falls from the sky, dealing damage")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.FISHING_BOBBER_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 12D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 200D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, -0.04D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, true)))
            .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
            .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.EXPLOSION, 1D, 0.1D))
            .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.METEOR, 4D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 200D, 4D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.FLAME, 40D, 4D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 120D, 4D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 35D, 4D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.ICE_NOVA, SpellConfiguration.Builder.instant(20, 60), "Ice Nova",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
            .manualDesc(
                "Damage enemies around you")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            //.onCast(PartBuilder.playSound(SoundRefs.EXPLOSION))

            .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 3D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 200D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.POOF, 20D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CRIT, 20D, 3D))

            .onCast(PartBuilder.justAction(SpellAction.ICE_NOVA.create()))

            .onCast(PartBuilder.damageInAoe(SpellCalcs.FROST_NOVA, 3D))

            .onCast(new VanillaEffectActionBuilder(Effects.MOVEMENT_SLOWDOWN).radius(3)
                .targetEnemies()
                .seconds(6)
                .build())

            .build();

        SpellBuilder.of(SpellKeys.POISON_CLOUD, SpellConfiguration.Builder.instant(30, 25 * 20), "Poison Cloud",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Erupt with poisonous gas, dealing damage to nearby enemies and spawning a poison cloud.")
            .weaponReq(CastingWeapon.ANY_WEAPON)

            .onCast(PartBuilder.playSound(SoundRefs.DING_LOW_PITCH))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

            .onExpire(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 10)))

            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.groundParticles(ParticleTypes.SNEEZE, 20D, 3D, 0.2D))
            .onTick("block", PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, 3D)
                .onTick(20D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
            )
            .build();

        SpellBuilder.of(SpellKeys.TELEPORT, SpellConfiguration.Builder.instant(30, 20 * 15), "Teleport",
                Arrays.asList(SpellTag.damage, SpellTag.movement)
            )
            .manualDesc("Teleport yourself in the direction you're looking at.")
            .teleportForward()
            .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

            .build();

        SpellBuilder.of(SpellKeys.REFRESH, SpellConfiguration.Builder.instant(40, 20 * 60 * 3)
                // if this becomes useless due to lower cooldowns, buff it or make it instead a spell that decreases mana cost
                , "Refresh",
                Arrays.asList())
            .manualDesc(
                "Refreshes all your spell cooldowns by 1 minute.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.DING_LOW_PITCH))

            .onCast(PartBuilder.justAction(SpellAction.REFRESH_COOLDOWNS_BY_X_TICKS.create(20 * 60D))
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.DRIPPING_WATER, 50D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 1.5D))
            .build();

        SpellBuilder.of(SpellKeys.ICE_SNAKE, SpellConfiguration.Builder.instant(20, 100), "Ice Snake",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
            .manualDesc(
                "Summon an ice snake in your direction, slowing enemies.")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.FISHING_THROW_LOW_PITCH))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 0.7D, SlashEntities.SIMPLE_PROJECTILE.get(), 60D, false)
                .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                .put(MapField.GRAVITY, false)
            ))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 5D, 0.5D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 5D, 0.3D))

            .onTick(PartBuilder.playSound(SoundRefs.ICE_BREAK))

            .onTick(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.ICE, 20D * 1)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, -0.02D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, true))
                .onTick(2D))

            .onTick(PartBuilder.damageInAoe(SpellCalcs.MAGIC_PROJECTILE, 6D)
                .addCondition(EffectCondition.DID_NOT_AFFECT_ENTITY_ALREADY.create())
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_HURT))
                .addPerEntityHit(PartBuilder.justAction(SpellAction.MARK_AS_AFFECTED_BY_ENTITY.create()))
                .onTick(1D))
            .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 1D, 2D))
            .build();

    }
}
