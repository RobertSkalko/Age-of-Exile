package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {
        return SpellBuilder.of(USE_THIS_EXACT_ID, SpellConfiguration.Builder.instant(5, 60 * 20 * 2)
                .setScaleManaToPlayer(),
            "Duel",
            Arrays.asList())
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.giveSelfEffect(ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, 20D * 10))
            .onCast(PartBuilder.giveSelfExileEffect(BeneficialEffects.EAGER, 20D * 10))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 2D, ENTITIES.SIMPLE_PROJECTILE, 20D, false)
                .put(MapField.IS_SILENT, true)
            ))
            .onHit(PartBuilder.justAction(SpellAction.POTION.createGive(StatusEffects.GLOWING, 20 * 10D))
                .addActions(SpellAction.POTION.createGive(ModRegistry.POTIONS.KNOCKBACK_RESISTANCE, 20 * 10D))
                .addTarget(TargetSelector.TARGET.create()))

            .buildForEffect();

    }
}
