package com.robertx22.age_of_exile.aoe_data.database.spells.reworked_spells;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.NewSpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.SpellEntityBuilder;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.SoundRefs;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;

public class TestNewSpellBuilder {
    // todo remove later

    public static void reg() {

        NewSpellBuilder.of(SpellKeys.POISON_CLOUD, SpellConfiguration.Builder.instant(30, 25 * 20), "Poison Cloud")
            .desc("Erupt with poisonous gas, dealing damage to nearby enemies and spawning a poison cloud.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .tags(SpellTag.area, SpellTag.damage)
            .onCast(
                PartBuilder.playSound(SoundRefs.DING_LOW_PITCH),
                PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D))
            )
            .addEntity(SpellEntityBuilder.defaultId()
                .onExpire(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 10)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, true)
                    .put(MapField.IS_BLOCK_FALLING, false)))
                .onTick(1, PartBuilder.groundParticles(ParticleTypes.SNEEZE, 20D, 3D, 0.2D))
                .onTick(20, PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, 3D)
                    .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
                )
            )
            .build()

        ;

        /*
        SpellBuilder.of(SpellKeys.POISON_CLOUD, SpellConfiguration.Builder.instant(30, 25 * 20), "Poison Cloud",
                Arrays.asList(SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Erupt with poisonous gas, dealing damage to nearby enemies and spawning a poison cloud.")
            .weaponReq(CastingWeapon.ANY_WEAPON)

            .onCast(PartBuilder.playSound(SoundRefs.DING_LOW_PITCH))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

            .onExpire(PartBuilder.justAction(SpellAction.POTION_AREA_PARTICLES.create(TextFormatting.GREEN, 10)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, true)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onTick("block", PartBuilder.groundParticles(ParticleTypes.SNEEZE, 20D, 3D, 0.2D))
            .onTick("block", PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, 3D)
                .onTick(20D)
                .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D))
            )
            .build();

         */

    }
}
