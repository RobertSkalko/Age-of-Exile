package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalHit;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class WeaponPrefixes implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_wep_dmg")
            .add(Elements.Fire, "Scorched")
            .add(Elements.Water, "Chilled")
            .add(Elements.Thunder, "Sparkling")
            .add(Elements.Nature, "Poisoned")
            .tier(1, x -> Arrays.asList(new StatModifier(2, 2.5F, 2.5F, 3, new AttackDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(1, 1.5F, 1.5F, 2, new AttackDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(0.5F, 1, 1, 1.5F, new AttackDamage(x), ModType.FLAT)))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("cruel")
            .Named("Cruel")
            .tier(1, new StatModifier(30, 35, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(25, 30, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(20, 25, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(15, 20, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(5, new StatModifier(5, 10, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("tyrannical")
            .Named("Tyrannical")
            .tier(1, new StatModifier(1F, 1.5F, 1.2F, 1.5F, new AttackDamage(Elements.Physical), ModType.FLAT))
            .tier(2, new StatModifier(0.6F, 0.8F, 0.8F, 1F, new AttackDamage(Elements.Physical), ModType.FLAT))
            .tier(3, new StatModifier(0.4F, 0.6F, 0.6F, 0.8F, new AttackDamage(Elements.Physical), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        ///// chance of effect
        AffixBuilder.Normal("snakes")
            .Named("Snake's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.POISON, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("yetis")
            .Named("Yeti's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.FROSTBURN, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.FROSTBURN, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.FROSTBURN, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("drakes")
            .Named("Drakes's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.BURN, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.BURN, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.BURN, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("valkyrie")
            .Named("Valkyrie's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.SHOCK, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.SHOCK, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.SHOCK, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();
        ///// chance of effect

        AffixBuilder.Normal("true_hit")
            .Named("True Hit")
            .tier(1, new StatModifier(5, 10, CriticalHit.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(3, 5, CriticalHit.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(2, 3, CriticalHit.getInstance(), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Suffix()
            .Build();

    }

}
