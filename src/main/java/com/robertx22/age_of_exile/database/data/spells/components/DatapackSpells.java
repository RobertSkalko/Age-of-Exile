package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.Builder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.BraveryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.JudgementEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.TrickeryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.divine.WizardryEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonedWeaponsEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.ThornArmorEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ranger.WoundsEffect;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class DatapackSpells {

    public static SpellConfiguration SINGLE_TARGET_PROJ_CONFIG = SpellConfiguration.Builder.instant(7, 20);
    static SpellConfiguration MULTI_TARGET_PROJ_CONFIG = SpellConfiguration.Builder.instant(10, 25);
    static SpellConfiguration HIGH_AOE_LONG_CD = SpellConfiguration.Builder.nonInstant(30, 120 * 20, 40);
    static SpellConfiguration PLANT_CONFIG = SpellConfiguration.Builder.instant(25, 60 * 20);

    public static Spell FROSTBALL = Spell.Builder.of("frostball", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
        .onTick(Builder.particleOnTick(3D, ParticleTypes.ITEM_SNOWBALL, 3D, 0.15D))
        .onHit(Builder.damage(ValueCalculationData.base(10), Elements.Water))
        .build();

    public static Spell FIREBALL = Spell.Builder.of("fireball", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.FIRE_CHARGE, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
        .onTick(Builder.particleOnTick(3D, ParticleTypes.FLAME, 3D, 0.15D))
        .onHit(Builder.damage(ValueCalculationData.base(10), Elements.Fire))
        .build();

    public static Spell POISONBALL = Spell.Builder.of("poisonball", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SLIME_BALL, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
        .onTick(Builder.particleOnTick(3D, ParticleTypes.ITEM_SLIME, 3D, 0.15D))
        .onHit(Builder.damage(ValueCalculationData.base(10), Elements.Nature))
        .build();

    public static Spell THROW_FLAMES = Spell.Builder.of("throw_flames", MULTI_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.BLAZE_POWDER, 3D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 50D, false)
            .put(MapField.PROJECTILES_APART, 30D)))
        .onTick(Builder.particleOnTick(3D, ParticleTypes.FLAME, 5D, 0.15D))
        .onHit(Builder.damage(ValueCalculationData.base(7), Elements.Fire))
        .build();

    public static Spell TIDAL_WAVE = Spell.Builder.of("tidal_wave", MULTI_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 5D, 0.6D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 40D, true)
            .put(MapField.PROJECTILES_APART, 50D)))
        .onTick(Builder.particleOnTick(1D, ModRegistry.PARTICLES.BUBBLE, 15D, 0.15D))
        .onTick(Builder.particleOnTick(1D, ParticleTypes.BUBBLE_POP, 15D, 0.15D))
        .onHit(Builder.damage(ValueCalculationData.scaleWithAttack(0.25F, 4), Elements.Water))
        .build();

    public static Spell THUNDERSTORM = Spell.Builder.of("thunderstorm", HIGH_AOE_LONG_CD)
        .onCast(Builder.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
        .onTick(Builder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
        .onTick(Builder.tickCloudParticle(2D, ParticleTypes.FALLING_WATER, 20D, 4D))
        .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Thunder, 4D)
            .addChained(EntityActivation.PER_ENTITY_HIT, Builder.empty()
                .addActions(SpellAction.SUMMON_LIGHTNING_STRIKE.create())
                .addCondition(EffectCondition.CHANCE.create(20D))))
        .build();

    public static Spell WHIRLPOOL = Spell.Builder.of("whirlpool", HIGH_AOE_LONG_CD)
        .onCast(Builder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 0.5D)
            .put(MapField.EXPIRE_ON_HIT, false)
            .put(MapField.GRAVITY, true)))
        .onTick(Builder.tickGroundParticle(1D, ParticleTypes.BUBBLE, 25D, 3.5D, 0.5D))
        .onTick(Builder.tickGroundParticle(1D, ParticleTypes.BUBBLE_POP, 75D, 3.5D, 0.5D))
        .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Water, 3.5D)
            .addChained(EntityActivation.PER_ENTITY_HIT, Builder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
        .build();

    public static Spell BLIZZARD = Spell.Builder.of("blizzard", HIGH_AOE_LONG_CD)
        .onCast(Builder.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
        .onTick(Builder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
        .onTick(Builder.tickCloudParticle(2D, ParticleTypes.ITEM_SNOWBALL, 20D, 4D))
        .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Water, 4D))
        .build();

    public static Spell THORN_ARMOR = Spell.Builder.of("thorn_armor", SpellConfiguration.Builder.instant(15, 200 * 20))
        .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.giveSelfExileEffect(ThornArmorEffect.INSTANCE))
        .build();

    public static Spell POISON_WEAPONS = Spell.Builder.of("poisoned_weapons", SpellConfiguration.Builder.instant(15, 160 * 20))
        .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.giveSelfExileEffect(PoisonedWeaponsEffect.getInstance()))
        .build();

    public static Spell MAGMA_FLOWER = Spell.Builder.of("magma_flower", DatapackSpells.PLANT_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
        .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.FIRE_CORAL, 150D)))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
            .put(MapField.ENTITY_NAME, "projectile")))
        .onTick(Builder.particleOnTick(30D, ParticleTypes.FLAME, 20D, 2D))
        .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
        .onTick(Builder.onTickDamageInAoe(30D, ValueCalculationData.base(3), Elements.Fire, 2D))
        .build();

    public static Spell HOLY_FLOWER = Spell.Builder.of("holy_flower", DatapackSpells.PLANT_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
        .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.HORN_CORAL, 150D)))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
            .put(MapField.ENTITY_NAME, "projectile")))
        .onTick(Builder.particleOnTick(30D, ParticleTypes.HEART, 20D, 2D))
        .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.ITEM_CROP_PLANT, 1D, 1D))
        .onTick(Builder.onTickHealInAoe(30D, ValueCalculationData.base(3), 2D))
        .build();

    public static Spell POISON_BUSH = Spell.Builder.of("thorn_bush", DatapackSpells.PLANT_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onTick("projectile", Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
        .onExpire("projectile", Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.BUBBLE_CORAL, 150D)))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, true)
            .put(MapField.ENTITY_NAME, "projectile")))
        .onTick(Builder.particleOnTick(30D, ParticleTypes.ITEM_SLIME, 60D, 2D))
        .onTick(Builder.particleOnTick(30D, ParticleTypes.WITCH, 15D, 2D))
        .onTick(Builder.playSoundEveryTicks(30D, SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1D, 2D))
        .onTick(Builder.onTickDamageInAoe(30D, ValueCalculationData.base(1), Elements.Nature, 2D))
        .onTick(Builder.addExileEffectToEnemiesInAoe(PoisonEffect.INSTANCE, 2D))
        .build();

    public static Spell HEART_OF_ICE = Spell.Builder.of("heart_of_ice", SpellConfiguration.Builder.instant(15, 160 * 20))
        .onCast(Builder.playSound(ModRegistry.SOUNDS.FREEZE, 1D, 1D))
        .onCast(Builder.aoeParticles(ParticleTypes.CLOUD, 40D, 1.5D))
        .onCast(Builder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
        .onCast(Builder.healCaster(ValueCalculationData.base(10)))
        .build();

    public static Spell HEALING_AURA = Spell.Builder.of("healing_aura", SpellConfiguration.Builder.instant(20, 160 * 20))
        .onCast(Builder.playSound(SoundEvents.ITEM_HOE_TILL, 1D, 1D))
        .onCast(Builder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
        .onCast(Builder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
        .onCast(Builder.healInAoe(ValueCalculationData.base(2), 2D))
        .build();

    public static Spell BLAZING_INFERNO = Spell.Builder.of("blazing_inferno", SpellConfiguration.Builder.instant(20, 160 * 20))
        .onCast(Builder.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1D, 1D))
        .onCast(Builder.groundEdgeParticles(ParticleTypes.FLAME, 100D, 2.8D, 0.2D))
        .onCast(Builder.groundEdgeParticles(ParticleTypes.FLAME, 50D, 2D, 0.2D))
        .onCast(Builder.groundEdgeParticles(ParticleTypes.FLAME, 25D, 1D, 0.2D))
        .onCast(Builder.groundEdgeParticles(ParticleTypes.SMOKE, 200D, 3D, 0.2D))
        .onCast(Builder.damageInAoe(ValueCalculationData.base(3), Elements.Fire, 3D))
        .build();

    // it falls into ground
    public static Spell LIGHTNING_TOTEM = Spell.Builder.of("lightning_totem", MULTI_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.TOTEM_OF_UNDYING, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 120D, true)
            .put(MapField.EXPIRE_ON_HIT, false)))
        .onTick(Builder.particleOnTick(20D, ModRegistry.PARTICLES.THUNDER, 80D, 2D))
        .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Thunder, 2D))
        .build();

    public static Spell ARROW_BARRAGE = Spell.Builder.of("arrow_barrage", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
        .onHit(Builder.damage(ValueCalculationData.base(4), Elements.Physical))
        .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
        .build();

    public static Spell RECOIL_SHOT = Spell.Builder.of("recoil_shot", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
        .onHit(Builder.damage(ValueCalculationData.base(4), Elements.Physical))
        .onCast(Builder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
        .onHit(Builder.addExileEffectToEnemiesInAoe(WoundsEffect.getInstance(), 1D))
        .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
        .build();

    public static Spell MULTI_SHOT = Spell.Builder.of("multi_shot", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(3D, 1.2D, 80D, true)))
        .onHit(Builder.damage(ValueCalculationData.base(3), Elements.Physical))
        .onHit(Builder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
        .build();

    public static Spell THUNDER_SPEAR = Spell.Builder.of("thunder_spear", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
        .onHit(Builder.damage(ValueCalculationData.base(6), Elements.Thunder))
        .onHit(Builder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
        .build();

    public static Spell SPEAR_OF_JUDGEMENT = Spell.Builder.of("spear_of_judgement", HIGH_AOE_LONG_CD)
        .onCast(Builder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
        .onHit(Builder.damage(ValueCalculationData.base(6), Elements.Thunder))
        .onHit(Builder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
        .onHit(Builder.addExileEffectToEnemiesInAoe(JudgementEffect.INSTANCE, 1D))
        .onTick(Builder.particleOnTick(1D, ParticleTypes.CLOUD, 15D, 0.015D))
        .build();

    public static Spell THUNDER_DASH = Spell.Builder.of("thunder_dash", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(ModRegistry.SOUNDS.DASH, 1D, 1D))
        .onCast(Builder.pushCaster(DashUtils.Way.FORWARDS, DashUtils.Strength.LARGE_DISTANCE))
        .onCast(Builder.damageInFront(ValueCalculationData.base(3), Elements.Thunder, 3D, 8D))
        .build();

    static SpellConfiguration DIVINE_BUFF_CONFIG = SpellConfiguration.Builder.nonInstant(30, 20 * 180, 40);

    public static Spell WIZARDRY = Spell.Builder.of("wizardry", DIVINE_BUFF_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.giveToAlliesInRadius(WizardryEffect.INSTANCE, 4D))
        .onCast(Builder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
        .build();

    public static Spell TRICKERY = Spell.Builder.of("trickery", DIVINE_BUFF_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.giveToAlliesInRadius(TrickeryEffect.INSTANCE, 4D))
        .onCast(Builder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
        .build();

    public static Spell BRAVERY = Spell.Builder.of("bravery", DIVINE_BUFF_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.giveToAlliesInRadius(BraveryEffect.INSTANCE, 4D))
        .onCast(Builder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
        .build();

    public static Spell PURIFYING_FIRES = Spell.Builder.of("purifying_fires", DIVINE_BUFF_CONFIG)
        .onCast(Builder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
        .onCast(Builder.swordSweepParticles())
        .onCast(Builder.damageInFront(ValueCalculationData.scaleWithAttack(1F, 3), Elements.Fire, 2D, 3D)
            .addChained(EntityActivation.PER_ENTITY_HIT, Builder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
        .build();

}
