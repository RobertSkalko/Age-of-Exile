package com.robertx22.age_of_exile.aoe_data.database.transcendent_affix;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import static com.robertx22.age_of_exile.database.data.transc_affix.TranscendentAffix.Builder.of;

public class TranscendentAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        of("crit", "Aspect of Malice", 1000, true, new StatModifier(5, 15, Stats.CRIT_CHANCE.get()));
        of("spell_crit", "Mana Hazard", 1000, true, new StatModifier(8, 20, Stats.SPELL_CRIT_DAMAGE.get()));
        of("grudge", "Eternal Grudge", 1000, true, new StatModifier(5, 20, Stats.DOT_DAMAGE.get()));
        of("spell_lifesteal", "Bitter Vengeance", 1000, true, new StatModifier(2, 6, Stats.SPELL_LIFESTEAL.get()));
        of("lifesteal", "Taste of Blood", 1000, true, new StatModifier(2, 6, Stats.LIFESTEAL.get()));

        of("armor", "Bulwark", 1000, false, new StatModifier(5, 20, Armor.getInstance(), ModType.PERCENT));
        of("hp", "Eternity", 1000, false, new StatModifier(4, 16, Health.getInstance(), ModType.PERCENT));
        of("ene", "Youth", 1000, false, new StatModifier(5, 20, Energy.getInstance(), ModType.PERCENT));
        of("mana", "Brilliance", 1000, false, new StatModifier(5, 20, Mana.getInstance(), ModType.PERCENT));

    }
}
