package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import net.minecraft.particles.ParticleTypes;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return
            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(15, 120 * 20), "Frost Armor",
                    Arrays.asList())
                .manualDesc("Give self effect:")
                .onCast(PartBuilder.playSound(SoundRefs.DING_LOW_PITCH))

                .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 150D, 2D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.CRIT, 25D, 2D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 100D, 2D))

                .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.FROST_ARMOR, 20 * 120D))
                .buildForEffect();

    }
}
