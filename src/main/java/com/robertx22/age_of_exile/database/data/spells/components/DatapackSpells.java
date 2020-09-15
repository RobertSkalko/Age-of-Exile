package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.Builder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registrators.SpellModifiers;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.BraveryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.JudgementEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.TrickeryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.WizardryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.*;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ember_mage.BurnEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.ImbueEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.WoundsEffect;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class DatapackSpells {

    public static String FROSTBALL_ID = "frostball";
    public static String FIREBALL_ID = "fireball";
    public static String POISONBALL_ID = "poison_ball";
    public static String THUNDERSPEAR_ID = "thunder_spear";
    public static String HEALING_AURA_ID = "healing_aura";
    public static String THORN_BUSH_ID = "thorn_bush";
    public static String HOLY_FLOWER_ID = "holy_flower";
    public static String THROW_FLAMES_ID = "throw_flames";
    public static String FLOWER_OF_ICE_ID = "flower_of_ice";
    public static String HEART_OF_ICE_ID = "heart_of_ice";
    public static String MAGMA_FLOWER_ID = "magma_flower";

    public static SpellConfiguration SINGLE_TARGET_PROJ_CONFIG() {
        return SpellConfiguration.Builder.instant(7, 20);
    }

    static SpellConfiguration MULTI_TARGET_PROJ_CONFIG() {
        return SpellConfiguration.Builder.instant(10, 25);
    }

    static SpellConfiguration HIGH_AOE_LONG_CD() {
        return SpellConfiguration.Builder.nonInstant(30, 120 * 20, 40);
    }

    static SpellConfiguration PLANT_CONFIG() {
        return SpellConfiguration.Builder.instant(25, 60 * 20);
    }

    static SpellConfiguration DIVINE_BUFF_CONFIG() {
        return SpellConfiguration.Builder.nonInstant(30, 20 * 180, 40);
    }

    public static void init() {

        Spell.Builder.of(FROSTBALL_ID, SINGLE_TARGET_PROJ_CONFIG(), "Ice Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(Builder.particleOnTick(3D, ParticleTypes.ITEM_SNOWBALL, 3D, 0.15D))
            .onHit(Builder.damage(ValueCalculationData.base(10), Elements.Water))
            .build();

        Spell.Builder.of(FIREBALL_ID, SINGLE_TARGET_PROJ_CONFIG(), "Fire Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.FIRE_CHARGE, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(Builder.particleOnTick(3D, ParticleTypes.FLAME, 3D, 0.15D))
            .onHit(Builder.damage(ValueCalculationData.base(10), Elements.Fire))
            .build();

        Spell.Builder.of(POISONBALL_ID, SINGLE_TARGET_PROJ_CONFIG(), "Poison Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SLIME_BALL, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(Builder.particleOnTick(3D, ParticleTypes.ITEM_SLIME, 3D, 0.15D))
            .onHit(Builder.damage(ValueCalculationData.base(10), Elements.Nature))
            .build();

        Spell.Builder.of(THROW_FLAMES_ID, MULTI_TARGET_PROJ_CONFIG(), "Throw Flames")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.BLAZE_POWDER, 3D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 50D, false)
                .put(MapField.PROJECTILES_APART, 30D)))
            .onTick(Builder.particleOnTick(3D, ParticleTypes.FLAME, 5D, 0.15D))
            .onHit(Builder.addExileEffectToEnemiesInAoe(BurnEffect.INSTANCE, 1D)
                .requiresSpellMod(SpellModifiers.THROW_FLAMES_BURN))
            .onHit(Builder.damage(ValueCalculationData.base(7), Elements.Fire))
            .build();

        Spell.Builder.of("tidal_wave", MULTI_TARGET_PROJ_CONFIG(), "Tidal Wave")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 5D, 0.6D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 40D, true)
                .put(MapField.PROJECTILES_APART, 50D)))
            .onTick(Builder.particleOnTick(1D, ModRegistry.PARTICLES.BUBBLE, 15D, 0.15D))
            .onTick(Builder.particleOnTick(1D, ParticleTypes.BUBBLE_POP, 15D, 0.15D))
            .onHit(Builder.damage(ValueCalculationData.scaleWithAttack(0.25F, 4), Elements.Water))
            .build();

        Spell.Builder.of("thunder_storm", HIGH_AOE_LONG_CD(), "Thunderstorm")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(Builder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(Builder.tickCloudParticle(2D, ParticleTypes.FALLING_WATER, 20D, 4D))
            .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Thunder, 4D)
                .addPerEntityHit(Builder.empty()
                    .addActions(SpellAction.SUMMON_LIGHTNING_STRIKE.create())
                    .addCondition(EffectCondition.CHANCE.create(20D))))
            .build();

        Spell.Builder.of("whirlpool", HIGH_AOE_LONG_CD(), "Whirlpool")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 0.5D)
                .put(MapField.EXPIRE_ON_HIT, false)
                .put(MapField.GRAVITY, true)))
            .onTick(Builder.tickGroundParticle(1D, ParticleTypes.BUBBLE, 25D, 3.5D, 0.5D))
            .onTick(Builder.tickGroundParticle(1D, ParticleTypes.BUBBLE_POP, 75D, 3.5D, 0.5D))
            .onTick(Builder.playSoundEveryTicks(20D, SoundEvents.ENTITY_DROWNED_HURT, 0.5D, 1D))
            .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Water, 3.5D)
                .addPerEntityHit(Builder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
            .build();

        Spell.Builder.of("blizzard", HIGH_AOE_LONG_CD(), "Blizzard")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(Builder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(Builder.tickCloudParticle(2D, ParticleTypes.ITEM_SNOWBALL, 20D, 4D))
            .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Water, 4D))
            .build();

        Spell.Builder.of("thorn_armor", SpellConfiguration.Builder.instant(15, 200 * 20), "Thorn Armor")
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveSelfExileEffect(ThornArmorEffect.INSTANCE))
            .build();

        Spell.Builder.of("poisoned_weapons", SpellConfiguration.Builder.instant(15, 160 * 20), "Poison Weapons")
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveSelfExileEffect(PoisonedWeaponsEffect.getInstance()))
            .build();

        Spell.Builder.of(MAGMA_FLOWER_ID, DatapackSpells.PLANT_CONFIG(), "Magma Flower")
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.FIRE_CORAL, 160D)))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick(Builder.particleOnTick(30D, ParticleTypes.FLAME, 20D, 2D))
            .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onTick(Builder.onTickDamageInAoe(30D, ValueCalculationData.base(3), Elements.Fire, 2D))
            .onTick(Builder.healInAoe(ValueCalculationData.base(2), 2D)
                .onTick(30D)
                .requiresSpellMod(SpellModifiers.MAGMA_FLOWER_HEAL))
            .build();

        Spell.Builder.of(FLOWER_OF_ICE_ID, DatapackSpells.PLANT_CONFIG(), "Flower of Ice")
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.TUBE_CORAL, 160D)))
            .onTick(Builder.particleOnTick(1D, ModRegistry.PARTICLES.BUBBLE, 30D, 2D))
            .onTick(Builder.particleOnTick(1D, ParticleTypes.BUBBLE_POP, 30D, 2D))
            .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.ENTITY_GENERIC_SPLASH, 1D, 1D))
            .onTick(Builder.restoreMagicShieldInRadius(ValueCalculationData.base(3), 2D)
                .onTick(30D))
            .onTick(Builder.restoreManaInRadius(ValueCalculationData.base(2), 2D)
                .onTick(30D)
                .requiresSpellMod(SpellModifiers.ICE_FLOWER_MANA_RESTORE))
            .build();

        Spell.Builder.of(HOLY_FLOWER_ID, DatapackSpells.PLANT_CONFIG(), "Holy Flower")
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.HORN_CORAL, 160D)))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick(Builder.onTickCleanseInRadius(30D, StatusEffects.POISON, 2D)
                .requiresSpellMod(SpellModifiers.HOLY_FLOWER_CLEANSE))
            .onTick(Builder.particleOnTick(30D, ParticleTypes.HEART, 20D, 2D))
            .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.ITEM_CROP_PLANT, 1D, 1D))
            .onTick(Builder.onTickHealInAoe(30D, ValueCalculationData.base(3), 2D))
            .build();

        Spell.Builder.of(THORN_BUSH_ID, DatapackSpells.PLANT_CONFIG(), "Poison Bush")
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.BUBBLE_CORAL, 160D)))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick(Builder.particleOnTick(30D, ParticleTypes.ITEM_SLIME, 60D, 2D))
            .onTick(Builder.particleOnTick(30D, ParticleTypes.WITCH, 15D, 2D))
            .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1D, 2D))
            .onTick(Builder.onTickDamageInAoe(30D, ValueCalculationData.base(1), Elements.Nature, 2D))
            .onTick(Builder.addExileEffectToEnemiesInAoe(PoisonEffect.INSTANCE, 2D)
                .requiresSpellMod(SpellModifiers.THORN_BUSH_EFFECT))
            .build();

        Spell.Builder.of(HEART_OF_ICE_ID, SpellConfiguration.Builder.instant(15, 160 * 20), "Hear of Ice")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(ModRegistry.SOUNDS.FREEZE, 1D, 1D))
            .onCast(Builder.aoeParticles(ParticleTypes.CLOUD, 40D, 1.5D))
            .onCast(Builder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(Builder.healCaster(ValueCalculationData.base(10)))
            .onCast(Builder.restoreMagicShieldToCaster(ValueCalculationData.base(10))
                .requiresSpellMod(SpellModifiers.HEART_OF_ICE_MAGIC_SHIELD))
            .build();

        Spell.Builder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Aura")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ITEM_HOE_TILL, 1D, 1D))
            .onCast(Builder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(Builder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(Builder.healInAoe(ValueCalculationData.base(2), 2D))
            .build();

        Spell.Builder.of("blazing_inferno", SpellConfiguration.Builder.multiCast(20, 20 * 30, 60, 3), "Ring of Fire")
            .onCast(Builder.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onCast(Builder.groundEdgeParticles(ParticleTypes.FLAME, 100D, 2.8D, 0.2D))
            .onCast(Builder.groundEdgeParticles(ParticleTypes.FLAME, 50D, 2D, 0.2D))
            .onCast(Builder.groundEdgeParticles(ParticleTypes.FLAME, 25D, 1D, 0.2D))
            .onCast(Builder.groundEdgeParticles(ParticleTypes.SMOKE, 200D, 3D, 0.2D))
            .onCast(Builder.damageInAoe(ValueCalculationData.base(3), Elements.Fire, 3D))
            .build();

        // it falls into ground
        Spell.Builder.of("lightning_totem", SpellConfiguration.Builder.nonInstant(12, 45 * 20, 20), "Lightning Totem")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.TOTEM_OF_UNDYING, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 120D, true)
                .put(MapField.EXPIRE_ON_HIT, false)))
            .onTick(Builder.particleOnTick(20D, ModRegistry.PARTICLES.THUNDER, 80D, 2D))
            .onTick(Builder.playSoundEveryTicks(20D, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
            .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Thunder, 2D))
            .build();

        Spell.Builder.of("arrow_barrage", SpellConfiguration.Builder.multiCast(10, 20 * 25, 60, 6), "Arrow Barrage")
            .weaponReq(CastingWeapon.RANGED)
            .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
            .onHit(Builder.damage(ValueCalculationData.scaleWithAttack(0.5F, 2), Elements.Physical))
            .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(Builder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .onTick(Builder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .build();

        Spell.Builder.of("recoil_shot", SINGLE_TARGET_PROJ_CONFIG(), "Recoil Shot")
            .weaponReq(CastingWeapon.RANGED)
            .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
            .onHit(Builder.damage(ValueCalculationData.base(4), Elements.Physical))
            .onCast(Builder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
            .onHit(Builder.addExileEffectToEnemiesInAoe(WoundsEffect.getInstance(), 1D))
            .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(Builder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .onTick(Builder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .build();

        Spell.Builder.of("multi_shot", SINGLE_TARGET_PROJ_CONFIG(), "Multi Shot")
            .weaponReq(CastingWeapon.RANGED)
            .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(3D, 1.2D, 80D, true)))
            .onHit(Builder.damage(ValueCalculationData.scaleWithAttack(0.5F, 3), Elements.Physical))
            .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(Builder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .onTick(Builder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .build();

        Spell.Builder.of(THUNDERSPEAR_ID, SINGLE_TARGET_PROJ_CONFIG(), "Thunder Spear")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(Builder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(Builder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .build();

        Spell.Builder.of("spear_of_judgement", SpellConfiguration.Builder.nonInstant(15, 20 * 45, 40), "Spear of Judgement")
            .onCast(Builder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(Builder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(Builder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .onHit(Builder.addExileEffectToEnemiesInAoe(JudgementEffect.INSTANCE, 1D))
            .onTick(Builder.particleOnTick(1D, ParticleTypes.CLOUD, 15D, 0.015D))
            .build();

        Spell.Builder.of("thunder_dash", SpellConfiguration.Builder.instant(15, 20 * 30), "Thunder Dash")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(Builder.playSound(ModRegistry.SOUNDS.DASH, 1D, 1D))
            .onCast(Builder.pushCaster(DashUtils.Way.FORWARDS, DashUtils.Strength.LARGE_DISTANCE))
            .onCast(Builder.damageInFront(ValueCalculationData.base(3), Elements.Thunder, 3D, 8D))
            .build();

        Spell.Builder.of("wizardry", DIVINE_BUFF_CONFIG(), "Wizardry")
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveToAlliesInRadius(WizardryEffect.INSTANCE, 4D))
            .onCast(Builder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
            .build();

        Spell.Builder.of("trickery", DIVINE_BUFF_CONFIG(), "Trickery")
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveToAlliesInRadius(TrickeryEffect.INSTANCE, 4D))
            .onCast(Builder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
            .build();

        Spell.Builder.of("bravery", DIVINE_BUFF_CONFIG(), "Bravery")
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveToAlliesInRadius(BraveryEffect.INSTANCE, 4D))
            .onCast(Builder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
            .build();

        Spell.Builder.of("purifying_fires", SpellConfiguration.Builder.instant(7, 20)
            .setSwingArm(), "Purifying Fires")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(Builder.swordSweepParticles())
            .onCast(Builder.damageInFront(ValueCalculationData.scaleWithAttack(0.75F, 2), Elements.Fire, 2D, 3D)
                .addPerEntityHit(Builder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        Spell.Builder.of("gorgons_gaze", SpellConfiguration.Builder.instant(15, 200 * 20), "Gorgon's Gaze")
            .onCast(Builder.playSound(ModRegistry.SOUNDS.STONE_CRACK, 1D, 1D))
            .onCast(Builder.addExileEffectToEnemiesInFront(PetrifyEffect.INSTANCE, 15D, 3D))
            .build();

        Spell.Builder.of("fire_bombs", SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Fire Bombs")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.COAL, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, true)))
            .onTick(Builder.particleOnTick(1D, ParticleTypes.SMOKE, 45D, 1D))
            .onHit(Builder.damageInAoe(ValueCalculationData.base(10), Elements.Fire, 2D))
            .build();

        Spell.Builder.of("arrow_storm", SpellConfiguration.Builder.multiCast(25, 20 * 160, 60, 6), "Arrow Storm")
            .weaponReq(CastingWeapon.RANGED)
            .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D, 1.2D, 80D, true)))
            .onHit(Builder.damage(ValueCalculationData.base(3), Elements.Physical))
            .onHit(Builder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(Builder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .onTick(Builder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(ImbueEffect.getInstance())))
            .build();

        Spell.Builder.of("imbue", SpellConfiguration.Builder.instant(15, 200 * 20), "Imbue")
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveSelfExileEffect(ImbueEffect.getInstance()))
            .build();

        Spell.Builder.of("nature_balm", SpellConfiguration.Builder.instant(15, 200 * 20), "Nature's Balm")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(Builder.giveSelfExileEffect(RegenerateEffect.INSTANCE))
            .build();

        Spell.Builder.of("volcano", HIGH_AOE_LONG_CD(), "Volcano")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(Builder.playSound(ModRegistry.SOUNDS.FIREBALL, 1D, 1D))
            .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(Builder.tickGroundParticle(1D, ParticleTypes.SMOKE, 10D, 3.5D, 0.5D))
            .onTick(Builder.tickGroundParticle(1D, ParticleTypes.LAVA, 10D, 3.5D, 0.5D))
            .onTick(Builder.tickGroundParticle(1D, ParticleTypes.FALLING_LAVA, 10D, 3.5D, 0.5D))
            .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Fire, 3.5D))
            .build();

    }
}
