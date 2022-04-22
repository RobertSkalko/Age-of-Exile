package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.VanillaEffectActionBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class AttackSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of(SpellKeys.MAGIC_PROJECTILE, SpellConfiguration.Builder.instant(0, 20), "Magic Projectile",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
            .manualDesc(
                "Throw out a magical spark, dealing " + SpellCalcs.MAGIC_PROJECTILE.getLocDmgTooltip()
                    + " " + Elements.Physical.getIconNameDmg())
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.FISHING_THROW_LOW_PITCH))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 8D, false)
            ))
            .onTick(PartBuilder.particleOnTick(4D, ParticleTypes.POOF, 1D, 0.1D))
            .onTick(PartBuilder.particleOnTick(2D, ParticleTypes.CLOUD, 1D, 0.2D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 1D, 0.01D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 5D, 0.3D))

            .onExpire(PartBuilder.damageInAoe(SpellCalcs.MAGIC_PROJECTILE, Elements.Physical, 2D))
            .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 1D, 2D))

            .onHit(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 3D, 1D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 3D, 1D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.POOF, 3D, 1D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.CRIT, 15D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.METEOR, SpellConfiguration.Builder.instant(18, 30), "Meteor",
                Arrays.asList(SpellTag.area, SpellTag.damage)
            )
            .manualDesc("Summon a meteor that falls from the sky, dealing " +
                SpellCalcs.METEOR.getLocDmgTooltip(Elements.Fire))
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
            .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.METEOR, Elements.Fire, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.FLAME, 30D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 100D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 25D, 3D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
            .build();

        SpellBuilder.of(SpellKeys.ICE_NOVA, SpellConfiguration.Builder.instant(20, 60), "Ice Nova",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
            .manualDesc(
                "TODO " + SpellCalcs.MAGIC_PROJECTILE.getLocDmgTooltip()
                    + " " + Elements.Physical.getIconNameDmg())
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundRefs.EXPLOSION))

            .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 3D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 200D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.POOF, 20D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CRIT, 20D, 3D))

            .onCast(PartBuilder.justAction(SpellAction.ICE_NOVA.create()))

            .onCast(PartBuilder.damageInAoe(SpellCalcs.MAGIC_PROJECTILE, Elements.Water, 3D)) // todo

            .onCast(new VanillaEffectActionBuilder(Effects.MOVEMENT_SLOWDOWN).radius(3)
                .targetEnemies()
                .seconds(6)
                .build())

            .build();

        SpellBuilder.of(SpellKeys.POISON_CLOUD, SpellConfiguration.Builder.instant(30, 25 * 20), "Poison Cloud",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Erupt with poisonous gas, dealing " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                    + " " + Elements.Earth.getIconNameDmg() + " to nearby enemies and applying Poison.")
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
            .onTick("block", PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, Elements.Earth, 3D)
                .onTick(20D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
            )
            .build();

    }
}
