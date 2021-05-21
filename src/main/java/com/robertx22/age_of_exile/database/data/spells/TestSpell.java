package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TestSpell {

    public static String ID = "test_spell";

    public static Spell get() {
        return SpellBuilder.of(ID, SpellConfiguration.Builder.instant(7, 20)
                .setChargesAndRegen("trap", 3, 20 * 15)
                .setSwingArm(), "Frost Trap",
            Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.IRON_INGOT, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 100D, true)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(ModRegistry.BLOCKS.TRAP, 200D)
                .put(MapField.ENTITY_NAME, "trap")
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("trap", PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 5D, 1D)
                .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.enemiesInRadius(1D))
                .addActions(SpellAction.EXPIRE.create())
                .addActions(SpellAction.SPECIFIC_ACTION.create("expire"))
                .onTick(2D))

            .onExpire("trap", PartBuilder.justAction(SpellAction.SPECIFIC_ACTION.create("expire")))

            .addSpecificAction("expire", PartBuilder.damageInAoe(ValueCalculation.base("frost_trap", 10), Elements.Water, 3D))
            .addSpecificAction("expire", PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 30D, 3D))
            .buildForEffect();
    }
}
