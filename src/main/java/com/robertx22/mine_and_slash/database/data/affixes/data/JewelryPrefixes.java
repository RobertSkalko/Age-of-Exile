package com.robertx22.mine_and_slash.database.data.affixes.data;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.affixes.ElementalAffixBuilder;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.requirements.SlotRequirement;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.ReducedAllStatReqOnItem;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class JewelryPrefixes implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
            .guid(x -> x.guidName + "_wep_dmg_jewelry")
            .add(Elements.Fire, "Smoking")
            .add(Elements.Water, "Freezing")
            .add(Elements.Thunder, "Shocking")
            .add(Elements.Nature, "Dripping")
            .tier(1, x -> Arrays.asList(new StatModifier(2, 3, 2, 3, new WeaponDamage(x), ModType.FLAT)))
            .tier(2, x -> Arrays.asList(new StatModifier(1, 3, 2, 3, new WeaponDamage(x), ModType.FLAT)))
            .tier(3, x -> Arrays.asList(new StatModifier(1, 1, 2, 2, new WeaponDamage(x), ModType.FLAT)))
            .Req(SlotRequirement.of(BaseGearType.SlotFamily.Jewelry))
            .Weight(200)
            .Prefix()
            .Build();

        AffixBuilder.Normal("of_the_troll")
            .Named("Of The Troll")
            .tier(1, new StatModifier(1, 2, HealthRegen.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.5F, 1, HealthRegen.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.3F, 0.5F, HealthRegen.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.of(x -> x.family()
                .isJewelry() || x.getTags()
                .contains(BaseGearType.SlotTag.Plate)))
            .Weight(200)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_spirit_markings")
            .Named("Of Spirit Markings")
            .tier(1, new StatModifier(1, 2, MagicShieldRegen.getInstance(), ModType.FLAT))
            .tier(2, new StatModifier(0.5F, 1, MagicShieldRegen.getInstance(), ModType.FLAT))
            .tier(3, new StatModifier(0.3F, 0.5F, MagicShieldRegen.getInstance(), ModType.FLAT))
            .Req(SlotRequirement.of(x -> x.family()
                .isJewelry() || x.getTags()
                .contains(BaseGearType.SlotTag.Cloth)))
            .Weight(200)
            .Suffix()
            .Build();

        AffixBuilder.Normal("of_ease")
            .Named("Of Ease")
            .tier(1, new StatModifier(20, 30, new ReducedAllStatReqOnItem(), ModType.FLAT))
            .tier(2, new StatModifier(15, 20, new ReducedAllStatReqOnItem(), ModType.FLAT))
            .tier(3, new StatModifier(10, 15, new ReducedAllStatReqOnItem(), ModType.FLAT))
            .Req(SlotRequirement.of(x -> x.getStatRequirements()
                .hasAny()))
            .Weight(300)
            .Suffix()
            .Build();

    }
}
