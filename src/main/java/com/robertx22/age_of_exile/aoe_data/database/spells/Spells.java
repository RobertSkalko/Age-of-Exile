package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spell_mods.SpellModifiers;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.*;

public class Spells implements ISlashRegistryInit {

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
    public static String BRAVERY_ID = "bravery";

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

    @Override
    public void registerAll() {

        SpellBuilder.of("awaken_mana", SpellConfiguration.Builder.instant(0, 300 * 20), "Awaken Mana")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(ValueCalculationData.base(30)))
            .build();

        SpellBuilder.of("arcane_bolt", SINGLE_TARGET_PROJ_CONFIG().setIsStarter(), "Arcane Bolt")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.ENDER_PEARL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.WITCH, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(8), Elements.Elemental))
            .build();

        SpellBuilder.of("arcane_comet", SpellConfiguration.Builder.instant(20, 20 * 30), "Arcane Comet")
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

        SpellBuilder.of("teleport", SpellConfiguration.Builder.instant(20, 20 * 30), "Teleport")
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_CASTER_IN_DIRECTION.create(12D)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(5), Elements.Elemental, 2D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.ENTITY_ENDERMAN_HURT, 1D, 1D)))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.ELE_RESIST, 20 * 10D))
            .build();

        SpellBuilder.of("magic_bomb", SpellConfiguration.Builder.nonInstant(15, 20 * 15, 20), "Magic Bomb")
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

        SpellBuilder.of(FROSTBALL_ID, SINGLE_TARGET_PROJ_CONFIG(), "Ice Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.ITEM_SNOWBALL, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(10), Elements.Water))
            .build();

        SpellBuilder.of(FIREBALL_ID, SINGLE_TARGET_PROJ_CONFIG(), "Fire Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.FIRE_CHARGE, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.FLAME, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(10), Elements.Fire))
            .build();

        SpellBuilder.of(POISONBALL_ID, SINGLE_TARGET_PROJ_CONFIG(), "Poison Ball")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SLIME_BALL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, false)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.ITEM_SLIME, 3D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(10), Elements.Nature))
            .build();

        SpellBuilder.of(THROW_FLAMES_ID, MULTI_TARGET_PROJ_CONFIG(), "Throw Flames")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_FIRECHARGE_USE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.BLAZE_POWDER, 3D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 50D, false)
                .put(MapField.PROJECTILES_APART, 45D)))
            .onTick(PartBuilder.particleOnTick(3D, ParticleTypes.FLAME, 5D, 0.15D))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.BURN, 1D, 20 * 3D)
                .addCondition(EffectCondition.CHANCE.create(25D))
                .requiresSpellMod(SpellModifiers.THROW_FLAMES_BURN))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.25F, 1), Elements.Fire))
            .build();

        SpellBuilder.of("tidal_wave", MULTI_TARGET_PROJ_CONFIG(), "Tidal Wave")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 5D, 0.6D, ENTITIES.SIMPLE_PROJECTILE, 40D, true)
                .put(MapField.PROJECTILES_APART, 50D)))
            .onTick(PartBuilder.particleOnTick(1D, PARTICLES.BUBBLE, 15D, 0.15D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.BUBBLE_POP, 15D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.25F, 4), Elements.Water))
            .build();

        SpellBuilder.of("thunder_storm", HIGH_AOE_LONG_CD(), "Thunderstorm")
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

        SpellBuilder.of("whirlpool", HIGH_AOE_LONG_CD(), "Whirlpool")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 0.5D)
                .put(MapField.EXPIRE_ON_HIT, false)
                .put(MapField.GRAVITY, true)))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.BUBBLE, 25D, 3.5D, 0.5D))
            .onTick(PartBuilder.tickGroundParticle(1D, ParticleTypes.BUBBLE_POP, 75D, 3.5D, 0.5D))
            .onTick(PartBuilder.playSoundEveryTicks(20D, SoundEvents.ENTITY_DROWNED_HURT, 0.5D, 1D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(3), Elements.Water, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
            .build();

        SpellBuilder.of("blizzard", HIGH_AOE_LONG_CD(), "Blizzard")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 100D, 4D)))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.CLOUD, 20D, 4D))
            .onTick(PartBuilder.tickCloudParticle(2D, ParticleTypes.ITEM_SNOWBALL, 20D, 4D))
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Water, 4D))
            .build();

        SpellBuilder.of("thorn_armor", SpellConfiguration.Builder.instant(15, 200 * 20), "Thorn Armor")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.THORN_ARMOR, 20 * 45D))
            .build();

        SpellBuilder.of("poisoned_weapons", SpellConfiguration.Builder.instant(15, 160 * 20), "Poison Weapons")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.POISON_WEAPONS, 20 * 30D))
            .build();

        SpellBuilder.of(MAGMA_FLOWER_ID, Spells.PLANT_CONFIG(), "Magma Flower")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onTick("projectile", PartBuilder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.FIRE_CORAL, 160D)))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick(PartBuilder.particleOnTick(30D, ParticleTypes.FLAME, 20D, 2D))
            .onTick(PartBuilder.playSoundEveryTicks(30D, SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onTick(PartBuilder.onTickDamageInAoe(30D, ValueCalculationData.base(3), Elements.Fire, 2D))
            .onTick(PartBuilder.healInAoe(ValueCalculationData.base(2), 2D)
                .onTick(30D)
                .requiresSpellMod(SpellModifiers.MAGMA_FLOWER_HEAL))
            .build();

        SpellBuilder.of(FLOWER_OF_ICE_ID, Spells.PLANT_CONFIG(), "Flower of Ice")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick("projectile", PartBuilder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.TUBE_CORAL, 160D)))
            .onTick(PartBuilder.particleOnTick(1D, PARTICLES.BUBBLE, 30D, 2D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.BUBBLE_POP, 30D, 2D))
            .onTick(PartBuilder.playSoundEveryTicks(30D, SoundEvents.ENTITY_GENERIC_SPLASH, 1D, 1D))
            .onTick(PartBuilder.onTickDamageInAoe(30D, ValueCalculationData.base(2), Elements.Water, 2D))
            .onTick(PartBuilder.restoreMagicShieldInRadius(ValueCalculationData.base(3), 2D)
                .onTick(30D))
            .onTick(PartBuilder.restoreManaInRadius(ValueCalculationData.base(2), 2D)
                .onTick(30D)
                .requiresSpellMod(SpellModifiers.ICE_FLOWER_MANA_RESTORE))
            .build();

        SpellBuilder.of(HOLY_FLOWER_ID, Spells.PLANT_CONFIG(), "Holy Flower")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onTick("projectile", PartBuilder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.HORN_CORAL, 160D)))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick(PartBuilder.onTickCleanseInRadius(30D, StatusEffects.POISON, 2D)
                .requiresSpellMod(SpellModifiers.HOLY_FLOWER_CLEANSE))
            .onTick(PartBuilder.particleOnTick(30D, ParticleTypes.HEART, 20D, 2D))
            .onTick(PartBuilder.playSoundEveryTicks(30D, SoundEvents.ITEM_CROP_PLANT, 1D, 1D))
            .onTick(PartBuilder.onTickHealInAoe(30D, ValueCalculationData.base(3), 2D))
            .build();

        SpellBuilder.of(THORN_BUSH_ID, Spells.PLANT_CONFIG(), "Poison Bush")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onTick("projectile", PartBuilder.particleOnTick(3D, ParticleTypes.HAPPY_VILLAGER, 3D, 0.15D))
            .onExpire("projectile", PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.BUBBLE_CORAL, 160D)))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.MELON_SEEDS, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 60D, true)
                .put(MapField.ENTITY_NAME, "projectile")))
            .onTick(PartBuilder.particleOnTick(30D, ParticleTypes.ITEM_SLIME, 60D, 2D))
            .onTick(PartBuilder.particleOnTick(30D, ParticleTypes.WITCH, 15D, 2D))
            .onTick(PartBuilder.playSoundEveryTicks(30D, SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1D, 2D))
            .onTick(PartBuilder.onTickDamageInAoe(30D, ValueCalculationData.base(1), Elements.Nature, 2D))
            .onTick(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.THORNS, 2D, 20 * 3D)
                .requiresSpellMod(SpellModifiers.THORN_BUSH_EFFECT))
            .build();

        SpellBuilder.of(HEART_OF_ICE_ID, SpellConfiguration.Builder.instant(15, 160 * 20), "Hear of Ice")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.FREEZE, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(10)))
            .onCast(PartBuilder.restoreMagicShieldToCaster(ValueCalculationData.base(10))
                .requiresSpellMod(SpellModifiers.HEART_OF_ICE_MAGIC_SHIELD))
            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Aura")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_HOE_TILL, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(ValueCalculationData.base(2), 2D))
            .build();

        SpellBuilder.of("blazing_inferno", SpellConfiguration.Builder.multiCast(20, 20 * 30, 60, 3), "Ring of Fire")
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
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(3D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.5F, 3), Elements.Physical))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
                .addCondition(EffectCondition.CASTER_HAS_POTION.create(POTIONS.getExileEffect(BeneficialEffects.IMBUE))))
            .build();

        SpellBuilder.of(THUNDERSPEAR_ID, SINGLE_TARGET_PROJ_CONFIG(), "Thunder Spear")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .build();

        SpellBuilder.of("spear_of_judgement", SpellConfiguration.Builder.nonInstant(15, 20 * 45, 40), "Spear of Judgement")
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createTrident(1D, 1.25D, 80D)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(6), Elements.Thunder))
            .onHit(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1D, 1D))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.JUDGEMENT, 1D, 80D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CLOUD, 15D, 0.015D))
            .build();

        SpellBuilder.of("thunder_dash", SpellConfiguration.Builder.instant(15, 20 * 30), "Thunder Dash")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.DASH, 1D, 1D))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.FORWARDS, DashUtils.Strength.LARGE_DISTANCE))
            .onCast(PartBuilder.damageInFront(ValueCalculationData.base(3), Elements.Thunder, 3D, 8D))
            .build();

        SpellBuilder.of("wizardry", DIVINE_BUFF_CONFIG(), "Wizardry")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveToAlliesInRadius(BeneficialEffects.WIZARDRY, 4D, 20 * 30D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
            .build();

        SpellBuilder.of("trickery", DIVINE_BUFF_CONFIG(), "Trickery")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveToAlliesInRadius(BeneficialEffects.TRICKERY, 4D, 20 * 30D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
            .build();

        SpellBuilder.of(BRAVERY_ID, DIVINE_BUFF_CONFIG(), "Bravery")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveToAlliesInRadius(BeneficialEffects.BRAVERY, 4D, 20 * 30D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.CLOUD, 20D, 4D, 0.2D))
            .build();

        SpellBuilder.of("purifying_fires", SpellConfiguration.Builder.instant(7, 20)
            .setSwingArm(), "Purifying Fires")
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(ValueCalculationData.scaleWithAttack(0.75F, 2), Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of("gorgons_gaze", SpellConfiguration.Builder.instant(15, 200 * 20), "Gorgon's Gaze")
            .onCast(PartBuilder.playSound(SOUNDS.STONE_CRACK, 1D, 1D))
            .onCast(PartBuilder.addExileEffectToEnemiesInFront(NegativeEffects.PETRIFY, 15D, 3D, 20 * 5D))
            .build();

        SpellBuilder.of("fire_bombs", SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Fire Bombs")
            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.COAL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, true)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 45D, 1D))
            .onHit(PartBuilder.damageInAoe(ValueCalculationData.base(10), Elements.Fire, 2D))
            .build();

        SpellBuilder.of("arrow_storm", SpellConfiguration.Builder.multiCast(25, 20 * 160, 60, 6), "Arrow Storm")
            .weaponReq(CastingWeapon.RANGED)
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

        SpellBuilder.of("imbue", SpellConfiguration.Builder.instant(15, 200 * 20), "Imbue")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.IMBUE, 20 * 30D))
            .build();

        SpellBuilder.of("nature_balm", SpellConfiguration.Builder.instant(15, 200 * 20), "Nature's Balm")
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
            .onTick(PartBuilder.onTickDamageInAoe(20D, ValueCalculationData.base(2), Elements.Fire, 3.5D))
            .build();

        SpellBuilder.of("passive_fire", SpellConfiguration.Builder.passive(), "Burning Rage")
            .onCast(PartBuilder.playSound(SOUNDS.FIREBALL, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.SMOKE, 100D, 5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.LAVA, 100D, 5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.FALLING_LAVA, 100D, 5D, 0.5D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(5), Elements.Fire, 5D))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.BURN, 5D, 20 * 80D))
            .build();

        SpellBuilder.of("passive_divine", SpellConfiguration.Builder.passive(), "Holy Protection")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 100D, 2D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(15)))
            .onCast(PartBuilder.giveSelfEffect(StatusEffects.REGENERATION, 100D))
            .onCast(PartBuilder.removeSelfEffect(StatusEffects.POISON))
            .onCast(PartBuilder.removeSelfEffect(StatusEffects.WITHER))
            .build();

        SpellBuilder.of("passive_storm", SpellConfiguration.Builder.passive(), "Sky Rage")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.CLOUD, 100D, 4D, 0.5D))
            .onCast(PartBuilder.groundParticles(PARTICLES.THUNDER, 100D, 4D, 0.5D))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(15), Elements.Thunder, 4D)
                .addPerEntityHit(PartBuilder.empty()
                    .addActions(SpellAction.SUMMON_LIGHTNING_STRIKE.create())))
            .build();

        SpellBuilder.of("passive_ocean", SpellConfiguration.Builder.passive(), "Regenerative Shell") // todo transfer power into over time regen to match name
            .onCast(PartBuilder.playSound(SOUNDS.FREEZE, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.BUBBLE_POP, 100D, 2D))
            .onCast(PartBuilder.aoeParticles(PARTICLES.BUBBLE, 100D, 2D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(5)))
            .onCast(PartBuilder.restoreMagicShieldToCaster(ValueCalculationData.base(10)))
            .onCast(PartBuilder.restoreManaToCaster(ValueCalculationData.base(10)))
            .onCast(PartBuilder.giveSelfEffect(StatusEffects.REGENERATION, 80D))
            .build();

        SpellBuilder.of("passive_nature", SpellConfiguration.Builder.passive(), "Gorgon's Patience")
            .onCast(PartBuilder.playSound(SOUNDS.STONE_CRACK, 1D, 1D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(5)))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 100D, 4D, 0.5D))
            .onCast(PartBuilder.groundParticles(PARTICLES.THUNDER, 100D, 4D, 0.5D))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.PETRIFY, 4D, 20 * 80D)
                .addPerEntityHit(PartBuilder.empty()
                    .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.ASH, 30D, 1D))))
            .build();

        SpellBuilder.of("passive_hunting", SpellConfiguration.Builder.passive(), "Unchained")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 100D, 2D))
            .onCast(PartBuilder.healCaster(ValueCalculationData.base(5)))
            .onCast(PartBuilder.removeSelfEffect(StatusEffects.SLOWNESS))
            .onCast(PartBuilder.removeSelfEffect(StatusEffects.BLINDNESS))
            .onCast(PartBuilder.giveSelfEffect(StatusEffects.SPEED, 300D))
            .build();

        SpellBuilder.of("passive_arcane", SpellConfiguration.Builder.passive(), "Magical Infusion")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ENDERMAN_SCREAM, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 100D, 3D))
            .onCast(PartBuilder.restoreMagicShieldToCaster(ValueCalculationData.base(20)))
            .onCast(PartBuilder.damageInAoe(ValueCalculationData.base(5), Elements.Elemental, 3D))
            .build();

        SpellBuilder.of("thunder_strikes", SpellConfiguration.Builder.multiCast(15, 20 * 15, 80, 4)
            .setSwingArm(), "Thunder Strikes")
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
