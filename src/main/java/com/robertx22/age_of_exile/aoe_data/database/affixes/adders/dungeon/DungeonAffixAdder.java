package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.dungeon;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class DungeonAffixAdder implements ISlashRegistryInit {

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
            .tier(1, x -> Arrays.asList(new StatModifier(1, 2, 2, 3, new AttackDamage(x), ModType.FLAT)))
            .DungeonPrefix()
            .Build();

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_ele_mobs")
            .add(Elements.Fire, "Of Embers")
            .add(Elements.Water, "Of Frost")
            .add(Elements.Nature, "Of Diseases")
            .add(Elements.Light, "Of Light")
            .add(Elements.Dark, "Of Curses")
            .tier(1, x -> Arrays.asList(
                new StatModifier(1, 2, 2, 3, new AttackDamage(x), ModType.FLAT),
                new StatModifier(20, 30, new ElementalResist(x), ModType.FLAT)
            ))
            .DungeonSuffix()
            .Build();

        AffixBuilder.Normal("armored_mobs")
            .Named("Armored")
            .tier(1, new StatModifier(20, 50, Armor.getInstance(), ModType.LOCAL_INCREASE))
            .DungeonPrefix()
            .Build();

        AffixBuilder.Normal("accurate_mobs")
            .Named("Accurate")
            .tier(1, new StatModifier(20, 50, Accuracy.getInstance(), ModType.LOCAL_INCREASE))
            .DungeonPrefix()
            .Build();

        AffixBuilder.Normal("life_on_hit_dun")
            .Named("Of Vampires")
            .tier(1, new StatModifier(3, 5, Lifesteal.getInstance(), ModType.FLAT))
            .DungeonSuffix()
            .Build();

    }
}
