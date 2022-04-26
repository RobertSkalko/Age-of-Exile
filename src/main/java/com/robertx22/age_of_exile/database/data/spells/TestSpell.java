package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.entity_predicates.SpellEntityPredicate;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return

            SpellBuilder.of(new SpellKey(USE_THIS_EXACT_ID),
                    // LEAVE THIS SPACE

                    SpellConfiguration.Builder.arrowImbue(8, 20 * 15), "Frost Arrow",
                    Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

                .manualDesc(
                    "Shoot an arrow that goes through enemies and deals dmg in radius and slows.")

                .weaponReq(CastingWeapon.RANGED)
                .attackStyle(PlayStyle.ranged)
                .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.Sound.play(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                    .put(MapField.PROJECTILE_SPEED, 1D)
                    .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                    .put(MapField.GRAVITY, false)))

                .onTick(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 50D, 0.3D))

                .onTick(PartBuilder.Damage.aoe(SpellCalcs.FROST_ARROW, 6D)
                    .addEntityPredicate(SpellEntityPredicate.DID_NOT_AFFECT_BY_ENTITY.create())
                    .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.ITEM_SNOWBALL, 500d, 1D))
                    .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.INSTANT_EFFECT, 500d, 1D))
                    .addPerEntityHit(PartBuilder.Particles.aoe(ParticleTypes.FIREWORK, 50D, 1D))
                    .addPerEntityHit(PartBuilder.Sound.play(SoundRefs.HURT))
                    .addPerEntityHit(PartBuilder.Sound.play(SoundRefs.EXPLOSION))
                    .addPerEntityHit(PartBuilder.justAction(SpellAction.MARK_AS_AFFECTED_BY_ENTITY.create()))
                    .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 40D)))
                    .onTick(1D))

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }

}
