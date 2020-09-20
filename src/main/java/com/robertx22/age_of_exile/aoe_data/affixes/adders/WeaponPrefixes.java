package com.robertx22.age_of_exile.aoe_data.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
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
            .tier(1, x -> Arrays.asList(new StatModifier(2, 3, 3, 5, new WeaponDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(1, 2, 2, 3, new WeaponDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(0.5F, 1, 1, 2, new WeaponDamage(x), ModType.FLAT)))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("cruel")
            .Named("Cruel")
            .tier(1, new StatModifier(45, 50, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(40, 45, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(30, 40, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(20, 30, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(5, new StatModifier(10, 20, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        AffixBuilder.Normal("tyrannical")
            .Named("Tyrannical")
            .tier(1, new StatModifier(0.3F, 2.5F, 0.5F, 3F, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .tier(2, new StatModifier(0.2F, 1F, 0.3F, 2F, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .tier(3, new StatModifier(0.1F, 0.5F, 0.5F, 1.2F, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Prefix()
            .Build();

        ///// chance of effect
        AffixBuilder.Normal("snakes")
            .Named("Snake's")
            .tier(1, new StatModifier(10, 50, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.POISON, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("yetis")
            .Named("Yeti's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.CHILL, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.CHILL, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.CHILL, ModType.FLAT))
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
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.STATIC, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.STATIC, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.STATIC, ModType.FLAT))
            .includesTags(SlotTag.weapon_family)
            .Weight(50)
            .Prefix()
            .Build();
        ///// chance of effect
    }

}
