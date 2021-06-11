package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.value_calc.ValueCalcAdder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
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
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class DexSpells implements ISlashRegistryInit {

    public static String EXPLOSIVE_ARROW_ID = "explosive_arrow";
    public static String MAKE_ARROWS = "make_arrows";

    @Override
    public void registerAll() {

        SpellBuilder.of("charged_bolt", SpellConfiguration.Builder.arrowSpell(8, 20 * 15), "Charged Bolt",
            Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Shoot a charged arrow that goes through enemies and deals "
                    + SpellCalcs.CHARGED_BOLT.getLocSpellTooltip() + " " + Elements.Physical.getIconNameDmg() + " in radius.")

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                .put(MapField.PROJECTILE_SPEED, 1D)
                .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                .put(MapField.GRAVITY, false)))

            .onHit(PartBuilder.aoeParticles(ParticleTypes.CRIT, 100D, 1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damageInAoe(SpellCalcs.CHARGED_BOLT, Elements.Physical, 2D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 4D, 0.1D))
            .build();

        SpellBuilder.of("demon", SpellConfiguration.Builder.nonInstant(10, 60 * 20, 30)
                .setRequireActions(Arrays.asList(PlayerAction.TECHNIQUE, PlayerAction.MELEE_ATTACK)),
            "Demon Transformation",
            Arrays.asList())
            .manualDesc(
                "Temporarily transform into a demon, giving you increased offensive power and knockback resistance."
            )
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITHER_SKELETON_DEATH, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.DEMON_TRANSFORMATION, 20D * 20))
            .build();

        SpellBuilder.of("execute", SpellConfiguration.Builder.instant(10, 20 * 60)
                .setRequireActions(Arrays.asList(PlayerAction.TECHNIQUE, PlayerAction.MELEE_ATTACK))
                .setSwingArm(), "Execute",
            Arrays.asList(SpellTag.area, SpellTag.damage, SpellTag.technique))
            .attackStyle(PlayStyle.ranged)
            .manualDesc(
                "Slash enemies in front of you for " + SpellCalcs.EXECUTE.getLocSpellTooltip()
            )
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITHER_DEATH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(SpellCalcs.EXECUTE, Elements.Physical, 1D, 2D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SOUL, 5D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 25D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SMOKE, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("mark_for_death", SpellConfiguration.Builder.instant(5, 60 * 20 * 2)
                .setScaleManaToPlayer(),
            "Marked for Death",
            Arrays.asList())
            .attackStyle(PlayStyle.ranged)
            .manualDesc("Mark your target for death, making them more vulnerable to damage.")
            .onCast(PartBuilder.giveSelfEffect(ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, 20D * 10))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.MURDER_INSTINCT, 20D * 10))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)
                .put(MapField.IS_SILENT, true)
            ))
            .onHit(PartBuilder.justAction(SpellAction.POTION.createGive(StatusEffects.GLOWING, 20 * 10D))
                .addActions(SpellAction.POTION.createGive(ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, 20 * 10D))
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.MARK_OF_DEATH, 10))
                .addTarget(TargetSelector.TARGET.create()))
            .build();

        SpellBuilder.of("the_hunt", SpellConfiguration.Builder.nonInstant(5, 60 * 20 * 2, 20)
                .setScaleManaToPlayer(),
            "The Hunt",
            Arrays.asList())
            .manualDesc(
                "Gain Night vision and set all enemies around you to glow."
            )
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WOLF_HOWL, 1D, 1D))
            .onCast(PartBuilder.giveSelfEffect(StatusEffects.NIGHT_VISION, 20D * 30))
            .onCast(PartBuilder.giveSelfEffect(StatusEffects.SPEED, 20D * 30))
            .onCast(PartBuilder.addEffectToEnemiesInAoe(StatusEffects.GLOWING, 20D, 20D * 20))

            .build();

        SpellBuilder.of("backflip", SpellConfiguration.Builder.instant(3, 20 * 25), "Backflip",
            Arrays.asList(SpellTag.technique))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .manualDesc("Jump back and gain Cleanse for a short time.")
            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.SET, -1.5D, ParticleMotion.CasterLook)
                .put(MapField.IGNORE_Y, true))
                .addTarget(TargetSelector.CASTER.create()))
            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 0.5D, ParticleMotion.Upwards))
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.CLEANSE, 20D * 3))

            .build();

        SpellBuilder.of("arrow_storm", SpellConfiguration.Builder.arrowSpell(20, 20 * 25), "Arrow Storm",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)

            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D)))
            .onHit(PartBuilder.damage(ValueCalcAdder.DIRECT_ARROW_HIT, Elements.Physical))
            .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculation.base("arrow_storm_sec", 3), Elements.Elemental))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
            .build();

        SpellBuilder.of("poison_arrow", SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Poison Arrow",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
            .onHit(PartBuilder.damage(ValueCalcAdder.DIRECT_ARROW_HIT, Elements.Nature))

            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.POISON.effectId, 2D, 20 * 8D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 100D, 2D))

            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1D, 1D))
            .onHit(PartBuilder.damageInAoe(ValueCalculation.scaleWithAttack("poison_arrow_aoe", 0.2F, 1), Elements.Nature, 2D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .build();

        SpellBuilder.of(EXPLOSIVE_ARROW_ID, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Explosive Arrow",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
            .onHit(PartBuilder.damage(ValueCalcAdder.DIRECT_ARROW_HIT, Elements.Physical))

            .onHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 0.1D))

            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .onHit(PartBuilder.damageInAoe(ValueCalculation.scaleWithAttack("explosive_arrow_eoe", 0.25F, 3), Elements.Physical, 2D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .build();

        SpellBuilder.of("recoil_shot", SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Recoil Shot",
            Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
            .onHit(PartBuilder.damage(ValueCalcAdder.DIRECT_ARROW_HIT, Elements.Physical))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.WOUNDS.effectId, 1D, 20 * 20D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D)
            )
            .build();

        SpellBuilder.of(MAKE_ARROWS, SpellConfiguration.Builder.nonInstant(10, 20 * 60 * 5, 80)
                .setScaleManaToPlayer(), "Produce Arrows",
            Arrays.asList())
            .manualDesc("Produce a stack of arrows.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.CASTER_USE_COMMAND.create("/give @s minecraft:arrow 64")))
            .build();

    }
}
