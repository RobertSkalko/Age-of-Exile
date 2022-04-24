package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import net.minecraft.particles.ParticleTypes;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return
            SpellBuilder.of(USE_THIS_EXACT_ID,
                    // LEAVE THIS SPACE

                    SpellConfiguration.Builder.instant(20, 20 * 60), "Healing Aria",
                    Arrays.asList(SpellTag.heal))
                .manualDesc(
                    "Heal allies around you for")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundRefs.DING))
                .onCast(PartBuilder.groundParticles(ParticleTypes.NOTE, 50D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
                .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_ARIA, 5D))

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }
}
