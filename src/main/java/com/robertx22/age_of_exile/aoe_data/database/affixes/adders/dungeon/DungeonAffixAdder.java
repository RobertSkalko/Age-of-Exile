package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.dungeon;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class DungeonAffixAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_wep_dmg_dun")
            .add(Elements.Fire, "Burning")
            .add(Elements.Water, "Freezing")
            .add(Elements.Nature, "Poisonous")
            .add(Elements.Physical, "Tyrannical")
            .add(Elements.Light, "Holy")
            .add(Elements.Dark, "Cursed")
            .stats(x -> Arrays.asList(new StatModifier(1, 2, new AttackDamage(x), ModType.FLAT)))
            .DungeonPrefix()
            .Build();

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_ele_mobs")
            .add(Elements.Fire, "Of Embers")
            .add(Elements.Water, "Of Frost")
            .add(Elements.Nature, "Of Diseases")
            .add(Elements.Light, "Of Light")
            .add(Elements.Dark, "Of Curses")
            .stats(x -> Arrays.asList(
                new StatModifier(1, 2, new AttackDamage(x), ModType.FLAT),
                new StatModifier(20, 30, new ElementalResist(x), ModType.FLAT)
            ))
            .DungeonSuffix()
            .Build();

        AffixBuilder.Normal("armored_mobs")
            .Named("Armored")
            .stats(new StatModifier(20, 50, Armor.getInstance(), ModType.PERCENT))
            .DungeonPrefix()
            .Build();

        AffixBuilder.Normal("accurate_mobs")
            .Named("Accurate")
            .stats(new StatModifier(20, 50, Stats.ACCURACY.get(), ModType.PERCENT))
            .DungeonPrefix()
            .Build();

        AffixBuilder.Normal("life_on_hit_dun")
            .Named("Of Vampires")
            .stats(new StatModifier(3, 5, Stats.LIFESTEAL.get(), ModType.FLAT))
            .DungeonSuffix()
            .Build();

    }
}
