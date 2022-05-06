package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return

            SpellBuilder.of(new SpellKey(USE_THIS_EXACT_ID),
                    // LEAVE THIS SPACE
                    SpellConfiguration.Builder.onJumpCritImbue(12, 20 * 45, 5)
                    , "Tidal Wave",
                    Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
                .manualDesc("Strike enemies in front of you")
                .attackStyle(PlayStyle.melee)
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.Sound.play(SoundEvents.GENERIC_SPLASH))

                .onCast(PartBuilder.Particles.swordSweep())

                .onCast(PartBuilder.Damage.inFront(SpellCalcs.TIDAL_WAVE, 2D, 3D)
                    .addPerEntityHit(PartBuilder.Particles.iceBurst(100D))
                    .addPerEntityHit(PartBuilder.Sound.play(SoundEvents.GENERIC_HURT))
                )
                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }

}
