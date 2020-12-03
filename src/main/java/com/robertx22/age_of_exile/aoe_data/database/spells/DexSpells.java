package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class DexSpells implements ISlashRegistryInit {
    public static SpellConfiguration SINGLE_TARGET_PROJ_CONFIG() {
        return SpellConfiguration.Builder.instant(7, 20);
    }

    public static String MULTI_SHOT_ID = "multi_shot";

    @Override
    public void registerAll() {

        SpellBuilder.of("imbue", SpellConfiguration.Builder.instant(15, 200 * 20), "Imbue",
            Arrays.asList())
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.IMBUE, 20 * 30D))
            .build();

        SpellBuilder.of("arrow_storm", SpellConfiguration.Builder.multiCast(25, 20 * 160, 60, 6), "Arrow Storm",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.RANGED)

            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Physical))
            .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
            )
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
            )
            .build();

        SpellBuilder.of("arrow_barrage", SpellConfiguration.Builder.multiCast(10, 20 * 25, 60, 6), "Arrow Barrage",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.RANGED)

            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.5F, 2), Elements.Physical))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
            )
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
            )
            .build();

        SpellBuilder.of("recoil_shot", SINGLE_TARGET_PROJ_CONFIG(), "Recoil Shot",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.RANGED)

            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.base(4), Elements.Physical))
            .onCast(PartBuilder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
            .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.WOUNDS, 1D, 20 * 20D))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
            )
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
            )
            .build();

        SpellBuilder.of(MULTI_SHOT_ID, SINGLE_TARGET_PROJ_CONFIG(), "Multi Shot",
            Arrays.asList(SkillGemTag.PROJECTILE, SkillGemTag.DAMAGE))
            .weaponReq(CastingWeapon.RANGED)

            .attackStyle(AttackPlayStyle.RANGED)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(3D, 1.2D, 80D, true)))
            .onHit(PartBuilder.damage(ValueCalculationData.scaleWithAttack(0.5F, 3), Elements.Physical))
            .onHit(PartBuilder.playSound(SoundEvents.ENTITY_ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.damage(ValueCalculationData.base(3), Elements.Elemental)
            )
            .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.WITCH, 5D, 0.1D)
            )
            .build();
    }
}
