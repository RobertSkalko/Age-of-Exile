package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ENTITIES;

public class TrapSpells implements ExileRegistryInit {

    static SpellBuilder trap(String id, String name, DefaultParticleType particle, ValueCalculation dmg, Elements element) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(7, 20)
                    .setChargesAndRegen("trap", 3, 20 * 30)
                    .setSwingArm(), name,
                Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .manualDesc(
                "Throw out a trap that stays on the ground and activates when an enemy approaches to deal "
                    + dmg.getLocSpellTooltip() + element.getIconNameDmg() + " damage in area around itself."
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.IRON_INGOT, 1D, 0.5D, ENTITIES.SIMPLE_PROJECTILE, 100D, true)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(ModRegistry.BLOCKS.TRAP, 20 * 15D)
                .put(MapField.ENTITY_NAME, "trap")
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("trap", PartBuilder.aoeParticles(particle, 5D, 1D)
                .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.enemiesInRadius(1D))
                .addActions(SpellAction.EXPIRE.create())
                .addActions(SpellAction.SPECIFIC_ACTION.create("expire"))
                .onTick(2D))

            .addSpecificAction("expire", PartBuilder.damageInAoe(dmg, element, 3D))
            .addSpecificAction("expire", PartBuilder.aoeParticles(particle, 30D, 3D))
            .addSpecificAction("expire", PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1D, 1D));

    }

    @Override
    public void registerAll() {

        trap("frost_trap", "Frost Trap", ParticleTypes.ITEM_SNOWBALL, ValueCalculation.base("frost_trap", 10), Elements.Water).build();
        trap("poison_trap", "Poison Trap", ParticleTypes.ITEM_SLIME, ValueCalculation.base("poison_trap", 10), Elements.Earth).build();
        trap("fire_trap", "Fire Trap", ParticleTypes.FLAME, ValueCalculation.base("fire_trap", 10), Elements.Fire).build();

    }
}
