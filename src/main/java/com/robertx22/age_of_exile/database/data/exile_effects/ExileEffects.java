package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.Spell.Builder;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.effect.StatusEffects;

public class ExileEffects implements ISlashRegistryInit {

    public static Integer ELE_WEAKNESS = 0;
    public static Integer ELE_RESIST = 1;
    public static Integer CLEANSE = 2;

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(ELE_WEAKNESS, "Ele Weakness")
                .stat(-15, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(ELE_WEAKNESS, "Ele Resist")
                .stat(15, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(CLEANSE, "Cleanse")
                .spell(Builder.forEffect()
                        .onTick(ComponentPart.PartBuilder.removeSelfEffect(StatusEffects.POISON).onTick(10D))
                        .buildForEffect())
                .build();


    }
}
