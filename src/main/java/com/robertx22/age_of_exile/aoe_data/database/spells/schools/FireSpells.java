package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.MISC_ITEMS;

public class FireSpells implements ExileRegistryInit {
    public static String FIREBALL_ID = "fireball";
    public static String FIRE_NOVA_ID = "fire_nova";
    public static String FLAME_STRIKE_ID = "flame_strike";

    @Override
    public void registerAll() {
        SpellBuilder.of(FLAME_STRIKE_ID, SpellConfiguration.Builder.instant(8, 15)
                    .setSwingArm(), "Flame Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("flame_strike", 0.5F, 1), Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("overload", SpellConfiguration.Builder.nonInstant(10, 60 * 20, 30), "Overload",
                Arrays.asList())
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.OVERLOAD.effectId, 20 * 10D))
            .build();

        SpellBuilder.breath("fire_breath", "Fire Breath", Elements.Fire, ParticleTypes.FLAME)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CAT_HISS, 1D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(10D)))
            .build();

        SpellBuilder.of("meteor", SpellConfiguration.Builder.nonInstant(18, 20 * 30, 30), "Meteor",
                Arrays.asList(SpellTag.area, SpellTag.damage)
            )
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 6D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, true)))
            .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
            .onExpire("block", PartBuilder.damageInAoe(ValueCalculation.base("meteor", 12), Elements.Fire, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .build();

        SpellBuilder.of(FIRE_NOVA_ID, SpellConfiguration.Builder.instant(20, 20 * 25), "Fire Nova",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))

            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 200D, 2.8D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 2D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 1D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.SMOKE, 200D, 1D, 0.05D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 0D, 0.2D))

            .onCast(PartBuilder.damageInAoe(ValueCalculation.base("fire_nova", 7), Elements.Fire, 3D))
            .build();

        SpellBuilder.of(FIREBALL_ID, SpellConfiguration.Builder.instant(7, 20)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Fire Ball",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .manualDesc(
                "Throw out a ball of fire, dealing " + SpellCalcs.FIREBALL.getLocSpellTooltip()
                    + " " + Elements.Fire.getIconNameDmg())
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1D, 0.6D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.FIREBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FLAME, 1D, 0.1D))

            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FALLING_LAVA, 1D, 0.5D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 1D, 0.01D))

            .onHit(PartBuilder.damage(SpellCalcs.FIREBALL, Elements.Fire))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_BURN, 1D, 2D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.LAVA, 1D, 0.5D))
            .build();

        SpellBuilder.of("lava_sphere", SpellConfiguration.Builder.nonInstant(20, 20 * 15, 20 * 2)
                    .setSwingArm(), "Lava Sphere",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.area))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.FIRE_CHARGE, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 100D, false)

            ))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.FLAME, 3D, 0.2D)
                .onTick(1D))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 1D, 0.2D)
                .onTick(1D))
            .onExpire(PartBuilder.damageInAoe(ValueCalculation.base("lava_sphere", 6), Elements.Fire, 2D))

            .onExpire(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 5D, 1D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.FLAME, 25D, 2D))

            .build();

    }
}
