package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.entity_predicates.SpellEntityPredicate;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.BLOCKS;
import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {
        return

            SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(10, 20 * 45)
                .setScaleManaToPlayer(), "Mage Circle", Arrays.asList(SpellTag.movement))
                .onCast(PartBuilder.playSound(SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(ENTITIES.SIMPLE_PROJECTILE, 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(BLOCKS.GLYPH, 20D * 10)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.giveExileEffectToAlliesInRadius(1.2D, BeneficialEffects.MAGE_CIRCLE.effectId, 20D * 1)
                    .addEntityPredicate(SpellEntityPredicate.IS_CASTER.create())
                    .onTick(1D))

                .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                    .addActions(SpellAction.PLAY_SOUND.create(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1D, 1D))
                    .addTarget(TargetSelector.CASTER.create()))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 3D, 1.2D, 0.5D)
                    .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))

                .buildForEffect();

    }
}
