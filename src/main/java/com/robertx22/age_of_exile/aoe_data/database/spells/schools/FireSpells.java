package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.MISC_ITEMS;

public class FireSpells implements ExileRegistryInit {
    public static String FIREBALL_ID = "fireball";
    public static String FIRE_NOVA_ID = "fire_nova";
    public static String FLAME_STRIKE_ID = "flame_strike";

    public static String OVERLOAD = "overload";
    public static String METEOR = "meteor";

    public static String VAMP_BLOOD = "vamp_blood";
    public static String DRACONIC_BLOOD = "draconic_blood";
    public static String FLAME_WEAPON = "fire_weapon";

    @Override
    public void registerAll() {

        SpellBuilder.of(FLAME_STRIKE_ID, SpellConfiguration.Builder.instant(8, 15)
                    .setSwingArm(), "Flame Strike",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc("Strike enemies in front for " +
                SpellCalcs.FLAME_STRIKE.getLocDmgTooltip(Elements.Fire))
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(SpellCalcs.FLAME_STRIKE, Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of(OVERLOAD, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Overload",
                Arrays.asList())
            .manualDesc("Gives effect to self.")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.OVERLOAD.effectId, 20 * 10D))
            .build();

        SpellBuilder.of(VAMP_BLOOD, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Vampiric BLood",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.VAMPIRIC_BLOOD.effectId, 20 * 60D))
            .build();

        SpellBuilder.of(DRACONIC_BLOOD, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Draconic BLood",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.DRACONIC_BLOOD.effectId, 20 * 60D))
            .build();

        SpellBuilder.of(FLAME_WEAPON, SpellConfiguration.Builder.instant(10, 20 * 30), "Flame Weapon",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.FIRE_WEAPON.effectId, 20 * 10D))
            .build();

        SpellBuilder.of(METEOR, SpellConfiguration.Builder.nonInstant(18, 20 * 30, 30), "Meteor",
                Arrays.asList(SpellTag.area, SpellTag.damage)
            )
            .manualDesc("Summon a meteor that falls from the sky, dealing " +
                SpellCalcs.METEOR.getLocDmgTooltip(Elements.Fire))

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 6D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, true)))
            .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
            .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.METEOR, Elements.Fire, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
            .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 1D))
            .onExpire("block", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))
            .build();

        SpellBuilder.of(FIRE_NOVA_ID, SpellConfiguration.Builder.instant(20, 20 * 25), "Fire Nova",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D))

            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 200D, 2.8D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 2D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 1D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.SMOKE, 200D, 1D, 0.05D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 0D, 0.2D))

            .onCast(PartBuilder.damageInAoe(SpellCalcs.FIRE_NOVA, Elements.Fire, 3D))
            .build();

        SpellBuilder.of(FIREBALL_ID, SpellConfiguration.Builder.instant(7, 20)
                    .setSwingArm()
                    .applyCastSpeedToCooldown(), "Fire Ball",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .manualDesc(
                "Throw out a ball of fire, dealing " + SpellCalcs.FIREBALL.getLocDmgTooltip()
                    + " " + Elements.Fire.getIconNameDmg())
            .weaponReq(CastingWeapon.MAGE_WEAPON)

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1D, 0.6D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(MISC_ITEMS.FIREBALL, 1D, 1D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FLAME, 1D, 0.1D))

            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FALLING_LAVA, 1D, 0.5D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 1D, 0.01D))

            .onHit(PartBuilder.damage(SpellCalcs.FIREBALL, Elements.Fire))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_BURN, 1D, 2D))
            .onHit(PartBuilder.aoeParticles(ParticleTypes.LAVA, 1D, 0.5D))
            .build();

    }
}
