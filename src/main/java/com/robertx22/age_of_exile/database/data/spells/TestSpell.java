package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return

            SpellBuilder.of(USE_THIS_EXACT_ID,
                    // LEAVE THIS SPACE
                    SpellConfiguration.Builder.instant(20, 100), "Ice Snake",
                    Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.staff_spell))
                .manualDesc(
                    "Summon an ice snake in your direction, slowing enemies.")
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundRefs.FISHING_THROW_LOW_PITCH))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 0.7D, SlashEntities.SIMPLE_PROJECTILE.get(), 60D, false)
                    .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                    .put(MapField.GRAVITY, false)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 5D, 0.5D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 5D, 0.3D))

                .onTick(PartBuilder.playSound(SoundRefs.ICE_BREAK))

                .onTick(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.ICE, 20D * 1)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, -0.02D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, true))
                    .onTick(2D))

                .onTick(PartBuilder.damageInAoe(SpellCalcs.ICE_SNAKE, 6D)
                    .addCondition(EffectCondition.DID_NOT_AFFECT_ENTITY_ALREADY.create())
                    .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_HURT))
                    .addPerEntityHit(PartBuilder.justAction(SpellAction.MARK_AS_AFFECTED_BY_ENTITY.create()))
                    .onTick(1D))
                .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 1D, 2D))

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }

}
