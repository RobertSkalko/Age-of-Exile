package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
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
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

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

    public static String FROST_TRAP = "frost_trap";
    public static String POISON_TRAP = "poison_trap";
    public static String FIRE_TRAP = "fire_trap";
    public static String HUNTER_POTION = "hunter_potion";
    public static String NIGHT_VISION = "night_vision";
    public static String SMOKE_BOMB = "smoke_bomb";

    @Override

    public void registerAll() {

        SpellBuilder.of(HUNTER_POTION, SpellConfiguration.Builder.instant(0, 60 * 20 * 3), "Hunter's Potion",
                Arrays.asList(SpellTag.heal)
            )
            .manualDesc("Drink a potion, healing you for " + SpellCalcs.HUNTER_POTION_HEAL.getLocDmgTooltip() + " health")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.healCaster(SpellCalcs.HUNTER_POTION_HEAL))
            .build();

        trap(FROST_TRAP, "Frost Trap", ParticleTypes.ITEM_SNOWBALL, SpellCalcs.RANGER_TRAP, Elements.Water).build();
        trap(POISON_TRAP, "Poison Trap", ParticleTypes.ITEM_SLIME, SpellCalcs.RANGER_TRAP, Elements.Earth).build();
        trap(FIRE_TRAP, "Fire Trap", ParticleTypes.FLAME, SpellCalcs.RANGER_TRAP, Elements.Fire).build();

        SpellBuilder.of(SMOKE_BOMB, SpellConfiguration.Builder.instant(7, 20 * 60), "Smoke Bomb",
                Arrays.asList())
            .manualDesc("Throw out a smoke bomb, blinding enemies and reducing threat.")
            .attackStyle(PlayStyle.ranged)
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.SPLASH_POTION_BREAK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.SMOKE_BOMB, AggroAction.Type.DE_AGGRO))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.BLIND.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 5))
                .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 200D, 3D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 3D))
            .build();

        SpellBuilder.of(NIGHT_VISION, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 5, 40)
                    .setScaleManaToPlayer(),
                "Night Vision",
                Arrays.asList())
            .manualDesc(
                "Give Night Vision to allies around you.")
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.LINGERING_POTION_THROW, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 100D, 3D))
            .onCast(PartBuilder.giveEffectToAlliesInRadius(Effects.NIGHT_VISION, 20D * 60D * 3, 5D))
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
            .onCast(PartBuilder.playSound(SoundEvents.CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.CASTER_USE_COMMAND.create("effect give @p minecraft:slow_falling 1 1 true")))
            .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1.6D))

            .teleportForward()

            .build();

        SpellBuilder.of(CHARGED_BOLT, SpellConfiguration.Builder.arrowSpell(8, 20 * 15), "Charged Bolt",
                Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Shoot a charged arrow that goes through enemies and deals "
                    + SpellCalcs.CHARGED_BOLT.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg() + " in radius and slows.")

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                .put(MapField.PROJECTILE_SPEED, 1D)
                .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                .put(MapField.GRAVITY, false)))

            .onHit(PartBuilder.aoeParticles(ParticleTypes.CRIT, 100D, 1D))
            .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damageInAoe(SpellCalcs.CHARGED_BOLT, Elements.Physical, 2D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D))))
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
            .onCast(PartBuilder.playSound(SoundEvents.WOLF_HOWL, 1D, 1D))
            .onCast(PartBuilder.giveSelfEffect(Effects.NIGHT_VISION, 20D * 30))
            .onCast(PartBuilder.giveSelfEffect(Effects.MOVEMENT_SPEED, 20D * 30))
            .onCast(PartBuilder.addEffectToEnemiesInAoe(Effects.GLOWING, 20D, 20D * 20))

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
            .manualDesc("Shoot out arrows in an arc, dealing " + SpellCalcs.ARROW_STORM.getLocDmgTooltip(Elements.Physical))
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D)))
            .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(SpellCalcs.ARROW_STORM, Elements.Physical))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
            .build();

        SpellBuilder.of(POISON_ARROW, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Poison Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .manualDesc("Shoot an arrow, dealing "
                + SpellCalcs.POISON_ARROW.getLocDmgTooltip(Elements.Physical) + " around it and poisoning enemies.")

            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))

            .onExpire(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.POISON.resourcePath, 2D, 20 * 8D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 100D, 2D))
            .onExpire(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
            .onExpire(PartBuilder.playSound(SoundEvents.SPLASH_POTION_BREAK, 1D, 1D))
            .onExpire(PartBuilder.damageInAoe(SpellCalcs.POISON_ARROW, Elements.Earth, 2D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D))))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .build();

        SpellBuilder.of(EXPLOSIVE_ARROW_ID, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Explosive Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .manualDesc("Shoot an arrow that does " + SpellCalcs.EXPLOSIVE_ARROW.getLocDmgTooltip(Elements.Physical) + " around it")
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 0.1D))
            .onExpire(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
            .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
            .onExpire(PartBuilder.damageInAoe(SpellCalcs.EXPLOSIVE_ARROW, Elements.Physical, 2D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D))))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .build();

        SpellBuilder.of(RECOIL_SHOT, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Recoil Shot",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
            .onHit(PartBuilder.damage(SpellCalcs.DIRECT_ARROW_HIT, Elements.Physical))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.WOUNDS.resourcePath, 1D, 20 * 20D))
            .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D)
            )
            .build();

        SpellBuilder.of(MAKE_ARROWS, SpellConfiguration.Builder.nonInstant(10, 20 * 60 * 5, 80)
                    .setScaleManaToPlayer(), "Produce Arrows",
                Arrays.asList())
            .manualDesc("Produce a stack of arrows.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.CASTER_USE_COMMAND.create("/give @s minecraft:arrow 64")))
            .build();

    }

    static SpellBuilder trap(String id, String name, BasicParticleType particle, ValueCalculation dmg, Elements element) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(7, 20)
                    .setChargesAndRegen("trap", 3, 20 * 30)
                    .setSwingArm(), name,
                Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .manualDesc(
                "Throw out a trap that stays on the ground and activates when an enemy approaches to deal "
                    + dmg.getLocDmgTooltip() + element.getIconNameDmg() + " damage in area around itself."
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.IRON_INGOT, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 100D, true)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.TRAP.get(), 20 * 15D)
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
            .addSpecificAction("expire", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D));

    }

}
