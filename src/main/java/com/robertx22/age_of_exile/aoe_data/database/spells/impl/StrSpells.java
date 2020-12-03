package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.PARTICLES;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.SOUNDS;

public class StrSpells implements ISlashRegistryInit {

    public static String FLAME_STRIKE_ID = "flame_strike";

    @Override
    public void registerAll() {

        SpellBuilder.of("thorn_armor", SpellConfiguration.Builder.instant(15, 200 * 20), "Thorn Armor",
            Arrays.asList(SkillGemTag.DAMAGE))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.THORN_ARMOR, 20 * 45D))
            .build();

        SpellBuilder.of("frost_armor", SpellConfiguration.Builder.instant(15, 120 * 20), "Frost Armor",
            Arrays.asList())
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.FROST_ARMOR, 20 * 120D))
            .build();

        SpellBuilder.of("poisoned_weapons", SpellConfiguration.Builder.instant(15, 160 * 20), "Poison Weapons",
            Arrays.asList(SkillGemTag.DAMAGE))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.POISON_WEAPONS, 20 * 30D))
            .build();

        SpellBuilder.of("thunder_dash", SpellConfiguration.Builder.instant(15, 20 * 30), "Thunder Dash",
            Arrays.asList(SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(AttackPlayStyle.MELEE)
            .onCast(PartBuilder.playSound(SOUNDS.DASH, 1D, 1D))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.FORWARDS, DashUtils.Strength.LARGE_DISTANCE))
            .onCast(PartBuilder.damageInFront(ValueCalculationData.base(3), Elements.Thunder, 3D, 8D))
            .build();

        SpellBuilder.of(FLAME_STRIKE_ID, SpellConfiguration.Builder.instant(8, 15)
                .setSwingArm(), "Flame Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculationData.scaleWithAttack(0.5F, 1), Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("tidal_strike", SpellConfiguration.Builder.instant(8, 12)
                .setSwingArm(), "Tidal Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculationData.scaleWithAttack(0.5F, 1), Elements.Water, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(PARTICLES.BUBBLE, 100D, 1D, 0.1D))
            )
            .build();

        SpellBuilder.of("thirst_strike", SpellConfiguration.Builder.instant(5, 15)
                .setSwingArm(), "Thirsting Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculationData.scaleWithAttack(0.5F, 1), Elements.Physical, 1.25D, 1.25D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 75D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.healCaster(ValueCalculationData.base(2)))
            )
            .build();

        SpellBuilder.of("whirlwind", SpellConfiguration.Builder.multiCast(10, 0, 100, 10)
                .setSwingArm(), "Whirlwind",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.giveSelfEffect(ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, 100D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1D, 1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 2D, 0.5D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.scaleWithAttack(0.2F, 1), Elements.Physical, 1.5D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 50D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.healCaster(ValueCalculationData.base(2)))
            )
            .build();

        SpellBuilder.of("charge", SpellConfiguration.Builder.multiCast(10, 20 * 15, 100, 50), "Charge",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.giveSelfEffect(StatusEffects.SPEED, 20D, 2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 20D, 1D, 0.5D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.scaleWithAttack(1F, 0), Elements.Physical, 1.75D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1D, 1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.cancelSpell())
            )
            .build();

    }
}
