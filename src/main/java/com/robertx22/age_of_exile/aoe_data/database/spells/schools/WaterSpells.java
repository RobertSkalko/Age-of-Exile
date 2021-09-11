package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
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
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.*;

public class WaterSpells implements ExileRegistryInit {
    public static String FROST_NOVA_AOE = "frost_nova";
    public static String FROSTBALL_ID = "frostball";

    @Override
    public void registerAll() {

        SpellBuilder.of("water_breath", SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 5, 40)
                    .setScaleManaToPlayer(),
                "Water Breathing",
                Arrays.asList())
            .manualDesc(
                "Give Water Breathing to allies around you.")

            .attackStyle(PlayStyle.magic)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_PLAYER_SPLASH, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 3D))
            .onCast(PartBuilder.giveEffectToAlliesInRadius(StatusEffects.WATER_BREATHING, 20D * 60D * 3, 5D))
            .build();

        SpellBuilder.of("mage_circle", SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Mage Circle", Arrays.asList(SpellTag.movement))

            .manualDesc(
                "Summon a Magic Circle. Standing in it provides you a buff." +
                    " After a certain duration you will be teleported to its location.")

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 10)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addTarget(TargetSelector.CASTER.create()))
            .onExpire(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))

            .onTick("block", PartBuilder.giveSelfExileEffect(BeneficialEffects.MAGE_CIRCLE, 20D)
                .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.alliesInRadius(2D)))

            .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 3D, 1.2D, 0.5D)
                .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
            .build();

        SpellBuilder.of("frost_armor", SpellConfiguration.Builder.instant(15, 120 * 20), "Frost Armor",
                Arrays.asList())
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.FROST_ARMOR, 20 * 120D))
            .build();

        SpellBuilder.of("tidal_strike", SpellConfiguration.Builder.instant(8, 12)
                    .setSwingArm(), "Tidal Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(SpellCalcs.TIDAL_STRIKE, Elements.Water, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                .addPerEntityHit(PartBuilder.groundEdgeParticles(PARTICLES.BUBBLE, 100D, 1D, 0.1D))
            )
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
        SpellBuilder.breath("frost_breath", "Frost Breath", Elements.Water, PARTICLES.FROST)
            .build();
    }
}
