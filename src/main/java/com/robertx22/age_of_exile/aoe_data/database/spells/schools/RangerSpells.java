package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.value_calc.ValueCalcAdder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class RangerSpells implements ExileRegistryInit {

    public static String EXPLOSIVE_ARROW_ID = "explosive_arrow";
    public static String MAKE_ARROWS = "make_arrows";
    public static String CHARGED_BOLT = "charged_bolt";
    public static String THE_HUNT = "the_hunt";
    public static String BACKFLIP = "backflip";
    public static String ARROW_STORM = "arrow_storm";
    public static String POISON_ARROW = "poison_arrow";
    public static String RECOIL_SHOT = "recoil_shot";
    public static String DASH_ID = "dash";

    @Override
    public void registerAll() {

        trap("frost_trap", "Frost Trap", ParticleTypes.ITEM_SNOWBALL, SpellCalcs.RANGER_TRAP, Elements.Water).build();
        trap("poison_trap", "Poison Trap", ParticleTypes.ITEM_SLIME, SpellCalcs.RANGER_TRAP, Elements.Earth).build();
        trap("fire_trap", "Fire Trap", ParticleTypes.FLAME, SpellCalcs.RANGER_TRAP, Elements.Fire).build();

        SpellBuilder.of("smoke_bomb", SpellConfiguration.Builder.instant(7, 20 * 60), "Smoke Bomb",
                Arrays.asList())
            .manualDesc("Throw out a smoke bomb, blinding enemies and reducing threat.")
            .attackStyle(PlayStyle.ranged)
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.SMOKE_BOMB, AggroAction.Type.DE_AGGRO))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.BLIND.effectId, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 5))
                .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 200D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 3D))
            .build();

        SpellBuilder.of("night_vision", SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 5, 40)
                    .setScaleManaToPlayer(),
                "Night Vision",
                Arrays.asList())
            .manualDesc(
                "Give Night Vision to allies around you.")

            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_LINGERING_POTION_THROW, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 100D, 3D))
            .onCast(PartBuilder.giveEffectToAlliesInRadius(StatusEffects.NIGHT_VISION, 20D * 60D * 3, 5D))
            .build();
        SpellBuilder.of(DASH_ID, SpellConfiguration.Builder.instant(10, 15)
                    .setScaleManaToPlayer()
                    .setChargesAndRegen("dash", 3, 20 * 30)
                , "Dash",
                Arrays.asList(SpellTag.movement, SpellTag.technique))

            .manualDesc(
                "Dash in your direction and gain slowfall.")

            .weaponReq(CastingWeapon.NON_MAGE_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.CASTER_USE_COMMAND.create("effect give @p minecraft:slow_falling 1 1 true")))
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1.6D)
                .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.POOF, 20D, 1D)
                    .put(MapField.Y_RANDOM, 0.5D)
                    .put(MapField.MOTION, ParticleMotion.CasterLook.name())
                    .put(MapField.SET_ADD, SetAdd.ADD.name()))
                .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.WHITE_ASH, 20D, 1D)
                    .put(MapField.Y_RANDOM, 0.5D)
                    .put(MapField.MOTION, ParticleMotion.CasterLook.name())
                    .put(MapField.SET_ADD, SetAdd.ADD.name())))

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 1D, ParticleMotion.CasterLook))
                .addTarget(TargetSelector.CASTER.create()))
            .build();

        SpellBuilder.of(CHARGED_BOLT, SpellConfiguration.Builder.arrowSpell(8, 20 * 15), "Charged Bolt",
                Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Shoot a charged arrow that goes through enemies and deals "
                    + SpellCalcs.CHARGED_BOLT.getLocSpellTooltip() + " " + Elements.Physical.getIconNameDmg() + " in radius and slows.")

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
            .onHit(PartBuilder.damageInAoe(SpellCalcs.CHARGED_BOLT, Elements.Physical, 2D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(StatusEffects.SLOWNESS, 40D))))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 4D, 0.1D))
            .build();

        SpellBuilder.of(THE_HUNT, SpellConfiguration.Builder.nonInstant(5, 60 * 20 * 2, 20)
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

        SpellBuilder.of(BACKFLIP, SpellConfiguration.Builder.instant(3, 20 * 25), "Backflip",
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

        SpellBuilder.of(ARROW_STORM, SpellConfiguration.Builder.arrowSpell(20, 20 * 25), "Arrow Storm",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)

            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D)))
            .onHit(PartBuilder.damage(ValueCalcAdder.DIRECT_ARROW_HIT, Elements.Physical))
            .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(SpellCalcs.ARROW_STORM, Elements.Elemental))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
            .build();

        SpellBuilder.of(POISON_ARROW, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Poison Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
            .onHit(PartBuilder.damage(ValueCalcAdder.DIRECT_ARROW_HIT, Elements.Earth))

            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.POISON.effectId, 2D, 20 * 8D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 100D, 2D))

            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1D, 1D))
            .onHit(PartBuilder.damageInAoe(SpellCalcs.POISON_ARROW, Elements.Earth, 2D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(StatusEffects.SLOWNESS, 40D))))
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
            .onHit(PartBuilder.damageInAoe(SpellCalcs.EXPLOSIVE_ARROW, Elements.Physical, 2D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(StatusEffects.SLOWNESS, 40D))))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .build();

        SpellBuilder.of(RECOIL_SHOT, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Recoil Shot",
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

    static SpellBuilder trap(String id, String name, DefaultParticleType particle, ValueCalculation dmg, Elements element) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(7, 20)
                    .setChargesAndRegen("trap", 3, 20 * 30)
                    .setSwingArm(), name,
                Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .manualDesc(
                "Throw out a trap that stays on the ground and activates when an enemy approaches to deal "
                    + dmg.getLocSpellTooltip() + element.getIconNameDmg() + " damage in area around itself."
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.IRON_INGOT, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 100D, true)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(ModRegistry.BLOCKS.TRAP, 20 * 15D)
                .put(MapField.ENTITY_NAME, "trap")
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("trap", PartBuilder.aoeParticles(particle, 5D, 1D)
                .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.enemiesInRadius(1D))
                .addActions(SpellAction.EXPIRE.create())
                .addActions(SpellAction.SPECIFIC_ACTION.create("expire"))
                .onTick(2D))

            .addSpecificAction("expire", PartBuilder.damageInAoe(dmg, element, 3D))
            .addSpecificAction("expire", PartBuilder.aoeParticles(particle, 30D, 3D))
            .addSpecificAction("expire", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D));

    }

}
