package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
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
    public static String THUNDERSPEAR_ID = "thunder_spear";
    public static String HEALING_AURA_ID = "healing_aura";
    public static String HEART_OF_ICE_ID = "heart_of_ice";

    @Override
    public void registerAll() {

        SpellBuilder.of(FROSTBALL_ID, SpellConfiguration.Builder.instant(7, 15)
                .setSwingArm(), "Ice Ball",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.SNOWBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 2D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculation.base("iceball", 8), Elements.Water))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 10D, 1D))
            .build();

        SpellBuilder.of(FIREBALL_ID, SpellConfiguration.Builder.instant(7, 20)
                .setSwingArm(), "Fire Ball",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.FIREBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, PARTICLES.FLAME, 1D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculation.base("fireball", 8), Elements.Fire))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.LAVA, 5D, 1D))
            .build();

        SpellBuilder.of(POISONBALL_ID, SpellConfiguration.Builder.instant(7, 20)
                .setSwingArm(), "Poison Ball",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.SLIMEBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SLIME, 2D, 0.15D))
            .onHit(PartBuilder.damage(ValueCalculation.base("poisonball", 8), Elements.Nature))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D))

            .build();

        SpellBuilder.of("frost_nova", SpellConfiguration.Builder.instant(30, 25 * 20), "Frost Nova",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.ITEM_SNOWBALL, 400D, 3.5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE_POP, 250D, 3.5D, 0.5D))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_DROWNED_HURT, 0.5D, 1D))
            .onCast(PartBuilder.damageInAoe(ValueCalculation.base("frost_nova", 5), Elements.Water, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.ENTITY_DROWNED_HURT, 1D, 1D)))
            .build();

        SpellBuilder.of("teleport", SpellConfiguration.Builder.instant(20, 20 * 30), "Teleport",
            Arrays.asList(SkillGemTag.DAMAGE)
        )
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_CASTER_IN_DIRECTION.create(12D)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

            .onCast(PartBuilder.damageInAoe(ValueCalculation.base("teleport", 8), Elements.Elemental, 2D)
                .addPerEntityHit(PartBuilder.playSound(SoundEvents.ENTITY_ENDERMAN_HURT, 1D, 1D))
            )
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.ELE_RESIST, 20 * 10D)
            )

            .build();

        SpellBuilder.of(HEART_OF_ICE_ID, SpellConfiguration.Builder.instant(15, 60 * 20), "Heart of Ice",
            Arrays.asList(SkillGemTag.HEALING))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.FREEZE, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.healCaster(ValueCalculation.base("heart_of_ice", 15)))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.FROSTBURN, 5D, 20D * 10D))
            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Atmosphere",
            Arrays.asList(SkillGemTag.HEALING))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SOUNDS.BUFF, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(ValueCalculation.base("healing_aura", 4), 2D))
            .build();

        SpellBuilder.breath("fire_breath", "Fire Breath", Elements.Fire, PARTICLES.FLAME)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CAT_HISS, 1D, 1D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(10D)))
            .build();

        SpellBuilder.breath("frost_breath", "Frost Breath", Elements.Water, PARTICLES.FROST)
            .build();

        SpellBuilder.of("fire_nova", SpellConfiguration.Builder.instant(20, 20 * 25), "Fire Nova",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))

            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 200D, 2.8D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 2D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 1D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.SMOKE, 200D, 1D, 0.05D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 0D, 0.2D))

            .onCast(PartBuilder.damageInAoe(ValueCalculation.base("frost_breath", 5), Elements.Fire, 3D))
            .build();

        SpellBuilder.of("awaken_mana", SpellConfiguration.Builder.instant(0, 300 * 20), "Awaken Mana",
            Arrays.asList(SkillGemTag.HEALING)
        )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(ValueCalculation.base("awaken_mana", 30)))

            .build();

        SpellBuilder.of("meteor", SpellConfiguration.Builder.instant(18, 20 * 30), "Meteor",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE)
        )
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 6D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, true)))
            .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
            .onExpire("block", PartBuilder.damageInAoe(ValueCalculation.base("meteor", 10), Elements.Fire, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .build();

        SpellBuilder.of("nature_balm", SpellConfiguration.Builder.instant(15, 60 * 20), "Nature's Balm",
            Arrays.asList(SkillGemTag.HEALING))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.REGENERATE, 20 * 15D))
            .build();

        SpellBuilder.of("gorgons_gaze", SpellConfiguration.Builder.instant(15, 60 * 20), "Gorgon's Gaze",
            Arrays.asList(SkillGemTag.AREA))
            .onCast(PartBuilder.playSound(SOUNDS.STONE_CRACK, 1D, 1D))
            .onCast(PartBuilder.addExileEffectToEnemiesInFront(NegativeEffects.PETRIFY, 15D, 3D, 20 * 5D))
            .build();

        SpellBuilder.of("fire_bombs", SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Fire Bombs",
            Arrays.asList(SkillGemTag.AREA, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.COAL, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 80D, true)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 45D, 1D))
            .onHit(PartBuilder.damageInAoe(ValueCalculation.base("fire_bombs", 9), Elements.Fire, 2D))
            .build();

        SpellBuilder.of("levitation", SpellConfiguration.Builder.instant(1, 1)
                .setScaleManaToPlayer(), "Levitation",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_SMOKER_SMOKE, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.SET, 0.3D, ParticleMotion.Upwards))
                .addTarget(TargetSelector.CASTER.create()))

            .onCast(PartBuilder.particleOnTick(1D, ParticleTypes.SOUL, 5D, 0.5D))
            .onCast(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 5D, 0.5D))
            .build();

        SpellBuilder.of("shield_test", SpellConfiguration.Builder.instant(15, 20 * 5), "Shield Test",
            Arrays.asList(SkillGemTag.SHIELDING))
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.GIVE_DAMAGE_ABSORB.create(ValueCalculation.base("shield_test", 15), 10D))
                .addTarget(TargetSelector.CASTER.create()))
            .build();

    }
}
