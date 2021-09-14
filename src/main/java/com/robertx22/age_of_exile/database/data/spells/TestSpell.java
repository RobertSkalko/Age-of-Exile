package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.PARTICLES;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {
        return
            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(7, 20)
                        .setSwingArm()
                        .applyCastSpeedToCooldown(), "Poison Ball",
                    Arrays.asList(SpellTag.projectile, SpellTag.damage))
                .manualDesc(
                    "Throw out a ball of poison, dealing " + SpellCalcs.POISON_BALL.getLocDmgTooltip()
                        + " " + Elements.Earth.getIconNameDmg())
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 5D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 40D, true)))
                .onTick(PartBuilder.particleOnTick(1D, PARTICLES.POISON, 1D, 0.15D))
                .onHit(PartBuilder.damage(SpellCalcs.POISON_BALL, Elements.Earth))
                .onHit(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D))

                .weight(0)
                .buildForEffect();

    }
}
