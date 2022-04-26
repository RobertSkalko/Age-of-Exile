package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return

            SpellBuilder.of(new SpellKey(USE_THIS_EXACT_ID),
                    // LEAVE THIS SPACE

                    SpellConfiguration.Builder.staffImbue(20, 20 * 15, 5)
                        .setEveryoneHas(), "Boulder Toss",
                    Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
                .manualDesc(
                    "Toss out multiple boulders, dealing heavy physical damage.")
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.Sound.play(SoundRefs.FISHING_THROW_LOW_PITCH))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.COBBLESTONE, 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 8D, false)
                    .put(MapField.SCALE, 7D)
                ))

                .onExpire(PartBuilder.Damage.aoe(SpellCalcs.MAGIC_PROJECTILE, 2D))
                .onExpire(PartBuilder.Sound.play(SoundEvents.GENERIC_HURT, 1D, 2D))
                .onExpire(PartBuilder.Sound.play(SoundEvents.GENERIC_EXPLODE))

                .onExpire(PartBuilder.Particles.aoe(ParticleTypes.SMOKE, 15D, 1D))
                .onExpire(PartBuilder.Particles.aoe(ParticleTypes.EXPLOSION, 3D, 1D))
                .onExpire(PartBuilder.Particles.aoe(ParticleTypes.POOF, 5D, 1D))
                .onExpire(PartBuilder.Particles.aoe(ParticleTypes.CRIT, 30D, 1D))

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }

}
