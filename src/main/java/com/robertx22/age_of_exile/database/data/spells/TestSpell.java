package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {
        return SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(10, 20 * 1)
                .setSwingArm()
                .setChargesAndRegen("shred", 3, 20 * 15), "Shred",
            Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITHER_SKELETON_HURT, 1D, 1D))

            .onCast(PartBuilder.swordSweepParticles())
            .onCast(PartBuilder.nova(ParticleTypes.CRIT, 200D, 2.8D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.ENCHANTED_HIT, 100D, 2D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.CRIT, 100D, 1D, 0.05D))
            .onCast(PartBuilder.nova(ParticleTypes.ENCHANTED_HIT, 200D, 1D, 0.05D))

            .onCast(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.SHRED, 10))
                .enemiesInRadius(3D))
            .onCast(PartBuilder.damageInAoe(ValueCalculation.scaleWithAttack("shred", 0.5F, 5), Elements.Physical, 3D))

            .buildForEffect();

    }
}
