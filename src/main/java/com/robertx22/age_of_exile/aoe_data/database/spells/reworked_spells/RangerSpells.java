package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class RangerSpells implements ExileRegistryInit {

    @Override
    public void registerAll() {

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
}
