package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.NewSpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.SpellEntityBuilder;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonProjectileAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

public class TestSpell {

    public static String USE_THIS_EXACT_ID = "test_spell"; // USE THE EXACT "ID" HERE OR THE TEST SPELL GEM WONT WORK

    public static Spell get() {

        return

            NewSpellBuilder.of(new SpellKey(USE_THIS_EXACT_ID),
                    // LEAVE THIS SPACE
                    SpellConfiguration.Builder.instant(8, 20 * 15), "Poisoner Device")

                .tags(SpellTag.projectile, SpellTag.area, SpellTag.damage)

                .desc("todo")

                .weaponReq(CastingWeapon.RANGED)
                .attackStyle(PlayStyle.ranged)

                .onCast(PartBuilder.Sound.play(SoundEvents.ANVIL_PLACE))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)
                    .put(MapField.ENTITY_NAME, "sight")
                ))

                .addEntity(SpellEntityBuilder.of("sight")
                    .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.SMOKER, 20 * 6D)
                        .put(MapField.ENTITY_NAME, "trap")
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))
                )
                .addEntity(SpellEntityBuilder.of("trap")
                    .onTick(5, PartBuilder.Sound.play(SoundEvents.WITCH_THROW))

                    .onTick(5, PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(4D)
                            .put(MapField.GRAVITY, false)
                            .put(MapField.HEIGHT, 0.5)
                            .put(MapField.PITCH_ZERO, true)
                            .put(MapField.POS_SOURCE, SummonProjectileAction.PositionSource.SOURCE_ENTITY.name())
                            .put(MapField.ENTITY_NAME, "projectile")
                            .put(MapField.LIFESPAN_TICKS, 30D)
                            .put(MapField.PROJECTILES_APART, 360D)
                            .put(MapField.PROJECTILE_SPEED, 0.5D)
                        )
                    )
                )
                .addEntity(SpellEntityBuilder.of("projectile")
                    .onTick(PartBuilder.Particles.aoe(ParticleTypes.SNEEZE, 1D, 0.1D))

                    .onExpire(PartBuilder.Damage.aoe(SpellCalcs.POISON_ARROW, 1D))
                )

                // LEAVE THIS SPACE
                // leave this part
                .buildForEffect();

    }

}
