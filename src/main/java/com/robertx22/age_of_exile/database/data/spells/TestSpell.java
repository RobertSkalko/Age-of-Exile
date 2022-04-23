package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return
            SpellBuilder.of(USE_THIS_EXACT_ID,
                    // LEAVE THIS SPACE

                    SpellConfiguration.Builder.onJumpCritImbue(8, 100, 3)
                    , "Meteor Strike",
                    Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
                .manualDesc("Strike enemies in front for " +
                    SpellCalcs.MAGIC_PROJECTILE.getLocDmgTooltip(Elements.Fire)) // todo
                .attackStyle(PlayStyle.melee)
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1D))
                .onCast(PartBuilder.playSound(SoundRefs.EXPLOSION))
                .onCast(PartBuilder.swordSweepParticles())

                .onCast(PartBuilder.damageInFront(SpellCalcs.MAGIC_PROJECTILE, 2D, 3D)
                    .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D))
                    .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 1D))

                )

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }
}
