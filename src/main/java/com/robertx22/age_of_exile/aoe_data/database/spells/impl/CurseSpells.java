package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;

public class CurseSpells implements ISlashRegistryInit {

    static void curse(String id, String name, EffectCtx effect) {
        SpellBuilder.of(id, SpellConfiguration.Builder.nonInstant(10, 20 * 30, 50)
            , name,
            Arrays.asList(SpellTag.area, SpellTag.curse))
            .onCast(PartBuilder.playSound(SoundEvents.ENTITY_WITHER_SKELETON_HURT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 50D, 3D))
            .onCast(PartBuilder.addExileEffectToEnemiesInAoe(effect.effectId, 3D, 20 * 15D))
            .build();
    }

    @Override
    public void registerAll() {

        curse("curse_of_agony", "Curse of Agony", NegativeEffects.AGONY);
        curse("curse_of_weak", "Curse of Weakness", NegativeEffects.WEAKNESS);
        curse("curse_of_despair", "Curse of Despair", NegativeEffects.DESPAIR);

    }
}
