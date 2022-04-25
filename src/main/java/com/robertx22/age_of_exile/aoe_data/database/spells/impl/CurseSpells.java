package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ExileEffectActionBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class CurseSpells implements ExileRegistryInit {

    static void curse(String id, String name, EffectCtx effect) {
        SpellBuilder.of(id, SpellConfiguration.Builder.nonInstant(10, 20 * 30, 20)
                    .setSwingArm()
                , name,
                Arrays.asList(SpellTag.area, SpellTag.curse))
            .manualDesc(
                "Curse enemies with " + effect.locname +
                    " and deal dmg")

            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
            .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 1D)
                .put(MapField.ENTITY_NAME, "block")
                .put(MapField.BLOCK_FALL_SPEED, 0D)
                .put(MapField.FIND_NEAREST_SURFACE, false)
                .put(MapField.IS_BLOCK_FALLING, false)))

            .onExpire("block", PartBuilder.Sound.play(SoundEvents.WITHER_SKELETON_HURT, 1D, 1D))
            .onExpire("block", PartBuilder.Damage.aoe(SpellCalcs.CURSE, 3D))

            .onExpire("block", new ExileEffectActionBuilder(effect).seconds(20)
                .radius(3)
                .targetEnemies()
                .build()
                .addPerEntityHit(
                    PartBuilder.justAction(SpellAction.PARTICLES_IN_RADIUS.create(ParticleTypes.SMOKE, 50D, 0.3D)
                        .put(MapField.HEIGHT, 2.2D)
                    )))
            .build();
    }

    @Override
    public void registerAll() {

        curse("curse_of_agony", "Curse of Agony", NegativeEffects.AGONY);
        curse("curse_of_weak", "Curse of Weakness", NegativeEffects.WEAKNESS);
        curse("curse_of_despair", "Curse of Despair", NegativeEffects.DESPAIR);

    }
}
