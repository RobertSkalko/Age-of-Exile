package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return
            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(0, 20), "Ice Nova",
                    Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
                .manualDesc(
                    "TODO " + SpellCalcs.MAGIC_PROJECTILE.getLocDmgTooltip()
                        + " " + Elements.Physical.getIconNameDmg())
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundRefs.EXPLOSION))

                .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 3D, 3D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 200D, 3D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.POOF, 20D, 3D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.CRIT, 20D, 3D))

                .onCast(PartBuilder.justAction(SpellAction.ICE_NOVA.create()))

                .onCast(PartBuilder.damageInAoe(SpellCalcs.MAGIC_PROJECTILE, Elements.Water, 3D)) // todo
                .onCast(PartBuilder.addEffectToEnemiesInAoe(Effects.MOVEMENT_SLOWDOWN, 3D, 20 * 6D)) // todo

                // leave this part
                .buildForEffect();

    }
}
