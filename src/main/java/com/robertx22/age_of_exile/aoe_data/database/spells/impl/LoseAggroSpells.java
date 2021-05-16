package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class LoseAggroSpells implements ISlashRegistryInit {

    static ValueCalculation CALC = ValueCalculation.base("lose_aggro", 20);

    @Override
    public void registerAll() {

        SpellBuilder.of("veil_of_night", SpellConfiguration.Builder.instant(7, 20 * 60), "Veil of Night",
            Arrays.asList())
            .attackStyle(PlayStyle.ranged)
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WOLF_GROWL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(CALC, AggroAction.Type.DE_AGGRO))
                .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.VEIL_OF_NIGHT, 20D * 20))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 10D, 1.5D))
            .build();

        SpellBuilder.of("ethereal_form", SpellConfiguration.Builder.instant(7, 20 * 60), "Ethereal Form",
            Arrays.asList())
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CAT_HISS, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(CALC, AggroAction.Type.DE_AGGRO))
                .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.ETHEREAL_FORM, 20D * 20))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.SOUL, 10D, 1.5D))
            .build();

    }
}
