package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String ID = "test_spell";

    public static Spell get() {
        return SpellBuilder.of(ID, SpellConfiguration.Builder.instant(10, 15)
                .setScaleManaToPlayer()
                .setChargesAndRegen("dash", 3, 20 * 30)
                .setSwingArm(), "Dash",
            Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
            .weaponReq(CastingWeapon.NON_MAGE_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.CASTER_USE_COMMAND.create("effect give @p minecraft:slow_falling 1 1 true")))
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1D, 1.6D)
                .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.POOF, 20D, 1D)
                    .put(MapField.Y_RANDOM, 0.5D)
                    .put(MapField.MOTION, ParticleMotion.CasterLook.name())
                    .put(MapField.SET_ADD, SetAdd.ADD.name()))
                .addActions(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.WHITE_ASH, 20D, 1D)
                    .put(MapField.Y_RANDOM, 0.5D)
                    .put(MapField.MOTION, ParticleMotion.CasterLook.name())
                    .put(MapField.SET_ADD, SetAdd.ADD.name())))

            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 1D, ParticleMotion.CasterLook))
                .addTarget(TargetSelector.CASTER.create()))

            .buildForEffect();

    }
}
