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
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class WaterSpells implements ExileRegistryInit {

    public static String HEART_OF_ICE = "heart_of_ice";
    public static String CHILLING_FIELD = "chilling_field";
    public static String CHILL_ERUPTION = "chill_eruption";

    @Override
    public void registerAll() {

        SpellBuilder.of(CHILL_ERUPTION, SpellConfiguration.Builder.instant(30, 25 * 20), "Chill Eruption",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Detonate every enemy affected by Chill to deal damage")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.GLASS_BREAK, 1D, 1D))

            .onCast(PartBuilder.Particles.groundEdge(ParticleTypes.ITEM_SNOWBALL, 250D, 5D, 0.5D))

            .onCast(PartBuilder.Damage.aoe(SpellCalcs.CHILL_ERUPTION, 5D)
                .addEntityPredicate(SpellEntityPredicate.HAS_EFFECT.create(NegativeEffects.CHILL))
                .addPerEntityHit(PartBuilder.Sound.playSoundPerTarget(SoundEvents.GLASS_HIT, 1D, 1D))
                .addPerEntityHit(PartBuilder.Sound.playSoundPerTarget(SoundEvents.GLASS_BREAK, 1D, 1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 100D, 1D))
                .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.ENCHANTED_HIT, 50D, 1D))
            )

            .build();

        SpellBuilder.of(CHILLING_FIELD, SpellConfiguration.Builder.instant(30, 20 * 60), "Chilling Field",
                Arrays.asList(SpellTag.damage, SpellTag.area))
            .weaponReq(CastingWeapon.ANY_WEAPON)

            .manualDesc("Freeze area of sight, applying chill and damaging enemies  every second.")

            .onCast(PartBuilder.Sound.play(SoundEvents.END_PORTAL_SPAWN, 1D, 1D))

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))
            .onTick("block", PartBuilder.Particles.ground(ParticleTypes.CLOUD, 20D, 2D, 0.2D))
            .onTick("block", PartBuilder.Sound.play(SoundEvents.HORSE_BREATHE, 1.1D, 1.5D)
                .onTick(20D))
            .onTick("block", PartBuilder.Damage.aoe(SpellCalcs.CHILLING_FIELD, 2D)
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
                "Heal allies around you")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.SPLASH, 50D, 5D, 0.2D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.DRIPPING_WATER, 50D, 5D, 0.2D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.HEART, 50D, 5D, 0.2D))
            .onCast(PartBuilder.Restore.Health.aoe(SpellCalcs.HEART_OF_ICE, 5D))
            .build();

    }
}
