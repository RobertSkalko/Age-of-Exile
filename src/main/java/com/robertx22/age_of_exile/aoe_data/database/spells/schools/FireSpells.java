package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class FireSpells implements ExileRegistryInit {
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
            .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1D))
            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.damageInFront(SpellCalcs.FLAME_STRIKE, Elements.Fire, 2D, 3D)
                .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
            .build();

        SpellBuilder.of(OVERLOAD, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Overload",
                Arrays.asList())
            .manualDesc("Gives effect to self.")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.OVERLOAD.resourcePath, 20 * 10D))
            .build();

        SpellBuilder.of(VAMP_BLOOD, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Vampiric BLood",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.VAMPIRIC_BLOOD.resourcePath, 20 * 60D))
            .build();

        SpellBuilder.of(DRACONIC_BLOOD, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Draconic BLood",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.DRACONIC_BLOOD.resourcePath, 20 * 60D))
            .build();

        SpellBuilder.of(FLAME_WEAPON, SpellConfiguration.Builder.instant(10, 20 * 30), "Flame Weapon",
                Arrays.asList())
            .manualDesc("Gives effect to nearby allies.")
            .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, BeneficialEffects.FIRE_WEAPON.resourcePath, 20 * 10D))
            .build();

        SpellBuilder.of(FIRE_NOVA_ID, SpellConfiguration.Builder.instant(20, 20 * 25), "Fire Nova",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .onCast(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 200D, 2.8D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 2D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 1D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.SMOKE, 200D, 1D, 0.05D))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 0D, 0.2D))

            .onCast(PartBuilder.damageInAoe(SpellCalcs.FIRE_NOVA, Elements.Fire, 3D))
            .build();

    }
}
