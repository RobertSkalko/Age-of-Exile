package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.*;

public class IntSpells implements ISlashRegistryInit {

    public static String FROSTBALL_ID = "frostball";
    public static String FIREBALL_ID = "fireball";
    public static String POISONBALL_ID = "poison_ball";
    public static String HEALING_AURA_ID = "healing_aura";
    public static String FIRE_NOVA_ID = "fire_nova";
    public static String FROST_NOVA_AOE = "frost_nova";

    @Override
    public void registerAll() {

        String HEAL_CASTER_ACTION = "heal_caster";

        SpellBuilder.of("sacrifice", SpellConfiguration.Builder.multiCast(0, 20 * 60, 80, 4)
                    .setSwingArm(), "Sacrifice",
                Arrays.asList())
            .weaponReq(CastingWeapon.ANY_WEAPON)

            .onCast(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculation.base("sacrifice", 5), Elements.Dark)
                    .put(MapField.DMG_EFFECT_TYPE, AttackType.dot.name()))
                .addTarget(TargetSelector.CASTER.create())
                .addActions(SpellAction.RESTORE_MANA.create(ValueCalculation.base("sac_res_mana", 5))))

            .onCast(PartBuilder.aoeParticles(ParticleTypes.SOUL, 2D, 0.15D))

            .build();

        SpellBuilder.of("soul_harvest", SpellConfiguration.Builder.nonInstant(10, 20 * 60, 40)
                , "Soul Harvest",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .addSpecificAction(HEAL_CASTER_ACTION, PartBuilder.healCaster(ValueCalculation.base("soul_harvest_heal", 1)))
            .onCast(PartBuilder.playSound(SoundEvents.PARTICLE_SOUL_ESCAPE, 1D, 1D)
                .enemiesInRadius(3D)
                .addPerEntityHit(PartBuilder.aoeParticles(PARTICLES.BLOOD_EXPLODE, 10D, 1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.SOUL, 10D, 0.2D)))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, 1D, 1D))
            .onCast(PartBuilder.damageInAoe(ValueCalculation.base("soul_harvest_dmg", 1), Elements.Dark, 3D))
            .onCast(PartBuilder.damageInAoe(ValueCalculation.base("soul_harvest_dmg", 1), Elements.Water, 3D))
            .onCast(PartBuilder.justAction(SpellAction.DO_ACTION_FOR_EACH_EFFECT_WITH_TAG_ON_TARGET.create(HEAL_CASTER_ACTION, EffectTags.negative))
                .addTarget(TargetSelector.AOE.enemiesInRadius(3D)))

            .build();

        SpellBuilder.of("shadow_ball", SpellConfiguration.Builder.instant(7, 15)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Shadow Bolt",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.SHADOWBALL, 1D, 0.8D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.PORTAL, 3D, 0.15D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.WITCH, 1D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculation.base("shadow_ball", 8), Elements.Dark))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.PORTAL, 10D, 1D))
            .addStat(new StatModifier(5, 5, Stats.DMG_PER_CURSE_ON_TARGET.get()))
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

        SpellBuilder.of("frost_sphere", SpellConfiguration.Builder.nonInstant(20, 20 * 30, 20 * 2)
                    .setSwingArm(), "Frost Sphere",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.area))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.2D, ENTITIES.SIMPLE_PROJECTILE, 100D, false)
                .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
            ))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 5D, 1.5D)
                .onTick(1D))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 1D, 0.2D)
                .onTick(1D))
            .onTick(PartBuilder.damageInAoe(ValueCalculation.base("frost_sphere", 3), Elements.Water, 2D)
                .onTick(15D)
                .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.SLOW, 3)))
            .build();

        SpellBuilder.of("black_hole", SpellConfiguration.Builder.nonInstant(30, 20 * 60, 30)
                    .setSwingArm(), "Black Hole",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.area))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .manualDesc("Summon a dark sphere that attracts nearby enemies to it, dealing "
                + SpellCalcs.BLACK_HOLE.getLocSpellTooltip()
                + Elements.Dark.getIconNameDmg() + " when it expires.")

            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.BLACK_HOLE, 20D * 5)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.particleOnTick(1D, ParticleTypes.PORTAL, 40D, 1D))
            .onTick("block", PartBuilder.particleOnTick(1D, ParticleTypes.WITCH, 8D, 1D))
            .onTick("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.BLACK_HOLE, Elements.Dark, 2D))
            .build();

        SpellBuilder.of("shooting_star", SpellConfiguration.Builder.instant(10, 20)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Shooting Star",
                Arrays.asList(SpellTag.projectile, SpellTag.heal))
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 1D, 1.7D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)
                .put(MapField.HITS_ALLIES, true)))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 1D, 0.5D)
                .onTick(1D))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 1D, 0.7D)
                .onTick(1D))
            .onExpire(PartBuilder.healInAoe(ValueCalculation.base("shooting_star", 10), 2D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.SOUL_FIRE_FLAME, 10D, 1D))
            .build();

        SpellBuilder.of(FROSTBALL_ID, SpellConfiguration.Builder.instant(7, 15)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Ice Ball",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .manualDesc(
                "Throw out a ball of ice, dealing " + SpellCalcs.ICEBALL.getLocSpellTooltip()
                    + " " + Elements.Water.getIconNameDmg())

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.SNOWBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 2D, 0.15D))
            .onHit(PartBuilder.damage(SpellCalcs.ICEBALL, Elements.Water))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 10D, 1D))
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

        SpellBuilder.of(POISONBALL_ID, SpellConfiguration.Builder.instant(7, 20)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Poison Ball",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .manualDesc(
                "Throw out a ball of poison, dealing " + SpellCalcs.POISON_BALL.getLocSpellTooltip()
                    + " " + Elements.Nature.getIconNameDmg())
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.SLIMEBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, PARTICLES.POISON, 1D, 0.15D))
            .onHit(PartBuilder.damage(SpellCalcs.POISON_BALL, Elements.Nature))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D))

            .build();

        SpellBuilder.of(FROST_NOVA_AOE, SpellConfiguration.Builder.instant(30, 25 * 20), "Frost Nova",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Explode with frost around you, dealing " + SpellCalcs.FROST_NOVA.getLocSpellTooltip()
                    + " " + Elements.Water.getIconNameDmg() + " to nearby enemies.")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.ITEM_SNOWBALL, 400D, 3.5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE_POP, 250D, 3.5D, 0.5D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_DROWNED_HURT, 0.5D, 1D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.FROST_NOVA, Elements.Water, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
            .build();

        SpellBuilder.of("teleport", SpellConfiguration.Builder.instant(20, 20 * 30)
                    .setScaleManaToPlayer(), "Teleport",
                Arrays.asList(SpellTag.damage, SpellTag.movement)
            )
            .manualDesc("Teleport yourself in the direction you're looking at.")

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 1D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))
            .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Atmosphere",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you for " + SpellCalcs.HEALING_AURA.getLocSpellTooltip() +
                    " health")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.BUFF, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_AURA, 2D))
            .build();

        SpellBuilder.of("wish", SpellConfiguration.Builder.instant(20, 20 * 60), "Wish",
                Arrays.asList(SpellTag.heal))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.BUFF, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 5D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.healInAoe(ValueCalculation.base("wish", 10), 5D))
            .build();

        SpellBuilder.breath("fire_breath", "Fire Breath", Elements.Fire, ParticleTypes.FLAME)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CAT_HISS, 1D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(10D)))
            .build();

        SpellBuilder.of("heal_ray", SpellConfiguration.Builder.instant(2, 1), "Healing Ray",
                Arrays.asList(SpellTag.heal))
            .onCast(PartBuilder.Particle.builder(ParticleTypes.SOUL_FIRE_FLAME, 1D, 0.5D)
                .set(MapField.MOTION, ParticleMotion.CasterLook.name())
                .set(MapField.HEIGHT, 0.1D)
                .build())
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 3D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)
                    .put(MapField.IS_SILENT, true)
                    .put(MapField.HITS_ALLIES, true))
                .addCondition(EffectCondition.CHANCE.create(20D)))
            .onHit(PartBuilder.healInAoe(ValueCalculation.base("breath_heal", 3), 2D))
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 0.3D, 2D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(10D)))
            .build();

        SpellBuilder.breath("frost_breath", "Frost Breath", Elements.Water, PARTICLES.FROST)
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

        SpellBuilder.of("awaken_mana", SpellConfiguration.Builder.instant(0, 300 * 20), "Awaken Mana",
                Arrays.asList(SpellTag.heal)
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(ValueCalculation.base("awaken_mana", 30)))

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

        SpellBuilder.of("nature_balm", SpellConfiguration.Builder.nonInstant(15, 60 * 20, 30)
                    .setScaleManaToPlayer(), "Rejuvenate",
                Arrays.asList(SpellTag.heal))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(3D, BeneficialEffects.REGENERATE.effectId, 20 * 15D))
            .build();

        SpellBuilder.of("overload", SpellConfiguration.Builder.nonInstant(10, 60 * 20, 30), "Overload",
                Arrays.asList())
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.OVERLOAD.effectId, 20 * 15D))
            .build();

        SpellBuilder.of("entangling_seed", SpellConfiguration.Builder.instant(15, 60 * 20)
                    .setSwingArm(), "Entangling Seed",
                Arrays.asList(SpellTag.area))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.BEETROOT_SEEDS, 1D, ENTITIES.SIMPLE_PROJECTILE, 40D)))

            .onExpire(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.PETRIFY, 5))
                .enemiesInRadius(3D))
            .onExpire(PartBuilder.groundParticles(ParticleTypes.LARGE_SMOKE, 50D, 3D, 0.25D))
            .onExpire(PartBuilder.groundParticles(ParticleTypes.ITEM_SLIME, 100D, 3D, 0.25D))
            .onExpire(PartBuilder.playSound(SOUNDS.STONE_CRACK, 1D, 1D))
            .build();

        SpellBuilder.of("storm_call", SpellConfiguration.Builder.nonInstant(10, 20 * 45, 30)
                , "Storm Call", Arrays.asList(SpellTag.area, SpellTag.damage))

            .manualDesc(
                "Summon a magic circle that attracts the storm, dealing " +
                    SpellCalcs.STORM_CALL.getLocSpellTooltip() + " " + Elements.Light.getIconNameDmg())

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 3)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.STORM_CALL, Elements.Light, 3D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.SUMMON_LIGHTNING_STRIKE.create())))

            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 3D, 1.2D, 0.5D)
                .onTick(3D))
            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, 3D, 1.2D, 0.5D)
                .onTick(3D))

            .build();
    }
}
