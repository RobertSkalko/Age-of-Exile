package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return
            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(30, 25 * 20), "Poison Cloud",
                    Arrays.asList(SpellTag.area, SpellTag.damage))
                .manualDesc(
                    "Erupt with poisonous gas, dealing " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                        + " " + Elements.Earth.getIconNameDmg() + " to nearby enemies and applying Poison.")
                .weaponReq(CastingWeapon.ANY_WEAPON)

                .onCast(PartBuilder.playSound(SoundRefs.DING_LOW_PITCH))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

                .onExpire(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 10)))

                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, true)
                    .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundParticles(ParticleTypes.SNEEZE, 20D, 3D, 0.2D))
                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, Elements.Earth, 3D)
                    .onTick(20D)
                    .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
                )
                // leave this part
                .buildForEffect();

    }
}
