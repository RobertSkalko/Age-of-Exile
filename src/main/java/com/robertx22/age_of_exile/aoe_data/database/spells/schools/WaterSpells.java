package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashParticles;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class WaterSpells implements ExileRegistryInit {
    public static String FROST_NOVA_AOE = "frost_nova";
    public static String FROSTBALL_ID = "frostball";

    public static String WATER_BREATH = "water_breath";
    public static String MAGE_CIRCLE = "mage_circle";
    public static String FROST_ARMOR = "frost_armor";
    public static String TIDAL_STRIKE = "tidal_strike";
    public static String NOURISHMENT = "nourishment";
    public static String HEART_OF_ICE = "heart_of_ice";
    public static String ICY_WEAPON = "ice_weapon";
    public static String CHILLING_FIELD = "chilling_field";

    @Override
    public void registerAll() {

        SpellBuilder.of(CHILLING_FIELD, SpellConfiguration.Builder.nonInstant(30, 20 * 60, 30)
                    .setSwingArm(), "Chilling Field",
                Arrays.asList(SpellTag.damage, SpellTag.area))
            .weaponReq(CastingWeapon.ANY_WEAPON)

            .manualDesc("Freeze area of sight, applying chill and damaging enemies for "
                + SpellCalcs.CHILLING_FIELD.getLocDmgTooltip()
                + Elements.Water.getIconNameDmg() + " every second.")

            .onCast(PartBuilder.playSound(SoundEvents.END_PORTAL_SPAWN, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.groundParticles(ParticleTypes.CLOUD, 20D, 2D, 0.2D))
            .onTick("block", PartBuilder.playSound(SoundEvents.HORSE_BREATHE, 1.1D, 1.5D)
                .onTick(20D))
            .onTick("block", PartBuilder.damageInAoe(SpellCalcs.CHILLING_FIELD, Elements.Water, 2D)
                .onTick(20D))
            .onTick("block", PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.CHILL.effectId, 2D, 20 * 4D)
                .onTick(20D))
            .build();

        SpellBuilder.of(ICY_WEAPON, SpellConfiguration.Builder.instant(10, 20 * 30), "Icy Weapon",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.ICY_WEAPON.effectId, 20 * 10D))
            .build();

        SpellBuilder.of(HEART_OF_ICE, SpellConfiguration.Builder.instant(20, 20 * 30), "Heart of Ice",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you for " + SpellCalcs.HEART_OF_ICE.getLocDmgTooltip() +
                    " health")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.SPLASH, 50D, 5D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.DRIPPING_WATER, 50D, 5D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.HEART_OF_ICE, 5D))
            .build();

        SpellBuilder.of(WATER_BREATH, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 5, 40)
                    .setScaleManaToPlayer(),
                "Water Breathing",
                Arrays.asList())
            .manualDesc(
                "Give Water Breathing to allies around you.")

            .attackStyle(PlayStyle.magic)
            .onCast(PartBuilder.playSound(SoundEvents.PLAYER_SPLASH, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 3D))
            .onCast(PartBuilder.giveEffectToAlliesInRadius(Effects.WATER_BREATHING, 20D * 60D * 3, 5D))
            .build();

        SpellBuilder.of(MAGE_CIRCLE, SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Mage Circle", Arrays.asList(SpellTag.movement))

            .manualDesc(
                "Summon a Magic Circle. Standing in it provides you a buff." +
                    " After a certain duration you will be teleported to its location.")

            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.GLYPH.get(), 20D * 10)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.CASTER.create()))
            .onExpire(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))

            .onTick("block", PartBuilder.giveSelfExileEffect(BeneficialEffects.MAGE_CIRCLE, 20D)
                .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.alliesInRadius(2D)))

            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 3D, 1.2D, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
            .build();

        SpellBuilder.of(FROST_ARMOR, SpellConfiguration.Builder.instant(15, 120 * 20), "Frost Armor",
                Arrays.asList())
            .manualDesc("Give self effect:")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.FROST_ARMOR, 20 * 120D))
            .build();

        SpellBuilder.of(TIDAL_STRIKE, SpellConfiguration.Builder.instant(8, 12)
                    .setSwingArm(), "Tidal Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front of you for " + SpellCalcs.TIDAL_STRIKE.getLocDmgTooltip(Elements.Water))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(SpellCalcs.TIDAL_STRIKE, Elements.Water, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.BUBBLE, 100D, 1D, 0.1D))
            )
            .build();

        SpellBuilder.of(FROSTBALL_ID, SpellConfiguration.Builder.instant(7, 15)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Ice Ball",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .manualDesc(
                "Throw out a ball of ice, dealing " + SpellCalcs.ICEBALL.getLocDmgTooltip()
                    + " " + Elements.Water.getIconNameDmg())

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.SNOWBALL.get(), 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 2D, 0.15D))
            .onHit(PartBuilder.damage(SpellCalcs.ICEBALL, Elements.Water))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 10D, 1D))
            .build();

        SpellBuilder.of(FROST_NOVA_AOE, SpellConfiguration.Builder.instant(30, 25 * 20), "Frost Nova",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Explode with frost around you, dealing " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                    + " " + Elements.Water.getIconNameDmg() + " to nearby enemies.")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.ITEM_SNOWBALL, 400D, 3.5D, 0.5D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE_POP, 250D, 3.5D, 0.5D))
            .onCast(PartBuilder.playSound(SoundEvents.DROWNED_HURT, 0.5D, 1D))
            .onCast(PartBuilder.damageInAoe(SpellCalcs.FROST_NOVA, Elements.Water, 3.5D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.DROWNED_HURT, 1D, 1D)))
            .build();

        SpellBuilder.breath("frost_breath", "Frost Breath", Elements.Water, SlashParticles.FROST.get())
            .build();

        SpellBuilder.of(NOURISHMENT, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Nourishment",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Apply buff to allies nearby ")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.NOURISHMENT.id, 20D * 25))
            .build();
    }
}
