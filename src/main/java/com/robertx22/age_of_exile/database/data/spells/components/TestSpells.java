package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.Builder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.PoisonedWeaponsEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.druid.ThornArmorEffect;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class TestSpells {

    static SpellConfiguration SINGLE_TARGET_PROJ_CONFIG = SpellConfiguration.Builder.instant(7, 20);
    static SpellConfiguration MULTI_TARGET_PROJ_CONFIG = SpellConfiguration.Builder.instant(10, 25);
    static SpellConfiguration HIGH_AOE_LONG_CD = SpellConfiguration.Builder.nonInstant(20, 120 * 20, 40);

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
        .onTick(Builder.cloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
        .onTick(Builder.cloudParticle(2D, ParticleTypes.FALLING_WATER, 20D, 4D))
        .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Thunder, 4D)
            .addChained(Builder.empty()
                .addTarget(BaseTargetSelector.AOE.create(4D, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES)
                    .put(MapField.SELECTION_CHANCE, 20D))
                .addActions(SpellAction.SUMMON_LIGHTNING_STRIKE.create())))
        .build();

    public static Spell WHIRLPOOL = Spell.Builder.of("whirlpool", HIGH_AOE_LONG_CD)
        .onCast(Builder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 0.5D)
            .put(MapField.EXPIRE_ON_HIT, false)
            .put(MapField.GRAVITY, true)))
        .onTick(Builder.groundParticle(1D, ParticleTypes.BUBBLE, 25D, 3.5D, 0.5D))
        .onTick(Builder.groundParticle(1D, ParticleTypes.BUBBLE_POP, 75D, 3.5D, 0.5D))
        .onTick(Builder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Water, 3.5D)
            .addChained(Builder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)
                .addTarget(BaseTargetSelector.AOE.create(3.5D, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES))))
        .build();

    public static Spell BLIZZARD = Spell.Builder.of("blizzard", HIGH_AOE_LONG_CD)
        .onCast(Builder.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1D, 1D))
        .onCast(Builder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
        .onTick(Builder.cloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
        .onTick(Builder.cloudParticle(2D, ParticleTypes.ITEM_SNOWBALL, 20D, 4D))
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

    // BIG PROBLEM
    public static Spell HEALING_FLOWER = Spell.Builder.of("healing_flower", SINGLE_TARGET_PROJ_CONFIG)
        .onCast(Builder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
        .onTick(Builder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
        .onCast(Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, false))
            .addChained(Builder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.ICE, 80D))))
        .build();

}
