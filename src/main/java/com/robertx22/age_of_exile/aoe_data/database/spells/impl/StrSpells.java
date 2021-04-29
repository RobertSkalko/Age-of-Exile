package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.PARTICLES;

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

        SpellBuilder.of(FLAME_STRIKE_ID, SpellConfiguration.Builder.instant(8, 15)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Flame Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("flame_strike", 0.5F, 1), Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("tidal_strike", SpellConfiguration.Builder.instant(8, 12)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Tidal Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("tidal_strike", 0.5F, 1), Elements.Water, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(PARTICLES.BUBBLE, 100D, 1D, 0.1D))
            )
            .build();

        SpellBuilder.of("gong_strike", SpellConfiguration.Builder.instant(8, 20 * 10)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK, PlayerAction.BLOCK))
                .setSwingArm(), "Gong Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1D, 1D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))

            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("gong_strike", 0.5F, 5), Elements.Physical, 2D, 3D))

            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 300D, 2D, 0.1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 5D, 2D, 0.1D))

            .build();

        SpellBuilder.of("thirst_strike", SpellConfiguration.Builder.instant(5, 15)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Thirsting Strike",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("thirst_strike", 0.25F, 1), Elements.Physical, 1.25D, 1.25D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 75D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.healCaster(ValueCalculation.base("thirst_strike_heal", 1)))
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
            .onCast(PartBuilder.damageInAoe(ValueCalculation.scaleWithAttack("whirlwind_dmg", 0.2F, 1), Elements.Physical, 1.5D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 50D, 0.5D, 0.1D))
            )
            .build();

        SpellBuilder.of("charge", SpellConfiguration.Builder.multiCast(10, 20 * 10, 60, 60)
                .setScaleManaToPlayer(), "Charge",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_STEP, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 0.3D, ParticleMotion.CasterLook))
                .addTarget(TargetSelector.CASTER.create()))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 20D, 1D, 0.5D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 1D, 0.5D))
            .onCast(PartBuilder.damageInAoe(ValueCalculation.scaleWithAttack("charge", 1F, 0), Elements.Physical, 1.75D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1D, 1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.cancelSpell())
            )
            .build();

        SpellBuilder.of("taunt", SpellConfiguration.Builder.instant(5, 15)
                .setSwingArm(), "Taunt",
            Arrays.asList(SkillGemTag.AREA))
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(AggroAction.Type.AGGRO))
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 20D, 3D))

            .build();

    }
}
