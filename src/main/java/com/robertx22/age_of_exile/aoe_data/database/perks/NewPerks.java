package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class NewPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.gameChanger("songbird", "Songbird",
            new OptScaleExactStat(-50, Stats.TOTAL_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(25, Stats.INCREASED_EFFECT_OF_AURAS_GIVEN.get()),
            new OptScaleExactStat(25, Stats.HEAL_CRIT_CHANCE.get()),
            new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get())
        );

        PerkBuilder.bigStat("tale_of_war", "Tale of War",
            new OptScaleExactStat(25, Stats.INCREASED_EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.offensive))
        );

        PerkBuilder.bigStat("robust_chainmail", "Robust Chainmail",
            new OptScaleExactStat(-20, Stats.PROJECTILE_DAMAGE_RECEIVED.get(), ModType.FLAT),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.SHIELD_STRENGTH.get())
        );

    }
}
