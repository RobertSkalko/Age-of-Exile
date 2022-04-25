package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.database.all_keys.base.SpellKey;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.block.Block;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.SoundEvents;

import java.util.List;

public class CommonSpellBuilders {

    public static Double DEFAULT_TOTEM_RADIUS = 3D;

    public static NewSpellBuilder totemSpell(Block block, SpellKey key, SpellConfiguration config, String name, List<SpellTag> tags, BasicParticleType particle) {

        return NewSpellBuilder.of(key, config, name)
            .tags(tags)
            .onCast(PartBuilder.Sound.play(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

            .addEntity(SpellEntityBuilder.defaultId()
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(block, 20D * 7.5D)
                    .put(MapField.ENTITY_NAME, "block")
                    .put(MapField.BLOCK_FALL_SPEED, 0D)
                    .put(MapField.FIND_NEAREST_SURFACE, false)
                    .put(MapField.IS_BLOCK_FALLING, false)))
            )

            .addEntity(SpellEntityBuilder.of("block")
                .onTick(PartBuilder.Sound.play(SoundEvents.NOTE_BLOCK_CHIME, 0.5D, 1D)
                    .addCondition(EffectCondition.EVERY_X_TICKS.create(20D)))
                .onTick(PartBuilder.Particles.tickAoe(2D, particle, 2D, 0.5D))
                .onTick(PartBuilder.Particles.groundEdge(particle, 50D, DEFAULT_TOTEM_RADIUS, 0.5D)
                    .addCondition(EffectCondition.EVERY_X_TICKS.create(20D))))

            ;

    }
}
