package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class RangerSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

        trap(SpellKeys.FROST_TRAP.id, "Frost Trap", ParticleTypes.ITEM_SNOWBALL, SpellCalcs.RANGER_TRAP).build();
        trap(SpellKeys.POISON_TRAP.id, "Poison Trap", ParticleTypes.ITEM_SLIME, SpellCalcs.RANGER_TRAP).build();
        trap(SpellKeys.FIRE_TRAP.id, "Fire Trap", ParticleTypes.FLAME, SpellCalcs.RANGER_TRAP).build();

        SpellBuilder.of(SpellKeys.EXPLOSIVE_ARROW,
                SpellConfiguration.Builder.arrowImbue(10, 20 * 10), "Explosive Arrow",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .manualDesc("Shoot an arrow that does ")
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))

            .onExpire(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 3D))
            .onExpire(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
            .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 2D, 1D))
            .onExpire(PartBuilder.damageInAoe(SpellCalcs.EXPLOSIVE_ARROW, 3D)
                .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D))))

            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
            .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.EXPLOSION, 1D, 0.1D))
            .onTick(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D)
                .onTick(2D))

            .build();

        SpellBuilder.of(SpellKeys.MAKE_ARROWS, SpellConfiguration.Builder.instant(10, 20 * 60 * 5)
                , "Produce Arrows",
                Arrays.asList())
            .manualDesc("Produce a stack of arrows.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ITEM_PICKUP))
            .onCast(PartBuilder.playSound(SoundRefs.DING))
            .onCast(PartBuilder.justAction(SpellAction.CASTER_USE_COMMAND.create("/give @s minecraft:arrow 64")))
            .build();
    }

    static SpellBuilder trap(String id, String name, BasicParticleType particle, ValueCalculation dmg) {

        return SpellBuilder.of(id, SpellConfiguration.Builder.instant(7, 100), name,
                Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .manualDesc(
                "Throw out a trap that stays on the ground and activates when an enemy approaches to deal damage in area around itself."
            )
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundRefs.FISHING_THROW))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.TRIPWIRE_HOOK, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 100D, true)))

            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.TRAP.get(), 20 * 4D)
                .put(MapField.ENTITY_NAME, "trap")
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("trap", PartBuilder.aoeParticles(particle, 5D, 0.5D)
                .onTick(2D))

            .onExpire("trap", PartBuilder.damageInAoe(dmg, 3D))
            .onExpire("trap", PartBuilder.aoeParticles(particle, 300D, 3D))
            .onExpire("trap", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 3D, 0.5D))
            .onExpire("trap", PartBuilder.aoeParticles(ParticleTypes.SMOKE, 100D, 3D))
            .onExpire("trap", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D));

    }
}
