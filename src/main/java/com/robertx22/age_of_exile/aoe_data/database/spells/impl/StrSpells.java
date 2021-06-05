package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.PARTICLES;

public class StrSpells implements ISlashRegistryInit {

    public static String FLAME_STRIKE_ID = "flame_strike";
    public static String CHARGE_ID = "charge";
    public static String GONG_STRIKE_ID = "gong_strike";

    @Override
    public void registerAll() {

        SpellBuilder.of("shred", SpellConfiguration.Builder.instant(10, 20 * 1)
                .setSwingArm()
                .setChargesAndRegen("shred", 3, 20 * 15), "Shred",
            Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Slash all nearby enemies, dealing "
                    + SpellCalcs.SHRED.getLocSpellTooltip() + " " + Elements.Physical.getIconNameDmg()
                    + " damage and reducing their defenses."
            )

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITHER_SKELETON_HURT, 1D, 1D))

            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.nova(ParticleTypes.CRIT, 200D, 2.8D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.ENCHANTED_HIT, 100D, 2D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.CRIT, 100D, 1D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.ENCHANTED_HIT, 200D, 1D, 0.05D))

            .onCast(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.SHRED, 10))
                .enemiesInRadius(3D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.SHRED, Elements.Physical, 3D))
            .build();

        SpellBuilder.of("pull", SpellConfiguration.Builder.instant(5, 60 * 20), "Pull",
            Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Pull enemies in area to you, dealing " +
                    SpellCalcs.PULL.getLocSpellTooltip() + " " +
                    Elements.Physical.getIconNameDmg() + " and slowing them."
            )
            .attackStyle(PlayStyle.melee)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ANVIL_HIT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addActions(SpellAction.POTION.createGive(StatusEffects.SLOWNESS, 20D * 5))
                .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL, Elements.Physical))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.STUN.effectId, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                .addTarget(TargetSelector.AOE.create(8D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 6D, 0.1D))
            .build();

        SpellBuilder.of("shout_warn", SpellConfiguration.Builder.instant(10, 60 * 20), "Warning Shout",
            Arrays.asList(SpellTag.area, SpellTag.shout, SpellTag.shield))

            .manualDesc(
                "Let out a warning shout, giving a "
                    + SpellCalcs.SHOUT_WARN.getLocSpellTooltip() + " Shield to all nearby allies.")

            .attackStyle(PlayStyle.melee)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WOLF_HOWL, 1D, 1D))
            .onCast(PartBuilder.giveShieldInRadius(10D, SpellCalcs.SHOUT_WARN, 10D))
            .build();

        SpellBuilder.of("thorn_armor", SpellConfiguration.Builder.instant(15, 200 * 20), "Thorn Armor",
            Arrays.asList(SpellTag.damage))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.THORN_ARMOR.effectId, 20 * 45D))
            .build();

        SpellBuilder.of("frost_armor", SpellConfiguration.Builder.instant(15, 120 * 20), "Frost Armor",
            Arrays.asList())
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.FROST_ARMOR, 20 * 120D))
            .build();

        SpellBuilder.of("poisoned_weapons", SpellConfiguration.Builder.instant(15, 160 * 20), "Poison Weapons",
            Arrays.asList(SpellTag.damage))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.POISON_WEAPONS, 20 * 30D))
            .build();

        SpellBuilder.of(FLAME_STRIKE_ID, SpellConfiguration.Builder.instant(8, 15)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Flame Strike",
            Arrays.asList(SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("flame_strike", 0.5F, 1), Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("tidal_strike", SpellConfiguration.Builder.instant(8, 12)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Tidal Strike",
            Arrays.asList(SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("tidal_strike", 0.5F, 1), Elements.Water, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(PARTICLES.BUBBLE, 100D, 1D, 0.1D))
            )
            .build();

        SpellBuilder.of(GONG_STRIKE_ID, SpellConfiguration.Builder.instant(8, 20 * 10)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK, PlayerAction.BLOCK))
                .setSwingArm(), "Gong Strike",
            Arrays.asList(SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1D, 1D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))

            .onCast(PartBuilder.damageInFront(ValueCalculation.scaleWithAttack("gong_strike", 0.5F, 5), Elements.Physical, 2D, 3D))
            .onCast(PartBuilder.addExileEffectToEnemiesInFront(NegativeEffects.STUN.effectId, 2D, 2D, 20D * 3))

            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 300D, 2D, 0.1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 5D, 2D, 0.1D))

            .build();

        SpellBuilder.of("thirst_strike", SpellConfiguration.Builder.instant(5, 15)
                .setRequireActions(Arrays.asList(PlayerAction.MELEE_ATTACK, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Thirsting Strike",
            Arrays.asList(SpellTag.area, SpellTag.damage))

            .attackStyle(PlayStyle.melee)
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
            Arrays.asList(SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.giveSelfEffect(ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, 100D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1D, 1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 2D, 0.5D))
            .onCast(PartBuilder.damageInAoe(ValueCalculation.scaleWithAttack("whirlwind_dmg", 0.2F, 1), Elements.Physical, 1.5D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 50D, 0.5D, 0.1D))
            )
            .build();

        SpellBuilder.of(CHARGE_ID, SpellConfiguration.Builder.multiCast(10, 20 * 10, 60, 60)
                .setScaleManaToPlayer(), "Charge",
            Arrays.asList(SpellTag.area, SpellTag.damage, SpellTag.movement))
            .manualDesc(
                "Charge in a direction, stopping upon first enemy hit to deal "
                    + SpellCalcs.CHARGE.getLocSpellTooltip() + " " + Elements.Physical.getIconNameDmg() + " in radius."

            )
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ANCIENT_DEBRIS_STEP, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 0.2D, ParticleMotion.CasterLook)
                .put(MapField.IGNORE_Y, true))
                .addTarget(TargetSelector.CASTER.create()))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 20D, 1D, 0.5D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 1D, 0.5D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.CHARGE, Elements.Physical, 1.75D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1D, 1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 100D, 0.5D, 0.1D))
                .addPerEntityHit(PartBuilder.cancelSpell())
            )
            .build();

        SpellBuilder.of("taunt", SpellConfiguration.Builder.instant(0, 20 * 30)
                .setSwingArm(), "Taunt",
            Arrays.asList(SpellTag.area))
            .manualDesc(
                "Shout, making enemies nearby want to attack you. " +
                    "Generates " + SpellCalcs.TAUNT.getLocSpellTooltip() + " threat."
            )
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 20D, 3D))

            .build();

    }
}
