package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class WeaponPrefixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_wep_dmg")
            .add(Elements.Fire, "Scorched")
            .add(Elements.Water, "Chilled")
            .add(Elements.Earth, "Poisoned")
            .add(Elements.Physical, "Tyrannical")
            .stats(x -> Arrays.asList(new StatModifier(3, 10, Stats.ELEMENTAL_DAMAGE.get(x), ModType.FLAT)))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_spell_ele_dmg")
            .add(Elements.Fire, "Scorched")
            .add(Elements.Water, "Chilled")
            .add(Elements.Earth, "Poisoned")
            .stats(x -> Arrays.asList(new StatModifier(5, 15, Stats.ELEMENTAL_SPELL_DAMAGE.get(x))))
            .Weight(500)
            .includesTags(SlotTag.mage_weapon)
            .Prefix()
            .Build();

        AffixBuilder.Normal("desolation")
            .Named("Desolation")
            .stats(new StatModifier(2, 6, Stats.SPELL_CRIT_CHANCE.get()), new StatModifier(3, 10, Stats.SPELL_CRIT_DAMAGE.get()))
            .includesTags(SlotTag.mage_weapon)
            .Weight(100)
            .Prefix()
            .Build();

        AffixBuilder.Normal("cruel")
            .Named("Cruel")
            .stats(new StatModifier(5, 15, new AttackDamage(Elements.Physical), ModType.PERCENT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("true_hit")
            .Named("True Hit")
            .stats(new StatModifier(2, 10, Stats.CRIT_CHANCE.get(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("crit_prefix")
            .Named("Critical")
            .stats(new StatModifier(4, 12, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("heal_crit_prefix")
            .Named("Truthful")
            .stats(new StatModifier(3, 12, Stats.HEAL_CRIT_CHANCE.get(), ModType.FLAT))
            .includesTags(SlotTag.scepter)
            .Prefix()
            .Build();

        AffixBuilder.Normal("heal_crit_dmg_prefix")
            .Named("Inspiring")
            .stats(new StatModifier(5, 20, Stats.HEAL_CRIT_DAMAGE.get(), ModType.FLAT))
            .includesTags(SlotTag.scepter)
            .Prefix()
            .Build();

    }

}
