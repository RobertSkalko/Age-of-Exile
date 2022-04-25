package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.NewSpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.SpellEntityBuilder;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return

            NewSpellBuilder.of(new SpellKey(USE_THIS_EXACT_ID),
                    // LEAVE THIS SPACE
                    SpellConfiguration.Builder.staffImbue(20, 20 * 15, 5), "Seeker Flames")
                .tags(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell)
                .desc(
                    "Summon a ball of flame seeking the targeted enemy.")
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.Sound.play(SoundEvents.BLAZE_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 30D, false)
                ))

                .addEntity(SpellEntityBuilder.defaultId()
                    .onTick(PartBuilder.Particles.tickAoe(4D, ParticleTypes.EFFECT, 1D, 0.1D))
                    .onTick(PartBuilder.Particles.tickAoe(1D, ParticleTypes.FLAME, 5D, 0.2D))

                    .onTick(PartBuilder.justAction(SpellAction.HOME_ON_TARGET.create()))

                    .onExpire(PartBuilder.Damage.aoe(SpellCalcs.SEEKER_FLAMES, 2D)
                        .addPerEntityHit(
                            PartBuilder.Particles.aoe(ParticleTypes.FLAME, 30D, 1D),
                            PartBuilder.Particles.aoe(ParticleTypes.SMOKE, 30D, 1D),
                            PartBuilder.Particles.aoe(ParticleTypes.FLAME, 30D, 1D),
                            PartBuilder.Sound.play(SoundEvents.GENERIC_HURT, 1D, 2D)
                        ))
                )

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }

}
