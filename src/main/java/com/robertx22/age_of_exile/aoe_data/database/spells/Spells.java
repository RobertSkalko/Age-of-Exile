package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.*;

public class Spells implements ISlashRegistryInit {

    public static Spell POISON_WEAPONS;
    public static Spell FROSTBALL;
    public static Spell FIREBALL;
    public static Spell POISONBALL;
    public static Spell THUNDER_SPEAR;
    public static Spell ARCANE_BALL;
    public static Spell ARCANE_COMET;
    public static Spell TELEPORT;
    public static Spell MAGIC_BOMB;
    public static Spell IMBUE;

    public static Spell FIRE_BOMBS;
    public static Spell INFERNO;
    public static Spell THUNDER_STORM;
    public static Spell WHIRLPOOL;
    public static Spell BLIZZARD;
    public static Spell THORN_ARMOR;
    public static Spell HEART_OF_ICE;
    public static Spell HEALING_AURA;
    public static Spell NATURE_BALM;
    public static Spell GORGON_GAZE;
    public static Spell SPEAR_OF_JUDGEMENT;
    public static Spell THUNDER_STRIKES;
    public static Spell AWAKEN_MANA;

    public static String FROSTBALL_ID = "frostball";
    public static String FIREBALL_ID = "fireball";
    public static String POISONBALL_ID = "poison_ball";
    public static String THUNDERSPEAR_ID = "thunder_spear";
    public static String HEALING_AURA_ID = "healing_aura";
    public static String HEART_OF_ICE_ID = "heart_of_ice";
    public static String BRAVERY_ID = "bravery";

    public static SpellConfiguration SINGLE_TARGET_PROJ_CONFIG() {
        return SpellConfiguration.Builder.instant(7, 20);
    }

    static SpellConfiguration HIGH_AOE_LONG_CD() {
        return SpellConfiguration.Builder.nonInstant(30, 120 * 20, 40);
    }

    static SpellConfiguration DIVINE_BUFF_CONFIG() {
        return SpellConfiguration.Builder.nonInstant(30, 20 * 180, 40);
    }

    @Override
    public void registerAll() {

        AWAKEN_MANA = SpellBuilder.of("awaken_mana", SpellConfiguration.Builder.instant(0, 300 * 20), "Awaken Mana")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(ValueCalculationData.base(30)))

            .build();

