package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ExileEffectActionBuilder;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.entity_predicates.SpellEntityPredicate;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class WaterSpells implements ExileRegistryInit {
    public static String FROST_NOVA_AOE = "frost_nova";

    public static String WATER_BREATH = "water_breath";
    public static String MAGE_CIRCLE = "mage_circle";
    public static String TIDAL_STRIKE = "tidal_strike";
    public static String NOURISHMENT = "nourishment";
    public static String HEART_OF_ICE = "heart_of_ice";
    public static String ICY_WEAPON = "ice_weapon";
    public static String CHILLING_FIELD = "chilling_field";
    public static String CHILL_ERUPTION = "chill_eruption";

    @Override
    public void registerAll() {

        SpellBuilder.of(CHILL_ERUPTION, SpellConfiguration.Builder.instant(30, 25 * 20), "Chill Eruption",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Detonate every enemy affected by Chill to deal " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                    + " " + Elements.Water.getIconNameDmg() + "")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.GLASS_BREAK, 1D, 1D))

            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ITEM_SNOWBALL, 250D, 5D, 0.5D))

            .onCast(PartBuilder.damageInAoe(SpellCalcs.CHILL_ERUPTION, Elements.Water, 5D)
                .addEntityPredicate(SpellEntityPredicate.HAS_EFFECT.create(NegativeEffects.CHILL))
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GLASS_HIT, 1D, 1D))
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GLASS_BREAK, 1D, 1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 100D, 1D))
                .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 50D, 1D))
            )

            .build();

        SpellBuilder.of(CHILLING_FIELD, SpellConfiguration.Builder.instant(30, 20 * 60), "Chilling Field",
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

            .onTick("block", new ExileEffectActionBuilder(NegativeEffects.CHILL).seconds(5)
                .radius(3)
                .targetEnemies()
                .build()
                .onTick(20D))

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

    }
}
