package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {
        return

            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(20, 20 * 30), "Teleport",
                Arrays.asList(SpellTag.damage, SpellTag.movement)
            )

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 1D)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, false)))
                .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                    .addTarget(TargetSelector.CASTER.create()))

                .onCast(PartBuilder.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 30D, 2D))

                .onCast(PartBuilder.damageInAoe(ValueCalculation.base("teleport", 8), Elements.Elemental, 2D)
                    .addPerEntityHit(PartBuilder.playSound(SoundEvents.ENTITY_ENDERMAN_HURT, 1D, 1D))
                )
                .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.ELE_RESIST.effectId, 20 * 10D)
                )

                .buildForEffect();

    }
}
