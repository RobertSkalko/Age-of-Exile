package com.robertx22.mine_and_slash.database.data.affixes.data;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.affixes.ElementalAffixBuilder;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.requirements.SlotRequirement;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

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
            .tier(1, x -> Arrays.asList(new StatModifier(1, 3, 2, 6, new WeaponDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(1, 3, 2, 6, new WeaponDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(1, 3, 2, 6, new WeaponDamage(x), ModType.FLAT)))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Prefix()
            .Build();

        AffixBuilder.Normal("cruel")
            .Named("Cruel")
            .tier(1, new StatModifier(60, 75, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(2, new StatModifier(45, 60, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(3, new StatModifier(35, 45, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(4, new StatModifier(20, 35, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .tier(5, new StatModifier(10, 20, new WeaponDamage(Elements.Physical), ModType.LOCAL_INCREASE))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Prefix()
            .Build();

        AffixBuilder.Normal("tyrannical")
            .Named("Tyrannical")
            .tier(1, new StatModifier(0.3F, 2.5F, 0.5F, 3F, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .tier(2, new StatModifier(0.2F, 1F, 0.3F, 2F, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .tier(3, new StatModifier(0.1F, 0.5F, 0.5F, 1.2F, new WeaponDamage(Elements.Physical), ModType.FLAT))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Prefix()
            .Build();

        ///// chance of effect
        AffixBuilder.Normal("snakes")
            .Named("Snake's")
            .tier(1, new StatModifier(10, 50, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.POISON, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.POISON, ModType.FLAT))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("yetis")
            .Named("Yeti's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.CHILL, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.CHILL, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.CHILL, ModType.FLAT))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("drakes")
            .Named("Drakes's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.BURN, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.BURN, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.BURN, ModType.FLAT))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Weight(50)
            .Prefix()
            .Build();

        AffixBuilder.Normal("valkyrie")
            .Named("Valkyrie's")
            .tier(1, new StatModifier(10, 15, ChanceToApplyEffect.STATIC, ModType.FLAT))
            .tier(2, new StatModifier(8, 10, ChanceToApplyEffect.STATIC, ModType.FLAT))
            .tier(3, new StatModifier(5, 8, ChanceToApplyEffect.STATIC, ModType.FLAT))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Weapon))
            .Weight(50)
            .Prefix()
            .Build();
        ///// chance of effect
    }

}