        ARCANE_BALL = SpellBuilder.of("arcane_bolt", SpellConfiguration.Builder.instant(7, 6), "Arcane Bolt")
            .projectile()
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.ENDER_PEARL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.WITCH, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(8), Elements.Elemental))
            .build();

        ARCANE_COMET = SpellBuilder.of("arcane_comet", SpellConfiguration.Builder.instant(18, 20 * 30), "Arcane Comet")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 6D)
                .put(MapField.HEIGHT, 14D)
            ))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.BUBBLE_CORAL_BLOCK, 200D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, -0.02D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, true)))
            .onTick("block", PartBuilder.particleOnTick(3D, ParticleTypes.WITCH, 25D, 0.5D))
            .onExpire("block", PartBuilder.damageInAoe(ValueCalculationData.base(12), Elements.Elemental, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.WITCH, 150D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .build();

        TELEPORT = SpellBuilder.of("teleport", SpellConfiguration.Builder.instant(20, 20 * 30), "Teleport")
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_CASTER_IN_DIRECTION.create(12D)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(8), Elements.Elemental, 2D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.ENTITY_ENDERMAN_HURT, 1D, 1D))
            )
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.ELE_RESIST, 20 * 10D)
            )

            .build();

        MAGIC_BOMB = SpellBuilder.of("magic_bomb", SpellConfiguration.Builder.nonInstant(20, 20 * 15, 20), "Magic Bomb")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITCH_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.ENDERMITE_SPAWN_EGG, 1D, 0.2D, ENTITIES.SIMPLE_PROJECTILE, 80D, true)
                .put(MapField.EXPIRE_ON_HIT, false)))
            .onTick(PartBuilder.particleOnTick(2D, ParticleTypes.WITCH, 12D, 0.15D))
            .onTick(PartBuilder.playSoundEveryTicks(20D, SoundEvents.ENTITY_ENDERMITE_STEP, 1D, 1D))
            .onExpire(PartBuilder.damageInAoe(ValueCalculationData.base(12), Elements.Elemental, 2D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.WITCH, 150D, 2D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
            .onExpire(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .build();

        FROSTBALL = SpellBuilder.of(FROSTBALL_ID, SpellConfiguration.Builder.instant(7, 5), "Ice Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .projectile()
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.ITEM_SNOWBALL, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(10), Elements.Water))

            .onHit(PartBuilder.exileEffect(NegativeEffects.FROSTBURN, 20 * 10D))

            .build();

        FIREBALL = SpellBuilder.of(FIREBALL_ID, SpellConfiguration.Builder.instant(7, 5), "Fire Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .projectile()
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.FIRE_CHARGE, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.FLAME, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(11), Elements.Fire))

            .onHit(PartBuilder.exileEffect(NegativeEffects.BURN, 20 * 10D)
                .addCondition(EffectCondition.CHANCE.create(20D)))

            .build();

        POISONBALL = SpellBuilder.of(POISONBALL_ID, SpellConfiguration.Builder.nonInstant(7, 0, 20), "Poison Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .projectile()
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SLIME_BALL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.ITEM_SLIME, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(10), Elements.Nature))

            .onHit(PartBuilder.exileEffect(NegativeEffects.THORNS, 20 * 10D)
                .addCondition(EffectCondition.CHANCE.create(20D)))

            .build();

        THUNDER_STORM = SpellBuilder.of("thunder_storm", HIGH_AOE_LONG_CD(), "Thunderstorm")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.FALLING_WATER, 20D, 4D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Thunder, 4D)
                .addPerEntityHit(PartBuilder.empty()
                    .addActions(SpellAction.SUMMON_LIGHTNING_STRIKE.create())
                    .addCondition(EffectCondition.CHANCE.create(20D))))
            .build();

        WHIRLPOOL = SpellBuilder.of("whirlpool", SpellConfiguration.Builder.multiCast(30, 120 * 20, 60, 6), "Whirlpool")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE, 200D, 3.5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE_POP, 250D, 3.5D, 0.5D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_DROWNED_HURT, 0.5D, 1D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(3), Elements.Water, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
            .build();

        BLIZZARD = SpellBuilder.of("blizzard", HIGH_AOE_LONG_CD(), "Blizzard")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.ITEM_SNOWBALL, 20D, 4D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Water, 4D))
            .build();

        THORN_ARMOR = SpellBuilder.of("thorn_armor", SpellConfiguration.Builder.instant(15, 200 * 20), "Thorn Armor")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.THORN_ARMOR, 20 * 45D))
            .build();

        SpellBuilder.of("frost_armor", SpellConfiguration.Builder.instant(15, 120 * 20), "Frost Armor")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.FROST_ARMOR, 20 * 120D))
            .build();

        POISON_WEAPONS = SpellBuilder.of("poisoned_weapons", SpellConfiguration.Builder.instant(15, 160 * 20), "Poison Weapons")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.POISON_WEAPONS, 20 * 30D))
            .build();

        HEART_OF_ICE = SpellBuilder.of(HEART_OF_ICE_ID, SpellConfiguration.Builder.instant(15, 160 * 20), "Heart of Ice")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.FREEZE, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(15)))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.FROSTBURN, 5D, 20D * 10D))
            .build();

        HEALING_AURA = SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Aura")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_HOE_TILL, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(ValueCalculationData.base(4), 2D))
            .build();

        INFERNO = SpellBuilder.of("blazing_inferno", SpellConfiguration.Builder.multiCast(20, 20 * 30, 60, 3), "Ring of Fire")
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 100D, 2.8D, 0.2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 25D, 1D, 0.2D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.SMOKE, 200D, 3D, 0.2D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(3), Elements.Fire, 3D))
            .build();

        // it falls into ground
        SpellBuilder.of("lightning_totem", SpellConfiguration.Builder.nonInstant(12, 45 * 20, 20), "Lightning Totem")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.TOTEM_OF_UNDYING, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 120D, true)
                .put(MapField.EXPIRE_ON_HIT, false)))
            .onTick(PartBuilder.particleOnTick(20D, PARTICLES.THUNDER, 80D, 2D))
            .onTick(PartBuilder.playSoundEveryTicks(20D, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Thunder, 2D))
            .build();

        SpellBuilder.of("arrow_barrage", SpellConfiguration.Builder.multiCast(10, 20 * 25, 60, 6), "Arrow Barrage")
            .weaponReq(CastingWeapon.RANGED)
            .projectile()
            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.5F, 2), Elements.Physical))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .build();

        SpellBuilder.of("recoil_shot", SINGLE_TARGET_PROJ_CONFIG(), "Recoil Shot")
            .weaponReq(CastingWeapon.RANGED)
            .projectile()
            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(4), Elements.Physical))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.WOUNDS, 1D, 20 * 20D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .build();

        SpellBuilder.of("multi_shot", SINGLE_TARGET_PROJ_CONFIG(), "Multi Shot")
            .weaponReq(CastingWeapon.RANGED)
            .projectile()
            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(3D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.5F, 3), Elements.Physical))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .build();

        THUNDER_SPEAR = SpellBuilder.of(THUNDERSPEAR_ID, SINGLE_TARGET_PROJ_CONFIG(), "Thunder Spear")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .projectile()
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .build();

        SPEAR_OF_JUDGEMENT = SpellBuilder.of("spear_of_judgement", SpellConfiguration.Builder.nonInstant(15, 20 * 45, 40), "Spear of Judgement")
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .projectile()
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.JUDGEMENT, 1D, 80D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CLOUD, 15D, 0.015D))
            .build();

        SpellBuilder.of("thunder_dash", SpellConfiguration.Builder.instant(15, 20 * 30), "Thunder Dash")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(AttackPlayStyle.MELEE)
            .onCast(PartBuilder.playSound(SOUNDS.DASH, 1D, 1D))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.FORWARDS, DashUtils.Strength.LARGE_DISTANCE))
            .onCast(PartBuilder.damageInFront(ValueCalculationData.base(3), Elements.Thunder, 3D, 8D))
            .build();

        SpellBuilder.of("purifying_fires", SpellConfiguration.Builder.instant(8, 15)
            .setSwingArm(), "Purifying Fires")
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculationData.scaleWithAttack(0.5F, 1), Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("tidal_strike", SpellConfiguration.Builder.instant(8, 12)
            .setSwingArm(), "Tidal Strike")
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

        GORGON_GAZE = SpellBuilder.of("gorgons_gaze", SpellConfiguration.Builder.instant(15, 200 * 20), "Gorgon's Gaze")
            .onCast(PartBuilder.playSound(SOUNDS.STONE_CRACK, 1D, 1D))
            .onCast(PartBuilder.addExileEffectToEnemiesInFront(NegativeEffects.PETRIFY, 15D, 3D, 20 * 5D))
            .build();

        FIRE_BOMBS = SpellBuilder.of("fire_bombs", SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Fire Bombs")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .projectile()
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.COAL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, true)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 45D, 1D))
            .onHit(PartBuilder.damageInAoe(ValueCalculationData.base(9), Elements.Fire, 2D))
            .build();

        SpellBuilder.of("arrow_storm", SpellConfiguration.Builder.multiCast(25, 20 * 160, 60, 6), "Arrow Storm")
            .weaponReq(CastingWeapon.RANGED)
            .projectile()
            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Physical))
            .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .build();

        IMBUE = SpellBuilder.of("imbue", SpellConfiguration.Builder.instant(15, 200 * 20), "Imbue")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.IMBUE, 20 * 30D))

            .build();

        NATURE_BALM = SpellBuilder.of("nature_balm", SpellConfiguration.Builder.instant(15, 60 * 20), "Nature's Balm")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.REGENERATE, 20 * 15D))
            .build();

        SpellBuilder.of("volcano", HIGH_AOE_LONG_CD(), "Volcano")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.FIREBALL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.SMOKE, 10D, 3.5D, 0.5D))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.LAVA, 10D, 3.5D, 0.5D))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.FALLING_LAVA, 10D, 3.5D, 0.5D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Fire, 3.5D))
            .build();

        THUNDER_STRIKES = SpellBuilder.of("thunder_strikes", SpellConfiguration.Builder.multiCast(15, 20 * 15, 80, 4)
            .setSwingArm(), "Thunder Strikes")
            .attackStyle(AttackPlayStyle.MELEE)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculationData.scaleWithAttack(0.5F, 1), Elements.Thunder, 2D, 3D)
                .addPerEntityHit(PartBuilder.cloudParticles(ParticleTypes.CRIT, 5D, 1D, 0.2D))
                .addPerEntityHit(PartBuilder.cloudParticles(ParticleTypes.CLOUD, 15D, 1D, 0.2D))
                .addPerEntityHit(PartBuilder.cloudParticles(PARTICLES.THUNDER, 100D, 1D, 0.2D))
            )
            .build();

    }
}
