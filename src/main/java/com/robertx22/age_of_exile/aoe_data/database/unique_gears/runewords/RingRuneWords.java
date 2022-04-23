package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.all_keys.BaseGearKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class RingRuneWords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                "air_disaster",
                "Aria of Disaster",
                BaseGearKeys.RING)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(15, 30, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT),
                new StatModifier(5, 15, Stats.AREA_DAMAGE.get(), ModType.FLAT),
                new StatModifier(3, 10, ManaRegen.getInstance(), ModType.FLAT)
            ))
            .makeRuneWordOnly()
            .devComment("song buffer / area damage")
            .build();

        UniqueGearBuilder.of(
                "playful_hope",
                "Playful Hope",
                BaseGearKeys.RING)
            .setReplacesName()
            .stats(Arrays.asList(
                new StatModifier(-75, 25, GlobalCriticalDamage.getInstance()),
                new StatModifier(-50, 15, new ElementalResist(Elements.Water), ModType.PERCENT),
                new StatModifier(-50, 15, new ElementalResist(Elements.Fire), ModType.PERCENT),
                new StatModifier(-50, 15, new ElementalResist(Elements.Earth), ModType.PERCENT)
            ))
            .makeRuneWordOnly()
            .devComment("global crit + res, high RNG")
            .build();

    }
}
