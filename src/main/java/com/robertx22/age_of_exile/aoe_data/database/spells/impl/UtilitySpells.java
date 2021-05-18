package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class UtilitySpells implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpellBuilder.of("conjure_ender_chest", SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 2, 40)
                .setScaleManaToPlayer(),
            "Conjure Ender Chest",
            Arrays.asList())
            .attackStyle(PlayStyle.magic)
            .onCast(PartBuilder.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.PORTAL, 100D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.OPEN_ENDER_CHEST.create()))
            .disableInDimension(DimensionIds.DUNGEON_DIMENSION)
            .build();

    }
}
