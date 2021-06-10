package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.BLOCKS;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {
        return

            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.nonInstant(30, 20 * 60, 30)
                    .setSwingArm(), "Black Hole",
                Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.area))
                .weaponReq(CastingWeapon.MAGE_WEAPON)

                .onCast(PartBuilder.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.BLACK_HOLE, 20D * 5)))

                .onTick("block", PartBuilder.particleOnTick(1D, ParticleTypes.PORTAL, 40D, 1D))
                .onTick("block", PartBuilder.particleOnTick(1D, ParticleTypes.WITCH, 8D, 1D))
                .onTick("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                    .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
                .onExpire("block", PartBuilder.damageInAoe(ValueCalculation.base("black_hole", 5), Elements.Dark, 2D))

                .buildForEffect();

    }
}
